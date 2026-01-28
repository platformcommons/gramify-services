package com.platformcommons.platform.service.assessment.reporting.application;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionsDim;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface OptionsDimService {
    void createOptionDim(Set<OptionsDim> optionDims);

    Set<OptionsDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentDTO assessment);

    void updateOptionDim(QuestionDTO questionDTO);

    void syncAssessmentData(AssessmentSyncContext syncContext);

    Set<OptionsDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentInstanceDim assessmentInstanceDim);

    List<Map<String,Object>> getOptionResponseByAssessmentInstanceId(Long assessmentInstanceID);

    Set<OptionsDim> getByQuestionId(Long childQuestionId, String baseLanguage);

    void createParentLink(QuestionDTO questionDTO);

    void  unlink(Long id);

    Set<OptionsDim> getOptionsDimByQuestionIds(Set<Long> singleton);

    Set<OptionsDim> getByQuestionIds(Set<Long> distinctQuestionIds, String baseLanguage);
}
