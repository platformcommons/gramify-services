package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssesseDimDTO;
import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;
import com.platformcommons.platform.service.assessment.reporting.application.HierarchySyncService;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseHierarchyResolutionConfiguration;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssesseDimRepository;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssesseHierarchyResolutionConfigurationRepository;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssessmentResponseHierarchySummaryRepository;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssessmentResponseHierarchyTimelineSummaryRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseDimDTOAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseHierarchyResolutionConfigurationDTOAssembler;
import com.platformcommons.platform.service.assessment.reporting.messaging.producer.AssesseHierarchyResolutionProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HierarchySyncServiceImpl implements HierarchySyncService {

    private static final int PAGE_SIZE = 500;
    private final AssessmentResponseHierarchySummaryRepository summaryRepository;
    private final AssessmentResponseHierarchyTimelineSummaryRepository timelineSummaryRepository;
    private final AssesseDimRepository assesseDimRepository;
    private final AssesseHierarchyResolutionConfigurationRepository configurationRepository;
    private final AssesseHierarchyResolutionProducer producer;
    private final AssesseDimDTOAssembler assesseDimDTOAssembler;
    private final AssesseHierarchyResolutionConfigurationDTOAssembler configDTOAssembler;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Async
    @Transactional
    public void resyncSummariesForAssessment(Long assessmentId) {
        log.debug("Starting hierarchy summary resync for assessmentId: {}", assessmentId);

        log.debug("Deleting existing summary records for assessmentId: {}", assessmentId);
        summaryRepository.deleteByAssessmentId(assessmentId);
        timelineSummaryRepository.deleteByAssessmentId(assessmentId);

        log.debug("Deleted existing summary records for assessmentId: {}", assessmentId);

        int pageNumber = 0;
        Page<AssesseDim> currentPage;

        do {
            Pageable pageable = PageRequest.of(pageNumber, PAGE_SIZE);
            currentPage = assesseDimRepository.findByAssessmentId(assessmentId, pageable);

            if (pageNumber == 0 && !currentPage.hasContent()) {
                log.warn("No AssesseDim records found for assessmentId: {}. Sync process finished.", assessmentId);
                return;
            }

            log.debug("Processing page {} of {} for assessmentId: {}. Records on this page: {}",
                    pageNumber + 1, currentPage.getTotalPages(), assessmentId, currentPage.getNumberOfElements());

            for (AssesseDim assesseDim : currentPage.getContent()) {
                String entityType = assesseDim.getAssesseeActorType();
                Optional<AssesseHierarchyResolutionConfiguration> configOpt = configurationRepository.getAssesseHierarchyResolutionConfigurationByEntityType(entityType);

                if (configOpt.isPresent()) {
                    AssesseDimDTO assesseDimDTO = assesseDimDTOAssembler.toDto(assesseDim);
                    AssesseHierarchyResolutionConfiguration config = configOpt.get();

                    AssesseDimHierarchyResolvedEventDTO event = AssesseDimHierarchyResolvedEventDTO.builder()
                            .assesseDim(assesseDimDTO)
                            .assesseHierarchyResolutionConfiguration(configDTOAssembler.toDto(config))
                            .build();
                    applicationEventPublisher.publishEvent(event);
                } else {
                    log.warn("No hierarchy config for entityType: {} (AssesseDim ID: {})", entityType, assesseDim.getId());
                }
            }
            pageNumber++;
        } while (currentPage.hasNext());

        log.debug("Finished emitting all events for assessmentId: {}. Consumers will now repopulate the summary tables.", assessmentId);
    }
}