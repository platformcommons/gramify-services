package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonLanguage;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonLanguageService {

    Long save(PersonLanguage personLanguage );

    PersonLanguage update(PersonLanguage personLanguage );

    Page<PersonLanguage> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonLanguage getById(Long id);


}
