package com.platformcommons.platform.service.assessment.reporting.messaging.producer;

import com.platformcommons.platform.service.assessment.dto.AssesseDimDTO;
import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;
import com.platformcommons.platform.service.assessment.dto.AssesseSyncedDTO;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.Optional;

@Component
@Slf4j
public class AssesseHierarchyResolutionProducer {



    private final Optional<KafkaTemplate<String, AssesseDimHierarchyResolvedEventDTO>> kafkaTemplate;

    public AssesseHierarchyResolutionProducer(@Autowired(required = false)
                                              @Qualifier("assesseDimHierarchyResolvedEventKafkaTemplate")
                                              KafkaTemplate<String, AssesseDimHierarchyResolvedEventDTO> kafkaTemplate) {
        this.kafkaTemplate = Optional.ofNullable(kafkaTemplate);
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void assesseDimHierarchyResolved(AssesseDimHierarchyResolvedEventDTO assesseDimDTO) {
        if (kafkaTemplate.isPresent()) {
            KafkaTemplate<String, AssesseDimHierarchyResolvedEventDTO> template = kafkaTemplate.get();
            log.debug("Publishing AssesseDimHierarchyResolvedEvent for assesseDimId: {}", assesseDimDTO.getAssesseDim().getId());
            template.send(MessagingConstant.ASSESSMENT_INSTANCE_ASSESSE_HIERARCHY_SYNCED_TOPIC, assesseDimDTO);

        } else {
            log.warn("KafkaTemplate 'assesseDimHierarchyResolvedEventKafkaTemplate' is not configured. " +
                    "Skipping Kafka message publish for assesseDimId: {}", assesseDimDTO.getAssesseDim().getId());
        }
    }


}
