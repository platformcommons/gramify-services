package com.platformcommons.platform.service.app.facade.assembler.impl;

import com.platformcommons.platform.service.app.domain.ColumnPreference;
import com.platformcommons.platform.service.app.domain.ColumnPreference.ColumnPreferenceBuilder;
import com.platformcommons.platform.service.app.dto.ColumnPreferenceDTO;
import com.platformcommons.platform.service.app.dto.ColumnPreferenceDTO.ColumnPreferenceDTOBuilder;
import java.util.Objects;

import com.platformcommons.platform.service.app.facade.assembler.ColumnPreferenceDTOAssembler;
import com.platformcommons.platform.service.app.facade.assembler.ColumnSettingDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ColumnPreferenceDTOAssemblerImpl implements ColumnPreferenceDTOAssembler {

    @Autowired
    private ColumnSettingDTOAssembler columnSettingDTOAssembler;

    @Override
    public ColumnPreferenceDTO toDTO(ColumnPreference entity,Boolean setPrimaryKeyAsZero) {
        if ( entity == null ) {
            return null;
        }

        ColumnPreferenceDTOBuilder columnPreferenceDTO = ColumnPreferenceDTO.builder();

        columnPreferenceDTO.uuid( entity.getUuid() );
        columnPreferenceDTO.isActive( entity.getIsActive() );
        columnPreferenceDTO.inactiveReason( entity.getInactiveReason() );
        columnPreferenceDTO.id( entity.getId() );
        columnPreferenceDTO.schemaCode( entity.getSchemaCode() );
        columnPreferenceDTO.entityId( entity.getEntityId() );
        columnPreferenceDTO.entityType( entity.getEntityType() );
        columnPreferenceDTO.ownerId( entity.getOwnerId() );
        columnPreferenceDTO.ownerType( entity.getOwnerType() );
        columnPreferenceDTO.parentColumnPreferenceId( entity.getParentColumnPreferenceId() );
        columnPreferenceDTO.columnSettings( columnSettingDTOAssembler.toDTOs(entity.getColumnSettings(),setPrimaryKeyAsZero) );

        if(Objects.equals(setPrimaryKeyAsZero,Boolean.TRUE)) {
            columnPreferenceDTO.id( 0L );
            columnPreferenceDTO.uuid(null);
        }
        return columnPreferenceDTO.build();
    }

    @Override
    public ColumnPreference fromDTO(ColumnPreferenceDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ColumnPreferenceBuilder columnPreference = ColumnPreference.builder();

        columnPreference.uuid( dto.getUuid() );
        columnPreference.isActive( dto.getIsActive() );
        columnPreference.inactiveReason( dto.getInactiveReason() );
        columnPreference.id( dto.getId() );
        columnPreference.schemaCode( dto.getSchemaCode() );
        columnPreference.entityId( dto.getEntityId() );
        columnPreference.entityType( dto.getEntityType() );
        columnPreference.ownerId( dto.getOwnerId() );
        columnPreference.ownerType( dto.getOwnerType() );
        columnPreference.parentColumnPreferenceId( dto.getParentColumnPreferenceId() );
        columnPreference.columnSettings( columnSettingDTOAssembler.fromDTOs(dto.getColumnSettings()) );

        return columnPreference.build();
    }

}
