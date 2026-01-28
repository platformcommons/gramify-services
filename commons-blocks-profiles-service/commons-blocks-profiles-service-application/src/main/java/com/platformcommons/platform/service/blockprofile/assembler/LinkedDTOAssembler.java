package com.platformcommons.platform.service.blockprofile.assembler;


import com.platformcommons.platform.service.blockprofile.domain.LinkedId;
import com.platformcommons.platform.service.blockprofile.dto.LinkedCodeDTO;
import com.platformcommons.platform.service.blockprofile.dto.LinkedIdDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LinkedDTOAssembler {

    LinkedId toDTO(LinkedId linkedId);


    LinkedId fromDTO(LinkedIdDTO linkedIdDTO);


    LinkedCodeDTO toDTO(LinkedCodeDTO linkedCodeDTO);


    LinkedCodeDTO fromDTO(LinkedCodeDTO linkedCodeDTO);
}
