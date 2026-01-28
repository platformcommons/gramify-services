package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.service.search.application.service.ProductSKUService;
import com.platformcommons.platform.service.search.application.utility.JPAUtility;
import com.platformcommons.platform.service.search.application.utility.SearchTermParser;
import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.ProductSKU;
import com.platformcommons.platform.service.search.domain.repo.ProductSKURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.*;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class ProductSKUServiceImpl implements ProductSKUService {

    @Autowired
    private ProductSKURepository productSKURepository;

    @Autowired
    private SolrTemplate solrTemplate;

    @Override
    public ProductSKU saveProductSKU(ProductSKU productSKU) {
        productSKU.init();
        return productSKURepository.save(productSKU);
    }

    @Override
    public Page<ProductSKU> searchProductSKU(String keyword, Integer page, Integer size) {
        return productSKURepository.findBySearchKeyword(keyword, PageRequest.of(page,size));
    }

    @Override
    public FacetPage<ProductSKU> getProduckSKUWithFilter(String searchTerm, Set<String> fields, List<Long> tmaChannelProductIds, Integer page, Integer size, String sortBy, String direction) {
        FacetQuery facetQuery = new SimpleFacetQuery(new SimpleStringCriteria(buildQueryForProductSKUSearch(searchTerm)))
                .setFacetOptions(new FacetOptions().addFacetOnFlieldnames(fields)
                        .setFacetMinCount(1));
        facetQuery.setPageRequest(PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction)));
        FilterQuery filterQuery = new SimpleFilterQuery();
        if(tmaChannelProductIds!=null && !tmaChannelProductIds.isEmpty()) {
            Criteria criteria = new Criteria("tmaChannelProductId").in(tmaChannelProductIds);
            filterQuery.addCriteria(criteria);
        }
        facetQuery.addFilterQuery(filterQuery);

        return solrTemplate.queryForFacetPage("ProductSKU",facetQuery, ProductSKU.class);
    }

    private String buildQueryForProductSKUSearch(String searchTerm){
        if(searchTerm!=null) {
            searchTerm = SearchTermParser.parseSearchTerm(searchTerm);
            return "genericProductNames:" + searchTerm +
                    " OR genericProductVarietyNames:" + searchTerm;
        }
        else {
            return "*:*";
        }
    }
}
