package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonSkill;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonSkillService {

    Long save(PersonSkill personSkill );

    PersonSkill update(PersonSkill personSkill );

    Page<PersonSkill> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonSkill getById(Long id);


}
