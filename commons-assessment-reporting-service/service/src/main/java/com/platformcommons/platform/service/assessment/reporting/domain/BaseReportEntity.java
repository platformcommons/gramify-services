package com.platformcommons.platform.service.assessment.reporting.domain;


import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;


@MappedSuperclass
@EntityListeners({AuditingEntityListener.class})
@NoArgsConstructor
public class BaseReportEntity {

    @Column(
            name = "linked_system",
            nullable = false,
            updatable = false
    )
    private String linkedSystem;

    @PrePersist
    private void prePersistBaseEntity() {
        this.linkedSystem = EventContext.getSystemEvent();
    }

    public BaseReportEntity(String linkedSystem) {
        this.linkedSystem = linkedSystem;
    }

}
