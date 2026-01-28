package com.platformcommons.platform.service.assessment.reporting.facade.impl;


import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.OptionFactService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.OptionFactFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptionFactFacadeImpl implements OptionFactFacade {

    private final OptionFactService optionFactService;
    @Override
    @Async
    public void assessmentInstanceEvent(AssessmentInstanceDTO dto, AssessmentReportSyncContext context, PlatformToken platformToken) {
        SecurityContextHolder.getContext().setAuthentication(platformToken);
        optionFactService.assessmentInstanceEvent(dto,context);
    }

    @Override
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        optionFactService.createAssessmentInstanceAssesseEvent(assesseDTO,context);
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, AssessmentReportSyncContext syncContext) {
        optionFactService.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO, syncContext);
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        optionFactService.assessmentInstanceDeleteEvent(id);
    }

    @Override
    public void syncAssessmentData(AssessmentInstanceDTO assessmentInstanceDTO, AssessmentReportSyncContext reportSyncContext, PlatformToken context) {
        SecurityContextHolder.getContext().setAuthentication(context);
        optionFactService.syncAssessmentData(assessmentInstanceDTO,reportSyncContext);
    }

    @Override
    public void questionUpdateEvent(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext) {
        optionFactService.questionUpdateEvent(questionDTO, syncContext);
    }


}
