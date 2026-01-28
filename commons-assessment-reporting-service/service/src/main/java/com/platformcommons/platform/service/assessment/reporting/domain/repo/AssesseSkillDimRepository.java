package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.AssesseSkillDim;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface AssesseSkillDimRepository extends BaseReportRepository<AssesseSkillDim,Long> {
    @Transactional
    @Modifying
    @Query("delete from AssesseSkillDim a where a.assesseId in (:assesseIds) and a.linkedSystem= :linkedSystem")
    int deleteByAssesseIdIn(Collection<Long> assesseIds, @Param("linkedSystem") String linkedSystem);
}
