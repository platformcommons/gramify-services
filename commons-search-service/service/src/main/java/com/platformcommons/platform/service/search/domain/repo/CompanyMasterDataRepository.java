package com.platformcommons.platform.service.search.domain.repo;

import java.util.List;

import com.platformcommons.platform.service.search.domain.CompanyMasterData;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface CompanyMasterDataRepository extends SolrCrudRepository<CompanyMasterData, Long> {

	List<CompanyMasterData> findByName(String name);

	@Query("name:?0* OR alias:?0*")
	public List<CompanyMasterData> findByCustomQuery(String searchTerm);
}
