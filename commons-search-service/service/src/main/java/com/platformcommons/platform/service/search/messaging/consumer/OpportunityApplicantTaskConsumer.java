package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.changemaker.dto.CustomOpportunityApplicantTaskDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.domain.OpportunityApplicantTask;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantTaskDTO;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantTaskFacade;
import com.platformcommons.platform.service.search.messaging.mapper.OpportunityApplicantTaskEventMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Slf4j
@ConditionalOnProperty(name = "commons.platform.event.enabled", havingValue = "true")
public class OpportunityApplicantTaskConsumer {

    @Autowired
    private OpportunityApplicantTaskFacade opportunityApplicantTaskFacade;

    @Autowired
    private OpportunityApplicantTaskEventMapper opportunityApplicantTaskEventMapper;

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_TASK_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void applicantOwnerResultCreated(@Payload EventDTO<CustomOpportunityApplicantTaskDTO> eventDTO){
        CustomOpportunityApplicantTaskDTO customOpportunityApplicantTaskDTO = eventDTO.getPayload(new TypeReference<CustomOpportunityApplicantTaskDTO>() {});
        OpportunityApplicantTaskDTO opportunityApplicantTaskDTO = opportunityApplicantTaskEventMapper.fromEventDTO(customOpportunityApplicantTaskDTO);
        opportunityApplicantTaskFacade.addOrUpdateOpportunityApplicantTaskInApplicant(opportunityApplicantTaskDTO);
        log.info("Consumed CREATED OpportunityApplicantTask for applicantId {} ", customOpportunityApplicantTaskDTO.getOpportunityApplicantId());
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_TASK_UPDATED, groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void opportunityTaskUpdated(@Payload EventDTO<CustomOpportunityApplicantTaskDTO> eventDTO) {
        CustomOpportunityApplicantTaskDTO customOpportunityApplicantTaskDTO = eventDTO.getPayload(new TypeReference<CustomOpportunityApplicantTaskDTO>() {});
        OpportunityApplicantTaskDTO opportunityApplicantTaskDTO = opportunityApplicantTaskEventMapper.fromEventDTO(customOpportunityApplicantTaskDTO);

        opportunityApplicantTaskFacade.addOrUpdateOpportunityApplicantTaskInApplicant(opportunityApplicantTaskDTO);

        log.info("Consumed UPDATED OpportunityApplicantTask for applicantId {}", customOpportunityApplicantTaskDTO.getOpportunityApplicantId());
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_TASK_CREATED_BULK, groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void opportunityTaskBulkCreated(@Payload EventDTO<Set<CustomOpportunityApplicantTaskDTO>> eventDTO) {
        Set<CustomOpportunityApplicantTaskDTO> customOpportunityApplicantTaskDTOSet = eventDTO.getPayload(new TypeReference<Set<CustomOpportunityApplicantTaskDTO>>() {});

        customOpportunityApplicantTaskDTOSet.forEach(dto -> {
            OpportunityApplicantTaskDTO opportunityApplicantTaskDTO = opportunityApplicantTaskEventMapper.fromEventDTO(dto);
            opportunityApplicantTaskFacade.addOrUpdateOpportunityApplicantTaskInApplicant(opportunityApplicantTaskDTO);
        });
        log.info("Consumed BULK CREATED OpportunityApplicantTask for applicantId");
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_TASK_DELETED, groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void opportunityTaskDeleted(@Payload EventDTO<CustomOpportunityApplicantTaskDTO> eventDTO) {
        CustomOpportunityApplicantTaskDTO customOpportunityApplicantTaskDTO = eventDTO.getPayload(new TypeReference<CustomOpportunityApplicantTaskDTO>() {});
        OpportunityApplicantTaskDTO opportunityApplicantTaskDTO = opportunityApplicantTaskEventMapper.fromEventDTO(customOpportunityApplicantTaskDTO);
        opportunityApplicantTaskFacade.deleteOpportunityApplicantTaskInApplicant(opportunityApplicantTaskDTO);
        log.info("Consumed DELETED OpportunityApplicantTask for applicantId {}", customOpportunityApplicantTaskDTO.getOpportunityApplicantId());
    }

}