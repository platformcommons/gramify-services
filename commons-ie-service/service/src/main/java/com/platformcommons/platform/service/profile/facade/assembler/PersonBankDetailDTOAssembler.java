package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.PersonBankDetail;
import com.platformcommons.platform.service.profile.dto.PersonBankDetailDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface PersonBankDetailDTOAssembler {

    PersonBankDetailDTO toDTO(PersonBankDetail entity);

    PersonBankDetail fromDTO(PersonBankDetailDTO dto);
}