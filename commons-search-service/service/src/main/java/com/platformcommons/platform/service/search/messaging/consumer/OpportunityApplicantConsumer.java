package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.changemaker.dto.AttendanceRequestDTO;
import com.platformcommons.platform.service.changemaker.dto.OpportunityApplicantDTO;
import com.platformcommons.platform.service.changemaker.dto.RecruiterAndCommitteeAssignDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.OpportunityApplicantService;
import com.platformcommons.platform.service.search.domain.GenericProductVariety;
import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
import com.platformcommons.platform.service.search.facade.assembler.OpportunityApplicantDTOAssembler;
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
public class OpportunityApplicantConsumer {

    @Autowired
    private OpportunityApplicantEventMapper mapper;

    @Autowired
    private OpportunityApplicantService service;

    @Autowired
    private OpportunityApplicantFacade opportunityApplicantFacade;

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_CREATE,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityApplicantCreated(@Payload EventDTO<OpportunityApplicantDTO> eventDTO){
        OpportunityApplicantDTO opportunityApplicantDTO = eventDTO.getPayload(new TypeReference<OpportunityApplicantDTO>() {});
        OpportunityApplicant opportunityApplicant = mapper.fromEventDTO(opportunityApplicantDTO);
        opportunityApplicantFacade.createOrUpdateOpportunityApplicant(opportunityApplicant);
        log.info("OpportunityApplicantDTO consumed and created with id >>>>>>>>>>>>>>>>>>> " + opportunityApplicant.getId());
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_UPDATE,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityApplicantUpdated(@Payload EventDTO<OpportunityApplicantDTO> eventDTO){
        OpportunityApplicantDTO opportunityApplicantDTO = eventDTO.getPayload(new TypeReference<OpportunityApplicantDTO>() {});
        OpportunityApplicant opportunityApplicant = mapper.fromEventDTO(opportunityApplicantDTO);
        opportunityApplicantFacade.createOrUpdateOpportunityApplicant(opportunityApplicant);
        log.info("OpportunityApplicantDTO consumed and updated with id >>>>>>>>>>>>>>>>>>> " + opportunityApplicant.getId());
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_UPDATE_BULK,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityApplicantUpdatedInBulk(@Payload EventDTO<List<OpportunityApplicantDTO>> eventDTO){
        List<OpportunityApplicantDTO> opportunityApplicantDTOList = eventDTO.getPayload(new TypeReference<List<OpportunityApplicantDTO>>() {});
        for(OpportunityApplicantDTO opportunityApplicantDTO : opportunityApplicantDTOList) {
            OpportunityApplicant opportunityApplicant = mapper.fromEventDTO(opportunityApplicantDTO);
            opportunityApplicantFacade.createOrUpdateOpportunityApplicant(opportunityApplicant);
            log.info("OpportunityApplicantDTO consumed and updated with id >>>>>>>>>>>>>>>>>>> " + opportunityApplicant.getId());
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_DELETE,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void opportunityApplicantDeleted(@Payload EventDTO<Long> id){
        Long opportunityApplicantId = id.getPayload(new TypeReference<Long>() {});
        service.delete(opportunityApplicantId);
        log.info("OpportunityApplicantDTO consumed and deleted with id >>>>>>>>>>>>>>>>>>> " + opportunityApplicantId);
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_SYNC,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void opportunityApplicantSync(@Payload EventDTO<OpportunityApplicantDTO> eventDTO){
        OpportunityApplicantDTO opportunityApplicantDTO = eventDTO.getPayload(new TypeReference<OpportunityApplicantDTO>() {});
        OpportunityApplicant opportunityApplicant = mapper.fromEventDTO(opportunityApplicantDTO);
        opportunityApplicantFacade.syncOpportunityApplicant(opportunityApplicant);
        log.info("OpportunityApplicantDTO consumed and synced with id >>>>>>>>>>>>>>>>>>> " + opportunityApplicant.getId());
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_ATTENDANCE_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityApplicantAttendanceUpdated(@Payload EventDTO<AttendanceRequestDTO> eventDTO){
        AttendanceRequestDTO attendanceRequestDTO = eventDTO.getPayload(new TypeReference<AttendanceRequestDTO>() {});
        opportunityApplicantFacade.updateAttendanceStatus(attendanceRequestDTO.getOpportunityApplicantId(),
                attendanceRequestDTO.getAttendanceStatus());
        log.info("AttendanceRequestDTO consumed and updated with id >>>>>>>>>>>>>>>>>>> " +
                attendanceRequestDTO.getOpportunityApplicantId());
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_UPDATE_ON_RECRUITER_OR_COMMITTEE_ASSIGNED_BULK,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityApplicantUpdatedForRecruiterOrCommitteeAssignedInBulkUpdated(@Payload EventDTO<List<RecruiterAndCommitteeAssignDTO>> eventDTO){
        List<RecruiterAndCommitteeAssignDTO> recruiterAndCommitteeAssignDTOS = eventDTO.getPayload(new TypeReference<List<RecruiterAndCommitteeAssignDTO>>() {});
        for (RecruiterAndCommitteeAssignDTO recruiterAndCommitteeAssignDTO : recruiterAndCommitteeAssignDTOS) {
            opportunityApplicantFacade.updateRecruiterOrCommitteeAssignedInBulk(recruiterAndCommitteeAssignDTO);
            log.info("RecruiterAndCommitteeAssignDTO consumed and updated with id >>>>>>>>>>>>>>>>>>> " + recruiterAndCommitteeAssignDTO.getOpportunityApplicantId());
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_ATTENDANCE_UPDATED_BULK,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public  void opportunityApplicantAttendanceUpdatedInBulk(@Payload EventDTO<Set<AttendanceRequestDTO>> eventDTO){
        Set<AttendanceRequestDTO> attendanceRequestDTOSet = eventDTO.getPayload(new TypeReference<Set<AttendanceRequestDTO>>() {});
        for(AttendanceRequestDTO attendanceRequestDTO : attendanceRequestDTOSet) {
            opportunityApplicantFacade.updateAttendanceStatus(attendanceRequestDTO.getOpportunityApplicantId(),
                    attendanceRequestDTO.getAttendanceStatus());
            log.info("AttendanceRequestDTO consumed and updated with id >>>>>>>>>>>>>>>>>>> " + attendanceRequestDTO.getOpportunityApplicantId());
        }
    }

}