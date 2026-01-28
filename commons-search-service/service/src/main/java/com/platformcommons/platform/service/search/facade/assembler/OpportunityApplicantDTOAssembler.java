package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantDTO;
import org.mapstruct.Mapper;

public interface OpportunityApplicantDTOAssembler {

    OpportunityApplicantDTO toDTO(OpportunityApplicant entity);

    OpportunityApplicantDTO toDTOForChangeMaker(OpportunityApplicant entity);

}
