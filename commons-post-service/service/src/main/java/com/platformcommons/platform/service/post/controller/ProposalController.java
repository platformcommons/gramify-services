package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.post.dto.PostDTO;
import com.platformcommons.platform.service.post.dto.ProposalDTO;
import com.platformcommons.platform.service.post.facade.ProposalFacade;
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
@Tag(name="Proposal")
public class ProposalController {


    @Autowired
    private ProposalFacade facade;


    @ApiOperation(value = "Save Proposal", nickname = "save", notes = "", response = Long.class, tags={ "Proposal", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = PostDTO.class) })
    @RequestMapping(value = "/api/v1/proposal",
            produces = { "application/json" },
            consumes = { "application/json" },
            method = RequestMethod.PATCH)
    ResponseEntity<Long> save(@Valid @RequestBody ProposalDTO body){
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(body));

    }


}
