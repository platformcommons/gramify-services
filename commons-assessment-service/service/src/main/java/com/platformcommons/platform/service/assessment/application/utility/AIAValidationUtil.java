package com.platformcommons.platform.service.assessment.application.utility;

import com.google.gson.Gson;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.assessment.domain.AiaDefaultResponse;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAssesse;
import com.platformcommons.platform.service.assessment.domain.SectionQuestions;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceAssesseDTOAssembler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AIAValidationUtil {

    private final AssessmentInstanceAssesseDTOAssembler assembler;
    public void validate(AssessmentInstanceAssesse aia) {

        try{
            if (aia.getAiadefaultresponseList() != null && !aia.getAiadefaultresponseList().isEmpty()) {
                aia.getAiadefaultresponseList().forEach(this::validate);

                Map<Long,Long> map = aia.getAiadefaultresponseList()
                        .stream()
                        .collect(Collectors.groupingBy(this::getQuestionId,Collectors.counting()));
                for (Map.Entry<Long, Long> entry : map.entrySet()) {
                    if(entry.getValue()>1L){
                        throw new InvalidInputException("Question Can be Only Answered Once");
                    }
                }
            }
        }
        catch (InvalidInputException e){
            log.error("Invalid Input Exception for aia {},  {}",new Gson().toJson(assembler.toDTO(aia)), e.getMessage());
            throw e;
        }

    }

    private void validate(AiaDefaultResponse aiaDR) {
        if(aiaDR.getSectionQuestion()!=null && !childQuestionFieldsAreNull(aiaDR)){
            throw new InvalidInputException("Response be only made to either parent or child question");
        }
        if(isNullSectionQuestion(aiaDR) && isNullChildQuestionData(aiaDR)){
            throw new InvalidInputException("Response must be made to either parent or child question");
        }
    }


    private boolean childQuestionFieldsAreNull(AiaDefaultResponse aiaDR) {
        return aiaDR.getChildQuestionId() == null && aiaDR.getChildDefaultOptionId() == null && aiaDR.getChildQuestionParentQuestionId() == null;
    }

    private boolean isNullSectionQuestion(AiaDefaultResponse aiaDR) {
        SectionQuestions sectionQuestion = aiaDR.getSectionQuestion();
        return sectionQuestion==null || sectionQuestion.getQuestionId()==null || Objects.equals(sectionQuestion.getQuestionId(),0L);
    }

    private boolean isNullChildQuestionData(AiaDefaultResponse aiaDR) {
        return aiaDR.getChildQuestionId() == null || aiaDR.getChildDefaultOptionId() == null || aiaDR.getChildQuestionParentQuestionId() == null;
    }



    public Long getQuestionId(AiaDefaultResponse aiaDR) {
        return Objects.isNull(aiaDR.getChildQuestionId())?aiaDR.getSectionQuestion().getQuestionId():aiaDR.getChildQuestionId();
    }

}
