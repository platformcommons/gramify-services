package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonInsurance;
import com.platformcommons.platform.service.profile.dto.PersonInsuranceDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonInsuranceDTOAssembler {

    PersonInsuranceDTO toDTO(PersonInsurance entity);

    PersonInsurance fromDTO(PersonInsuranceDTO dto);
}