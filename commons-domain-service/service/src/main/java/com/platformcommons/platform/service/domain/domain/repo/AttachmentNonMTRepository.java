package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AttachmentNonMTRepository extends NonMultiTenantBaseRepository<Attachment, Long> {
    @Query("FROM Attachment where entityType=:entityType and entityId in (:entityIds)")
    List<Attachment> findByEntityTypeAndEntityIdIn(String entityType, Set<Long> entityIds);

    @Query("FROM Attachment where entityType=:entityType and entityId=:entityId and id=:id")
    Optional<Attachment> findByEntityTypeAndEntityIdAndId(String entityType, Long entityId, Long id);
}