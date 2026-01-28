package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.MimicLinkedRefCodeDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicLinkedRefCodeDTOAssembler;
import org.springframework.stereotype.Component;

@Component
public class MimicLinkedRefCodeDTOAssemblerImpl implements MimicLinkedRefCodeDTOAssembler {
    @Override
    public MimicLinkedRefCodeDTO toDTO(String refClassCode) {
        return MimicLinkedRefCodeDTO.builder().code(refClassCode).build();
    }

    @Override
    public String fromDTO(MimicLinkedRefCodeDTO refClassDTO) {
        return refClassDTO==null?null:refClassDTO.getCode();
    }
}
