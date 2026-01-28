package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.Set;

public interface AssessmentQuestionPaperRepository extends BaseRepository<AssessmentQuestionPaper, Long> {
    @Query("select (count(a) > 0) from AssessmentQuestionPaper a where a.assessment.id = ?1")
    boolean existsByAssessment_Id(Long id);

    @Query("select count(a) > 0 from AssessmentQuestionPaper a where a.id = ?1 and a.createdByUser = ?2")
    boolean existsByIdAndUserId(Long id, Long createdBy);

    @Query("select a from AssessmentQuestionPaper a where a.id = ?1 and a.createdByUser = ?2")
    Optional<AssessmentQuestionPaper> findByIdAndUserId(Long id, Long createdBy);

    @Query("select a from AssessmentQuestionPaper a where a.assessment.id = :assessmentId")
    Set<AssessmentQuestionPaper> findByAssessmentId(Long assessmentId);
}