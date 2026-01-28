package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.MimicRefDataDTO;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import com.platformcommons.platform.service.entity.common.RefData;

public interface MimicRefDataDTOAssembler {
    MimicRefDataDTO toDTO(RefData refData);
    RefData fromDTO(MimicRefDataDTO mimicRefDataDTO);

    RefDataDTO toRefDataDTO(MimicRefDataDTO assessmentType);

    MimicRefDataDTO toMimicRefDataDTO(RefDataDTO dto);
}
