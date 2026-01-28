package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.service.domain.domain.Benefit;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.domain.application.BenefitService;
import com.platformcommons.platform.service.domain.domain.repo.BenefitRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class BenefitServiceImpl implements BenefitService {


    @Autowired
    private BenefitRepository repository;




    @Override
    public Long save(Benefit benefit ){
        return repository.save(benefit).getId();
    }



    @Override
    public Benefit update(Benefit benefit) {
       Benefit fetchedBenefit = repository.findById(benefit.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request Benefit with  %d  not found",benefit.getId())));

       fetchedBenefit.update(benefit);
       return repository.save(fetchedBenefit);
    }


    @Override
    public Page<Benefit> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        Benefit fetchedBenefit = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request Benefit with  %d  not found",id)));
        fetchedBenefit.deactivate(reason);
        repository.save(fetchedBenefit);
    }


    @Override
    public Benefit getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request Benefit with  %d  not found",id)));
    }


}
