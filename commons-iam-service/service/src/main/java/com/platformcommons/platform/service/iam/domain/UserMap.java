package com.platformcommons.platform.service.iam.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class UserMap extends AuthBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false, name = "tenant_partner", referencedColumnName = "id")
    private TenantPartner tenantPartner;

    @ManyToOne
    @JoinColumn(nullable = false, name = "source_user", referencedColumnName = "id")
    private User sourceUser;

    @ManyToOne
    @JoinColumn(nullable = false, name = "target_user", referencedColumnName = "id")
    private User targetUser;

    @Column(nullable = false)
    private String status;



    @Builder
    public UserMap(Long id, TenantPartner tenantPartner, User sourceUser, User targetUser,
                   String status) {
        this.id = id;
        this.tenantPartner = tenantPartner;
        this.sourceUser = sourceUser;
        this.targetUser = targetUser;
        this.status = status;

    }
}
