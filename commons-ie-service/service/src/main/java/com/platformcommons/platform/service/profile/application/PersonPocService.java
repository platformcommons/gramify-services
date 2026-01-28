package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonPoc;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonPocService {

    Long save(PersonPoc personPoc );

    PersonPoc update(PersonPoc personPoc );

    Page<PersonPoc> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonPoc getById(Long id);


}
