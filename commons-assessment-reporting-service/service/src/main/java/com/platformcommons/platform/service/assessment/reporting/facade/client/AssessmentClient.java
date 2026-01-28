package com.platformcommons.platform.service.assessment.reporting.facade.client;

import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Set;

@FeignClient(name = "assessment-service${igx-assessment-service.context-path:/assessment-service}", contextId = "Assessment-Client")
public interface AssessmentClient {

    @GetMapping(value = "/api/v1/questions",produces = "application/json")
    Set<QuestionDTO> getQuestionById(@RequestParam Set<Long> ids, @RequestHeader("Authorization") String appKey);

    @GetMapping(value = "/api/v1/assessments/context/{assessmentId}",produces = "application/json" )
    AssessmentSyncContext getAssessmentContext(@PathVariable Long assessmentId, @RequestHeader("X-SESSIONID") String sessionId);

    @GetMapping(value = "/api/v1/assessmentInstanceAssesses/assessments/{assessmentId}",produces = "application/json")
    Set<AssessmentInstanceAssesseDTO> getAssesses(@PathVariable Long assessmentId,
                                                      @Min(value = 0,message = "Page should be greater than zero") @RequestParam(required = false) Integer page,
                                                      @Max(value = 100,message = "Size Should not be greater than Hundred") @RequestParam(required = false) Integer size,
                                                      @RequestHeader("X-SESSIONID") String sessionId);

    @GetMapping(value = "/api/v1/assessmentInstanceAssesses/assessments/count/{assessmentId}",produces = "application/json")
    Long getAssessmentInstanceAssesseCount(@PathVariable Long assessmentId,
                                           @RequestHeader("X-SESSIONID") String sessionId);

}
