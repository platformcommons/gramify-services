package com.platformcommons.platform.service.search.facade;

import com.platformcommons.platform.service.search.dto.OpportunityApplicantTaskDTO;

public interface OpportunityApplicantTaskFacade {

    void addOrUpdateOpportunityApplicantTaskInApplicant(OpportunityApplicantTaskDTO opportunityApplicantTaskDTO);

    void deleteOpportunityApplicantTaskInApplicant(OpportunityApplicantTaskDTO opportunityApplicantTaskDTO);


}
