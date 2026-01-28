package com.platformcommons.platform.service.iam.facade;

import com.platformcommons.platform.service.iam.dto.TenantMetaMasterDTO;

import java.util.Set;

public interface TenantMetaMasterFacade {
    void addMetaMasters(Set<TenantMetaMasterDTO> tenantMetaMasterDTOS);

    Set<String> getMetaMasterValue(String appContext , String key);

}
