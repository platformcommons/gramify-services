package com.platformcommons.platform.service.profile.facade.assembler;

import com.platformcommons.platform.service.profile.domain.CostSpecificationMaster;
import com.platformcommons.platform.service.profile.domain.PersonEducation;
import com.platformcommons.platform.service.profile.dto.CostSpecificationMasterDTO;
import com.platformcommons.platform.service.profile.dto.PersonEducationDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring",uses = {
})
public interface CostSpecificationMasterDTOAssembler {
    CostSpecificationMasterDTO toDTO(CostSpecificationMaster entity);

    CostSpecificationMaster fromDTO(CostSpecificationMasterDTO dto);
}
