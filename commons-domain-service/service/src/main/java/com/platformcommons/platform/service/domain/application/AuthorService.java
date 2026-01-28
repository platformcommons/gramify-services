package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.Author;

import org.springframework.data.domain.Page;

import java.util.*;

public interface AuthorService {

    Long save(Author author );

    Author update(Author author );

    Page<Author> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    Author getById(Long id);

    Author getCurrentTenantAuthor();

    Author getByTenantId(Long tenantId);
}
