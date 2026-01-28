package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.service.iam.application.TenantProfileService;
import com.platformcommons.platform.service.iam.application.TenantService;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.TenantProfile;
import com.platformcommons.platform.service.iam.domain.repo.TenantProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TenantProfileServiceImpl implements TenantProfileService {

    @Autowired
    private TenantProfileRepository repository;

    @Autowired
    private TenantService tenantService;

    @Override
    public TenantProfile getOrCreateByTenantId(Long tenantId) {
        TenantProfile tenantProfile = null;
        Optional<TenantProfile> optionalTenantProfile = repository.findByTenantId(tenantId);
        if(optionalTenantProfile.isPresent()) {
            tenantProfile = optionalTenantProfile.get();
        }
        else {
            tenantProfile = TenantProfile.builder().build();
            Tenant tenant = tenantService.getTenantById(tenantId);
            tenant.setTenantProfile(tenantProfile);
            tenant = tenantService.updateTenant(tenant,Boolean.TRUE);
            tenantProfile = tenant.getTenantProfile();
        }
        return tenantProfile;
    }
}
