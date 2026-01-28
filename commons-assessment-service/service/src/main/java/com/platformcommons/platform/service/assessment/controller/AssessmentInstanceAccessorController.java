package com.platformcommons.platform.service.assessment.controller;

import com.platformcommons.platform.service.assessment.application.utility.PageUtil;
import com.platformcommons.platform.service.assessment.client.AssessmentInstanceAccessorAPI;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAccessorDTO;
import com.platformcommons.platform.service.assessment.facade.AssessmentInstanceAccessorFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping
@Tag(name = "AssessmentInstanceAccessor")
@RequiredArgsConstructor
public class AssessmentInstanceAccessorController implements AssessmentInstanceAccessorAPI {

    private final AssessmentInstanceAccessorFacade facade;

    @PostMapping("/api/v1/assessment-instance-accessor/create")
    @ApiOperation(value = "createAssessmentInstanceAccessor", nickname = "createAssessmentInstanceAccessor", tags={ "AssessmentInstanceAccessor", })
    public ResponseEntity<List<AssessmentInstanceAccessorDTO>> createAssessmentInstanceAccessor(@RequestParam Long instanceId, @RequestParam Set<String> logins ){
        return ResponseEntity.ok(facade.createAssessmentInstanceAccessor(instanceId,logins));
    }

    @GetMapping("/api/v1/assessment-instance-accessor")
    @ApiOperation(value = "getAssessmentInstanceAccessors", nickname = "getAssessmentInstanceAccessors", notes = "",  tags={ "AssessmentInstanceAccessor", })
    public ResponseEntity<PageDTO<AssessmentInstanceAccessorDTO>> getAssessmentInstanceAccessors(@RequestParam Long instanceId,@RequestParam Integer page,@RequestParam Integer size){
        return PageUtil.getResponseEntity(facade.getAssessmentInstanceAccessors(instanceId,page,size));
    }

    @Override
    public ResponseEntity<Void> removeAssessmentInstanceAccessors(Long instanceId,Set<String> logins){
        facade.removeAssessmentInstanceAccessors(logins,instanceId);
        return ResponseEntity.ok().build();
    }

}
