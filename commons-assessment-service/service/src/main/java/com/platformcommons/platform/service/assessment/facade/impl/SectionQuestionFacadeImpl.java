package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.service.assessment.application.SectionQuestionService;
import com.platformcommons.platform.service.assessment.domain.vo.SectionQuestionVO;
import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;
import com.platformcommons.platform.service.assessment.facade.SectionQuestionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SectionQuestionFacadeImpl implements SectionQuestionFacade {

    private final SectionQuestionService sectionQuestionService;


    @Override
    public Map<Long, QuestionContextCacheDTO> getSectionQuestions(Long assessmentInstanceId) {
        return sectionQuestionService.getSectionQuestions(assessmentInstanceId)
                .stream()
                .collect(Collectors.toMap(SectionQuestionVO::getSectionQuestionId,sectionQuestionVO -> QuestionContextCacheDTO.builder()
                .questionId(sectionQuestionVO.getQuestionId())
                .isMandatory(sectionQuestionVO.getIsMandatory())
                .isChildQuestion(false)
                .build()));
    }
}
