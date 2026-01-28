package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.App;
import com.platformcommons.platform.service.search.domain.CompanyMasterDataV2;
import com.platformcommons.platform.service.search.domain.EducationalInstitute;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CompanyMasterDataV2Repository extends SolrCrudRepository<CompanyMasterDataV2,String> {


    Optional<CompanyMasterDataV2> findByCode(String code);


}
