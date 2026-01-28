package com.platformcommons.platform.service.post.application;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface AttachmentInfoService {

    List<AttachmentDTO> getAttachmentsByEntityIdAndEntityType(Long entityId, String entityType);

    Attachment findByEntityTypeAndEntityIdAndAttachmentId(String entityType, Long entityId, Long attachmentId);

    List<AttachmentDTO> getAttachmentDTOList(List<Attachment> attachmentList);

    Attachment uploadAttachment(MultipartFile file, String entityType,Long entityId);

    void deleteById(Long id, String reason);
}
