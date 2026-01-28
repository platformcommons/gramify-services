package com.platformcommons.platform.service.assessment.reporting.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.application.utility.SectionQuestionUtil;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler.AssessmentInstanceAssesseEventHandler;
import com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler.OptionResponseHandler;
import com.platformcommons.platform.service.assessment.reporting.messaging.producer.AssesseeFactSyncProducer;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class AssessmentInstanceAssesseConsumer {

    private final AssessmentInstanceAssesseEventHandler assessmentInstanceAssesseEventHandler;
    private final OptionResponseHandler optionResponseHandler;

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_INSTANCE_ASSESSE_CREATED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void createAssessmentInstanceAssesse(@Payload EventDTO<AssessmentInstanceAssesseDTO> payload) {

        log.debug("Consuming Assessment Instance Assesse Create Event for {}", payload);
        try {
            AssessmentInstanceAssesseDTO assesseDTO = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentInstanceAssesseDTO>() {});
            SectionQuestionUtil.setSectionQuestion(assesseDTO);
            assessmentInstanceAssesseEventHandler.createAssessmentInstanceAssesseEvent(assesseDTO);
            optionResponseHandler.publish(assesseDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @KafkaListener(topics = MessagingConstant.ASSESSMENT_INSTANCE_ASSESSE_UPDATE_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void updateAssessmentInstanceAssesse(@Payload EventDTO<AssessmentInstanceAssesseDTO> payload) {

        log.debug("Consuming Assessment Instance Assesse Update Event for {}", payload);
        try {
            AssessmentInstanceAssesseDTO assesseDTO = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentInstanceAssesseDTO>() {});
            SectionQuestionUtil.setSectionQuestion(assesseDTO);
            assessmentInstanceAssesseEventHandler.updateAssessmentInstanceAssesseEvent(assesseDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
