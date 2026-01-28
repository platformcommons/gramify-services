package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonBankDetail;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonBankDetailService;
import com.platformcommons.platform.service.profile.domain.repo.PersonBankDetailRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonBankDetailServiceImpl implements PersonBankDetailService {


    @Autowired
    private PersonBankDetailRepository repository;




    @Override
    public Long save(PersonBankDetail personBankDetail ){
        return repository.save(personBankDetail).getId();
    }



    @Override
    public PersonBankDetail update(PersonBankDetail personBankDetail) {
       PersonBankDetail fetchedPersonBankDetail = repository.findById(personBankDetail.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonBankDetail with  %d  not found",personBankDetail.getId())));

       fetchedPersonBankDetail.update(personBankDetail);
       return repository.save(fetchedPersonBankDetail);
    }


    @Override
    public Page<PersonBankDetail> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonBankDetail fetchedPersonBankDetail = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonBankDetail with  %d  not found",id)));
        fetchedPersonBankDetail.deactivate(reason);
        repository.save(fetchedPersonBankDetail);
    }


    @Override
    public PersonBankDetail getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonBankDetail with  %d  not found",id)));
    }

 
}
