package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.AppFeatureConfiguration;

import java.util.List;

/**
 * Service contract for managing App Feature Configuration lifecycle and retrieval.
 */
public interface AppFeatureConfigurationService {

    AppFeatureConfiguration getByParams(String ownerEntityId, String ownerEntityType, String forEntityType);

    List<AppFeatureConfiguration> getAllByOwnerEntityType(String ownerEntityType, String forEntityType);

    AppFeatureConfiguration getForLoggedInTenantByHierarchy(String marketId, String forEntityType, String tenantId);

    AppFeatureConfiguration save(AppFeatureConfiguration entity);

    void patch(AppFeatureConfiguration entity);

    void deleteById(Long id, String reason);

    AppFeatureConfiguration getById(Long id);
    
}
