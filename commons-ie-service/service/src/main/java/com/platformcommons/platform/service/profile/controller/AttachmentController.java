package com.platformcommons.platform.service.profile.controller;

import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.profile.facade.AttachmentFacade;
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
@Tag(name = "Attachment")
public class AttachmentController {

    @Autowired
    private AttachmentFacade attachmentFacade;


    @ApiOperation(value = "Get Attachments For entityId and entityType", nickname = "getAttachmentsForEntityIdAndEntityType", notes = "", response = List.class,
            tags={ "Attachment"})
    @ApiResponses(value = {
            @ApiResponse(code = 206, message = "OK", response = List.class) })
    @RequestMapping(value = "/api/v1/attachment",
            produces = MediaType.APPLICATION_JSON_VALUE,
            method = RequestMethod.GET)
    public ResponseEntity<List<AttachmentDTO>> getAttachmentForEntityIdAndEntityType(@NotNull @Valid @RequestParam(value = "entityId") Long entityId,
                                                                                 @NotNull @RequestParam(value = "entityType") String entityType,
                                                                                 @RequestParam(value = "attachmentKind",required = false) String attachmentKind,
                                                                                 @RequestParam(value = "attachmentKindIdentifier",required = false) String attachmentKindIdentifier,
                                                                                 @RequestParam Integer page,
                                                                                 @RequestParam Integer size,
                                                                                 @RequestParam(required = false) String sortBy,
                                                                                 @RequestParam(required = false) String direction
                                                                              ) {
        return ResponseEntity.status(HttpStatus.OK).body(
                attachmentFacade.getAttachments(entityId,entityType,attachmentKind,attachmentKindIdentifier,page,size,sortBy,direction)
        );
    }

    @ApiOperation(value = "createAttachment", nickname = "createAttachment", notes = "This API create attachment for entityId,entityType ", tags = {"Attachment"})
    @RequestMapping(value = "/api/v1/attachment/upload",
            method = RequestMethod.POST)
    public ResponseEntity<AttachmentDTO> uploadAttachment(@RequestPart(required = true) MultipartFile file,
                                                                @RequestParam(required = true) Long entityId,
                                                                @RequestParam(required = true) String entityType,
                                                                @RequestParam(required = false) String attachmentKind,
                                                                @RequestParam(required = false) String attachmentKindIdentifier,
                                                                @RequestParam(required = false) String attachmentKindMeta,
                                                                @RequestParam(required = false) Long sequence
                                                                ) throws Exception {

        return ResponseEntity.ok(attachmentFacade.postAttachment(entityId, entityType, attachmentKind,
                attachmentKindIdentifier, attachmentKindMeta, sequence, file));
    }

    @ApiOperation(value = "createAttachmentUsingURL", nickname = "createAttachmentUsingURL", notes = "This API create attachment for entityId,entityType using URL ", tags = {"Attachment"})
    @RequestMapping(value = "/api/v1/attachment/upload/url",
            method = RequestMethod.POST)
    public ResponseEntity<AttachmentDTO> createAttachmentUsingURL(@RequestParam(required = true) String url,
                                                                  @RequestParam(required = true) String fileName,
                                                                         @RequestParam(required = true) Long entityId,
                                                                         @RequestParam(required = true) String entityType,
                                                                         @RequestParam(required = false) String attachmentKind,
                                                                         @RequestParam(required = false) String attachmentKindIdentifier,
                                                                         @RequestParam(required = false) String attachmentKindMeta,
                                                                         @RequestParam(required = false) Long sequence
    ) {

        return ResponseEntity.ok(attachmentFacade.postAttachmentByURL(entityId, entityType, attachmentKind,
                attachmentKindIdentifier, attachmentKindMeta, sequence, url, fileName));
    }

    @ApiOperation(value = "upload Attachment with entity type", nickname = "uploadAttachmentWithEntityType", notes = "",tags={ "Attachment", })
    @RequestMapping(value = "/api/v1/attachment/entity-type",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<AttachmentDTO> uploadAttachmentWithEntityType(@RequestPart MultipartFile file,@RequestParam(required = false) String entityType){
        return ResponseEntity.status(HttpStatus.OK).body(attachmentFacade.uploadAttachmentWithEntityType(file,entityType));
    }

    @ApiOperation(value = "delete a attachment", nickname = "deleteAttachment", notes = "", tags={ "Attachment", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "/api/v1/attachment",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAttachment(@NotNull @Valid @RequestParam(value = "entityId", required = true) Long entityId,
                                          @NotNull @Valid @RequestParam(value = "entityType", required = true) String entityType,
                                          @NotNull @Valid @RequestParam(value = "attachmentId", required = true) Long attachmentId,
                                          @NotNull @Valid @RequestParam(value = "reason", required = true) String reason){
        attachmentFacade.deleteAttachment(entityId,entityType,attachmentId,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ApiOperation(value = "upload Attachment with entity id", nickname = "uploadAttachmentWithEntityId",
            notes = "",tags={ "Attachment", })
    @RequestMapping(value = "/api/v1/attachment/ie-icon",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<AttachmentDTO> uploadAttachmentForIeIcon(@RequestPart MultipartFile file,@RequestParam Long ieId) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(attachmentFacade.uploadAttachmentIeIcon(file,ieId));
    }

}
