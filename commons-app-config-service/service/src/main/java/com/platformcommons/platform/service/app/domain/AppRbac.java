package com.platformcommons.platform.service.app.domain;

import java.util.Objects;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Getter
@Setter
@Table(name = "app_rbac")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AppRbac extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<AppRbac> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "app_code")
    @NotNull(message = "appCode must not be null")
    private String appCode;

    @Column(name = "is_default")
    private Boolean isDefault;

    @Column(name = "role")
    private String role;

    @Column(name = "priority")
    private Integer priority;

    @Column(name = "context")
    private String context;

    @Column(name = "market_context")
    private String marketContext;

    @Column(name = "is_market_default")
    private Boolean isMarketDefault;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "default_landing_route")
    private String defaultLandingRoute;

    @Transient
    private boolean isNew;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appRbac")
    @BatchSize(size = 20)
    private Set<AppMenu> appMenuList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appRbac")
    @BatchSize(size = 20)
    private Set<AppOperation> appOperationList;

    @Builder
    public AppRbac(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                   String appCode, Boolean isDefault, String role,Integer priority, boolean isNew, Set<AppMenu> appMenuList,
                   Set<AppOperation> appOperationList,String context,String entityType,
                   String defaultLandingRoute, String marketContext, Boolean isMarketDefault) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.appCode = appCode;
        this.isDefault = isDefault;
        this.role = role;
        this.priority = priority;
        this.isNew = isNew;
        this.appMenuList = appMenuList;
        this.appOperationList = appOperationList;
        this.context = context;
        this.entityType = entityType;
        this.marketContext = marketContext;
        this.isMarketDefault = isMarketDefault;
        this.defaultLandingRoute = defaultLandingRoute;
        if(this.appMenuList!=null){
            this.appMenuList.forEach(it-> it.setAppRbac(this));
        }
        if(this.appOperationList!=null){
            this.appOperationList.forEach(it->it.setAppRbac(this));
        }
    }

    public void init() {
        this.id = 0L;
        if(this.appOperationList!=null){
            appOperationList.forEach(it->it.init(this,null));
            this.appOperationList.forEach(appOperation -> {
                if(appOperation.getRead().equals(Boolean.FALSE) && appOperation.getCreate().equals(Boolean.FALSE) &&
                appOperation.getUpdate().equals(Boolean.FALSE) && appOperation.getDelete().equals(Boolean.FALSE)){
                    this.appMenuList.stream().filter(it->it.getMenuCode()
                            .equals(appOperation.getLinkedMenuCode())).forEach(it->it.setIsEnabled(Boolean.FALSE));

                }
            });
        }
        if(appMenuList != null) {
            appMenuList.forEach(it->it.init(this,null,null));
        }
        if(isDefault == null) {
            isDefault = Boolean.FALSE;
        }
        if(isMarketDefault == null) {
            isMarketDefault = Boolean.FALSE;
        }
    }

    public void patch(AppRbac toBeUpdated) {
        PlatformUtil.validateLoginTenantAndAdmin(this.getTenantId());
        if(toBeUpdated.getAppCode() != null) {
            this.appCode = toBeUpdated.getAppCode();
        }
        if(toBeUpdated.getIsDefault() != null) {
            this.isDefault = toBeUpdated.getIsDefault();
        }
        if(toBeUpdated.getRole() != null) {
            this.role = toBeUpdated.getRole();
        }
        if(toBeUpdated.getPriority() != null) {
            this.priority = toBeUpdated.getPriority();
        }
        if(toBeUpdated.getContext() != null) {
            this.context = toBeUpdated.getContext();
        }
        if(toBeUpdated.getEntityType() != null) {
            this.entityType = toBeUpdated.getEntityType();
        }
        if(toBeUpdated.getDefaultLandingRoute() != null) {
            this.defaultLandingRoute = toBeUpdated.getDefaultLandingRoute();
        }
        if (toBeUpdated.getMarketContext() != null) {
            this.marketContext = toBeUpdated.getMarketContext();
        }
        if (toBeUpdated.getIsMarketDefault() != null) {
            this.isMarketDefault = toBeUpdated.getIsMarketDefault();
        }
        if(toBeUpdated.getAppMenuList() != null ) {
            this.getAppMenuList().forEach(appMenu -> {
                toBeUpdated.getAppMenuList().forEach(toBeUpdatedAppMenu -> {
                    if(Objects.equals(appMenu.getId(),toBeUpdatedAppMenu.getId())) {
                        appMenu.patch(toBeUpdatedAppMenu);
                    }
                });
            });
            toBeUpdated.getAppMenuList().forEach(toBeSaved -> {
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    toBeSaved.init(this,null,null);
                    this.getAppMenuList().add(toBeSaved);
                }
            });
        }
        if(toBeUpdated.getAppOperationList() != null ) {
            this.getAppOperationList().forEach(appOperation -> {
                toBeUpdated.getAppOperationList().forEach(toBeUpdatedappOperation -> {
                    if(Objects.equals(appOperation.getId(),toBeUpdatedappOperation.getId())) {
                        appOperation.patch(toBeUpdatedappOperation);
                    }
                });
            });
            toBeUpdated.getAppOperationList().forEach(toBeSaved -> {
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    toBeSaved.init(this,null);
                    this.getAppOperationList().add(toBeSaved);
                }
            });
        }
    }


}