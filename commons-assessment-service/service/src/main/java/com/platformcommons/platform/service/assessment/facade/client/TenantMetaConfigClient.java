package com.platformcommons.platform.service.assessment.facade.client;

import com.platformcommons.platform.service.assessment.dto.TenantMetaConfigDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "commons-iam-service${common-iam-service.context-path:/commons-iam-service}", contextId = "Commons-Iam-Client")
public interface TenantMetaConfigClient {

    @GetMapping("/api/v1/tenant-meta-config/")
    TenantMetaConfigDTO getTenantMetaConfig(@RequestHeader("X-SESSIONID") String sessionId);

}