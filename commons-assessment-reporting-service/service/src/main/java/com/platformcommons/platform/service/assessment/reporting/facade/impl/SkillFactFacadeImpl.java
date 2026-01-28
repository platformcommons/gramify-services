package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.security.token.PlatformToken;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.SkillFactService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.SkillFactFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SkillFactFacadeImpl implements SkillFactFacade {

    private final SkillFactService skillFactService;

    @Override
    @Async
    public void assessmentInstanceEvent(AssessmentInstanceDTO dto, AssessmentReportSyncContext context, PlatformToken platformToken) {
        SecurityContextHolder.getContext().setAuthentication(platformToken);
        skillFactService.assessmentInstanceEvent(dto,context);
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, AssessmentReportSyncContext syncContext) {
        skillFactService.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO, syncContext);
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        skillFactService.assessmentInstanceDeleteEvent(id);
    }

    @Override
    public void syncAssessmentData(AssessmentInstanceDTO assessmentInstanceDTO, AssessmentReportSyncContext reportSyncContext, PlatformToken context) {
        SecurityContextHolder.getContext().setAuthentication(context);
        skillFactService.syncAssessmentData(assessmentInstanceDTO,reportSyncContext);
    }

    @Override
    public void questionUpdateEvent(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext) {
        skillFactService.questionUpdateEvent(questionDTO,syncContext);
    }
}