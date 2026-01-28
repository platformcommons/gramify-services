package com.platformcommons.platform.service.assessment.facade;

import com.platformcommons.platform.service.assessment.domain.vo.OptionVO;
import com.platformcommons.platform.service.assessment.dto.OptionsDTO;

import java.util.Set;

public interface OptionFacade {

    Set<OptionVO> getOptions(Set<Long> questionIds);

}
