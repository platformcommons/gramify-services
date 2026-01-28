package com.platformcommons.platform.service.assessment.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "shedlock")
public class ShedLock implements DomainEntity<ShedLock> {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "lock_until")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockUntil;

    @Column(name = "locked_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lockedAt;

    @Column(name = "locked_by")
    private String lockedBy;

    @Builder
    public ShedLock(Long id, String name, Date lockUntil, Date lockedAt, String lockedBy) {
        this.id = id;
        this.name = name;
        this.lockUntil = lockUntil;
        this.lockedAt = lockedAt;
        this.lockedBy = lockedBy;
    }
}
