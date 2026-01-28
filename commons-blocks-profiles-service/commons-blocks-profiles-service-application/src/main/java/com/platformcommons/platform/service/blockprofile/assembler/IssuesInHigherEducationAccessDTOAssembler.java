package com.platformcommons.platform.service.blockprofile.assembler;

import com.platformcommons.platform.service.blockprofile.domain.IssuesInHigherEducationAccess;
import com.platformcommons.platform.service.blockprofile.dto.IssuesInHigherEducationAccessDTO;
import com.platformcommons.platform.service.blockprofile.assembler.LinkedDTOAssembler;
import java.util.*;

import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
       MLTextDTOAssembler.class,
       RefDataDTOValidator.class,
       LinkedDTOAssembler.class,
        VillageEducationInfrastructureDTOAssembler.class,
    })
public interface IssuesInHigherEducationAccessDTOAssembler {

    IssuesInHigherEducationAccessDTO toDTO(IssuesInHigherEducationAccess entity);

    IssuesInHigherEducationAccess fromDTO(IssuesInHigherEducationAccessDTO dto);
}