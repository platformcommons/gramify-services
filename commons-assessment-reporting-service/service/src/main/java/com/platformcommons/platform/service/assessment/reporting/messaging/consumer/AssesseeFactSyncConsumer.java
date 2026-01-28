package com.platformcommons.platform.service.assessment.reporting.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.assessment.dto.AssesseSyncedDTO;
import com.platformcommons.platform.service.assessment.reporting.application.HierarchyResolutionService;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AssesseeFactSyncConsumer {



    private final HierarchyResolutionService hierarchyResolutionService;

    @KafkaListener(topics = MessagingConstant.ASSESSMENT_INSTANCE_ASSESSE_SYNCED_TOPIC, groupId = MessagingConstant.GROUP_ID, containerFactory = MessagingConstant.JSON_CONTAINER_FACTORY)
    public void createAssessment(@Payload EventDTO<AssesseSyncedDTO> payload) {
        try {
            AssesseSyncedDTO assessee = payload.getPayloadAndInitializeContext(new TypeReference<AssesseSyncedDTO>() {});
            if(log.isTraceEnabled()) {
                log.trace("Processing assessee sync event for assesseeId: {}", assessee.getAssesseeId());
            }
            hierarchyResolutionService.resolveAndPopulateHierarchy(assessee);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
