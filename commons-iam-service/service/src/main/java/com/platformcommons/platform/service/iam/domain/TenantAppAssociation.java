package com.platformcommons.platform.service.iam.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor(access =  AccessLevel.PUBLIC)
@Entity
@Table(name = "tenant_app_association")
public class TenantAppAssociation extends AuthBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false, unique = true)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, name = "tenant")
    private Tenant tenant;

    @Column(name = "app_context", nullable = false)
    private String appContext;

    @Column(name = "is_complete")
    private Boolean isComplete;

    @Column(name = "solution_subscription_code")
    private String solutionSubscriptionCode;

    @Column(name = "subscription_price_model_code")
    private String subscriptionPriceModelCode;


    @Column(name = "appDomainURL")
    private String appDomainURL;


    @Column(nullable = true, name = "market_context")
    private String marketContext;

    @Column(name = "referralCode")
    private String referralCode;

    @ElementCollection
    @MapKeyColumn(name="step")
    @Column(name="is_complete")
    @CollectionTable(name="app_association_step_status", joinColumns=@JoinColumn(name="app_association_id"))
    private Map<String, Boolean> stepStatus;

    @Builder
    public TenantAppAssociation(Long id, Tenant tenant, String appContext, Boolean isComplete, String solutionSubscriptionCode,
                                String subscriptionPriceModelCode, String appDomainURL, Map<String, Boolean> stepStatus,
                                String marketContext, String referralCode) {
        this.id = id;
        this.tenant = tenant;
        this.appContext = appContext;
        this.isComplete = isComplete;
        this.solutionSubscriptionCode = solutionSubscriptionCode;
        this.subscriptionPriceModelCode = subscriptionPriceModelCode;
        this.appDomainURL = appDomainURL;
        this.stepStatus = stepStatus;
        this.marketContext = marketContext;
        this.referralCode = referralCode;
    }
}
