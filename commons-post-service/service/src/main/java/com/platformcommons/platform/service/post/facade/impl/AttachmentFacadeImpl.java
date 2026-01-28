package com.platformcommons.platform.service.post.facade.impl;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.application.AttachmentInfoService;
import com.platformcommons.platform.service.post.facade.AttachmentFacade;
import com.platformcommons.platform.service.post.facade.assembler.AttachmentDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Component
public class AttachmentFacadeImpl implements AttachmentFacade {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentInfoService attachmentInfoService;

    @Autowired
    private AttachmentDTOAssembler attachmentDTOAssembler;

    @Override
    public void deleteAttachment(Long entityId, String entityType, Long attachmentId, String reason) {
        Attachment attachment = attachmentInfoService.findByEntityTypeAndEntityIdAndAttachmentId(entityType,entityId,attachmentId);
        isAuthorizedToUpdateAttachment(attachment);
        attachmentService.deleteAttachment(entityType,entityId,attachmentId,reason);
    }

    @Override
    public AttachmentDTO uploadAttachment(MultipartFile file, String entityType, Long entityId) {
        Attachment attachment = attachmentInfoService.uploadAttachment(file,entityType,entityId);
        return attachmentDTOAssembler.toDTO(attachment);
    }

    @Override
    public void deleteById(Long id, String reason) {
        attachmentInfoService.deleteById(id,reason);
    }

    private void isAuthorizedToUpdateAttachment(Attachment attachment) {
        boolean isAllowed = attachment.getCreatedByUser()
                .equals(PlatformSecurityUtil.getCurrentUserId())
                || PlatformSecurityUtil.isTenantAdmin();
        if (!isAllowed) throw new UnAuthorizedAccessException("User is not authorized to update this Attachment.");
    }

    @Override
    public AttachmentDTO postAttachment(Long entityId,String entityType,String attachmentKind,String attachmentKindIdentifier,
                                        String attachmentKindMeta,Long sequence, MultipartFile file) throws IOException {
        return attachmentDTOAssembler.toDTO(attachmentService.uploadAttachment(file, attachmentBuilder(entityId, entityType, attachmentKind, attachmentKindIdentifier,
                attachmentKindMeta, sequence)));
    }

    @Override
    public AttachmentDTO postAttachmentByURL(Long entityId, String entityType, String attachmentKind,
                                             String attachmentKindIdentifier, String attachmentKindMeta, Long sequence, String url, String fileName) {
        return attachmentDTOAssembler.toDTO(attachmentService.uploadAttachmentByURL( attachmentBuilderForURL(entityId, entityType, attachmentKind, attachmentKindIdentifier,
                attachmentKindMeta, sequence,url,fileName)));
    }


    @Override
    public AttachmentDTO uploadAttachmentWithEntityTypeWithURL(String url,String entityType, String fileName, String attachmentKind) {
        Attachment attachment = new Attachment();
        attachment.setEntityType(entityType);
        attachment.setCompleteURL(url);
        attachment.setFileName(fileName);
        attachment.setAttachmentKind(attachmentKind);
        attachment.setIsURLUpload(Boolean.TRUE);
        attachment.setPublic(Boolean.TRUE);
        return attachmentDTOAssembler.toDTO(attachmentService.uploadAttachmentByURL(attachment));
    }

    private Attachment attachmentBuilder(Long entityId, String entityType, String attachmentKind,
                                         String attachmentKindIdentifier, String attachmentKindMeta, Long sequence) {
        Attachment attachment = new Attachment();
        attachment.setEntityId(entityId);
        attachment.setEntityType(entityType);
        attachment.setAttachmentKind(attachmentKind);
        attachment.setAttachmentKindIdentifier(attachmentKindIdentifier);
        attachment.setSequence(sequence);
        attachment.setAttachmentKindMeta(attachmentKindMeta);
        attachment.setPublic(Boolean.TRUE);
        return attachment;
    }

    private Attachment attachmentBuilderForURL(Long entityId, String entityType, String attachmentKind,
                                               String attachmentKindIdentifier, String attachmentKindMeta, Long sequence,String url,String fileName) {
        Attachment attachment = new Attachment();
        attachment.setEntityId(entityId);
        attachment.setEntityType(entityType);
        attachment.setAttachmentKind(attachmentKind);
        attachment.setAttachmentKindIdentifier(attachmentKindIdentifier);
        attachment.setSequence(sequence);
        attachment.setAttachmentKindMeta(attachmentKindMeta);
        attachment.setCompleteURL(url);
        attachment.setFileName(fileName);
        attachment.setIsURLUpload(Boolean.TRUE);
        attachment.setPublic(Boolean.TRUE);
        return attachment;
    }
}
