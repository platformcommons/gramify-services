package com.platformcommons.platform.service.report.facade.client;


import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.report.dto.NotificationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
@FeignClient(name = "notification-service",contextId = "ReportNotificationClient",url = "${commons.platform.ms.base-url}${commons.platform.ms.notification-service.context-path}")
public interface NotificationClient {

    @PostMapping("/api/v1/notification")
    String sendNotification(@RequestBody NotificationDTO notificationDTO, @RequestHeader(PlatformSecurityConstant.SESSIONID) String token);


}
