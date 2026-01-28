package com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class AssessmentQuestionPaperEventHandler {

    private final AssessmentInstanceDimFacade assessmentInstanceDimFacade;
    private final QuestionPaperSectionDimFacade questionPaperSectionDimFacade;
    private final SectionQuestionDimFacade sectionQuestionDimFacade;
    private final QuestionFactFacade questionFactFacade;
    private final OptionFactFacade optionFactFacade;
    private final SkillFactFacade skillFactFacade;
    private final AssesseDimFacade assesseDimFacade;

    public void handleAssessmentQuestionPaperCreatedEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        assessmentInstanceDimFacade.assessmentQuestionPaperCreate(assessmentQuestionPaperDTO);
        questionPaperSectionDimFacade.assessmentQuestionPaperCreate(assessmentQuestionPaperDTO);
        sectionQuestionDimFacade.assessmentQuestionPaperCreate(assessmentQuestionPaperDTO);
    }

    public void handleAssessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {

        assessmentInstanceDimFacade.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO);
        questionPaperSectionDimFacade.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO);
        sectionQuestionDimFacade.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO);
        if(!assesseDimFacade.responseExist(assessmentQuestionPaperDTO.getAssessment().getId())) {
            AssessmentReportSyncContext syncContext = assessmentInstanceDimFacade.getSyncContext(assessmentQuestionPaperDTO.getAssessment(),true);
            questionFactFacade.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO, syncContext);
            optionFactFacade.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO, syncContext);
            skillFactFacade.assessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO, syncContext);
        }
        assessmentInstanceDimFacade.updateSyncContext(assessmentQuestionPaperDTO.getAssessment().getId());
    }
}
