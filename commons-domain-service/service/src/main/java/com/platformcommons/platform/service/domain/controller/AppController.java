package com.platformcommons.platform.service.domain.controller;

import com.platformcommons.platform.service.domain.dto.AppDTO;
import com.platformcommons.platform.service.domain.facade.AppFacade;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.sdk.bulkupload.interfaces.dto.BulkUploadRequestDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@Tag(name = "App")
@RequestMapping("api/v1/apps")
public class AppController {

    @Autowired
    private AppFacade facade;

    @ApiOperation(value = "", tags = {"App"})
    @PostMapping
    public ResponseEntity<Long> createApp(@RequestBody AppDTO appDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(appDTO));
    }


    @ApiOperation(value = "", tags = {"App"})
    @PatchMapping
    public ResponseEntity<AppDTO> updateApp(@RequestBody AppDTO appDTO){
        return ResponseEntity.status(HttpStatus.OK).body(facade.update(appDTO));
    }

    @ApiOperation(value = "", tags = {"App"})
    @GetMapping
    public ResponseEntity<AppDTO> getById(@RequestParam Long id){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getById(id));
    }

    @ApiOperation(value = "", tags = {"App"})
    @GetMapping("/slug")
    public ResponseEntity<AppDTO> getBySlug(@RequestParam String slug){
        return ResponseEntity.status(HttpStatus.OK).body(facade.getBySlug(slug));
    }



    @ApiOperation(value = "Create Attachment For App", nickname = "createAttachmentForApp", notes = "",
            response = AttachmentDTO.class, tags={ "App"})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "CREATED", response = AttachmentDTO.class) })
    @RequestMapping(value = "/attachment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.POST)
    public ResponseEntity<AttachmentDTO> createAttachmentForDomain( @NotNull @Valid @RequestParam(value="appId",required = true) Long appId,
                                                                    @RequestPart(value = "file",required = true) MultipartFile file) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.createAttachment(appId,file));
    }

    @ApiOperation(value = "Get Attachments For App", nickname = "getAttachmentsForApp", notes = "", response = List.class,
            tags={ "App"})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = List.class) })
    @RequestMapping(value = "/attachment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<List<AttachmentDTO>> getAttachmentForDomain(@NotNull @Valid @RequestParam(value = "appId", required = true) Long appId) {
        return ResponseEntity.status(HttpStatus.OK).body(facade.getAttachments(appId));
    }
    @ApiOperation(value = "Upload App data", nickname = "Uploads App from .xlxs file",
            notes = "This API uploads all the app data provided with from excel file",
            response = BulkUploadRequestDTO.class, responseContainer = "Set", tags={ "App", })
    @PostMapping("/upload-applications")
    public ResponseEntity<BulkUploadRequestDTO> uploadApplications(@RequestPart MultipartFile file) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.uploadApplications(file));
    }
}
