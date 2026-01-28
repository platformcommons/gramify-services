package com.platformcommons.platform.service.profile.facade.assembler.impl;

import com.platformcommons.platform.service.attachment.config.AttachmentClient;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.profile.facade.assembler.AttachmentAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AttachmentAssemblerImpl implements AttachmentAssembler {
    @Autowired
    private AttachmentClient attachmentClient;
    @Override
    public AttachmentDTO toDto(Attachment attachment) {
        return AttachmentDTO.builder()
                .id(attachment.getId())
                .attachmentKind(attachment.getAttachmentKind())
                .attachmentKindIdentifier(attachment.getAttachmentKindIdentifier())
                .attachmentKindMeta(attachment.getEntityMeta())
                .sequence(attachment.getSequence())
                .fileName(attachment.getFileName())
                .mimeType(attachment.getMimeType())
                .createdByUser(attachment.getCreatedByUser())
                .createdTimestamp(attachment.getCreatedTimestamp())
                .signedURL(attachment.getIsURLUpload() != null && attachment.getIsURLUpload() ? null : this.attachmentClient.getSignedURL(attachment.getPath()))
                .fileSizeInBytes(attachment.getFileSizeInBytes())
                .completeURL(attachment.getCompleteURL())
                .build();
    }
}
