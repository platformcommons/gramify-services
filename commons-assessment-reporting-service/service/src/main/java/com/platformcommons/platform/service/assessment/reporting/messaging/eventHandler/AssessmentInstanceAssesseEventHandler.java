package com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler;

import com.platformcommons.platform.service.assessment.dto.AssesseSyncedDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Component
@RequiredArgsConstructor
@Transactional
public class AssessmentInstanceAssesseEventHandler {

    private final AssesseDimFacade assesseDimFacade;
    private final AssesseSkillDimFacade assesseSkillDimFacade;
    private final AssesseResponseFactFacade assesseResponseFactFacade;
    private final AssessmentInstanceDimFacade assessmentInstanceDimFacade;
    private final QuestionFactFacade questionFactFacade;
    private final OptionFactFacade optionFactFacade;
    private final ApplicationEventPublisher publisher;


    public void createAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO) {
        AssessmentReportSyncContext context = assessmentInstanceDimFacade.getSyncContext(assesseDTO.getAssessmentInstance().getAssessment(), false);
        assesseDimFacade.createAssessmentInstanceAssesseEvent(assesseDTO,context);
        assesseSkillDimFacade.createAssessmentInstanceAssesseEvent(assesseDTO,context);
        assesseResponseFactFacade.createAssessmentInstanceAssesseEvent(assesseDTO,context);

        questionFactFacade.createAssessmentInstanceAssesseEvent(assesseDTO,context);
        optionFactFacade.createAssessmentInstanceAssesseEvent(assesseDTO,context);
        publisher.publishEvent(AssesseSyncedDTO.builder().assesseeId(assesseDTO.getId()).build());
    }

    public void updateAssessmentInstanceAssesseEvent(AssessmentInstanceAssesseDTO assesseDTO) {
        AssessmentReportSyncContext context = assessmentInstanceDimFacade.getSyncContext(assesseDTO.getAssessmentInstance().getAssessment(), false);

        assesseDimFacade.deleteDimByAssesseId(assesseDTO,context);
        assesseSkillDimFacade.deleteDimByAssesssesId(Collections.singleton(assesseDTO.getId()));
        assesseResponseFactFacade.deleteDimByAssesseId(assesseDTO.getId());

        assesseDimFacade.createAssessmentInstanceAssesseEvent(assesseDTO,context);
        assesseSkillDimFacade.createAssessmentInstanceAssesseEvent(assesseDTO,context);
        assesseResponseFactFacade.createAssessmentInstanceAssesseEvent(assesseDTO,context);

    }
}
