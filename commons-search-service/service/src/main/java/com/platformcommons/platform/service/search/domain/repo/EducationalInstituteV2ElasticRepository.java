package com.platformcommons.platform.service.search.domain.repo;

import com.platformcommons.platform.service.search.domain.EducationalInstituteV2;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface EducationalInstituteV2ElasticRepository extends ElasticsearchRepository<EducationalInstituteV2,String> {

    Optional<EducationalInstituteV2> findByCode(String code);

}
