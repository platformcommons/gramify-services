package com.platformcommons.platform.service.iam.dto;

import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.util.Set;
import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class MarketConfigDTO extends BaseTransactionalDTO implements Serializable {
    private Long id;
    private Long governorTenant;
    private String marketUUID;
    private String defaultOpportunityStatus;
    @Valid
    private Set<MarketPartnerDTO> marketPartners;
    private String defaultMarketPartnerAssociationStatus;
    private String signupValidationMethod;
    private String solutionSubscriptionCode;
    private Boolean marketCreationAllowed;
    private Boolean displayTenantsFromAllMarkets;
    private Long sourceMarketConfigId;
    private String appContext;
    private String marketAppBaseUrl;
    private String governorTenantLogin;

    @Builder
    public MarketConfigDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Boolean isActive, Long id, Long governorTenant, String marketUUID, String defaultOpportunityStatus, Set<MarketPartnerDTO> marketPartners, String defaultMarketPartnerAssociationStatus, String signupValidationMethod, String solutionSubscriptionCode, Boolean marketCreationAllowed, Boolean displayTenantsFromAllMarkets, Long sourceMarketConfigId, String appContext, String marketAppBaseUrl, String governorTenantLogin) {
        super(uuid, appCreatedAt, appLastModifiedAt, isActive);
        this.id = id;
        this.governorTenant = governorTenant;
        this.marketUUID = marketUUID;
        this.defaultOpportunityStatus = defaultOpportunityStatus;
        this.marketPartners = marketPartners;
        this.defaultMarketPartnerAssociationStatus = defaultMarketPartnerAssociationStatus;
        this.signupValidationMethod = signupValidationMethod;
        this.solutionSubscriptionCode = solutionSubscriptionCode;
        this.marketCreationAllowed = marketCreationAllowed;
        this.displayTenantsFromAllMarkets = displayTenantsFromAllMarkets;
        this.sourceMarketConfigId = sourceMarketConfigId;
        this.appContext = appContext;
        this.marketAppBaseUrl = marketAppBaseUrl;
        this.governorTenantLogin = governorTenantLogin;
    }
}