package com.platformcommons.platform.service.domain.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Table(name = "feature",indexes = {
        @Index(columnList = "type"),
        @Index(columnList = "code"),
        @Index(columnList = "on_app_version"),
        @Index(columnList = "value")
})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class Feature extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<Feature> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "app_id", nullable = true, updatable = false)
    private App appId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "use_case_id", nullable = true, updatable = false)
    private UseCase useCaseId;

    @Column(name = "name")
    private String name;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @Column(name = "type")  //
    private String type;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private String value;

    @Column(name = "on_app_version")
    private String onAppVersion;

    @Transient
    private boolean isNew;

    @Builder
    public Feature(String uuid,  Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, App appId, UseCase useCaseId, String name, String description, String type, String code, String value, String onAppVersion) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.appId = appId;
        this.useCaseId = useCaseId;
        this.name = name;
        this.description = description;
        this.type = type;
        this.code = code;
        this.value = value;
        this.onAppVersion = onAppVersion;
    }

    public void init() {
    }

    public void update(Feature toBeUpdated) {
    }
}