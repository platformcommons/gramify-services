package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.PersonSocialMedia;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.PersonSocialMediaService;
import com.platformcommons.platform.service.profile.domain.repo.PersonSocialMediaRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PersonSocialMediaServiceImpl implements PersonSocialMediaService {


    @Autowired
    private PersonSocialMediaRepository repository;




    @Override
    public Long save(PersonSocialMedia personSocialMedia ){
        return repository.save(personSocialMedia).getId();
    }



    @Override
    public PersonSocialMedia update(PersonSocialMedia personSocialMedia) {
       PersonSocialMedia fetchedPersonSocialMedia = repository.findById(personSocialMedia.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonSocialMedia with  %d  not found",personSocialMedia.getId())));

       fetchedPersonSocialMedia.update(personSocialMedia);
       return repository.save(fetchedPersonSocialMedia);
    }


    @Override
    public Page<PersonSocialMedia> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        PersonSocialMedia fetchedPersonSocialMedia = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request PersonSocialMedia with  %d  not found",id)));
        fetchedPersonSocialMedia.deactivate(reason);
        repository.save(fetchedPersonSocialMedia);
    }


    @Override
    public PersonSocialMedia getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request PersonSocialMedia with  %d  not found",id)));
    }

 
}
