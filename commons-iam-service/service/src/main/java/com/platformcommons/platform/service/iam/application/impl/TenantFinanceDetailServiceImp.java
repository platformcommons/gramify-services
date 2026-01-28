package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.DataAccessException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.iam.application.TenantFinanceDetailService;
import com.platformcommons.platform.service.iam.application.TenantProfileService;
import com.platformcommons.platform.service.iam.domain.TenantFinanceDetail;
import com.platformcommons.platform.service.iam.domain.TenantProfile;
import com.platformcommons.platform.service.iam.domain.repo.TenantFinanceDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class TenantFinanceDetailServiceImp implements TenantFinanceDetailService {

    @Autowired
    private TenantFinanceDetailRepo repository;

    @Autowired
    private TenantProfileService tenantProfileService;

    public TenantFinanceDetail getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TenantFinanceDetail not found with id: " + id));
    }

    @Override
    public TenantFinanceDetail createForLoggedInTenant(Long tenantId,TenantFinanceDetail tenantFinanceDetail) {
        TenantProfile tenantProfile = tenantProfileService.getOrCreateByTenantId(tenantId);
        tenantFinanceDetail.init(tenantProfile);
        return repository.save(tenantFinanceDetail);
    }

    @Override
    public void patchForLoggedInTenant(TenantFinanceDetail toBeUpdated,Long tenantId) {
        TenantFinanceDetail fetchedFinancialDetail = getById(toBeUpdated.getId());
        AuthorizeForUpdate(fetchedFinancialDetail,tenantId);
        fetchedFinancialDetail.patchUpdate(toBeUpdated);
        repository.save(fetchedFinancialDetail);
    }

    private static void AuthorizeForUpdate(TenantFinanceDetail tenantFinanceDetail,Long tenantId) {
        if (!tenantFinanceDetail.getTenantId().equals(tenantId)) {
            throw new DataAccessException("Not Allowed to update Tenant Financial Details");
        }
    }


    @Override
    public void deleteById(Long id,String inactiveReason,Long tenantId) {
        TenantFinanceDetail tenantFinanceDetail = getById(id);
        AuthorizeForUpdate(tenantFinanceDetail,tenantId);
        tenantFinanceDetail.deactivate(inactiveReason);
        repository.save(tenantFinanceDetail);
    }

    @Override
    public Page<TenantFinanceDetail> getPageByTenantId(Long tenantId, Pageable pageable) {
        return repository.findAllByTenantId(tenantId, pageable);
    }

}
