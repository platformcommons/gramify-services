package com.platformcommons.platform.service.profile.controller;

import com.platformcommons.platform.service.profile.dto.IEIdCardTemplateDTO;
import com.platformcommons.platform.service.profile.facade.IEIdCardTemplateFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "IEIdCardTemplate")
public class IEIdCardTemplateController {
    @Autowired
    private IEIdCardTemplateFacade ieIdCardTemplateFacade;

    @ApiOperation(value = "delete a IEIdCardTemplate", nickname = "deleteIEIdCardTemplate", notes = "", tags={ "IEIdCardTemplate", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "/api/v1/IEIdCardTemplate/{id}/{reason}",
            method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteIEIdCardTemplate(@PathVariable Long id,@PathVariable String reason) {
        ieIdCardTemplateFacade.delete(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "Create a new IEIdCardTemplate", nickname = "createIEIdCardTemplate", notes = "", response = IEIdCardTemplateDTO.class, tags={ "IEIdCardTemplate", })
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = IEIdCardTemplateDTO.class) })
    @RequestMapping(value = "/api/v1/IEIdCardTemplate",
            produces = { "application/json" },
            method = RequestMethod.POST)
    public ResponseEntity<Long> createIEIdCardTemplate(@Valid @RequestBody IEIdCardTemplateDTO ieIdCardTemplateDTO) {
        Long createdIEIdCardTemplate = ieIdCardTemplateFacade.save(ieIdCardTemplateDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdIEIdCardTemplate);
    }

    @ApiOperation(value = "Update an existing IEIdCardTemplate", nickname = "updateIEIdCardTemplate", notes = "", response = IEIdCardTemplateDTO.class, tags={ "IEIdCardTemplate", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IEIdCardTemplateDTO.class) })
    @RequestMapping(value = "/api/v1/IEIdCardTemplate",
            produces = { "application/json" },
            method = RequestMethod.PATCH)
    public ResponseEntity<IEIdCardTemplateDTO> updateIEIdCardTemplate(@Valid @RequestBody IEIdCardTemplateDTO ieIdCardTemplateDTO) {
        IEIdCardTemplateDTO updatedIEIdCardTemplate = ieIdCardTemplateFacade.update(ieIdCardTemplateDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedIEIdCardTemplate);
    }

    @ApiOperation(value = "Get IEIdCardTemplate by ID", nickname = "getIEIdCardTemplateById", notes = "", response = IEIdCardTemplateDTO.class, tags={ "IEIdCardTemplate", })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = IEIdCardTemplateDTO.class),
            @ApiResponse(code = 404, message = "Not Found") })
    @RequestMapping(value = "/api/v1/IEIdCardTemplate/{id}",
            produces = { "application/json" },
            method = RequestMethod.GET)
    public ResponseEntity<IEIdCardTemplateDTO> getIEIdCardTemplateById(@PathVariable Long id) {
        IEIdCardTemplateDTO ieIdCardTemplateDTO = ieIdCardTemplateFacade.getById(id);
        if (ieIdCardTemplateDTO == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(ieIdCardTemplateDTO);
    }



}
