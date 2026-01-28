package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;


import java.util.*;

public interface AssessmentQuestionPaperService {

    AssessmentQuestionPaper createAssessmentQuestionPaper(AssessmentQuestionPaper assessmentQuestionPaper);
    Set<AssessmentQuestionPaper> getAllAssessmentQuestionPapersOfInstance(Long instanceId);
    AssessmentQuestionPaper updateAssessmentQuestionPaperV3(AssessmentQuestionPaper assessmentQuestionPaper);
    boolean checkQuestionPaperForAssessment(Long id);
    Set<AssessmentQuestionPaper> getAllAssessmentQuestionPapersByAssessmentId(Long id);

    Long getAssessmentInstanceIdByQuestionId(Long id);
}
