package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.search.application.service.GenericProductVarietyService;
import com.platformcommons.platform.service.search.application.utility.TraderTypeCategoryUtil;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.dto.*;
import com.platformcommons.platform.service.search.facade.assembler.impl.GenericProductVarietyDTOAssembler;
import com.platformcommons.platform.service.search.facade.GenericProductVarietyFacade;
import com.platformcommons.platform.service.search.facade.client.CommonsReportClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimplePivotField;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.FacetPivotFieldEntry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class GenericProductVarietyFacadeImpl implements GenericProductVarietyFacade {

    @Autowired
    private GenericProductVarietyDTOAssembler assembler;

    @Autowired
    private GenericProductVarietyService genericProductVarietyService;

    @Autowired
    private TraderTypeCategoryUtil traderTypeCategoryUtil;

    @Autowired
    private CommonsReportClient commonsReportClient;

    @Value("${commons.platform.service.market-client.api-key:YXBwS2V5OmRlYzAxODgzLTZjNDItNDMxOC1hYjk1LWRmZjJkMzEwNDFiMSxhcHBDb2RlOkNPTU1PTlMuU0VSQUNIX0NMSUVOVA==}")
    private String appKey;

    @Override
    public GenericProductVarietySearchDTO save(GenericProductVarietySearchDTO genericProductVarietySearchDTO) {
        return assembler.toDTO(genericProductVarietyService.save(assembler.fromDTO(genericProductVarietySearchDTO)));
    }

    @Override
    public GenericProductVarietySearchDTO getById(Long id) {
        return assembler.toDTO(genericProductVarietyService.getById(id));
    }

    @Override
    public FacetPageDTO<GenericProductVarietySearchDTO> filterSearch(String searchTerm, Set<String> facetFields,
                                                                     Integer page, Integer size, String sortBy, String direction) {
        FacetPage<GenericProductVariety> facetPage = genericProductVarietyService.filterSearch(searchTerm,facetFields,page,
                size,sortBy,direction);
        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        facetFields.forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            facetPage.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<>(facetPage.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),
                facetPage.hasNext(), facetResult, facetPage.getTotalElements());


    }

    @Override
    public FacetPageDTO<GenericProductVarietySearchDTO> getFacetWithFilters(GenericProductVarietyFilterDTO genericProductVarietyFilterDTO) {
        FacetPage<GenericProductVariety> facetPage = genericProductVarietyService.getFacetWithFilters(genericProductVarietyFilterDTO);
        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        genericProductVarietyFilterDTO.getFields().forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            facetPage.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<>(facetPage.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),
                facetPage.hasNext(), facetResult, facetPage.getTotalElements());

    }

    @Override
    public FacetPageDTO<GenericProductVarietySearchDTO> getFacetWithFilters(String channelCode, String marketCode, String searchTerm, Set<String> categoryCodes,
                                                                            Set<String> subCategoryCodes, Set<String> fields, Long maxSellerCount,
                                                                            Double priceStartRange, Double priceEndRange,
                                                                            Double quantityStartRange, Double quantityEndRange, Set<Long> genericProductVarietyId,
                                                                            Set<Long> genericProductId, Long excludeGenericProductVarietyId, Integer page, Integer size, String sortBy, String direction) {
        GenericProductVarietyFilterDTO filterDTO = GenericProductVarietyFilterDTO.builder()
                .marketCode(marketCode)
                .channelCode(channelCode)
                .fields(fields)
                .searchTerm(searchTerm)
                .categories(categoryCodes)
                .subCategories(subCategoryCodes)
                .maxSellerCount(maxSellerCount)
                .priceStartRange(priceStartRange)
                .priceEndRange(priceEndRange)
                .quantityStartRange(quantityStartRange)
                .quantityEndRange(quantityEndRange)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .genericProductVarietyId(genericProductVarietyId)
                .genericProductId(genericProductId)
                .excludeGenericProductVarietyId(excludeGenericProductVarietyId)
                .build();
        return this.getFacetWithFilters(filterDTO);

    }

    @Override
    public GenericProductVarietySearchDTO getByGenericProductVarietyId(Long genericProductVarietyId, String channelCode, String marketCode) {
        return assembler.toDTO(genericProductVarietyService.getByGenericProductVarietyId(genericProductVarietyId,channelCode,marketCode));
    }

    @Override
    public Set<GenericProductSearchDTO> getGenericProducts(String marketCode,String channelCode,String categoryCode,String subCategoryCode,String searchTerm) {
        FacetPage<GenericProductVariety> facetResult = genericProductVarietyService.getGenericProducts(marketCode,channelCode,categoryCode,subCategoryCode,searchTerm);

        List<FacetPivotFieldEntry> pivot = facetResult.getPivot(new SimplePivotField("genericProductId","genericProductName_str"));

        Set<GenericProductSearchDTO> genericProductSearchDTOS = new HashSet<>();
        pivot.forEach(facetPivotFieldEntry -> {
            Long genericProductId = Long.parseLong(facetPivotFieldEntry.getValue());
            List<FacetPivotFieldEntry> genericProductNameEntries = facetPivotFieldEntry.getPivot();
            genericProductNameEntries.forEach(genericProductNameEntry->{
                String genericProductName = genericProductNameEntry.getValue();
                genericProductSearchDTOS.add(GenericProductSearchDTO.builder()
                        .genericProductId(genericProductId)
                        .genericProductName(genericProductName)
                        .build());
            });
        });

        return genericProductSearchDTOS;
    }

    @Override
    public FacetPageDTO<GenericProductVarietySearchDTO> getFacetWithFiltersV2(String channelCode, String marketCode, String searchTerm,
                                                                              Set<String> categoryCodes, Set<String> subCategoryCodes,
                                                                              Set<String> fields, Long maxSellerCount, Double priceStartRange,
                                                                              Double priceEndRange, Double quantityStartRange,
                                                                              Double quantityEndRange, Set<Long> genericProductVarietyId,
                                                                              Set<Long> genericProductId, Long excludeGenericProductVarietyId,Set<String> excludeCategoryCodes,
                                                                              Integer page, Integer size, String sortBy, String direction,String parentClassificationCode,
                                                                              String sourceTraderType, String transactionType, Long solutionId) {
        GenericProductVarietyFilterDTO filterDTO = GenericProductVarietyFilterDTO.builder()
                .marketCode(marketCode)
                .channelCode(channelCode)
                .fields(fields)
                .searchTerm(searchTerm)
                .categories(categoryCodes)
                .subCategories(subCategoryCodes)
                .maxSellerCount(maxSellerCount)
                .priceStartRange(priceStartRange)
                .priceEndRange(priceEndRange)
                .quantityStartRange(quantityStartRange)
                .quantityEndRange(quantityEndRange)
                .page(page)
                .size(size)
                .sortBy(sortBy)
                .direction(direction)
                .genericProductVarietyId(genericProductVarietyId)
                .genericProductId(genericProductId)
                .excludeGenericProductVarietyId(excludeGenericProductVarietyId)
                .excludeCategoryCodes(excludeCategoryCodes)
                .solutionId(solutionId)
                .build();

        Set<String> classificationCodes = new HashSet<>();
        if(sourceTraderType!=null && !sourceTraderType.isEmpty()){
            classificationCodes = traderTypeCategoryUtil.getClassificationCodesForTraderInterest(filterDTO.getMarketCode(), parentClassificationCode,sourceTraderType,transactionType);
        }

        FacetPage<GenericProductVariety> facetPage = genericProductVarietyService.getFacetWithFiltersV2(filterDTO,classificationCodes,sourceTraderType);
        Map<String, Map<String,Long>> facetResult = new HashMap<>();
        fields.forEach(it-> {
            Map<String,Long> map = new HashMap<>();
            facetPage.getFacetResultPage(it).getContent().forEach(it2->map.put(it2.getValue(),it2.getValueCount()));
            facetResult.put(it,map);
        });
        return new FacetPageDTO<>(facetPage.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new)),
                facetPage.hasNext(), facetResult, facetPage.getTotalElements());
    }

    @Override
    public Set<GenericProductSearchDTO> getGenericProductsV2(String marketCode,String channelCode,String categoryCode,String subCategoryCode,
                                                             String searchTerm,String parentClassificationCode, String sourceTraderType, String transactionType) {
        Set<String> classificationCodes = new HashSet<>();
        if(sourceTraderType!=null && !sourceTraderType.isEmpty()){
            classificationCodes = traderTypeCategoryUtil.getClassificationCodesForTraderInterest(marketCode, parentClassificationCode,sourceTraderType,transactionType);
        }
        FacetPage<GenericProductVariety> facetResult = genericProductVarietyService.getGenericProductsV2(marketCode,channelCode,categoryCode,
                subCategoryCode,searchTerm,classificationCodes,sourceTraderType);

        List<FacetPivotFieldEntry> pivot = facetResult.getPivot(new SimplePivotField("genericProductId","genericProductName_str"));

        Set<GenericProductSearchDTO> genericProductSearchDTOS = new HashSet<>();
        pivot.forEach(facetPivotFieldEntry -> {
            Long genericProductId = Long.parseLong(facetPivotFieldEntry.getValue());
            List<FacetPivotFieldEntry> genericProductNameEntries = facetPivotFieldEntry.getPivot();
            genericProductNameEntries.forEach(genericProductNameEntry->{
                String genericProductName = genericProductNameEntry.getValue();
                genericProductSearchDTOS.add(GenericProductSearchDTO.builder()
                        .genericProductId(genericProductId)
                        .genericProductName(genericProductName)
                        .build());
            });
        });

        return genericProductSearchDTOS;
    }

    @Override
    public Set<GenericProductVarietySearchDTO> getGenericProductVarieties(String marketCode, String channelCode, Set<String> genericProductVarietyCodes) {
        return assembler.toDTOs(genericProductVarietyService.getGenericProductVarieties(marketCode,channelCode,genericProductVarietyCodes));
    }

    @Override
    public void reindex() {
        genericProductVarietyService.reindex();
    }

    @Override
    public void refreshGenericProductVarieties(Long marketId, Long channelId, Set<Long> genericProductVarietyIds) {
        for (Long gpvId : genericProductVarietyIds) {
            GPVSummaryDTO summary = fetchGpvSummary(channelId, gpvId);
            genericProductVarietyService.updateSolrWithSummary(marketId, channelId, gpvId, summary);
        }
    }

    private GPVSummaryDTO fetchGpvSummary(Long channelId, Long gpvId) {
        String paramFormat = "CHANNLE_ID=%s--VARIETY_ID=%s";
        String params = String.format(paramFormat, channelId, gpvId);

        Set<GPVSummaryDTO> summaries = commonsReportClient
                .getGpvProductSummary(
                        PlatformSecurityConstant.TOKEN_TYPE_APP_KEY + " " + appKey,
                        params, 0, 1)
                .getBody();

        return (summaries != null)
                ? summaries.stream().findFirst().orElse(null)
                : null;
    }

}
