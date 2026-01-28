package com.platformcommons.platform.service.profile.application.impl;

import com.platformcommons.platform.service.profile.domain.OrganizationFinance;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.profile.application.OrganizationFinanceService;
import com.platformcommons.platform.service.profile.domain.repo.OrganizationFinanceRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.*;
import java.util.stream.Collectors;


@Service
public class OrganizationFinanceServiceImpl implements OrganizationFinanceService {


    @Autowired
    private OrganizationFinanceRepository repository;




    @Override
    public Long save(OrganizationFinance organizationFinance ){
        return repository.save(organizationFinance).getId();
    }



    @Override
    public OrganizationFinance update(OrganizationFinance organizationFinance) {
       OrganizationFinance fetchedOrganizationFinance = repository.findById(organizationFinance.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request OrganizationFinance with  %d  not found",organizationFinance.getId())));

       fetchedOrganizationFinance.update(organizationFinance);
       return repository.save(fetchedOrganizationFinance);
    }


    @Override
    public Page<OrganizationFinance> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        OrganizationFinance fetchedOrganizationFinance = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request OrganizationFinance with  %d  not found",id)));
        fetchedOrganizationFinance.deactivate(reason);
        repository.save(fetchedOrganizationFinance);
    }


    @Override
    public OrganizationFinance getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request OrganizationFinance with  %d  not found",id)));
    }

 
}
