package com.platformcommons.platform.service.domain.domain.repo;

import com.platformcommons.platform.service.domain.domain.TagHierarchy;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.Set;

public interface TagHierarchyRepository extends NonMultiTenantBaseRepository<TagHierarchy, Long> {


    @Query("select th from TagHierarchy th " +
            "where th.parentTag.id in (:parentTagIds) " +
            " and ( th.depth <= :depth OR :depth is NULL ) " +
            " and ( th.parentTag.context = :context OR :context IS NULL) " +
            " and ( th.tag.type = :type OR :type IS NULL) " +
            " and th.isActive = true " +
            " and th.tag.isActive = true " )
    Set<TagHierarchy> findSubTags(Set<Long> parentTagIds,
                          Long depth,
                          String context,
                          String type);

    @Query("select distinct th.tag.id from TagHierarchy th " +
            "where th.parentTag.id in (:parentTagIds) " +
            " and ( th.depth <= :depth OR :depth is NULL ) " +
            " and ( th.parentTag.context = :context OR :context IS NULL) " +
            " and ( th.tag.type = :type OR :type IS NULL) " +
            " and th.isActive = true " +
            " and th.tag.isActive = true " )
    Set<Long> findSubTagsIds(Set<Long> parentTagIds,
                                  Long depth,
                                  String context,
                                  String type);

    @Query("SELECT th FROM TagHierarchy th WHERE th.parentTag.id = :parentTagId AND th.tag.id = :tagId AND th.isActive = true")
    TagHierarchy findByParentTagIdAndTagId(Long parentTagId, Long tagId);

    @Query("SELECT th FROM TagHierarchy th WHERE th.parentTag.id = :parentTagId AND th.depth = :depth " +
           "AND (:context IS NULL OR th.parentTag.context = :context) " +
           "AND (:type IS NULL OR th.tag.type = :type) " +
           "AND th.isActive = true AND th.tag.isActive = true")
    Set<TagHierarchy> findByParentTagIdAndExactDepth(Long parentTagId, Long depth, String context, String type);

}