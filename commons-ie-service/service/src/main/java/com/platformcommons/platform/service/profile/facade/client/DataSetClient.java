package com.platformcommons.platform.service.profile.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "commons-report-service${commons.service.commons-report-service.context-path:/commons-report-service}",contextId = "DatasetClient")
public interface DataSetClient {

    @GetMapping("/api/v3/datasets/name/{Query}/execute")
    List<Map<String,Object>> executeQueryV3(@PathVariable("Query") String query,
                                            @RequestParam(value = "params",required = false) String params,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("size") Integer size,
                                            @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId
    );


}