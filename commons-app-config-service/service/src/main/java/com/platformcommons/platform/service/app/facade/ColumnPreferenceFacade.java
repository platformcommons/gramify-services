package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.dto.ColumnPreferenceDTO;

public interface ColumnPreferenceFacade {

    ColumnPreferenceDTO getById(Long id);

    ColumnPreferenceDTO getForLoggedInUserByHierarchy(String marketId, String schemaCode, String entityId, String entityType);

    Long postForLoggedInUser(ColumnPreferenceDTO columnPreferenceDTO);

    Long postForDefaultOwnerLevels(ColumnPreferenceDTO columnPreferenceDTO);

    void patchUpdateForLoggedInUser(ColumnPreferenceDTO columnPreferenceDTO);

    void patchUpdateForDefaultOwnerLevels(ColumnPreferenceDTO columnPreferenceDTO);

    ColumnPreferenceDTO getByParamsForExactMatch(String entityId, String entityType, String schemaCode, String ownerId, String ownerType);

    void syncColumnPreference(String sourceOwnerType, String schemaCode);
}
