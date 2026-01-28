package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.changemaker.dto.RuleResultCustomDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.application.service.OpportunityApplicantService;
import com.platformcommons.platform.service.search.domain.RuleResult;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
import com.platformcommons.platform.service.search.messaging.mapper.RuleResultEventMapper;
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
public class RuleResultConsumer {

    @Autowired
    private OpportunityApplicantFacade opportunityApplicantFacade;

    @Autowired
    private RuleResultEventMapper ruleResultEventMapper;

    @Transactional
    @KafkaListener(topics = MessagingConstant.OPPORTUNITY_APPLICANT_RULE_RESULT_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void ruleResultCreated(@Payload EventDTO<RuleResultCustomDTO> eventDTO){
        RuleResultCustomDTO ruleResultCustomDTO = eventDTO.getPayload(new TypeReference<RuleResultCustomDTO>() {});
        RuleResult ruleResult = ruleResultEventMapper.fromEventDTO(ruleResultCustomDTO);
        opportunityApplicantFacade.addRuleResultInApplicant(ruleResult);
        log.info("RuleResultCustomDTO consumed for applicant id  >>>>>>>>>>>>>>>>>>> " + ruleResultCustomDTO.getAppCreatedAt());
    }

}