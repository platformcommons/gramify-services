package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonProfile;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonProfileService {

    Long save(PersonProfile personProfile );

    PersonProfile update(PersonProfile personProfile );

    Page<PersonProfile> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonProfile getById(Long id);


}
