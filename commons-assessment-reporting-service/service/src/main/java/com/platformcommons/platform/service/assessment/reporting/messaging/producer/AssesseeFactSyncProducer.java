package com.platformcommons.platform.service.assessment.reporting.messaging.producer;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssesseSyncedDTO;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class AssesseeFactSyncProducer {

    private final Optional<KafkaTemplate<String, EventDTO>> kafkaTemplate;


    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void assessmentInstanceAssesseCreated(AssesseSyncedDTO assesse) {
        if (kafkaTemplate.isPresent()) {
            KafkaTemplate<String, EventDTO> template = kafkaTemplate.get();

            log.info("Publishing AssesseSyncedEvent for assesseeId: {}", assesse);

            EventDTO<AssesseSyncedDTO> assesseEventDTO = EventDTO.<AssesseSyncedDTO>builder()
                    .requestPayload(assesse)
                    .withContext(true)
                    .build();
            template.send(MessagingConstant.ASSESSMENT_INSTANCE_ASSESSE_SYNCED_TOPIC, assesseEventDTO);

        } else {
            log.warn("KafkaTemplate for EventDTO is not configured. " +
                    "Skipping Kafka message publish for assesseeId: {}", assesse);
        }
    }


}
