package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.QuestionFact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface QuestionFactRepository extends BaseReportRepository<QuestionFact, Long> {

    @Transactional
    @Modifying
    @Query("delete from QuestionFact q where q.assessmentId = ?1 and q.linkedSystem = ?2")
    int deleteByAssessmentId(Long assessmentId, @Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from QuestionFact q where q.sectionQuestionId in (?1) and q.linkedSystem = ?2")
    int deleteBySectionQuestionIdIn(Collection<Long> sectionQuestionIds, @Param("linkedSystem") String linkedSystem);

    @Query("select q from QuestionFact q where q.assessmentInstanceId = ?1 and q.linkedSystem = ?2")
    List<QuestionFact> findByAssessmentInstanceId(Long assessmentInstanceId, @Param("linkedSystem") String linkedSystem);


    @Transactional
    @Modifying
    @Query(nativeQuery = true,value = "update question_fact f  " +
            "set f.responded_count = f.responded_count + :respondentCount, " +
            "f.correct_count = f.correct_count + :correctCount, " +
            "f.incorrect_count = f.incorrect_count + :incorrectCount, " +
            "f.skipped_count = f.skipped_count + :skippedCount " +
            "where id=:id and linked_system = :linkedSystem")
    int updateQuestionFact(Long respondentCount, Long correctCount, Long incorrectCount, Long skippedCount, Long id, @Param("linkedSystem") String linkedSystem);

    @Query("select q.assessmentId from QuestionFact q where q.sectionQuestionId = ?1 and q.linkedSystem = ?2")
    Set<Long> findAssessmentIdByQuestionId(Long id,@Param("linkedSystem") String linkedSystem);

    @Query("select fact from QuestionFact fact where fact.questionId in (:ids) and fact.linkedSystem = :systemEvent")
    Set<QuestionFact> findByQuestionIds(Set<Long> ids, String systemEvent);

    @Query("select fact from QuestionFact fact where fact.parentQuestionId in (:ids) and fact.linkedSystem = :systemEvent")
    Set<QuestionFact> findByParentQuestionId(Set<Long> ids, String systemEvent);

    @Query("SELECT fact FROM QuestionFact fact " +
            " where fact.questionId in (:ids) and " +
            "       fact.linkedSystem = :systemEvent")
    void deleteByQuestionIdIn(Set<Long> ids, String systemEvent);
}