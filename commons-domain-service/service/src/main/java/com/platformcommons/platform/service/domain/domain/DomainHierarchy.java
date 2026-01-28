package com.platformcommons.platform.service.domain.domain;

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
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "domain_hierarchy")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DomainHierarchy extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<DomainHierarchy> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "domain must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "domain", nullable = false, updatable = false)
    private Domain domain;

    @NotNull(message = "parentDomain must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_domain", nullable = false, updatable = false)
    private Domain parentDomain;

    @NotNull(message = "depth must not be null")
    @Column(name = "depth")
    private Long depth;

    @Transient
    private boolean isNew;

    @Builder
    public DomainHierarchy(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Domain domain, Domain parentDomain, Long depth) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.domain = domain;
        this.parentDomain = parentDomain;
        this.depth = depth;
    }

    public void init() {
    }

    public void update(DomainHierarchy toBeUpdated) {
    }
}