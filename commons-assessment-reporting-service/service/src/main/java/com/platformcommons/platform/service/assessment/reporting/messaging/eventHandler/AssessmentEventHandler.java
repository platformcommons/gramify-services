package com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.AssessmentInstanceDimFacade;
import com.platformcommons.platform.service.assessment.reporting.facade.UserContributionDimFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@Slf4j
@Transactional
public class AssessmentEventHandler {

    private final AssessmentInstanceDimFacade facade;
    private final UserContributionDimFacade userContributionDimFacade;

    public void handleAssessmentCreatedEvent(AssessmentDTO assessmentDTO) {
        log.trace("Handling AssessmentCreate Event in AssessmentEventHandler");
        facade.assessmentCreateEvent(assessmentDTO);
        userContributionDimFacade.createAssessmentContributions(assessmentDTO);
    }

    public void handleAssessmentUpdateEvent(AssessmentDTO assessmentDTO) {
        facade.assessmentUpdateEvent(assessmentDTO);
    }
}
