package com.platformcommons.platform.service.iam.facade.assembler.impl;

import com.platformcommons.platform.service.attachment.config.AttachmentClient;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.iam.facade.assembler.AttachmentDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class AttachmentDTOAssemblerImpl implements AttachmentDTOAssembler {

    @Autowired
    private AttachmentClient attachmentClient;

    @Override
    public AttachmentDTO toDTO(Attachment attachment) {
        if (attachment == null) {
            return null;
        }

        AttachmentDTO.AttachmentDTOBuilder attachmentDTO = AttachmentDTO.builder();

        attachmentDTO.id(attachment.getId());
        attachmentDTO.isActive(attachment.getIsActive());
        attachmentDTO.inactiveReason(attachment.getInactiveReason());
        attachmentDTO.fileName(attachment.getFileName());
        attachmentDTO.mimeType(attachment.getMimeType());
        attachmentDTO.createdTimestamp(attachment.getCreatedTimestamp());
        attachmentDTO.createdByUser(attachment.getCreatedByUser());
        attachmentDTO.fileSizeInBytes(attachment.getFileSizeInBytes());
        attachmentDTO.completeURL(attachment.getCompleteURL());
        attachmentDTO.sequence(attachment.getSequence());
        attachmentDTO.name(attachment.getName());
        attachmentDTO.attachmentKind(attachment.getAttachmentKind());
        attachmentDTO.attachmentKindMeta(attachment.getAttachmentKindMeta());
        attachmentDTO.attachmentKindIdentifier(attachment.getAttachmentKindIdentifier());
        attachmentDTO.entityId(attachment.getEntityId());
        attachmentDTO.entityType(attachment.getEntityType());
        attachmentDTO.isPublic(attachment.isPublic());
        if(!attachment.isPublic()) {
            attachmentDTO.signedURL(attachment.getIsURLUpload() != null && attachment.getIsURLUpload() ?
                    null : this.attachmentClient.getSignedURL(attachment.getPath()));
        }
        return attachmentDTO.build();
    }

    @Override
    public Attachment fromDTO(AttachmentDTO dto) {
        if (dto == null) {
            return null;
        }

        Attachment.AttachmentBuilder attachmentBuilder = Attachment.builder();

        attachmentBuilder.id(dto.getId());
        attachmentBuilder.isActive(dto.getIsActive());
        attachmentBuilder.inactiveReason(dto.getInactiveReason());
        attachmentBuilder.fileName(dto.getFileName());
        attachmentBuilder.mimeType(dto.getMimeType());
        attachmentBuilder.completeURL(dto.getCompleteURL());
        attachmentBuilder.attachmentKind(dto.getAttachmentKind());
        attachmentBuilder.attachmentKindMeta(dto.getAttachmentKindMeta());
        attachmentBuilder.attachmentKindIdentifier(dto.getAttachmentKindIdentifier());
        attachmentBuilder.sequence(dto.getSequence());
        attachmentBuilder.name(dto.getName());
        attachmentBuilder.isURLUpload(dto.getIsUploadedByURL());
        attachmentBuilder.entityId(dto.getEntityId());
        attachmentBuilder.entityType(dto.getEntityType());
        attachmentBuilder.fileSizeInBytes(dto.getFileSizeInBytes());
        attachmentBuilder.isPublic(dto.getIsPublic() == null ? Boolean.TRUE : dto.getIsPublic());

        return attachmentBuilder.build();
    }



    @Override
    public List<AttachmentDTO> toDTOs(List<Attachment> attachments) {
        if (attachments == null) {
            return null;
        }

        List<AttachmentDTO> list = new ArrayList<>(Math.max((int) (attachments.size() / .75f) + 1, 16));
        for (Attachment entity : attachments) {
            list.add(toDTO(entity));
        }

        return list;
    }

    @Override
    public List<Attachment> fromDTOs(List<AttachmentDTO> dtos) {
        if (dtos == null) {
            return null;
        }

        List<Attachment> list = new ArrayList<>(Math.max((int) (dtos.size() / .75f) + 1, 16));
        for (AttachmentDTO dto : dtos) {
            list.add(fromDTO(dto));
        }

        return list;
    }
}