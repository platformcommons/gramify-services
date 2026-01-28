package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.repo.OptionsNonMTRepository;
import com.platformcommons.platform.service.assessment.domain.vo.OptionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.Set;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionsNonMTRepository optionsRepository;

    @Override
    public Set<OptionVO> getOptions(Set<Long> questionIds) {
        return optionsRepository.getOptionsByQuestionIds(questionIds);
    }
}
