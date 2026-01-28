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
@Table(name = "author",
indexes = {@Index(columnList = "tenant")})
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class Author extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<Author> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tenant")
    private Long tenant;

    @Column(name = "website")
    private String website;

    @Column(name = "primary_contact")
    private String primaryContact;

    @Column(name = "secondary_contact")
    private String secondaryContact;

    @Column(name = "primary_email")
    private String primaryEmail;

    @Column(name = "secondary_email")
    private String secondaryEmail;

    @Column(name = "type")
    private String type;

    @Transient
    private boolean isNew;


    @Builder
    public Author(String uuid,  Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String name, Long tenant, String website, String primaryContact, String secondaryContact, String primaryEmail, String secondaryEmail, String type, Set<App> appList, Set<UseCase> useCaseList) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.name = name;
        this.tenant = tenant;
        this.website = website;
        this.primaryContact = primaryContact;
        this.secondaryContact = secondaryContact;
        this.primaryEmail = primaryEmail;
        this.secondaryEmail = secondaryEmail;
        this.type = type;

    }

    public void init() {
    }

    public void update(Author toBeUpdated) {
    }
}