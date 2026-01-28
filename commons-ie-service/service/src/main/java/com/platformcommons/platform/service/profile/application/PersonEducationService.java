package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonEducation;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonEducationService {

    Long save(PersonEducation personEducation );

    PersonEducation update(PersonEducation personEducation );

    Page<PersonEducation> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonEducation getById(Long id);


}
