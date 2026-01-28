package com.platformcommons.platform.service.app.facade.assembler;

import com.platformcommons.platform.service.app.domain.Instance;
import com.platformcommons.platform.service.app.dto.InstanceDTO;
import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {MLTextDTOAssembler.class})
public interface InstanceDTOAssembler {

    InstanceDTO toDTO(Instance instance);

    Instance fromDTO(InstanceDTO instanceDTO);

}
