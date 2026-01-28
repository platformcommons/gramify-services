package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.QuestionFactService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.QuestionFactFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class QuestionFactFacadeImpl implements QuestionFactFacade {

    private final QuestionFactService questionFactService;

    @Override
    @Async
    public void assessmentInstanceEvent(AssessmentInstanceDTO dto, AssessmentReportSyncContext context, PlatformToken platformToken) {
        SecurityContextHolder.getContext().setAuthentication(platformToken);
        questionFactService.assessmentInstanceEvent(dto,context);
    }

    @Override
    @Transactional
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        questionFactService.createAssessmentInstanceAssesseEvent(assesseDTO,context);
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, AssessmentReportSyncContext syncContext) {
        questionFactService.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO, syncContext);
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        questionFactService.assessmentInstanceDeleteEvent(id);
    }

    @Override
    @Async
    public void syncAssessmentData(AssessmentInstanceDTO assessmentInstanceDTO, AssessmentReportSyncContext reportSyncContext, PlatformToken context) {
        SecurityContextHolder.getContext().setAuthentication(context);
        questionFactService.syncAssessmentData(assessmentInstanceDTO,reportSyncContext);

    }

    @Override
    public void questionUpdateEvent(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext) {
        questionFactService.questionUpdateEvent(questionDTO, syncContext);
    }

}
