package com.platformcommons.platform.service.profile.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.client.CostSpecificationMasterAPI;
import com.platformcommons.platform.service.profile.dto.CostSpecificationMasterDTO;
import com.platformcommons.platform.service.profile.dto.IeDTO;
import com.platformcommons.platform.service.profile.facade.CostSpecificationMasterFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Tag(name = "CostSpecificationMaster")
public class CostSpecificationMasterController implements CostSpecificationMasterAPI {

    @Autowired
    private CostSpecificationMasterFacade costSpecificationMasterFacade;

    @Override
    public ResponseEntity<Void> deleteCostSpecificationMaster(Long id, String reason) {
        costSpecificationMasterFacade.delete(id, reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<CostSpecificationMasterDTO> getCostSpecificationMaster(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(costSpecificationMasterFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<CostSpecificationMasterDTO>> getCostSpecificationMasterPage(Integer page, Integer size) {
        PageDTO<CostSpecificationMasterDTO> result = costSpecificationMasterFacade.getAllPage(page, size);
        return ResponseEntity.status(result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<Long> postCostSpecificationMaster(CostSpecificationMasterDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(costSpecificationMasterFacade.save(body));
    }

    @Override
    public ResponseEntity<CostSpecificationMasterDTO> putCostSpecificationMaster(CostSpecificationMasterDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(costSpecificationMasterFacade.update(body));
    }

    @ApiOperation(value = "Get CostSpecificationMasters by context", nickname = "getCostSpecificationMasterByContext", notes = "", response = CostSpecificationMasterDTO.class, responseContainer = "Set", tags={ "CostSpecificationMaster", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CostSpecificationMasterDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/cost-specification-master/filter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<CostSpecificationMasterDTO>> getCostSpecificationMasterByContext(@RequestParam(value = "context", required = false) String context,
                                                                                            @RequestParam(value = "contextId", required = false) String contextId,
                                                                                            @NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                                            @NotNull @Valid @RequestParam(value = "size", required = true) Integer size) {
        PageDTO<CostSpecificationMasterDTO> result = costSpecificationMasterFacade.getCostSpecificationMasterByContext(context,contextId,page, size);
        return ResponseEntity.status(result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }

    @ApiOperation(value = "Upload Bulk Cost Specification Master", nickname = "uploadCostSpecificationMaster", notes = "This API upload bulk  data ", tags = {"CostSpecificationMaster"})
    @RequestMapping(value = "/api/v1/cost-specification-master/bulk",
            method = RequestMethod.POST)
    public ResponseEntity<Void> uploadBulkSpecification(@RequestPart(required = true) MultipartFile file) throws Exception {
        costSpecificationMasterFacade.createBulkCostSpecificationMaster(file);
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

}
