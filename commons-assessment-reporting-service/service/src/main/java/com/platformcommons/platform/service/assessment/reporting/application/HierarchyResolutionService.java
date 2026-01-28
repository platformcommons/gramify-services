package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssesseDimDTO;
import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;
import com.platformcommons.platform.service.assessment.dto.AssesseSyncedDTO;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseHierarchyResolutionConfiguration;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessseHierarchyResolver;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssesseHierarchyResolutionConfigurationRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseDimDTOAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseHierarchyResolutionConfigurationDTOAssembler;
import com.platformcommons.platform.service.assessment.reporting.messaging.producer.AssesseHierarchyResolutionProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HierarchyResolutionService {

    private final AssesseDimService service;
    private final AssesseHierarchyResolutionConfigurationRepository configurationRepository;
    private final DatasetResolverService datasetResolverService;
    private final AssesseDimDTOAssembler assesseDimDTOAssembler;
    private final AssesseHierarchyResolutionProducer assesseHierarchyResolutionProducer;
    private final AssesseHierarchyResolutionConfigurationDTOAssembler assesseHierarchyResolutionConfigurationDTOAssembler;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Transactional
    public void resolveAndPopulateHierarchy(AssesseSyncedDTO dto) {
        log.debug("Starting hierarchy resolution for assesseeId: {}", dto.getAssesseeId());
        Optional<AssesseDim> assesseDimByAssesseId = service.getAssesseDimByAssesseId(dto.getAssesseeId());
        if(!assesseDimByAssesseId.isPresent()){
            log.debug("Starting hierarchy resolution for assesseeId: {}", dto.getAssesseeId());
            return;
        }
        AssesseDim assesseDim = assesseDimByAssesseId.get();
        String entityType = assesseDim.getAssesseeActorType();
        log.debug("Found AssesseDim ID: {} with entityType: '{}'", assesseDim.getId(), entityType);
        Optional<AssesseHierarchyResolutionConfiguration> configOpt = configurationRepository.getAssesseHierarchyResolutionConfigurationByEntityType(entityType);
        if (!configOpt.isPresent()) {
            log.debug("No AssesseHierarchyResolutionConfiguration found for entityType: '{}'. Halting hierarchy resolution.", entityType);
            return;
        }
        configOpt.ifPresent(config -> {
            log.debug("Found hierarchy configuration for entityType: '{}' with {} resolvers.", entityType, config.getHierarchyResolvers().size());
            config.getHierarchyResolvers().stream()
                    .sorted(Comparator.comparing(AssessseHierarchyResolver::getHierarchyLevel))
                    .forEach(resolver -> {
                        log.debug("Processing resolver for level: {}", resolver.getHierarchyLevel());
                        String resolvedValue = datasetResolverService.resolveValue(resolver,assesseDim);
                        setHierarchyLevelValue(assesseDim, resolver.getHierarchyLevel(), resolvedValue);
                    });
            log.debug("Saving updated AssesseDim with ID: {}", assesseDim.getId());
            AssesseDim dim = service.save(assesseDim);
            AssesseDimDTO assesseDimDTO = assesseDimDTOAssembler.toDto(dim);

            applicationEventPublisher.publishEvent(AssesseDimHierarchyResolvedEventDTO.builder()
                    .assesseDim(assesseDimDTO)
                    .assesseHierarchyResolutionConfiguration(assesseHierarchyResolutionConfigurationDTOAssembler.toDto(config))
                    .build());
        });
        ;

    }

    private void setHierarchyLevelValue(AssesseDim assesse, Long level, String value) {
        if (level == null) return;
        log.debug("Setting hierarchy level {} to value '{}' for assesseeId: {}", level, value, assesse.getAssesseeActorId());
        switch (level.intValue()) {
            case 1:
                assesse.setL1(value);
                break;
            case 2:
                assesse.setL2(value);
                break;
            case 3:
                assesse.setL3(value);
                break;
            case 4:
                assesse.setL4(value);
                break;
            case 5:
                assesse.setL5(value);
                break;
            default:
                break;
        }
    }
}