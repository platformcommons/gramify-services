package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.CommonHealthIssue;
import com.platformcommons.platform.service.blockprofile.dto.CommonHealthIssueDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageHealthInfrastructureDTOAssembler.class,
    })
public interface CommonHealthIssueDTOAssembler {

    CommonHealthIssueDTO toDTO(CommonHealthIssue entity);

    CommonHealthIssue fromDTO(CommonHealthIssueDTO dto);
}