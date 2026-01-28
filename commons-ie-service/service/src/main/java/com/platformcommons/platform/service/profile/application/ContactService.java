package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.Contact;

import org.springframework.data.domain.Page;

import java.util.*;

public interface ContactService {

    Long save(Contact contact );

    Contact update(Contact contact );

    Page<Contact> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Contact getById(Long id);


}
