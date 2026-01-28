package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.Opportunity;
import com.platformcommons.platform.service.search.dto.OpportunityDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OpportunityDTOAssembler {

    OpportunityDTO toDTO(Opportunity opportunity);

    Opportunity fromDTO(OpportunityDTO opportunityDTO);
}
