package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.service.assessment.application.OptionService;
import com.platformcommons.platform.service.assessment.domain.vo.OptionVO;
import com.platformcommons.platform.service.assessment.facade.OptionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class OptionFacadeImpl implements OptionFacade {

    private final OptionService service;

    @Override
    public Set<OptionVO> getOptions(Set<Long> questionIds) {
        return service.getOptions(questionIds);
    }


}
