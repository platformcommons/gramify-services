
package com.platformcommons.platform.service.iam.domain.repo;

import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.iam.domain.vo.AttachmentVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface AttachmentNonMTRepository extends NonMultiTenantBaseRepository<Attachment,Long> {

    @Query(" SELECT new com.platformcommons.platform.service.iam.domain.vo.AttachmentVO(a.attachmentKindMeta, a.attachmentKindIdentifier, " +
            " COUNT(a.id)) " +
            " FROM Attachment a " +
            " WHERE a.entityType = :entityType AND a.entityId = :entityId AND a.isActive = 1 " +
            " GROUP BY a.attachmentKindMeta, a.attachmentKindIdentifier ")
    Set<AttachmentVO> getAllAlbums(Long entityId, String entityType);

    @Query(" SELECT a FROM #{#entityName} a " +
            " WHERE a.entityType = :entityType " +
            " AND a.entityId = :entityId " +
            " AND (:attachmentKindMeta IS NULL OR a.attachmentKindMeta = :attachmentKindMeta) " +
            " AND a.isActive = 1 ")
    Page<Attachment> getAttachmentsByPage(Long entityId, String entityType, String attachmentKindMeta, Pageable pageRequest);
}
