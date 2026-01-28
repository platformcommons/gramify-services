package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.Question;
import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface QuestionNonMTRepository extends NonMultiTenantBaseRepository<Question, Long> {

    @Query(value = "select q from Question q where q.id in (:questionIds)")
    Set<Question> getQuestionByIds(Set<Long> questionIds);


    @Query("SELECT q.id FROM Question q WHERE q.isQuestionModified=true OR q.isQuestionModified IS NULL")
    Set<Long> getProjectionQuestion();

    @Query("SELECT sectionQuestion.questionId from AssessmentInstance ai " +
            "join ai.assessment.assessmentQuestionPaperList questionPaper " +
            "join  questionPaper.questionpapersectionList paperSection " +
            "join paperSection.sectionquestionsList sectionQuestion " +
            "where ai.id=:instanceId")
    Set<Long> getQuestionIdsByInstanceId(Long instanceId);

    @Transactional
    @Modifying
    @Query(value = "update question   " +
            "set duplicated_count = IFNULL(duplicated_count,0)+:frequency  " +
            "where question.id = :id ", nativeQuery = true)
    void updateQuestionCount(Long id, Long frequency);

    @Query("SELECT distinct cq FROM Question q " +
            " JOIN q.defaultOptionsList dop " +
            " JOIN dop.childQuestionList cq " +
            " WHERE q.id in(:questionIds) ")
    Set<Long> getChildQuestionIds(Set<Long> questionIds);

}
