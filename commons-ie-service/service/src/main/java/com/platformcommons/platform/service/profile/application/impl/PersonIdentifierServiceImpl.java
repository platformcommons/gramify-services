package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonIdentifier;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonIdentifierService;
import com.platformcommons.platform.service.profile.domain.repo.PersonIdentifierRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonIdentifierServiceImpl implements PersonIdentifierService {


    @Autowired
    private PersonIdentifierRepository repository;




    @Override
    public Long save(PersonIdentifier personIdentifier ){
        return repository.save(personIdentifier).getId();
    }



    @Override
    public PersonIdentifier update(PersonIdentifier personIdentifier) {
       PersonIdentifier fetchedPersonIdentifier = repository.findById(personIdentifier.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonIdentifier with  %d  not found",personIdentifier.getId())));

       fetchedPersonIdentifier.update(personIdentifier);
       return repository.save(fetchedPersonIdentifier);
    }


    @Override
    public Page<PersonIdentifier> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonIdentifier fetchedPersonIdentifier = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonIdentifier with  %d  not found",id)));
        fetchedPersonIdentifier.deactivate(reason);
        repository.save(fetchedPersonIdentifier);
    }


    @Override
    public PersonIdentifier getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonIdentifier with  %d  not found",id)));
    }

 
}
