package com.platformcommons.platform.service.assessment.reporting.application.context;

import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
public class AssessmentSyncContext {

    private AssessmentDTO assessment;
    private AssessmentInstanceDTO assessmentInstanceDTO;
    private AssessmentQuestionPaperDTO assessmentQuestionPaperDTO;
    private Map<Long, QuestionDTO> questionDTOMap;

    @Builder
    public AssessmentSyncContext(AssessmentDTO assessment, AssessmentInstanceDTO assessmentInstanceDTO, AssessmentQuestionPaperDTO assessmentQuestionPaperDTO, Map<Long,QuestionDTO> questionDTOMap) {
        this.assessment = assessment;
        this.assessmentInstanceDTO = assessmentInstanceDTO;
        this.assessmentQuestionPaperDTO = assessmentQuestionPaperDTO;
        this.questionDTOMap = questionDTOMap;
    }

    public QuestionDTO getQuestionId(Long questionId){
        return questionDTOMap.get(questionId);
    }

}
