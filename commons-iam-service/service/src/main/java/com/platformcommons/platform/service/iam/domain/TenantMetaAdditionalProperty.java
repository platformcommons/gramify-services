package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;


@Entity
@Getter
@Setter
@Table(
        name = "tenant_meta_additional_property",
        uniqueConstraints = {
                @UniqueConstraint(
                        name="UniqueTenantMeta",
                        columnNames = {"tenant_meta_config","is_active","meta_key",}
                )})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class TenantMetaAdditionalProperty  extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<TenantMetaConfig> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "meta_key", nullable = false)
    private String metaKey;

    @Column(name = "meta_value", nullable = false)
    private String metaValue;

    @Column(name = "is_multi_valued")
    private Boolean isMultivalued;

    @Column(name = "multi_value_separator")
    private String multiValueSeparator;

    @Column(name = "app_context")
    private String appContext;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "tenant_meta_additional_properties_attributes",
            joinColumns = {@JoinColumn(name="tenant_meta_additional_property_id",referencedColumnName = "id")})
    @MapKeyColumn(name = "attribute_key")
    @Column(name = "attribute_value", columnDefinition = "LONGTEXT")
    private Map<String,String> attributes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_meta_config")
    private TenantMetaConfig tenantMetaConfig;

    @Builder
    public TenantMetaAdditionalProperty(String uuid, Boolean isActive, Long appCreatedTimestamp,
                                        Long appLastModifiedTimestamp, Long id, String metaKey,
                                        String metaValue, Boolean isMultivalued, String multiValueSeparator,
                                        TenantMetaConfig tenantMetaConfig,Map<String,String> attributes,
                                        String appContext) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.metaKey = metaKey;
        this.metaValue = metaValue;
        this.isMultivalued = isMultivalued;
        this.multiValueSeparator = multiValueSeparator;
        this.tenantMetaConfig = tenantMetaConfig;
        this.attributes = attributes;
        this.appContext = appContext;
    }

    public static TenantMetaAdditionalProperty createPropertyObjectForSingleValue(String metaKey,String metaValue,String appContext) {
        return TenantMetaAdditionalProperty.builder()
                .metaKey(metaKey)
                .metaValue(metaValue)
                .appContext(appContext)
                .isMultivalued(Boolean.FALSE)
                .build();
    }

    public static TenantMetaAdditionalProperty createPropertyObjectForMultiValues(String metaKey, String metaValue,
                                                                                  String multiValueSeparator, String appContext) {
        return TenantMetaAdditionalProperty.builder()
                .metaKey(metaKey)
                .metaValue(metaValue)
                .appContext(appContext)
                .multiValueSeparator(multiValueSeparator)
                .isMultivalued(Boolean.TRUE)
                .build();
    }

    public void init(TenantMetaConfig tenantMetaConfig) {
        this.id = 0L;
        this.tenantMetaConfig = tenantMetaConfig;
    }

    public void patch(TenantMetaAdditionalProperty toBeUpdated) {
        if(toBeUpdated.getMetaKey() != null) {
            this.metaKey = toBeUpdated.getMetaKey();
        }
        if(toBeUpdated.getMetaValue() != null) {
            this.metaValue = toBeUpdated.getMetaValue();
        }
        if(toBeUpdated.getMultiValueSeparator() != null) {
            this.multiValueSeparator = toBeUpdated.getMultiValueSeparator();
        }
        if(toBeUpdated.getIsMultivalued() != null) {
            this.isMultivalued = toBeUpdated.getIsMultivalued();
        }
        if(toBeUpdated.getAttributes() != null) {
            this.attributes = toBeUpdated.getAttributes();
        }
        if(toBeUpdated.getAppContext() != null) {
            this.appContext = toBeUpdated.getAppContext();
        }
    }
}
