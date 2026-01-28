package com.platformcommons.platform.service.post.facade.client;

import com.platformcommons.platform.service.post.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@FeignClient(name = "notification-service", contextId = "NotificationClient", url="${commons.platform.ms.base-url}${commons.platform.ms.notification-service.context-path}")
public interface NotificationClient {

    @RequestMapping(value = "/api/v1/notification", method = RequestMethod.POST)
    ResponseEntity<String> sendNotification(@Valid @RequestBody NotificationDTO body, @RequestHeader("X-SESSIONID") String sessionId);

    @RequestMapping(value = "/api/v1/notification", method = RequestMethod.POST)
    ResponseEntity<String> sendNotificationWithAuthorizationHeader(@Valid @RequestBody NotificationDTO body, @RequestHeader("Authorization") String tokenOrApiKey);

}