package com.platformcommons.platform.service.assessment.messaging.producer;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AssessmentInstanceEventProducer {

    private final Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate;

    public AssessmentInstanceEventProducer(@Qualifier("jsonMessageTemplate")  Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void assessmentInstanceCreated(AssessmentInstanceDTO eventDTO) {

        kafkaTemplate.ifPresent(kafkaTemplate -> {
            EventDTO<AssessmentInstanceDTO> event = EventDTO.<AssessmentInstanceDTO>builder()
                    .requestPayload(eventDTO)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.ASSESSMENT_INSTANCE_CREATED_TOPIC, event);
            log.debug("Assessment Instance Created Topic: {}, Assessment Instance ID: {}", EventConstants.ASSESSMENT_INSTANCE_CREATED_TOPIC,eventDTO.getId());
        });
    }

    public void assessmentInstanceUpdated(AssessmentInstanceDTO eventDTO) {
        kafkaTemplate.ifPresent( kafkaTemplate -> {
            EventDTO<AssessmentInstanceDTO> event = EventDTO.<AssessmentInstanceDTO>builder()
                    .requestPayload(eventDTO)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.ASSESSMENT_INSTANCE_UPDATED_TOPIC, event);
            log.debug("Assessment Instance Updated Topic: {}, Assessment Instance ID: {}", EventConstants.ASSESSMENT_INSTANCE_UPDATED_TOPIC,eventDTO.getId());
        });
    }

    public void assessmentInstanceDeleted(Long eventDTO) {
        kafkaTemplate.ifPresent( kafkaTemplate -> {
            EventDTO<Long> event = EventDTO.<Long>builder()
                    .requestPayload(eventDTO)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.ASSESSMENT_INSTANCE_DELETED_TOPIC, event);
            log.debug("Assessment Instance Deleted Topic: {}, Assessment Instance ID: {}", EventConstants.ASSESSMENT_INSTANCE_DELETED_TOPIC,eventDTO);
        });
    }
}
