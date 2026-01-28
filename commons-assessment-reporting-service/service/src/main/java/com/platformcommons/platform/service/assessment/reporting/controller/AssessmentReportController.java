package com.platformcommons.platform.service.assessment.reporting.controller;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.platformcommons.platform.service.assessment.dto.AssessmentPivotReportZipDTO;
import com.platformcommons.platform.service.assessment.dto.SectionQuestionResponseDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.ReportFacade;
import com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler.OptionResponseHandler;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.*;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "AssessmentReportController")
public class AssessmentReportController {

    private final ReportFacade reportFacade;
    private final OptionResponseHandler processor;
    @ApiOperation(value="getOptionResponses",tags = {"AssessmentReportController"})
    @GetMapping(path = "/api/v1/assessmentresponsereport/question-responses/{assessmentInstanceId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<ResponseEntity<List<SectionQuestionResponseDTO>>>> getOptionResponses(@PathVariable Long assessmentInstanceId) {
        return Flux.<List<SectionQuestionResponseDTO>>create(sink -> processor.subscribe(sink::next,assessmentInstanceId))
                .log()
                .map(el-> ServerSentEvent.<ResponseEntity<List<SectionQuestionResponseDTO>>>builder()
                        .event("question-responses")
                        .data( !el.isEmpty() ? ResponseEntity.ok(el):ResponseEntity.noContent().build())
                        .build());
    }

    @ApiOperation(value="getAssessmentReportingQuery",tags = {"AssessmentReportController"})
    @GetMapping(value = "/api/v1/assessmentresponsereport/assessment-reporting-query", produces = "application/zip" )
    public ResponseEntity<?> getAssessmentReportingQuery(@RequestParam Set<Long> assessmentInstanceId,
                                                         @RequestParam(required = false) String delimiter,
                                                         @RequestParam String language) throws Exception {

        AssessmentPivotReportZipDTO resource = reportFacade.getAssessmentReportQuery(assessmentInstanceId,delimiter,language);

        final String fileName = (String.format("%s_%s_%s.zip",assessmentInstanceId.size()==1?resource.getInstanceName():"AssessmentReport_", LocalDateTime.now().toLocalDate(),LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)))
                .replaceAll("[- *]", "_");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData( "attachment", fileName);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource.getBytes());
    }

    @ApiOperation(value="getAssessmentReportingQueryV5",tags = {"AssessmentReportController"})
    @GetMapping(value = "/api/v5/assessmentresponsereport/assessment-reporting-query", produces = "application/zip" )
    public ResponseEntity<?> getAssessmentReportingQueryV5(@RequestParam Set<Long> assessmentInstanceId,
                                                         @RequestParam(required = false) String delimiter,
                                                         @RequestParam String language) throws Exception {

        AssessmentPivotReportZipDTO resource = reportFacade.getAssessmentReportQueryV5(assessmentInstanceId,delimiter,language);

        final String fileName = (String.format("%s_%s_%s.zip",assessmentInstanceId.size()==1?resource.getInstanceName():"AssessmentReport_", LocalDateTime.now().toLocalDate(),LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)))
                .replaceAll("[- *]", "_");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData( "attachment", fileName);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource.getBytes());
    }

    @ApiOperation(value="getAssessmentReportingQueryV2",tags = {"AssessmentReportController"})
    @GetMapping(value = "/api/v2/assessmentresponsereport/assessment-reporting-query", produces = "application/zip" )
    public ResponseEntity<?> getAssessmentReportingQueryV2(@RequestParam Long assessmentInstanceId,
                                                         @RequestParam(required = false) String delimiter,
                                                         @RequestParam(required = false) Set<Long> aiaIds,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "YYYY-MM-DD") Date startDate,
                                                         @RequestParam(required = false) @DateTimeFormat(pattern = "YYYY-MM-DD") Date endDate,
                                                         @RequestParam String language) throws Exception {

        AssessmentPivotReportZipDTO resource = reportFacade.getAssessmentReportQueryV2(assessmentInstanceId,delimiter,language,aiaIds,startDate,endDate);

        final String fileName = (String.format("%s_%s_%s.zip",resource.getInstanceName(), LocalDateTime.now().toLocalDate(),LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)))
                .replaceAll("[- *]", "_");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData( "attachment", fileName);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource.getBytes());
    }

    @ApiOperation(value="getAssessmentReportingQueryV3",tags = {"AssessmentReportController"})
    @GetMapping(value = "/api/v3/assessmentresponsereport/assessment-reporting-query", produces = "application/json" )
    public ResponseEntity<List<Map<String,String>>> getAssessmentReportingQueryV3(@RequestParam Long assessmentInstanceId,
                                                                                  @RequestParam String language,
                                                                                  @RequestParam(required = false) Boolean cacheDisabled,
                                                                                  @RequestParam String marketCode) {
        cacheDisabled = Optional.ofNullable(cacheDisabled).orElse(false);
        List<Map<String,String>> jsonArray = reportFacade.getAssessmentReportQueryV3(assessmentInstanceId,language,marketCode,cacheDisabled);

        return ResponseEntity.status(HttpStatus.OK).body(jsonArray);
    }

    @ApiOperation(value="getAssessmentReportingQueryV4",tags = {"AssessmentReportController"})
    @GetMapping(value = "/api/v4/assessmentresponsereport/assessment-reporting-query", produces = "application/zip" )
    public ResponseEntity<?> getAssessmentReportingQueryV4(@RequestParam Long assessmentInstanceId,@RequestParam String language, @RequestParam(required = false) String delimiter){

        AssessmentPivotReportZipDTO resource = reportFacade.getAssessmentReportQueryV4(assessmentInstanceId,delimiter,language);

        final String fileName = (String.format("%s_%s_%s.zip",resource.getInstanceName(), LocalDateTime.now().toLocalDate(),LocalDateTime.now().toEpochSecond(ZoneOffset.UTC)))
                .replaceAll("[- *]", "_");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData( "attachment", fileName);
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource.getBytes());
    }

    @DeleteMapping(path = "/cache")
    @ApiOperation(value="clearCache",tags = {"AssessmentReportController"})
    public ResponseEntity<?> clearCache( @RequestParam(required = false) Long instanceId) {
        processor.evictCache(instanceId);
        return ResponseEntity.ok(HttpEntity.EMPTY);
    }

}
