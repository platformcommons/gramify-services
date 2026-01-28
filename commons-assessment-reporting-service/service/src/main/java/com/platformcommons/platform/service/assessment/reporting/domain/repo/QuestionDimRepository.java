package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface QuestionDimRepository extends BaseReportRepository<QuestionDim, Long> {
    @Query("select q from QuestionDim q where q.parentQuestionId in (:parentQuestionIds) and q.language=:language and q.linkedSystem = :linkedSystem")
    Set<QuestionDim> findByParentQuestionIdIn(Collection<Long> parentQuestionIds, String language,String linkedSystem);

    @Query("select q from QuestionDim q where q.questionId = ?1 and q.language = ?2 and q.linkedSystem = ?3")
    Optional<QuestionDim> findByQuestionIdAndLanguageAndLinkedSystem(Long questionId, String language, String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from QuestionDim q where q.questionId in ?1 and q.linkedSystem = ?2")
    int deleteByQuestionIdIn(Collection<Long> questionIds, @Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from QuestionDim q where q.id in ?1 and q.linkedSystem= ?2")
    int deleteByIdIn(Collection<Long> ids, @Param("linkedSystem") String linkedSystem);

    @Query("select q from QuestionDim q where q.questionId = ?1 and q.linkedSystem=?2 ")
    List<QuestionDim> findByQuestionId(Long questionId, @Param("linkedSystem") String linkedSystem);

    @Query("select q from QuestionDim q where q.questionId in ?1 and q.language = ?2 and q.linkedSystem = ?3 ")
    Set<QuestionDim> findByQuestionIdInAndLanguage(Collection<Long> questionIds, String languages, @Param("linkedSystem") String linkedSystem);

    @Query("update QuestionDim " +
           " set parentQuestionId = :id," +
           "     childDefaultOptionId = :childDefaultOptionId " +
           " where questionId in (:childQuestionList) and linkedSystem = :linkedSystem")
    @Modifying
    @Transactional
    void updateParentQuestionIdAndDefaultOptionId(Set<Long> childQuestionList, Long id, Long childDefaultOptionId, String linkedSystem);

    @Query("update QuestionDim " +
            " set parentQuestionId = null," +
            "     childDefaultOptionId = null " +
            " where parentQuestionId = :id and linkedSystem = :linkedSystem")
    @Modifying
    @Transactional
    void unlink(Long id,String linkedSystem);

    @Query(value = "select question_dim.question_id from question_dim " +
            "join options_dim od on question_dim.child_default_option_id = od.default_option_id and od.language=question_dim.language " +
            "where question_dim.parent_question_id = :parentQuestionId and " +
            "      od.language='ENG' and " +
            "      question_dim.linked_system = :linkedSystem and " +
            "      od.linked_system = :linkedSystem  " +
            "order by od.sequence;",nativeQuery = true)
    List<Long> getSequencedChildQuestions(Long parentQuestionId, String linkedSystem);

    @Query("SELECT dim from QuestionDim dim where dim.parentQuestionId = :questionId and dim.linkedSystem = :systemEvent")
    Set<QuestionDim> getChildQuestionsDimById(Long questionId, String systemEvent);
}
