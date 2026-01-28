package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.AppFeatureConfigurationAPI;
import com.platformcommons.platform.service.app.dto.AppFeatureConfigurationDTO;
import com.platformcommons.platform.service.app.facade.AppFeatureConfigurationFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@Tag(name = "AppFeatureConfiguration", description = "CRUD and query endpoints for App Feature Configuration.")
public class AppFeatureConfigurationController implements AppFeatureConfigurationAPI {

    @Autowired
    private AppFeatureConfigurationFacade facade;

    @Override
    public ResponseEntity<Void> delete(Long id, String reason) {
        facade.deleteById(id, reason);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<AppFeatureConfigurationDTO> getById(Long id) {
        return ResponseEntity.ok(facade.getById(id));
    }

    @Override
    public ResponseEntity<AppFeatureConfigurationDTO> getByLoggedInTenantWithHierarchy(String marketId, String forEntityType, String tenantId) {
        return ResponseEntity.ok(facade.getForLoggedInTenantByHierarchy(marketId, forEntityType, tenantId));
    }

    @Override
    public ResponseEntity<AppFeatureConfigurationDTO> getByLoggedInTenantWithHierarchyAndFallback(String marketId, String forEntityType, String tenantId) {
        return ResponseEntity.ok(facade.getForLoggedInTenantByHierarchyAndFallback(marketId, forEntityType, tenantId));
    }

    @Override
    public ResponseEntity<Void> patch(AppFeatureConfigurationDTO body) {
        facade.patch(body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<AppFeatureConfigurationDTO> save(AppFeatureConfigurationDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.save(body));
    }

    @Override
    public ResponseEntity<Void> syncDefaultFeatureConfigToGivenFeatureConfigs(String forEntityType, String ownerEntityType) {
        facade.syncDefaultFeatureConfigToGivenFeatureConfigs(forEntityType,ownerEntityType);
        return ResponseEntity.noContent().build();
    }

}
