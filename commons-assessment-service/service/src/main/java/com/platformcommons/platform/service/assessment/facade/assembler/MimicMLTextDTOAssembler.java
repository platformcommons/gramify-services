package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.dto.MimicMLTextDTO;
import com.platformcommons.platform.service.dto.commons.MLTextDTO;
import com.platformcommons.platform.service.entity.common.MLText;

import java.util.Set;

public interface MimicMLTextDTOAssembler {
    MimicMLTextDTO toDTO(MLText mlText);
    Set<MimicMLTextDTO> toDTOs(Set<MLText> mlTexts);

    MLText fromDTO(MimicMLTextDTO mimicmltextDTO);
    Set<MLText> fromDTOs(Set<MimicMLTextDTO> mimicMLTextDTOS);


    MimicMLTextDTO toMimicMLTextDTO(MLTextDTO mlTextDTO);
    MLTextDTO toMLTextDTO(MimicMLTextDTO mimicMLTextDTO);

}
