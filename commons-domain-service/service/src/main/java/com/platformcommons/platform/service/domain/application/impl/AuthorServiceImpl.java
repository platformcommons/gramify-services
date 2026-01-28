package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.domain.domain.Author;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.domain.application.AuthorService;
import com.platformcommons.platform.service.domain.domain.repo.AuthorRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class AuthorServiceImpl implements AuthorService {



    @Autowired
    private AuthorRepository repository;



    @Override
    public Long save(Author author ){
        if(repository.getCurrentTenantAuthor(PlatformSecurityUtil.getCurrentTenantId()).isPresent()){
            throw  new DuplicateResourceException("Author already exists for the current tenant");
        }
        author.setTenant(PlatformSecurityUtil.getCurrentTenantId());
        return repository.save(author).getId();
    }


    @Override
    public Author update(Author author) {
       Author fetchedAuthor = repository.findById(author.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Author with  %d  not found",author.getId())));
       fetchedAuthor.update(author);
       return repository.save(fetchedAuthor);
    }


    @Override
    public Page<Author> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Author fetchedAuthor = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Author with  %d  not found",id)));
        fetchedAuthor.deactivate(reason);
        repository.save(fetchedAuthor);
    }


    @Override
    public Author getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Author with  %d  not found",id)));
    }

    @Override
    public Author getCurrentTenantAuthor() {
        return repository.getCurrentTenantAuthor(PlatformSecurityUtil.getCurrentTenantId())
                .orElseThrow(()-> new NotFoundException("Author Information not found"));
    }

    @Override
    public Author getByTenantId(Long tenantId) {
        return repository.getCurrentTenantAuthor(tenantId)
                .orElseThrow(()-> new NotFoundException("Author Information not found"));
    }


}
