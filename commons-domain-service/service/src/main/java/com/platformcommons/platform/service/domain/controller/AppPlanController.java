package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.dto.AppDTO;
import com.platformcommons.platform.service.domain.dto.AppPlanDTO;
import com.platformcommons.platform.service.domain.facade.AppPlanFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Tag(name = "AppPlan")
@RequestMapping("api/v1/app-plans")
public class AppPlanController {

    @Autowired
    private AppPlanFacade appPlanFacade;

    @ApiOperation(value = "", tags = {"AppPlan"})
    @PostMapping
    ResponseEntity<Void> createAppPlans(@NotNull @Valid @RequestParam  Long appId,
                                        @RequestBody List<AppPlanDTO> appPlanDTOS){
        appPlanFacade.createAppPlans(appId,appPlanDTOS);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "", tags = {"AppPlan"})
    @GetMapping
    ResponseEntity<PageDTO<AppPlanDTO>> fetchAppPlansByApp(@NotNull @Valid @RequestParam  Long appId,
                                                           @RequestParam Integer page,
                                                           @RequestParam Integer size){
        PageDTO<AppPlanDTO> results=appPlanFacade.fetchAppPlans(appId,page,size);
        return ResponseEntity.status(results.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "", tags = {"AppPlan"})
    @PatchMapping
    public ResponseEntity<AppPlanDTO> updateAppPlan(@RequestBody AppPlanDTO appPlanDTO){
        return ResponseEntity.status(HttpStatus.OK).body(appPlanFacade.update(appPlanDTO));
    }
}
