package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.Address;
import com.platformcommons.platform.service.profile.dto.AddressDTO;


import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
    })
public interface AddressDTOAssembler {

    AddressDTO toDTO(Address entity);

    Address fromDTO(AddressDTO dto);
}