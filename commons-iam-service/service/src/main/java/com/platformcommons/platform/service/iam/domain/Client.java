package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id", "tenant_id"})})
public class Client extends NonMultiTenantBaseTransactionalEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(nullable = false, name = "client_id")
    private String clientId;

    @Column(name = "client_secret", nullable = false)
    private String clientSecret;

    @Column(name = "realm_representation")
    private String realmRepresentation;


    @Builder
    public Client(Long id, String clientId, String clientSecret,
                  String realmRepresentation) {
        this.id = id;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.realmRepresentation = realmRepresentation;
    }
}
