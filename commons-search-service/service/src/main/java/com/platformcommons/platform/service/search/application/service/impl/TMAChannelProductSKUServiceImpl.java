package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.market.dto.UpdateTmaChannelProductGpGpvRequestDTO;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.product.dto.ProductSKUDTO;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.GenericProductVarietyService;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductSKUService;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.application.utility.SolrUtil;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelProductSKU;
import com.platformcommons.platform.service.search.domain.repo.TMAChannelProductSKURepository;
import com.platformcommons.platform.service.search.dto.GPVSummaryDTO;
import com.platformcommons.platform.service.search.dto.TmaChannelProductStatusDTO;
import com.platformcommons.platform.service.search.facade.client.ClassificationClientV2;
import com.platformcommons.platform.service.search.facade.client.CommonsReportClient;
import com.platformcommons.platform.service.search.facade.client.GenericProductVarietyClientV2;
import com.platformcommons.platform.service.search.messaging.mapper.TMAChannelProductSKUEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TMAChannelProductSKUServiceImpl implements TMAChannelProductSKUService {

    @Autowired
    private TMAChannelProductSKURepository repository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private CommonsReportClient commonsReportClient;

    @Autowired
    private GenericProductVarietyClientV2 genericProductVarietyClient;

    @Autowired
    private GenericProductVarietyService genericProductVarietyService;

    @Autowired
    private TMAChannelProductService tmaChannelProductService;

    @Autowired
    private TMAChannelProductSKUEventMapper tmaChannelProductSKUEventMapper;

    @Value("${commons.platform.service.market-client.api-key:YXBwS2V5OmRlYzAxODgzLTZjNDItNDMxOC1hYjk1LWRmZjJkMzEwNDFiMSxhcHBDb2RlOkNPTU1PTlMuU0VSQUNIX0NMSUVOVA==}")
    private String appKey;

    @Autowired
    private ClassificationClientV2 classificationClientV2;

    @Override
    public TMAChannelProductSKU save(TMAChannelProductSKU tmaChannelProductSKU) {
        tmaChannelProductSKU.init();
        return repository.save(tmaChannelProductSKU);
    }

    @Override
    public Set<TMAChannelProductSKU> saveAll(Set<TMAChannelProductSKU> tmaChannelProductSKUSet) {
        tmaChannelProductSKUSet.forEach(TMAChannelProductSKU::init);
        return StreamSupport.stream(repository.saveAll(tmaChannelProductSKUSet).spliterator(), false)
                .collect(Collectors.toSet());
    }

    @Override
    public FacetPage<TMAChannelProductSKU> getTMAChannelProductSKUWithFilter(String searchTerm, String groupByField,String pivotFieldKey,  String pivotFieldValue,
                                                                   List<String> projectionFields, Integer page, Integer size, String sortBy, String direction) {
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForTMAChannelProductSKUSearch(searchTerm)))
                .setGroupOptions(new GroupOptions().addGroupByField(groupByField).setLimit(1));
        facetQuery.setFacetOptions(new FacetOptions().addFacetOnPivot(pivotFieldKey,pivotFieldValue));
        facetQuery.setPageRequest(PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction)));
        FilterQuery filterQuery = new SimpleFilterQuery();
        if(projectionFields!=null && !projectionFields.isEmpty()){
            projectionFields.forEach(it-> facetQuery.addProjectionOnField(new SimpleField(it)));
        }
        facetQuery.addFilterQuery(filterQuery);
        return solrTemplate.queryForFacetPage("tma_channel_product_sku",facetQuery, TMAChannelProductSKU.class);
    }

    @Override
    public void updateTraderDetails(Long traderId, String traderDisplayName, String iconPic) {

        Criteria criteria = new Criteria("traderId").is(traderId);
        Query countQuery = new SimpleQuery(criteria);
        long totalCount = solrTemplate.count("tma_channel_product_sku", countQuery);

        int pageSize = 50;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        for (int page = 0; page < totalPages; page++) {
            Query pagedQuery = new SimpleQuery(criteria);
            pagedQuery.setPageRequest(PageRequest.of(page, pageSize));

            List<TMAChannelProductSKU> products = solrTemplate
                    .query("tma_channel_product_sku", pagedQuery, TMAChannelProductSKU.class)
                    .getContent();

            for (TMAChannelProductSKU product : products) {
                product.setLastModifiedTimestamp();
                product.setTraderName(traderDisplayName != null ? traderDisplayName:product.getTraderName());
                product.setTraderIconURL(iconPic != null ? iconPic:product.getTraderIconURL());
            }

            solrTemplate.saveBeans("tma_channel_product_sku", products);
            solrTemplate.commit("tma_channel_product_sku");
        }
    }

    @Override
    public void updateWithProduct(ProductDTO payload) {
        Set<TMAChannelProductSKU> fetchedList = repository.findAllByProductId(payload.getId());
        fetchedList.forEach(entity -> {
            entity.setLastModifiedTimestamp();
            entity.setProductName(payload.getName().stream().findFirst().get().getText());
            entity.setProductCode(payload.getCode());
        });
        repository.saveAll(fetchedList);
    }

    @Override
    public Set<TMAChannelProductSKU> getAllTMAChannelProductSKUByProductIds(Long productId, Long marketId, Long channelId) {
        return repository.findAllByProductIdAndMarketIdAndChannelId(productId,marketId,channelId);
    }

    @Override
    public FacetPage<TMAChannelProductSKU> getPublishedSKUs(String channelCode, String marketCode,
                                                       String searchTerm, Set<String> categoryCodes, Set<String> subCategoryCodes,
                                                       Set<String> fields, Long genericProductId, Long genericProductVarietyId, Long traderId, Integer page, Integer size,
                                                       String sortBy, String direction, Long solutionId, String productSubType,
                                                            Set<String> skuFactorCodes, Set<String> servingAreaCodes, Boolean isTypeAsOffering,
                                                            String tagType,Set<String> tagCodes) {

        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForTMAChannelProductSKUSearch(searchTerm)))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(fields)
                        .setFacetMinCount(1));
        facetQuery.setPageRequest(PageRequest.of(page,size,
                JPAUtility.convertToSort(sortBy,direction)));

        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(marketCode)
                .and("channelCode").is(channelCode);
        filterQuery.addCriteria(marketAndChannelCriteria);


        if(categoryCodes!=null && !categoryCodes.isEmpty()){
            Criteria criteria = new Criteria("categoryCodes").in(categoryCodes);
            filterQuery.addCriteria(criteria);
        }
        if(subCategoryCodes!=null && !subCategoryCodes.isEmpty()){
            Criteria criteria = new Criteria("subCategoryCodes").in(subCategoryCodes);
            filterQuery.addCriteria(criteria);
        }

        if(genericProductId!=null){
            Criteria criteria = new Criteria("genericProductId").is(genericProductId);
            filterQuery.addCriteria(criteria);
        }

        if (genericProductVarietyId != null && "TAG_TYPE.GENERIC_PRODUCT_VARIETY".equalsIgnoreCase(tagType) && tagCodes != null && !tagCodes.isEmpty()) {
            String safeType = SolrUtil.solrSafeKey(tagType);
            Criteria gpvCriteria = new Criteria("genericProductVarietyId").is(genericProductVarietyId);
            Criteria tagCriteria = new Criteria("TAG_CODE_BY_TYPE_" + safeType)
                    .in(tagCodes.stream().map(SolrUtil::escapeQueryChars).collect(Collectors.toList()));

            Criteria orCriteria = new Criteria().or(gpvCriteria).or(tagCriteria);
            filterQuery.addCriteria(orCriteria);
        } else {
            // fallback: keep your existing handling
            if (genericProductVarietyId != null) {
                filterQuery.addCriteria(new Criteria("genericProductVarietyId").is(genericProductVarietyId));
            }
            FilterQuery fq = searchProductSkusByTagCode(tagType, tagCodes);
            if (fq != null) {
                facetQuery.addFilterQuery(fq);
            }
        }

        if(traderId!=null){
            Criteria criteria = new Criteria("traderId").is(traderId);
            filterQuery.addCriteria(criteria);
        }
        if(solutionId !=null){
            Criteria criteria = new Criteria("approvedForSolutionIds").is(solutionId).or("unApprovedSolutionIds").is(solutionId);
            filterQuery.addCriteria(criteria);
        }
        if(productSubType!=null){
            Criteria criteria = new Criteria("productSubType").is(productSubType);
            filterQuery.addCriteria(criteria);
        }
        if(skuFactorCodes!=null && !skuFactorCodes.isEmpty()){
            Criteria criteria = new Criteria("skuFactorCodes").in(skuFactorCodes);
            filterQuery.addCriteria(criteria);
        }
        if(isTypeAsOffering!=null && isTypeAsOffering){
            Criteria criteria = new Criteria("productType").startsWith("OFFERINGS")
                    .and(new Criteria("productSubType").startsWith("OFFERINGS"));
            filterQuery.addCriteria(criteria);
        }

        if(servingAreaCodes != null && !servingAreaCodes.isEmpty()) {
            List<String> excludedProductTypes = Arrays.asList(
                    "OFFERINGS.KNOWLEDGE_OFFERINGS",
                    "OFFERINGS.PRODUCT_OFFERINGS"
            );
            Criteria nonExcludedCriteria = new Criteria("productType").not().in(excludedProductTypes)
                    .and("servingAreaCodes").in(servingAreaCodes);
            Criteria excludedCriteria = new Criteria("productType").in(excludedProductTypes);
            Criteria finalCriteria = new Criteria().or(nonExcludedCriteria).or(excludedCriteria);
            filterQuery.addCriteria(finalCriteria);
        }

        Criteria criteria = new SimpleStringCriteria("(isActive:true OR (*:* -isActive:[* TO *]))");
        filterQuery.addCriteria(criteria);

        Criteria publishCriteria = new SimpleStringCriteria("(skuTmaChannelProductStatus:TMA_CHANNEL_PRODUCT_PUBLISH_STATUS.PUBLISHED OR (*:* -skuTmaChannelProductStatus:[* TO *]))");
        filterQuery.addCriteria(publishCriteria);

        facetQuery.addFilterQuery(filterQuery);
//        FilterQuery fq = searchProductSkusByTagCode(tagType,tagCodes);
//        if(fq != null){
//            facetQuery.addFilterQuery(fq);
//        }
        return solrTemplate.queryForFacetPage("tma_channel_product_sku",facetQuery, TMAChannelProductSKU.class);
    }

    @Override
    public void deleteTmaChannelProductSKU(Long productSkuId, Long productId, Long traderId, Long channelId, Long marketId) {
        try {
            Criteria skuCriteria = Criteria.where("productSKUId").is(productSkuId).and("productId").is(productId)
                    .and("traderId").is(traderId).and("channelId").is(channelId).and("marketId").is(marketId);
            Query skuQuery = new SimpleQuery(skuCriteria);

            TMAChannelProductSKU sku = solrTemplate.query("tma_channel_product_sku", skuQuery, TMAChannelProductSKU.class)
                    .getContent().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("TMAChannelProductSKU not found for given criteria."));

            sku.setIsActive(false);
            solrTemplate.saveBean("tma_channel_product_sku", sku);
            solrTemplate.commit("tma_channel_product_sku");

            Criteria activeSkusCriteria = Criteria.where("productId").is(productId).and("traderId").is(traderId)
                    .and("channelId").is(channelId).and("marketId").is(marketId).and("isActive").is(true);
            Query activeSkusQuery = new SimpleQuery(activeSkusCriteria);

            long activeSkuCount = solrTemplate.count("tma_channel_product_sku", activeSkusQuery);

            if (activeSkuCount == 0) {
                Criteria productCriteria = Criteria.where("productId").is(productId).and("traderId").is(traderId)
                        .and("channelId").is(channelId).and("marketId").is(marketId);
                Query productQuery = new SimpleQuery(productCriteria);

                TMAChannelProduct product = solrTemplate.query("tma_channel_product", productQuery, TMAChannelProduct.class)
                        .getContent().stream().findFirst()
                        .orElseThrow(() -> new RuntimeException("TMAChannelProduct not found for given criteria."));

                product.setIsActive(false);
                solrTemplate.saveBean("tma_channel_product", product);
                solrTemplate.commit("tma_channel_product");

                String paramFormat = "CHANNLE_ID=%d--VARIETY_ID=%d";
                String params = String.format(paramFormat,product.getChannelId(), product.getGenericProductVarietyId());
                Set<GPVSummaryDTO> gpvSummaryDTOs = commonsReportClient.getGpvProductSummary(PlatformSecurityConstant.TOKEN_TYPE_APP_KEY+ " "+appKey,params,0,1).getBody();

                if (gpvSummaryDTOs != null){
                    GPVSummaryDTO gpvSummaryDTO = gpvSummaryDTOs.stream().findFirst().orElse(null);
                    Criteria gpvCriteria = Criteria.where("genericProductVarietyId").is(product.getGenericProductVarietyId())
                            .and("channelId").is(channelId).and("marketId").is(marketId);
                    Query gpvQuery = new SimpleQuery(gpvCriteria);

                    GenericProductVariety gpv = solrTemplate.query("generic_product_variety", gpvQuery, GenericProductVariety.class)
                            .getContent().stream().findFirst()
                            .orElseThrow(() -> new RuntimeException("GenericProductVariety not found for given criteria."));

                    if(gpvSummaryDTO!=null){
                        gpv.updateSummary(gpvSummaryDTO);
                    }
                    else{
                        gpv.setTraderIds(null);
                        gpv.setTraderCount(0L);
                        gpv.setProductNames(null);
                    }
                    solrTemplate.saveBean("generic_product_variety", gpv);
                    solrTemplate.commit("generic_product_variety");
                }

                //refresh TmaChannelProductSKU GPV tags GPVs
                Set<String> varietyTags = getGenericProductVarietyTagCodes(sku);

                Set<GenericProductVarietyEventDTO> gpvByCodeSet = new HashSet<>();
                if (!varietyTags.isEmpty()) {
                    gpvByCodeSet.addAll(
                            Objects.requireNonNull(genericProductVarietyClient.getCustomForGenericProductVarietyV2(varietyTags, "Appkey " + appKey).getBody())
                    );
                }
                Set<GenericProductVarietyEventDTO> diffSet = gpvByCodeSet.stream()
                        .filter(gpv -> !Objects.equals(gpv.getGenericProductVarietyId(), product.getGenericProductVarietyId()))
                        .collect(Collectors.toSet());

                if (!diffSet.isEmpty()) {
                    for (GenericProductVarietyEventDTO diffGpv : diffSet) {

                        String gpvParamFormat = "CHANNLE_ID=%d--VARIETY_ID=%d";
                        String gpvParams = String.format(gpvParamFormat, product.getChannelId(), diffGpv.getGenericProductVarietyId());

                        Set<GPVSummaryDTO> diffGpvSummaryDTOs =
                                commonsReportClient.getGpvProductSummary(
                                        PlatformSecurityConstant.TOKEN_TYPE_APP_KEY + " " + appKey,
                                        gpvParams, 0, 1
                                ).getBody();

                        if (diffGpvSummaryDTOs != null) {
                            GPVSummaryDTO diffGpvSummaryDTO = diffGpvSummaryDTOs.stream().findFirst().orElse(null);

                            Criteria gpvCriteria = Criteria.where("genericProductVarietyId").is(diffGpv.getGenericProductVarietyId())
                                    .and("channelId").is(product.getChannelId())
                                    .and("marketId").is(product.getMarketId());

                            Query gpvQuery = new SimpleQuery(gpvCriteria);

                            GenericProductVariety gpv = solrTemplate.query("generic_product_variety", gpvQuery, GenericProductVariety.class)
                                    .getContent().stream().findFirst()
                                    .orElseThrow(() -> new RuntimeException("GenericProductVariety not found for given criteria."));

                            if (diffGpvSummaryDTO != null) {
                                gpv.updateSummary(diffGpvSummaryDTO);
                            } else {
                                gpv.setTraderIds(null);
                                gpv.setTraderCount(0L);
                                gpv.setProductNames(null);
                            }

                            solrTemplate.saveBean("generic_product_variety", gpv);
                            solrTemplate.commit("generic_product_variety");
                        }
                    }
                }

            }
            else {
                //refresh product  //Todo
                ////refresh gpv   //Todo
            }
        } catch (Exception e) {
            throw new RuntimeException("Error while deleting TMAChannelProductSKU and updating parent product: " + e.getMessage(), e);
        }

    }

    @Override
    public void updateByTmaChannelProductId(TMAChannelProductSKU tmaChannelProductSKU) {
        Criteria skuCriteria = Criteria.where("skuTmaChannelProductId").is(tmaChannelProductSKU.getSkuTmaChannelProductId());

        Query skuQuery = new SimpleQuery(skuCriteria);
        TMAChannelProductSKU sku = solrTemplate.query("tma_channel_product_sku", skuQuery, TMAChannelProductSKU.class)
                .getContent().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("TMAChannelProductSKU not found for given criteria."));

        sku.setSkuTmaChannelProductStatus(tmaChannelProductSKU.getSkuTmaChannelProductStatus()!=null
                ? tmaChannelProductSKU.getSkuTmaChannelProductStatus() : sku.getSkuTmaChannelProductStatus());

        sku.setSkuTmaChannelProductStatus(getPublishTmaChannelProductSkuStatus(sku.getDefaultSKUTmaChannelProductId()));
        solrTemplate.saveBean("tma_channel_product_sku", sku);
        solrTemplate.commit("tma_channel_product_sku");

        Criteria publishedSkusCriteria = Criteria.where("productId").is(tmaChannelProductSKU.getProductId()).and("traderId").is(tmaChannelProductSKU.getTraderId())
                .and("channelId").is(tmaChannelProductSKU.getChannelId()).and("marketId").is(tmaChannelProductSKU.getMarketId());
        Query publishedSkusQuery = new SimpleQuery(publishedSkusCriteria);

        Criteria publishCriteria = new SimpleStringCriteria("(skuTmaChannelProductStatus:TMA_CHANNEL_PRODUCT_PUBLISH_STATUS.PUBLISHED OR (*:* -skuTmaChannelProductStatus:[* TO *]))");
        publishedSkusQuery.addCriteria(publishCriteria);

        long publishedSkuCount = solrTemplate.count("tma_channel_product_sku", publishedSkusQuery);

        Criteria productCriteria = Criteria.where("productId").is(tmaChannelProductSKU.getProductId()).and("traderId").is(tmaChannelProductSKU.getTraderId())
                .and("channelId").is(tmaChannelProductSKU.getChannelId()).and("marketId").is(tmaChannelProductSKU.getMarketId());
        Query productQuery = new SimpleQuery(productCriteria);
        TMAChannelProduct product = solrTemplate.query("tma_channel_product", productQuery, TMAChannelProduct.class)
                .getContent().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("TMAChannelProduct not found for given criteria."));

        if (publishedSkuCount == 0) {
            product.setTmaChannelProductStatus("TMA_CHANNEL_PRODUCT_PUBLISH_STATUS.DRAFT");
        }
        else {
            product.setTmaChannelProductStatus("TMA_CHANNEL_PRODUCT_PUBLISH_STATUS.PUBLISHED");
        }

        //refresh tmaChannelProduct other details for published/draft tmaChannelProductSKU  //Todo
        solrTemplate.saveBean("tma_channel_product", product);
        solrTemplate.commit("tma_channel_product");

        String paramFormat = "CHANNLE_ID=%d--VARIETY_ID=%d";
        String params = String.format(paramFormat,product.getChannelId(), product.getGenericProductVarietyId());
        Set<GPVSummaryDTO> gpvSummaryDTOs = commonsReportClient.getGpvProductSummary(PlatformSecurityConstant.TOKEN_TYPE_APP_KEY+ " "+appKey,params,0,1).getBody();

        if (gpvSummaryDTOs != null){
            GPVSummaryDTO gpvSummaryDTO = gpvSummaryDTOs.stream().findFirst().orElse(null);
            Criteria gpvCriteria = Criteria.where("genericProductVarietyId").is(product.getGenericProductVarietyId())
                    .and("channelId").is(product.getChannelId()).and("marketId").is(product.getMarketId());
            Query gpvQuery = new SimpleQuery(gpvCriteria);

            GenericProductVariety gpv = solrTemplate.query("generic_product_variety", gpvQuery, GenericProductVariety.class)
                    .getContent().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("GenericProductVariety not found for given criteria."));

            if(gpvSummaryDTO!=null){
                gpv.updateSummary(gpvSummaryDTO);
            }
            else{
                gpv.setTraderIds(null);
                gpv.setTraderCount(0L);
                gpv.setProductNames(null);
            }
            solrTemplate.saveBean("generic_product_variety", gpv);
            solrTemplate.commit("generic_product_variety");
        }

        //refresh TmaChannelProductSKU GPV tags GPVs
        Set<String> varietyTags = getGenericProductVarietyTagCodes(sku);

        Set<GenericProductVarietyEventDTO> gpvByCodeSet = new HashSet<>();
        if (!varietyTags.isEmpty()) {
            gpvByCodeSet.addAll(
                    Objects.requireNonNull(genericProductVarietyClient.getCustomForGenericProductVarietyV2(varietyTags, "Appkey " + appKey).getBody())
            );
        }
        Set<GenericProductVarietyEventDTO> diffSet = gpvByCodeSet.stream()
                .filter(gpv -> !Objects.equals(gpv.getGenericProductVarietyId(), product.getGenericProductVarietyId()))
                .collect(Collectors.toSet());

        if (!diffSet.isEmpty()) {
            for (GenericProductVarietyEventDTO diffGpv : diffSet) {

                String gpvParamFormat = "CHANNLE_ID=%d--VARIETY_ID=%d";
                String gpvParams = String.format(gpvParamFormat, product.getChannelId(), diffGpv.getGenericProductVarietyId());

                Set<GPVSummaryDTO> diffGpvSummaryDTOs =
                        commonsReportClient.getGpvProductSummary(
                                PlatformSecurityConstant.TOKEN_TYPE_APP_KEY + " " + appKey,
                                gpvParams, 0, 1
                        ).getBody();

                if (diffGpvSummaryDTOs != null) {
                    GPVSummaryDTO diffGpvSummaryDTO = diffGpvSummaryDTOs.stream().findFirst().orElse(null);

                    Criteria gpvCriteria = Criteria.where("genericProductVarietyId").is(diffGpv.getGenericProductVarietyId())
                            .and("channelId").is(product.getChannelId())
                            .and("marketId").is(product.getMarketId());

                    Query gpvQuery = new SimpleQuery(gpvCriteria);

                    GenericProductVariety gpv = solrTemplate.query("generic_product_variety", gpvQuery, GenericProductVariety.class)
                            .getContent().stream().findFirst()
                            .orElseThrow(() -> new RuntimeException("GenericProductVariety not found for given criteria."));

                    if (diffGpvSummaryDTO != null) {
                        gpv.updateSummary(diffGpvSummaryDTO);
                    } else {
                        gpv.setTraderIds(null);
                        gpv.setTraderCount(0L);
                        gpv.setProductNames(null);
                    }

                    solrTemplate.saveBean("generic_product_variety", gpv);
                    solrTemplate.commit("generic_product_variety");
                }
            }
        }


    }

    private String getPublishTmaChannelProductSkuStatus(Long skuTmaChannelProductId) {
        if (skuTmaChannelProductId == null) {
            return null;
        }

        String params = String.format("TMA_CHANNEL_PRODUCT_SKU_ID=%d", skuTmaChannelProductId);
        return Optional.ofNullable(
                        commonsReportClient.getTmaChannelProductSkuStatus(
                                PlatformSecurityConstant.TOKEN_TYPE_APP_KEY + " " + appKey,
                                params, 0, 1)
                )
                .map(ResponseEntity::getBody)
                .flatMap(set -> set.stream().findFirst())
                .map(TmaChannelProductStatusDTO::getTmaChannelProductStatus)
                .orElse(null);
    }

    @Override
    public void updateGpAndGpv(UpdateTmaChannelProductGpGpvRequestDTO updateTmaChannelProductGpGpvRequestDTO) {
        ProductDTO productDTO = updateTmaChannelProductGpGpvRequestDTO.getProductDTO();
        Long traderId = updateTmaChannelProductGpGpvRequestDTO.getTraderId();
        Long channelId = updateTmaChannelProductGpGpvRequestDTO.getChannelId();
        String channelCode = updateTmaChannelProductGpGpvRequestDTO.getChannelCode();
        String channelName = updateTmaChannelProductGpGpvRequestDTO.getChannelName();
        Long marketId = updateTmaChannelProductGpGpvRequestDTO.getMarketId();
        String marketCode = updateTmaChannelProductGpGpvRequestDTO.getMarketCode();
        String marketName = updateTmaChannelProductGpGpvRequestDTO.getMarketName();
        Long oldGpvId;

        Criteria skusCriteria = Criteria.where("productId").is(productDTO.getId())
                .and("traderId").is(traderId).and("channelId").is(channelId).and("marketId").is(marketId);
        Query skusCountQuery = new SimpleQuery(skusCriteria);
        long skuCount = solrTemplate.count("tma_channel_product_sku", skusCountQuery);

        Query skuQuery = new SimpleQuery(skusCriteria);
        skuQuery.setRows((int) skuCount);
        List<TMAChannelProductSKU> fetchedSkus = solrTemplate.query("tma_channel_product_sku", skuQuery, TMAChannelProductSKU.class)
                .getContent();

        oldGpvId = fetchedSkus.stream().map(TMAChannelProductSKU::getGenericProductVarietyId).filter(Objects::nonNull).findFirst().orElse(null);
        GenericProductVarietyEventDTO genericProductVarietyEventDTO = genericProductVarietyClient.
                getCustomForGenericProductVariety(productDTO.getVarietyId(), "Appkey "+appKey).getBody();
        Set<TMAChannelProductSKU> tmaChannelProductSKUSet = tmaChannelProductSKUEventMapper.mapForGpAndGpv(productDTO,genericProductVarietyEventDTO,
                channelId,marketId);

        Set<String> mergedVarietyTags = new HashSet<>();
        if (!fetchedSkus.isEmpty() && !tmaChannelProductSKUSet.isEmpty()) {
            fetchedSkus.forEach(sku -> tmaChannelProductSKUSet.forEach(skuToBeUpdated -> {
                if (sku.getId().equals(skuToBeUpdated.getId())) {
                    Set<String> oldVarietyTags = getGenericProductVarietyTagCodes(sku);

                    sku.updateBasicDetails(skuToBeUpdated);
                    sku.updateTagDetails(skuToBeUpdated);
                    sku.setSkuTmaChannelProductStatus(
                            getPublishTmaChannelProductSkuStatus(sku.getDefaultSKUTmaChannelProductId())
                    );

                    Set<String> newVarietyTags = getGenericProductVarietyTagCodes(skuToBeUpdated);

                    mergedVarietyTags.addAll(oldVarietyTags);
                    mergedVarietyTags.addAll(newVarietyTags);

                }
            }));
        }


        solrTemplate.saveBeans("tma_channel_product_sku", fetchedSkus);
        solrTemplate.commit("tma_channel_product_sku");

        tmaChannelProductService.updateGpAndGpv(genericProductVarietyEventDTO,productDTO,channelId,marketId);
        genericProductVarietyService.createOrUpdate(genericProductVarietyEventDTO, channelId, channelCode, channelName, marketId, marketCode, marketName);

        //refresh new gpv
        String paramFormat = "CHANNLE_ID=%d--VARIETY_ID=%d";
        String params = String.format(paramFormat,channelId, productDTO.getVarietyId());
        Set<GPVSummaryDTO> gpvSummaryDTOs = commonsReportClient.getGpvProductSummary(PlatformSecurityConstant.TOKEN_TYPE_APP_KEY+ " "+appKey,params,0,1).getBody();

        if (gpvSummaryDTOs != null){
            GPVSummaryDTO gpvSummaryDTO = gpvSummaryDTOs.stream().findFirst().orElse(null);
            Criteria gpvCriteria = Criteria.where("genericProductVarietyId").is(productDTO.getVarietyId())
                    .and("channelId").is(channelId).and("marketId").is(marketId);
            Query gpvQuery = new SimpleQuery(gpvCriteria);

            GenericProductVariety gpv = solrTemplate.query("generic_product_variety", gpvQuery, GenericProductVariety.class)
                    .getContent().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("GenericProductVariety not found for given criteria."));

            if(gpvSummaryDTO!=null){
                gpv.updateSummary(gpvSummaryDTO);
            }
            else{
                gpv.setTraderIds(null);
                gpv.setTraderCount(0L);
                gpv.setProductNames(null);
            }
            solrTemplate.saveBean("generic_product_variety", gpv);
            solrTemplate.commit("generic_product_variety");
        }

        //refresh old gpv
        String oldGpvParamFormat = "CHANNLE_ID=%d--VARIETY_ID=%d";
        String oldGpvParams = String.format(oldGpvParamFormat,channelId, oldGpvId);
        Set<GPVSummaryDTO> oldGpvSummaryDTOs = commonsReportClient.getGpvProductSummary(PlatformSecurityConstant.TOKEN_TYPE_APP_KEY+ " "+appKey,oldGpvParams,0,1).getBody();

        if (oldGpvSummaryDTOs != null){
            GPVSummaryDTO gpvSummaryDTO = oldGpvSummaryDTOs.stream().findFirst().orElse(null);
            Criteria gpvCriteria = Criteria.where("genericProductVarietyId").is(productDTO.getVarietyId())
                    .and("channelId").is(channelId).and("marketId").is(marketId);
            Query gpvQuery = new SimpleQuery(gpvCriteria);

            GenericProductVariety gpv = solrTemplate.query("generic_product_variety", gpvQuery, GenericProductVariety.class)
                    .getContent().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("GenericProductVariety not found for given criteria."));

            if(gpvSummaryDTO!=null){
                gpv.updateSummary(gpvSummaryDTO);
            }
            else{
                gpv.setTraderIds(null);
                gpv.setTraderCount(0L);
                gpv.setProductNames(null);
            }
            solrTemplate.saveBean("generic_product_variety", gpv);
            solrTemplate.commit("generic_product_variety");
        }

        Set<GenericProductVarietyEventDTO> gpvByCodeSet = new HashSet<>();
        if (!mergedVarietyTags.isEmpty()) {
            gpvByCodeSet.addAll(
                    Objects.requireNonNull(genericProductVarietyClient.getCustomForGenericProductVarietyV2(mergedVarietyTags, "Appkey " + appKey).getBody())
            );
        }
        Set<GenericProductVarietyEventDTO> diffSet = gpvByCodeSet.stream()
                .filter(gpv -> !Objects.equals(gpv.getGenericProductVarietyId(), productDTO.getVarietyId()))
                .filter(gpv -> !Objects.equals(gpv.getGenericProductVarietyId(), oldGpvId))
                .collect(Collectors.toSet());

        if (!diffSet.isEmpty()) {
            for (GenericProductVarietyEventDTO diffGpv : diffSet) {
                genericProductVarietyService.createOrUpdate(diffGpv, channelId, channelCode, channelName, marketId, marketCode, marketName);

                String gpvParamFormat = "CHANNLE_ID=%d--VARIETY_ID=%d";
                String gpvParams = String.format(gpvParamFormat, channelId, diffGpv.getGenericProductVarietyId());

                Set<GPVSummaryDTO> diffGpvSummaryDTOs =
                        commonsReportClient.getGpvProductSummary(
                                PlatformSecurityConstant.TOKEN_TYPE_APP_KEY + " " + appKey,
                                gpvParams, 0, 1
                        ).getBody();

                if (diffGpvSummaryDTOs != null) {
                    GPVSummaryDTO diffGpvSummaryDTO = diffGpvSummaryDTOs.stream().findFirst().orElse(null);

                    Criteria gpvCriteria = Criteria.where("genericProductVarietyId").is(diffGpv.getGenericProductVarietyId())
                            .and("channelId").is(channelId)
                            .and("marketId").is(marketId);

                    Query gpvQuery = new SimpleQuery(gpvCriteria);

                    GenericProductVariety gpv = solrTemplate.query("generic_product_variety", gpvQuery, GenericProductVariety.class)
                            .getContent().stream().findFirst()
                            .orElseThrow(() -> new RuntimeException("GenericProductVariety not found for given criteria."));

                    if (diffGpvSummaryDTO != null) {
                        gpv.updateSummary(diffGpvSummaryDTO);
                    } else {
                        gpv.setTraderIds(null);
                        gpv.setTraderCount(0L);
                        gpv.setProductNames(null);
                    }

                    solrTemplate.saveBean("generic_product_variety", gpv);
                    solrTemplate.commit("generic_product_variety");
                }
            }
        }


    }

    private Set<String> getGenericProductVarietyTagCodes(TMAChannelProductSKU sku) {
        if (sku.getTagCodesByType() == null) {
            return Collections.emptySet();
        }
        return new HashSet<>(
                sku.getTagCodesByType()
                        .getOrDefault("TAG_TYPE_GENERIC_PRODUCT_VARIETY", Collections.emptyList())
        );
    }


    @Override
    public Set<TMAChannelProductSKU> getTMAChannelProductSkusById(Set<Long> tmaChannelProductSkuIds) {
        Query query = new SimpleQuery();

        if(tmaChannelProductSkuIds!=null && !tmaChannelProductSkuIds.isEmpty()){
            Criteria criteria = new Criteria("skuTmaChannelProductId").in(tmaChannelProductSkuIds);
            query.addCriteria(criteria);
            Integer gpvCodeCount = tmaChannelProductSkuIds.size();
            query.setRows(gpvCodeCount);
        }

        return new HashSet<>(solrTemplate.query("tma_channel_product_sku", query, TMAChannelProductSKU.class).getContent());
    }

    @Override
    public void updateGenericProductDetails(GenericProduct newGenericProduct) {
        Criteria criteria = new Criteria("genericProductId").is(newGenericProduct.getGenericProductId()).and("marketId").is(newGenericProduct.marketId);
        Query countQuery = new SimpleQuery(criteria);
        long totalCount = solrTemplate.count("tma_channel_product_sku", countQuery);

        int pageSize = 50;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        for (int page = 0; page < totalPages; page++) {
            Query pagedQuery = new SimpleQuery(criteria);
            pagedQuery.setPageRequest(PageRequest.of(page, pageSize));

            List<TMAChannelProductSKU> tmaProductSkus = solrTemplate
                    .query("tma_channel_product_sku", pagedQuery, TMAChannelProductSKU.class)
                    .getContent();
            TMAChannelProductSKU newTmaChannelProductSku = convertGpBasicDetailsToTmaChannelProduct(newGenericProduct);
            for (TMAChannelProductSKU tmaChannelProductSKU : tmaProductSkus) {
                tmaChannelProductSKU.updateBasicDetails(newTmaChannelProductSku);
            }

            solrTemplate.saveBeans("tma_channel_product_sku", tmaProductSkus);
            solrTemplate.commit("tma_channel_product_sku");
        }
    }

    private TMAChannelProductSKU convertGpBasicDetailsToTmaChannelProduct(GenericProduct newGenericProduct) {
        return TMAChannelProductSKU.builder()
                .genericProductId(newGenericProduct.getGenericProductId())
                .genericProductCode(newGenericProduct.getGenericProductCode())
                .genericProductName(newGenericProduct.getGenericProductName())
                .categoryCodes(newGenericProduct.getCategoryCodes())
                .categoryNames(newGenericProduct.getCategoryNames())
                .subCategoryCodes(newGenericProduct.getSubCategoryCodes())
                .subCategoryNames(newGenericProduct.getSubCategoryNames())
                .build();
    }

    @Override
    public void updateGenericProductVarietyDetails(GenericProductVariety newGenericProductVariety) {
        Long gpvId = newGenericProductVariety.getGenericProductVarietyId();
        Long marketId = newGenericProductVariety.getMarketId();
        String gpvCode = newGenericProductVariety.getGenericProductVarietyCode();

        // Build criteria to find SKUs with this GPV as primary or as tag
        String orPart = "genericProductVarietyId:" + gpvId
                + " OR tagCodes:TAG." + gpvCode
                + " OR TAG_CODE_BY_TYPE_TAG_TYPE_GENERIC_PRODUCT_VARIETY:TAG." + gpvCode;

        Criteria criteria = new Criteria("marketId").is(marketId)
                .and(new Criteria().expression("(" + orPart + ")"));

        // Process in batches
        Query query = new SimpleQuery(criteria);
        int pageSize = 20;
        int page = 0;
        Page<TMAChannelProductSKU> resultPage;

        do {
            query.setPageRequest(PageRequest.of(page, pageSize));
            resultPage = solrTemplate.query("tma_channel_product_sku", query, TMAChannelProductSKU.class);

            List<TMAChannelProductSKU> skus = resultPage.getContent();
            if (!skus.isEmpty()) {
                updateSKUsBatchWithFeign(skus, newGenericProductVariety, marketId, newGenericProductVariety.getChannelId());
            }
            page++;

        } while (!resultPage.isLast());
    }

    private void updateSKUsBatchWithFeign(List<TMAChannelProductSKU> skus, GenericProductVariety newGenericProductVariety, Long marketId, Long channelId) {
        Set<Long> skuIds = skus.stream()
                .map(TMAChannelProductSKU::getProductSKUId).collect(Collectors.toSet());

        // Get fresh SKU data from product service
        ResponseEntity<Map<Long, ProductSKUDTO>> response = classificationClientV2.getProductSKUsWithAppKey("Appkey " + appKey, skuIds);
        Map<Long, ProductSKUDTO> feignSKUData = response.getBody();

        if (feignSKUData != null) {
            for (TMAChannelProductSKU sku : skus) {
                Long skuId = sku.getProductSKUId();
                ProductSKUDTO feignSKU = feignSKUData.get(skuId);

                if (feignSKU != null) {
                    // Case 1: SKU has this GPV as primary GPV - update basic details
                    if (Objects.equals(sku.getGenericProductVarietyId(), newGenericProductVariety.getGenericProductVarietyId())) {
                        TMAChannelProductSKU newSku = convertGpvBasicDetailsToTmaChannelProduct(newGenericProductVariety);
                        sku.updateBasicDetails(newSku);
                    }

                    // Case 2: Update all tag fields from fresh data
                    TMAChannelProductSKU feignTagData = tmaChannelProductSKUEventMapper.mapTagsOnly(feignSKU, marketId, channelId);
                    sku.updateTagDetails(feignTagData);
                }
            }

            solrTemplate.saveBeans("tma_channel_product_sku", skus);
            solrTemplate.commit("tma_channel_product_sku");
        }
    }

    private TMAChannelProductSKU convertGpvBasicDetailsToTmaChannelProduct(GenericProductVariety newGenericProductVariety) {
        return TMAChannelProductSKU.builder()
                .genericProductId(newGenericProductVariety.getGenericProductId())
                .genericProductCode(newGenericProductVariety.getGenericProductCode())
                .genericProductName(newGenericProductVariety.getGenericProductName())
                .genericProductVarietyId(newGenericProductVariety.getGenericProductVarietyId())
                .genericProductVarietyCode(newGenericProductVariety.getGenericProductVarietyCode())
                .genericProductVarietyName(newGenericProductVariety.getGenericProductVarietyName())
                .categoryCodes(newGenericProductVariety.getCategoryCodes())
                .categoryNames(newGenericProductVariety.getCategoryNames())
                .subCategoryCodes(newGenericProductVariety.getSubCategoryCodes())
                .subCategoryNames(newGenericProductVariety.getSubCategoryNames())
                .build();
    }


    private String buildQueryForTMAChannelProductSKUSearch(String searchTerm){
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "productName:" + searchTerm +
                    " OR genericProductVarietyName:" + searchTerm +
                    " OR genericProductName:" + searchTerm +
                    " OR productSKUName:" + searchTerm +
                    " OR traderName:" + searchTerm +
                    " OR tagLabels:" + searchTerm +
                    " OR TAG_LABEL_BY_TYPE_TAG_TYPE_GENERIC_PRODUCT_VARIETY:" + searchTerm +
                    " OR TAG_LABEL_BY_TYPE_TAG_TYPE_GENERIC_PRODUCT:" + searchTerm +
                    " OR TAG_LABEL_ENG:" + searchTerm ;
        }
        else {
            return "*:*";
        }
    }

    private FilterQuery searchProductSkusByTagCode(String tagType, Set<String> tagCodes) {
        if (tagType != null && !tagType.isEmpty() && tagCodes != null && !tagCodes.isEmpty()) {
            // Case 1: TagType + TagCodes → filter only in tagCodesByType
            String safeType = SolrUtil.solrSafeKey(tagType);

            Criteria criteria = new Criteria("TAG_CODE_BY_TYPE_" + safeType)
                    .in(tagCodes.stream()
                            .map(SolrUtil::escapeQueryChars)
                            .collect(Collectors.toList()));

            return new SimpleFilterQuery(criteria);

        } else if (tagCodes != null && !tagCodes.isEmpty()) {
            // Case 2: Only TagCodes → search in both tagCodes and tagCodesByType
            Criteria tagCodesCriteria = new Criteria("tagCodes")
                    .in(tagCodes.stream()
                            .map(SolrUtil::escapeQueryChars)
                            .collect(Collectors.toList()));

            return new SimpleFilterQuery(tagCodesCriteria);
        }
        return null;
    }

    @Override
    public void reindex() {
        int page = 0;
        int pageSize = 200;
        Page<TMAChannelProductSKU> resultPage;
        do{
            Pageable pageable = PageRequest.of(page, pageSize);
            resultPage = repository.findAll((pageable));
            List<TMAChannelProductSKU> dataBatch = resultPage.getContent();
            solrTemplate.saveBeans(ServiceConstant.TMA_CHANNEL_PRODUCT_SKU_CORE, dataBatch);
            solrTemplate.commit(ServiceConstant.TMA_CHANNEL_PRODUCT_SKU_CORE);
            page++;
        }while (!resultPage.isLast());
    }
    @Override
    public List<TMAChannelProductSKU> fetchBySkuIdsAndChannelMarket(Set<String> ids, Long marketId, Long channelId) {

        Criteria criteria = Criteria.where("id").in(ids)
                .and("marketId").is(marketId)
                .and("channelId").is(channelId);

        Query solrQuery = new SimpleQuery(criteria);
        solrQuery.setRows(ids.size());

        return solrTemplate.query("tma_channel_product_sku", solrQuery, TMAChannelProductSKU.class)
                .getContent();
    }

    @Override
    public void saveAllByBeans(List<TMAChannelProductSKU> toUpdate) {
        solrTemplate.saveBeans("tma_channel_product_sku", toUpdate);
        solrTemplate.commit("tma_channel_product_sku");
    }

    @Override
    public Set<TMAChannelProductSKU> getTMAChannelProductSkusByProductSkuIds(Set<Long> productSKUIds, Long marketId, Long channelId) {
        Query query = new SimpleQuery();
            Criteria criteria = new Criteria("productSKUId").in(productSKUIds);
                criteria = criteria.and("marketId").is(marketId);
                criteria = criteria.and("channelId").is(channelId);
            query.addCriteria(criteria);
            query.setRows(productSKUIds.size());
        return new HashSet<>(
                solrTemplate
                        .query("tma_channel_product_sku", query, TMAChannelProductSKU.class)
                        .getContent()
        );
    }
}
