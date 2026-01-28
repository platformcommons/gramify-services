package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.AssesseResponseFact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface AssesseResponseFactRepository extends BaseReportRepository<AssesseResponseFact, Long> {
    @Transactional
    @Modifying
    @Query("delete from AssesseResponseFact a where a.assessmentId = :assessmentId and a.linkedSystem=:linkedSystem")
    int deleteByAssessmentId(Long assessmentId,@Param("linkedSystem") String linkedSystem);

    @Transactional
    @Modifying
    @Query("delete AssesseResponseFact " +
            "where assessmentInstanceAssesseId=:id and linkedSystem=:linkedSystem")
    void deleteDimByAssesseId(Long id, String linkedSystem);

    List<AssesseResponseFact> findByAssessmentInstanceAssesseIdIn(Set<Long> assessmentInstanceAssesseIds);
}