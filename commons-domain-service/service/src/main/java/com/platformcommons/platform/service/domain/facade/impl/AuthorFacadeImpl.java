package com.platformcommons.platform.service.domain.facade.impl;

import com.platformcommons.platform.service.domain.domain.Author;
import com.platformcommons.platform.service.domain.dto.AuthorDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.domain.facade.AuthorFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.domain.application.AuthorService;
import com.platformcommons.platform.service.domain.facade.assembler.AuthorDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class AuthorFacadeImpl implements AuthorFacade {


    @Autowired
    private AuthorService service;

    @Autowired
    private AuthorDTOAssembler assembler;


    @Override
    public Long save(AuthorDTO authorDTO ){
        return service.save(assembler.fromDTO(authorDTO));
    }


    @Override
    public AuthorDTO update(AuthorDTO authorDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(authorDTO)));
    }

    @Override
    public PageDTO<AuthorDTO> getAllPage(Integer page, Integer size){
        Page<Author> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public AuthorDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

    @Override
    public AuthorDTO getAuthorByTenant(Long tenantId) {
        return assembler.toDTO(service.getByTenantId(tenantId));
    }


}
