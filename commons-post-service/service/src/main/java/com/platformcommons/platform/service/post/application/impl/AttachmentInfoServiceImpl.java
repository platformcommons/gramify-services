package com.platformcommons.platform.service.post.application.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.attachment.config.AttachmentClient;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.domain.repo.AttachmentRepository;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.post.application.AttachmentInfoService;
import com.platformcommons.platform.service.post.application.constant.PostConstant;
import com.platformcommons.platform.service.post.domain.repo.AttachmentNonMTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentInfoServiceImpl implements AttachmentInfoService {

    @Autowired
    private AttachmentNonMTRepository attachmentNonMTRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentClient attachmentClient;

    @Autowired
    private AttachmentService attachmentService;


    @Override
    public List<AttachmentDTO> getAttachmentsByEntityIdAndEntityType(Long entityId, String entityType) {
        List<Attachment> attachmentList = this.attachmentNonMTRepository.findByEntityTypeAndEntityIdIn(entityType, Collections.singleton(entityId));
        return (List)attachmentList.stream().map(this::getAttachmentDTO).collect(Collectors.toList());
    }

    @Override
    public Attachment findByEntityTypeAndEntityIdAndAttachmentId(String entityType, Long entityId, Long attachmentId) {
        return attachmentNonMTRepository.findByEntityTypeAndEntityIdAndId(entityType,entityId,attachmentId)
                .orElseThrow(() -> new NotFoundException(String.format
                        ("Attachment Not found With Id=%d and entityId=%d and entityType %s",attachmentId,entityId,entityType)));
    }

    private AttachmentDTO getAttachmentDTO(Attachment attachment) {
        return AttachmentDTO.builder()
                .id(attachment.getId())
                .fileName(attachment.getFileName())
                .mimeType(attachment.getMimeType())
                .createdByUser(attachment.getCreatedByUser())
                .createdTimestamp(attachment.getCreatedTimestamp())
                .signedURL(attachment.getIsURLUpload() != null && attachment.getIsURLUpload() ? null : this.attachmentClient.getSignedURL(attachment.getPath()))
                .fileSizeInBytes(attachment.getFileSizeInBytes())
                .completeURL(attachment.getCompleteURL())
                .name(attachment.getName())
                .attachmentKind(attachment.getAttachmentKind())
                .attachmentKindIdentifier(attachment.getAttachmentKindIdentifier())
                .sequence(attachment.getSequence())
                .attachmentKindMeta(attachment.getAttachmentKindMeta())
                .build();
    }

    public List<AttachmentDTO> getAttachmentDTOList(List<Attachment> attachmentList) {
        List<AttachmentDTO> attachmentDTOList = new ArrayList<>();
        attachmentList.forEach(attachment -> attachmentDTOList.add(getAttachmentDTO(attachment)));
        return attachmentDTOList;
    }

    @Override
    public Attachment uploadAttachment(MultipartFile file, String entityType,Long entityId) {
        Attachment attachment= new Attachment();
        attachment.setEntityId(entityId);
        attachment.setEntityType(entityType==null ? PostConstant.ENTITY_TYPE_POST : entityType);
        attachment.setPublic(Boolean.TRUE);
        try {
            return attachmentService.uploadAttachment(file, attachment);
        }catch (IOException ex){
            throw new InvalidInputException(ex.getMessage());
        }
    }

    @Override
    public void deleteById(Long id, String reason) {
        Attachment attachment = getById(id);
        if((attachment.getIsURLUpload() == null || attachment.getIsURLUpload().equals(Boolean.FALSE))
                && attachment.getPath() != null ) {
            attachmentClient.deleteAttachment(attachment);
        }
        attachment.deactivate(reason);
        this.attachmentRepository.save(attachment);

    }

    public Attachment getById(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Attachment with id %d not found",id)));
    }
}
