package com.platformcommons.platform.service.search.application.service.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.search.application.service.DomainService;
import com.platformcommons.platform.service.search.domain.Domain;
import com.platformcommons.platform.service.search.domain.repo.DomainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DomainServiceImpl  implements DomainService {


    @Autowired
    private DomainRepository domainRepository;

    @Override
    public Domain createDomain(Domain domain) {
        return domainRepository.save(domain);
    }

    @Override
    public Domain getDomainById(Long id){
        return domainRepository.findById(id).orElseThrow(()-> new NotFoundException("Domain Not Found"));
    }

    @Override
    public Domain updateDomain(Domain domain) {
        Domain fetchedDomain=domainRepository.findById(domain.getId()).orElseThrow(()->
                new NotFoundException(String.format("Domain with  %d  not found",domain.getId())));
        fetchedDomain.update(domain);
        return domainRepository.save(fetchedDomain);
    }
}
