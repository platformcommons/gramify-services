package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.PersonSkill;
import com.platformcommons.platform.service.profile.dto.PersonSkillDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface PersonSkillFacade {

    Long save(PersonSkillDTO personSkillDTO );

    PersonSkillDTO update(PersonSkillDTO personSkillDTO );

    PageDTO<PersonSkillDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    PersonSkillDTO getById(Long id);


}
