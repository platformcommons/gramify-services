package com.platformcommons.platform.service.assessment.messaging.producer;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class QuestionEventProducer {

    private final Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate;

    public QuestionEventProducer(@Qualifier("jsonMessageTemplate") Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void questionCreated(QuestionDTO eventDTO) {
        kafkaTemplate.ifPresent(kafkaTemplate -> {
            EventDTO<QuestionDTO> event = EventDTO.<QuestionDTO>builder()
                    .requestPayload(eventDTO)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.QUESTION_CREATED_TOPIC, event);
            log.debug("Question Created Topic: {}, Question ID: {}", EventConstants.QUESTION_CREATED_TOPIC,eventDTO.getId());
        });
    }

    public void questionUpdated(QuestionDTO eventDTO) {
        kafkaTemplate.ifPresent(kafkaTemplate -> {
            EventDTO<QuestionDTO> event = EventDTO.<QuestionDTO>builder()
                    .requestPayload(eventDTO)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.QUESTION_UPDATED_TOPIC, event);
            log.debug("Question Updated Topic: {}, Question ID: {}", EventConstants.QUESTION_UPDATED_TOPIC,eventDTO.getId());
        });
    }
}
