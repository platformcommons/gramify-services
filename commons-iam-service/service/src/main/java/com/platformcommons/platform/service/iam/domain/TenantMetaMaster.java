package com.platformcommons.platform.service.iam.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints =
@UniqueConstraint(columnNames = {"app_context", "meta_code"})
)
public class TenantMetaMaster extends AuthBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false, name = "meta_code")
    private String metaCode;

    @Column(nullable = false, name = "meta_name")
    private String metaName;

    @Column(name = "app_context", nullable = false)
    private String appContext;

    @Column(name = "meta_description")
    private String metaDescription;

    @Column(nullable = false, name = "meta_data_type")
    private String metaDataType;

    @ElementCollection
    private Set<String> metaDefaultValues;

    @ElementCollection
    private Set<String> metaAllowedValue;

    @Builder
    public TenantMetaMaster(
            String metaCode, String metaName,
            String metaDescription, String metaDataType, Set<String> metaDefaultValues,
            Set<String> metaAllowedValue, String appContext) {
        this.metaCode = metaCode;
        this.metaName = metaName;
        this.metaDescription = metaDescription;
        this.metaDataType = metaDataType;
        this.metaDefaultValues = metaDefaultValues;
        this.metaAllowedValue = metaAllowedValue;
        this.appContext = appContext;
    }

    public void update(TenantMetaMaster inputMetaMaster) {
        this.metaName = inputMetaMaster.getMetaName();
        this.metaDescription = inputMetaMaster.getMetaDescription();
        this.metaDefaultValues = inputMetaMaster.getMetaDefaultValues();
        this.metaAllowedValue = inputMetaMaster.getMetaAllowedValue();
    }
}
