package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.App;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Collection;
import java.util.Optional;

import java.util.List;
import java.util.Set;

public interface AppRepository extends SolrCrudRepository<App,Long> {

    @Facet(fields= {"domainCodes"}, minCount=1)
    @Query(value = "*")
    FacetPage<App> getFacet(Pageable pageable);

    Optional<App> findByName(String name);


    @Query("name:?0*")
    Page<App> findAppByName(String name, Pageable page);

    Set<App> findByIdIn(Collection<Long> ids);
}
