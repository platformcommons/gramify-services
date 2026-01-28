package com.platformcommons.platform.service.domain.facade;

import com.platformcommons.platform.service.domain.domain.Benefit;
import com.platformcommons.platform.service.domain.dto.BenefitDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface BenefitFacade {

    Long save(BenefitDTO benefitDTO );

    BenefitDTO update(BenefitDTO benefitDTO );

    PageDTO<BenefitDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    BenefitDTO getById(Long id);


}
