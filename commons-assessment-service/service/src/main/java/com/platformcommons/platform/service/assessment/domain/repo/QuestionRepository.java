package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface QuestionRepository extends BaseRepository<Question, Long> {
    @Query("SELECT q from Question q where q.duplicatedFrom=:id and q.isQuestionModified=false order by q.createdTimestamp")
    List<Question> getQuestionExistenceInTenancy(Long id, Pageable pageable);

    @Query("SELECT q from Question q " +
            " left join q.questionText as text " +
            "where ( q.domain               = :domain          OR :domain              is null ) and " +
            "      ( q.subDomain            = :subDomain       OR :subDomain           is Null ) and " +
            "      ( q.isQuestionModified   = true             OR q.isQuestionModified is null ) and " +
            "      ( q.questionType         = :questionType    OR :questionType        is null ) and " +
            "      ( q.questionSubtype      = :questionSubType OR :questionSubType     is null ) and " +
            "      ( text.languageCode      = :language        OR :language            is null ) "
    )
    Page<Question> getQuestions(String domain, String subDomain,
                                   String questionType, String questionSubType,
                                   String language, Pageable pageable );

    @Query(value = "select  docql.child_question_list " +
            " from question " +
            " join default_options d on question.id = d.question " +
            " join default_options_child_question_list docql on d.id = docql.default_options_id " +
            " where question.id in (:questionIds) and d.is_active ",nativeQuery = true)
    Set<Long> getChildQuestionsIds(Set<Long> questionIds);
}