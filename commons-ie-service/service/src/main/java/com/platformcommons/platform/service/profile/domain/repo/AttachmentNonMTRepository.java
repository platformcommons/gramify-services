package com.platformcommons.platform.service.profile.domain.repo;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentNonMTRepository extends NonMultiTenantBaseRepository<Attachment,Long> {

    @Query("FROM Attachment e WHERE e.entityId = :entityId and e.entityType = :entityType " +
            "and (:attachmentKind IS NULL OR e.attachmentKind = :attachmentKind) and " +
            "(:attachmentKindIdentifier IS NULL OR e.attachmentKindIdentifier = :attachmentKindIdentifier)")
    Page<Attachment> findByEntityIdAndEntityTypeAndAttachmentKindAndIdentifier(Long entityId, String entityType,
                                                                               String attachmentKind, String attachmentKindIdentifier, Pageable pageable);

}
