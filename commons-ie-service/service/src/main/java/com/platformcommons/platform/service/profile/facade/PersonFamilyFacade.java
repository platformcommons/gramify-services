package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonFamily;
import com.platformcommons.platform.service.profile.dto.PersonFamilyDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonFamilyFacade {

    Long save(PersonFamilyDTO personFamilyDTO );

    PersonFamilyDTO update(PersonFamilyDTO personFamilyDTO );

    PageDTO<PersonFamilyDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonFamilyDTO getById(Long id);


}
