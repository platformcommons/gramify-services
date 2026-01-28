package com.platformcommons.platform.service.assessment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Getter @Setter
public class SectionQuestionResponseDTO {

    private Long assessmentInstanceId;
    private Long sectionQuestionId;
    private Long questionId;
    private Boolean childQuestion;

    private List<OptionResponseDTO> optionResponseDTOList;
    @Builder
    public SectionQuestionResponseDTO(Long assessmentInstanceId,
                                      Long questionId,
                                      Long sectionQuestionId,
                                      List<OptionResponseDTO> optionResponseDTOList,
                                      Boolean childQuestion){
        this.assessmentInstanceId = assessmentInstanceId;
        this.sectionQuestionId = sectionQuestionId;
        this.questionId = questionId;
        this.optionResponseDTOList = optionResponseDTOList;
        this.childQuestion = childQuestion;
    }


    public void increaseResponseCount(Long id) {
        Optional<OptionResponseDTO> optional = optionResponseDTOList.stream()
                .filter(el-> Objects.equals(el.getOptionId(), id))
                .findFirst();
        if(optional.isPresent()){
            optional.get().setResponseCount(optional.get().getResponseCount()+1);
        }
        else{
            optionResponseDTOList.add(OptionResponseDTO.builder()
                    .responseCount(1L)
                    .optionId(id)
                    .build());
        }
    }
}