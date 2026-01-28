package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonEducation;
import com.platformcommons.platform.service.profile.dto.PersonEducationDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonEducationFacade {

    Long save(PersonEducationDTO personEducationDTO );

    PersonEducationDTO update(PersonEducationDTO personEducationDTO );

    PageDTO<PersonEducationDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonEducationDTO getById(Long id);


}
