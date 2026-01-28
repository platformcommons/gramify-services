package com.platformcommons.platform.service.report.facade.client;

import com.platformcommons.platform.service.report.dto.LoginRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
@FeignClient(name = "commons-iam-service${commons.service.commons-iam-service.context-path:/commons-iam-service}", contextId = "ReportIAMClient", url = "${commons.service.commons-iam-service.url:}")
public interface IAmClient {

    @PostMapping("api/v1/obo/login")
    ResponseEntity getTLDSessionId(@RequestBody LoginRequestDTO loginRequestDTO,
                                   @RequestHeader(value = "X-APPID", required = false)String appId,
                                   @RequestHeader(value = "X-APPVERSION",required = false)String appVersion,
                                   @RequestHeader(value = "X-APPKEY", required = false)String appKey,
                                   @RequestHeader(value = "X-DEVICE-ID",required = false)String deviceId);

}
