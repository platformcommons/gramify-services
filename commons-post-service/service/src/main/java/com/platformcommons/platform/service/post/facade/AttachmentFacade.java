package com.platformcommons.platform.service.post.facade;

import com.platformcommons.platform.service.dto.commons.AttachmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AttachmentFacade {

    void deleteAttachment(Long entityId, String entityType, Long attachmentId, String reason);

    AttachmentDTO uploadAttachment(MultipartFile file, String entityType, Long entityId);

    void deleteById(Long id, String reason);

    AttachmentDTO postAttachment(Long entityId, String entityType, String attachmentKind, String attachmentKindIdentifier,
                                 String attachmentKindMeta, Long sequence, MultipartFile file) throws IOException;

    AttachmentDTO postAttachmentByURL(Long entityId, String entityType, String attachmentKind,
                                      String attachmentKindIdentifier, String attachmentKindMeta, Long sequence, String url, String fileName);


    AttachmentDTO uploadAttachmentWithEntityTypeWithURL(String url, String entityType, String fileName, String attachmentKind);
}
