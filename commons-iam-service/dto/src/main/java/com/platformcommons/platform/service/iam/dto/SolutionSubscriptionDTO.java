package com.platformcommons.platform.service.iam.dto;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
public class SolutionSubscriptionDTO {

    private String appContext;

    @NotNull(message = "solutionSubscriptionCode can not be null")
    private String solutionSubscriptionCode;

    private String subscriptionPriceModelCode;

    private String domainURL;

    @NotNull(message = "MarketUUID can not be null")
    private String defaultMarketUUID;


    @Builder
    public SolutionSubscriptionDTO(String appContext, String solutionSubscriptionCode, String subscriptionPriceModelCode,
                                   String domainURL, String defaultMarketUUID) {
        this.appContext = appContext;
        this.solutionSubscriptionCode = solutionSubscriptionCode;
        this.subscriptionPriceModelCode = subscriptionPriceModelCode;
        this.domainURL = domainURL;
        this.defaultMarketUUID = defaultMarketUUID;
    }
}
