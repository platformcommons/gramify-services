package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.User;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.Optional;

public interface UserRepository extends SolrCrudRepository<User,Long> {

    void deleteByUserId(Long userId);

    Optional<User> findByUserId(Long userId);

}
