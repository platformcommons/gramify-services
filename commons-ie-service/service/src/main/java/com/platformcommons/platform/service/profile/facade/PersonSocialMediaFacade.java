package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonSocialMedia;
import com.platformcommons.platform.service.profile.dto.PersonSocialMediaDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonSocialMediaFacade {

    Long save(PersonSocialMediaDTO personSocialMediaDTO );

    PersonSocialMediaDTO update(PersonSocialMediaDTO personSocialMediaDTO );

    PageDTO<PersonSocialMediaDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonSocialMediaDTO getById(Long id);


}
