package com.platformcommons.platform.service.search.domain.repo;

import java.util.List;

import com.platformcommons.platform.service.search.domain.TenantUser;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface TenantUserRepository extends SolrCrudRepository<TenantUser, Long> {

	@Query("completeName:?0 OR email:?0")
	List<TenantUser> findByCustomQuery(String searchTerm);

	List<TenantUser> findByMobile(Long mobile);

	List<TenantUser> findByCompleteNameAndEmailAndMobile(String name,String email,Long mobile);
}
