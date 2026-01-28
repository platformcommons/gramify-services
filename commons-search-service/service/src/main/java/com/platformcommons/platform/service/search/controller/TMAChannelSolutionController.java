package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.TMAChannelSolutionDTO;
import com.platformcommons.platform.service.search.facade.TMAChannelSolutionFacade;
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
@Tag(name = "TMAChannelSolution")
public class TMAChannelSolutionController {
    @Autowired
    private TMAChannelSolutionFacade tmaChannelSolutionFacade;

    @ApiOperation(value = "get TMAChannelSolution by searchTerm", nickname = "readTMAChannelSolution", notes = "", response = TMAChannelSolutionDTO.class, responseContainer = "Set", tags={ "TMAChannelSolution", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TMAChannelSolutionDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/tma-channel-solution/filter",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<TMAChannelSolutionDTO>> readTMAChannelSolution(@RequestParam(value = "marketCode", required = true) String marketCode,
                                                                          @RequestParam(value = "channelCode", required = true) String channelCode,
                                                                    @RequestParam(value = "searchTerm", required = false) String searchTerm,
                                                                    @RequestParam(value = "baseSolutionId", required = false) Long baseSolutionId,
                                                                    @RequestParam(value = "categoryCodes", required = false) Set<String> categoryCodes,
                                                                    @RequestParam(value = "subCategoryCodes", required = false) Set<String> subCategoryCodes,
                                                                    @RequestParam(value = "traderId", required = false) Long traderId,
                                                                          @RequestParam(value = "solutionType", required = false) String solutionType,
                                                                          @RequestParam(value = "solutionClass", required = false) String solutionClass,
                                                                          @RequestParam(value = "tagType", required = false) String tagType,
                                                                          @RequestParam(value = "tagCodes", required = false) Set<String> tagCodes,
                                                                    @NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                    @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                                    @RequestParam(required = false) String sortBy,
                                                                    @RequestParam(required = false) String direction){
        PageDTO<TMAChannelSolutionDTO> results=tmaChannelSolutionFacade.readTMAChannelSolutionByTitles(marketCode,channelCode,
                searchTerm,baseSolutionId,categoryCodes,subCategoryCodes,traderId,solutionType,solutionClass,tagType,tagCodes,page,size,sortBy,direction);
        return ResponseEntity.status(results.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }

    @ApiOperation(value = "get TMAChannelSolutions", nickname = "getTMAChannelSolutions", notes = "", response = TMAChannelSolutionDTO.class, responseContainer = "Set", tags={ "TMAChannelSolution", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TMAChannelSolutionDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/tma-channel-solution/page",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<TMAChannelSolutionDTO>> getTMAChannelSolutions(@RequestParam(value = "marketCode", required = true) String marketCode,
                                                                          @RequestParam(value = "channelCode", required = true) String channelCode,
                                                                          @RequestParam(value = "traderId", required = false) Long traderId,
                                                                          @RequestParam(value = "solutionType", required = false) String solutionType,
                                                                          @RequestParam(value = "solutionClass", required = false) String solutionClass,
                                                                          @NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                                          @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                                          @RequestParam(required = false) String sortBy,
                                                                          @RequestParam(required = false) String direction){
        PageDTO<TMAChannelSolutionDTO> results=tmaChannelSolutionFacade.getTMAChannelSolutions(marketCode,channelCode,traderId,solutionType,solutionClass,page,size,sortBy,direction);
        return ResponseEntity.status(results.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }


    @ApiOperation(value = "", nickname = "saveTMAChannelSolution", notes = "", response = TMAChannelSolutionDTO.class, tags={ "TMAChannelSolution", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = TMAChannelSolutionDTO.class) })
    @RequestMapping(value = "/api/v1/tma-channel-solution",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<TMAChannelSolutionDTO> saveTMAChannelSolution(@Valid @RequestBody TMAChannelSolutionDTO tmaChannelSolutionDTO) {
        return new ResponseEntity<>(tmaChannelSolutionFacade.save(tmaChannelSolutionDTO), HttpStatus.CREATED);
    }

    @ApiOperation(
            value = "Reindex TMA Channel Solution SKU",
            notes = "Triggers reindexing of all TMA Channel Solution.",
            response = Void.class,
            tags = {"TMAChannelSolution"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content - Reindex completed successfully")
    })
    @RequestMapping(value = "/api/v1/tma-channel-solution/re-index",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Void> reindex () {
        tmaChannelSolutionFacade.reindex();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
