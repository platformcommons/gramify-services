package com.platformcommons.platform.service.iam.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.attachment.config.AttachmentClient;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.domain.repo.AttachmentRepository;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.iam.application.AttachmentInfoService;
import com.platformcommons.platform.service.iam.domain.vo.AttachmentVO;
import com.platformcommons.platform.service.iam.facade.AttachmentFacade;
import com.platformcommons.platform.service.iam.facade.assembler.AttachmentDTOAssembler;
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

    @Autowired
    private AttachmentInfoService attachmentInfoService;

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
    public void deleteAttachment(Long attachmentId, String reason) {
        attachmentInfoService.deleteById(attachmentId, reason);
    }

    @Override
    public AttachmentDTO getById(Long id) {
        Attachment attachment = attachmentInfoService.getById(id);
        return assembler.toDTO(attachment);
    }


    @Override
    public Set<AttachmentVO> getAllAlbums(Long entityId, String entityType) {
        return attachmentInfoService.getAllAlbums(entityId, entityType);
    }

    @Override
    public PageDTO<AttachmentDTO> getAttachmentsByPage(Long entityId, String entityType, String attachmentKindMeta,
                                                         Integer page, Integer size, String sortBy, String direction) {



        if (sortBy == null) {
            sortBy = "id";
            direction = "asc";
        }
        Pageable pageRequest = PageRequest.of(page,size, JPAUtility.convertToSort(sortBy,direction));
        Page<Attachment> results = attachmentInfoService.getAttachmentsByPage(entityId, entityType, attachmentKindMeta, pageRequest);
        return new PageDTO<>(results.getContent()
                .stream()
                .map(it -> assembler.toDTO(it))
                .collect(Collectors.toCollection(LinkedHashSet::new)),
                results.hasNext(), results.getTotalElements());
    }


    @Override
    public Map<Long,Set<AttachmentDTO>> getAttachmentsByEntityIDsAndType(String entityType, Set<Long> entityIds){
        return attachmentService.getAttachmentsForEntityTypeAndEntityIds(entityType,entityIds);
    }
}
