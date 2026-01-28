package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonInsurance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonInsuranceService;
import com.platformcommons.platform.service.profile.domain.repo.PersonInsuranceRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonInsuranceServiceImpl implements PersonInsuranceService {


    @Autowired
    private PersonInsuranceRepository repository;




    @Override
    public Long save(PersonInsurance personInsurance ){
        return repository.save(personInsurance).getId();
    }



    @Override
    public PersonInsurance update(PersonInsurance personInsurance) {
       PersonInsurance fetchedPersonInsurance = repository.findById(personInsurance.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonInsurance with  %d  not found",personInsurance.getId())));

       fetchedPersonInsurance.update(personInsurance);
       return repository.save(fetchedPersonInsurance);
    }


    @Override
    public Page<PersonInsurance> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonInsurance fetchedPersonInsurance = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonInsurance with  %d  not found",id)));
        fetchedPersonInsurance.deactivate(reason);
        repository.save(fetchedPersonInsurance);
    }


    @Override
    public PersonInsurance getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonInsurance with  %d  not found",id)));
    }

 
}
