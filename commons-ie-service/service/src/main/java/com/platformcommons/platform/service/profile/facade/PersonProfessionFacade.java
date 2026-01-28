package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonProfession;
import com.platformcommons.platform.service.profile.dto.PersonProfessionDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonProfessionFacade {

    Long save(PersonProfessionDTO personProfessionDTO );

    PersonProfessionDTO update(PersonProfessionDTO personProfessionDTO );

    PageDTO<PersonProfessionDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonProfessionDTO getById(Long id);


}
