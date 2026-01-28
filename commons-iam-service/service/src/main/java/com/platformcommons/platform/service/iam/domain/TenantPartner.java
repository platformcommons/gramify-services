package com.platformcommons.platform.service.iam.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"parent_tenant", "tenant"})})
public class TenantPartner extends AuthBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;


    @ManyToOne
    @JoinColumn(nullable = false, name = "tenant", referencedColumnName = "id")
    private Tenant tenant;

    @ManyToOne
    @JoinColumn(nullable = false, name = "parent_tenant", referencedColumnName = "id")
    private Tenant parentTenant;

    @Column(nullable = false)
    private String status;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExtraAttribute> extraAttributes;

    @Builder
    public TenantPartner(Long id, Tenant tenant, Tenant parentTenant, String status, Set<ExtraAttribute> extraAttributes, Boolean isPrimary) {
        this.id = id;
        this.tenant = tenant;
        this.parentTenant = parentTenant;
        this.status = status;
        this.extraAttributes = extraAttributes;
        this.isPrimary = isPrimary;
    }

    public void init() {
        this.id = null;
    }
}
