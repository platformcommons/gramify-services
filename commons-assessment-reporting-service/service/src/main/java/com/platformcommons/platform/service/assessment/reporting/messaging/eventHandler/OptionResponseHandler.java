package com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler;

import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.reporting.facade.cache.SectionQuestionResponseCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

@Service
@Slf4j
@RequiredArgsConstructor
@EnableAsync
public class OptionResponseHandler {
    private final Map<Long,List<Consumer<List<SectionQuestionResponseDTO>>>> listeners = new ConcurrentHashMap<>();
    private final SectionQuestionResponseCache cache;

    public void subscribe(Consumer<List<SectionQuestionResponseDTO>> listener, Long assessmentInstanceID) {
        if(!listeners.containsKey(assessmentInstanceID))
            listeners.put(assessmentInstanceID, new CopyOnWriteArrayList<>());
        listeners.get(assessmentInstanceID).add(listener);

        List<SectionQuestionResponseDTO> list=cache.getResponses(assessmentInstanceID);
        if(list!=null && !list.isEmpty())
            listener.accept(list);
        else {
            listener.accept(new ArrayList<>());
        }
    }

    public void publish(AssessmentInstanceAssesseDTO aia) {
        Long assessmentInstanceID=aia.getAssessmentInstance().getId();
        if(!listeners.containsKey(assessmentInstanceID))
            return;
        List<SectionQuestionResponseDTO> responses = new ArrayList<>();
        for(AiaDefaultResponseDTO aiadr:aia.getAiadefaultResponseList()){
            Long sectionQuestionId =aiadr.getSectionQuestion()==null?null:aiadr.getSectionQuestion().getId();
            Long questionid = aiadr.getSectionQuestion()==null?aiadr.getChildQuestionId():aiadr.getSectionQuestion().getQuestionId();
            if(sectionQuestionId==null || (aiadr.getOptionId()==null && (aiadr.getDrobjectiveresponseList()==null || aiadr.getDrobjectiveresponseList().isEmpty())) ) continue;

            SectionQuestionResponseDTO dto = SectionQuestionResponseDTO.builder()
                    .assessmentInstanceId(aia.getAssessmentInstance().getId())
                    .sectionQuestionId(sectionQuestionId)
                    .questionId(questionid)
                    .optionResponseDTOList(new ArrayList<>())
                    .childQuestion(aiadr.getChildQuestionId()!=null)
                    .build();

            if(aiadr.getOptionId()!=null)
                dto.getOptionResponseDTOList().add(OptionResponseDTO.builder().optionId(aiadr.getOptionId().getId()).responseCount(1L).build());
            if(aiadr.getDrobjectiveresponseList()!=null && !aiadr.getDrobjectiveresponseList().isEmpty()){
                for( DrObjectiveResponseDTO objectiveResponse:aiadr.getDrobjectiveresponseList()){
                    dto.getOptionResponseDTOList().add(OptionResponseDTO.builder()
                                                                        .optionId(objectiveResponse.getDefaultOption().getOptions().getId())
                                                                        .responseCount(1L)
                                                                        .build());
                }
            }
            responses.add(dto);
        }
        cache.updateResponses(responses,assessmentInstanceID);
        listeners.get(assessmentInstanceID).forEach(listener -> {
            listener.accept( cache.getResponses(assessmentInstanceID) );
        });
    }

    public void evictCache(Long id) {
        cache.evictCache(id);
    }
}