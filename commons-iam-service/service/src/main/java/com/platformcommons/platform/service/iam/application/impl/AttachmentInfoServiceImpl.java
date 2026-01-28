
package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.attachment.config.AttachmentClient;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.domain.repo.AttachmentRepository;
import com.platformcommons.platform.service.iam.application.AttachmentInfoService;
import com.platformcommons.platform.service.iam.domain.repo.AttachmentNonMTRepository;
import com.platformcommons.platform.service.iam.domain.vo.AttachmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AttachmentInfoServiceImpl implements AttachmentInfoService {

    @Autowired
    private AttachmentNonMTRepository attachmentNonMTRepository;

    @Autowired
    private AttachmentRepository attachmentRepository;

    @Autowired
    private AttachmentClient attachmentClient;

    @Override
    public Attachment getById(Long id) {
        return attachmentRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Attachment with id %d not found",id)));
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

    @Override
    public Set<AttachmentVO> getAllAlbums(Long entityId, String entityType) {
        return attachmentNonMTRepository.getAllAlbums(entityId, entityType);
    }

    @Override
    public Page<Attachment> getAttachmentsByPage(Long entityId, String entityType, String attachmentKindMeta, Pageable pageRequest) {
        return attachmentNonMTRepository.getAttachmentsByPage(entityId, entityType, attachmentKindMeta, pageRequest);
    }
}