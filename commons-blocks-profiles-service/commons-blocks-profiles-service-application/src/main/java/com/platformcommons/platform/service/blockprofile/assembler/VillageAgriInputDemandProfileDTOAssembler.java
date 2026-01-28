package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.VillageAgriInputDemandProfile;
import com.platformcommons.platform.service.blockprofile.dto.VillageAgriInputDemandProfileDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        MachinesInDemandDTOAssembler.class,
        PurposeOfCreditDTOAssembler.class,
        FertilizersInDemandDTOAssembler.class,
        SourceOfRawMaterialDTOAssembler.class,
        SeedsInDemandDTOAssembler.class,
        MainCreditSourceDTOAssembler.class,
        ExistingStorageIssueDTOAssembler.class,
        StorageNeededForCropDTOAssembler.class,
        WhereFarmersBuyInputDTOAssembler.class,
        RawMaterialsNeededForIndustryDTOAssembler.class,
        PesticidesInDemandDTOAssembler.class,
        CurrentStorageMethodDTOAssembler.class,
    })
public interface VillageAgriInputDemandProfileDTOAssembler {

    VillageAgriInputDemandProfileDTO toDTO(VillageAgriInputDemandProfile entity);

    VillageAgriInputDemandProfile fromDTO(VillageAgriInputDemandProfileDTO dto);
}