package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.changemaker.dto.ApplicantAssessmentDataDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.facade.ApplicantAssessmentDataFacade;
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
public class ApplicantAssessmentDataConsumer {

    @Autowired
    private OpportunityApplicantFacade opportunityApplicantFacade;

    @Autowired
    private ApplicantAssessmentDataFacade applicantAssessmentDataFacade;

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_ASSESSMENT_DATA_POST, groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void applicantAssessmentDataPost(@Payload EventDTO<ApplicantAssessmentDataDTO> eventDTO) {
        ApplicantAssessmentDataDTO assessmentDataDTO = eventDTO.getPayload(new TypeReference<ApplicantAssessmentDataDTO>() {});
        applicantAssessmentDataFacade.addOrUpdateApplicantAssessmentResponse(assessmentDataDTO);
        log.info("ApplicantAssessmentData consumed for applicant id  >>>>>>>>>>>>>>>>>>> " + assessmentDataDTO.getApplicantId());
    }
}
