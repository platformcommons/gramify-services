package com.platformcommons.platform.service.iam.messaging.producer;

import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.messaging.MessagingConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "commons.platform.event.enabled", havingValue = "true")
@Slf4j
public class TenantProducer {

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    @Async
    public void tenantUpdated(TenantDTO tenantDTO){
        EventDTO<TenantDTO> eventDTO = EventDTO.<TenantDTO>builder()
                .requestPayload(tenantDTO)
                .build();
        kafkaTemplate.send(MessagingConstant.COMMONS_TENANT_UPDATED, eventDTO);
        log.error("Messaging ---> Updated Tenant with id {}",tenantDTO.getId());

    }
}
