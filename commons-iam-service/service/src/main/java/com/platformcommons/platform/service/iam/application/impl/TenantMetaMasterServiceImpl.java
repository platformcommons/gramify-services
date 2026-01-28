package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.service.iam.application.TenantMetaMasterService;
import com.platformcommons.platform.service.iam.domain.TenantMetaMaster;
import com.platformcommons.platform.service.iam.domain.repo.TenantMetaMasterRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TenantMetaMasterServiceImpl implements TenantMetaMasterService {

    private final TenantMetaMasterRepository tenantMetaMasterRepository;

    public TenantMetaMasterServiceImpl(TenantMetaMasterRepository tenantMetaMasterRepository) {
        this.tenantMetaMasterRepository = tenantMetaMasterRepository;
    }

    @Override
    public List<TenantMetaMaster> getTenantMetaMasterForAppContext(String appContext) {
        return tenantMetaMasterRepository.findByAppContext(appContext);
    }

    @Override
    public Optional<TenantMetaMaster> getByAppContextAndCode(String appContext, String metaCode) {
        return tenantMetaMasterRepository.findByAppContextAndMetaCode(appContext, metaCode);
    }

    @Override
    public TenantMetaMaster saveTenantMetaMaster(TenantMetaMaster tenantMetaMaster) {
        return tenantMetaMasterRepository.save(tenantMetaMaster);
    }

    @Override
    public Optional<TenantMetaMaster> getMetaData(String code) {
        return tenantMetaMasterRepository.getTenantMetaMasterByMetaCode(code);
    }
}
