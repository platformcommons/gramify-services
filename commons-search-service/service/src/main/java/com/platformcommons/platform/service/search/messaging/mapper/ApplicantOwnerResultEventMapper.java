package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.changemaker.dto.ApplicantOwnerResultCustomDTO;
import com.platformcommons.platform.service.search.domain.ApplicantOwnerResult;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class ApplicantOwnerResultEventMapper {

    public ApplicantOwnerResult fromEventDTO(ApplicantOwnerResultCustomDTO applicantOwnerResultCustomDTO) {
        if(applicantOwnerResultCustomDTO == null) {
            return null;
        }

        return ApplicantOwnerResult.builder()
                .id(applicantOwnerResultCustomDTO.getId())
                .ownerUserId(applicantOwnerResultCustomDTO.getOwnerUserId())
                .stepCode(applicantOwnerResultCustomDTO.getStepCode())
                .stepResultCode(applicantOwnerResultCustomDTO.getStepResultCode())
                .opportunityApplicantId(applicantOwnerResultCustomDTO.getOpportunityApplicantId())
                .build();
    }

    public Set<ApplicantOwnerResult> fromEventDTOSet(Set<ApplicantOwnerResultCustomDTO> applicantOwnerResultCustomDTOS) {
        if(applicantOwnerResultCustomDTOS == null || applicantOwnerResultCustomDTOS.isEmpty()) {
            return null;
        }

        Set<ApplicantOwnerResult> set = new HashSet<>();
        for (ApplicantOwnerResultCustomDTO dto : applicantOwnerResultCustomDTOS) {
            ApplicantOwnerResult applicantOwnerResult = fromEventDTO(dto);
            if (dto != null) {
                set.add(applicantOwnerResult);
            }
        }
        return set;
    }

}