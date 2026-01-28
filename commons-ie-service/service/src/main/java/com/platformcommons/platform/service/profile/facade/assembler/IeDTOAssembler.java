package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.Ie;
import com.platformcommons.platform.service.profile.dto.IeDTO;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring",uses = {
        PersonDTOAssembler.class,
        DeliveryModeDTOAssembler.class,
        IeSubTypeDTOAssembler.class
    })
public interface IeDTOAssembler {

    IeDTO toDTO(Ie entity);

    Ie fromDTO(IeDTO dto);

    List<Ie> fromDTOs(List<IeDTO> ieList);

    List<IeDTO> toDTOs(List<Ie> savedIEs);
}