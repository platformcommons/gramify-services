package com.platformcommons.platform.service.app.domain;

import com.platformcommons.platform.service.entity.base.BaseEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity(name = "instance")
public class Instance extends BaseEntity implements DomainEntity<Instance> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @NotEmpty(message = "name must not be null or empty")
    @Column(nullable = false, length = 100)
    private String name;

    @NotEmpty(message = "code must not be null or empty")
    @Column(unique = true, nullable = false, updatable = false, length = 20)
    private String code;

    @NotEmpty(message = "baseUrl must not be null or empty")
    @Column(name = "base_url", unique = true, nullable = false)
    private String baseUrl;

    @Column(name = "app_mill_url")
    private String appMillUrl;

    @NotEmpty(message = "healthUri must not be null or empty")
    @Column(name = "health_uri", nullable = false)
    private String healthUri;

    @NotNull(message = "status must not be null")
    private String status;

    @Builder
    public Instance(Long id, String name, String code, String baseUrl, String appMillUrl, String healthUri, String status) {
        this.id = null == id || id == 0L ? null : id;
        this.name = name;
        this.code = code;
        this.baseUrl = baseUrl;
        this.appMillUrl = appMillUrl;
        this.healthUri = healthUri;
        this.status = status;
    }

    public void init() {
    }

    public void update(Instance toBeUpdated){
        this.name = toBeUpdated.getName();
        this.code = toBeUpdated.getCode();
        this.baseUrl = toBeUpdated.getBaseUrl();
        this.appMillUrl = toBeUpdated.getAppMillUrl();
        this.healthUri = toBeUpdated.getHealthUri();
        this.status = toBeUpdated.getStatus();
    }


}

