package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.changemaker.dto.ApplicantOwnerResultCustomDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.domain.ApplicantOwnerResult;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
import com.platformcommons.platform.service.search.messaging.mapper.ApplicantOwnerResultEventMapper;
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
public class ApplicantOwnerResultConsumer {

    @Autowired
    private OpportunityApplicantFacade opportunityApplicantFacade;

    @Autowired
    private ApplicantOwnerResultEventMapper applicantOwnerResultEventMapper;

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_OWNER_RESULT_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void applicantOwnerResultCreated(@Payload EventDTO<ApplicantOwnerResultCustomDTO> eventDTO){
        ApplicantOwnerResultCustomDTO applicantOwnerResultCustomDTO = eventDTO.getPayload(new TypeReference<ApplicantOwnerResultCustomDTO>() {});
        ApplicantOwnerResult applicantOwnerResult = applicantOwnerResultEventMapper.fromEventDTO(applicantOwnerResultCustomDTO);
        opportunityApplicantFacade.addOrUpdateApplicantOwnerResultInApplicant(applicantOwnerResult);
        log.info("RuleResultCustomDTO consumed for applicant id  >>>>>>>>>>>>>>>>>>> " + applicantOwnerResultCustomDTO.getAppCreatedAt());
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_OWNER_RESULT_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void applicantOwnerResultUpdated(@Payload EventDTO<ApplicantOwnerResultCustomDTO> eventDTO){
        ApplicantOwnerResultCustomDTO applicantOwnerResultCustomDTO = eventDTO.getPayload(new TypeReference<ApplicantOwnerResultCustomDTO>() {});
        ApplicantOwnerResult applicantOwnerResult = applicantOwnerResultEventMapper.fromEventDTO(applicantOwnerResultCustomDTO);
        opportunityApplicantFacade.addOrUpdateApplicantOwnerResultInApplicant(applicantOwnerResult);
        log.info("RuleResultCustomDTO consumed for applicant id  >>>>>>>>>>>>>>>>>>> " + applicantOwnerResultCustomDTO.getAppCreatedAt());
    }

}