package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.ColumnPreference;
import com.platformcommons.platform.service.app.dto.ColumnPreferenceDTO;

public interface ColumnPreferenceDTOAssembler {

    ColumnPreferenceDTO toDTO(ColumnPreference entity,Boolean setPrimaryKeyAsZero);

    ColumnPreference fromDTO(ColumnPreferenceDTO dto);
}
