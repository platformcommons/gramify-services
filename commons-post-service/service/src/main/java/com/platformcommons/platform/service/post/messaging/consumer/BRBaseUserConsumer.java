package com.platformcommons.platform.service.post.messaging.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mindtree.bridge.platform.dto.UserDTO;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.constant.ServiceConstant;
import com.platformcommons.platform.service.post.application.PostActorService;
import com.platformcommons.platform.service.post.messaging.MessagingConstant;
import com.platformcommons.platform.service.post.messaging.MessagingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class BRBaseUserConsumer {

    @Autowired
    private MessagingUtil messagingUtil;

    @Autowired
    private PostActorService postActorService;

    @KafkaListener(topics = MessagingConstant.BRBASE_USER_UPDATED,groupId = MessagingConstant.GROUP_ID,
            containerFactory = ServiceConstant.KAFKA_STRING_LISTENER_FACTORY)
    public void  consumeBRBaseUserUpdate(@Payload String payload){

        try {

            String  userPayload = (String)new ObjectMapper().readValue(payload, Map.class).get("requestPayload");
            String tenantContext = (String)new ObjectMapper().readValue(payload,Map.class).get("tenantContext");
            String useContext = (String)new ObjectMapper().readValue(payload,Map.class).get("userContext");
            messagingUtil.setContext(useContext,tenantContext);
            UserDTO userDTO =  new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).readValue(userPayload ,UserDTO.class);
            postActorService.updatePostActorDetails(userDTO);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
