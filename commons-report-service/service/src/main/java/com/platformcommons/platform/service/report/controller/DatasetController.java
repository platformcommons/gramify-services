package com.platformcommons.platform.service.report.controller;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.report.application.constant.FileType;
import com.platformcommons.platform.service.report.client.DatasetAPI;
import com.platformcommons.platform.service.report.dto.DatasetDTO;
import com.platformcommons.platform.service.report.facade.DatasetFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "Dataset")
public class DatasetController implements DatasetAPI {

    @Autowired
    private DatasetFacade datasetFacade;

    @ApiOperation(value = "addDataset", nickname = "addDataset", notes = "", response = Long.class, tags={ "Dataset", })
    @PostMapping("/api/v1/datasets")
    public ResponseEntity<Long> addDataset(@RequestBody DatasetDTO datasetDTO,
                                @RequestParam(name = "dataSource") Long datasource,
                                @RequestParam(value = "datasetGroupCodes",required = false) Set<String> datasetGroupCodes) {
        PlatformSecurityUtil.validatePlatformAdmin();
        return ResponseEntity.ok(datasetFacade.save(datasetDTO, datasource,datasetGroupCodes));
    }


    @ApiOperation(value = "updateDataset", nickname = "updateDataset", notes = "",  tags={ "Dataset", })
    @PutMapping("/api/v1/datasets")
    public ResponseEntity<Void> updateDataset(@RequestBody DatasetDTO datasetDTO) {
        PlatformSecurityUtil.validatePlatformAdmin();
        datasetFacade.update(datasetDTO);
        return ResponseEntity.accepted().build();
    }


    @ApiOperation(value = "getDataSetById", nickname = "getDataSetById", notes = "", response = DatasetDTO.class, tags={ "Dataset", })
    @GetMapping("/api/v1/datasets/{id}")
    public ResponseEntity<DatasetDTO> getDataSetById(@PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(datasetFacade.getById(id));
    }

    @ApiOperation(value = "getDataSetByName", nickname = "getDataSetByName", notes = "", response = DatasetDTO.class, tags={ "Dataset", })
    @GetMapping("/api/v1/datasets/name/{name}")
    public ResponseEntity<DatasetDTO> getDataSetByName(@PathVariable(name = "name") String name) {
        return ResponseEntity.ok(datasetFacade.getByName(name));
    }

    @ApiOperation(value = "getDataSetsByListOfNames", nickname = "getDataSetsByNames", notes = "", response = DatasetDTO.class, tags={ "Dataset", })
    @GetMapping("/api/v1/datasets/name-list/")
    public ResponseEntity<List<DatasetDTO>> getDataSetsByListOfNames(@RequestParam(name = "name") List<String> names) {
        return ResponseEntity.ok(datasetFacade.getByListOfNames(names));
    }

    @ApiOperation(value = "executeDataSet", nickname = "executeDataSet", notes = "", response = Object.class, tags={ "Dataset", })
    @GetMapping("/api/v1/datasets/name/{name}/execute")
    public ResponseEntity<Object> executeDataSet(@PathVariable(name = "name") String name, @RequestParam(value = "params",
            required = false)String params){
        return ResponseEntity.status(HttpStatus.OK).body(datasetFacade.execute(name,params));
    }

    @ApiOperation(value = "executeDataSet", nickname = "executeDataSet", notes = "", response = Object.class, tags={ "Dataset", })
    @PostMapping("/api/v1/datasets/name/{name}/execute")
    public ResponseEntity<Object> executeDataSetPostBody(@PathVariable(name = "name") String name,
                                                         @RequestBody(required = false) Map<String,String> params){
        return ResponseEntity.status(HttpStatus.OK).body(datasetFacade.execute(name,params));
    }


    @ApiOperation(value = "executeDataSet", nickname = "executeDataSet", notes = "", response = Object.class, tags={ "Dataset", })
    @GetMapping("/api/v2/datasets/name/{name}/execute")
    public ResponseEntity<Object> executeDataSetV2(@PathVariable(name = "name") String name,
                                                   @RequestParam(value = "params", required = false)String params,
                                                   @RequestParam(required = true) Integer page,
                                                   @RequestParam(required = true) Integer size){
        return ResponseEntity.status(HttpStatus.OK).body(datasetFacade.executeV2(name,params,page,size));
    }

    @ApiOperation(value = "executeDataSet", nickname = "executeDataSet", notes = "This API Used to get dataSet result with Page, size & StartDate & EndDate", response = Object.class, tags={ "Dataset", })
    @GetMapping("/api/v4/datasets/name/{name}/execute")
    public ResponseEntity<Object> executeDataSetV2(@PathVariable(name = "name") String name,
                                                   @RequestParam(value = "params", required = false)String params,
                                                   @RequestParam(value = "startDate", required = false) String startDate,
                                                   @RequestParam(value = "endDate",required = false) String endDate,
                                                   @RequestParam(required = true) Integer page,
                                                   @RequestParam(required = true) Integer size){
        return ResponseEntity.status(HttpStatus.OK).body(datasetFacade.executeV2(name,params,startDate,endDate,page,size));
    }

    @ApiOperation(value = "executeDataSetDownload", nickname = "executeDataSetDownload", notes = "", response = InputStreamResource.class, tags={ "Dataset", })
    @RequestMapping(value = "/api/v1/datasets/name/{name}/execute/download", method = RequestMethod.GET)
    ResponseEntity<?> executeQueryDownload(@PathVariable("name") String name,
                                                @Valid @RequestParam(value = "param", required = false) String param,
                                                @RequestParam(required = false) FileType fileType){

        fileType= fileType!=null? fileType: FileType.CSV;
        byte[] resource = datasetFacade.executeQueryDownload(name,param,fileType);
        if (resource != null) {
            final String fileName = ("output" + "_" + PlatformSecurityUtil.getCurrentTenantId() + "_" +
                    LocalDateTime.now().toLocalDate() + LocalDateTime.now().toEpochSecond(ZoneOffset.UTC) + fileType.getExtension()).replaceAll("[- *]", "_");

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(fileType.getContentType()));
            headers.setContentDispositionFormData("attachment", fileName);
            return ResponseEntity.status(HttpStatus.OK).headers(headers).body(resource);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }


    @Override
    public ResponseEntity<PageDTO<DatasetDTO>> getDatasetPage(Integer page, Integer size, String sortBy, String direction) {
        return ResponseEntity.status(HttpStatus.OK).body(datasetFacade.getAllDataset(page,size,sortBy,direction));
    }
    @Override
    public ResponseEntity<Object> executeDataSetV3(String name, Integer page, Integer size, String params) {
        return ResponseEntity.status(HttpStatus.OK).body(datasetFacade.executeV3(name,params,page,size));
    }

}
