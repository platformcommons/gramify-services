package com.platformcommons.platform.service.profile.domain;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "organization_finance")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class OrganizationFinance extends BaseTransactionalEntity implements DomainEntity<OrganizationFinance> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personOrganizationId must not be null")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_organization_id",updatable = false,nullable = false)
    private PersonOrganization personOrganizationId;

    @Column(name = "gst_number")
    private String gstNumber;

    @Column(name = "cin")
    private String cin;

    @Column(name = "tan")
    private String tan;

    @Column(name = "pan")
    private String pan;

    @Transient
    private boolean isNew;

    @Builder
    public OrganizationFinance(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, PersonOrganization personOrganizationId, String gstNumber, String cin, String tan, String pan) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personOrganizationId = personOrganizationId;
        this.gstNumber = gstNumber;
        this.cin = cin;
        this.tan = tan;
        this.pan = pan;
    }

    public void init() {
    }

    public void update(OrganizationFinance toBeUpdated) {
    }
}