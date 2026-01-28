package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.AssesseDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssesseDimRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssesseDimAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AssesseDimServiceImpl implements AssesseDimService {

    private final AssesseDimRepository assesseDimRepository;
    private final AssesseDimAssembler assesseDimAssembler;


    @Override
    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        assesseDimRepository.save(assesseDimAssembler.toAssesseDim(assesseDTO, context));
    }

    @Override
    public boolean responseExist(Long id) {
        return assesseDimRepository.existsByAssessmentId(id,EventContext.getSystemEvent());
    }

    @Override
    public void deleteDimByAssessmentId(Long assessment) {
        assesseDimRepository.deleteByAssessmentId(assessment, EventContext.getSystemEvent());
    }

    @Override
    public String getUserIdsForInstance(Set<Long> ids) {
        return assesseDimRepository.getUserIdsForInstance(ids);
    }

    @Override
    public void deleteDimByAssesseId(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        assesseDimRepository.deleteDimByAssesseId(assesseDTO.getId(), EventContext.getSystemEvent());
    }

    @Override
    public Optional<AssesseDim> getAssesseDimByAssesseId(Long assesseeId) {
        return assesseDimRepository.getAssesseDimByAssesseId(assesseeId);
    }

    @Override
    public AssesseDim save(AssesseDim assesseDim) {
        return assesseDimRepository.save(assesseDim);
    }
}
