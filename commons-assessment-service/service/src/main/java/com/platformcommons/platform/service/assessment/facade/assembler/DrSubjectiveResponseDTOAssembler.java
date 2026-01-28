package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.DrSubjectiveResponse;
import com.platformcommons.platform.service.assessment.dto.DrSubjectiveResponseDTO;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DrSubjectiveResponseDTOAssembler {

    @Mapping(source = "createdTimestamp", target = "createdAt")
    @Mapping(source = "createdByUser", target = "createdBy")
    @Mapping(source = "tenantId", target = "tenantId")
    DrSubjectiveResponseDTO toDTO(DrSubjectiveResponse entity);
    DrSubjectiveResponse fromDTO(DrSubjectiveResponseDTO dto);
}