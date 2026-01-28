package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.service.assessment.application.AssessmentInstanceAccessorService;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstanceAccessor;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAccessorDTO;
import com.platformcommons.platform.service.assessment.facade.AssessmentInstanceAccessorFacade;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceAccessorAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssessmentInstanceAccessorFacadeImpl implements AssessmentInstanceAccessorFacade {

    private final AssessmentInstanceAccessorService service;

    private final AssessmentInstanceAccessorAssembler assembler;

    @Override
    public List<AssessmentInstanceAccessorDTO> createAssessmentInstanceAccessor(Long instanceId, Set<String> logins) {
        return service.createAssessmentInstanceAccessor(instanceId,logins).stream().map(assembler::toDto).collect(Collectors.toList());
    }

    @Override
    public PageDTO<AssessmentInstanceAccessorDTO> getAssessmentInstanceAccessors(Long instanceId, Integer page, Integer size) {
        Page<AssessmentInstanceAccessor> instanceAccessorPage=service.getAssessmentInstanceAccessors(instanceId,page,size);
        return new PageDTO<>(instanceAccessorPage.get().map(assembler::toDto).collect(Collectors.toSet()), instanceAccessorPage.hasNext(),instanceAccessorPage.getTotalElements());
    }

    @Override
    public void removeAssessmentInstanceAccessors(Set<String> logins, Long instanceId) {
        service.removeAssessmentInstanceAccessors(logins,instanceId);
    }
}
