package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.service.iam.application.TenantPartnerService;
import com.platformcommons.platform.service.iam.domain.Tenant;
import com.platformcommons.platform.service.iam.domain.TenantPartner;
import com.platformcommons.platform.service.iam.domain.repo.TenantPartnerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class TenantPartnerServiceImpl implements TenantPartnerService {

    private final TenantPartnerRepository tenantPartnerRepository;
    @Override
    public Long addTenantPartner(Long currentTenantId, Long parentTenantId, String metaValue, Boolean isPrimary) {
        TenantPartner tenantPartner = buildDomain(currentTenantId,parentTenantId,metaValue,isPrimary);
        return tenantPartnerRepository.save(tenantPartner).getId();
    }

    @Override
    public Long getTenantPartner(Long currentTenantId, Long parentTenantId) {
        return tenantPartnerRepository.getTenantPartner(currentTenantId,parentTenantId);
    }

    private TenantPartner buildDomain(Long currentTenantId, Long parentTenantId, String metaValue, Boolean isPrimary) {
        return TenantPartner.builder().id(null).parentTenant(Tenant.builder().id(parentTenantId).build())
                .tenant(Tenant.builder().id(currentTenantId).build()).status(metaValue).isPrimary(isPrimary).build();
    }
}
