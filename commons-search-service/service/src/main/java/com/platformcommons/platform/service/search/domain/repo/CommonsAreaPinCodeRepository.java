package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.CommonsAreaPinCode;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Set;

public interface CommonsAreaPinCodeRepository extends SolrCrudRepository<CommonsAreaPinCode, Long> {
    @Query("areaCode:?0")
    public Set<CommonsAreaPinCode> findByCustomQuery(String searchTerm);
}
