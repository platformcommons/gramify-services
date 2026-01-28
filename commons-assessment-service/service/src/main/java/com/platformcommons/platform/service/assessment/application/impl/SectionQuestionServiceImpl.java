package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.service.assessment.application.SectionQuestionService;
import com.platformcommons.platform.service.assessment.domain.SectionQuestions;
import com.platformcommons.platform.service.assessment.domain.repo.SectionQuestionsNonMTRepository;
import com.platformcommons.platform.service.assessment.domain.repo.SectionQuestionsRepository;
import com.platformcommons.platform.service.assessment.domain.vo.SectionQuestionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class SectionQuestionServiceImpl implements SectionQuestionService {
    private final SectionQuestionsNonMTRepository sectionQuestionsRepository;


    @Override
    public Set<SectionQuestionVO> getSectionQuestions(Long assessmentInstanceId) {
        return sectionQuestionsRepository.getSectionQuestionsByInstanceId(assessmentInstanceId);
    }

    @Override
    public List<SectionQuestions> getSectionQuestions(Set<Long> sectionQuestionIds) {
        return sectionQuestionsRepository.findAllById(sectionQuestionIds);
    }
}
