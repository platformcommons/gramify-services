package com.platformcommons.platform.service.post.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.iam.dto.TenantDTO;
import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;


@FeignClient(name = "commons-iam-service${commons.service.commons-iam-service.context-path:/commons-iam-service}",
        contextId = "Commons-Iam-Client")
public interface CommonsIamClient {

    @RequestMapping(
            value = "/api/v1/tenants/{id}",
            method = RequestMethod.GET)
    ResponseEntity<TenantDTO> getTenantById(@RequestParam Long id,
                                            @RequestHeader(name = PlatformSecurityConstant.SESSIONID) String sessionId);

    @GetMapping(value = "/api/v1/tenant-meta-config/cache")
    ResponseEntity<TenantMetaConfigDTO> getTenantMetaConfigByTenantLoginAndLoadCache(@NotNull @Valid @RequestParam(value = "tenantLogin") String tenantLogin,
                                                                                     @RequestHeader("Authorization") String apikey);

    @GetMapping(value = "/api/v1/tenant/cache")
    ResponseEntity<TenantDTO> getTenantByTenantIdAndLoadCache(@NotNull @Valid @RequestParam(value = "tenantId") Long tenantId,
                                                              @RequestHeader("Authorization") String apikey);

}
