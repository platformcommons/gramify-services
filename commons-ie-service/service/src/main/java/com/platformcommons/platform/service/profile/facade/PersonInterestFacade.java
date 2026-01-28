package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonInterest;
import com.platformcommons.platform.service.profile.dto.PersonInterestDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonInterestFacade {

    Long save(PersonInterestDTO personInterestDTO );

    PersonInterestDTO update(PersonInterestDTO personInterestDTO );

    PageDTO<PersonInterestDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonInterestDTO getById(Long id);


}
