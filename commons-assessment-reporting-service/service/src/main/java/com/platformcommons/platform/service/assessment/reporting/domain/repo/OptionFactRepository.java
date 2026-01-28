package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.OptionFact;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

public interface OptionFactRepository extends BaseReportRepository<OptionFact, Long> {
    @Transactional
    @Modifying
    @Query("delete from OptionFact o where o.assessmentId = :assessmentId and o.linkedSystem = :linkedSystem")
    int deleteByAssessmentId(Long assessmentId, @Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from OptionFact o where o.assessmentInstanceId = :assessmentInstanceId and o.linkedSystem = :linkedSystem")
    int deleteByAssessmentInstanceId(Long assessmentInstanceId, @Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query(value = "update option_fact f  " +
                   "set f.response_count = f.response_count + :responseCount " +
                   "where f.id=:id and linked_system = :linkedSystem",nativeQuery = true)
    int updateOptionFactOptionResponseCount(Long responseCount, Long id, @Param("linkedSystem") String linkedSystem);

    @Transactional
    @Modifying
    @Query(value = "update option_fact f  " +
            "set f.response_count = f.response_count + :responseCount " +
            "where f.id=:id and linked_system = :linkedSystem",nativeQuery = true)
    int updateOptionFactMTFOptionResponseCount(Long responseCount, Long id, @Param("linkedSystem") String linkedSystem);

    @Query("select o from OptionFact o where o.assessmentInstanceId = :assessmentInstanceId and o.linkedSystem=:linkedSystem")
    List<OptionFact> findByAssessmentInstanceId(Long assessmentInstanceId, @Param("linkedSystem") String linkedSystem);

    @Transactional
    @Modifying
    @Query("delete from OptionFact o where o.questionId = :questionId and o.linkedSystem=:systemEvent")
    void deleteByQuestionId(Long questionId, String systemEvent);

    @Query("select distinct o.defaultOptionId from OptionFact o where o.parentQuestionId in (:questionIds) and o.linkedSystem=:systemEvent")
    Set<Long> findByParentQuestionId(Set<Long> questionIds, String systemEvent);

    @Query("select distinct o.defaultOptionId from OptionFact o where o.childDefaultOptionId in (:ids) and o.linkedSystem=:systemEvent")
    Set<Long> findByChildDefaultOptionId(Set<Long> ids, String systemEvent);

    @Transactional
    @Modifying
    @Query("delete from OptionFact o where o.defaultOptionId in (:ids) and o.linkedSystem = :systemEvent")
    void deleteByDefaultOptionIdIn(Set<Long> ids, String systemEvent);
}