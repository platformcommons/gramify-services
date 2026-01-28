package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import com.platformcommons.platform.service.iam.application.utility.CryptUtil;
import com.platformcommons.platform.service.iam.application.utility.PlatformUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "tenant_meta_config")
public class TenantMetaConfig extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<TenantMetaConfig> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "tenant_login")
    private String tenantLogin;

    @Column(name = "user_login")
    @NotNull(message ="userLogin must not be null")
    private String userLogin;

    @Column(name = "password",nullable = false)
    @Convert(converter = CryptUtil.class)
    @NotNull(message ="password must not be null")
    private String password;

    @Column(name = "actor_context")
    private String actorContext;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tenantMetaConfig")
    private Set<TenantMetaAdditionalProperty> tenantMetaAdditionalPropertySet;

    public TenantMetaConfig() {
    }


    public void init() {
        this.id = 0L;
        if(this.tenantMetaAdditionalPropertySet != null) {
            this.tenantMetaAdditionalPropertySet.forEach(it->it.init(this));
        }
    }

    @Builder
    public TenantMetaConfig(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                            Long id,  String tenantLogin,String password,
                            Map<String, String> config, String actorContext, String userLogin,
                            Set<TenantMetaAdditionalProperty> tenantMetaAdditionalPropertySet) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.tenantLogin = tenantLogin;
        this.actorContext = actorContext;
        this.userLogin = userLogin;
        this.password = password;
        this.tenantMetaAdditionalPropertySet = tenantMetaAdditionalPropertySet;
    }


    public void update(TenantMetaConfig toBeUpdated) {
        this.tenantLogin = toBeUpdated.getTenantLogin();
        this.actorContext = toBeUpdated.getActorContext();
        this.userLogin = toBeUpdated.getUserLogin();
        this.tenantMetaAdditionalPropertySet = toBeUpdated.getTenantMetaAdditionalPropertySet();
    }


    public void patch(TenantMetaConfig toBeUpdated) {
        PlatformUtil.validateLoginTenantAndAdmin(this.getTenantLogin());
        if(toBeUpdated.getTenantLogin() != null) {
            this.tenantLogin = toBeUpdated.getTenantLogin();
        }
        if(toBeUpdated.getUserLogin() != null) {
            this.userLogin = toBeUpdated.getUserLogin();
        }
        if(toBeUpdated.getPassword() != null) {
            this.password = toBeUpdated.getPassword();
        }
        if(toBeUpdated.getActorContext() != null) {
            this.actorContext = toBeUpdated.getActorContext();
        }
        if (toBeUpdated.getTenantMetaAdditionalPropertySet() != null ) {
            this.getTenantMetaAdditionalPropertySet().forEach(metaProperty -> {
                toBeUpdated.getTenantMetaAdditionalPropertySet().forEach(toBeUpdatedMetaProperty -> {
                    if(Objects.equals(metaProperty.getId(),toBeUpdatedMetaProperty.getId())) {
                        metaProperty.patch(toBeUpdatedMetaProperty);
                    }
                });
            });
            toBeUpdated.getTenantMetaAdditionalPropertySet().forEach(toBeSaved -> {
                if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                    toBeSaved.init(this);
                    this.getTenantMetaAdditionalPropertySet().add(toBeSaved);
                }
            });
        }
    }

    public void patchTenantMetaConfigAdditionalProperty(Set<TenantMetaAdditionalProperty> tenantMetaAdditionalPropertySet) {
        this.getTenantMetaAdditionalPropertySet().forEach(metaProperty -> {
            tenantMetaAdditionalPropertySet.forEach(toBeUpdatedMetaProperty -> {
                if(Objects.equals(metaProperty.getId(),toBeUpdatedMetaProperty.getId())) {
                    metaProperty.patch(toBeUpdatedMetaProperty);
                }
            });
        });
        tenantMetaAdditionalPropertySet.forEach(toBeSaved -> {
            if(toBeSaved.getId() == null || toBeSaved.getId().equals(0L)) {
                toBeSaved.init(this);
                this.getTenantMetaAdditionalPropertySet().add(toBeSaved);
            }
        });
    }
}
