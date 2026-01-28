package com.platformcommons.platform.service.profile.facade;

import com.platformcommons.platform.service.profile.domain.OrganizationFinance;
import com.platformcommons.platform.service.profile.dto.OrganizationFinanceDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;

import java.util.*;

public interface OrganizationFinanceFacade {

    Long save(OrganizationFinanceDTO organizationFinanceDTO );

    OrganizationFinanceDTO update(OrganizationFinanceDTO organizationFinanceDTO );

    PageDTO<OrganizationFinanceDTO> getAllPage(Integer page, Integer size);

    void delete(Long id,String reason);

    OrganizationFinanceDTO getById(Long id);


}
