package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.ExistingStorageIssue;
import com.platformcommons.platform.service.blockprofile.dto.ExistingStorageIssueDTO;
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
public interface ExistingStorageIssueDTOAssembler {

    ExistingStorageIssueDTO toDTO(ExistingStorageIssue entity);

    ExistingStorageIssue fromDTO(ExistingStorageIssueDTO dto);
}