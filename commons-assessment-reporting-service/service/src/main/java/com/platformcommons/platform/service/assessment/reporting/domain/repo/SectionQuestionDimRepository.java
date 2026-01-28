package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface SectionQuestionDimRepository extends BaseReportRepository<SectionQuestionDim, Long> {
    @Transactional
    @Modifying
    @Query("delete from SectionQuestionDim s where s.assessmentId = :assessmentId and s.linkedSystem = :linkedSystem")
    int deleteByAssessmentId(Long assessmentId, @Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from SectionQuestionDim s where s.sectionQuestionId in (:sectionQuestionIds) and s.linkedSystem = :linkedSystem")
    int deleteBySectionQuestionIdIn(Collection<Long> sectionQuestionIds, @Param("linkedSystem") String linkedSystem);
    @Query("select s from SectionQuestionDim s where s.questionPaperId = :questionPaperId and s.linkedSystem = :linkedSystem")
    List<SectionQuestionDim> findByQuestionPaperId(Long questionPaperId, @Param("linkedSystem") String linkedSystem);
    @Query("select s from SectionQuestionDim s where s.assessmentId = :assessmentId and s.linkedSystem = :linkedSystem")
    Set<SectionQuestionDim> findByAssessmentId(Long assessmentId, @Param("linkedSystem") String linkedSystem);

    @Query(value = "select section_question_id from section_question_dim " +
            " join question_paper_section_dim qps on section_question_dim.question_paper_section_id = qps.question_paper_section_id " +
            " join assessment_instance_dim instanceDim on instanceDim.assessment_id = qps.assessment_id " +
            " where qps.assessment_id in (:assessmentId) and " +
            "       section_question_dim.linked_system = :linkedSystem  and " +
            "       qps.linked_system = :linkedSystem and " +
            "       instanceDim.linked_system = :linkedSystem " +
            " order by instanceDim.sequence,instanceDim.assessment_instance_id,qps.sequence, section_question_dim.sequence" , nativeQuery = true)
    List<Long> getSequencedSectionQuestion(Set<Long> assessmentId, String linkedSystem);
}