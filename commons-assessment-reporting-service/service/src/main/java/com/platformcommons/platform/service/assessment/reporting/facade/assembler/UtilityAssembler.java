package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionsDim;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BinaryOperator;

public interface UtilityAssembler {


    String filterMLTexts(Set<MimicMLTextDTO> assessmentName, String baseLanguage);

    String getRefDataCode(MimicRefDataDTO assessmentMode);

    String getActorId(AssessmentInstanceActorDTO monitoredBy);

    String getActorType(AssessmentInstanceActorDTO monitoredBy);

    String getBaseLanguage(AssessmentDTO assessment);

    Double getTotalWeight(AssessmentQuestionPaperDTO questionPaper);

    Long getTotalQuestions(AssessmentQuestionPaperDTO questionPaper);

    Double getTotalWeight(QuestionPaperSectionDTO questionPaperSectionDTO);

    Long getMandatoryQuestion(QuestionPaperSectionDTO questionPaperSectionDTO);

    AssessmentReportSyncContext createContext(AssessmentInstanceDim assessmentInstanceDim, Set<SectionQuestionDim> sectionQuestionDims, Set<QuestionDim> questionDims, Set<OptionsDim> optionsDims);

    Double getScore(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context);

    Long getCorrectQuestions(AssessmentInstanceAssesseDTO assesseDTO);

    Double getScore(AiaDefaultResponseDTO aiaDefaultResponseDTO, AssessmentReportSyncContext context);

    Double getTotalWeightSectionQuestionDimDTO(List<SectionQuestionDimDTO> value);
    Double getTotalWeightQuestionDimDTO(List<QuestionDimDTO> value);

    Set<AiaDefaultResponseDTO> getCorrectQuestions(Set<AiaDefaultResponseDTO> responsesForSkill, Map<Long, Double> questionWeightMap);

    Set<AiaDefaultResponseDTO> getIncorrectQuestions(Set<AiaDefaultResponseDTO> responsesForSkill, Map<Long, Double> questionWeightMap);

    String getMultiselectResponseIds(AiaDefaultResponseDTO aiaDefaultResponseDTO);

    String getOptionText(OptionsDTO optionId, AssessmentDTO assessment);

    String getMultiselectResponse(AiaDefaultResponseDTO aiaDefaultResponseDTO, AssessmentDTO assessment);

    String getSubjectiveResponseIds(AiaDefaultResponseDTO aiaDefaultResponseDTO);
    String getSubjectiveResponses(AiaDefaultResponseDTO aiaDefaultResponseDTO);

    String getQuestionResponse(String optionsText, String multiselectResponse, String subjectiveResponses);

    Long getCorrectQuestions(AiaDefaultResponseDTO aiaDefaultResponseDTO, AssessmentReportSyncContext context);

    Long getOptionCount(QuestionDTO questionDTO);

    Long getCorrectOptions(QuestionDTO questionDTO);
    static <T> BinaryOperator<List<T>> getOperator(){
        return (existingValue, newValue) -> {
            existingValue.addAll(newValue);
            return existingValue;
        };
    }

    String getChildQuestionId(DefaultOptionsDTO defaultOptionsDTO);

    String getLanguageCode(MimicMLTextDTO language);

    String getOptionText(OptionsDTO options, String language);

    String getBaseLanguage(String baseLanguage);
}
