package com.platformcommons.platform.service.app.domain;

import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_rbac_meta")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class AppRbacMeta extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<AppRbacMeta> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "app_code")
    @NotNull(message = "appCode must not be null")
    private String appCode;

    @Column(name = "entity_type")
    private String entityType;

    @Column(name = "role_type")
    private String roleType;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appRbacMeta")
    @BatchSize(size = 20)
    private Set<AppMenu> appMenuList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "appRbacMeta")
    @BatchSize(size = 20)
    private Set<AppOperation> appOperationList;

    @Builder
    public AppRbacMeta(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                       String appCode, Set<AppMenu> appMenuList,String entityType, String roleType,
                       Set<AppOperation> appOperationList) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.appCode = appCode;
        this.appMenuList = appMenuList;
        this.appOperationList = appOperationList;
        this.entityType = entityType;
        this.roleType = roleType;
    }

    public void init() {
        this.id = 0L;
        if(appMenuList != null) {
            appMenuList.forEach(it->it.init(null,this,null));
        }
        if(this.appOperationList!=null){
            appOperationList.forEach(it->it.init(null,this));
        }
    }

    public void patch(AppRbacMeta toBeUpdated) {
        PlatformUtil.validateLoginTenantAndAdmin(this.getTenantId());
        if(toBeUpdated.getAppCode() != null) {
            this.appCode = toBeUpdated.getAppCode();
        }
        if(toBeUpdated.getEntityType() != null) {
            this.entityType = toBeUpdated.getEntityType();
        }
        if(toBeUpdated.getRoleType() != null) {
            this.roleType = toBeUpdated.getRoleType();
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
                    toBeSaved.init(null,this,null);
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
                    toBeSaved.init(null,this);
                    this.getAppOperationList().add(toBeSaved);
                }
            });
        }
    }
}