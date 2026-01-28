package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.EducationalInstituteV2;
import com.platformcommons.platform.service.search.dto.EducationalInstituteV2DTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface EducationalInstituteV2DTOAssembler {

    EducationalInstituteV2DTO toDTO(EducationalInstituteV2 entity);

    EducationalInstituteV2 fromDTO(EducationalInstituteV2DTO dto);
}
