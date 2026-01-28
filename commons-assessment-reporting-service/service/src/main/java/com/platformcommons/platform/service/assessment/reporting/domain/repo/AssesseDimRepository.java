package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.AssesseDim;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public interface AssesseDimRepository extends BaseReportRepository<AssesseDim, Long> {
    @Transactional
    @Modifying
    @Query("delete from AssesseDim a where a.assessmentId = :assessmentId and a.linkedSystem=:linkedSystem")
    int deleteByAssessmentId(Long assessmentId, @Param("linkedSystem") String linkedSystem);
    @Query("select (count(a) > 0) from AssesseDim a where a.assessmentId = :assessmentId and a.linkedSystem=:linkedSystem")
    boolean existsByAssessmentId(Long assessmentId, @Param("linkedSystem") String linkedSystem);

    @Query(value = "select group_concat(distinct assessee_actor_id) as userIds from assesse_dim where assessment_instance_id in (:ids)", nativeQuery = true)
    String getUserIdsForInstance(Set<Long> ids);

    @Transactional
    @Modifying
    @Query("delete AssesseDim where assesseId=:id and linkedSystem=:systemEvent")
    void deleteDimByAssesseId(Long id, String systemEvent);


    @Query("SELECT assesse  FROM AssesseDim assesse WHERE assesse.assesseId = :assesseeId")
    Optional<AssesseDim> getAssesseDimByAssesseId(Long assesseeId);

    Page<AssesseDim> findByAssessmentId(Long assessmentId, Pageable pageable);
}