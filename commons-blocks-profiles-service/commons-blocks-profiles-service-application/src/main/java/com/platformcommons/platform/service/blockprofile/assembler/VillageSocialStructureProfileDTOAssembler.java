package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageSocialStructureProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageSocialStructureProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        OtherCommunityGroupDTOAssembler.class,
    })
public interface VillageSocialStructureProfileDTOAssembler {

    VillageSocialStructureProfileDTO toDTO(VillageSocialStructureProfile entity);

    VillageSocialStructureProfile fromDTO(VillageSocialStructureProfileDTO dto);
}