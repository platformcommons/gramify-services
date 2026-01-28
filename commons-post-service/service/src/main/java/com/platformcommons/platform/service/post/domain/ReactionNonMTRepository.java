package com.platformcommons.platform.service.post.domain;

import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import com.platformcommons.platform.service.post.domain.vo.ReactionCountVO;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ReactionNonMTRepository extends NonMultiTenantBaseRepository<Reaction,Long> {

    @Query("SELECT COUNT(r) FROM #{#entityName} r " +
            " WHERE r.reactedOnEntityId = :entityId " +
            " AND r.reactedOnEntityType = :entityType " +
            " AND r.tenantId = :tenantId " +
            " AND r.isActive = 1 ")
    Long findTotalCountByEntityIdAndEntityType(Long entityId, String entityType, Long tenantId);




    @Query("SELECT new com.platformcommons.platform.service.post.domain.vo.ReactionCountVO( " +
            " COUNT(r), r.reactedOnEntityId, r.reactedOnEntityType) " +
            " FROM #{#entityName} r " +
            " WHERE r.reactedOnEntityId IN :entityIds " +
            " AND r.reactedOnEntityType = :entityType " +
            " AND ( :reactionType is NULL OR r.reactionType = :reactionType ) "+
            " AND r.tenantId = :tenantId " +
            " AND r.isActive = 1 " +
            " GROUP BY r.reactedOnEntityId, r.reactedOnEntityType ")
    Set<ReactionCountVO> findTotalCountByEntityIdsAndEntityType(
            Set<Long> entityIds,
            String entityType,
            String reactionType,
            Long tenantId);
}
