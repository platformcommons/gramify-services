package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.Contact;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.ContactService;
import com.platformcommons.platform.service.profile.domain.repo.ContactRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class ContactServiceImpl implements ContactService {


    @Autowired
    private ContactRepository repository;




    @Override
    public Long save(Contact contact ){
        return repository.save(contact).getId();
    }



    @Override
    public Contact update(Contact contact) {
       Contact fetchedContact = repository.findById(contact.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Contact with  %d  not found",contact.getId())));

       fetchedContact.update(contact);
       return repository.save(fetchedContact);
    }


    @Override
    public Page<Contact> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Contact fetchedContact = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Contact with  %d  not found",id)));
        fetchedContact.deactivate(reason);
        repository.save(fetchedContact);
    }


    @Override
    public Contact getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Contact with  %d  not found",id)));
    }

 
}
