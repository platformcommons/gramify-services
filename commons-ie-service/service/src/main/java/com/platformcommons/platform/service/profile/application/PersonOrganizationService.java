package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonOrganization;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonOrganizationService {

    Long save(PersonOrganization personOrganization );

    PersonOrganization update(PersonOrganization personOrganization );

    Page<PersonOrganization> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonOrganization getById(Long id);


}
