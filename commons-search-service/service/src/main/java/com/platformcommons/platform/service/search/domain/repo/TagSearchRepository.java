package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.TagSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface TagSearchRepository extends SolrCrudRepository<TagSearch,String> {

    @Query("labels:?0 OR name:?0" )
    Page<TagSearch> findByNameML(String keyword, Pageable pageable);
}
