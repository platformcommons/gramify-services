package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.domain.AiaDefaultResponse;
import com.platformcommons.platform.service.assessment.dto.AiaDefaultResponseDTO;

public interface AiaDefaultResponseDTOAssembler {

    AiaDefaultResponseDTO toDTO(AiaDefaultResponse entity);

    AiaDefaultResponse fromDTO(AiaDefaultResponseDTO dto);
}