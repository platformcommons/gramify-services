package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.Address;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.AddressService;
import com.platformcommons.platform.service.profile.domain.repo.AddressRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class AddressServiceImpl implements AddressService {


    @Autowired
    private AddressRepository repository;




    @Override
    public Long save(Address address ){
        return repository.save(address).getId();
    }



    @Override
    public Address update(Address address) {
       Address fetchedAddress = repository.findById(address.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Address with  %d  not found",address.getId())));

       fetchedAddress.update(address);
       return repository.save(fetchedAddress);
    }


    @Override
    public Page<Address> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Address fetchedAddress = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Address with  %d  not found",id)));
        fetchedAddress.deactivate(reason);
        repository.save(fetchedAddress);
    }


    @Override
    public Address getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Address with  %d  not found",id)));
    }

 
}
