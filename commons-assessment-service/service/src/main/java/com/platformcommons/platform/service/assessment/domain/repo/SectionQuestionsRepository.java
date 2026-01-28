package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.SectionQuestions;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface SectionQuestionsRepository extends BaseRepository<SectionQuestions, Long> {

    @Query(value = "select sq.id from SectionQuestions sq where sq.questionId = :questionId")
    List<Long> getSectionQuestionIdByQuestionId(Long questionId);

    @Modifying
    @Transactional
    @Query("update SectionQuestions sq set sq.isActive = false where sq.id IN (:sectionQuestionIds)")
    void deactivateRemovedSectionQuestionsList(Set<Long> sectionQuestionIds);

    @Query("SELECT sq FROm SectionQuestions sq WHERE sq.questionId = :id ")
    Optional<SectionQuestions> getSectionQuestionByQuestionId(Long id);
}