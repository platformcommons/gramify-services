package com.platformcommons.platform.service.search.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class OpportunityStakeHolderCustomDTO {
    private Long userId;
    private Set<Long> opportunityIds;

    @Builder
    public OpportunityStakeHolderCustomDTO(Long userId, Set<Long> opportunityIds) {
        this.userId = userId;
        this.opportunityIds = opportunityIds;
    }
}
