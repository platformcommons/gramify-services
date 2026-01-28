package com.platformcommons.platform.service.app.facade.client;

import com.platformcommons.platform.service.app.dto.RoleDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "commons-report-service${commons.service.commons-report-service.context-path:/commons-report-service}",
        contextId = "commons-report-client")
public interface CommonsReportClient {

    @RequestMapping(
            value = "/api/v1/datasets/name/VMS_GET_TENANT_ROLES/execute",
            method = RequestMethod.GET)
    ResponseEntity<List<RoleDTO>> getTenantRolesByTenantId(@RequestParam(name = "params") String params,
                                                           @RequestHeader("X-SESSIONID") String sessionId);
}
