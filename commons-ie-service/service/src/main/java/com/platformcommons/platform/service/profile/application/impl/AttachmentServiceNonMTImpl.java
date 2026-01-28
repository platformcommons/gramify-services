package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.attachment.config.AttachmentClient;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.profile.application.AttachmentServiceNonMT;
import com.platformcommons.platform.service.profile.domain.repo.AttachmentNonMTRepository;
import com.platformcommons.platform.service.utility.JPAUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AttachmentServiceNonMTImpl implements AttachmentServiceNonMT {

    @Autowired
    private AttachmentNonMTRepository attachmentNonMTRepository;

    @Autowired
    private AttachmentClient attachmentClient;

    @Override
    public Page<Attachment> getAttachmentsByEntityIdAndEntityTypeAndAttachmentKindAndIdentifierWithHierarchy(Long entityId,
                                                                                                             String entityType, String attachmentKind,
                                                                                                             String attachmentKindIdentifier,
                                                                                                             Integer page, Integer size, String sortBy,
                                                                                                             String direction) {

        Page<Attachment> attachments = attachmentNonMTRepository.findByEntityIdAndEntityTypeAndAttachmentKindAndIdentifier
                (entityId, entityType, attachmentKind, attachmentKindIdentifier,PageRequest.of(page, size, JPAUtility.convertToSort(sortBy, direction)));
        return attachments;
    }

}
