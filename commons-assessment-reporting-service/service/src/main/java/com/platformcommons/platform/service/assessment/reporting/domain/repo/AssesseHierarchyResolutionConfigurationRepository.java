package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.AssesseHierarchyResolutionConfiguration;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssesseHierarchyResolutionConfigurationRepository extends BaseReportRepository<AssesseHierarchyResolutionConfiguration, Long> {
    Optional<AssesseHierarchyResolutionConfiguration> findById(Long id);


    Optional<AssesseHierarchyResolutionConfiguration> getAssesseHierarchyResolutionConfigurationByEntityType(String entityType);
}