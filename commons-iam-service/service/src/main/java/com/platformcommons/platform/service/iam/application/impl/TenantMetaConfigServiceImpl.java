package com.platformcommons.platform.service.iam.application.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.iam.application.TenantMetaConfigService;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import com.platformcommons.platform.service.iam.domain.TenantMetaAdditionalProperty;
import com.platformcommons.platform.service.iam.domain.TenantMetaConfig;
import com.platformcommons.platform.service.iam.domain.repo.TenantMetaAdditionalPropertyRepository;
import com.platformcommons.platform.service.iam.domain.repo.TenantMetaConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TenantMetaConfigServiceImpl implements TenantMetaConfigService {

    private final TenantMetaConfigRepository tenantMetaConfigRepository;
    private final TenantMetaConfigRepository repository;
    private final TenantMetaAdditionalPropertyRepository tenantMetaAdditionalPropertyRepository;

    @Override
    public void save(TenantMetaConfig tenantMetaConfig) {
        tenantMetaConfig.init();
        repository.save(tenantMetaConfig);
    }

    @Override
    public TenantMetaConfig getTenantMetaConfig(Long tenantId, String tenantLogin) {
        Optional<TenantMetaConfig> tenantMetaConfig = tenantMetaConfigRepository.findByTenantIdOrTenantLogin(tenantId,tenantLogin);
        if(tenantMetaConfig.isPresent()) {
            return tenantMetaConfig.get();
        }
        else {
            if(tenantId != null) {
                throw new NotFoundException(String.format("Tenant Meta Config not found with tenantId %d",tenantId));
            }
            else {
                throw new NotFoundException(String.format("Tenant Meta Config not found with tenantLogin %s",tenantLogin));
            }
        }
    }

    @Override
    public Optional<TenantMetaConfig> getTenantMetaConfigOptional(Long tenantId, String tenantLogin) {
       return tenantMetaConfigRepository.findByTenantIdOrTenantLogin(tenantId,tenantLogin);
    }

    @Override
    public TenantMetaConfig getById(Long id) {
        return repository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Request TenantMetaConfig with  %d  not found",id)));
    }

    @Override
    public void addTenantMetaAdditionalProperty(String tenantLogin, Set<TenantMetaAdditionalProperty> tenantMetaAdditionalProperties) {
        TenantMetaConfig tenantMetaConfig  = getTenantMetaConfig(null,tenantLogin);
        PlatformUtil.validateLoginTenantAndAdminByTenantLogin(tenantLogin);
        tenantMetaAdditionalProperties.forEach(it->  it.init(tenantMetaConfig));
        tenantMetaAdditionalPropertyRepository.saveAll(tenantMetaAdditionalProperties);
    }

    @Override
    public void patchTenantMetaAdditionalProperty(String tenantLogin, Set<TenantMetaAdditionalProperty> tenantMetaAdditionalProperties) {
        TenantMetaConfig tenantMetaConfig  = getTenantMetaConfig(null,tenantLogin);
        PlatformUtil.validateLoginTenantAndAdminByTenantLogin(tenantLogin);
        tenantMetaConfig.patchTenantMetaConfigAdditionalProperty(tenantMetaAdditionalProperties);
        tenantMetaConfigRepository.save(tenantMetaConfig);
    }


    @Override
    public TenantMetaConfig patchTenantMetaConfigForLoggedInTenant(TenantMetaConfig tenantMetaConfig) {
        TenantMetaConfig fetchedTenantMetaConfig = getById(tenantMetaConfig.getId());
        PlatformUtil.validateLoginTenantAndAdminByTenantId(fetchedTenantMetaConfig.getTenantId());
        fetchedTenantMetaConfig.patch(tenantMetaConfig);
        fetchedTenantMetaConfig = repository.save(fetchedTenantMetaConfig);
        return fetchedTenantMetaConfig;
    }

    @Override
    public TenantMetaConfig postTenantMetaConfigForLoggedInTenant(TenantMetaConfig tenantMetaConfig) {
        if (repository.findByTenantLogin(PlatformSecurityUtil.getCurrentTenantLogin()).isPresent()) {
            throw new DuplicateResourceException(String.format("Tenant Meta config already exists for tenant with login %s"
                    ,PlatformSecurityUtil.getCurrentTenantLogin()));
        }
        tenantMetaConfig.init();
        return repository.save(tenantMetaConfig);
    }

    @Override
    public TenantMetaConfig deleteTenantMetaConfig(Long id, String reason) {
        PlatformUtil.validatePlatformAdmin();
        TenantMetaConfig fetchedTenantMetaConfig = getById(id);
        fetchedTenantMetaConfig.deactivate(reason);
        return repository.save(fetchedTenantMetaConfig);
    }

    @Override
    public Page<TenantMetaConfig> getAllTenantMetaConfig(String metaKey, String metaValue, Pageable pageable) {
        return repository.findAllTenantMetaConfigs(metaKey,metaValue, pageable);
    }

    @Override
    public Set<TenantMetaAdditionalProperty> getTenantMetaAdditionalPropertiesByMetaKeysAndTenantLogin(Set<String> metaKeys,
                                                                                                       String tenantLogin,
                                                                                                       String appContext,
                                                                                                       String defaultAppContext) {
        return tenantMetaAdditionalPropertyRepository.findAllByMetaKeysAndTenantLogin(metaKeys,tenantLogin,appContext,
                defaultAppContext);
    }

    @Override
    public Set<TenantMetaConfig> getAllTenantMetaConfigSet(String metaKey, String metaValue) {
        return repository.findAllTenantMetaConfigsSet(metaKey, metaValue);
    }

}
