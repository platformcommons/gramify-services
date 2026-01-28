package com.platformcommons.platform.service.assessment.reporting.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler.AssessmentQuestionPaperEventHandler;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@Slf4j
@RequiredArgsConstructor
public class AssessmentQuestionPaperConsumer {

    private final AssessmentQuestionPaperEventHandler eventHandler;

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_QUESTION_PAPER_CREATED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void createdAssessmentQuestionPaperEvent(@Payload EventDTO<AssessmentQuestionPaperDTO> payload) {

        log.debug("Consuming AssessmentQuestionPaper Create Event for {}", payload);
        try {
            AssessmentQuestionPaperDTO assessmentQuestionPaperDTO = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentQuestionPaperDTO>() {});
            eventHandler.handleAssessmentQuestionPaperCreatedEvent(assessmentQuestionPaperDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_QUESTION_PAPER_UPDATED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void updateAssessmentQuestionPaperEvent(@Payload EventDTO<AssessmentQuestionPaperDTO> payload) {

        log.debug("Consuming AssessmentQuestionPaper Update Event for {}", payload);
        try {
            AssessmentQuestionPaperDTO assessmentQuestionPaperDTO = payload.getPayloadAndInitializeContext(new TypeReference<AssessmentQuestionPaperDTO>() {});
            eventHandler.handleAssessmentQuestionPaperUpdateEvent(assessmentQuestionPaperDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
