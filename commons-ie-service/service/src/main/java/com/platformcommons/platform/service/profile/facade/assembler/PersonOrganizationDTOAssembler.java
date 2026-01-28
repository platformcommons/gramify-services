package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonOrganization;
import com.platformcommons.platform.service.profile.dto.PersonOrganizationDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
          OrganizationFinanceDTOAssembler.class,
    })
public interface PersonOrganizationDTOAssembler {

    PersonOrganizationDTO toDTO(PersonOrganization entity);

    PersonOrganization fromDTO(PersonOrganizationDTO dto);
}