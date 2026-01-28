package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssesseSyncedDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentOptionResponseHierarchySummaryService;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentResponseHierarchySummaryService;
import com.platformcommons.platform.service.assessment.reporting.application.AssessmentResponseHierarchyTimelineSummaryService;
import com.platformcommons.platform.service.assessment.reporting.application.constant.LinkedSystem;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.application.utility.SectionQuestionUtil;
import com.platformcommons.platform.service.assessment.reporting.facade.*;
import com.platformcommons.platform.service.assessment.reporting.facade.client.AssessmentClient;
import com.platformcommons.platform.service.assessment.reporting.facade.client.CommonsAssessmentClient;
import com.platformcommons.platform.service.assessment.reporting.facade.client.DatasetClient;
import com.platformcommons.platform.service.assessment.reporting.messaging.producer.AssesseeFactSyncProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class SyncDatasetsFacadeImpl implements SyncDatasetsFacade {

    private static final Integer SIZE = 100;
    private final AssessmentClient assessmentClient;
    private final DatasetClient datasetClient;
    private final AssessmentInstanceDimFacade instanceDimFacade;
    private final QuestionDimFacade questionDimFacade;
    private final OptionsDimFacade optionsDimFacade;
    private final QuestionPaperSectionDimFacade questionPaperSectionDimFacade;
    private final SectionQuestionDimFacade sectionQuestionDimFacade;
    private final QuestionFactFacade questionFactFacade;
    private final OptionFactFacade optionFactFacade;
    private final SkillFactFacade skillFactFacade;
    private final AssesseDimFacade assesseDimFacade;
    private final AssesseSkillDimFacade assesseSkillDimFacade;
    private final AssesseResponseFactFacade assesseResponseFactFacade;
    private final LTLDFacade ltldFacade;
    private final AssesseeFactSyncProducer assesseeFactSyncProducer;
    private final AssessmentOptionResponseHierarchySummaryService assessmentOptionResponseHierarchySummaryService;
    private final AssessmentResponseHierarchySummaryService assessmentResponseHierarchySummaryService;
    private final AssessmentResponseHierarchyTimelineSummaryService assessmentResponseHierarchyTimelineSummaryService;

    private final ApplicationEventPublisher eventPublisher;

    @Value("${commons.assessment.service.appKey}")
    private String appKey;
    private final CommonsAssessmentClient commonsAssessmentClient;
    private final String COMMONS_ASSESSE_COUNT = "COMMONS_ASSESSMENT.GET_ASSESSE_COUNT";

    @Override
    public void syncAssessmentData(Long assessment) {

        AssessmentSyncContext syncContext = instanceDimFacade.getAssessmentContext(assessment);
        questionDimFacade.syncAssessmentData(syncContext);
        optionsDimFacade.syncAssessmentData(syncContext);
        instanceDimFacade.syncAssessmentData(syncContext);
        questionPaperSectionDimFacade.syncAssessmentData(syncContext);
        sectionQuestionDimFacade.syncAssessmentData(syncContext);

        AssessmentReportSyncContext reportSyncContext = instanceDimFacade.getSyncContext(syncContext.getAssessment(), false);

        questionFactFacade.syncAssessmentData(syncContext.getAssessmentInstanceDTO(), reportSyncContext, PlatformSecurityUtil.getContext());
        optionFactFacade.syncAssessmentData(syncContext.getAssessmentInstanceDTO(), reportSyncContext, PlatformSecurityUtil.getContext());
        skillFactFacade.syncAssessmentData(syncContext.getAssessmentInstanceDTO(), reportSyncContext, PlatformSecurityUtil.getContext());
    }



    @Override
    public void syncAssesseData(Long assessment, Boolean sa) {
        if (sa)  syncAssessmentData(assessment);
        AssessmentSyncContext syncContext = instanceDimFacade.getAssessmentContext(assessment);
        AssessmentReportSyncContext reportSyncContext = instanceDimFacade.getSyncContext(syncContext.getAssessment(), false);
        final String sessionId = PlatformSecurityUtil.getToken();

        assesseDimFacade.deleteDimByAssessmentId(assessment);
        assesseResponseFactFacade.deleteDimByAssessmentId(assessment);
        assessmentResponseHierarchySummaryService.resetAll(assessment);
        assessmentResponseHierarchyTimelineSummaryService.resetAll(assessment);
        assessmentOptionResponseHierarchySummaryService.resetAll(assessment);

        Long count = getAssessmentInstanceAssesseCount(assessment, PlatformSecurityUtil.getToken());
        int page = (int) Math.ceil((double) count / SIZE);

        IntStream.range(1, page + 1)
                .parallel()
                .forEach(i -> {

                    Set<AssessmentInstanceAssesseDTO> assessmentInstanceAssesseDTOS;
                    int retry = 0;
                    while(true) {
                        try {
                            assessmentInstanceAssesseDTOS = getAssesses(assessment, i - 1, SIZE, sessionId);
                            break;
                        } catch (Exception e) {
                            if(retry==4) throw e;
                            retry++;
                            log.error("Error in syncAssesseData", e);
                            log.error("Thread Name: " + Thread.currentThread().getName());
                            log.error("Page: " + i);

                        }
                    }
                    assessmentInstanceAssesseDTOS.forEach(SectionQuestionUtil::setSectionQuestion);
                    Set<Long> assesses = assessmentInstanceAssesseDTOS.stream().map(AssessmentInstanceAssesseDTO::getId).collect(Collectors.toSet());
                    assesseSkillDimFacade.deleteDimByAssesssesId(assesses);

                    log.info("Thread Name: " + Thread.currentThread().getName());
                    assessmentInstanceAssesseDTOS.forEach(assesseDTO -> {
                        assesseDimFacade.createAssessmentInstanceAssesseEvent(assesseDTO, reportSyncContext);
                        assesseResponseFactFacade.createAssessmentInstanceAssesseEvent(assesseDTO, reportSyncContext);
                        assesseSkillDimFacade.createAssessmentInstanceAssesseEvent(assesseDTO, reportSyncContext);

                        questionFactFacade.createAssessmentInstanceAssesseEvent(assesseDTO, reportSyncContext);
                        optionFactFacade.createAssessmentInstanceAssesseEvent(assesseDTO, reportSyncContext);
                        eventPublisher.publishEvent(AssesseSyncedDTO.builder().assesseeId(assesseDTO.getId()).build());
                    });

                });


    }

    private Set<AssessmentInstanceAssesseDTO> getAssesses(Long assessment, int i, Integer size, String sessionId) {
        if(EventContext.getSystemEvent().equals(LinkedSystem.LTLD)){
            return null;
        }
        else if(EventContext.getSystemEvent().equals(LinkedSystem.COMMONS)){
            return commonsAssessmentClient.getAssesses(assessment, i, size, sessionId);
        }
        else if(EventContext.getSystemEvent().equals(LinkedSystem.IGX)){
            return assessmentClient.getAssesses(assessment, i, size, sessionId);
        }
        else {
            throw new InvalidInputException("Invalid linked system");
        }
    }

    private Long getAssessmentInstanceAssesseCount(Long assessment, String token) {
        if(EventContext.getSystemEvent().equals(LinkedSystem.LTLD)){
            throw new InvalidInputException("Invalid linked system");
        }
        else if(EventContext.getSystemEvent().equals(LinkedSystem.COMMONS)){
            return Long.parseLong(datasetClient.executeQueryV3(COMMONS_ASSESSE_COUNT,String.format("ASSESSMENT_ID=%s",assessment.toString()), 0,0,PlatformSecurityUtil.getToken()).get(0).get("count").toString());
        }
        else if(EventContext.getSystemEvent().equals(LinkedSystem.IGX)){
            return assessmentClient.getAssessmentInstanceAssesseCount(assessment, token);
        }
        else {
            throw new InvalidInputException("Invalid linked system");
        }

    }

}
