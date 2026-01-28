package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.CurrentStorageMethod;
import com.platformcommons.platform.service.blockprofile.dto.CurrentStorageMethodDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageAgriInputDemandProfileDTOAssembler.class,
    })
public interface CurrentStorageMethodDTOAssembler {

    CurrentStorageMethodDTO toDTO(CurrentStorageMethod entity);

    CurrentStorageMethod fromDTO(CurrentStorageMethodDTO dto);
}