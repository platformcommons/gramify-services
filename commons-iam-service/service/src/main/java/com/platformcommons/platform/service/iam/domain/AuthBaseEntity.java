package com.platformcommons.platform.service.iam.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.io.Serializable;
import java.util.Calendar;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class AuthBaseEntity implements Serializable {

    @Column(name = "created_timestamp", nullable = false, updatable = false)
    private Long createdTimestamp;

    @Column(name = "last_modified_timestamp", nullable = false)
    private Long lastModifiedTimestamp;


    @PrePersist
    private void prePersistBaseEntity() {
        this.createdTimestamp = this.lastModifiedTimestamp = Calendar.getInstance().getTimeInMillis();
    }

    @PreUpdate
    private void preUpdateBaseEntity() {
        this.lastModifiedTimestamp = Calendar.getInstance().getTimeInMillis();
    }
}
