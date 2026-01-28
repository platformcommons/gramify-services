package com.platformcommons.platform.service.assessment.controller;

import com.platformcommons.platform.service.assessment.dto.AssessmentContextDTO;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.client.AssessmentAPI;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.ConfigDTO;
import com.platformcommons.platform.service.assessment.dto.UploadedAssessmentInfo;
import com.platformcommons.platform.service.assessment.facade.AssessmentFacade;
import com.platformcommons.platform.service.assessment.facade.BulkUploadFacade;
import com.platformcommons.platform.service.assessment.service.CleanupService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import java.io.ByteArrayInputStream;
import java.util.Objects;
import java.util.Set;

@Validated
@RestController
@RequestMapping
@Tag(name = "Assessment")
@RequiredArgsConstructor
public class AssessmentController implements AssessmentAPI {

    private final AssessmentFacade facade;

    private final BulkUploadFacade bulkUploadFacade;
    private final CleanupService cleanupService;

    @Override
    public ResponseEntity<Long> createAssessment(AssessmentDTO assessmentDTO) {
        return new ResponseEntity<>(facade.createAssessment(assessmentDTO), HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<AssessmentDTO> getAssessmentById(Long assessmentId) {
        return new ResponseEntity<>(facade.getAssessmentById(assessmentId), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<AssessmentDTO> updateAssessmentV2(AssessmentDTO assessmentDTO) {
        facade.updateAssessmentV2(assessmentDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(value = "getContext", nickname = "getContext", notes = "", response = AssessmentContextDTO.class, tags={ "Assessment", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssessmentContextDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @GetMapping(value = "/api/v1/assessments/context/{assessmentId}")
    public ResponseEntity<AssessmentContextDTO> getContext(@PathVariable Long assessmentId){
        return new ResponseEntity<>(facade.getContext(assessmentId), HttpStatus.OK);
    }

    @ApiOperation(value = "getAssessmentContextByUUIDsAndCodes",
            nickname = "getAssessmentContextByUUIDsAndCodes",
            notes = "",
            response = AssessmentContextDTO.class,
            tags={ "Assessment", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssessmentContextDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found") })
    @GetMapping(value = "/api/v1/assessments/context")
    public ResponseEntity<Set<AssessmentContextDTO>> getContexts(@RequestParam(required = false) Set<String> uuids,
                                                                 @RequestParam(required = false) Set<String> codes){
        if(CollectionUtils.isEmpty(uuids) && CollectionUtils.isEmpty(codes))  return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(facade.getContexts(uuids,codes), HttpStatus.OK);
    }

    @ApiOperation(value = "deleteCacheContextForInstance", nickname = "deleteCacheContextForInstance", response = AssessmentContextDTO.class, tags={ "Assessment", })
    @DeleteMapping(value = "/api/v1/assessments/context/cached")
    public ResponseEntity<Void> deleteCacheContextForInstance(@RequestParam(required = false) Long assessmentInstanceId){
        facade.evictCacheContext(assessmentInstanceId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @ApiOperation(value = "Upload", nickname = "UploadAssessment", notes = "", tags = {"Assessment"})
    @PostMapping("/api/v1/assessments/upload-assessment")
    public ResponseEntity<UploadedAssessmentInfo> uploadAssessment(@RequestPart MultipartFile file, @RequestParam Integer maxDepth, @RequestParam(required = false) Boolean createInstance) throws Exception {
        if(Objects.isNull(createInstance)) createInstance = Boolean.FALSE;
        UploadedAssessmentInfo uploadedAssessmentInfo = bulkUploadFacade.uploadV2(file, maxDepth,createInstance);
        return ResponseEntity.status(HttpStatus.OK).body(uploadedAssessmentInfo);
    }
    @ApiOperation(value = "Download Assessment as Excel", nickname = "downloadAssessment", notes = "Provides an Excel sheet for a given assessment ID.", tags = {"Assessment"})
    @GetMapping("/api/v1/assessments/{assessmentId}/download")
    public ResponseEntity<Resource> downloadAssessment(@PathVariable Long assessmentId) throws Exception {
        ByteArrayInputStream in = bulkUploadFacade.downloadAssessmentAsExcel(assessmentId);

        HttpHeaders headers = new HttpHeaders();
        String filename = "assessment_" + assessmentId + ".xlsx";
        headers.add("Content-Disposition", "attachment; filename=" + filename);
        headers.add("Access-Control-Expose-Headers", "Content-Disposition");


        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
    @ApiOperation(value = "addAssessmentConfig", nickname = "addAssessmentConfig", notes = "Add a new configuration to an assessment.", response = AssessmentDTO.class, tags = { "Assessment" })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = AssessmentDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @PostMapping(value = "/api/v1/assessments/{assessmentId}/configs")
    public ResponseEntity<AssessmentDTO> addAssessmentConfig(@PathVariable("assessmentId") Long assessmentId, @RequestBody ConfigDTO configDTO) {
        return new ResponseEntity<>(facade.addAssessmentConfig(assessmentId, configDTO), HttpStatus.CREATED);
    }


    @PostMapping("/api/v1/review-assessment/reminder")
    @ApiOperation(value = "Trigger Review Assessment Reminder", tags={ "Assessment", })
    public ResponseEntity<Void>  triggerReviewAssessmentReminder(){
        facade.triggerReviewAssessmentReminder();
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "CleanUp", nickname = "CleanUpAssessment", notes = "", tags = {"Assessment"})
    @DeleteMapping("/api/v1/assessments/cleanup-assessment-dangling")
    public ResponseEntity<Void> cleanUpDanglingQuestion(@RequestParam Long userId) throws Exception {
        if (!PlatformSecurityUtil.isPlatformAdmin()) {
            throw new Exception("Only platform admin can clean up assessment");
        }
        cleanupService.cleanUpDanglingQuestion(userId,50000L);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

