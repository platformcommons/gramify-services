package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.product.dto.GenericProductVarietyEventDTO;
import com.platformcommons.platform.service.product.dto.ProductDTO;
import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.TMAChannelProductService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.*;
import com.platformcommons.platform.service.search.domain.repo.TMAChannelProductRepository;
import com.platformcommons.platform.service.search.facade.client.ClassificationClientV2;
import com.platformcommons.platform.service.search.messaging.mapper.TMAChannelProductEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TMAChannelProductServiceImpl implements TMAChannelProductService {

    @Autowired
    private TMAChannelProductRepository tmaChannelProductRepository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Autowired
    private ClassificationClientV2 classificationClientV2;

    @Autowired
    private TMAChannelProductEventMapper tmaChannelProductEventMapper;


    @Value("${commons.platform.service.search-client.api-key:YXBwS2V5OmRlYzAxODgzLTZjNDItNDMxOC1hYjk1LWRmZjJkMzEwNDFiMSxhcHBDb2RlOkNPTU1PTlMuU0VSQUNIX0NMSUVOVA==}")
    private String appKey;

    @Override
    public TMAChannelProduct save(TMAChannelProduct TMAChannelProduct) {
        TMAChannelProduct.init();
        return tmaChannelProductRepository.save(TMAChannelProduct);
    }

    @Override
    public TMAChannelProduct update(TMAChannelProduct TMAChannelProduct) {
        return tmaChannelProductRepository.save(TMAChannelProduct);
    }

    @Override
    public TMAChannelProduct getById(String id) {
        return tmaChannelProductRepository.findById(id).orElseThrow(()->
                new NotFoundException(String.format("TMAChannelProduct with  %d  not found",id)));
    }

    @Override
    public Optional<TMAChannelProduct> getByOptional(String id) {
        return tmaChannelProductRepository.findById(id);
    }

    @Override
    public void saveAll(Set<TMAChannelProduct> tmaChannelProductSet) {
        tmaChannelProductSet.forEach(TMAChannelProduct::setCreatedTimestamp);
        tmaChannelProductRepository.saveAll(tmaChannelProductSet);
    }

    @Override
    public Page<TMAChannelProduct> getByGenericProductVarietyId(String marketCode, String channelCode, Long varietyId, Set<Long> excludeVarietyIds, Long traderId, Integer page, Integer size, String sortBy, String direction) {

        Query query = new SimpleQuery();

        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(marketCode)
                .and("channelCode").is(channelCode);
        query.addCriteria(marketAndChannelCriteria);

        if(varietyId !=null ){
            Criteria criteria = new Criteria("genericProductVarietyId").is(varietyId);
            query.addCriteria(criteria);
        }
        if(traderId !=null ){
            Criteria criteria = new Criteria("traderId").is(traderId);
            query.addCriteria(criteria);
        }
        if(excludeVarietyIds!=null && !excludeVarietyIds.isEmpty()){
            Criteria criteria = new Criteria("genericProductVarietyId").is(excludeVarietyIds).not();
            query.addCriteria(criteria);
        }
        query.setPageRequest(PageRequest.of(page, size, JPAUtility.convertToSort(sortBy,direction)));

        return solrTemplate.query("tma_channel_product",query,TMAChannelProduct.class);

    }

    @Override
    public Set<TMAChannelProduct> getByGenericProductVarietyId(Long varietyId, Long marketId, Long channelId) {
        return tmaChannelProductRepository.findByGenericProductVarietyIdAndMarketIdAndChannelId(varietyId,marketId,channelId);
    }

    @Override
    public void updateTraderDetails(Long traderId, String traderDisplayName, String iconPic) {

        Criteria criteria = new Criteria("traderId").is(traderId);
        Query countQuery = new SimpleQuery(criteria);
        long totalCount = solrTemplate.count("tma_channel_product", countQuery);

        int pageSize = 50;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        for (int page = 0; page < totalPages; page++) {
            Query pagedQuery = new SimpleQuery(criteria);
            pagedQuery.setPageRequest(PageRequest.of(page, pageSize));

            List<TMAChannelProduct> products = solrTemplate
                    .query("tma_channel_product", pagedQuery, TMAChannelProduct.class)
                    .getContent();

            for (TMAChannelProduct product : products) {
                product.setLastModifiedTimestamp();
                product.setTraderName(traderDisplayName != null ? traderDisplayName:product.getTraderName());
                product.setTraderIconURL(iconPic != null ? iconPic:product.getTraderIconURL());
            }

            solrTemplate.saveBeans("tma_channel_product", products);
            solrTemplate.commit("tma_channel_product");
        }
    }

    @Override
    public void updateWithProduct(ProductDTO payload) {
        Set<TMAChannelProduct> fetchedList = tmaChannelProductRepository.findByProductId(payload.getId());
        fetchedList.forEach(entity -> {
            entity.setLastModifiedTimestamp();
           entity.setProductName(payload.getName().stream().findFirst().get().getText());
           entity.setProductCode(payload.getCode());
        });
        tmaChannelProductRepository.saveAll(fetchedList);
    }

    @Override
    public Set<TMAChannelProduct> getByVarietyIdWithOutPage(Long marketId, Long channelId, Set<Long> genericProductVarietyIds) {
        Query query = new SimpleQuery();

        Criteria marketAndChannelCriteria = Criteria.where("marketId").is(marketId)
                .and("channelId").is(channelId);
        query.addCriteria(marketAndChannelCriteria);

        Criteria criteria = new Criteria("genericProductVarietyId").in(genericProductVarietyIds);
        query.addCriteria(criteria);

        return new HashSet<>(solrTemplate.query("tma_channel_product", query, TMAChannelProduct.class).getContent());
    }

    @Override
    public Page<TMAChannelProduct> getByGenericProductVarietyIdV2(String marketCode, String channelCode, Long varietyId, Set<Long> excludeVarietyIds,
                                                                  Long traderId, Integer page, Integer size, String sortBy, String direction,
                                                                  Set<String> classificationCodes,String sourceTraderType, Long solutionId,
                                                                  Set<String> categoryCodes, Set<String> subCategoryCodes,String searchTerm,Set<Long> linkedSolutionResourceIds) {

        Query query = new SimpleQuery(new SimpleStringCriteria(buildQueryForTMAChannelProductSearch(searchTerm)));


        Criteria marketAndChannelCriteria = Criteria.where("marketCode").is(marketCode)
                .and("channelCode").is(channelCode);
        query.addCriteria(marketAndChannelCriteria);

        if(varietyId !=null ){
            Criteria criteria = new Criteria("genericProductVarietyId").is(varietyId);
            query.addCriteria(criteria);
        }
        if(traderId !=null ){
            Criteria criteria = new Criteria("traderId").is(traderId);
            query.addCriteria(criteria);
        }
        if(excludeVarietyIds!=null && !excludeVarietyIds.isEmpty()){
            Criteria criteria = new Criteria("genericProductVarietyId").is(excludeVarietyIds).not();
            query.addCriteria(criteria);
        }
        if(classificationCodes!=null && !classificationCodes.isEmpty()){ //for trader interest classification
            Criteria criteria = new Criteria("categoryCodes").in(classificationCodes);
            query.addCriteria(criteria);
        }
        assert classificationCodes != null;
        if(classificationCodes.isEmpty() && sourceTraderType!=null){
            Criteria criteria = new Criteria("categoryCodes").is("?");
            query.addCriteria(criteria);
        }
        if(solutionId !=null){
            Criteria criteria = new Criteria("approvedForSolutionIds").is(solutionId).or("unApprovedSolutionIds").is(solutionId);
            query.addCriteria(criteria);
        }
        if(categoryCodes!=null && !categoryCodes.isEmpty()){
            Criteria criteria = new Criteria("categoryCodes").in(categoryCodes);
            query.addCriteria(criteria);
        }
        if(subCategoryCodes!=null && !subCategoryCodes.isEmpty()){
            Criteria criteria = new Criteria("subCategoryCodes").in(subCategoryCodes);
            query.addCriteria(criteria);
        }
        if(linkedSolutionResourceIds!=null && !linkedSolutionResourceIds.isEmpty()){
            Criteria criteria = new Criteria("linkedSolutionResourceIds").in(linkedSolutionResourceIds);
            query.addCriteria(criteria);
        }
        query.setPageRequest(PageRequest.of(page, size, JPAUtility.convertToSort(sortBy,direction)));

        return solrTemplate.query("tma_channel_product",query,TMAChannelProduct.class);

    }

    @Override
    public Set<TMAChannelProduct> getByDefaultSkuId(Long productSKUId) {
        return tmaChannelProductRepository.findByDefaultSkuId(productSKUId);
    }

    @Override
    public void updateGpAndGpv(GenericProductVarietyEventDTO genericProductVarietyEventDTO, ProductDTO productDTO, Long channelId, Long marketId) {
        TMAChannelProduct tmaChannelProduct = tmaChannelProductEventMapper.fromProductDTO(genericProductVarietyEventDTO,productDTO,channelId,marketId);

        Criteria productCriteria = Criteria.where("productId").is(productDTO.getId())
                .and("channelId").is(channelId).and("marketId").is(marketId);
        Query productQuery = new SimpleQuery(productCriteria);
        TMAChannelProduct fetchedProduct = solrTemplate.query("tma_channel_product", productQuery, TMAChannelProduct.class)
                .getContent().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("TMAChannelProduct not found for given criteria."));

        if(fetchedProduct.getId().equalsIgnoreCase(tmaChannelProduct.getId())){
            fetchedProduct.updateBasicDetails(tmaChannelProduct);
        }

        solrTemplate.saveBean("tma_channel_product", fetchedProduct);
        solrTemplate.commit("tma_channel_product");
    }

    @Override
    public void updateGenericProductDetails(GenericProduct newGenericProduct) {
        Criteria criteria = new Criteria("genericProductId").is(newGenericProduct.getGenericProductId()).and("marketId").is(newGenericProduct.marketId);
        Query countQuery = new SimpleQuery(criteria);
        long totalCount = solrTemplate.count("tma_channel_product", countQuery);

        int pageSize = 50;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        for (int page = 0; page < totalPages; page++) {
            Query pagedQuery = new SimpleQuery(criteria);
            pagedQuery.setPageRequest(PageRequest.of(page, pageSize));

            List<TMAChannelProduct> tmaProducts = solrTemplate
                    .query("tma_channel_product", pagedQuery, TMAChannelProduct.class)
                    .getContent();
            TMAChannelProduct newTmaChannelProduct = convertGpBasicDetailsToTmaChannelProduct(newGenericProduct);
            for (TMAChannelProduct tmaChannelProduct : tmaProducts) {
                tmaChannelProduct.updateBasicDetails(newTmaChannelProduct);
            }

            solrTemplate.saveBeans("tma_channel_product", tmaProducts);
            solrTemplate.commit("tma_channel_product");
        }
    }
    private TMAChannelProduct convertGpBasicDetailsToTmaChannelProduct(GenericProduct newGenericProduct) {
        return TMAChannelProduct.builder()
                .genericProductId(newGenericProduct.getGenericProductId())
                .genericProductCode(newGenericProduct.getGenericProductCode())
                .categoryCodes(newGenericProduct.getCategoryCodes())
                .categoryNames(newGenericProduct.getCategoryNames())
                .subCategoryCodes(newGenericProduct.getSubCategoryCodes())
                .subCategoryNames(newGenericProduct.getSubCategoryNames())
                .build();
    }
    @Override
    public void updateGenericProductVarietyDetails(GenericProductVariety newGenericProductVariety) {
        Criteria criteria = new Criteria("genericProductVarietyId").is(newGenericProductVariety.getGenericProductVarietyId())
                .and("marketId").is(newGenericProductVariety.marketId);
        Query countQuery = new SimpleQuery(criteria);
        long totalCount = solrTemplate.count("tma_channel_product", countQuery);
        int pageSize = 50;
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        for (int page = 0; page < totalPages; page++) {
            Query pagedQuery = new SimpleQuery(criteria);
            pagedQuery.setPageRequest(PageRequest.of(page, pageSize));
            List<TMAChannelProduct> tmaProducts = solrTemplate
                    .query("tma_channel_product", pagedQuery, TMAChannelProduct.class)
                    .getContent();
            TMAChannelProduct newTmaChannelProduct = convertGpvBasicDetailsToTmaChannelProduct(newGenericProductVariety);
            for (TMAChannelProduct tmaChannelProduct : tmaProducts) {
                tmaChannelProduct.updateBasicDetails(newTmaChannelProduct);
            }
            solrTemplate.saveBeans("tma_channel_product", tmaProducts);
            solrTemplate.commit("tma_channel_product");
        }
    }

    private TMAChannelProduct convertGpvBasicDetailsToTmaChannelProduct(GenericProductVariety newGenericProductVariety) {
        return TMAChannelProduct.builder()
                .genericProductId(newGenericProductVariety.getGenericProductId())
                .genericProductCode(newGenericProductVariety.getGenericProductCode())
                .genericProductVarietyId(newGenericProductVariety.getGenericProductVarietyId())
                .genericProductVarietyCode(newGenericProductVariety.getGenericProductVarietyCode())
                .categoryCodes(newGenericProductVariety.getCategoryCodes())
                .categoryNames(newGenericProductVariety.getCategoryNames())
                .subCategoryCodes(newGenericProductVariety.getSubCategoryCodes())
                .subCategoryNames(newGenericProductVariety.getSubCategoryNames())
                .build();
    }

    private String buildQueryForTMAChannelProductSearch(String searchTerm){
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "productName:" + searchTerm +
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
        Page<TMAChannelProduct> resultPage;
        do{
            Pageable pageable = PageRequest.of(page, pageSize);
            resultPage = tmaChannelProductRepository.findAll((pageable));

            List<TMAChannelProduct> dataBatch = resultPage.getContent();

            solrTemplate.saveBeans(ServiceConstant.TMA_CHANNEL_PRODUCT_CORE, dataBatch);
            solrTemplate.commit(ServiceConstant.TMA_CHANNEL_PRODUCT_CORE);
            page++;
        }while (!resultPage.isLast());
    }
}
