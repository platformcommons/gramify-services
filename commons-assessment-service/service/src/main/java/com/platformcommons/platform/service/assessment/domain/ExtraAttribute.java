package com.platformcommons.platform.service.assessment.domain;

import java.util.Set;
import javax.persistence.*;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;
@Entity
@Getter
@Setter
@Table(name = "aia_extra_attribute")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class ExtraAttribute extends BaseTransactionalEntity implements DomainEntity<ExtraAttribute> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "attribute_type")
    private String attributeType;

    @Column(name = "attribute_value")
    private String attributeValue;

    @Column(name = "tenant")
    private Long tenant;

    @Transient
    private boolean isNew;

    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;

    @Builder
    public ExtraAttribute(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String attributeType, String attributeValue, Long tenant, Set<AssessmentInstanceAssesse> assessmentInstanceAssesseList,String linkedSystemId,String linkedSystemType) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.attributeType = attributeType;
        this.attributeValue = attributeValue;
        this.tenant = tenant;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType=linkedSystemType;
    }

}