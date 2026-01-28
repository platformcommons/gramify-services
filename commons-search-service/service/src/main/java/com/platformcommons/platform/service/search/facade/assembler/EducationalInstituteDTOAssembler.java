package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.CompanyMasterData;
import com.platformcommons.platform.service.search.domain.EducationalInstitute;
import com.platformcommons.platform.service.search.dto.CompanyMasterDataDTO;
import com.platformcommons.platform.service.search.dto.EducationalInstituteDTO;
import org.springframework.stereotype.Component;

@Component
public class EducationalInstituteDTOAssembler {

    public EducationalInstituteDTO toDTO(EducationalInstitute institute){
        return EducationalInstituteDTO.builder()
                .id(institute.getId())
                .name(institute.getName())
                .alias(institute.getAlias())
                .city(institute.getCity())
                .state(institute.getState())
                .type(institute.getType()).build();
    }

    public EducationalInstitute fromDTO(EducationalInstituteDTO instituteDTO){
        return EducationalInstitute.builder()
                .id(instituteDTO.getId())
                .name(instituteDTO.getName())
                .alias(instituteDTO.getAlias())
                .city(instituteDTO.getCity())
                .state(instituteDTO.getState())
                .type(instituteDTO.getType()).build();
    }
}
