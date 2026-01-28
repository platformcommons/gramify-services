package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssesseResponseFactService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssesseResponseFactRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseResponseFactAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssesseResponseFactServiceImpl implements AssesseResponseFactService {

    private final AssesseResponseFactRepository assesseResponseFactRepository;
    private final AssesseResponseFactAssembler assesseResponseFactAssembler;

    @Override
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        assesseResponseFactRepository.saveAll(assesseResponseFactAssembler.toAssesseResponseFact(assesseDTO,context));
    }

    @Override
    public void deleteDimByAssessmentId(Long assessment) {
        assesseResponseFactRepository.deleteByAssessmentId(assessment, EventContext.getSystemEvent());
    }

    @Override
    public void deleteDimByAssesseId(Long id) {
        assesseResponseFactRepository.deleteDimByAssesseId(id,EventContext.getSystemEvent());
    }

}
