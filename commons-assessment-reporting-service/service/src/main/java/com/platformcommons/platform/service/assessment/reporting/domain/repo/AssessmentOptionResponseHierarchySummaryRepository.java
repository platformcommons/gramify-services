package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentOptionResponseHierarchySummary;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AssessmentOptionResponseHierarchySummaryRepository extends JpaRepository<AssessmentOptionResponseHierarchySummary, Long> {

    @Query("SELECT s FROM AssessmentOptionResponseHierarchySummary s " +
            "WHERE s.assessmentInstanceId = :instanceId " +
            "AND s.actorType = :actorType " +
            "AND s.hierarchyLevel = :level " +
            "AND s.resolvedHierarchyValue = :value " +
            "AND s.optionId = :optionId " +
            "AND s.defaultOptionId = :defaultOptionId") // Adjust null handling if defaultOptionId can be null
    Optional<AssessmentOptionResponseHierarchySummary> findByKeyFields(
            Long instanceId,
            String actorType,
            Long level,
            String value,
            Long optionId,
            Long defaultOptionId // Adjust null handling if defaultOptionId can be null
    );

    @Modifying
    @Query("UPDATE AssessmentOptionResponseHierarchySummary s " +
            "SET s.responseCount = s.responseCount + :count " +
            "WHERE s.assessmentInstanceId = :instanceId " +
            "AND s.actorType = :actorType " +
            "AND s.hierarchyLevel = :level " +
            "AND s.resolvedHierarchyValue = :value " +
            "AND s.optionId = :optionId " +
            "AND s.defaultOptionId = :defaultOptionId") // Adjust null handling if defaultOptionId can be null
    int incrementResponseCount(
            Long instanceId,
            String actorType,
            Long level,
            String value,
            Long optionId,
            Long defaultOptionId, // Adjust null handling if defaultOptionId can be null
            Long count
    );


    @Modifying
    @Query("DELETE FROM AssessmentOptionResponseHierarchySummary s WHERE s.assessmentId = :assessmentId")
    void deleteByAssessmentId(Long assessmentId);
}
