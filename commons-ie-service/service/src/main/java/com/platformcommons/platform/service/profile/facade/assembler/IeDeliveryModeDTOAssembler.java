package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.person.dto.IeDeliveryModeDTO;
import com.platformcommons.platform.service.profile.domain.DeliveryMode;
import com.platformcommons.platform.service.profile.dto.DeliveryModeDTO;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(componentModel = "spring")
public interface IeDeliveryModeDTOAssembler {

    IeDeliveryModeDTO toDTO(DeliveryMode entity);

    DeliveryMode fromDTO(IeDeliveryModeDTO dto);

    Set<IeDeliveryModeDTO> toDTOs(Set<DeliveryMode> deliveryModes);
}