package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.TenantAppAssociation;

public interface TenantAppAssociationService {


    TenantAppAssociation save(TenantAppAssociation tenantAppAssociation);


    TenantAppAssociation getByAppContextAndTenantId(String appContext,Long tenantId);
}
