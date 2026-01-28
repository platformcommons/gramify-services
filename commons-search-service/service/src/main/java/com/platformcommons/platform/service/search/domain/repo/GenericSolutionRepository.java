package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.GenericSolution;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Optional;

public interface GenericSolutionRepository extends SolrCrudRepository<GenericSolution,String> {

    @Query("titles:?0")
    Page<GenericSolution> findByTitles(String keyword, Pageable pageable);

    @Query("id:?0 AND marketId:?1")
    Optional<GenericSolution> findByIdAndMarketId(Long id,Long marketId);
}
