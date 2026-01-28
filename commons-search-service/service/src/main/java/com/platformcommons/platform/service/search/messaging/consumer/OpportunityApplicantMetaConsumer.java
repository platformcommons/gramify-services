package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.changemaker.dto.OpportunityApplicantMetaDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.OpportunityApplicantService;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
import com.platformcommons.platform.service.search.messaging.mapper.OpportunityApplicantEventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Component
@Slf4j
@ConditionalOnProperty(name = "commons.platform.event.enabled", havingValue = "true")
public class OpportunityApplicantMetaConsumer {

    @Autowired
    private OpportunityApplicantEventMapper mapper;

    @Autowired
    private OpportunityApplicantService service;

    @Autowired
    private OpportunityApplicantFacade opportunityApplicantFacade;

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_META_CREATE,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityApplicantMetaCreated(@Payload EventDTO<OpportunityApplicantMetaDTO> eventDTO){
        OpportunityApplicantMetaDTO opportunityApplicantMetaDTO = eventDTO.getPayload(new TypeReference<OpportunityApplicantMetaDTO>() {});
        opportunityApplicantFacade.appendOpportunityApplicantMetaDetails(opportunityApplicantMetaDTO);
        log.info("OpportunityApplicantMetaDTO consumed and created with applicant id >>>>>>>>>>>>>>>>>>> " + opportunityApplicantMetaDTO.getOpportunityApplicantId());
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_META_UPDATE,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityApplicantMetaUpdated(@Payload EventDTO<OpportunityApplicantMetaDTO> eventDTO){
        OpportunityApplicantMetaDTO opportunityApplicantMetaDTO = eventDTO.getPayload(new TypeReference<OpportunityApplicantMetaDTO>() {});
        opportunityApplicantFacade.appendOpportunityApplicantMetaDetails(opportunityApplicantMetaDTO);
        log.info("OpportunityApplicantMetaDTO consumed and created with applicant id >>>>>>>>>>>>>>>>>>> " + opportunityApplicantMetaDTO.getOpportunityApplicantId());
    }
}