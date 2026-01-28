package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.domain.dto.DomainEventDTO;
import com.platformcommons.platform.service.domain.dto.DomainHierarchyEventDTO;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.DomainService;
import com.platformcommons.platform.service.search.domain.Domain;
import com.platformcommons.platform.service.search.facade.assembler.DomainEventDTOMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class DomainConsumer {

    @Autowired
    private DomainService domainService;

    @Autowired
    private DomainEventDTOMapper domainEventDTOMapper;

    @Transactional
    @KafkaListener(topics = MessagingConstant.DOMAIN_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void domainCreated(@Payload EventDTO<DomainEventDTO> eventDTO){
        DomainEventDTO domainEventDTO = eventDTO.getPayload(new TypeReference<DomainEventDTO>() {});
        domainService.createDomain(domainEventDTOMapper.fromDTO(domainEventDTO));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.DOMAIN_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void domainUpdated(@Payload EventDTO<DomainEventDTO> eventDTO){
        DomainEventDTO domainEventDTO = eventDTO.getPayload(new TypeReference<DomainEventDTO>() {});
        domainService.updateDomain(domainEventDTOMapper.fromDTO(domainEventDTO));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.DOMAIN_HIERARCHY_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void domainHierarchyCreated(@Payload EventDTO<DomainHierarchyEventDTO> eventDTO){
        DomainHierarchyEventDTO domainHierarchyEventDTO = eventDTO.getPayload(new TypeReference<DomainHierarchyEventDTO>() {});
        Domain parentDomain = domainService.getDomainById(domainHierarchyEventDTO.getParentId());
        Domain domain = domainService.getDomainById(domainHierarchyEventDTO.getChildId());
        Set<String> subDomainCodes =parentDomain.getSubDomainCodes();
       if(subDomainCodes!=null){
           subDomainCodes.add(domain.getCode());

       }
       else {
           subDomainCodes = new LinkedHashSet<>();
           subDomainCodes.add(domain.getCode());
       }
       parentDomain.setSubDomainCodes(subDomainCodes);
       domainService.createDomain(parentDomain);

    }
}
