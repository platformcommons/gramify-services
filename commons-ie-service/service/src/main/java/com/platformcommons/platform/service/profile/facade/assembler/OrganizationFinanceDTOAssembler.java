package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.OrganizationFinance;
import com.platformcommons.platform.service.profile.dto.OrganizationFinanceDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface OrganizationFinanceDTOAssembler {

    OrganizationFinanceDTO toDTO(OrganizationFinance entity);

    OrganizationFinance fromDTO(OrganizationFinanceDTO dto);
}