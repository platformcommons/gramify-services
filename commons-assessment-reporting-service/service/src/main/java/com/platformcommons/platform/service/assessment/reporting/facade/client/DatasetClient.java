package com.platformcommons.platform.service.assessment.reporting.facade.client;

import com.platformcommons.platform.service.assessment.dto.ResolvedHierarchyDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "commons-report-service${common-report-service.context-path:/commons-report-service}",
        contextId = "Commons-Report-Client")
public interface DatasetClient {

    @GetMapping("/api/v3/datasets/name/{Query}/execute")
    List<Map<String,Object>> executeQueryV3(@PathVariable("Query") String query,
                                            @RequestParam("params") String params,
                                            @RequestParam("page") Integer page,
                                            @RequestParam("size") Integer size,
                                            @RequestHeader("X-SESSIONID") String sessionId
    );
    @GetMapping("/api/v3/datasets/name/{Query}/execute")
    List<Map<String,Object>> executeQueryV3AppKey(@PathVariable("Query") String query,
                                                  @RequestParam("params") String params,
                                                  @RequestParam("page") Integer page,
                                                  @RequestParam("size") Integer size,
                                                  @RequestHeader("Authorization") String appkey
    );

    @GetMapping("/api/v1/datasets/name/{Query}/execute")
    ResponseEntity<List<ResolvedHierarchyDTO>> resolvedValue(@PathVariable("Query") String query,
                                                            @RequestParam("params") String params,
                                                            @RequestHeader("X-SESSIONID") String sessionId
    );
}
