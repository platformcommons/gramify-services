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
@Table(name = "person_language")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class PersonLanguage extends BaseTransactionalEntity implements DomainEntity<PersonLanguage> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "personId must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id", nullable = false, updatable = false)
    private Person personId;

    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "can_speak")
    private Boolean canSpeak;

    @Column(name = "can_write")
    private Boolean canWrite;

    @Column(name = "can_read")
    private Boolean canRead;

    @Transient
    private boolean isNew;

    @Builder
    public PersonLanguage(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Person personId, String languageCode, Boolean canSpeak, Boolean canWrite, Boolean canRead) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.personId = personId;
        this.languageCode = languageCode;
        this.canSpeak = canSpeak;
        this.canWrite = canWrite;
        this.canRead = canRead;
    }

    public void init() {
    }

    public void update(PersonLanguage toBeUpdated) {
    }
}