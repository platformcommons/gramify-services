package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import org.springframework.data.domain.Page;

public interface AttachmentServiceNonMT {

    Page<Attachment> getAttachmentsByEntityIdAndEntityTypeAndAttachmentKindAndIdentifierWithHierarchy(Long entityId,
                                                                                                      String entityType, String attachmentKind, String attachmentKindIdentifier,
                                                                                                      Integer page, Integer size, String sortBy, String direction);

}
