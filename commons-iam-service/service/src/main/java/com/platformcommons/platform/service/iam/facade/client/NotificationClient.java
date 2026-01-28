package com.platformcommons.platform.service.iam.facade.client;


import com.fasterxml.jackson.databind.JsonNode;
import com.platformcommons.platform.service.iam.messaging.model.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "notification-service", contextId = "NotificationClient",
        url="${commons.platform.ms.base-url}${commons.platform.ms.notification-service.context-path}")
public interface NotificationClient {

    @RequestMapping(value = "/api/v1/notification", method = RequestMethod.POST,
            consumes = "application/json")
    String sendNotification(@Valid @RequestBody NotificationDTO body,
                            @RequestHeader("Authorization") String tokenOrApiKey);

    @RequestMapping(value = "/api/v1/notification", method = RequestMethod.POST,
            consumes = "application/json")
    String sendNotificationWithSessionId(@Valid @RequestBody NotificationDTO body,
                                         @RequestParam String marketId,
                                         @RequestHeader("X-SESSIONID") String sessionId);

    @RequestMapping(value = "/api/v1/notification/otp/validate", method = RequestMethod.POST,
            consumes = "application/json")
    JsonNode validateOTP(@RequestParam(name = "messageId") String key,
                         @RequestParam(name = "otp") String otp,
                         @RequestHeader("Authorization") String apiKey);

}
