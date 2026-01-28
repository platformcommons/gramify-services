package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.EducationalInstitute;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;

public interface EducationalInstituteRepository extends SolrCrudRepository<EducationalInstitute, Long> {

	@Query("name:?0* OR alias:?0*")
	public List<EducationalInstitute> findByCustomQuery(String searchTerm);
}
