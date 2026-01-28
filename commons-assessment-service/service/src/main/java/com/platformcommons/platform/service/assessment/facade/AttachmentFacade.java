package com.platformcommons.platform.service.assessment.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

public interface AttachmentFacade {

    AttachmentDTO createAttachment(Long entityId, String entityType, String attachmentKind, String attachmentName,
                                   MultipartFile file, Boolean isPublic, String attachmentKindMeta, String attachmentKindIdentifier);

    Map<Long,Set<AttachmentDTO>> getAttachmentsByEntityIDsAndType(String entityType, Set<Long> entityIds);
}
