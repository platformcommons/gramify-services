package com.platformcommons.platform.service.app.controller;

import com.platformcommons.platform.service.app.client.ColumnPreferenceAPI;
import com.platformcommons.platform.service.app.dto.ColumnPreferenceDTO;
import com.platformcommons.platform.service.app.facade.ColumnPreferenceFacade;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "ColumnPreference")
public class ColumnPreferenceController implements ColumnPreferenceAPI {

    @Autowired
    private ColumnPreferenceFacade facade;

    @Override
    public ResponseEntity<ColumnPreferenceDTO> getById(Long id) {
        ColumnPreferenceDTO columnPreferenceDTO = facade.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(columnPreferenceDTO);
    }

    @Override
    public ResponseEntity<ColumnPreferenceDTO> getByParamsForExactMatch(String ownerType, String schemaCode, String entityId,
                                                                        String entityType, String ownerId) {
        ColumnPreferenceDTO columnPreferenceDTO = facade.getByParamsForExactMatch(
                entityId, entityType, schemaCode, ownerId, ownerType);
        return ResponseEntity.status(HttpStatus.OK).body(columnPreferenceDTO);
    }

    @Override
    public ResponseEntity<ColumnPreferenceDTO> getForLoggedInUserByHierarchy(String marketId, String schemaCode,
                                                                             String entityId, String entityType) {
        ColumnPreferenceDTO columnPreferenceDTO = facade.getForLoggedInUserByHierarchy(marketId, schemaCode, entityId, entityType);
        return ResponseEntity.status(HttpStatus.OK).body(columnPreferenceDTO);
    }

    @Override
    public ResponseEntity<Void> patchForDefaultOwnerLevels(ColumnPreferenceDTO body) {
        facade.patchUpdateForDefaultOwnerLevels(body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Void> patchUpdateForLoggedInUser(ColumnPreferenceDTO body) {
        facade.patchUpdateForLoggedInUser(body);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Override
    public ResponseEntity<Long> postForDefaultOwnerLevels(ColumnPreferenceDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.postForDefaultOwnerLevels(body));
    }

    @Override
    public ResponseEntity<Long> postForLoggedInUser(ColumnPreferenceDTO body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(facade.postForLoggedInUser(body));

    }

    @Override
    public ResponseEntity<Void> syncColumnPreference(String sourceOwnerType, String schemaCode) {
        facade.syncColumnPreference(sourceOwnerType,schemaCode);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
