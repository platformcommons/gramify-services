package com.platformcommons.platform.service.post.controller;

import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.client.AttachmentAPI;
import com.platformcommons.platform.service.post.facade.AttachmentFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Set;

@RestController
@Tag(name="Attachment")
public class AttachmentController implements AttachmentAPI {

    @Autowired
    private AttachmentFacade attachmentFacade;

    @ApiOperation(value = "Upload Attachment", nickname = "uploadAttachment", notes = "",tags={ "Attachment", })
    @RequestMapping(value = "/api/v1/attachment",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<AttachmentDTO> uploadAttachment(@RequestPart MultipartFile file,
                                                   @RequestParam(name = "entityType",required = false) String entityType,
                                                   @RequestParam(name = "entityId", required = false) Long entityId){
        return ResponseEntity.status(HttpStatus.OK).body(attachmentFacade.uploadAttachment(file,entityType,entityId));
    }

    @Override
    public ResponseEntity<Void> deleteAttachment(Long entityId, Long attachmentId, String reason, String entityType) {
        attachmentFacade.deleteAttachment(entityId,entityType,attachmentId,reason);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Delete attachment By Id", nickname = "deleteAttachment", notes = "This API deletes existing attachment By Id", tags={ "Attachment", })
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "No Content") })
    @RequestMapping(value = "/api/v2/attachments",
            method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteAttachment(@RequestParam Long id, @RequestParam String reason) {
        attachmentFacade.deleteById(id,reason);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
    public ResponseEntity<AttachmentDTO> uploadBulkGenericProductVariety(@RequestParam(required = true) String url,
                                                                         @RequestParam(required = false) String fileName,
                                                                         @RequestParam(required = true) Long entityId,
                                                                         @RequestParam(required = true) String entityType,
                                                                         @RequestParam(required = false) String attachmentKind,
                                                                         @RequestParam(required = false) String attachmentKindIdentifier,
                                                                         @RequestParam(required = false) String attachmentKindMeta,
                                                                         @RequestParam(required = false) Long sequence
    ) {

        return ResponseEntity.ok(attachmentFacade.postAttachmentByURL(entityId, entityType, attachmentKind,
                attachmentKindIdentifier, attachmentKindMeta, sequence, url,fileName));
    }



    @ApiOperation(value = "upload Attachment with entity type", nickname = "uploadAttachmentWithEntityType", notes = "",tags={ "Attachment", })
    @RequestMapping(value = "/api/v1/attachment/entity-type/link",
            produces = { "application/json" },
            method = RequestMethod.POST)
    ResponseEntity<AttachmentDTO> uploadAttachmentWithEntityType(@RequestParam(required = true) String url,
                                                                 @NotNull @Valid @RequestParam(required = true) String entityType,
                                                                 @RequestParam(required = false) String fileName,
                                                                 @RequestParam(required = false) String attachmentKind){
        return ResponseEntity.status(HttpStatus.OK).body(attachmentFacade.uploadAttachmentWithEntityTypeWithURL(url,entityType,fileName,attachmentKind));
    }


}
