package com.platformcommons.platform.service.post.facade.client;

import com.platformcommons.platform.service.post.dto.UserDetailsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "commons-report-service${commons.report-service.context-path:/commons-report-service}",
        url="${commons.platform.ms.base-url}${commons.platform.ms.report-service.context-path}",
        contextId = "commons-post-service")
public interface CommonsReportClient {
    @RequestMapping(
            value = "/api/v1/datasets/name/VMS_USER_DETAILS/execute",
            method = RequestMethod.GET)
    ResponseEntity<List<UserDetailsDTO>> getUserDetails(@RequestHeader("X-SESSIONID") String sessionId,
                                                  @RequestParam(value = "params") String params);
}
