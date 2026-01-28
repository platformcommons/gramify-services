package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.facade.assembler.AttachmentDTOAssembler;
import com.platformcommons.platform.service.attachment.config.AttachmentClient;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


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
        Attachment attachment = new Attachment();

        attachment.setId(dto.getId());
        attachment.setFileName(dto.getFileName());
        attachment.setMimeType(dto.getMimeType());
        attachment.setFileSizeInBytes(dto.getFileSizeInBytes());
        attachment.setCompleteURL(dto.getCompleteURL());
        attachment.setSequence(dto.getSequence());
        attachment.setName(dto.getName());
        attachment.setAttachmentKind(dto.getAttachmentKind());
        attachment.setAttachmentKindMeta(dto.getAttachmentKindMeta());
        attachment.setAttachmentKindIdentifier(dto.getAttachmentKindIdentifier());


        return attachment;
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