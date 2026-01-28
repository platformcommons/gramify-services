package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.service.iam.application.TenantMetaMasterService;
import com.platformcommons.platform.service.iam.application.TenantMetaService;
import com.platformcommons.platform.service.iam.domain.TenantMeta;
import com.platformcommons.platform.service.iam.domain.repo.TenantMetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class TenantMetaServiceImpl implements TenantMetaService {

    @Autowired
    private TenantMetaRepository tenantMetaRepository;
    @Autowired
    private TenantMetaMasterService tenantMetaMasterService;

    @Override
    public void addTenantMetaData(String tenantLogin, TenantMeta tenantMeta) {

    }

    @Override
    public TenantMeta getTenantMetaData(String tenantLogin, String metaCode) {
        return tenantMetaRepository.findByTenantLoginAndMetaCode(tenantLogin, metaCode).orElse(null);
    }

    @Override
    public String getMetaData(String code, Long tenantId) {
        return tenantMetaRepository.getTenantMetaByMetaCode(code,tenantId)
                .orElseGet(() -> tenantMetaMasterService.getMetaData(code)
                        .flatMap(metaMaster -> metaMaster.getMetaDefaultValues().stream().findFirst())
                        .orElse(null));
    }
}
