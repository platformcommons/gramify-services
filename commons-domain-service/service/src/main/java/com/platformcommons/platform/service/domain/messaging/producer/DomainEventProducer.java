package com.platformcommons.platform.service.domain.messaging.producer;

import com.platformcommons.platform.service.domain.dto.DomainEventDTO;
import com.platformcommons.platform.service.domain.dto.DomainHierarchyEventDTO;
import com.platformcommons.platform.service.domain.messaging.constants.EventConstants;
import com.platformcommons.platform.service.dto.event.EventDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class DomainEventProducer {

    @Autowired
    private KafkaTemplate<String, EventDTO> kafkaTemplate;

    public void  domainCreated(DomainEventDTO domainEventDTO){
        EventDTO<DomainEventDTO> eventDTO = EventDTO.<DomainEventDTO>builder()
                .requestPayload(domainEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.DOMAIN_CREATED, eventDTO);
    }

    public void  domainUpdated(DomainEventDTO domainEventDTO){
        EventDTO<DomainEventDTO> eventDTO = EventDTO.<DomainEventDTO>builder()
                .requestPayload(domainEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.DOMAIN_UPDATED, eventDTO);
    }


    public void  domainHierarchyCreated(DomainHierarchyEventDTO hierarchyEventDTO){
        EventDTO<DomainHierarchyEventDTO> eventDTO = EventDTO.<DomainHierarchyEventDTO>builder()
                .requestPayload(hierarchyEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.DOMAIN_HIERARCHY_CREATED, eventDTO);
    }

    public void  domainHierarchyUpdated(DomainHierarchyEventDTO hierarchyEventDTO){
        EventDTO<DomainHierarchyEventDTO> eventDTO = EventDTO.<DomainHierarchyEventDTO>builder()
                .requestPayload(hierarchyEventDTO)
                .build();
        kafkaTemplate.send(EventConstants.DOMAIN_HIERARCHY_UPDATED, eventDTO);
    }
}
