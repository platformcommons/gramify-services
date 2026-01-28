package com.platformcommons.platform.service.iam.application;

import com.platformcommons.platform.service.iam.domain.TenantMetaMaster;

import java.util.List;
import java.util.Optional;

public interface TenantMetaMasterService {

    List<TenantMetaMaster> getTenantMetaMasterForAppContext(String appContext);

    Optional<TenantMetaMaster> getByAppContextAndCode(String appContext, String metaCode);

    TenantMetaMaster saveTenantMetaMaster(TenantMetaMaster tenantMetaMaster);

    Optional<TenantMetaMaster> getMetaData(String code);
}
