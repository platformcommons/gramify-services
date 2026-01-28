package com.platformcommons.platform.service.assessment.facade;

import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;

import java.util.*;

public interface AssessmentQuestionPaperFacade {

    Long createAssessmentQuestionPaper(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    Set<AssessmentQuestionPaperDTO> getAllAssessmentQuestionPapersOfInstance(Long instanceId);

    AssessmentQuestionPaperDTO updateAssessmentQuestionPaperV3(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO);

    Set<AssessmentQuestionPaperDTO> getAllAssessmentQuestionPapersByAssessmentId(Long id);

    Long getAssessmentInstanceIdByQuestionId(Long id);

    Map<Long, QuestionContextCacheDTO> getSectionQuestions(Long assessmentInstanceId);
}
