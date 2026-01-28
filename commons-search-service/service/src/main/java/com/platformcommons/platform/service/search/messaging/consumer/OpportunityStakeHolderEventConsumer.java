package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.changemaker.dto.OpportunityDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.OpportunityService;
import com.platformcommons.platform.service.search.domain.Opportunity;
import com.platformcommons.platform.service.search.dto.OpportunityStakeHolderCustomDTO;
import com.platformcommons.platform.service.search.messaging.mapper.OpportunityEventMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OpportunityStakeHolderEventConsumer {
    @Autowired
    private OpportunityService service;

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_STAKE_HOLDER_ADDED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void stakeHolderAdded(@Payload EventDTO<OpportunityStakeHolderCustomDTO> eventDTO){
        OpportunityStakeHolderCustomDTO stakeHolderCustomDTO = eventDTO.getPayload(new TypeReference<OpportunityStakeHolderCustomDTO>() {});
        service.addOpportunityStakeHolderToOpportunities(stakeHolderCustomDTO);
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_STAKE_HOLDER_DELETED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void stakeHolderDeleted(@Payload EventDTO<OpportunityStakeHolderCustomDTO> eventDTO){
        OpportunityStakeHolderCustomDTO stakeHolderCustomDTO = eventDTO.getPayload(new TypeReference<OpportunityStakeHolderCustomDTO>() {});
        service.deleteOpportunityStakeHolderFromOpportunity(stakeHolderCustomDTO);
    }

}
