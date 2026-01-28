package com.platformcommons.platform.service.domain.application;

import com.platformcommons.platform.service.domain.domain.DomainHierarchy;

import org.springframework.data.domain.Page;

import java.util.*;

public interface DomainHierarchyService {

    Long save(DomainHierarchy domainHierarchy );

    DomainHierarchy update(DomainHierarchy domainHierarchy );

    Page<DomainHierarchy> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    DomainHierarchy getById(Long id);

    void createHierarchy(Long domainId, Long parentDomainId);

}
