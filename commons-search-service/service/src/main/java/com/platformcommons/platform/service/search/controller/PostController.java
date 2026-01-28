package com.platformcommons.platform.service.search.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.search.dto.PostDTO;
import com.platformcommons.platform.service.search.facade.PostFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@Tag(name = "Post")
public class PostController {

    @Autowired
    private PostFacade facade;

    @ApiOperation(value = "Get Post Timeline", notes = "", response = PostDTO.class, responseContainer = "Set")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostDTO.class, responseContainer = "Set") })
    @RequestMapping(value = "/api/v1/post/timeline",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<PageDTO<PostDTO>> getPostTimeline(@RequestParam(value = "page") Integer page,
                                                            @RequestParam(value = "size") Integer size,
                                                            @RequestParam(value = "sortBy", required = false) String sortBy,
                                                            @RequestParam(value = "direction", required = false) String direction,
                                                            @RequestParam(value = "tenantLogin") String tenantLogin,
                                                            @RequestParam(value = "appContext") String appContext,
                                                            @RequestParam(value = "domainContext", required = false) String domainContext,
                                                            @RequestParam(value = "languageContext", required = false) String languageContext,
                                                            @RequestParam(value = "locationContext", required = false) String locationContext,
                                                            @RequestParam(value = "postType") String postType,
                                                            @RequestParam(value = "postSubTypes", required = false) Set<String> postSubTypes,
                                                            @RequestParam(value = "searchTerm",required = false) String searchTerm) {
        PageDTO<PostDTO> result = facade.getPostTimeline(searchTerm,tenantLogin, appContext, domainContext, languageContext,
                locationContext, postType, postSubTypes, page, size, sortBy, direction);
        return ResponseEntity.status(result.hasNext() ? HttpStatus.PARTIAL_CONTENT : HttpStatus.OK).body(result);
    }
}
