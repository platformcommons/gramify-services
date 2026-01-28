package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.DeliveryMode;
import com.platformcommons.platform.service.profile.dto.DeliveryModeDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface DeliveryModeDTOAssembler {

    DeliveryModeDTO toDTO(DeliveryMode entity);

    DeliveryMode fromDTO(DeliveryModeDTO dto);

    Set<DeliveryModeDTO> toDTOs(Set<DeliveryMode> deliveryModes);
}