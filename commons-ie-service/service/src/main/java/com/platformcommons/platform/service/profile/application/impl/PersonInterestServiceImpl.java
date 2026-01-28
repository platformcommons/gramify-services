package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonInterest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonInterestService;
import com.platformcommons.platform.service.profile.domain.repo.PersonInterestRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonInterestServiceImpl implements PersonInterestService {


    @Autowired
    private PersonInterestRepository repository;




    @Override
    public Long save(PersonInterest personInterest ){
        return repository.save(personInterest).getId();
    }



    @Override
    public PersonInterest update(PersonInterest personInterest) {
       PersonInterest fetchedPersonInterest = repository.findById(personInterest.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonInterest with  %d  not found",personInterest.getId())));

       fetchedPersonInterest.update(personInterest);
       return repository.save(fetchedPersonInterest);
    }


    @Override
    public Page<PersonInterest> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonInterest fetchedPersonInterest = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonInterest with  %d  not found",id)));
        fetchedPersonInterest.deactivate(reason);
        repository.save(fetchedPersonInterest);
    }


    @Override
    public PersonInterest getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonInterest with  %d  not found",id)));
    }

 
}
