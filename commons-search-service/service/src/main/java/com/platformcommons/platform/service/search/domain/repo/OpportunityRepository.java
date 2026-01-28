package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.Opportunity;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface OpportunityRepository extends SolrCrudRepository<Opportunity,Long> {

    List<Opportunity> findByTenantAndTenantLogin(Long id, String tenantLogin);
}
