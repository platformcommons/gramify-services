package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.MimicLanguageDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.MimicLanguageDTOAssembler;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.entity.common.MLText;
import org.springframework.stereotype.Component;

@Component
public class MimicLanguageDTOAssemblerImpl implements MimicLanguageDTOAssembler {


    @Override
    public MimicLanguageDTO toDTO(MLText mlText) {
        return MimicLanguageDTO.builder()
                .code(mlText.getLanguageCode())
                .build();
    }

    @Override
    public String fromDTO(MimicLanguageDTO languageDTO) {
        return languageDTO.getCode();
    }
    @Override
    public MimicLanguageDTO toMimicLanguageDTO(MLTextDTO mlTextDTO) {
        return MimicLanguageDTO.builder()
                .code(mlTextDTO.getLanguageCode())
                .build();
    }
}
