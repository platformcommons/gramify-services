package com.platformcommons.platform.service.search.facade.assembler;

import com.platformcommons.platform.service.search.domain.OpportunityApplicantTask;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantTaskDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface OpportunityApplicantTaskAssembler {

    OpportunityApplicantTaskDTO toDTO(OpportunityApplicantTask opportunityApplicantTask);

    OpportunityApplicantTask fromDTO(OpportunityApplicantTaskDTO opportunityApplicantTaskDTO);

}
