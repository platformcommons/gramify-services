package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.assessment.domain.SectionQuestions;
import com.platformcommons.platform.service.assessment.domain.vo.SectionQuestionVO;
import com.platformcommons.platform.service.entity.repo.base.NonMultiTenantBaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface SectionQuestionsNonMTRepository extends NonMultiTenantBaseRepository<SectionQuestions,Long> {


    @Query("SELECT new com.platformcommons.platform.service.assessment.domain.vo.SectionQuestionVO(ai.assessment.id,aqp.id,qps.id,sq.id,q.id,q.questionType,sq.isMandatory) " +
            "FROM AssessmentInstance ai " +
            " JOIN ai.assessment.assessmentQuestionPaperList aqp " +
            " JOIn aqp.questionpapersectionList qps " +
            " JOIN qps.sectionquestionsList sq " +
            " JOIN Question q on sq.questionId=q.id " +
            " WHERE ai.id = :assessmentInstanceId ")
    Set<SectionQuestionVO> getSectionQuestionsByInstanceId(Long assessmentInstanceId);
}
