package com.platformcommons.platform.service.domain.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.entity.common.CurrencyValue;
import com.platformcommons.platform.service.entity.common.UoMValue;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_plan")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AppPlan extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<AppPlan> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "name must not be null")
    @Column(name = "name")
    private String name;


    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", nullable = true, updatable = false)
    private App app;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "price", referencedColumnName = "id")
    private CurrencyValue price;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "AppPlanHasFeature", joinColumns = @JoinColumn(name = "app_plan_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "feature_id", referencedColumnName = "id"))
    private Set<Feature> featureList;

    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_billed", referencedColumnName = "id")
    private UoMValue userBilled;

    @Builder
    public AppPlan(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                   String name, App app, CurrencyValue price, Set<Feature> featureList, UoMValue userBilled,String description) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.name = name;
        this.app = app;
        this.price = price;
        this.featureList = featureList;
        this.userBilled = userBilled;
        this.description = description;
    }

    public void init() {
    }

    public void update(AppPlan toBeUpdated) {
        if(toBeUpdated.getName()!=null){
            this.name = toBeUpdated.getName();
        }
        if(toBeUpdated.getPrice()!=null){
            this.price = toBeUpdated.getPrice();
        }
        if(toBeUpdated.getUserBilled()!=null){
            this.userBilled = toBeUpdated.getUserBilled();
        }
        if(toBeUpdated.getDescription()!=null){
            this.description = toBeUpdated.getDescription();
        }
    }
}
