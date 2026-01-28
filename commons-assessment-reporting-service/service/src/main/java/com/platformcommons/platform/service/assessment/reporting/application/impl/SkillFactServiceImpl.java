package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.SkillFactService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.SkillFactRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.SkillFactAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SkillFactServiceImpl implements SkillFactService {

    private final SkillFactRepository skillFactRepository;
    private final SkillFactAssembler skillFactAssembler;


    @Override
    public void assessmentInstanceEvent(AssessmentInstanceDTO dto, AssessmentReportSyncContext context) {
        skillFactRepository.saveAll(skillFactAssembler.getSkillFacts(context));
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, AssessmentReportSyncContext syncContext) {
        skillFactRepository.deleteByAssessmentInstanceId(syncContext.getAssessmentInstanceDim().getAssessmentInstanceId(),EventContext.getSystemEvent());
        skillFactRepository.saveAll(skillFactAssembler.getSkillFacts(syncContext));
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        skillFactRepository.deleteByAssessmentId(id, EventContext.getSystemEvent());
    }

    @Override
    public void syncAssessmentData(AssessmentInstanceDTO assessmentInstanceDTO, AssessmentReportSyncContext reportSyncContext) {
        skillFactRepository.deleteByAssessmentId(assessmentInstanceDTO.getAssessment().getId(),EventContext.getSystemEvent());
        assessmentInstanceEvent(assessmentInstanceDTO, reportSyncContext);
    }

    @Override
    public void questionUpdateEvent(QuestionDTO questionDTO, AssessmentReportSyncContext syncContext) {
        skillFactRepository.deleteByAssessmentIdAndQuestionId(syncContext.getAssessmentInstanceDim().getAssessmentId(), questionDTO.getId(),EventContext.getSystemEvent());
        skillFactRepository.saveAll(skillFactAssembler.getSkillFacts(questionDTO,syncContext));
    }
}
