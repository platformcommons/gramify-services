package com.platformcommons.platform.service.app.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.AppRbacService;
import com.platformcommons.platform.service.app.domain.repo.AppRbacRepository;
import com.platformcommons.platform.service.app.dto.RoleDTO;
import com.platformcommons.platform.service.app.messaging.MessagingConstant;
import com.platformcommons.platform.service.constant.ServiceConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class BRCTLDTenantRoleConsumer {

    @Autowired
    AppRbacService appRbacService;

    @Autowired
    AppRbacRepository appRbacRepository;

    @KafkaListener(topics = MessagingConstant.BRBASE_TENANT_ROLE_UPDATED, groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_STRING_LISTENER_FACTORY)
    void updateAppRbacWhenRoleUpdatesUsingKafka(@Payload String payload) {
        try {
            String rolePayload = (String) new ObjectMapper().readValue(payload, Map.class).get("requestPayload");
            String tenantContext = (String) new ObjectMapper().readValue(payload, Map.class).get("tenantContext");
            String userContext = (String) new ObjectMapper().readValue(payload, Map.class).get("userContext");
            PlatformSecurityUtil.setEventTLDContext(userContext, tenantContext, "ACTOR_TYPE.BRIDGE_USER", null);

            RoleDTO roleDTO = new ObjectMapper().readValue(rolePayload, new TypeReference<RoleDTO>() {
            });
            if (roleDTO.getNotes() != null && !roleDTO.getNotes().isEmpty()) {
                Set<String> appCodes = Stream.of("CM-ADMIN", "IAM" , "PS3" ).collect(Collectors.toSet());
                appRbacService.updateRbacPriorityByRoleAndAppCodes(appCodes, roleDTO.getCode(), Integer.valueOf(roleDTO.getNotes()));
            }
        } catch (JsonProcessingException e) {
            log.error(String.format("Error occurred while updating appRoleBac - %s", ExceptionUtils.getFullStackTrace(e)));
            throw new RuntimeException();
        }
    }

}