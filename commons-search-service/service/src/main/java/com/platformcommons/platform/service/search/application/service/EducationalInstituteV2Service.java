package com.platformcommons.platform.service.search.application.service;

import com.platformcommons.platform.service.search.domain.EducationalInstituteV2;
import org.springframework.data.elasticsearch.core.SearchHits;

import java.util.Map;
import java.util.Set;

public interface EducationalInstituteV2Service {

    EducationalInstituteV2 save(EducationalInstituteV2 body);

    void deleteByCode(String code);

    SearchHits<EducationalInstituteV2> getBySearchText(String searchText, Set<String> instituteTypeSet, Integer page, Integer size);

    Map<String, EducationalInstituteV2> getByEducationInstituteCodesInBulk(Set<String> educationInstituteCodes);
}
