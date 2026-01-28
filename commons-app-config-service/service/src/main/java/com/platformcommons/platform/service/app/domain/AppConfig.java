package com.platformcommons.platform.service.app.domain;

import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.app.application.utility.PlatformUtil;
import com.platformcommons.platform.service.entity.base.BaseEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Calendar;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "app_config")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AppConfig extends BaseEntity implements DomainEntity<AppConfig> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "appVersion must not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "app_version", nullable = false, updatable = false)
    private AppVersion appVersion;

    @Column(name = "scope_code")
    private String scopeCode;

    @Column(name = "scope_value")
    private String scopeValue;

    @NotNull(message = "configKey must not be null")
    @Column(name = "config_key")
    private String configKey;

    @NotNull(message = "configValue must not be null")
    @Column(name = "config_value")
    private String configValue;

    @NotNull(message = "configType must not be null")
    @Column(name = "config_type")
    private String configType;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @BatchSize(size = 20)
    private Set<MLText> configValueML;

    @Column(name = "notes")
    private String notes;


    @Builder
    public AppConfig(Long id, AppVersion appVersion,
                     String scopeCode, String scopeValue, String configKey,
                     String configValue, Set<MLText> configValueML, String notes, String configType) {
        this.id = id;
        this.appVersion = appVersion;
        this.scopeCode = scopeCode;
        this.scopeValue = scopeValue;
        this.configKey = configKey;
        this.configValue = configValue;
        this.configValueML = configValueML;
        this.notes = notes;
        this.configType = configType;
    }

    public void  init(){
        this.id=0L;
    }

    public void update(AppConfig appConfig){
        PlatformUtil.validateLoginTenant(this.getCreatedByTenant());
        this.setConfigValue(appConfig.getConfigValue());
        if(appConfig.getConfigValueML()!=null && !appConfig.getConfigValueML().isEmpty()) {
            this.getConfigValueML().clear();
            this.getConfigValueML().addAll(appConfig.getConfigValueML());
        }
        this.setScopeValue(appConfig.getScopeValue());
        this.setScopeCode(appConfig.getScopeCode());
    }

    public void patchUpdate(AppConfig toBeUpdated){
        PlatformUtil.validateLoginTenantAndAdmin(this.getCreatedByTenant());
        if(toBeUpdated.getConfigValue() != null) {
            this.configValue = toBeUpdated.getConfigValue();
        }
        if(toBeUpdated.getConfigValueML()!=null && !toBeUpdated.getConfigValueML().isEmpty()) {
            this.getConfigValueML().clear();
            this.getConfigValueML().addAll(toBeUpdated.getConfigValueML());
        }
        if(toBeUpdated.getScopeCode() != null) {
            this.scopeCode = toBeUpdated.getScopeCode();
        }
        if(toBeUpdated.getScopeValue() != null) {
            this.scopeValue = toBeUpdated.getScopeValue();
        }
    }
}