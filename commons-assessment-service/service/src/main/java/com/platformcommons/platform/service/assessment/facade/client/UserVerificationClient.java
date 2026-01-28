package com.platformcommons.platform.service.assessment.facade.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "commons-iam-service${common-iam-service.context-path:/commons-iam-service}", contextId = "Commons-Iam-Client")
public interface UserVerificationClient {

    @GetMapping("/api/v1/user/verificaton/")
    ResponseEntity<Boolean> isVerified(@RequestHeader("X-SESSIONID") String sessionId);

}
