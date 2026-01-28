package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.reporting.domain.AssesseHierarchyResolutionConfiguration;
import java.util.List;
import java.util.Optional;

public interface AssesseHierarchyResolutionConfigurationService {

    AssesseHierarchyResolutionConfiguration createConfiguration(AssesseHierarchyResolutionConfiguration configuration);

    AssesseHierarchyResolutionConfiguration updateConfiguration(Long id, AssesseHierarchyResolutionConfiguration configuration);


    Optional<AssesseHierarchyResolutionConfiguration> findOne(Long id);

}