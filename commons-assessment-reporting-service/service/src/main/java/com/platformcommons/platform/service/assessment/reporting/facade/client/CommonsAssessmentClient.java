package com.platformcommons.platform.service.assessment.reporting.facade.client;

import com.platformcommons.platform.constant.PlatformSecurityConstant;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;


@FeignClient(name = "commons-assessment-service${commons-assessment-service.context-path:/commons-assessment-service}",contextId = "CommonsAssessmentService")
public interface CommonsAssessmentClient {
    @GetMapping(value = "/api/v1/assessments/context/{assessmentId}")
    AssessmentSyncContext getContext(@PathVariable Long assessmentId, @RequestHeader("X-SESSIONID") String sessionId);

    @GetMapping(value = "/api/v1/assessmentInstanceAssesses/assessments/{assessmentId}",produces = "application/json")
    Set<AssessmentInstanceAssesseDTO> getAssesses(@PathVariable Long assessmentId,
                                                  @Min(value = 0,message = "Page should be greater than zero") @RequestParam(required = false) Integer page,
                                                  @Max(value = 100,message = "Size Should not be greater than Hundred") @RequestParam(required = false) Integer size,
                                                  @RequestHeader(PlatformSecurityConstant.SESSIONID) String sessionId);


    @GetMapping(value = "/api/v2/assessmentinstances/{assessmentInstanceId}")
    AssessmentInstanceDTO getAssessmentInstanceByIdV2(@PathVariable Long assessmentInstanceId,@RequestHeader(PlatformSecurityConstant.SESSIONID) String token);

    @GetMapping(value = "/api/v3/assessmentinstances")
    AssessmentInstanceDTO getAssessmentInstanceByIdV3(@RequestParam Long assessmentInstanceId,@RequestHeader("Authorization") String authorization);

}
