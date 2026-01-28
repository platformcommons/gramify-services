package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.dto.TenantPointOfContactDTO;

public interface TenantPointOfContactFacade {

    TenantPointOfContactDTO getForLoggedInTenant(Long id);

    TenantPointOfContactDTO createForLoggedInTenant(TenantPointOfContactDTO tenantPointOfContactDTO);

    void patchForLoggedInTenant(TenantPointOfContactDTO partialUpdate);

    void deleteById(Long id,String inactiveReason);

    PageDTO<TenantPointOfContactDTO> getPageByTenantId(Long tenantId, Integer page, Integer size, String sortBy, String direction);

}
