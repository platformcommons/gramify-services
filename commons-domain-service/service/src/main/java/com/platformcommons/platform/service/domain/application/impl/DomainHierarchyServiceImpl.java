package com.platformcommons.platform.service.domain.application.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.domain.domain.DomainHierarchy;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;



import com.platformcommons.platform.service.domain.application.DomainHierarchyService;
import com.platformcommons.platform.service.domain.domain.repo.DomainHierarchyRepository;
import com.platformcommons.platform.exception.generic.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureQuery;


@Service
public class DomainHierarchyServiceImpl implements DomainHierarchyService {


    private static final String ADD_DOMAIN_HIERARCHY = "addDomainHierarchy";
    @Autowired
    private DomainHierarchyRepository repository;


    @Autowired
    private EntityManager entityManager;


    @Override
    public Long save(DomainHierarchy domainHierarchy ){
        return repository.save(domainHierarchy).getId();
    }



    @Override
    public DomainHierarchy update(DomainHierarchy domainHierarchy) {
       DomainHierarchy fetchedDomainHierarchy = repository.findById(domainHierarchy.getId())
    		.orElseThrow(()-> new NotFoundException(String.format("Request DomainHierarchy with  %d  not found",domainHierarchy.getId())));

       fetchedDomainHierarchy.update(domainHierarchy);
       return repository.save(fetchedDomainHierarchy);
    }


    @Override
    public Page<DomainHierarchy> getByPage(Integer page, Integer size) {
        return repository.findAll(PageRequest.of(page,size));
    }

    @Override
    public void deleteById(Long id, String reason) {
        DomainHierarchy fetchedDomainHierarchy = repository.findById(id)
           		.orElseThrow(()-> new NotFoundException(String.format("Request DomainHierarchy with  %d  not found",id)));
        fetchedDomainHierarchy.deactivate(reason);
        repository.save(fetchedDomainHierarchy);
    }


    @Override
    public DomainHierarchy getById(Long id) {
       return repository.findById(id)
    		.orElseThrow(()-> new NotFoundException(String.format("Request DomainHierarchy with  %d  not found",id)));
    }

    @Override
    public void createHierarchy(Long domainId, Long parentDomainId) {
        StoredProcedureQuery store =
                entityManager.createStoredProcedureQuery(ADD_DOMAIN_HIERARCHY);
        store.registerStoredProcedureParameter(1, Long.class, ParameterMode.IN);
        store.registerStoredProcedureParameter(2, Long.class, ParameterMode.IN);
        store.registerStoredProcedureParameter(3,Long.class,ParameterMode.IN);
        store.registerStoredProcedureParameter(4,Long.class,ParameterMode.IN);


        store.setParameter(1, parentDomainId);
        store.setParameter(2, domainId);
        store.setParameter(3, PlatformSecurityUtil.getCurrentTenantId());
        store.setParameter(4,PlatformSecurityUtil.getCurrentUserId());
        store.execute();
    }



}
