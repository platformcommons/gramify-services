package com.platformcommons.platform.service.profile.facade.impl;

import com.platformcommons.platform.service.profile.domain.PersonLanguage;
import com.platformcommons.platform.service.profile.dto.PersonLanguageDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.platformcommons.platform.service.profile.facade.PersonLanguageFacade;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.platformcommons.platform.service.profile.application.PersonLanguageService;
import com.platformcommons.platform.service.profile.facade.assembler.PersonLanguageDTOAssembler;
import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;

@Component
@Transactional
public class PersonLanguageFacadeImpl implements PersonLanguageFacade {


    @Autowired
    private PersonLanguageService service;

    private PersonLanguageDTOAssembler assembler;


    @Override
    public Long save(PersonLanguageDTO personLanguageDTO ){
        return service.save(assembler.fromDTO(personLanguageDTO));
    }


    @Override
    public PersonLanguageDTO update(PersonLanguageDTO personLanguageDTO ){
        return assembler.toDTO(service.update(assembler.fromDTO(personLanguageDTO)));
    }

    @Override
    public PageDTO<PersonLanguageDTO> getAllPage(Integer page, Integer size){
        Page<PersonLanguage> result= service.getByPage(page,size);
        return new PageDTO<>(result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new)), result.hasNext());
    }

    @Override
    public void delete(Long id,String reason){
        service.deleteById(id,reason);
    }

    @Override
    public PersonLanguageDTO getById(Long id){
        return assembler.toDTO(service.getById(id));
    }

 
}
