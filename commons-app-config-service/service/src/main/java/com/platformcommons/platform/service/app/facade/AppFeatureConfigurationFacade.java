package com.platformcommons.platform.service.app.facade;

import com.platformcommons.platform.service.app.dto.AppFeatureConfigurationDTO;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

public interface AppFeatureConfigurationFacade {

    AppFeatureConfigurationDTO getByParams(String ownerEntityId, String ownerEntityType, String forEntityType);

    List<AppFeatureConfigurationDTO> getAllByOwnerEntityType(String ownerEntityType, String forEntityType);

    AppFeatureConfigurationDTO getForLoggedInTenantByHierarchyAndFallback(String marketId, String forEntityType, String tenantId);

    AppFeatureConfigurationDTO getForLoggedInTenantByHierarchy(String marketId, String forEntityType, String tenantId);

    AppFeatureConfigurationDTO save(AppFeatureConfigurationDTO body);

    void patch(AppFeatureConfigurationDTO body);

    void deleteById(Long id, String reason);

    AppFeatureConfigurationDTO getById(Long id);

    void syncDefaultFeatureConfigToGivenFeatureConfigs(@NotNull @Valid String forEntityType,
                                                       @NotNull @Valid String ownerEntityType);
}
