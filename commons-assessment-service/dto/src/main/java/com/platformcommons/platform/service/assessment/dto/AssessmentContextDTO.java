package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@NoArgsConstructor
@Getter
@Setter
public class AssessmentContextDTO {

    private AssessmentDTO assessment;
    private AssessmentInstanceDTO assessmentInstanceDTO;
    private AssessmentQuestionPaperDTO assessmentQuestionPaperDTO;
    private Map<Long, QuestionDTO> questionDTOMap;

    @Builder
    public AssessmentContextDTO(AssessmentDTO assessment, AssessmentInstanceDTO assessmentInstanceDTO, AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, Set<QuestionDTO> questionDTOMap) {
        this.assessment = assessment;
        this.assessmentInstanceDTO = assessmentInstanceDTO;
        this.assessmentQuestionPaperDTO = assessmentQuestionPaperDTO;
        this.questionDTOMap = questionDTOMap.stream().collect(Collectors.toMap(QuestionDTO::getId, Function.identity()));
    }
    
    public QuestionDTO getQuestionId(Long questionId){
        return questionDTOMap.get(questionId);
    }
    
}
