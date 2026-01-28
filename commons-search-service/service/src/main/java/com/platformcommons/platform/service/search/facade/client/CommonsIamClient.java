package com.platformcommons.platform.service.search.facade.client;

import com.platformcommons.platform.service.iam.dto.TenantMetaConfigDTO;
import com.platformcommons.platform.service.iam.dto.UserConsentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;


@FeignClient(name = "commons-iam-service${commons.service.commons-iam-service.context-path:/commons-iam-service}",
        contextId = "commons-iam-client")
public interface CommonsIamClient {

    @GetMapping(value = "/api/v1/tenant-meta-config/cache")
    ResponseEntity<TenantMetaConfigDTO> getTenantMetaConfigByTenantLoginAndLoadCache(@NotNull @Valid @RequestParam(value = "tenantLogin" ) String tenantLogin,
                                                                                  @RequestHeader("Authorization") String apikey);
}