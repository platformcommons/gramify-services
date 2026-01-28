package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonInsurance;
import com.platformcommons.platform.service.profile.dto.PersonInsuranceDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonInsuranceFacade {

    Long save(PersonInsuranceDTO personInsuranceDTO );

    PersonInsuranceDTO update(PersonInsuranceDTO personInsuranceDTO );

    PageDTO<PersonInsuranceDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonInsuranceDTO getById(Long id);


}
