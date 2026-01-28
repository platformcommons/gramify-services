package com.platformcommons.platform.service.assessment.reporting.facade.client;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "ltld-client",url="${commons-ltld.url:https://dev.platformcommons.org/ltld}")
public interface LTLDClient {

//    @GetMapping("/api/v1/assessments")
//    List<AssessmentDTO> getAssessmentDtoList(@RequestHeader(PlatformSecurityConstant.SESSIONID) String token,
//                                             @RequestParam String criteria);


}
