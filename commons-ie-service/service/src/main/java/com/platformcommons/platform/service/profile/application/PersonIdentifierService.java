package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonIdentifier;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonIdentifierService {

    Long save(PersonIdentifier personIdentifier );

    PersonIdentifier update(PersonIdentifier personIdentifier );

    Page<PersonIdentifier> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonIdentifier getById(Long id);


}
