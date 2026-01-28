package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.dto.event.EventDTO;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.facade.UserFacade;
import com.platformcommons.platform.service.worknode.dto.WorkforceDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@ConditionalOnProperty(name = "commons.platform.event.enabled", havingValue = "true")
@Slf4j
public class WorknodeConsumer {

    @Autowired
    private UserFacade userFacade;

    @Transactional
    @KafkaListener(topics = MessagingConstant.COMMONS_WORKFORCE_CREATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void workforceCreated(@Payload EventDTO<WorkforceDTO> eventDTO){
        WorkforceDTO workforceDTO = eventDTO.getPayload(new TypeReference<WorkforceDTO>() {});
        if(workforceDTO != null && workforceDTO.getUserId() != null) {
            userFacade.updateWorkForceToAnUser(workforceDTO);
            log.info(String.format("WorkForce Added To User With id: %d ",workforceDTO.getUserId()));
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.COMMONS_WORKFORCE_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_LISTENER_FACTORY)
    public  void workforceUpdated(@Payload EventDTO<WorkforceDTO> eventDTO){
        WorkforceDTO workforceDTO = eventDTO.getPayload(new TypeReference<WorkforceDTO>() {});
        if(workforceDTO != null && workforceDTO.getUserId() != null) {
            userFacade.updateWorkForceToAnUser(workforceDTO);
            log.info(String.format("WorkForce Updated To User With id: %d ",workforceDTO.getUserId()));
        }
    }

}
