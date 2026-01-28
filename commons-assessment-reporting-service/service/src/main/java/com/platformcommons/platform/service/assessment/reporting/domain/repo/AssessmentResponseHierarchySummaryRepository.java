package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentResponseHierarchySummary;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AssessmentResponseHierarchySummaryRepository extends JpaRepository<AssessmentResponseHierarchySummary, Long> {

    @Query("SELECT summary FROM AssessmentResponseHierarchySummary summary " +
            " WHERE  summary.assessmentInstanceId = :assessmentInstanceId and " +
            " summary.actorType = :actorType and " +
            " summary.hierarchyLevel = :hierarchyLevel and " +
            " summary.resolvedHierarchyValue = :resolvedHierarchyValue ")
    Optional<AssessmentResponseHierarchySummary> findByAssessmentInstanceIdAndActorTypeAndHierarchyLevelAndResolvedHierarchyValue(
            Long assessmentInstanceId,
            String actorType,
            Long hierarchyLevel,
            String resolvedHierarchyValue
    );

    @Modifying
    @Query("UPDATE AssessmentResponseHierarchySummary s " +
            "SET s.responseCount = s.responseCount + :count " +
            "WHERE s.assessmentInstanceId = :instanceId " +
            "AND s.actorType = :actorType " +
            "AND s.hierarchyLevel = :level " +
            "AND s.resolvedHierarchyValue = :value")
    int incrementResponseCount(
            Long instanceId,
            String actorType,
            Long level,
            String value,
            Long count);


    @Modifying
    @Query("DELETE FROM AssessmentResponseHierarchySummary s WHERE s.assessmentId = :assessmentId")
    void deleteByAssessmentId(@Param("assessmentId") Long assessmentId);
}
