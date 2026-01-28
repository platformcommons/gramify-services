package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.Domain;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface DomainRepository extends SolrCrudRepository<Domain,Long> {
}
