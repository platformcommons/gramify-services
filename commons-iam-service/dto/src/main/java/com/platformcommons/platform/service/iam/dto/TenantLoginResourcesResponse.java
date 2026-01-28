package com.platformcommons.platform.service.iam.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantLoginResourcesResponse {

    Boolean marketConfigPresent;

    Boolean leadPresent;

    Set<MarketConfigWithTenantsDTO> marketConfigWithTenantsSet;

    Set<LeadDTO> leads;

    Boolean tenantIsPartOfCurrentMarket;
}
