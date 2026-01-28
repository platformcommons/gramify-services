package com.platformcommons.platform.service.assessment.controller;



import com.platformcommons.platform.service.assessment.facade.AttachmentFacade;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

@RestController
@Tag(name = "attachment-controller")
@RequiredArgsConstructor
public class AttachmentController {

    @Autowired
    private AttachmentFacade attachmentFacade;


    @GetMapping("api/v1/attachments/entity-type")
    public ResponseEntity<Map<Long,Set<AttachmentDTO>>> getAttachmentsByPage(@RequestParam(value = "entityType") String entityType,
                                                                             @RequestParam(value = "entityIds") Set<Long> entityIds){
        return ResponseEntity.status(HttpStatus.OK).body(attachmentFacade.getAttachmentsByEntityIDsAndType(entityType, entityIds));
    }


    @ApiOperation(value = "Upload Attachment", response = AttachmentDTO.class)
    @PostMapping("api/v1/attachments")
    public ResponseEntity<AttachmentDTO> postAttachment(@RequestParam(required = false)  Long entityId,
                                                        @RequestParam(required = true)  String entityType,
                                                        @RequestParam(required = false)  String attachmentKind,
                                                        @RequestParam(required = false)  String attachmentName,
                                                        @RequestPart(required = false) MultipartFile file,
                                                        @RequestParam(required = false) Boolean isPublic,
                                                        @RequestParam(required = false) String attachmentKindMeta,
                                                        @RequestParam(required = false) String attachmentKindIdentifier){
        isPublic = isPublic!=null ? isPublic: Boolean.TRUE;
        return ResponseEntity.status(HttpStatus.OK).body(attachmentFacade.createAttachment(entityId, entityType,
                attachmentKind,attachmentName, file, isPublic, attachmentKindMeta, attachmentKindIdentifier));
    }

}
