package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonProfile;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonProfileService;
import com.platformcommons.platform.service.profile.domain.repo.PersonProfileRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonProfileServiceImpl implements PersonProfileService {


    @Autowired
    private PersonProfileRepository repository;




    @Override
    public Long save(PersonProfile personProfile ){
        return repository.save(personProfile).getId();
    }



    @Override
    public PersonProfile update(PersonProfile personProfile) {
       PersonProfile fetchedPersonProfile = repository.findById(personProfile.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonProfile with  %d  not found",personProfile.getId())));

       fetchedPersonProfile.update(personProfile);
       return repository.save(fetchedPersonProfile);
    }


    @Override
    public Page<PersonProfile> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonProfile fetchedPersonProfile = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonProfile with  %d  not found",id)));
        fetchedPersonProfile.deactivate(reason);
        repository.save(fetchedPersonProfile);
    }


    @Override
    public PersonProfile getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonProfile with  %d  not found",id)));
    }

 
}
