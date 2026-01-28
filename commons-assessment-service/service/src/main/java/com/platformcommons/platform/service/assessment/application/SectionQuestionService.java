package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.SectionQuestions;
import com.platformcommons.platform.service.assessment.domain.vo.SectionQuestionVO;

import java.util.List;
import java.util.Set;


public interface SectionQuestionService {
    Set<SectionQuestionVO> getSectionQuestions(Long assessmentInstanceId);

    List<SectionQuestions> getSectionQuestions(Set<Long> sectionQuestionIds);
}
