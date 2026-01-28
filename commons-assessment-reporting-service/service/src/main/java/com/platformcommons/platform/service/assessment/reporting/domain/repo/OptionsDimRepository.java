package com.platformcommons.platform.service.assessment.reporting.domain.repo;

import com.platformcommons.platform.service.assessment.reporting.domain.OptionsDim;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OptionsDimRepository extends BaseReportRepository<OptionsDim, Long> {
    @Transactional
    @Modifying
    @Query("update OptionsDim o set o.parentQuestionId = :parentQuestionId, o.childDefaultOptionId = :defaultOptionId where o.questionId in (:questionIds) and o.linkedSystem = :linkedSystem")
    int updateParentQuestionIdAndDefaultOptionIdByQuestionIdIn(Long parentQuestionId, Long defaultOptionId, Collection<Long> questionIds,String linkedSystem);
    @Query("select o from OptionsDim o where o.questionId = ?1 and o.language = ?2 and o.linkedSystem = ?3")
    Set<OptionsDim> findByQuestionIdAndLanguageAndLinkedSystem(Long questionId, String language, String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from OptionsDim o where o.questionId in (:questionIds) and o.linkedSystem = :linkedSystem")
    int deleteByQuestionIdIn(Collection<Long> questionIds, @Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from OptionsDim o where o.defaultOptionId in (:defaultOptionIds) and o.linkedSystem = :linkedSystem")
    int deleteByDefaultOptionIdIn(Collection<Long> defaultOptionIds, @Param("linkedSystem") String linkedSystem);
    @Query("select o from OptionsDim o where o.questionId = :questionId and o.linkedSystem = :linkedSystem")
    Set<OptionsDim> findByQuestionId(Long questionId, @Param("linkedSystem") String linkedSystem);
    @Transactional
    @Modifying
    @Query("delete from OptionsDim o where o.questionId = :questionId and o.linkedSystem = :linkedSystem and o.defaultOptionId is not null")
    int deleteByQuestionIdForDefaultOptionId(Long questionId, @Param("linkedSystem") String linkedSystem);
    @Query("select o from OptionsDim o where o.questionId in (:questionIds) and o.linkedSystem = :linkedSystem")
    Set<OptionsDim> findByQuestionIdIn(Collection<Long> questionIds, @Param("linkedSystem") String linkedSystem);


    @Transactional
    @Query(nativeQuery = true,
            value = "select max(question_fact.assessment_instance_id) as assessmentInstanceId, " +
                    "       max(option_fact.response_count)           as responseCount, " +
                    "       max(option_fact.question_id)              as questionId, " +
                    "       option_fact.options_id                    as optionId, " +
                    "       question_fact.section_question_id         as sectionQuestionId " +
                    " from question_fact " +
                    "         join option_fact on option_fact.question_id = question_fact.question_id " +
                    " where question_fact.assessment_instance_id = :assessmentInstanceId and option_fact.linked_system = :linkedSystem " +
                    " group by option_fact.options_id, question_fact.section_question_id;"
    )
    List<Map<String, Object>> getOptionResponse(Long assessmentInstanceId, @Param("linkedSystem") String linkedSystem);

    @Query("update OptionsDim dim" +
            " set dim.parentQuestionId = :parentQuestionId," +
            "     dim.childDefaultOptionId = :childDefaultOptionId " +
            " where dim.questionId in (:childQuestionList) and dim.linkedSystem = :linkedSystem")
    @Modifying
    @Transactional
    void updateParentQuestionIdAndDefaultOptionId(Set<Long> childQuestionList, Long parentQuestionId, Long childDefaultOptionId, String linkedSystem);

    @Query("update OptionsDim dim" +
            " set dim.parentQuestionId   = null " +
            " where dim.parentQuestionId = :questionId and " +
            "       dim.linkedSystem     = :systemEvent ")
    @Modifying
    @Transactional
    void unlink(Long questionId, String systemEvent);

    @Transactional
    @Modifying
    @Query("delete from OptionsDim o where o.questionId = :questionId and o.linkedSystem = :systemEvent and o.mtfOptionId is not null")
    void deleteByQuestionIdForMtfOptionId(Long questionId, String systemEvent);

    @Query("select o from OptionsDim o where o.parentQuestionId in (:ids) and o.linkedSystem = :systemEvent")
    Set<OptionsDim> findByParentQuestionIdIn(Set<Long> ids, String systemEvent);

    Set<OptionsDim> findByOptionsIdIn(Set<Long> optionIds);
}