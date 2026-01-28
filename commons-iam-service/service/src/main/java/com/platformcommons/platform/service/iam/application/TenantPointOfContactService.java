package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.TenantPointOfContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TenantPointOfContactService {

    TenantPointOfContact post(Long tenantId, TenantPointOfContact detail);

    void patch( TenantPointOfContact tenantFinanceDetail,Long tenantId);

    void deleteById(Long id,String inactiveReason,Long tenantId);

    TenantPointOfContact getByIdAndTenantId(Long tenantId, Long id);

    Page<TenantPointOfContact> getPageByTenantId(Long tenantId, Pageable pageable);

}
