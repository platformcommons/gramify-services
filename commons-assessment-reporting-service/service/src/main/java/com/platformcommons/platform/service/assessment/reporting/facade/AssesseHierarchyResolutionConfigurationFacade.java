package com.platformcommons.platform.service.assessment.reporting.facade;


import com.platformcommons.platform.service.assessment.dto.AssesseHierarchyResolutionConfigurationDTO;

import java.util.List;
import java.util.Optional;

public interface AssesseHierarchyResolutionConfigurationFacade {

    AssesseHierarchyResolutionConfigurationDTO createConfiguration(AssesseHierarchyResolutionConfigurationDTO dto);

    AssesseHierarchyResolutionConfigurationDTO updateConfiguration(Long id, AssesseHierarchyResolutionConfigurationDTO dto);


    Optional<AssesseHierarchyResolutionConfigurationDTO> findOne(Long id);

}