package com.platformcommons.platform.service.assessment.controller;

import com.platformcommons.platform.service.assessment.application.constant.OwnedBy;
import com.platformcommons.platform.service.assessment.application.utility.PageUtil;
import com.platformcommons.platform.service.assessment.client.AssessmentInstanceAPI;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceDetailVO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceSearchDTO;
import com.platformcommons.platform.service.assessment.facade.AssessmentInstanceFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Validated
@RestController
@RequestMapping
@Tag(name = "AssessmentInstance")
@RequiredArgsConstructor
public class AssessmentInstanceController implements AssessmentInstanceAPI {

    // TODO: Add Apis to App
    private final AssessmentInstanceFacade facade;

    @Override

    public ResponseEntity<Long> createAssessmentInstance(AssessmentInstanceDTO assessmentInstanceDTO) {
        return new ResponseEntity<>(facade.createAssessmentInstance(assessmentInstanceDTO), HttpStatus.CREATED);
    }

    @GetMapping("/api/v1/assessmentinstances")
    @ApiOperation(value = "getAllAssessmentInstances", nickname = "getAllAssessmentInstances",  tags={ "AssessmentInstance", })
    public ResponseEntity<Set<AssessmentInstanceDTO>> getAllAssessmentInstances(@NotNull(message = "page must not be null") @Min(value = 0, message = "page must be equal or greater than 0") @RequestParam Integer page, @NotNull(message = "size must not be null") @Min(value = 1, message = "size must be in between 1 to 100") @Max(value = 100, message = "size must be in between 1 to 100") @RequestParam Integer size) {
        PageDTO<AssessmentInstanceDTO> result = facade.getAllAssessmentInstances(page, size);
        return new ResponseEntity<>(result.getElements(), result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AssessmentInstanceDTO> duplicateAssessmentInstance(Long assessmentInstanceId,String name) {
        return new ResponseEntity<>(facade.duplicateAssessmentInstance(assessmentInstanceId,name), HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<AssessmentInstanceDTO> updateAssessmentInstanceV2(AssessmentInstanceDTO assessmentInstanceDTO) {
        facade.updateAssessmentInstanceV2(assessmentInstanceDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<AssessmentInstanceDTO> deleteAssessmentInstanceV2(Long assessmentInstanceId,String reason) {
        facade.deleteAssessmentInstanceV2(assessmentInstanceId,reason);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @Override
    public ResponseEntity<AssessmentInstanceDTO> getAssessmentInstanceByIdV2(Long assessmentInstanceId) {
//        AuthorityEvaluator.preAuthorizeAPIKey("ASSESSMENT_ORG.READ_ASSESSMENT_INSTANCE");
        return new ResponseEntity<>(facade.getAssessmentInstanceByIdv2(assessmentInstanceId), HttpStatus.OK);
    }



    @GetMapping("/api/v2/assessmentinstances/info")
    @ApiOperation(value = "getAssessmentInstanceInfo", nickname = "getAssessmentInstanceInfo",  tags={ "AssessmentInstance", })
    public ResponseEntity<PageDTO<AssessmentInstanceDetailVO>> getAssessmentInstanceInfo(@RequestParam(required = false) String domain,
                                                                                         @RequestParam OwnedBy ownedBy,
                                                                                         @RequestParam(required = false) String subdomain,
                                                                                         @RequestParam(required = false) String status,
                                                                                         @RequestParam String sortBy,
                                                                                         @RequestParam String sortOrder,
                                                                                         @RequestParam Integer page,
                                                                                         @RequestParam Integer size,
                                                                                         @RequestParam String language,
                                                                                         @RequestParam(name = "context",required = false) String context,
                                                                                         @RequestParam(required = false) String text) {
        return PageUtil.getResponseEntity(facade.getAssessmentInstanceInfo(domain,ownedBy,subdomain,status,sortBy,sortOrder,page,size,language,text,context));
    }

    @GetMapping("/api/v1/assessmentinstances/assessment")
    @ApiOperation(value = "getAssessmentInstanceByAssessmentType", nickname = "getAssessmentInstanceByAssessmentType",  tags={ "AssessmentInstance", })
    public ResponseEntity<Set<AssessmentInstanceDTO>> getAssessmentInstanceByAssessmentType(@RequestParam String assessmentType,@RequestParam Boolean assessmentIsActive, @RequestParam Boolean isActive) {
        return new ResponseEntity<>(facade.getAssessmentInstanceByAssessmentType(assessmentType,assessmentIsActive,isActive), HttpStatus.OK);
    }
    @GetMapping("/api/v1/assessmentinstances/assessment/type")
    @ApiOperation(value = "Get AssessmentInstance By Types",  tags={ "AssessmentInstance", })
    public ResponseEntity<PageDTO<AssessmentInstanceDTO>> getAssessmentInstanceByAssessmentType(@RequestParam(required = false) Set<String> assessmentTypes,
                                                                                                @RequestParam(required = false) Set<String> assessmentKind,
                                                                                                @RequestParam(required = false) String assessmentSubType,
                                                                                                @RequestParam(required = false) String forEntityId,
                                                                                                @RequestParam(required = false) String forEntityType,
                                                                                                @RequestParam String context,
                                                                                                @RequestParam Boolean assessmentIsActive,
                                                                                                @RequestParam Boolean isActive,
                                                                                                @RequestParam @Min(value = 0,message = "Page must be greater than 0") Integer page,
                                                                                                @RequestParam @Max(value = 100,message = "Size must be lesser than 0") Integer size) {
        return PageUtil.getResponseEntity(facade.getAssessmentInstanceByAssessmentTypes(assessmentTypes,assessmentKind, assessmentSubType, assessmentIsActive,context,isActive,page,size,forEntityId,forEntityType));
    }

    @ApiOperation(value = "getAssessmentInstanceByIdV3",
            nickname = "getAssessmentInstanceByIdV3",
            response = AssessmentInstanceDTO.class,
            tags = {"AssessmentInstance"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AssessmentInstanceDTO.class),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found")})
    @RequestMapping(value = "/api/v3/assessmentinstances",
            produces = {"application/json"},
            method = RequestMethod.GET)
    public ResponseEntity<AssessmentInstanceDTO> getAssessmentInstanceByIdV3(@RequestParam Long assessmentInstanceId) {
        return new ResponseEntity<>(facade.getAssessmentInstanceByIdv2(assessmentInstanceId), HttpStatus.OK);
    }

    @GetMapping("/api/v1/assessmentinstances/search-vo")
    @ApiOperation(value = "getAllAssessmentInstanceSearchDTO", nickname = "getAllAssessmentInstanceSearchDTO",  tags={ "AssessmentInstance", })
    public ResponseEntity<Set<AssessmentInstanceSearchDTO>> getAllAssessmentInstanceSearchDTO(@NotNull(message = "page must not be null") @Min(value = 0, message = "page must be equal or greater than 0") @RequestParam Integer page, @NotNull(message = "size must not be null") @Min(value = 1, message = "size must be in between 1 to 100") @Max(value = 100, message = "size must be in between 1 to 100") @RequestParam Integer size) {
        PageDTO<AssessmentInstanceSearchDTO> result = facade.getAllAssessmentInstanceSearchDTO(page, size);
        return new ResponseEntity<>(result.getElements(), result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK);
    }


}

