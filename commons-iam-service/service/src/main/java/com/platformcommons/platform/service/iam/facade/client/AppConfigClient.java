package com.platformcommons.platform.service.iam.facade.client;


import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.app.dto.AppConfigDTO;
import com.platformcommons.platform.service.app.dto.AppRbacDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Set;

@FeignClient(name = "commons-app-config-service${commons.service.commons-app-config-service.context-path:/commons-app-config-service}",
        contextId = "AppConfigClient")
public interface AppConfigClient {

    @PostMapping("/api/v1/app-version/{appVersionId}/app-config")
    ResponseEntity<Void> postApiV1AppConfigAdd(@PathVariable("appVersionId") Long appVersionId,
                                               @Valid @RequestBody Set<AppConfigDTO> body,
                                               @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);

    @GetMapping("/api/v1/app-rbac/primary/app-code/{appCode}")
    ResponseEntity<Void> getOrCreatePrimaryRbacByAppCodeAndMarketContext(@PathVariable("appCode") String appCode,
                                                                               @Valid @RequestParam(value = "marketContext", required = false) String marketContext,
                                                                               @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);



}
