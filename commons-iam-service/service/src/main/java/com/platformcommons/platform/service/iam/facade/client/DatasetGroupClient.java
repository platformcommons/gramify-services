package com.platformcommons.platform.service.iam.facade.client;


import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.report.dto.TenantInfoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@FeignClient(name = "commons-report-service${commons.service.commons-report-service.context-path:/commons-report-service}",
        contextId = "DatasetGroupClient")
public interface DatasetGroupClient {


    @RequestMapping(value = "/api/v1/dataset-groups/tenant-info/{datasetGroupCode}", method = RequestMethod.POST)
    ResponseEntity<Void> addTenantInfoToDatasetGroupCode(@PathVariable("datasetGroupCode") String datasetGroupCode,
                                                         @Valid @RequestBody TenantInfoDTO body,
                                                         @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);



}
