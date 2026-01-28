package com.platformcommons.platform.service.assessment.messaging.producer;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AssessmentEventProducer {

    private final Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate;

    public AssessmentEventProducer(@Qualifier("jsonMessageTemplate") Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void assessmentCreated(AssessmentDTO assessmentDTO) {
        kafkaTemplate.ifPresent(kafkaTemplate -> {
            EventDTO<AssessmentDTO> eventDTO = EventDTO.<AssessmentDTO>builder()
                    .requestPayload(assessmentDTO)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.ASSESSMENT_CREATED_TOPIC, eventDTO);
            log.debug("Assessment Created Topic: {}, Assessment ID: {}", EventConstants.ASSESSMENT_CREATED_TOPIC,assessmentDTO.getId());
        });
    }

    public void assessmentUpdated(AssessmentDTO assessmentDTO) {
        kafkaTemplate.ifPresent(kafkaTemplate -> {
            EventDTO<AssessmentDTO> eventDTO = EventDTO.<AssessmentDTO>builder()
                    .requestPayload(assessmentDTO)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.ASSESSMENT_UPDATED_TOPIC, eventDTO);
            log.debug("Assessment Updated Topic: {}, Assessment ID: {}", EventConstants.ASSESSMENT_UPDATED_TOPIC,assessmentDTO.getId());
        });
    }

}