package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonSkill;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonSkillService;
import com.platformcommons.platform.service.profile.domain.repo.PersonSkillRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonSkillServiceImpl implements PersonSkillService {


    @Autowired
    private PersonSkillRepository repository;




    @Override
    public Long save(PersonSkill personSkill ){
        return repository.save(personSkill).getId();
    }



    @Override
    public PersonSkill update(PersonSkill personSkill) {
       PersonSkill fetchedPersonSkill = repository.findById(personSkill.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonSkill with  %d  not found",personSkill.getId())));

       fetchedPersonSkill.update(personSkill);
       return repository.save(fetchedPersonSkill);
    }


    @Override
    public Page<PersonSkill> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonSkill fetchedPersonSkill = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonSkill with  %d  not found",id)));
        fetchedPersonSkill.deactivate(reason);
        repository.save(fetchedPersonSkill);
    }


    @Override
    public PersonSkill getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonSkill with  %d  not found",id)));
    }

 
}
