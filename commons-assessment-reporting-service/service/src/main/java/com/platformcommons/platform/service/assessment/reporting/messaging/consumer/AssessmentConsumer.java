package com.platformcommons.platform.service.assessment.reporting.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler.AssessmentEventHandler;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class AssessmentConsumer {

    private final AssessmentEventHandler assessmentEventHandler;

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_CREATED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void createAssessment(@Payload EventDTO<AssessmentDTO> payload) {

        log.debug("Consuming Assessment Create Event for {}", payload);
        try {
            AssessmentDTO assessmentPayload = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentDTO>() {});
            assessmentEventHandler.handleAssessmentCreatedEvent(assessmentPayload);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_UPDATED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void updateAssessment(@Payload EventDTO<AssessmentDTO> payload) {
            log.info("Consuming Assessment Updated Event");
            try {
                AssessmentDTO assessmentPayload = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentDTO>() {});
                assessmentEventHandler.handleAssessmentUpdateEvent(assessmentPayload);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            log.debug("Consumed Assessment Updated Event for {}", payload);
    }

}
