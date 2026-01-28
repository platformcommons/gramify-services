package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.SkillFact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface SkillFactRepository extends BaseReportRepository<SkillFact, Long> {
    @Transactional
    @Modifying
    @Query("delete from SkillFact s where s.assessmentId = :assessmentId and s.linkedSystem = :linkedSystem")
    int deleteByAssessmentId(Long assessmentId, @Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from SkillFact s where s.assessmentInstanceId = :assessmentInstanceId and s.linkedSystem = :linkedSystem")
    int deleteByAssessmentInstanceId(Long assessmentInstanceId, @Param("linkedSystem") String linkedSystem);

    @Transactional
    @Modifying
    @Query("delete from SkillFact s where s.assessmentId = :assessmentId and s.questionId = :id and s.linkedSystem = :linkedSystem")
    void deleteByAssessmentIdAndQuestionId(Long assessmentId, Long id, @Param("linkedSystem") String linkedSystem);
}