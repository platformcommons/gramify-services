package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.ColumnSetting;
import com.platformcommons.platform.service.app.dto.ColumnSettingDTO;

import java.util.Set;

public interface ColumnSettingDTOAssembler {

    ColumnSettingDTO toDTO(ColumnSetting entity, Boolean setPrimaryKeyAsZero);

    Set<ColumnSettingDTO> toDTOs(Set<ColumnSetting> set, Boolean setPrimaryKeyAsZero);

    ColumnSetting fromDTO(ColumnSettingDTO dto);

    Set<ColumnSetting> fromDTOs(Set<ColumnSettingDTO> set);

}
