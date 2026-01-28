package com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Transactional
public class AssessmentInstanceEventHandler {

    private final AssessmentInstanceDimFacade facade;

    private final QuestionPaperSectionDimFacade questionPaperSectionDimFacade;
    private final SectionQuestionDimFacade sectionQuestionDimFacade;

    private final QuestionFactFacade questionFactFacade;
    private final OptionFactFacade optionFactFacade;
    private final SkillFactFacade skillFactFacade;


    public void handleAssessmentInstanceCreateEvent(AssessmentInstanceDTO dto){
        facade.assessmentInstanceCreateEvent(dto);
        AssessmentReportSyncContext context = facade.getSyncContext(dto.getAssessment(), false);
        questionFactFacade.assessmentInstanceEvent(dto,context, PlatformSecurityUtil.getContext());
        optionFactFacade.assessmentInstanceEvent(dto,context, PlatformSecurityUtil.getContext());
        skillFactFacade.assessmentInstanceEvent(dto,context, PlatformSecurityUtil.getContext());

    }


    public void handleAssessmentInstanceUpdateEvent(AssessmentInstanceDTO assessmentInstanceDTO) {
        facade.assessmentInstanceUpdateEvent(assessmentInstanceDTO);
        facade.updateSyncContext(assessmentInstanceDTO.getAssessment().getId());
    }

    public void handleAssessmentInstanceDeleteEvent(AssessmentInstanceDTO assessmentInstanceDTO) {
        facade.assessmentInstanceDeleteEvent(assessmentInstanceDTO.getId());
        questionPaperSectionDimFacade.assessmentInstanceDeleteEvent(assessmentInstanceDTO.getAssessment().getId());
        sectionQuestionDimFacade.assessmentInstanceDeleteEvent(assessmentInstanceDTO.getAssessment().getId());
        questionFactFacade.assessmentInstanceDeleteEvent(assessmentInstanceDTO.getAssessment().getId());
        optionFactFacade.assessmentInstanceDeleteEvent(assessmentInstanceDTO.getAssessment().getId());
        skillFactFacade.assessmentInstanceDeleteEvent(assessmentInstanceDTO.getAssessment().getId());
    }
}
