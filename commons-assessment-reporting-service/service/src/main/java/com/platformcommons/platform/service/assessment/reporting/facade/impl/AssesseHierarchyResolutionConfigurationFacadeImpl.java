package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.AssesseHierarchyResolutionConfigurationDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssesseHierarchyResolutionConfigurationService;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseHierarchyResolutionConfiguration;
import com.platformcommons.platform.service.assessment.reporting.facade.AssesseHierarchyResolutionConfigurationFacade;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseHierarchyResolutionConfigurationDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Transactional
public class AssesseHierarchyResolutionConfigurationFacadeImpl implements AssesseHierarchyResolutionConfigurationFacade {

    private final AssesseHierarchyResolutionConfigurationService service;
    private final AssesseHierarchyResolutionConfigurationDTOAssembler mapper;

    @Override
    public AssesseHierarchyResolutionConfigurationDTO createConfiguration(AssesseHierarchyResolutionConfigurationDTO dto) {
        AssesseHierarchyResolutionConfiguration entity = mapper.toEntity(dto);
        AssesseHierarchyResolutionConfiguration savedEntity = service.createConfiguration(entity);
        return mapper.toDto(savedEntity);
    }

    @Override
    public AssesseHierarchyResolutionConfigurationDTO updateConfiguration(Long id, AssesseHierarchyResolutionConfigurationDTO dto) {
        AssesseHierarchyResolutionConfiguration entityToUpdate = mapper.toEntity(dto);
        AssesseHierarchyResolutionConfiguration updatedEntity = service.updateConfiguration(id, entityToUpdate);
        return mapper.toDto(updatedEntity);
    }


    @Override
    public Optional<AssesseHierarchyResolutionConfigurationDTO> findOne(Long id) {
        return service.findOne(id).map(mapper::toDto);
    }

}