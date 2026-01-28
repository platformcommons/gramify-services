package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonInterest;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonInterestService {

    Long save(PersonInterest personInterest );

    PersonInterest update(PersonInterest personInterest );

    Page<PersonInterest> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonInterest getById(Long id);


}
