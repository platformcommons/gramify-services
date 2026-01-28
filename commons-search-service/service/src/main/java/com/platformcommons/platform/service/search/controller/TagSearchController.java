package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.TagSearchDTO;
import com.platformcommons.platform.service.search.facade.TagSearchFacade;
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
@Tag(name = "TagSearch")
public class TagSearchController {
    @Autowired
    private TagSearchFacade tagSearchFacade;

    @ApiOperation(value = "", nickname = "readTagSearch", notes = "", response = TagSearchDTO.class, responseContainer = "Set", tags={ "TagSearch", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagSearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/tag-search",
            produces = { "application/json" },
            method = RequestMethod.GET)
    ResponseEntity<PageDTO<TagSearchDTO>> readTagSearch(@Valid @RequestParam(value = "keyword", required = false) String keyword,
                                                  @NotNull @Valid @RequestParam(value = "page", required = true) Integer page,
                                                  @NotNull  @Valid @RequestParam(value = "size", required = true) Integer size,
                                                        @Valid @RequestParam(value = "context", required = false) String context,
                                                        @RequestParam(value = "excludeTypes", required = false) Set<String> excludeTypes){
        PageDTO<TagSearchDTO> results=tagSearchFacade.readTagSearch(keyword, context,excludeTypes, page, size);
        return ResponseEntity.status(results.hasNext()? HttpStatus.PARTIAL_CONTENT:HttpStatus.OK).body(results);
    }



    @ApiOperation(value = "", nickname = "syncTag", notes = "", response = TagSearchDTO.class, responseContainer = "Set", tags={ "TagSearch", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TagSearchDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "api/v1/tag-search/sync",
            method = RequestMethod.POST)
    ResponseEntity<Void> sync(@Valid @RequestParam(value = "page", required = false) Integer startPage,
                              @NotNull @Valid @RequestParam(value = "size", required = true) Integer defaultSize,
                              @RequestParam(value = "type", required = false) String type) {
        tagSearchFacade.syncTag(startPage,defaultSize,type);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(
            value = "Reindex Tag Search",
            notes = "Triggers reindexing of all Tag Search.",
            response = Void.class,
            tags = {"TagSearch"}
    )
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content - Reindex completed successfully")
    })
    @RequestMapping(value = "/api/v1/tag-search/re-index",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Void> reindex () {
        tagSearchFacade.reindex();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
