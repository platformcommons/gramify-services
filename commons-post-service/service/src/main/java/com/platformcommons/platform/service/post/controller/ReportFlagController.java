package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.post.client.ReportFlagAPI;
import com.platformcommons.platform.service.post.dto.ReportFlagDTO;
import com.platformcommons.platform.service.post.facade.ReportFlagFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;


@RestController
@Tag(name = "ReportFlag")
public class ReportFlagController implements ReportFlagAPI {


    @Autowired
    private ReportFlagFacade facade;

    @Override
    public ResponseEntity<Void> deleteReportFlag(Long id, String reason) {
        facade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<ReportFlagDTO> getReportFlag(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<ReportFlagDTO>> getReportFlagPage(Integer page, Integer size, String reportType, String marketCode) {
        PageDTO<ReportFlagDTO> result = facade.getAllPage(page,size,reportType,marketCode);
        return ResponseEntity.status(result.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<Long> postReportFlag(ReportFlagDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(body));
    }

    @Override
    public ResponseEntity<ReportFlagDTO> putReportFlag(ReportFlagDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(body));
    }



    @ApiOperation(value = "getReportFlagsByLoggedInUser", nickname = "getReportFlagsByLoggedInUser", notes = "",  tags={ "ReportFlag", })
    @RequestMapping(value = "/api/v1/report-flag/entity-types",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public  ResponseEntity<Map<String, Set<ReportFlagDTO>>> getReportFlagsByLoggedInUser(@RequestParam Set<String> entityTypes){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getReportFlagsByLoggedInUser(entityTypes));
    }

    @ApiOperation(value = "Patch ReportFlag", nickname = "patchReportFlag", notes = "", response = ReportFlagDTO.class, tags={ "ReportFlag", })
    @PatchMapping(value = "/api/v1/report-flag")
    public ResponseEntity<ReportFlagDTO> patchReportFlag(@RequestBody ReportFlagDTO body){
        return ResponseEntity.status(HttpStatus.OK).body(facade.patchReportFlag(body));
    }
}
