package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.GenericProductVarietyService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.repo.GenericProductVarietyRepository;
import com.platformcommons.platform.service.search.dto.GPVSummaryDTO;
import com.platformcommons.platform.service.search.dto.GenericProductVarietyFilterDTO;
import com.platformcommons.platform.service.search.facade.client.ClassificationClientV2;
import com.platformcommons.platform.service.search.messaging.mapper.GenericProductVarietyEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GenericProductVarietyServiceImpl implements GenericProductVarietyService {


    @Autowired
    private GenericProductVarietyRepository repository;


    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private ClassificationClientV2 classificationClientV2;

    @Autowired
    private GenericProductVarietyEventMapper genericProductVarietyEventMapper;


    @Value("${commons.platform.service.search-client.api-key:YXBwS2V5OmRlYzAxODgzLTZjNDItNDMxOC1hYjk1LWRmZjJkMzEwNDFiMSxhcHBDb2RlOkNPTU1PTlMuU0VSQUNIX0NMSUVOVA==}")
    private String appKey;

    @Override
    public GenericProductVariety save(GenericProductVariety genericProductVariety) {
        genericProductVariety.init();
        return repository.save(genericProductVariety);
    }

    @Override
    public GenericProductVariety update(GenericProductVariety genericProductVariety) {
        return repository.save(genericProductVariety);
    }

    @Override
    public GenericProductVariety getById(Long id) {

        return  null;
//        return repository.findById(id).orElseThrow(()-> new
//                NotFoundException(String.format("GenericProduct with id %d not found",id)));
    }

    @Override
    public Optional<GenericProductVariety> getByIdOptional(String id) {
        return repository.findById(id);
    }

    @Override
    public FacetPage<GenericProductVariety> filterSearch(String searchTerm, Set<String> facetFields,
                                                         Integer page, Integer size, String sortBy, String direction) {
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForProductSKUSearch(searchTerm)))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(facetFields)
                        .setFacetMinCount(1));
        facetQuery.setPageRequest(PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction)));
        FilterQuery filterQuery = new SimpleFilterQuery();
        facetQuery.addFilterQuery(filterQuery);
        return solrTemplate.queryForFacetPage("generic_product_variety",facetQuery, GenericProductVariety.class);
    }

    @Override
    public FacetPage<GenericProductVariety> getFacetWithFilters(GenericProductVarietyFilterDTO filterDTO) {
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForProductSKUSearch(filterDTO.getSearchTerm())))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(filterDTO.getFields())
                        .setFacetMinCount(1).setFacetLimit(-1));
        facetQuery.setPageRequest(PageRequest.of(filterDTO.getPage(), filterDTO.getSize(),
                JPAUtility.convertToSort(filterDTO.getSortBy(),filterDTO.getDirection())));

        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(filterDTO.getMarketCode())
                .and("channelCode").is(filterDTO.getChannelCode());
        filterQuery.addCriteria(marketAndChannelCriteria);

        if(filterDTO.getPriceStartRange()!=null && filterDTO.getPriceEndRange()!=null) {
            Criteria criteria = Criteria.where("minPrice").greaterThanEqual(filterDTO.getPriceStartRange())
                    .and("maxPrice").lessThanEqual(filterDTO.getPriceEndRange());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getQuantityStartRange()!=null && filterDTO.getQuantityEndRange()!=null) {
            Criteria criteria = Criteria.where("minAvailableQuantity").greaterThanEqual(filterDTO.getQuantityStartRange())
                    .and("maxAvailableQuantity").lessThanEqual(filterDTO.getQuantityEndRange());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getMaxSellerCount()!=null ){
            Criteria criteria = new Criteria("traderCount").greaterThanEqual(filterDTO.getMaxSellerCount());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCategories()!=null && !filterDTO.getCategories().isEmpty()){
            Criteria criteria = new Criteria("categoryCodes").in(filterDTO.getCategories());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getSubCategories()!=null && !filterDTO.getSubCategories().isEmpty()){
            Criteria criteria = new Criteria("subCategoryCodes").in(filterDTO.getSubCategories());
            filterQuery.addCriteria(criteria);
        }

        if(filterDTO.getGenericProductVarietyId()!=null && !filterDTO.getGenericProductVarietyId().isEmpty()){
            Criteria criteria = new Criteria("genericProductVarietyId").in(filterDTO.getGenericProductVarietyId());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getGenericProductId()!=null && !filterDTO.getGenericProductId().isEmpty()){
            Criteria criteria = new Criteria("genericProductId").is(filterDTO.getGenericProductId());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getExcludeGenericProductVarietyId()!=null){
            Criteria criteria = new Criteria("genericProductVarietyId").is(filterDTO.getExcludeGenericProductVarietyId()).not();
            filterQuery.addCriteria(criteria);
        }

        Criteria criteria = new SimpleStringCriteria("(isActive:true OR (*:* -isActive:[* TO *]))");
        filterQuery.addCriteria(criteria);
        facetQuery.addFilterQuery(filterQuery);
        return solrTemplate.queryForFacetPage("generic_product_variety",facetQuery, GenericProductVariety.class);
    }

    @Override
    public GenericProductVariety getByGenericProductVarietyId(Long genericProductVarietyId, String channelCode, String marketCode) {
        return repository.findByGenericProductVarietyIdAndChannelCodeAndMarketCode(genericProductVarietyId,channelCode,marketCode).stream()
                .findFirst().orElseThrow(()->  new NotFoundException("GenericProductVariety Not found"));
    }

    @Override
    public FacetPage<GenericProductVariety> getGenericProducts(String marketCode,String channelCode,String categoryCode,String subCategoryCode,String searchTerm) {
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForGenericProductSearch(searchTerm)));
        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(marketCode)
                .and("channelCode").is(channelCode);
        filterQuery.addCriteria(marketAndChannelCriteria);
        if(categoryCode!=null){
            Criteria criteria = new Criteria("categoryCodes").in(categoryCode);
            filterQuery.addCriteria(criteria);
        }
        if(subCategoryCode!=null){
            Criteria criteria = new Criteria("subCategoryCodes").in(subCategoryCode);
            filterQuery.addCriteria(criteria);
        }
        facetQuery.addFilterQuery(filterQuery);
        facetQuery.setFacetOptions(new FacetOptions().addFacetOnPivot("genericProductId","genericProductName_str"));

        return solrTemplate.queryForFacetPage("generic_product_variety",facetQuery, GenericProductVariety.class);
    }

    @Override
    public FacetPage<GenericProductVariety> getFacetWithFiltersV2(GenericProductVarietyFilterDTO filterDTO,Set<String> classificationCodes,String sourceTraderType) {

        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForProductSKUSearch(filterDTO.getSearchTerm())))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(filterDTO.getFields())
                        .setFacetMinCount(1).setFacetLimit(-1));
        facetQuery.setPageRequest(PageRequest.of(filterDTO.getPage(), filterDTO.getSize(),
                JPAUtility.convertToSort(filterDTO.getSortBy(),filterDTO.getDirection())));

        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(filterDTO.getMarketCode())
                .and("channelCode").is(filterDTO.getChannelCode());
        filterQuery.addCriteria(marketAndChannelCriteria);

        if(filterDTO.getPriceStartRange()!=null && filterDTO.getPriceEndRange()!=null) {
            Criteria criteria = Criteria.where("minPrice").greaterThanEqual(filterDTO.getPriceStartRange())
                    .and("maxPrice").lessThanEqual(filterDTO.getPriceEndRange());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getQuantityStartRange()!=null && filterDTO.getQuantityEndRange()!=null) {
            Criteria criteria = Criteria.where("minAvailableQuantity").greaterThanEqual(filterDTO.getQuantityStartRange())
                    .and("maxAvailableQuantity").lessThanEqual(filterDTO.getQuantityEndRange());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getMaxSellerCount()!=null ){
            Criteria criteria = new Criteria("traderCount").greaterThanEqual(filterDTO.getMaxSellerCount());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getCategories()!=null && !filterDTO.getCategories().isEmpty()){
            Criteria criteria = new Criteria("categoryCodes").in(filterDTO.getCategories());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getSubCategories()!=null && !filterDTO.getSubCategories().isEmpty()){
            Criteria criteria = new Criteria("subCategoryCodes").in(filterDTO.getSubCategories());
            filterQuery.addCriteria(criteria);
        }

        if(filterDTO.getGenericProductVarietyId()!=null){
            Criteria criteria = new Criteria("genericProductVarietyId").in(filterDTO.getGenericProductVarietyId());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getGenericProductId()!=null && !filterDTO.getGenericProductId().isEmpty()){
            Criteria criteria = new Criteria("genericProductId").is(filterDTO.getGenericProductId());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getExcludeGenericProductVarietyId()!=null){
            Criteria criteria = new Criteria("genericProductVarietyId").is(filterDTO.getExcludeGenericProductVarietyId()).not();
            filterQuery.addCriteria(criteria);
        }
        if(classificationCodes!=null && !classificationCodes.isEmpty()){   //for trader interest classification
            Criteria criteria = new Criteria("categoryCodes").in(classificationCodes);
            filterQuery.addCriteria(criteria);
        }
        assert classificationCodes != null;
        if(classificationCodes.isEmpty() && sourceTraderType!=null){
            Criteria criteria = new Criteria("categoryCodes").is("?");
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getSolutionId()!=null){
            Criteria criteria = new Criteria("solutionIds").is(filterDTO.getSolutionId());
            filterQuery.addCriteria(criteria);
        }
        if(filterDTO.getExcludeCategoryCodes()!=null && !filterDTO.getExcludeCategoryCodes().isEmpty()){
            Criteria criteria = new Criteria("categoryCodes").in(filterDTO.getExcludeCategoryCodes()).not();
            filterQuery.addCriteria(criteria);
        }

        Criteria criteria = new SimpleStringCriteria("(isActive:true OR (*:* -isActive:[* TO *]))");
        filterQuery.addCriteria(criteria);

        facetQuery.addFilterQuery(filterQuery);
        return solrTemplate.queryForFacetPage("generic_product_variety",facetQuery, GenericProductVariety.class);
    }

    @Override
    public FacetPage<GenericProductVariety> getGenericProductsV2(String marketCode,String channelCode,String categoryCode,String subCategoryCode,
                                                                 String searchTerm,Set<String> classificationCodes,String sourceTraderType) {
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForGenericProductSearch(searchTerm)));
        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(marketCode)
                .and("channelCode").is(channelCode);
        filterQuery.addCriteria(marketAndChannelCriteria);
        if(categoryCode!=null){
            Criteria criteria = new Criteria("categoryCodes").in(categoryCode);
            filterQuery.addCriteria(criteria);
        }
        if(subCategoryCode!=null){
            Criteria criteria = new Criteria("subCategoryCodes").in(subCategoryCode);
            filterQuery.addCriteria(criteria);
        }
        if(classificationCodes!=null && !classificationCodes.isEmpty()){  //for trader interest classification
            Criteria criteria = new Criteria("categoryCodes").in(classificationCodes);
            filterQuery.addCriteria(criteria);
        }
        assert classificationCodes != null;
        if(classificationCodes.isEmpty() && sourceTraderType!=null){
            Criteria criteria = new Criteria("categoryCodes").is("?");
            filterQuery.addCriteria(criteria);
        }

        facetQuery.addFilterQuery(filterQuery);
        facetQuery.setFacetOptions(new FacetOptions().addFacetOnPivot("genericProductId","genericProductName_str"));

        return solrTemplate.queryForFacetPage("generic_product_variety",facetQuery, GenericProductVariety.class);
    }

    @Override
    public Set<GenericProductVariety> getByMarketIdAndGenericProductVarietyId(Long marketId, Long id) {
        Query query = new SimpleQuery();
        Criteria marketAndGpvCriteria = Criteria.where("marketId").is(marketId)
                .and("genericProductVarietyId").is(id);
        query.addCriteria(marketAndGpvCriteria);
        return new HashSet<>(solrTemplate.query("generic_product_variety", query, GenericProductVariety.class).getContent());
    }

    @Override
    public Set<GenericProductVariety> getGenericProductVarieties(String marketCode, String channelCode, Set<String> genericProductVarietyCodes) {
        Query query = new SimpleQuery();
        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(marketCode)
                .and("channelCode").is(channelCode);
        query.addCriteria(marketAndChannelCriteria);

        if(genericProductVarietyCodes!=null && !genericProductVarietyCodes.isEmpty()){
            Criteria criteria = new Criteria("genericProductVarietyCode").in(genericProductVarietyCodes);
            query.addCriteria(criteria);
            Integer gpvCodeCount = genericProductVarietyCodes.size();
            query.setRows(gpvCodeCount);
        }

        return new HashSet<>(solrTemplate.query("generic_product_variety", query, GenericProductVariety.class).getContent());
    }

    @Override
    public void saveAll(Set<GenericProductVariety> genericProductVarietySet) {
        repository.saveAll(genericProductVarietySet);
    }

    @Override
    public void createOrUpdate(GenericProductVarietyEventDTO genericProductVarietyEventDTO, Long channelId, String channelCode, String channelName, Long marketId, String marketCode, String marketName) {

        Optional<GenericProductVariety> genericProductVarietyOptional = getByIdOptional(genericProductVarietyEventMapper
                .generateGenericProductVarietySearchId(genericProductVarietyEventDTO.getGenericProductVarietyId(),
                        channelId,marketId));

        genericProductVarietyEventDTO.setChannelId(channelId);
        genericProductVarietyEventDTO.setChannelCode(channelCode);
        genericProductVarietyEventDTO.setChannelName(channelName);
        genericProductVarietyEventDTO.setMarketId(marketId);
        genericProductVarietyEventDTO.setMarketCode(marketCode);
        genericProductVarietyEventDTO.setMarketName(marketName);

        GenericProductVariety genericProductVariety = genericProductVarietyEventMapper.buildGenericProductVarietyForDefaultChannel(genericProductVarietyEventDTO);
        if(genericProductVarietyOptional.isPresent()){
            GenericProductVariety fetched = genericProductVarietyOptional.get();
            fetched.updateBasicDetails(genericProductVariety);
            update(fetched);
        }
        else {
            save(genericProductVariety);
        }
    }

    @Override
    public void updateGenericProductDetails(GenericProduct newGenericProduct) {
        Criteria criteria = new Criteria("genericProductId").is(newGenericProduct.getGenericProductId()).and("marketId").is(newGenericProduct.marketId);
        Query countQuery = new SimpleQuery(criteria);
        long totalCount = solrTemplate.count("generic_product_variety", countQuery);

        int pageSize = 50;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        for (int page = 0; page < totalPages; page++) {
            Query pagedQuery = new SimpleQuery(criteria);
            pagedQuery.setPageRequest(PageRequest.of(page, pageSize));

            List<GenericProductVariety> gpvs = solrTemplate
                    .query("generic_product_variety", pagedQuery, GenericProductVariety.class)
                    .getContent();
            GenericProductVariety newGenericProductVariety = convertGpBasicDetailsToGpv(newGenericProduct);
            for (GenericProductVariety gpv : gpvs) {
                gpv.updateBasicDetails(newGenericProductVariety);
            }

            solrTemplate.saveBeans("generic_product_variety", gpvs);
            solrTemplate.commit("generic_product_variety");
        }
    }

    @Override
    public void updateSolrWithSummary(Long marketId, Long channelId, Long gpvId, GPVSummaryDTO summary) {
        Criteria gpvCriteria = Criteria.where("genericProductVarietyId").is(gpvId)
                .and("channelId").is(channelId)
                .and("marketId").is(marketId);
        Query gpvQuery = new SimpleQuery(gpvCriteria);

        GenericProductVariety gpv = solrTemplate.query("generic_product_variety", gpvQuery, GenericProductVariety.class)
                .getContent()
                .stream()
                .findFirst()
                .orElse(null);

        if(gpv != null){
            if (summary != null) {
                gpv.updateSummary(summary);
            } else {
                gpv.setTraderIds(null);
                gpv.setTraderCount(0L);
                gpv.setProductNames(null);
            }
            solrTemplate.saveBean("generic_product_variety", gpv);
            solrTemplate.commit("generic_product_variety");
        }

    }

    private GenericProductVariety convertGpBasicDetailsToGpv(GenericProduct newGenericProduct) {
        return GenericProductVariety.builder()
                .genericProductId(newGenericProduct.getGenericProductId())
                .genericProductName(newGenericProduct.getGenericProductName())
                .genericProductCode(newGenericProduct.getGenericProductCode())
                .categoryCodes(newGenericProduct.getCategoryCodes())
                .categoryNames(newGenericProduct.getCategoryNames())
                .subCategoryCodes(newGenericProduct.getSubCategoryCodes())
                .subCategoryNames(newGenericProduct.getSubCategoryNames())
                .build();
    }

    private String buildQueryForProductSKUSearch(String searchTerm) {
        if (searchTerm != null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "genericProductName:" + searchTerm +
                    " OR genericProductVarietyName:" + searchTerm +
                    " OR productNames:" + searchTerm +
                    " OR keywords:" + searchTerm;
        } else {
            return "*:*";
        }
    }

    private String buildQueryForGenericProductSearch(String searchTerm){
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "genericProductName:" + searchTerm +
                    " OR genericProductVarietyName:" + searchTerm;
        }
        else {
            return "*:*";
        }
    }

    @Override
    public void reindex() {
        int page = 0;
        int pageSize = 200;
        Page<GenericProductVariety> resultPage;
        do{
            Pageable pageable = PageRequest.of(page, pageSize);
            resultPage = repository.findAll((pageable));

            List<GenericProductVariety> dataBatch = resultPage.getContent();

            solrTemplate.saveBeans(ServiceConstant.GENERIC_PRODUCT_VARIETY_CORE, dataBatch);
            solrTemplate.commit(ServiceConstant.GENERIC_PRODUCT_VARIETY_CORE);
            page++;
        }while (!resultPage.isLast());
    }
}
