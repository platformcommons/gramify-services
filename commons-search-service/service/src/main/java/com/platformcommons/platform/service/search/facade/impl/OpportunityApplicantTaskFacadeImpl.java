package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.search.application.service.OpportunityApplicantService;
import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import com.platformcommons.platform.service.search.domain.OpportunityApplicantTask;
import com.platformcommons.platform.service.search.dto.OpportunityApplicantTaskDTO;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantTaskFacade;
import com.platformcommons.platform.service.search.facade.assembler.OpportunityApplicantTaskAssembler;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@Transactional
@Slf4j
public class OpportunityApplicantTaskFacadeImpl implements  OpportunityApplicantTaskFacade {

    @Autowired
    private OpportunityApplicantService opportunityApplicantService;

    @Autowired
    private OpportunityApplicantTaskAssembler assembler;


    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void addOrUpdateOpportunityApplicantTaskInApplicant(OpportunityApplicantTaskDTO opportunityApplicantTaskDTO) {
        if (opportunityApplicantTaskDTO == null || opportunityApplicantTaskDTO.getOpportunityApplicantId() == null ||
                opportunityApplicantTaskDTO.getOpportunityTaskId() == null) {
            return;
        }

        Long applicantId = opportunityApplicantTaskDTO.getOpportunityApplicantId();
        Long taskId = opportunityApplicantTaskDTO.getOpportunityTaskId();

        Optional<OpportunityApplicant> optionalOpportunityApplicant = opportunityApplicantService.getOptionalById(applicantId);
        if (optionalOpportunityApplicant.isPresent()) {
            OpportunityApplicant opportunityApplicant = optionalOpportunityApplicant.get();
            Set<OpportunityApplicantTask> opportunityApplicantTasks =opportunityApplicant.getOpportunityApplicantTasks();

            if (opportunityApplicantTasks == null) {
                opportunityApplicantTasks = new HashSet<>();
            }

            Optional<OpportunityApplicantTask> optionalOpportunityApplicantTask = opportunityApplicantTasks.stream()
                    .filter(t -> Objects.equals(t.getOpportunityTaskId(), taskId))
                    .findFirst();

            if (optionalOpportunityApplicantTask.isPresent()) {
                OpportunityApplicantTask fetchedOpportunityApplicantTask = optionalOpportunityApplicantTask.get();
                fetchedOpportunityApplicantTask.patchUpdate(assembler.fromDTO(opportunityApplicantTaskDTO));
            } else {
                opportunityApplicantTasks.add(assembler.fromDTO(opportunityApplicantTaskDTO));
                log.info("Added new task {} for applicant {}", taskId, applicantId);
            }

            opportunityApplicant.setOpportunityApplicantTasks(opportunityApplicantTasks);
            opportunityApplicantService.save(opportunityApplicant);
        }
    }


    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void deleteOpportunityApplicantTaskInApplicant(OpportunityApplicantTaskDTO opportunityApplicantTaskDTO) {

        if (opportunityApplicantTaskDTO == null || opportunityApplicantTaskDTO.getOpportunityApplicantId() == null ||
                opportunityApplicantTaskDTO.getOpportunityTaskId() == null) {
            return;
        }

        Long applicantId = opportunityApplicantTaskDTO.getOpportunityApplicantId();
        Long taskId = opportunityApplicantTaskDTO.getOpportunityTaskId();

        Optional<OpportunityApplicant> optionalOpportunityApplicant = opportunityApplicantService.getOptionalById(applicantId);
        if (optionalOpportunityApplicant.isPresent()) {
            OpportunityApplicant applicant = optionalOpportunityApplicant.get();
            Set<OpportunityApplicantTask> opportunityApplicantTasks = applicant.getOpportunityApplicantTasks();

            if (opportunityApplicantTasks != null && !opportunityApplicantTasks.isEmpty()) {
                opportunityApplicantTasks.removeIf(t ->
                        Objects.equals(t.getOpportunityTaskId(), taskId));

                applicant.setOpportunityApplicantTasks(opportunityApplicantTasks);
                opportunityApplicantService.save(applicant);
            }
        }
    }
}
