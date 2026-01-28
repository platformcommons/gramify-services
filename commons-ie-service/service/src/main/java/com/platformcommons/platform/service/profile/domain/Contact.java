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
@Table(name = "contact")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Contact extends BaseTransactionalEntity implements DomainEntity<Contact> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personId must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    private Person personId;

    @Column(name = "contact_type")
    private String contactType;

    @Column(name = "contact_value")
    private String contactValue;

    @Column(name = "is_verified")
    private Boolean isVerified;

    @Transient
    private boolean isNew;

    @Builder
    public Contact(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Person personId, String contactType, String contactValue, Boolean isVerified) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personId = personId;
        this.contactType = contactType;
        this.contactValue = contactValue;
        this.isVerified = isVerified;
    }

    public void init() {
    }

    public void update(Contact toBeUpdated) {
    }

    public void patch(Contact toBeUpdated) {
        if (toBeUpdated.getContactType() != null) {
            this.setContactType(toBeUpdated.getContactType());
        }
        if (toBeUpdated.getContactValue() != null) {
            this.setContactValue(toBeUpdated.getContactValue());
        }
        if (toBeUpdated.getIsVerified() != null) {
            this.setIsVerified(toBeUpdated.getIsVerified());
        }
    }
}