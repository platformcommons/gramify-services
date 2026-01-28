package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentResponseHierarchyTimelineSummary;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AssessmentResponseHierarchyTimelineSummaryRepository extends JpaRepository<AssessmentResponseHierarchyTimelineSummary, Long> {

    @Query("SELECT s FROM  AssessmentResponseHierarchyTimelineSummary s " +
            "WHERE s.assessmentInstanceId = :instanceId " +
            "AND s.actorType = :actorType " +
            "AND s.hierarchyLevel = :level " +
            "AND s.hierarchyResolvedValue = :value " +
            "AND s.timelineType = :timelineType " +
            "AND s.timelineValue = :timelineValue")
    Optional<AssessmentResponseHierarchyTimelineSummary> findByAssessmentIdAndActorTypeAndActorIdAndHierarchyLevelAndHierarchyResolvedValueAndTimelineTypeAndTimelineValue(
            Long instanceId,
            String actorType,
            Long level,
            String value,
            String timelineType,
            String timelineValue
    );
    @Modifying
    @Query("UPDATE AssessmentResponseHierarchyTimelineSummary s " +
            "SET s.responseCount = s.responseCount + :count " +
            "WHERE s.assessmentInstanceId = :instanceId " +
            "AND s.actorType = :actorType " +
            "AND s.hierarchyLevel = :level " +
            "AND s.hierarchyResolvedValue = :value " +
            "AND s.timelineType = :timelineType " +
            "AND s.timelineValue = :timelineValue")
    void incrementResponseCount(
            Long instanceId,
            String actorType,
            Long level,
            String value,
            String timelineType,
            String timelineValue,
            Long count
    );

    @Modifying
    @Query("DELETE FROM AssessmentResponseHierarchyTimelineSummary s WHERE s.assessmentId = :assessmentId")
    void deleteByAssessmentId(@Param("assessmentId") Long assessmentId);
}
