package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonProfile;
import com.platformcommons.platform.service.profile.dto.PersonProfileDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonProfileFacade {

    Long save(PersonProfileDTO personProfileDTO );

    PersonProfileDTO update(PersonProfileDTO personProfileDTO );

    PageDTO<PersonProfileDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonProfileDTO getById(Long id);


}
