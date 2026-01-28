package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.DrObjectiveResponse;
import com.platformcommons.platform.service.assessment.dto.DrObjectiveResponseDTO;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {
        OptionsDTOAssembler.class
    })
public interface DrObjectiveResponseDTOAssembler {

    @Mapping(source = "createdTimestamp", target = "createdAt")
    @Mapping(source = "createdByUser", target = "createdBy")
    @Mapping(source = "tenantId", target = "tenantId")
    DrObjectiveResponseDTO toDTO(DrObjectiveResponse entity);

    DrObjectiveResponse fromDTO(DrObjectiveResponseDTO dto);
}