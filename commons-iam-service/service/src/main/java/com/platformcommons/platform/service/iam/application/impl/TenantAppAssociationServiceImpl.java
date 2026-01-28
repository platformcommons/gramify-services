package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.iam.application.TenantAppAssociationService;
import com.platformcommons.platform.service.iam.domain.TenantAppAssociation;
import com.platformcommons.platform.service.iam.domain.repo.TenantAppAssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantAppAssociationServiceImpl implements TenantAppAssociationService {

    @Autowired
    private TenantAppAssociationRepository repository;
    @Override
    public TenantAppAssociation save(TenantAppAssociation tenantAppAssociation) {
        return repository.save(tenantAppAssociation);
    }

    @Override
    public TenantAppAssociation getByAppContextAndTenantId(String appContext,Long tenantId) {
        return repository.findByTenant_idAndAppContext(tenantId,appContext)
                .orElseThrow(()-> new NotFoundException("Tenant id not registered for this App"));
    }

}
