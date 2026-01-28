package com.platformcommons.platform.service.profile.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.profile.client.CostSpecificationAPI;
import com.platformcommons.platform.service.profile.dto.CostSpecificationDTO;
import com.platformcommons.platform.service.profile.facade.CostSpecificationFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Tag(name = "CostSpecification")
public class CostSpecificationController implements CostSpecificationAPI {

    @Autowired
    private CostSpecificationFacade costSpecificationFacade;

    @Override
    public ResponseEntity<Void> deleteCostSpecification(Long id, String reason) {
        costSpecificationFacade.delete(id, reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<CostSpecificationDTO> getCostSpecification(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(costSpecificationFacade.getById(id));
    }

    @Override
    public ResponseEntity<PageDTO<CostSpecificationDTO>> getCostSpecificationPage(Integer page, Integer size) {
        PageDTO<CostSpecificationDTO> result = costSpecificationFacade.getAllPage(page, size);
        return ResponseEntity.status(result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }

    @Override
    public ResponseEntity<CostSpecificationDTO> postCostSpecification(CostSpecificationDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(costSpecificationFacade.save(body));
    }

    @Override
    public ResponseEntity<CostSpecificationDTO> putCostSpecification(CostSpecificationDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(costSpecificationFacade.update(body));
    }

    @ApiOperation(value = "Patch CostSpecification", nickname = "patchCostSpecification", notes = "", response = CostSpecificationDTO.class, tags={ "CostSpecification", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = CostSpecificationDTO.class) })
    @RequestMapping(value = "/api/v1/cost-specification",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    public ResponseEntity<CostSpecificationDTO> patchCostSpecification(@Valid @RequestBody CostSpecificationDTO body) {
        return ResponseEntity.status(HttpStatus.OK).body(costSpecificationFacade.patchCostSpecification(body));
    }
}
