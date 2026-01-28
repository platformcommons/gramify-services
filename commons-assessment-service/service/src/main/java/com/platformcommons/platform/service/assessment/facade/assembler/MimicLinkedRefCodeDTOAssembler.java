package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.MimicLinkedRefCodeDTO;

public interface MimicLinkedRefCodeDTOAssembler {
    MimicLinkedRefCodeDTO toDTO(String refClassDTO);
    String fromDTO(MimicLinkedRefCodeDTO refClassDTO);
}
