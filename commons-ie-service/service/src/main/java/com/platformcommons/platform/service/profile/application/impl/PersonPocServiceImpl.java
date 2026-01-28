package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonPoc;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonPocService;
import com.platformcommons.platform.service.profile.domain.repo.PersonPocRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonPocServiceImpl implements PersonPocService {


    @Autowired
    private PersonPocRepository repository;




    @Override
    public Long save(PersonPoc personPoc ){
        return repository.save(personPoc).getId();
    }



    @Override
    public PersonPoc update(PersonPoc personPoc) {
       PersonPoc fetchedPersonPoc = repository.findById(personPoc.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonPoc with  %d  not found",personPoc.getId())));

       fetchedPersonPoc.update(personPoc);
       return repository.save(fetchedPersonPoc);
    }


    @Override
    public Page<PersonPoc> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonPoc fetchedPersonPoc = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonPoc with  %d  not found",id)));
        fetchedPersonPoc.deactivate(reason);
        repository.save(fetchedPersonPoc);
    }


    @Override
    public PersonPoc getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonPoc with  %d  not found",id)));
    }

 
}
