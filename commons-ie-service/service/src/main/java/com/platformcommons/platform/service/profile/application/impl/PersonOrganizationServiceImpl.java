package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonOrganization;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonOrganizationService;
import com.platformcommons.platform.service.profile.domain.repo.PersonOrganizationRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonOrganizationServiceImpl implements PersonOrganizationService {


    @Autowired
    private PersonOrganizationRepository repository;




    @Override
    public Long save(PersonOrganization personOrganization ){
        return repository.save(personOrganization).getId();
    }



    @Override
    public PersonOrganization update(PersonOrganization personOrganization) {
       PersonOrganization fetchedPersonOrganization = repository.findById(personOrganization.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonOrganization with  %d  not found",personOrganization.getId())));

       fetchedPersonOrganization.update(personOrganization);
       return repository.save(fetchedPersonOrganization);
    }


    @Override
    public Page<PersonOrganization> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonOrganization fetchedPersonOrganization = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonOrganization with  %d  not found",id)));
        fetchedPersonOrganization.deactivate(reason);
        repository.save(fetchedPersonOrganization);
    }


    @Override
    public PersonOrganization getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonOrganization with  %d  not found",id)));
    }

 
}
