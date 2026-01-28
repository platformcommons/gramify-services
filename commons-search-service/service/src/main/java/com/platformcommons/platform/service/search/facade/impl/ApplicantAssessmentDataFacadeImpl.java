package com.platformcommons.platform.service.search.facade.impl;

import com.platformcommons.platform.service.changemaker.dto.ApplicantAssessmentDataDTO;
import com.platformcommons.platform.service.search.application.service.OpportunityApplicantService;
import com.platformcommons.platform.service.search.domain.ApplicantAssessmentResponse;
import com.platformcommons.platform.service.search.domain.OpportunityApplicant;
import com.platformcommons.platform.service.search.facade.ApplicantAssessmentDataFacade;
import com.platformcommons.platform.service.search.facade.OpportunityApplicantFacade;
import com.platformcommons.platform.service.search.messaging.mapper.ApplicantAssessmentResponseEventMapper;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.ElasticsearchStatusException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Component
@Slf4j
public class ApplicantAssessmentDataFacadeImpl implements ApplicantAssessmentDataFacade {

    @Autowired
    private OpportunityApplicantService opportunityApplicantService;

    @Autowired
    private ApplicantAssessmentResponseEventMapper assessmentResponseEventMapper;

    @Override
    @Retryable(value = ElasticsearchStatusException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 100))
    public void addOrUpdateApplicantAssessmentResponse(ApplicantAssessmentDataDTO applicantAssessmentDataDTO) {
        Long applicantId = applicantAssessmentDataDTO.getApplicantId();
        Set<ApplicantAssessmentResponse> applicantAssessmentResponseSet =
                assessmentResponseEventMapper.fromEventDTOSet(applicantAssessmentDataDTO.getApplicantAssessmentResponseSet());

        Optional<OpportunityApplicant> optionalOpportunityApplicant = opportunityApplicantService.getOptionalById(applicantId);

        if (optionalOpportunityApplicant.isPresent()) {
            OpportunityApplicant opportunityApplicant = optionalOpportunityApplicant.get();
            Set<ApplicantAssessmentResponse> fetchedAssessmentResponses = opportunityApplicant.getApplicantAssessmentResponses();

            if (fetchedAssessmentResponses == null) {
                fetchedAssessmentResponses = new HashSet<>();
            }

            for (ApplicantAssessmentResponse applicantAssessmentResponse : applicantAssessmentResponseSet) {
                Long assessmentId = applicantAssessmentResponse.getAssessmentId();

                Optional<ApplicantAssessmentResponse> optionalApplicantAssessmentResponse = fetchedAssessmentResponses.stream()
                        .filter(assessment -> Objects.equals(assessment.getAssessmentId(), assessmentId))
                        .findFirst();

                if (optionalApplicantAssessmentResponse.isPresent()) {
                    ApplicantAssessmentResponse assessmentResponse = optionalApplicantAssessmentResponse.get();
                    fetchedAssessmentResponses.remove(assessmentResponse);
                    fetchedAssessmentResponses.add(applicantAssessmentResponse);
                } else {
                    fetchedAssessmentResponses.add(applicantAssessmentResponse);
                    log.info("added the new assessment {} for applicantId {}", assessmentId, applicantId);
                }
            }
            opportunityApplicant.setApplicantAssessmentResponses(fetchedAssessmentResponses);
            opportunityApplicantService.save(opportunityApplicant);
        }
    }
}
