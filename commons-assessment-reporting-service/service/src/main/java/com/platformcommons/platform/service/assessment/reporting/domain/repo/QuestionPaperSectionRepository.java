package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.QuestionPaperSectionDim;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface QuestionPaperSectionRepository extends BaseReportRepository<QuestionPaperSectionDim, Long> {
    @Transactional
    @Modifying
    @Query("delete from QuestionPaperSectionDim q where q.questionPaperId = :questionPaperId and q.linkedSystem = :linkedSystem")
    int deleteByQuestionPaperId(Long questionPaperId,@Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from QuestionPaperSectionDim q where q.assessmentId = :assessmentId and q.linkedSystem = :linkedSystem")
    int deleteByAssessmentId(Long assessmentId,@Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from QuestionPaperSectionDim q where q.questionPaperSectionId in (:questionPaperSectionIds) and q.linkedSystem = :linkedSystem")
    int deleteByQuestionPaperSectionIdIn(Collection<Long> questionPaperSectionIds,@Param("linkedSystem") String linkedSystem);
    @Query("select q from QuestionPaperSectionDim q where q.questionPaperId = :questionPaperId and q.linkedSystem = :linkedSystem")
    List<QuestionPaperSectionDim> findByQuestionPaperId(Long questionPaperId,@Param("linkedSystem") String linkedSystem);
}