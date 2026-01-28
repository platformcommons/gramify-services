package com.platformcommons.platform.service.profile.facade.client;

import com.google.gson.JsonObject;
import com.platformcommons.platform.constant.PlatformSecurityConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(
        name = "commons-worknode-service${commons.service.commons-worknode-service.context-path:/commons-worknode-service}",
        contextId = "WorkNodeClient"
)
public interface WorkNodeClient {

    @PostMapping("/api/v1/workforce")
    Long mapABAToWorkForce(@RequestParam Long worknodeId, @RequestBody Map<String,Object> gson, @RequestHeader( PlatformSecurityConstant.SESSIONID)String sessionId);

}