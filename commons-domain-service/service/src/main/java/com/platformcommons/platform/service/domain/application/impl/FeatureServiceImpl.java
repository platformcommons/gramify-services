package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.service.domain.domain.Feature;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.domain.application.FeatureService;
import com.platformcommons.platform.service.domain.domain.repo.FeatureRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class FeatureServiceImpl implements FeatureService {


    @Autowired
    private FeatureRepository repository;




    @Override
    public Long save(Feature feature ){
        return repository.save(feature).getId();
    }



    @Override
    public Feature update(Feature feature) {
       Feature fetchedFeature = repository.findById(feature.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Feature with  %d  not found",feature.getId())));

       fetchedFeature.update(feature);
       return repository.save(fetchedFeature);
    }


    @Override
    public Page<Feature> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Feature fetchedFeature = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Feature with  %d  not found",id)));
        fetchedFeature.deactivate(reason);
        repository.save(fetchedFeature);
    }


    @Override
    public Feature getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Feature with  %d  not found",id)));
    }


}
