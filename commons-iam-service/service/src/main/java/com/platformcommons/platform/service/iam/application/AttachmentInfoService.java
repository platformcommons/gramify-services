package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.iam.domain.vo.AttachmentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public interface AttachmentInfoService {

    Attachment getById(Long id);

    void deleteById(Long id, String reason);

    Set<AttachmentVO> getAllAlbums(Long entityId, String entityType);

    Page<Attachment> getAttachmentsByPage(Long entityId, String entityType, String attachmentKindMeta, Pageable pageRequest);
}