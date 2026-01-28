package com.platformcommons.platform.service.assessment.application.search;


import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceDetailVO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.List;

public interface SearchService {

    PageDTO<QuestionDTO> searchByQuestionByQuestionText(String domain, String subDomain, String questionText, String questionType, String questionSubType, String sortBy, String sortOrder, String language, Integer page, Integer size);

    PageDTO<AssessmentInstanceDetailVO> searchByAssessmentName(String language, Integer page, Integer size, String sortBy, String sortOrder, String text, List<Long> resultSet);

    PageDTO<AssessmentInstanceDetailVO> searchByAssessmentName(String formName, String domain, String subDomain, String language, Integer page, Integer size, String sortBy, String sortOrder, String assessmentType, String context);
}