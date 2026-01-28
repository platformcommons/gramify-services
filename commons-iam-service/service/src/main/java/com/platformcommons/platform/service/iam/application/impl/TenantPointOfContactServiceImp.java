package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.DataAccessException;
import com.platformcommons.platform.service.iam.application.TenantPointOfContactService;
import com.platformcommons.platform.service.iam.application.TenantProfileService;
import com.platformcommons.platform.service.iam.domain.TenantPointOfContact;
import com.platformcommons.platform.service.iam.domain.TenantProfile;
import com.platformcommons.platform.service.iam.domain.repo.TenantPointOfContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TenantPointOfContactServiceImp implements TenantPointOfContactService {

    @Autowired
    private TenantPointOfContactRepository repository;

    @Autowired
    private TenantProfileService tenantProfileService;

    public TenantPointOfContact getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TenantPointOfContact not found with id: " + id));
    }

    @Override
    public TenantPointOfContact getByIdAndTenantId(Long tenantId, Long id){
        TenantPointOfContact tenantPointOfContact = getById(id);
        authorizeTenantAccess(tenantPointOfContact, tenantId);
        return tenantPointOfContact;
    }

    @Override
    public TenantPointOfContact post(Long tenantId, TenantPointOfContact tenantPointOfContact) {
        TenantProfile tenantProfile = tenantProfileService.getOrCreateByTenantId(tenantId);
        tenantPointOfContact.init(tenantProfile);

        return repository.save(tenantPointOfContact);
    }

    @Override
    public void patch( TenantPointOfContact tobeUpdated,Long tenantId){
        TenantPointOfContact fetchTenantPointOfContact = getById(tobeUpdated.getId());
        authorizeTenantAccess(fetchTenantPointOfContact, tenantId);
        fetchTenantPointOfContact.patchUpdate(tobeUpdated);
        repository.save(fetchTenantPointOfContact);

    }

    public static void authorizeTenantAccess (TenantPointOfContact tenantPointOfContact, Long tenantId) {
        if (!tenantPointOfContact.getTenantId().equals(tenantId)){
            throw new DataAccessException("Not allowed to update Tenant Point of Contact");
        }
    }

    @Override
    public void deleteById(Long id,String inactiveReason,Long tenantId){
        TenantPointOfContact tenantPointOfContact = getById(id);
        authorizeTenantAccess(tenantPointOfContact, tenantId);
        tenantPointOfContact.deactivate(inactiveReason);
        repository.save(tenantPointOfContact);
    }

    @Override
    public Page<TenantPointOfContact> getPageByTenantId(Long tenantId, Pageable pageable) {
        return repository.findByTenantId(tenantId, pageable);
    }

}
