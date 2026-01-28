package com.platformcommons.platform.service.assessment.messaging.producer;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AssessmentQuestionPaperEventProducer {

    private final Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate;

    public AssessmentQuestionPaperEventProducer(@Qualifier("jsonMessageTemplate") Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void assessmentQuestionPaperCreated(AssessmentQuestionPaperDTO eventDTO) {
        kafkaTemplate.ifPresent(kafkaTemplate -> {
            EventDTO<AssessmentQuestionPaperDTO> event = EventDTO.<AssessmentQuestionPaperDTO>builder()
                    .requestPayload(eventDTO)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.ASSESSMENT_QUESTION_PAPER_CREATED_TOPIC, event);
            log.debug("Assessment Question Paper Created Topic: {}, Assessment Question Paper ID: {}", EventConstants.ASSESSMENT_QUESTION_PAPER_CREATED_TOPIC,eventDTO.getId());
        });
    }

    public void assessmentQuestionPaperUpdated(AssessmentQuestionPaperDTO eventDTO) {
        kafkaTemplate.ifPresent(kafkaTemplate -> {
            EventDTO<AssessmentQuestionPaperDTO> event = EventDTO.<AssessmentQuestionPaperDTO>builder()
                    .requestPayload(eventDTO)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.ASSESSMENT_QUESTION_PAPER_UPDATED_TOPIC, event);
            log.debug("Assessment Question Paper Updated Topic: {}, Assessment Question Paper ID: {}", EventConstants.ASSESSMENT_QUESTION_PAPER_UPDATED_TOPIC,eventDTO.getId());
        });
    }
}
