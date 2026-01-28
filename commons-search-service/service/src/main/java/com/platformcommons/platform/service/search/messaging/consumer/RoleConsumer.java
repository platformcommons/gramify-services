package com.platformcommons.platform.service.search.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtree.bridge.platform.dto.UserRoleMapDTO;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.search.application.constant.MessagingConstant;
import com.platformcommons.platform.service.search.facade.UserFacade;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Component
@ConditionalOnProperty(name = "commons.platform.event.enabled", havingValue = "true")
@Slf4j
public class RoleConsumer {

    @Autowired
    private UserFacade userFacade;

    @Transactional
    @KafkaListener(topics = MessagingConstant.BRBASE_USER_NEW_ROLE_ASSIGNED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_STRING_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void assignRole(@Payload String payload) {
        try {
            String  userRoleMapDTOPayload = (String)new ObjectMapper().readValue(payload, Map.class).get("requestPayload");
            UserRoleMapDTO userRoleMapDTO =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(userRoleMapDTOPayload ,UserRoleMapDTO.class);
            if(userRoleMapDTO != null && userRoleMapDTO.getUser() != null && userRoleMapDTO.getUser().getId() != null
                 && userRoleMapDTO.getRole() != null && userRoleMapDTO.getRole().getCode() != null) {
                userFacade.assignRoleToUser(userRoleMapDTO);
                log.info(String.format("Role Assigned For User: %d",userRoleMapDTO.getUser().getId()));
            }
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.BRBASE_USER_ROLE_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_STRING_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void updateRole(@Payload String payload) {
        try {
            String  userRoleMapDTOPayload = (String)new ObjectMapper().readValue(payload, Map.class).get("requestPayload");
            UserRoleMapDTO userRoleMapDTO =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(userRoleMapDTOPayload ,UserRoleMapDTO.class);
            if(userRoleMapDTO != null && userRoleMapDTO.getUser() != null && userRoleMapDTO.getUser().getId() != null
                    && userRoleMapDTO.getRole() != null && userRoleMapDTO.getRole().getCode() != null) {
                userFacade.assignRoleToUser(userRoleMapDTO);
                log.info(String.format("Role Assigned For User: %d",userRoleMapDTO.getUser().getId()));
            }
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @KafkaListener(topics = MessagingConstant.BRBASE_USER_ROLE_DELETED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_STRING_LISTENER_FACTORY,
            concurrency = "${commons.platform.event.listener.concurrency:5}")
    public void deleteRole(@Payload String payload) {
        try {
            String  userRoleMapDTOPayload = (String)new ObjectMapper().readValue(payload, Map.class).get("requestPayload");
            UserRoleMapDTO userRoleMapDTO =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(userRoleMapDTOPayload ,UserRoleMapDTO.class);
            if(userRoleMapDTO != null && userRoleMapDTO.getUser() != null && userRoleMapDTO.getUser().getId() != null
                    && userRoleMapDTO.getRole() != null && userRoleMapDTO.getRole().getCode() != null) {
                userFacade.deleteRoleFromUser(userRoleMapDTO);
                log.info(String.format("Role Deleted For User: %d",userRoleMapDTO.getUser().getId()));
            }
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
