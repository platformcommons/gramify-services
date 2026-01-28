package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.UseCase;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface UseCaseRepository extends SolrCrudRepository<UseCase,Long> {
}
