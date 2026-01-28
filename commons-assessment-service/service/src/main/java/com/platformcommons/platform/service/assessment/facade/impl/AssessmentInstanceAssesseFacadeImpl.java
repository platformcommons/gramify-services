package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.service.assessment.application.AssessmentInstanceAssesseService;
import com.platformcommons.platform.service.assessment.application.utility.submission.CGPostSubmissionExecutorTask;
import com.platformcommons.platform.service.assessment.domain.Assessment;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAssesse;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseFilterDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceAssesseDTOAssembler;
import com.platformcommons.platform.service.assessment.messaging.producer.AssessmentInstanceAssesseEventProducer;
import com.platformcommons.platform.service.dto.base.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.assessment.facade.AssessmentInstanceAssesseFacade;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AssessmentInstanceAssesseFacadeImpl implements AssessmentInstanceAssesseFacade {

    private final AssessmentInstanceAssesseService service;
    private final AssessmentInstanceAssesseDTOAssembler assembler;
    private final AssessmentInstanceAssesseEventProducer producer;

    @Override
    public Long createAssessmentInstanceAssesseV2(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO) {
        assessmentInstanceAssesseDTO=assembler.toDTO(service.createAssessmentInstanceAssesseV3(assembler.fromDTO(assessmentInstanceAssesseDTO)));
        produce(assessmentInstanceAssesseDTO);
        return assessmentInstanceAssesseDTO.getId();
    }

    private void produce(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO) {
        producer.assessmentInstanceAssesseCreated(assessmentInstanceAssesseDTO);
    }


    @Override
    public PageDTO<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesse(Long assessmentId, Integer page, Integer size) {
        Page<AssessmentInstanceAssesse> assesseDTOS = service.getAssessmentInstanceAssesse(assessmentId,page,size);
        return new PageDTO<>(assesseDTOS.get().map(assembler::toDTO).collect(Collectors.toSet()),false);
    }

    @Override
    public Long getAssessmentInstanceAssesseCount(Long assessmentId) {
        return service.getAssessmentInstanceAssesseCount(assessmentId);
    }

    @Override
    public void deleteAssessmentInstanceAssesse(Long id, String reason) {
        service.deleteAssessmentInstanceAssesse(id, reason);
    }

    @Override
    public Set<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesseByAssessedForEntityIds(List<String> assessedForEntityId, String assessedForEntityType, List<Long> assessmentInstanceId) {
        return service.getAssessmentInstanceAssesseByAssessedForEntityIds(assessedForEntityId,assessedForEntityType,assessmentInstanceId).stream().map(assembler::toDTO).collect(Collectors.toSet());
    }
    @Override
    public Set<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesseByAssesse(List<String> actorIds, String actorType, List<Long> assessmentInstanceId) {
        return service.getAssessmentInstanceAssesseByAssesse(actorIds,actorType,assessmentInstanceId).stream().map(assembler::toDTO).collect(Collectors.toSet());
    }

    @Override
    public AssessmentInstanceAssesseDTO updateAssessmentInstanceAssesseWithResponse(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO) {
        AssessmentInstanceAssesse updatedAssesse = service.updateAssessmentInstanceAssesseWithResponse(assembler.fromDTO(assessmentInstanceAssesseDTO));
        AssessmentInstanceAssesseDTO updatedDTO = assembler.toDTO(updatedAssesse);
        producer.assessmentInstanceAssesseUpdated(updatedDTO);
        return updatedDTO;
    }

    @Override
    public AssessmentInstanceAssesseDTO getAssessmentInstanceAssesseById(Long id) {
        return assembler.toDTO(service.getAssessmentInstanceAssesseById(id));
    }

    @Override
    public PageDTO<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesseByAccessorId(AssessmentInstanceAssesseFilterDTO filterDTO) {
        return service.getAssessmentInstanceAssesseByAccessorId(filterDTO);
    }

    @Override
    public AssessmentInstanceAssesseDTO patchUpdateAssesse(AssessmentInstanceAssesseDTO body) {
        AssessmentInstanceAssesse updatedAssesse = service.patchUpdateAssesse(assembler.fromDTO(body));
        AssessmentInstanceAssesseDTO updatedDTO = assembler.toDTO(updatedAssesse);
        producer.assessmentInstanceAssesseUpdated(updatedDTO);
        return updatedDTO;
    }

    @Override
    public AssessmentInstanceAssesseDTO createAssessmentInstanceAssesseV3(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO) {
        assessmentInstanceAssesseDTO=assembler.toDTO(service.createAssessmentInstanceAssesseV3(assembler.fromDTO(assessmentInstanceAssesseDTO)));
        produce(assessmentInstanceAssesseDTO);
        return assessmentInstanceAssesseDTO;
    }

    @Override
    public Map<String, AssessmentInstanceAssesseDTO> createAssessmentInstanceAssesseForComponents(Map<String, AssessmentInstanceAssesseDTO> body) {
        Map<String, AssessmentInstanceAssesseDTO> results = new LinkedHashMap<>();
        body.forEach((key, value)-> {
            AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO;
            if(Objects.isNull(value.getId()) || Objects.equals(value.getId(), 0L)){
                assessmentInstanceAssesseDTO=assembler.toDTO(service.createAssessmentInstanceAssesseV3(assembler.fromDTO(value)));
                producer.assessmentInstanceAssesseCreated(assessmentInstanceAssesseDTO);
            }
            else{
                assessmentInstanceAssesseDTO=assembler.toDTO(service.patchUpdateAssesse(assembler.fromDTO(value)));
                producer.assessmentInstanceAssesseUpdated(assessmentInstanceAssesseDTO);
            }
            results.put(key,assessmentInstanceAssesseDTO);
        });
        log.trace("Log Prior completing AssessmentInstanceAssesseFacadeImpl.createAssessmentInstanceAssesseForComponents transaction");
        return  results;

    }


}
