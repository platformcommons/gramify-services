package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonEducation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonEducationService;
import com.platformcommons.platform.service.profile.domain.repo.PersonEducationRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonEducationServiceImpl implements PersonEducationService {


    @Autowired
    private PersonEducationRepository repository;




    @Override
    public Long save(PersonEducation personEducation ){
        return repository.save(personEducation).getId();
    }



    @Override
    public PersonEducation update(PersonEducation personEducation) {
       PersonEducation fetchedPersonEducation = repository.findById(personEducation.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonEducation with  %d  not found",personEducation.getId())));

       fetchedPersonEducation.update(personEducation);
       return repository.save(fetchedPersonEducation);
    }


    @Override
    public Page<PersonEducation> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonEducation fetchedPersonEducation = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonEducation with  %d  not found",id)));
        fetchedPersonEducation.deactivate(reason);
        repository.save(fetchedPersonEducation);
    }


    @Override
    public PersonEducation getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonEducation with  %d  not found",id)));
    }

 
}
