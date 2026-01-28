package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.TenantFinanceDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantFinanceDetailService {

    TenantFinanceDetail createForLoggedInTenant(Long tenantId,TenantFinanceDetail detail);

    void patchForLoggedInTenant( TenantFinanceDetail tenantFinanceDetail,Long tenantId);

    void deleteById(Long id,String inactiveReason,Long tenantId);

    Page<TenantFinanceDetail> getPageByTenantId(Long tenantId, Pageable pageable);

}
