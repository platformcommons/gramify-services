package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.GenericSolutionDTO;
import com.platformcommons.platform.service.search.facade.GenericSolutionFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Set;

@RestController
@Tag(name = "GenericSolution")
public class GenericSolutionController {
    @Autowired
    private GenericSolutionFacade genericSolutionFacade;

    @ApiOperation(value = "get GenericSolution by searchTerm", nickname = "readGenericSolution", notes = "", response = GenericSolutionDTO.class, responseContainer = "Set", tags={ "GenericSolution", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = GenericSolutionDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/generic-solution/filter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<GenericSolutionDTO>> readGenericSolution(@RequestParam(value = "marketId", required = true) String marketId,
                                                                    @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                    @RequestParam(value = "categoryCodes", required = false) Set<String> categoryCodes,
                                                                    @RequestParam(value = "subCategoryCodes", required = false) Set<String> subCategoryCodes,
                                                                    @RequestParam(value = "solutionType", required = false) String solutionType,
                                                                    @NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                    @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                                    @RequestParam(required = false) String sortBy,
                                                                    @RequestParam(required = false) String direction){
        PageDTO<GenericSolutionDTO> results=genericSolutionFacade.readGenericSolutionByTitles(marketId,searchTerm,categoryCodes,subCategoryCodes,solutionType,page,size,sortBy,direction);
        return ResponseEntity.status(results.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "Sync Generic Solution Variant Count", tags={ "GenericSolution", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK") })
    @RequestMapping(value = "/api/v1/generic-solution/variant-count/sync",
            produces = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<Void> syncGenericSolutionVariantCount(@RequestParam(name="marketId",required = true) Long marketId,
                                                                @RequestParam(name="channelId",required = true) Long channelId,
                                                                @RequestParam(name="baseSolutionId",required = true) Long baseSolutionId) {
        genericSolutionFacade.syncGenericSolutionVariantCount(marketId,channelId,baseSolutionId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
