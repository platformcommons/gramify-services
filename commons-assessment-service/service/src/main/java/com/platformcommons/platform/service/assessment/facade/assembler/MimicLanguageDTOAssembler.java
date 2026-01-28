package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.MimicLanguageDTO;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.entity.common.MLText;

public interface MimicLanguageDTOAssembler {
    MimicLanguageDTO toDTO(MLText languageDTO);
    String fromDTO(MimicLanguageDTO languageDTO);

    MimicLanguageDTO toMimicLanguageDTO(MLTextDTO mlTextDTO);
}
