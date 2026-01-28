
package com.platformcommons.platform.service.iam.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class LeadExistResponse {
    private boolean isTenantPresent;
    private List<TenantVO> tenants;
    private boolean isLeadPresent;
    private List<LeadDTO> leads;
}
