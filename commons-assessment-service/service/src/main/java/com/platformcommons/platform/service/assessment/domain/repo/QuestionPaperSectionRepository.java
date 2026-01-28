package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.QuestionPaperSection;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface QuestionPaperSectionRepository extends BaseRepository<QuestionPaperSection, Long> {

    @Modifying
    @Transactional
    @Query("update QuestionPaperSection qps set qps.isActive = false where qps.id IN (:questionPaperSectionIds)")
    void deactivateRemovedSectionPaperQuestions(Set<Long> questionPaperSectionIds);

}