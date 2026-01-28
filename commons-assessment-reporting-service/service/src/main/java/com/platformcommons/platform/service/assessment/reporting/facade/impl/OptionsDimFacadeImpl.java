package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.CachedSectionQuestionResponse;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.OptionsDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.OptionsDimFacade;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.OptionsDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.SectionResponseDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OptionsDimFacadeImpl implements OptionsDimFacade {

    private final OptionsDimAssembler assembler;
    private final OptionsDimService service;
    @Autowired
    private SectionResponseDTOAssembler sectionResponseDTOAssembler;

    @Override
    public void createOptionDim(QuestionDTO questionDTO) {
        service.createOptionDim(assembler.toOptionDims(questionDTO));
        service.createParentLink(questionDTO);
    }

    @Override
    public void updateOptionDim(QuestionDTO questionDTO) {
        service.updateOptionDim(questionDTO);
        service.createParentLink(questionDTO);
    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        service.syncAssessmentData(syncContext);
    }

    @Override
    public Map<Long, CachedSectionQuestionResponse> getOptionResponseByAssessmentId(Long assessmentInstanceID) {
        return service.getOptionResponseByAssessmentInstanceId(assessmentInstanceID)
                .stream()
                .collect(Collectors.groupingBy(map->((Number)map.get("questionId")).longValue()))
                .values()
                .stream()
                .map(sectionResponseDTOAssembler::toCacheDto)
                .collect(Collectors.toMap(CachedSectionQuestionResponse::getQuestionId, dto->dto));
    }

    @Override
    public void unlink(Long id) {
        service.unlink(id);
    }

}
