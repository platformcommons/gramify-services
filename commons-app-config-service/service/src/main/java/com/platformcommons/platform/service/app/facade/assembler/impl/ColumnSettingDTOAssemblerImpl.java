package com.platformcommons.platform.service.app.facade.assembler.impl;

import com.platformcommons.platform.service.app.domain.ColumnSetting;
import com.platformcommons.platform.service.app.domain.ColumnSetting.ColumnSettingBuilder;
import com.platformcommons.platform.service.app.dto.ColumnSettingDTO;
import com.platformcommons.platform.service.app.dto.ColumnSettingDTO.ColumnSettingDTOBuilder;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.platformcommons.platform.service.app.facade.assembler.ColumnSettingDTOAssembler;
import org.springframework.stereotype.Component;

@Component
public class ColumnSettingDTOAssemblerImpl implements ColumnSettingDTOAssembler {

    @Override
    public ColumnSettingDTO toDTO(ColumnSetting entity,Boolean setPrimaryKeyAsZero) {
        if ( entity == null ) {
            return null;
        }

        ColumnSettingDTOBuilder columnSettingDTO = ColumnSettingDTO.builder();

        columnSettingDTO.uuid( entity.getUuid() );
        columnSettingDTO.isActive( entity.getIsActive() );
        columnSettingDTO.inactiveReason( entity.getInactiveReason() );
        columnSettingDTO.id( entity.getId() );
        columnSettingDTO.code( entity.getCode() );
        columnSettingDTO.label( entity.getLabel() );
        columnSettingDTO.sequence( entity.getSequence() );
        columnSettingDTO.visibility( entity.getVisibility() );

        if(Objects.equals(setPrimaryKeyAsZero,Boolean.TRUE)) {
            columnSettingDTO.id( 0L );
            columnSettingDTO.uuid(null);
        }
        return columnSettingDTO.build();
    }

    @Override
    public Set<ColumnSettingDTO> toDTOs(Set<ColumnSetting> set,Boolean setPrimaryKeyAsZero) {
        if ( set == null ) {
            return null;
        }

        Set<ColumnSettingDTO> set1 = new HashSet<ColumnSettingDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ColumnSetting columnSetting : set ) {
            set1.add( toDTO( columnSetting,setPrimaryKeyAsZero ) );
        }

        return set1;
    }

    @Override
    public Set<ColumnSetting> fromDTOs(Set<ColumnSettingDTO> set) {
        if ( set == null ) {
            return null;
        }

        Set<ColumnSetting> set1 = new HashSet<ColumnSetting>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( ColumnSettingDTO columnSettingDTO : set ) {
            set1.add( fromDTO( columnSettingDTO ) );
        }

        return set1;
    }

    @Override
    public ColumnSetting fromDTO(ColumnSettingDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ColumnSettingBuilder columnSetting = ColumnSetting.builder();

        columnSetting.uuid( dto.getUuid() );
        columnSetting.isActive( dto.getIsActive() );
        columnSetting.inactiveReason( dto.getInactiveReason() );
        columnSetting.id( dto.getId() );
        columnSetting.code( dto.getCode() );
        columnSetting.label( dto.getLabel() );
        columnSetting.sequence( dto.getSequence() );
        columnSetting.visibility( dto.getVisibility() );

        return columnSetting.build();
    }
}