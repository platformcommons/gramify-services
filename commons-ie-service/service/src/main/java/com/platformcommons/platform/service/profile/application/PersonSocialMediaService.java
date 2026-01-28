package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.PersonSocialMedia;

import org.springframework.data.domain.Page;

import java.util.*;

public interface PersonSocialMediaService {

    Long save(PersonSocialMedia personSocialMedia );

    PersonSocialMedia update(PersonSocialMedia personSocialMedia );

    Page<PersonSocialMedia> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    PersonSocialMedia getById(Long id);


}
