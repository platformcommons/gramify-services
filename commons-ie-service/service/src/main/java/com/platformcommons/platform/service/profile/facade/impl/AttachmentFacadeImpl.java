package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.profile.application.AttachmentServiceNonMT;
import com.platformcommons.platform.service.profile.application.constant.ServiceConstant;
import com.platformcommons.platform.service.profile.facade.AttachmentFacade;
import com.platformcommons.platform.service.profile.facade.IeFacade;
import com.platformcommons.platform.service.profile.facade.assembler.AttachmentAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AttachmentFacadeImpl implements AttachmentFacade {

    @Autowired
    private AttachmentServiceNonMT attachmentServiceNonMT;

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentAssembler assembler;

    @Autowired
    private IeFacade ieFacade;

    private static  final String TRADER_ENTITY_TYPE= "ENTITY_TYPE.TRADER";

    @Override
    public List<AttachmentDTO> getAttachments(Long entityId, String entityType, String attachmentKind, String attachmentKindIdentifier
            , Integer page, Integer size, String sortBy, String direction) {
        Page<Attachment> result = attachmentServiceNonMT.getAttachmentsByEntityIdAndEntityTypeAndAttachmentKindAndIdentifierWithHierarchy
                (entityId, entityType, attachmentKind, attachmentKindIdentifier, page, size, sortBy, direction);

         return result.getContent().stream().map(assembler::toDto).collect(Collectors.toList());
    }

    @Override
    public AttachmentDTO postAttachment(Long entityId,String entityType,String attachmentKind,String attachmentKindIdentifier,
                               String attachmentKindMeta,Long sequence, MultipartFile file) throws IOException {
       return assembler.toDto(attachmentService.uploadAttachment(file, attachmentBuilder(entityId, entityType, attachmentKind, attachmentKindIdentifier,
                attachmentKindMeta, sequence)));
    }

    @Override
    public AttachmentDTO postAttachmentByURL(Long entityId, String entityType, String attachmentKind,
                                             String attachmentKindIdentifier, String attachmentKindMeta, Long sequence, String url, String fileName) {
        return assembler.toDto(attachmentService.uploadAttachmentByURL( attachmentBuilderForURL(entityId, entityType, attachmentKind, attachmentKindIdentifier,
                attachmentKindMeta, sequence,url,fileName)));
    }

    @Override
    public AttachmentDTO uploadAttachmentWithEntityType(MultipartFile file, String entityType) {
        Attachment attachment= new Attachment();
        attachment.setEntityType(entityType==null?TRADER_ENTITY_TYPE:entityType);
        attachment.setPublic(Boolean.TRUE);
        try {
            attachment = attachmentService.uploadAttachment(file, attachment);
        }catch (IOException ex){
            throw new InvalidInputException(ex.getMessage());
        }
        return AttachmentDTO.builder()
                .id(attachment.getId())
                .mimeType(attachment.getMimeType())
                .completeURL(attachment.getCompleteURL())
                .fileName(attachment.getFileName())
                .createdByUser(attachment.getCreatedByUser())
                .build();
    }

    public void deleteAttachment(Long entityId, String entityType, Long attachmentId, String reason) {
        attachmentService.deleteAttachment(entityType,entityId,attachmentId,reason);
    }

    @Override
    public AttachmentDTO uploadAttachmentIeIcon(MultipartFile file, Long ieId) throws IOException {
        AttachmentDTO attachmentDTO= assembler.toDto(attachmentService.uploadAttachment(file, attachmentBuilder(ieId, ServiceConstant.ENTITY_TYPE_IE,
                "USER_LOGO", "LOGO",
                null, null)));
        String ieIcon= attachmentDTO.getCompleteURL();
        ieFacade.updateIeIcon(ieId,ieIcon);
        return  attachmentDTO;
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
