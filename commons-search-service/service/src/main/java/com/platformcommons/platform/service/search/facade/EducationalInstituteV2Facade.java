package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.search.dto.EducationalInstituteV2DTO;
import com.platformcommons.platform.service.search.dto.FacetPageDTO;

import java.util.Map;
import java.util.Set;

public interface EducationalInstituteV2Facade {
    EducationalInstituteV2DTO save(EducationalInstituteV2DTO body);

    void deleteByCode(String code);

    FacetPageDTO<EducationalInstituteV2DTO> getBySearchText(String searchText, Set<String> instituteTypeSet, Integer page, Integer size);

    Map<String, EducationalInstituteV2DTO> getByEducationInstituteCodesInBulk(Set<String> educationInstituteCodes);
}
