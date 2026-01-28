package com.platformcommons.platform.service.assessment.messaging.producer;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;


@Component
@Slf4j
public class AssessmentInstanceAssesseEventProducer {

    private final Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate;

    public AssessmentInstanceAssesseEventProducer(@Qualifier("jsonMessageTemplate")  Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void assessmentInstanceAssesseCreated(AssessmentInstanceAssesseDTO assesse) {
        kafkaTemplate.ifPresent(kafkaTemplate -> {
            EventDTO<AssessmentInstanceAssesseDTO> assesseEventDTO = EventDTO.<AssessmentInstanceAssesseDTO>builder()
                    .requestPayload(assesse)
                    .withContext(true)

                    .build();
            kafkaTemplate.send(EventConstants.ASSESSMENT_INSTANCE_ASSESSE_CREATED_TOPIC, assesseEventDTO);
            log.debug("Assessment Instance Assesse Created Topic: {}, Assesse ID: {}", EventConstants.ASSESSMENT_INSTANCE_ASSESSE_CREATED_TOPIC,assesse.getId());
        });
        if(log.isTraceEnabled()) {
            log.trace("Emitting create assesse event for id {}",assesse.getId());
        }
    }
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void assessmentInstanceAssesseUpdated(AssessmentInstanceAssesseDTO assesse) {
        kafkaTemplate.ifPresent(kafkaTemplate -> {
            EventDTO<AssessmentInstanceAssesseDTO> assesseEventDTO = EventDTO.<AssessmentInstanceAssesseDTO>builder()
                    .requestPayload(assesse)
                    .withContext(true)
                    .build();
            kafkaTemplate.send(EventConstants.ASSESSMENT_INSTANCE_ASSESSE_UPDATED_TOPIC, assesseEventDTO);
            log.debug("Assessment Instance Assesse Updated Topic: {}, Assesse ID: {}", EventConstants.ASSESSMENT_INSTANCE_ASSESSE_UPDATED_TOPIC,assesse.getId());
        });
        if(log.isTraceEnabled()) {
            log.trace("Emitting update assesse event for id {}",assesse.getId());
        }
    }
}
