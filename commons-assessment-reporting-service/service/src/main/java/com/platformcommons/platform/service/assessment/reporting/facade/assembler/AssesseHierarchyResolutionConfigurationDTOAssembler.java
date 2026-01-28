package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.AssesseHierarchyResolutionConfigurationDTO;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseHierarchyResolutionConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AssesseHierarchyResolutionConfigurationDTOAssembler {

    AssesseHierarchyResolutionConfiguration toEntity(AssesseHierarchyResolutionConfigurationDTO dto);

    AssesseHierarchyResolutionConfigurationDTO toDto(AssesseHierarchyResolutionConfiguration entity);

    List<AssesseHierarchyResolutionConfigurationDTO> toDtoList(List<AssesseHierarchyResolutionConfiguration> entities);

}