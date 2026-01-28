package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductService;
import com.platformcommons.platform.service.search.application.utility.TraderTypeCategoryUtil;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.dto.TMAChannelProductDTO;
import com.platformcommons.platform.service.search.facade.TMAChannelProductFacade;
import com.platformcommons.platform.service.search.facade.assembler.TMAChannelProductDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class TMAChannelProductFacadeImpl implements TMAChannelProductFacade {

    @Autowired
    private TMAChannelProductService service;

    @Autowired
    private TMAChannelProductDTOAssembler assembler;

    @Autowired
    private TraderTypeCategoryUtil traderTypeCategoryUtil;

    @Override
    public TMAChannelProductDTO save(TMAChannelProductDTO tmaChannelProductDTO) {
        return assembler.toDTO(service.save(assembler.fromDTO(tmaChannelProductDTO)));
    }

    @Override
    public void saveAll(Set<TMAChannelProductDTO> tmaChannelProductDTOS) {
        service.saveAll(assembler.fromDTOs(tmaChannelProductDTOS));
    }

    @Override
    public PageDTO<TMAChannelProductDTO> getByGenericProductVarietyId(String marketCode, String channelCode, Long varietyId, Set<Long> excludeVarietyIds,Long traderId, Integer page, Integer size, String sortBy, String direction) {
        Page<TMAChannelProduct> result= service.getByGenericProductVarietyId(marketCode,channelCode,varietyId,excludeVarietyIds,traderId,page,size,sortBy,direction);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());

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
    public Set<TMAChannelProductDTO> getByVarietyIdWithOutPage(Long marketId, Long channelId, Set<Long> genericProductVarietyIds) {
        return assembler.toDTOs(service.getByVarietyIdWithOutPage(marketId,channelId,genericProductVarietyIds));
    }

    @Override
    public PageDTO<TMAChannelProductDTO> getByGenericProductVarietyIdV2(String marketCode, String channelCode, Long varietyId,
                                                                        Set<Long> excludeVarietyIds,Long traderId, Integer page,
                                                                        Integer size, String sortBy, String direction,String parentClassificationCode,
                                                                        String sourceTraderType, String transactionType, Long solutionId,
                                                                        Set<String> categoryCodes, Set<String> subCategoryCodes, String searchTerm, Set<Long> linkedSolutionResourceIds) {
        Set<String> classificationCodes = new HashSet<>();
        if(sourceTraderType!=null && !sourceTraderType.isEmpty()){
            classificationCodes = traderTypeCategoryUtil.getClassificationCodesForTraderInterest(marketCode, parentClassificationCode,sourceTraderType,transactionType);
        }
        Page<TMAChannelProduct> result= service.getByGenericProductVarietyIdV2(marketCode,channelCode,varietyId,excludeVarietyIds,
                traderId,page,size,sortBy,direction, classificationCodes,sourceTraderType,solutionId,categoryCodes,subCategoryCodes,searchTerm,linkedSolutionResourceIds);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext(),result.getTotalElements());

    }

    @Override
    public void reindex() {
        service.reindex();
    }
}
