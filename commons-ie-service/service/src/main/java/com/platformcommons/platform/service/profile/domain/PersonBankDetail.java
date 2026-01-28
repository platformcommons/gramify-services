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
@Table(name = "person_bank_detail")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PersonBankDetail extends BaseTransactionalEntity implements DomainEntity<PersonBankDetail> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personId must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    private Person personId;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "branch_name")
    private String branchName;

    @Column(name = "branch_code")
    private String branchCode;

    @Column(name = "micr")
    private String micr;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "is_primary")
    private Boolean isPrimary;

    @Transient
    private boolean isNew;

    @Builder
    public PersonBankDetail(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Person personId, String bankName, String accountNumber, String branchName, String branchCode, String micr, String accountType, Boolean isPrimary) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personId = personId;
        this.bankName = bankName;
        this.accountNumber = accountNumber;
        this.branchName = branchName;
        this.branchCode = branchCode;
        this.micr = micr;
        this.accountType = accountType;
        this.isPrimary = isPrimary;
    }

    public void init() {
    }

    public void update(PersonBankDetail toBeUpdated) {
    }
}