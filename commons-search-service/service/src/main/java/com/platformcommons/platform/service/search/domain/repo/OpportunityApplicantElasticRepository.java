package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Set;

public interface OpportunityApplicantElasticRepository extends ElasticsearchRepository<OpportunityApplicant,Long> {

    Set<OpportunityApplicant> findByMarketUserId(Long userId);
}
