package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonLanguage;
import com.platformcommons.platform.service.profile.dto.PersonLanguageDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonLanguageFacade {

    Long save(PersonLanguageDTO personLanguageDTO );

    PersonLanguageDTO update(PersonLanguageDTO personLanguageDTO );

    PageDTO<PersonLanguageDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonLanguageDTO getById(Long id);


}
