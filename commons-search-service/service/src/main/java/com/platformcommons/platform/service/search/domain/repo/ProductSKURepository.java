package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.ProductSKU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface ProductSKURepository extends SolrCrudRepository<ProductSKU,Long> {

    @Query("productNames:?0* OR genericProductNames:?0* OR genericProductVarietyNames:?0*")
    public Page<ProductSKU> findBySearchKeyword(String keyword,Pageable pageable);

}
