package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonIdentifier;
import com.platformcommons.platform.service.profile.dto.PersonIdentifierDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonIdentifierFacade {

    Long save(PersonIdentifierDTO personIdentifierDTO );

    PersonIdentifierDTO update(PersonIdentifierDTO personIdentifierDTO );

    PageDTO<PersonIdentifierDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonIdentifierDTO getById(Long id);


}
