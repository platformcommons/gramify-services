package com.platformcommons.platform.service.assessment.controller;

import com.platformcommons.platform.service.assessment.application.utility.PageUtil;
import com.platformcommons.platform.service.assessment.application.utility.submission.CGPostSubmissionExecutorTask;
import com.platformcommons.platform.service.assessment.client.AssessmentInstanceAssesseAPI;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseFilterDTO;
import com.platformcommons.platform.service.assessment.facade.AssessmentInstanceAssesseFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Validated
@RestController
@RequestMapping
@Tag(name = "AssessmentInstanceAssesse")
@RequiredArgsConstructor
public class AssessmentInstanceAssesseController implements AssessmentInstanceAssesseAPI {

    private final AssessmentInstanceAssesseFacade facade;
    @Autowired
    @Lazy
    private CGPostSubmissionExecutorTask postSubmissionExecutorTask;

    @Override
    public ResponseEntity<Long> createAssessmentInstanceAssesse(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO) {
        return new ResponseEntity<>(facade.createAssessmentInstanceAssesseV2(assessmentInstanceAssesseDTO), HttpStatus.CREATED);
    }

    @ApiOperation(value = "createAssessmentInstanceAssesseV3", nickname = "createAssessmentInstanceAssesse", response = Long.class, tags={ "AssessmentInstanceAssesse", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @PostMapping(value = "/api/v3/assessmentInstanceAssesses")
    public ResponseEntity<AssessmentInstanceAssesseDTO> createAssessmentInstanceAssesseV2(@RequestBody AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO) {
        AssessmentInstanceAssesseDTO assesseDTO =  facade.createAssessmentInstanceAssesseV3(assessmentInstanceAssesseDTO);
//        postSubmissionExecutorTask.onSubmit(assesseDTO.getId(), SecurityContextHolder.getContext().getAuthentication());
        return new ResponseEntity<>(assesseDTO, HttpStatus.CREATED);
    }

    @ApiOperation(value = "createAssessmentInstanceAssesseV3", nickname = "createAssessmentInstanceAssesse", response = Long.class, tags={ "AssessmentInstanceAssesse", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @PostMapping(value = "/api/v3/assessmentInstanceAssesses/cg/sync")
    public ResponseEntity<AssessmentInstanceAssesseDTO> createAssessmentInstanceAssesseV2SyncCG(@RequestParam Long aiaId) {
        postSubmissionExecutorTask.onSubmit(aiaId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesseById(Long id) {
        return new ResponseEntity<>(facade.getAssessmentInstanceAssesseById(id), HttpStatus.OK);
    }


    @Override
    public ResponseEntity<AssessmentInstanceAssesseDTO> updateAssessmentInstanceAssesse(AssessmentInstanceAssesseDTO body) {
        AssessmentInstanceAssesseDTO updatedDTO = facade.updateAssessmentInstanceAssesseWithResponse(body);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @ApiOperation(value = "updateAssessmentInstanceAssesse", nickname = "updateAssessmentInstanceAssesse", response = AssessmentInstanceAssesseDTO.class, tags={ "AssessmentInstanceAssesse", })
    @PatchMapping(value = "/api/v1/assessmentInstanceAssesses")
    public ResponseEntity<AssessmentInstanceAssesseDTO> updateAssesse(@RequestBody AssessmentInstanceAssesseDTO body) {
        AssessmentInstanceAssesseDTO updatedDTO = facade.patchUpdateAssesse(body);
        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @DeleteMapping("/api/v1/assessmentInstanceAssesses/{id}")
    @ApiOperation(value = "deleteAssessmentInstanceAssesse", nickname = "deleteAssessmentInstanceAssesse", notes = "", response = Long.class, tags={ "AssessmentInstanceAssesse", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "NO_CONTENT"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    public ResponseEntity<?> deleteAssessmentInstanceAssesse(@PathVariable Long id,@RequestParam(required = false) String reason) {
        facade.deleteAssessmentInstanceAssesse(id, reason);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/api/v1/assessmentInstanceAssesses/assessments/{assessmentId}")
    @ApiOperation(value = "getAssessmentInstanceAssesse", nickname = "getAssessmentInstanceAssesse", notes = "", response = Long.class, tags={ "AssessmentInstanceAssesse", })
    public ResponseEntity<Set<AssessmentInstanceAssesseDTO>> getAssessmentInstanceAssesse(@PathVariable Long assessmentId,
                                                                                          @Min(value = 0,message = "Page should be greater than zero") @RequestParam(required = false) Integer page,
                                                                                          @Max(value = 100,message = "Size Should not be greater than Hundred") @RequestParam(required = false) Integer size) {
        return new ResponseEntity<>(facade.getAssessmentInstanceAssesse(assessmentId,page,size).getElements(), HttpStatus.OK);
    }

    @GetMapping("/api/v1/assessmentInstanceAssesses/assessments/count/{assessmentId}")
    @ApiOperation(value = "getAssessmentInstanceAssesseCount", nickname = "getAssessmentInstanceAssesseCount", notes = "", response = Long.class, tags={ "AssessmentInstanceAssesse", })
    public ResponseEntity<Long> getAssessmentInstanceAssesseCount(@PathVariable Long assessmentId){
        return new ResponseEntity<>(facade.getAssessmentInstanceAssesseCount(assessmentId),HttpStatus.OK);
    }


    @GetMapping("/api/v1/assessmentInstanceAssesses/assessedForEntityIds/")
    @ApiOperation(value = "getAssessmentInstanceAssesseByAssessedForEntityIds", nickname = "getAssessmentInstanceAssesseByAssessedForEntityIds", notes = "", response = Long.class, tags={ "AssessmentInstanceAssesse", })
    public ResponseEntity<Set<AssessmentInstanceAssesseDTO>> getAssessmentInstanceAssesseByAssessedForEntityIds(@RequestParam List<String> assessedForEntityId, @RequestParam String assessedForEntityType, @RequestParam List<Long> assessmentInstanceId) {
        return new ResponseEntity<>(facade.getAssessmentInstanceAssesseByAssessedForEntityIds(assessedForEntityId,assessedForEntityType,assessmentInstanceId), HttpStatus.OK);
    }
    @GetMapping("/api/v1/assessmentInstanceAssesses/assesse")
    @ApiOperation(value = "getAssessmentInstanceAssesseByAssesse", nickname = "getAssessmentInstanceAssesseByAssesse", response = Long.class, tags={ "AssessmentInstanceAssesse", })
    public ResponseEntity<Set<AssessmentInstanceAssesseDTO>> getAssessmentInstanceAssesseByAssesse(@RequestParam List<String> actorIds,
                                                                                                   @RequestParam String actorType,
                                                                                                   @RequestParam List<Long> assessmentInstanceId) {
        return new ResponseEntity<>(facade.getAssessmentInstanceAssesseByAssesse(actorIds,actorType,assessmentInstanceId), HttpStatus.OK);
    }

    @GetMapping("/api/v1/assessmentInstanceAssesses")
    @ApiOperation(value = "get AssessmentInstanceAssesse by assessorId and assessorType",nickname = "getAssessmentInstanceAssesseByFilters", notes = "", tags = { "AssessmentInstanceAssesse" })
    public ResponseEntity<PageDTO<AssessmentInstanceAssesseDTO>> getAssessmentInstanceAssesseByFilters(
            @RequestParam(required = false) Set<Long> instanceIds,
            @RequestParam(required = false) String subType,
            @RequestParam(required = false) String assesseId,
            @RequestParam(required = false) String assesseType,
            @RequestParam String context,
            @RequestParam String assessorId,
            @RequestParam String assessorType,
            @RequestParam Integer page,
            @RequestParam Integer size) {

        return PageUtil.getResponseEntity(facade.getAssessmentInstanceAssesseByAccessorId(AssessmentInstanceAssesseFilterDTO.builder()
                .instanceIds(instanceIds)
                .subType(subType)
                .assesseId(assesseId)
                .assesseType(assesseType)
                .context(context)
                .assessorId(assessorId)
                .assessorType(assessorType)
                .page(page)
                .size(size)
                .build()));
    }


    @ApiOperation(value = "createAssessmentInstanceAssesse", nickname = "createAssessmentInstanceAssesse", notes = "", response = Long.class, tags={ "AssessmentInstanceAssesse", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = Long.class),
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/v2/assessmentInstanceAssesses/component",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<Map<String,AssessmentInstanceAssesseDTO>> createAssessmentInstanceAssesseForComponents(@Valid @RequestBody Map<String, AssessmentInstanceAssesseDTO> body){
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createAssessmentInstanceAssesseForComponents(body));
    }
}

