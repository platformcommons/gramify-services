package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.SchemeImplementationChallenge;
import com.platformcommons.platform.service.blockprofile.dto.SchemeImplementationChallengeDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        GovtSchemesDTOAssembler.class,
    })
public interface SchemeImplementationChallengeDTOAssembler {

    SchemeImplementationChallengeDTO toDTO(SchemeImplementationChallenge entity);

    SchemeImplementationChallenge fromDTO(SchemeImplementationChallengeDTO dto);
}