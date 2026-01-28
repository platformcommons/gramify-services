package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.opportunity.dto.OpportunityDTO;
import com.platformcommons.platform.service.changemaker.dto.TenantDataSyncDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.OpportunityService;
import com.platformcommons.platform.service.search.domain.Opportunity;
import com.platformcommons.platform.service.search.messaging.mapper.OpportunityEventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@ConditionalOnProperty(name = "commons.platform.event.enabled", havingValue = "true")
public class OpportunityConsumer {

    @Autowired
    private OpportunityService opportunityService;

    @Autowired
    private OpportunityEventMapper opportunityEventMapper;

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityCreated(@Payload EventDTO<OpportunityDTO> eventDTO){
        OpportunityDTO opportunityDTO = eventDTO.getPayload(new TypeReference<OpportunityDTO>() {});
        Opportunity opportunity = opportunityEventMapper.fromEventDTO(opportunityDTO);
        opportunityService.save(opportunity);
        log.info(String.format("Opportunity Created Consumed with id >>>>>>>>>>>>>>>>>> %d ",opportunity.getId()));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityUpdated(@Payload EventDTO<OpportunityDTO> eventDTO){
        OpportunityDTO opportunityDTO = eventDTO.getPayload(new TypeReference<OpportunityDTO>() {});
        Opportunity opportunity = opportunityEventMapper.fromEventDTO(opportunityDTO);
        opportunityService.putUpdate(opportunity);
        log.info(String.format("Opportunity Update Consumed with id >>>>>>>>>>>>>>>>>> %d ",opportunity.getId()));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_DELETED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityDeleted(@Payload EventDTO<Long> id){
        Long opportunityId = id.getPayload(new TypeReference<Long>() {});
        opportunityService.delete(opportunityId);
        log.info(String.format("Opportunity Delete Consumed with id >>>>>>>>>>>>>>>>>> %d ",id.getPayload(new TypeReference<Long>() {})));
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_SYNC,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void opportunitySync(@Payload EventDTO<OpportunityDTO> eventDTO){
        OpportunityDTO opportunityDTO = eventDTO.getPayload(new TypeReference<OpportunityDTO>() {});
        Opportunity opportunity = opportunityEventMapper.fromEventDTO(opportunityDTO);
        opportunityService.putUpdate(opportunity);
        log.info("OpportunityDTO with id >>>>>>>>>>>>>>>>>>> " + opportunity.getId()+" >>>>>>>>>>> consumed and synced in solr");
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_TENANT_DETAILS_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:1}")
    public void opportunityTenantDetailsUpdated(@Payload EventDTO<TenantDataSyncDTO> eventDTO){
        TenantDataSyncDTO tenantDataSyncDTO = eventDTO.getPayload(new TypeReference<TenantDataSyncDTO>() {});
        opportunityService.updateOpportunityTenantDetails(tenantDataSyncDTO);
        log.info("TenantDataSyncDTO with id >>>>>>>>>>>>>>>>>>> " + tenantDataSyncDTO.getId()+" >>>>>>>>>>> consumed and updated in solr");
    }

}