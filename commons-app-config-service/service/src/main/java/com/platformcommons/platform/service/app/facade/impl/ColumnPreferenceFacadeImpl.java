package com.platformcommons.platform.service.app.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.ColumnPreferenceService;
import com.platformcommons.platform.service.app.application.constant.ServiceConstant;
import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.app.domain.ColumnPreference;
import com.platformcommons.platform.service.app.dto.ColumnPreferenceDTO;
import com.platformcommons.platform.service.app.facade.ColumnPreferenceFacade;
import com.platformcommons.platform.service.app.facade.assembler.ColumnPreferenceDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Component
@Transactional
public class ColumnPreferenceFacadeImpl implements ColumnPreferenceFacade {

    @Autowired
    private ColumnPreferenceService service;

    @Autowired
    private ColumnPreferenceDTOAssembler columnPreferenceDTOAssembler;

    @Override
    public ColumnPreferenceDTO getById(Long id) {
        ColumnPreference columnPreference = service.getById(id);
        return columnPreferenceDTOAssembler.toDTO(columnPreference, Boolean.FALSE);
    }

    @Override
    public ColumnPreferenceDTO getForLoggedInUserByHierarchy(String marketId, String schemaCode, String entityId,
                                                             String entityType) {
        String userId = String.valueOf(PlatformSecurityUtil.getCurrentUserId());
        String tenantId = String.valueOf(PlatformSecurityUtil.getCurrentTenantId());
        Boolean setPrimaryKeyAsZero = Boolean.FALSE;

        ColumnPreference columnPreference = service.getByUserIdAndHierarchy(marketId,entityId,entityType,schemaCode,
                userId,tenantId);
        if(columnPreference != null && !Objects.equals(columnPreference.getOwnerType(), ServiceConstant.OWNER_TYPE_USER)) {
            setPrimaryKeyAsZero = Boolean.TRUE;
        }
        ColumnPreferenceDTO columnPreferenceDTO = columnPreferenceDTOAssembler.toDTO(columnPreference,setPrimaryKeyAsZero);

        if(setPrimaryKeyAsZero) {
            columnPreferenceDTO.setParentColumnPreferenceId(columnPreference.getId());
        }
        return columnPreferenceDTO;
    }

    @Override
    public Long postForLoggedInUser(ColumnPreferenceDTO columnPreferenceDTO) {
        Objects.requireNonNull(columnPreferenceDTO.getSchemaCode(),"SchemaCode must not be null");
        Objects.requireNonNull(columnPreferenceDTO.getParentColumnPreferenceId(),"ParentColumnPreferenceId must not be null");
        String userId = String.valueOf(PlatformSecurityUtil.getCurrentUserId());
        columnPreferenceDTO.setOwnerId(userId);
        columnPreferenceDTO.setOwnerType(ServiceConstant.OWNER_TYPE_USER);
        return service.post(columnPreferenceDTOAssembler.fromDTO(columnPreferenceDTO));
    }

    @Override
    public Long postForDefaultOwnerLevels(ColumnPreferenceDTO columnPreferenceDTO) {
        PlatformUtil.validatePlatformAdmin();
        Objects.requireNonNull(columnPreferenceDTO.getSchemaCode(),"SchemaCode must not be null");
        Objects.requireNonNull(columnPreferenceDTO.getOwnerType(),"OwnerType must not be null");

        if(!Objects.equals(columnPreferenceDTO.getOwnerType(),ServiceConstant.OWNER_TYPE_PLATFORM)
                   && columnPreferenceDTO.getParentColumnPreferenceId() == null) {
            throw new InvalidInputException("ParentColumnPreferenceId must not be null");
        }
        if(Objects.equals(columnPreferenceDTO.getOwnerType(),ServiceConstant.OWNER_TYPE_USER)) {
            throw new InvalidInputException("OwnerType must not be of type USER");
        }
        return service.post(columnPreferenceDTOAssembler.fromDTO(columnPreferenceDTO));
    }

    @Override
    public void patchUpdateForLoggedInUser(ColumnPreferenceDTO columnPreferenceDTO) {
        String userId = String.valueOf(PlatformSecurityUtil.getCurrentUserId());
        service.patchUpdateForLoggedInUser(columnPreferenceDTOAssembler.fromDTO(columnPreferenceDTO),userId);
    }

    public void patchUpdateForDefaultOwnerLevels(ColumnPreferenceDTO columnPreferenceDTO) {
        PlatformUtil.validatePlatformAdmin();
        if (columnPreferenceDTO.getOwnerType() != null && Objects.equals(columnPreferenceDTO.getOwnerType(), ServiceConstant.OWNER_TYPE_USER)) {
            throw new InvalidInputException("OwnerType in the DTO must not be of type USER for default level patch.");
        }
        ColumnPreference columnPreference = columnPreferenceDTOAssembler.fromDTO(columnPreferenceDTO);
        service.patchUpdateForDefaultOwnerLevels(columnPreference);
    }

    @Override
    public ColumnPreferenceDTO getByParamsForExactMatch(String entityId, String entityType, String schemaCode, String ownerId, String ownerType) {
        PlatformUtil.validatePlatformAdmin();
        ColumnPreference columnPreference = service.getByParamsForExactMatch(entityId,entityType,schemaCode,ownerId,ownerType);
        return columnPreferenceDTOAssembler.toDTO(columnPreference,Boolean.FALSE);
    }

    @Override
    public void syncColumnPreference(String sourceOwnerType, String schemaCode) {
        PlatformUtil.validatePlatformAdmin();
        service.syncColumnPreference(sourceOwnerType,schemaCode);
    }
}
