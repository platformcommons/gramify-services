package com.platformcommons.platform.service.assessment.reporting.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.CachedSectionQuestionResponse;
import com.platformcommons.platform.service.assessment.dto.SectionQuestionResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SectionResponseDTOAssembler {

    private final OptionResponseAssembler optionResponseAssembler;
    public SectionQuestionResponseDTO toDTO(List<Map<String,Object>> sectionResponseList){
        if(sectionResponseList==null || sectionResponseList.isEmpty()){
            return null;
        }
        long assessmentInstanceId=sectionResponseList.get(0).get("assessmentInstanceID")!=null?((Number) sectionResponseList.get(0).get("assessmentInstanceID")).longValue():0;
        long sectionQuestionId=sectionResponseList.get(0).get("sectionQuestionID")!=null?((Number) sectionResponseList.get(0).get("sectionQuestionID")).longValue():0;
        long questionId = sectionResponseList.get(0).get("questionID")!=null?((Number) sectionResponseList.get(0).get("questionID")).longValue():0;
        if(sectionResponseList.stream().anyMatch(
                el ->
                        (el.get("assessmentInstanceID")!=null && ((Number)el.get("assessmentInstanceID")).longValue() != assessmentInstanceId) ||
                                (el.get("sectionQuestionID")!=null && ((Number)el.get("sectionQuestionID")).longValue() != sectionQuestionId)

        )) throw new DataIntegrityViolationException("Group should contain consistent data");

        return SectionQuestionResponseDTO.builder()
                .assessmentInstanceId(assessmentInstanceId)
                .sectionQuestionId(sectionQuestionId)
                .questionId(questionId)
                .optionResponseDTOList(sectionResponseList.stream().map(optionResponseAssembler::toDTO).collect(Collectors.toList()))
                .build();

    }

    public CachedSectionQuestionResponse toCacheDto(List<Map<String, Object>> sectionResponseList) {
        if(sectionResponseList==null || sectionResponseList.isEmpty()){
            return null;
        }
        long assessmentInstanceId=sectionResponseList.get(0).get("assessmentInstanceID")!=null?((Number) sectionResponseList.get(0).get("assessmentInstanceID")).longValue():0;
        long sectionQuestionID=sectionResponseList.get(0).get("sectionQuestionID")!=null?((Number) sectionResponseList.get(0).get("sectionQuestionID")).longValue():0;
        long questionId = sectionResponseList.get(0).get("questionID")!=null?((Number) sectionResponseList.get(0).get("questionID")).longValue():0;
        if(sectionResponseList.stream().anyMatch(
                el ->
                        (el.get("assessmentInstanceID")!=null && ((Number)el.get("assessmentInstanceID")).longValue() != assessmentInstanceId) ||
                                (el.get("sectionQuestionID")!=null && ((Number)el.get("sectionQuestionID")).longValue() != sectionQuestionID)

        )) throw new DataIntegrityViolationException("Group should contain consistent data");
        Map<Long,Long> optionResponse = new HashMap<>();
        sectionResponseList.forEach(el -> optionResponse.put( ((Number)el.get("optionId")).longValue(),((Number)el.get("responseCount")).longValue()));
        return CachedSectionQuestionResponse.builder()
                .assessmentInstanceId(assessmentInstanceId)
                .sectionQuestionId(sectionQuestionID)
                .questionId(questionId)
                .optionresponse(optionResponse)
                .build();

    }
}