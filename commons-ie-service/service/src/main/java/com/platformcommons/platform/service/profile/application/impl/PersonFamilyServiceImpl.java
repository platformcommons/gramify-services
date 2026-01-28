package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonFamily;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonFamilyService;
import com.platformcommons.platform.service.profile.domain.repo.PersonFamilyRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonFamilyServiceImpl implements PersonFamilyService {


    @Autowired
    private PersonFamilyRepository repository;




    @Override
    public Long save(PersonFamily personFamily ){
        return repository.save(personFamily).getId();
    }



    @Override
    public PersonFamily update(PersonFamily personFamily) {
       PersonFamily fetchedPersonFamily = repository.findById(personFamily.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonFamily with  %d  not found",personFamily.getId())));

       fetchedPersonFamily.update(personFamily);
       return repository.save(fetchedPersonFamily);
    }


    @Override
    public Page<PersonFamily> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonFamily fetchedPersonFamily = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonFamily with  %d  not found",id)));
        fetchedPersonFamily.deactivate(reason);
        repository.save(fetchedPersonFamily);
    }


    @Override
    public PersonFamily getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonFamily with  %d  not found",id)));
    }

 
}
