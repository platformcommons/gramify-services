package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.dto.base.PageDTO;
import com.platformcommons.platform.service.iam.dto.TenantFinanceDetailDTO;

public interface TenantFinanceDetailFacade {

    TenantFinanceDetailDTO createForLoggedInTenant(TenantFinanceDetailDTO tenantFinanceDetailDTO);

    void patchForLoggedInTenant(TenantFinanceDetailDTO partialUpdate);

    void deleteById(Long id,String inactiveReason);

    PageDTO<TenantFinanceDetailDTO> getPageByTenantId(Long tenantId, Integer page, Integer size, String sortBy, String direction);

}
