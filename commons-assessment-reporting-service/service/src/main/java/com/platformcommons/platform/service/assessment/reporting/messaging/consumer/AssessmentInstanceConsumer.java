package com.platformcommons.platform.service.assessment.reporting.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler.AssessmentInstanceEventHandler;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class AssessmentInstanceConsumer {

    private final AssessmentInstanceEventHandler eventHandler;

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_INSTANCE_CREATED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void createAssessmentInstanceEvent(@Payload EventDTO<AssessmentInstanceDTO> payload) {

        log.debug("Consuming Assessment Instance Created Event for {}", payload);
        try {
            AssessmentInstanceDTO assessmentInstanceDTO = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentInstanceDTO>() {});
            eventHandler.handleAssessmentInstanceCreateEvent(assessmentInstanceDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_INSTANCE_UPDATED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void updateAssessmentInstanceEvent(@Payload EventDTO<AssessmentInstanceDTO> payload) {

        log.debug("Consuming Assessment Instance Update Event for {}", payload);
        try {
            AssessmentInstanceDTO assessmentInstanceDTO  = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentInstanceDTO>() {});
            eventHandler.handleAssessmentInstanceUpdateEvent(assessmentInstanceDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_INSTANCE_DELETED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void deleteAssessmentInstanceEvent(@Payload EventDTO<AssessmentInstanceDTO> payload) {

        log.debug("Consuming Assessment Instance Deleted Event for {}", payload);
        try {
            AssessmentInstanceDTO assessmentInstanceDTO = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentInstanceDTO>() {});
            eventHandler.handleAssessmentInstanceDeleteEvent(assessmentInstanceDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
