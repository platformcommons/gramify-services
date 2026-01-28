package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAssesse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface AssessmentInstanceAssesseRepository extends BaseRepository<AssessmentInstanceAssesse, Long> {
    @Query("select count(a) from AssessmentInstanceAssesse a where a.assessmentInstance.assessment.id = ?1")
    long countByAssessmentInstance_Assessment_Id(Long id);

    @Query("select a from AssessmentInstanceAssesse a where a.assessmentInstance.assessment.id = ?1")
    Page<AssessmentInstanceAssesse> findByAssessmentId(Long assessmentId, PageRequest of);

    @Query(value = "select a from AssessmentInstanceAssesse a " +
            " where a.assessedForEntityId in (:assessedForEntityIds) " +
            " and a.assessedForEntityType = :assessedForEntityType " +
            " and a.assessmentInstance.id in (:assessmentInstanceIds)")
    @Transactional(readOnly = true)
    Set<AssessmentInstanceAssesse> findByAssessedForEntityIdAndAssessedForEntityTypeAndAssessmentInstance_Ids(List<String> assessedForEntityIds, String assessedForEntityType, List<Long> assessmentInstanceIds);

    @Query(value = "select a from AssessmentInstanceAssesse a " +
            " where a.assessee.actorId in (:actorIds) " +
            " and a.assessee.actorType = :actorType " +
            " and a.assessmentInstance.id in (:assessmentInstanceIds)")
    @Transactional(readOnly = true)
    Set<AssessmentInstanceAssesse> getAssessmentInstanceAssesseByAssesse(List<String> actorIds, String actorType, List<Long> assessmentInstanceIds);
    boolean existsByUuid(String uuid);
}