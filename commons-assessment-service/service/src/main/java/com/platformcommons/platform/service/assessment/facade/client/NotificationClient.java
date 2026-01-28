package com.platformcommons.platform.service.assessment.facade.client;

import com.platformcommons.platform.service.assessment.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

@FeignClient(name = "notification-service",  url="${commons.platform.ms.base-url}")
public interface NotificationClient {


    @RequestMapping(value = "gateway/notification-service/api/v1/notification", method = RequestMethod.POST)
    ResponseEntity<String>  sendNotification(@Valid @RequestBody NotificationDTO notificationDTO, @RequestHeader("Authorization") String authorisation);
    @RequestMapping(value = "gateway/notification-service/api/v1/notification", method = RequestMethod.POST)
    ResponseEntity<String> sendNotificationWithSessionId(@Valid @RequestBody NotificationDTO notificationDTO, @RequestHeader("X-SESSIONID") String sessionId);

}
