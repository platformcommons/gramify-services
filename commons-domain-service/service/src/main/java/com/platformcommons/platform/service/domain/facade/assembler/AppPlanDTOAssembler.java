package com.platformcommons.platform.service.domain.facade.assembler;

import com.platformcommons.platform.service.domain.domain.AppPlan;
import com.platformcommons.platform.service.domain.dto.AppPlanDTO;
import com.platformcommons.platform.service.facade.assembler.CurrencyValueDTOAssembler;
import com.platformcommons.platform.service.facade.assembler.UoMDTOAssembler;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring",uses = {
        FeatureDTOAssembler.class,
        AppDTOAssembler.class,
        CurrencyValueDTOAssembler.class,
        UoMDTOAssembler.class
})
public interface AppPlanDTOAssembler {

    AppPlanDTO toDTO(AppPlan entity);

    AppPlan fromDTO(AppPlanDTO dto);

    List<AppPlan> fromDTOs(List<AppPlanDTO> list);
}
