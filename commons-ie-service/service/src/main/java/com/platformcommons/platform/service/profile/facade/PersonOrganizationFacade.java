package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonOrganization;
import com.platformcommons.platform.service.profile.dto.PersonOrganizationDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonOrganizationFacade {

    Long save(PersonOrganizationDTO personOrganizationDTO );

    PersonOrganizationDTO update(PersonOrganizationDTO personOrganizationDTO );

    PageDTO<PersonOrganizationDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonOrganizationDTO getById(Long id);


}
