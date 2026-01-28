package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.GenericProduct;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface GenericProductRepository extends SolrCrudRepository<GenericProduct, Long> {

}
