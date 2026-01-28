package com.platformcommons.platform.service.iam.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
public class TenantMeta extends AuthBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, updatable = false, name = "meta_code")
    private String metaCode;

    @Column(nullable = false, name = "meta_value")
    private String metaValue;

    @ElementCollection
    private Set<String> metaValueList;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, updatable = false, name = "tenant")
    @Setter
    private Tenant tenant;

    @Builder
    public TenantMeta(Long id, String metaCode, String metaValue, Set<String> metaValueList, Tenant tenant) {
        this.id = id;
        this.metaCode = metaCode;
        this.metaValue = metaValue;
        this.metaValueList = metaValueList;
        this.tenant = tenant;
    }

    public void update(TenantMeta tenantMeta) {
        this.metaValueList = tenantMeta.getMetaValueList();
    }
}
