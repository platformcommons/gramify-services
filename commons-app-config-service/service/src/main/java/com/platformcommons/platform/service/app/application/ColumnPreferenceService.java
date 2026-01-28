package com.platformcommons.platform.service.app.application;

import com.platformcommons.platform.service.app.domain.ColumnPreference;

public interface ColumnPreferenceService {

    ColumnPreference getByUserIdAndHierarchy( String marketId, String entityId, String entityType,String schemaCode,
                                                     String userId, String tenantId);

    Long post(ColumnPreference columnPreference);

    void patchUpdateForLoggedInUser(ColumnPreference columnPreference, String userId);

    ColumnPreference getById(Long id);

    void patchUpdateForDefaultOwnerLevels(ColumnPreference columnPreference);

    ColumnPreference getByParamsForExactMatch(String entityId, String entityType, String schemaCode, String ownerId, String ownerType);

    void syncColumnPreference(String sourceOwnerType, String schemaCode);
}
