package com.platformcommons.platform.service.assessment.application;

import com.platformcommons.platform.service.assessment.domain.vo.OptionVO;
import com.platformcommons.platform.service.assessment.dto.OptionsDTO;

import java.util.Set;

public interface OptionService {
    Set<OptionVO> getOptions(Set<Long> questionIds);
}
