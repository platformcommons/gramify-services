package com.platformcommons.platform.service.iam.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.platformcommons.platform.service.dto.base.BaseTransactionalDTO;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
@Validated
public class MarketPartnerDTO extends BaseTransactionalDTO implements Serializable {
    private Long id;
    private Long tenantId;
    private String tenantLogin;
    private Date joinedOn;
    private String status;
    private Long marketConfigId;
    private String solutionSubscriptionCode;
    private Date dateOfStatusChange;
    private Map<String,Boolean> stepStatus;

    @Builder
    public MarketPartnerDTO(String uuid, Long appCreatedAt, Long appLastModifiedAt, Boolean isActive, Long id, Long tenantId,
                            Date joinedOn, String status, Long marketConfigId, String solutionSubscriptionCode, Date dateOfStatusChange,
                            Map<String,Boolean> stepStatus, String tenantLogin) {
        super(uuid, appCreatedAt, appLastModifiedAt, isActive);
        this.id = id;
        this.tenantId = tenantId;
        this.joinedOn = joinedOn;
        this.status = status;
        this.marketConfigId = marketConfigId;
        this.solutionSubscriptionCode = solutionSubscriptionCode;
        this.dateOfStatusChange = dateOfStatusChange;
        this.stepStatus = stepStatus;
        this.tenantLogin = tenantLogin;
    }
}
