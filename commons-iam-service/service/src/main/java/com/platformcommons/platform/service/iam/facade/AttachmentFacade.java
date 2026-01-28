package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import com.platformcommons.platform.service.iam.domain.vo.AttachmentVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

public interface AttachmentFacade {

    AttachmentDTO createAttachment(Long entityId, String entityType, String attachmentKind, String attachmentName,
                                   MultipartFile file, Boolean isPublic, String attachmentKindMeta, String attachmentKindIdentifier);
    void deleteAttachment(Long attachmentId, String reason);

    AttachmentDTO getById(Long id);

    Set<AttachmentVO> getAllAlbums(Long entityId, String entityType);

    PageDTO<AttachmentDTO> getAttachmentsByPage(Long entityId, String entityType, String attachmentKindMeta,
                                                  Integer page, Integer size, String sortBy, String direction);

    Map<Long,Set<AttachmentDTO>> getAttachmentsByEntityIDsAndType(String entityType, Set<Long> entityIds);
}
