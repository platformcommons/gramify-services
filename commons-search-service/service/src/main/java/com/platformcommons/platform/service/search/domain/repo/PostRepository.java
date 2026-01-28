package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.Post;
import org.springframework.data.solr.repository.SolrCrudRepository;


public interface PostRepository extends SolrCrudRepository<Post,Long> {

}
