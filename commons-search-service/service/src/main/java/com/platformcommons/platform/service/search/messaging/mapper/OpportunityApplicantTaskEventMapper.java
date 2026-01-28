package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.changemaker.dto.CustomOpportunityApplicantTaskDTO;
import com.platformcommons.platform.service.search.domain.OpportunityApplicantTask;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantTaskDTO;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class OpportunityApplicantTaskEventMapper {

    public OpportunityApplicantTaskDTO fromEventDTO(CustomOpportunityApplicantTaskDTO customOpportunityApplicantTaskDTO) {
        if(customOpportunityApplicantTaskDTO == null) {
            return null;
        }

        return OpportunityApplicantTaskDTO.builder()
                .opportunityTaskId(customOpportunityApplicantTaskDTO.getOpportunityTaskId())
                .opportunityApplicantTaskStatus(customOpportunityApplicantTaskDTO.getOpportunityApplicantTaskStatus())
                .opportunityApplicantId(customOpportunityApplicantTaskDTO.getOpportunityApplicantId())
                .selectionStepId(customOpportunityApplicantTaskDTO.getSelectionStepId())
                .build();
    }

    public Set<OpportunityApplicantTaskDTO> fromEventDTOSet(Set<CustomOpportunityApplicantTaskDTO> customOpportunityApplicantTaskDTOSet) {
        if(customOpportunityApplicantTaskDTOSet == null ||  customOpportunityApplicantTaskDTOSet.isEmpty()) {
            return null;
        }

        Set<OpportunityApplicantTaskDTO> opportunityApplicantTasks = new HashSet<>();
        for (CustomOpportunityApplicantTaskDTO customOpportunityApplicantTaskDTO : customOpportunityApplicantTaskDTOSet) {
            OpportunityApplicantTaskDTO opportunityApplicantTaskDTO = fromEventDTO(customOpportunityApplicantTaskDTO);
            if (customOpportunityApplicantTaskDTO != null) {
                opportunityApplicantTasks.add(opportunityApplicantTaskDTO);
            }
        }
        return opportunityApplicantTasks;
    }

    public OpportunityApplicantTask fromCustomEventDTO(CustomOpportunityApplicantTaskDTO customOpportunityApplicantTaskDTO) {
        if(customOpportunityApplicantTaskDTO == null) {
            return null;
        }

        return OpportunityApplicantTask.builder()
                .opportunityTaskId(customOpportunityApplicantTaskDTO.getOpportunityTaskId())
                .opportunityApplicantTaskStatus(customOpportunityApplicantTaskDTO.getOpportunityApplicantTaskStatus())
                .opportunityApplicantId(customOpportunityApplicantTaskDTO.getOpportunityApplicantId())
                .selectionStepId(customOpportunityApplicantTaskDTO.getSelectionStepId())
                .build();
    }

    public Set<OpportunityApplicantTask> fromCustomEventDTOSet(Set<CustomOpportunityApplicantTaskDTO> customOpportunityApplicantTaskDTOSet) {
        if(customOpportunityApplicantTaskDTOSet == null ||  customOpportunityApplicantTaskDTOSet.isEmpty()) {
            return null;
        }

        Set<OpportunityApplicantTask> opportunityApplicantTaskSet = new HashSet<>();
        for (CustomOpportunityApplicantTaskDTO customOpportunityApplicantTaskDTO : customOpportunityApplicantTaskDTOSet) {
            OpportunityApplicantTask opportunityApplicantTask = fromCustomEventDTO(customOpportunityApplicantTaskDTO);
            if (customOpportunityApplicantTaskDTO != null) {
                opportunityApplicantTaskSet.add(opportunityApplicantTask);
            }
        }
        return opportunityApplicantTaskSet;
    }

}