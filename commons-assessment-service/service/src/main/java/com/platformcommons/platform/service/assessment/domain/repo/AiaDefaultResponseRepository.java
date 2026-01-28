package com.platformcommons.platform.service.assessment.domain.repo;

import com.platformcommons.platform.service.entity.repo.base.BaseRepository;
import com.platformcommons.platform.service.assessment.domain.AiaDefaultResponse;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AiaDefaultResponseRepository extends BaseRepository<AiaDefaultResponse, Long> {

    Optional<AiaDefaultResponse> findByIdAndIsActive(Long aIADefaultResponseId, Boolean aTrue);

    Optional<AiaDefaultResponse> findByuuidAndIsActive(String aIADefaultResponseId, Boolean aTrue);

    @Query("SELECT COUNT(aiadr.id) > 0 FROM AiaDefaultResponse aiadr WHERE aiadr.sectionQuestion.id in :sectionQuestionIds")
    Boolean existsAIADefaultResponseForSectionQuestions(List<Long> sectionQuestionIds);

    @Query(nativeQuery = true,value = "select " +
            "                aia.assessment_instance as assessmentInstanceID, " +
            "                a.section_question as sectionQuestionID, " +
            "                sq.question_id as questionID," +
            "                coalesce(a.option_id,o.options) as optionId, " +
            "                count(coalesce(a.option_id,o.options)) as responseCount " +
            "    from " +
            "             assessment_instance_assesse aia " +
            "join aia_default_response a on aia.id = a.assessment_instance_assesse " +
            "LEFT join dr_objective_response d on a.id = d.aiadefault_response " +
            "LEFT join default_options o on d.default_option = o.id " +
            "INNER JOIN section_questions sq on sq.id = a.section_question " +
            "where aia.assessment_instance = :assessmentInstanceID and coalesce(a.option_id,o.options) is not null " +
            "group by a.section_question, coalesce(a.option_id,o.options)")
    List<Map<String, Object>> getOptionResponse(Long assessmentInstanceID);

}