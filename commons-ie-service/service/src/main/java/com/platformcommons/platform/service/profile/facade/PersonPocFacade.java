package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonPoc;
import com.platformcommons.platform.service.profile.dto.PersonPocDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonPocFacade {

    Long save(PersonPocDTO personPocDTO );

    PersonPocDTO update(PersonPocDTO personPocDTO );

    PageDTO<PersonPocDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonPocDTO getById(Long id);


}
