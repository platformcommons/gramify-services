package com.platformcommons.platform.service.app.application.impl;

import com.platformcommons.platform.exception.generic.DuplicateResourceException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.AppFeatureConfigurationService;
import com.platformcommons.platform.service.app.application.constant.AppFeatureConfigurationConstant;
import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.app.domain.AppFeatureConfiguration;
import com.platformcommons.platform.service.app.domain.repo.FeatureConfigurationNonMTRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for managing App Feature Configuration.
 * Includes hierarchy-based retrieval, creation with duplicate validation, partial updates, and soft deletion.
 */
@Service
public class AppFeatureConfigurationServiceImpl implements AppFeatureConfigurationService {

    @Autowired
    private FeatureConfigurationNonMTRepository nonMTRepository;

    @Override
    public AppFeatureConfiguration getByParams(String ownerEntityId, String ownerEntityType, String forEntityType) {
        return nonMTRepository.findByParams(ownerEntityId, ownerEntityType, forEntityType).orElseThrow(
                ()-> new NotFoundException("AppFeatureConfig not found")
        );
    }

    @Override
    public List<AppFeatureConfiguration> getAllByOwnerEntityType(String ownerEntityType, String forEntityType) {
        return nonMTRepository.findAllByOwnerEntityType(ownerEntityType,forEntityType);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AppFeatureConfiguration getForLoggedInTenantByHierarchy(String marketId, String forEntityType, String tenantId) {
        AppFeatureConfiguration appFeatureConfiguration;
        Optional<AppFeatureConfiguration> optionalFeatureConfiguration;

        // 1. Tenant level
        if(tenantId != null){
            optionalFeatureConfiguration = nonMTRepository.findByParams(tenantId, AppFeatureConfigurationConstant.OWNER_ENTITY_TYPE_TENANT,
                    forEntityType);
            if (optionalFeatureConfiguration.isPresent()) {
                return optionalFeatureConfiguration.get();
            }
        }

        // 2. Market level
        optionalFeatureConfiguration = nonMTRepository.findByParams(marketId,AppFeatureConfigurationConstant.OWNER_ENTITY_TYPE_MARKET,
                forEntityType);
        if (optionalFeatureConfiguration.isPresent()) {
            return optionalFeatureConfiguration.get();
        }

        // 3. Platform default (mark fallback)
        optionalFeatureConfiguration = nonMTRepository.findByParams(null,AppFeatureConfigurationConstant.OWNER_ENTITY_TYPE_PLATFORM,
                forEntityType);
        if (optionalFeatureConfiguration.isPresent()) {
            appFeatureConfiguration = optionalFeatureConfiguration.get();
            appFeatureConfiguration.setIsFallback(Boolean.TRUE);
            return optionalFeatureConfiguration.get();
        }
        else {
            throw new NotFoundException("Default Feature Configuration not found");
        }

    }

    /**
     * Create a new configuration with duplicate guard based on
     * (ownerEntityId, ownerEntityType, forEntityType). Initializes child components prior to save.
     *
     * @param entity configuration to create
     * @return persisted configuration
     * @throws DuplicateResourceException when a configuration already exists for the same owner and entity type
     */
    @Override
    public AppFeatureConfiguration save(AppFeatureConfiguration entity) {
        // Duplicate check by (ownerEntityId, ownerEntityType, forEntityType)
        Optional<AppFeatureConfiguration> existing = nonMTRepository.findByParams(
                entity.getOwnerEntityId(), entity.getOwnerEntityType(), entity.getForEntityType());
        if (existing.isPresent()) {
            throw new DuplicateResourceException("Feature configuration already exists for the given owner and entity type");
        }

        entity.init();
        return nonMTRepository.save(entity);
    }

    /**
     * Partially update an existing configuration by patching component sets and adding new ones.
     *
     * @param entity contains ID to update and fields to patch
     * @throws NotFoundException when the configuration is not found
     */
    @Override
    public void patch(AppFeatureConfiguration entity) {
        AppFeatureConfiguration fetchedFeatureConfig = getById(entity.getId());
        fetchedFeatureConfig.update(entity);
        nonMTRepository.save(fetchedFeatureConfig);
    }

    /**
     * Soft delete a configuration by ID with an optional reason stored on the entity.
     *
     * @param id configuration id
     * @param reason optional reason for deactivation
     * @throws NotFoundException when the configuration is not found
     */
    @Override
    public void deleteById(Long id, String reason) {
        AppFeatureConfiguration fetchedFeatureConfig = getById(id);
        fetchedFeatureConfig.deactivate(reason);
        nonMTRepository.save(fetchedFeatureConfig);
    }

    /**
     * Fetch configuration by ID or throw if missing.
     *
     * @param id configuration id
     * @return persisted configuration
     * @throws NotFoundException when not found
     */
    @Override
    public AppFeatureConfiguration getById(Long id) {
        return nonMTRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Feature configuration not found"));
    }

}
