package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.product.dto.ProductSKUDTO;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductSKUService;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelProductSKUDTO;
import com.platformcommons.platform.service.search.facade.CommonsLocationFacade;
import com.platformcommons.platform.service.search.facade.TMAChannelProductSKUFacade;
import com.platformcommons.platform.service.search.facade.assembler.TMAChannelProductSKUDTOAssembler;
import com.platformcommons.platform.service.search.facade.client.ClassificationClientV2;
import com.platformcommons.platform.service.search.messaging.mapper.TMAChannelProductSKUEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Transactional
public class TMAChannelProductSKUFacadeImpl implements TMAChannelProductSKUFacade {

    @Autowired
    private TMAChannelProductSKUService service;

    @Autowired
    private TMAChannelProductSKUDTOAssembler assembler;

    @Autowired
    private CommonsLocationFacade commonsLocationFacade;

    @Autowired
    private ClassificationClientV2 classificationClientV2;

    @Autowired
    private TMAChannelProductSKUEventMapper tmaChannelProductSKUEventMapper;

    @Override
    public TMAChannelProductSKUDTO save(TMAChannelProductSKUDTO tmaChannelProductSKUDTO) {
        return assembler.toDTO(service.save(assembler.fromDTO(tmaChannelProductSKUDTO)));
    }

    @Override
    public void updateWithTrader(Long id, String traderDisplayName, String iconPic) {
        service.updateTraderDetails(id,traderDisplayName,iconPic);
    }

    @Override
    public void updateWithProduct(ProductDTO payload) {
        service.updateWithProduct(payload);
    }

    @Override
    public FacetPageDTO<TMAChannelProductSKUDTO> getPublishedSKUs(String channelCode, String marketCode,
                                                                  String searchTerm, Set<String> categoryCodes,
                                                                  Set<String> subCategoryCodes, Set<String> fields,
                                                                  Long genericProductId, Long genericProductVarietyId, Long traderId, Integer page, Integer size,
                                                                  String sortBy, String direction, Long solutionId, String productSubType,
                                                                  Set<String> skuFactorCodes, String locationCode, Boolean isTypeAsOffering,
                                                                  String tagType,Set<String> tagCodes) {

        // Get location hierarchy if locationCode is provided
        Set<String> servingAreaCodes = new HashSet<>();
        if (locationCode != null && !locationCode.isEmpty()) {
            servingAreaCodes = commonsLocationFacade.getLocationHierarchy(locationCode);
        }

        FacetPage<TMAChannelProductSKU> result= service.getPublishedSKUs(channelCode,marketCode,searchTerm,categoryCodes,subCategoryCodes,
                fields,genericProductId,genericProductVarietyId,traderId,page,size,sortBy,direction,solutionId,productSubType,skuFactorCodes,
                servingAreaCodes,isTypeAsOffering,tagType,tagCodes);

        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        fields.forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            result.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),facetResult,result.getTotalElements());

    }

    @Override
    public Map<Long,TMAChannelProductSKUDTO> getTMAChannelProductSkusById(Set<Long> tmaChannelProductSkuIds) {
        Set<TMAChannelProductSKUDTO> result  = assembler.toDTOs(service.getTMAChannelProductSkusById(tmaChannelProductSkuIds));
        return result.stream()
                .filter(dto -> dto.getSkuTmaChannelProductId() != null)
                .collect(Collectors.toMap(
                        TMAChannelProductSKUDTO::getSkuTmaChannelProductId,
                        Function.identity()
                ));
    }

    @Override
    public void reindex() {
        service.reindex();
    }

    @Override
    public void refreshTmaChannelProductSKUS(Long marketId, Long channelId, Set<Long> productSkuIds) {

        if (productSkuIds == null || productSkuIds.isEmpty()) {
            return;
        }

        Map<Long, ProductSKUDTO> productSkuMap = fetchProductSkusFromService(productSkuIds);
        if (productSkuMap.isEmpty()) {
            return;
        }

        Set<String> skuIds = productSkuMap.values().stream()
                .map(dto -> generateTMAChannelProductSKUId(dto.getId(), dto.getProductId(), channelId, marketId))
                .collect(Collectors.toSet());
        List<TMAChannelProductSKU> solrSkus = service.fetchBySkuIdsAndChannelMarket(skuIds, marketId, channelId);
        Map<String, TMAChannelProductSKU> solrSkuMap = solrSkus.stream()
                .collect(Collectors.toMap(TMAChannelProductSKU::getId, sku -> sku));

        List<TMAChannelProductSKU> toUpdate = productSkuMap.values().stream()
                .map(dto -> {
                    String skuId = generateTMAChannelProductSKUId(dto.getId(), dto.getProductId(), channelId, marketId);
                    TMAChannelProductSKU existing = solrSkuMap.get(skuId);
                    if (existing != null) {
                        TMAChannelProductSKU mapped = tmaChannelProductSKUEventMapper.mapTagsOnly(dto, marketId, channelId);
                        existing.updateTagDetails(mapped);
                        return existing;
                    }
                    return null;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if(!toUpdate.isEmpty()){
            service.saveAllByBeans(toUpdate);
        }

    }

    private Map<Long, ProductSKUDTO> fetchProductSkusFromService(Set<Long> productSkuIds) {
        ResponseEntity<Map<Long, ProductSKUDTO>> response =
                classificationClientV2.getProductSKUs(productSkuIds, PlatformSecurityUtil.getToken());
        return response.getBody() != null ? response.getBody() : Collections.emptyMap();
    }

    private String generateTMAChannelProductSKUId(Long skuId,Long productId, Long channelId, Long marketId){
        return  skuId+"-"+productId+"-"+channelId+"-"+marketId;
    }

    @Override
    public Map<Long, TMAChannelProductSKUDTO> getTMAChannelProductSkusByProductSkuIds(Set<Long> productSKUIds, Long marketId, Long channelId) {
        Set<TMAChannelProductSKUDTO> result  = assembler.toDTOs(service.getTMAChannelProductSkusByProductSkuIds(productSKUIds,marketId,channelId));
        return result.stream()
                .filter(dto -> dto.getSkuTmaChannelProductId() != null)
                .collect(Collectors.toMap(
                        TMAChannelProductSKUDTO::getProductSKUId,
                        Function.identity()
                ));
    }

}
