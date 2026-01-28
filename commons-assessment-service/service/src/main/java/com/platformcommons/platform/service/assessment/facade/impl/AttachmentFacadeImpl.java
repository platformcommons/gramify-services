package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.assessment.facade.AttachmentFacade;
import com.platformcommons.platform.service.assessment.facade.assembler.AttachmentDTOAssembler;
import com.platformcommons.platform.service.attachment.config.AttachmentClient;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.domain.repo.AttachmentRepository;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;

import com.platformcommons.platform.service.utility.JPAUtility;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Transactional
public class AttachmentFacadeImpl implements AttachmentFacade {

    @Autowired
    private AttachmentService attachmentService;

    @Autowired
    private AttachmentDTOAssembler assembler;

    @Autowired
    private AttachmentClient attachmentClient;


    @Override
    public AttachmentDTO createAttachment(Long entityId, String entityType, String attachmentKind, String attachmentName,
                                          MultipartFile file, Boolean isPublic, String attachmentKindMeta, String attachmentKindIdentifier) {
        Attachment attachment = new Attachment();
        attachment.setEntityId(entityId);
        attachment.setEntityType(entityType);
        attachment.setAttachmentKind(attachmentKind);
        attachment.setAttachmentKindMeta(attachmentKindMeta);
        attachment.setAttachmentKindIdentifier(attachmentKindIdentifier);
        attachment.setPublic(isPublic != null ? isPublic:Boolean.TRUE);
        attachment.setName(attachmentName);
        try {
            attachment =  attachmentService.uploadAttachment(file,attachment);
        } catch (IOException e) {
            throw new InvalidInputException("File could not be uploaded");
        }
        return assembler.toDTO(attachment);
    }


    @Override
    public Map<Long,Set<AttachmentDTO>> getAttachmentsByEntityIDsAndType(String entityType, Set<Long> entityIds){
        return attachmentService.getAttachmentsForEntityTypeAndEntityIds(entityType,entityIds);
    }
}
