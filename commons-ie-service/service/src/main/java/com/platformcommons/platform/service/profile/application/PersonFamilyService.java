package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonFamily;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonFamilyService {

    Long save(PersonFamily personFamily );

    PersonFamily update(PersonFamily personFamily );

    Page<PersonFamily> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonFamily getById(Long id);


}
