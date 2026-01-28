package com.platformcommons.platform.service.assessment.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.application.utility.submission.CGPostSubmissionExecutorTask;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@ConditionalOnProperty(name = "kafka.consumer.assessment-instance-assesse.enabled", havingValue = "true", matchIfMissing = true)
public class AssessmentInstanceAssesseConsumer {


    private final CGPostSubmissionExecutorTask cGPostSubmissionExecutorTask;

    public AssessmentInstanceAssesseConsumer(CGPostSubmissionExecutorTask cGPostSubmissionExecutorTask) {
        this.cGPostSubmissionExecutorTask = cGPostSubmissionExecutorTask;
    }

    @KafkaListener(topics = EventConstants.ASSESSMENT_INSTANCE_ASSESSE_CREATED_TOPIC, groupId = EventConstants.GROUP_ID,
            containerFactory = EventConstants.JSON_CONTAINER_FACTORY)
    public void createAssessmentInstanceAssesse(@Payload EventDTO<AssessmentInstanceAssesseDTO> payload) {

        log.debug("Consuming Assessment Instance Assesse Create Event for {}", payload);
        try {
            AssessmentInstanceAssesseDTO assesseDTO = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentInstanceAssesseDTO>() {});
            cGPostSubmissionExecutorTask.buildTargetDTO(assesseDTO,assesseDTO.getAssessmentInstance().getId());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }




}
