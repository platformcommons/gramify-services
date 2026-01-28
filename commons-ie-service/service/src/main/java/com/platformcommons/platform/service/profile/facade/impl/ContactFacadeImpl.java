package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.Contact;
import com.platformcommons.platform.service.profile.dto.ContactDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.ContactFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.ContactService;
import com.platformcommons.platform.service.profile.facade.assembler.ContactDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class ContactFacadeImpl implements ContactFacade {


    @Autowired
    private ContactService service;

    private ContactDTOAssembler assembler;


    @Override
    public Long save(ContactDTO contactDTO ){
        return service.save(assembler.fromDTO(contactDTO));
    }


    @Override
    public ContactDTO update(ContactDTO contactDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(contactDTO)));
    }

    @Override
    public PageDTO<ContactDTO> getAllPage(Integer page, Integer size){
        Page<Contact> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public ContactDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
