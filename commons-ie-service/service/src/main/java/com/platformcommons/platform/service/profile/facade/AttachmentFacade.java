package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface AttachmentFacade {

    List<AttachmentDTO> getAttachments(Long entityId, String entityType, String attachmentKind, String attachmentKindIdentifier
            , Integer page, Integer size, String sortBy, String direction);

    AttachmentDTO postAttachment(Long entityId, String entityType, String attachmentKind, String attachmentKindIdentifier,
                                 String attachmentKindMeta, Long sequence, MultipartFile file) throws IOException;

    AttachmentDTO postAttachmentByURL(Long entityId, String entityType, String attachmentKind,
                                      String attachmentKindIdentifier, String attachmentKindMeta, Long sequence, String url, String fileName);

    AttachmentDTO uploadAttachmentWithEntityType(MultipartFile file, String entityType);

    void deleteAttachment(Long entityId, String entityType, Long attachmentId, String reason);

    AttachmentDTO uploadAttachmentIeIcon(MultipartFile file, Long ieId) throws IOException;
}
