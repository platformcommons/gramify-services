package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonProfession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonProfessionService;
import com.platformcommons.platform.service.profile.domain.repo.PersonProfessionRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonProfessionServiceImpl implements PersonProfessionService {


    @Autowired
    private PersonProfessionRepository repository;




    @Override
    public Long save(PersonProfession personProfession ){
        return repository.save(personProfession).getId();
    }



    @Override
    public PersonProfession update(PersonProfession personProfession) {
       PersonProfession fetchedPersonProfession = repository.findById(personProfession.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonProfession with  %d  not found",personProfession.getId())));

       fetchedPersonProfession.update(personProfession);
       return repository.save(fetchedPersonProfession);
    }


    @Override
    public Page<PersonProfession> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonProfession fetchedPersonProfession = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonProfession with  %d  not found",id)));
        fetchedPersonProfession.deactivate(reason);
        repository.save(fetchedPersonProfession);
    }


    @Override
    public PersonProfession getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonProfession with  %d  not found",id)));
    }

 
}
