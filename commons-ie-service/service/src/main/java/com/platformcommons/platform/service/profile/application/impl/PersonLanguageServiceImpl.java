package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonLanguage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonLanguageService;
import com.platformcommons.platform.service.profile.domain.repo.PersonLanguageRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonLanguageServiceImpl implements PersonLanguageService {


    @Autowired
    private PersonLanguageRepository repository;




    @Override
    public Long save(PersonLanguage personLanguage ){
        return repository.save(personLanguage).getId();
    }



    @Override
    public PersonLanguage update(PersonLanguage personLanguage) {
       PersonLanguage fetchedPersonLanguage = repository.findById(personLanguage.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonLanguage with  %d  not found",personLanguage.getId())));

       fetchedPersonLanguage.update(personLanguage);
       return repository.save(fetchedPersonLanguage);
    }


    @Override
    public Page<PersonLanguage> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonLanguage fetchedPersonLanguage = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonLanguage with  %d  not found",id)));
        fetchedPersonLanguage.deactivate(reason);
        repository.save(fetchedPersonLanguage);
    }


    @Override
    public PersonLanguage getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonLanguage with  %d  not found",id)));
    }

 
}
