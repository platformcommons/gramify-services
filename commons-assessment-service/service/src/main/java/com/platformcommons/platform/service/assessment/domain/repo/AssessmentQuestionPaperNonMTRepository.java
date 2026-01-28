package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

public interface AssessmentQuestionPaperNonMTRepository extends NonMultiTenantBaseRepository<AssessmentQuestionPaper, Long> {

    @Transactional
    @Modifying
    @Query(value = "UPDATE assessment_question_paper AS aqp   " +
            "            JOIN assessment AS a ON aqp.assessment = a.id   " +
            "            JOIN assessment_instance AS ai ON a.id = ai.assessment   " +
            "            SET aqp.duplicated_count = IFNULL(aqp.duplicated_count,0)+1   " +
            "            WHERE ai.id = ?1 ;",nativeQuery = true)
    void updateDuplicatedCountById(Long instance);

    @Query("select a from AssessmentQuestionPaper a " +
            "join a.assessment.assessmentInstanceList instance " +
            "where instance.id = :instanceId"
    )
    Set<AssessmentQuestionPaper> getAllAssessmentQuestionPapersOfInstance(Long instanceId);
}
