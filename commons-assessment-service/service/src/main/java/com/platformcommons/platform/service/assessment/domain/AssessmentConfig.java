package com.platformcommons.platform.service.assessment.domain;


import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "assessment_config")
public class AssessmentConfig extends BaseTransactionalEntity implements DomainEntity<Assessment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "assessment", nullable = false, updatable = false)
    private Assessment assessment;

    @Column(name = "config_key")
    @NotNull
    private String configKey;

    @Column(name = "config_value")
    private String configValue;

    public AssessmentConfig(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Assessment assessment, String configKey, String configValue) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.assessment = assessment;
        this.configKey = configKey;
        this.configValue = configValue;
    }

    public void init(Assessment assessment) {
        this.id=null;
        this.assessment=assessment;
    }
}
