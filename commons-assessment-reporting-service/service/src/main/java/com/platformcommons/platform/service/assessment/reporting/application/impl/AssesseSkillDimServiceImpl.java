package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssesseSkillDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssesseSkillDimRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseSkillDimAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssesseSkillDimServiceImpl implements AssesseSkillDimService {

    private final AssesseSkillDimRepository assesseSkillDimRepository;
    private final AssesseSkillDimAssembler assesseSkillDimAssembler;

    @Override
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        assesseSkillDimRepository.saveAll(assesseSkillDimAssembler.toAssesseSkillDim(assesseDTO,context));
    }

    @Override
    public void deleteDimByAssesssesId(Set<Long> collect) {
        assesseSkillDimRepository.deleteByAssesseIdIn(collect, EventContext.getSystemEvent());
    }
}
