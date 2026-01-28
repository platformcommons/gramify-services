package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.service.search.application.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.service.GenericProductService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.GenericProduct;
import com.platformcommons.platform.service.search.domain.TMAChannelProduct;
import com.platformcommons.platform.service.search.domain.repo.GenericProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GenericProductServiceImpl implements GenericProductService {

    @Autowired
    GenericProductRepository repository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public List<GenericProduct> findAllGenericProducts() {
        Iterable<GenericProduct> iterable = repository.findAll();
        List<GenericProduct> list = new ArrayList<>();
        iterable.forEach(list::add);
        return list;
    }

    @Override
    public GenericProduct save(GenericProduct genericProduct) {
        return repository.save(genericProduct);
    }

    @Override
    public Set<GenericProduct> getByMarketIdAndGenericProductId(Long marketId, Long id){
        Query query = new SimpleQuery();
        Criteria marketAndGpCriteria = Criteria.where("marketId").is(marketId)
                .and("genericProductId").is(id);
        query.addCriteria(marketAndGpCriteria);
        return new HashSet<>(solrTemplate.query("generic_product", query, GenericProduct.class).getContent());
    }

    @Override
    public void saveAll(Set<GenericProduct> genericProducts) {
        repository.saveAll(genericProducts);
    }

    @Override
    public FacetPage<GenericProduct> getFacetWithFilters(String marketCode, String searchTerm, Set<String> categoryCodes, Set<String> subCategoryCodes,
                                                         Set<String> fields, Integer page, Integer size, String sortBy, String direction) {

        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForGenericProductSearch(searchTerm)))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(fields)
                        .setFacetMinCount(1).setFacetLimit(-1));
        facetQuery.setPageRequest(PageRequest.of(page, size, JPAUtility.convertToSort(sortBy,direction)));

        FilterQuery filterQuery = new SimpleFilterQuery();
        Criteria marketCriteria = Criteria.where("marketCode").is(marketCode);
        filterQuery.addCriteria(marketCriteria);

        if(categoryCodes!=null && !categoryCodes.isEmpty()){
            Criteria criteria = new Criteria("categoryCodes").in(categoryCodes);
            filterQuery.addCriteria(criteria);
        }
        if(subCategoryCodes!=null && !subCategoryCodes.isEmpty()){
            Criteria criteria = new Criteria("subCategoryCodes").in(subCategoryCodes);
            filterQuery.addCriteria(criteria);
        }

        Criteria criteria = new SimpleStringCriteria("(isActive:true OR (*:* -isActive:[* TO *]))");
        filterQuery.addCriteria(criteria);

        facetQuery.addFilterQuery(filterQuery);
        return solrTemplate.queryForFacetPage("generic_product",facetQuery, GenericProduct.class);
    }

    private String buildQueryForGenericProductSearch(String searchTerm) {
        if (searchTerm != null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "genericProductName:" + searchTerm +
                    " OR keywords:" + searchTerm;
        } else {
            return "*:*";
        }
    }

    @Override
    public void reindex() {
        int page = 0;
        int pageSize = 200;
        Page<GenericProduct> resultPage;
        do{
            Pageable pageable = PageRequest.of(page, pageSize);
            resultPage = repository.findAll((pageable));

            List<GenericProduct> dataBatch = resultPage.getContent();

            solrTemplate.saveBeans(ServiceConstant.GENERIC_PRODUCT_CORE, dataBatch);
            solrTemplate.commit(ServiceConstant.GENERIC_PRODUCT_CORE);
            page++;
        }while (!resultPage.isLast());
    }
}
