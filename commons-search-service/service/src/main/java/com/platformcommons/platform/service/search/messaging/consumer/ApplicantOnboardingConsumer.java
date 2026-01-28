package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.changemaker.dto.CustomApplicantOnboardingDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
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
public class ApplicantOnboardingConsumer {

    @Autowired
    private OpportunityApplicantFacade opportunityApplicantFacade;

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_ONBOARDING_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void applicantOnboardingCreated(@Payload EventDTO<CustomApplicantOnboardingDTO> eventDTO){
        CustomApplicantOnboardingDTO customApplicantOnboardingDTO = eventDTO.getPayload(new TypeReference<CustomApplicantOnboardingDTO>() {});
        opportunityApplicantFacade.addApplicantOnboardingDetailsInApplicant(customApplicantOnboardingDTO);
        log.info("CustomApplicantOnboardingDTO consumed for applicant id  >>>>>>>>>>>>>>>>>>> " + customApplicantOnboardingDTO.getOpportunityApplicantId());
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_ONBOARDING_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void applicantOnboardingUpdated(@Payload EventDTO<CustomApplicantOnboardingDTO> eventDTO){
        CustomApplicantOnboardingDTO customApplicantOnboardingDTO = eventDTO.getPayload(new TypeReference<CustomApplicantOnboardingDTO>() {});
        opportunityApplicantFacade.addApplicantOnboardingDetailsInApplicant(customApplicantOnboardingDTO);
        log.info("CustomApplicantOnboardingDTO consumed for applicant id  >>>>>>>>>>>>>>>>>>> " + customApplicantOnboardingDTO.getOpportunityApplicantId());
    }

}