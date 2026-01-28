package com.platformcommons.platform.service.profile.application;

import com.platformcommons.platform.service.profile.domain.OrganizationFinance;

import org.springframework.data.domain.Page;

import java.util.*;

public interface OrganizationFinanceService {

    Long save(OrganizationFinance organizationFinance );

    OrganizationFinance update(OrganizationFinance organizationFinance );

    Page<OrganizationFinance> getByPage(Integer page, Integer size);

    void deleteById(Long id,String reason);

    OrganizationFinance getById(Long id);


}
