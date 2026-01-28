package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.StorageNeededForCrop;
import com.platformcommons.platform.service.blockprofile.dto.StorageNeededForCropDTO;
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
public interface StorageNeededForCropDTOAssembler {

    StorageNeededForCropDTO toDTO(StorageNeededForCrop entity);

    StorageNeededForCrop fromDTO(StorageNeededForCropDTO dto);
}