package com.platformcommons.platform.service.search.messaging.mapper;

import com.platformcommons.platform.service.changemaker.dto.RuleResultCustomDTO;
import com.platformcommons.platform.service.search.domain.RuleResult;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class RuleResultEventMapper {

    public RuleResult fromEventDTO(RuleResultCustomDTO ruleResultCustomDTO) {
        if(ruleResultCustomDTO == null) {
            return null;
        }

        return RuleResult.builder()
                .ruleCode(ruleResultCustomDTO.getRuleCode())
                .ruleOutcome(ruleResultCustomDTO.getRuleOutcome())
                .opportunityApplicantId(ruleResultCustomDTO.getOpportunityApplicantId())
                .build();
    }

    public Set<RuleResult> fromEventDTOSet(Set<RuleResultCustomDTO> ruleResultCustomDTOSet) {
        if(ruleResultCustomDTOSet == null || ruleResultCustomDTOSet.isEmpty()) {
            return null;
        }

        Set<RuleResult> set = new HashSet<>();
        for (RuleResultCustomDTO dto : ruleResultCustomDTOSet) {
            RuleResult ruleResult = fromEventDTO(dto);
            if (dto != null) {
                set.add(ruleResult);
            }
        }
        return set;
    }

}