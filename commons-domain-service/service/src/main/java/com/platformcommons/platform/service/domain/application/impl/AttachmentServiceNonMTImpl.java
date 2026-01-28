package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.service.attachment.config.AttachmentClient;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.domain.application.AttachmentServiceNonMT;
import com.platformcommons.platform.service.domain.domain.repo.AttachmentNonMTRepository;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceNonMTImpl implements AttachmentServiceNonMT {

    @Autowired
    private AttachmentNonMTRepository attachmentNonMTRepository;

    @Autowired
    private AttachmentClient attachmentClient;

    @Override
    public List<AttachmentDTO> getAttachmentsByEntityIdAndEntityType(Long entityId, String entityType) {
        List<Attachment> attachmentList = this.attachmentNonMTRepository.findByEntityTypeAndEntityIdIn(entityType, Collections.singleton(entityId));
        return attachmentList.stream().map(this::getAttachmentDTO).collect(Collectors.toList());
    }

    private AttachmentDTO getAttachmentDTO(Attachment attachment) {
        return AttachmentDTO.builder().id(attachment.getId()).fileName(attachment.getFileName()).mimeType(attachment.getMimeType()).createdByUser(attachment.getCreatedByUser()).createdTimestamp(attachment.getCreatedTimestamp()).signedURL(attachment.getIsURLUpload() != null && attachment.getIsURLUpload() ? null : this.attachmentClient.getSignedURL(attachment.getPath())).fileSizeInBytes(attachment.getFileSizeInBytes()).completeURL(attachment.getCompleteURL()).build();
    }
}
