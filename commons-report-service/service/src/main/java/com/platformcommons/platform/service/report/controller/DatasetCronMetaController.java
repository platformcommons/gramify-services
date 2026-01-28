package com.platformcommons.platform.service.report.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.report.dto.DatasetCronMetaDTO;
import com.platformcommons.platform.service.report.facade.DatasetCronMetaFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "DatasetCronMeta")
@RequiredArgsConstructor
public class DatasetCronMetaController {


    private final DatasetCronMetaFacade datasetCronMetaFacade;
    @PostMapping("/api/v1/dataset-cron-meta/{dataSetId}")
    @ApiOperation(value = "Add dataset cron meta",tags = "DatasetCronMeta")
    public ResponseEntity<DatasetCronMetaDTO> addDatasetCronMeta(@RequestBody DatasetCronMetaDTO cronMetaDTO,@PathVariable Long dataSetId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(datasetCronMetaFacade.createCronMetaForDataset(cronMetaDTO,dataSetId));
    }

    @GetMapping("/api/v1/dataset-cron-meta/dataset/{datasetId}")
    @ApiOperation(value = "Get dataset cron meta",tags = "DatasetCronMeta")
    public ResponseEntity<PageDTO<DatasetCronMetaDTO>> getDatasetCronMetas(@PathVariable Long datasetId,
                                                                           @RequestParam Integer page,
                                                                           @RequestParam Integer size) {
        PageDTO<DatasetCronMetaDTO> pageDTO = datasetCronMetaFacade.getDatasetCronMetas(datasetId, page, size);
        return ResponseEntity.status(pageDTO.hasNext()?HttpStatus.PARTIAL_CONTENT:HttpStatus.OK)
                .body(pageDTO);
    }

    @PatchMapping("/api/v1/dataset-cron-meta")
    @ApiOperation(value = "Patch dataset cron meta",tags = "DatasetCronMeta")
    public ResponseEntity<DatasetCronMetaDTO> patchDatasetCronMeta(@RequestBody DatasetCronMetaDTO cronMetaDTO,
                                                                   @RequestParam(required = false) Long dataSetId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(datasetCronMetaFacade.patchDatasetCronMeta(cronMetaDTO,dataSetId));
    }

}
