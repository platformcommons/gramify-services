package com.platformcommons.platform.service.assessment.reporting.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler.QuestionEventHandler;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class QuestionConsumer {

    private final QuestionEventHandler questionEventHandler;

    @KafkaListener(topics = MessagingConstant.QUESTION_CREATED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void createQuestionEvent(@Payload EventDTO<QuestionDTO> payload) {

        log.debug("Consuming Question Create Event for {}", payload);
        try {
            QuestionDTO questionDTO = payload.getPayloadAndInitializeContext(new TypeReference<QuestionDTO>(){});
            questionEventHandler.createQuestionEvent(questionDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @KafkaListener(topics = MessagingConstant.QUESTION_UPDATED_TOPIC, groupId = MessagingConstant.GROUP_ID,
            containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void updateQuestionEvent(@Payload EventDTO<QuestionDTO> payload) {

        log.debug("Consuming Question Update Event for {}", payload);
        try {
            QuestionDTO questionDTO = payload.getPayloadAndInitializeContext(new TypeReference<QuestionDTO>(){});
            questionEventHandler.updateQuestionEvent(questionDTO);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
