package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.assessment.reporting.application.AssesseHierarchyResolutionConfigurationService;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseHierarchyResolutionConfiguration;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssesseHierarchyResolutionConfigurationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AssesseHierarchyResolutionConfigurationServiceImpl implements AssesseHierarchyResolutionConfigurationService {

    private final AssesseHierarchyResolutionConfigurationRepository repository;

    @Override
    public AssesseHierarchyResolutionConfiguration createConfiguration(AssesseHierarchyResolutionConfiguration configuration) {
        log.debug("Request to save AssesseHierarchyResolutionConfiguration : {}", configuration);
        configuration.getHierarchyResolvers()
                .forEach(resolver -> resolver.setConfiguration(configuration));
        configuration.init();
        return repository.save(configuration);
    }

    @Override
    public AssesseHierarchyResolutionConfiguration updateConfiguration(Long id, AssesseHierarchyResolutionConfiguration updatedConfig) {
        log.debug("Request to update AssesseHierarchyResolutionConfiguration : {}", updatedConfig);
        AssesseHierarchyResolutionConfiguration existingConfig = repository.findById(id).orElseThrow(() -> new NotFoundException("AssesseHierarchyResolutionConfiguration not found"));

        existingConfig.patch(updatedConfig);
        return repository.save(existingConfig);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<AssesseHierarchyResolutionConfiguration> findOne(Long id) {
        log.debug("Request to get AssesseHierarchyResolutionConfiguration : {}", id);
        return repository.findById(id);
    }


}