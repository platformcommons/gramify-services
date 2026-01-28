package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonProfession;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonProfessionService {

    Long save(PersonProfession personProfession );

    PersonProfession update(PersonProfession personProfession );

    Page<PersonProfession> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonProfession getById(Long id);


}
