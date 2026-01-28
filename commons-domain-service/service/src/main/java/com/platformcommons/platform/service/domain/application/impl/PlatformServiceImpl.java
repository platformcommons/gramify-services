package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.service.domain.domain.Platform;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.domain.application.PlatformService;
import com.platformcommons.platform.service.domain.domain.repo.PlatformRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class PlatformServiceImpl implements PlatformService {


    @Autowired
    private PlatformRepository repository;




    @Override
    public Long save(Platform platform ){
        return repository.save(platform).getId();
    }



    @Override
    public Platform update(Platform platform) {
       Platform fetchedPlatform = repository.findById(platform.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Platform with  %d  not found",platform.getId())));

       fetchedPlatform.update(platform);
       return repository.save(fetchedPlatform);
    }


    @Override
    public Page<Platform> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Platform fetchedPlatform = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Platform with  %d  not found",id)));
        fetchedPlatform.deactivate(reason);
        repository.save(fetchedPlatform);
    }


    @Override
    public Platform getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Platform with  %d  not found",id)));
    }


}
