package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonSocialMedia;
import com.platformcommons.platform.service.profile.dto.PersonSocialMediaDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonSocialMediaFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonSocialMediaService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonSocialMediaDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonSocialMediaFacadeImpl implements PersonSocialMediaFacade {


    @Autowired
    private PersonSocialMediaService service;

    private PersonSocialMediaDTOAssembler assembler;


    @Override
    public Long save(PersonSocialMediaDTO personSocialMediaDTO ){
        return service.save(assembler.fromDTO(personSocialMediaDTO));
    }


    @Override
    public PersonSocialMediaDTO update(PersonSocialMediaDTO personSocialMediaDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personSocialMediaDTO)));
    }

    @Override
    public PageDTO<PersonSocialMediaDTO> getAllPage(Integer page, Integer size){
        Page<PersonSocialMedia> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonSocialMediaDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
