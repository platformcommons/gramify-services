package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.CommonsLocation;
import com.platformcommons.platform.service.search.dto.CommonsLocationDTO;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Set;

public interface CommonsLocationRepository extends SolrCrudRepository<CommonsLocation,String> {

    @Query("addressLabel_str:?0")
    Set<CommonsLocation> findByAddressLabel(String addressLabel);
}
