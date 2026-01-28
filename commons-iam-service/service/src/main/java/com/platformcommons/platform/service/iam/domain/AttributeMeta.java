package com.platformcommons.platform.service.iam.domain;

import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@Table(name = "attribute_meta")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AttributeMeta  extends NonMultiTenantBaseTransactionalEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Column(name = "attribute_code")
    private String attributeCode;

    @Column(name = "data_type")
    private String dataType;


    @Column(name = "data_source")
    private String dataSource;

    @Column(name =  "validation_constraint")
    private String validationConstraint;

    @Builder
    public AttributeMeta(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                         Long id, String attributeCode, String dataType, String validationConstraint, String dataSource) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.attributeCode = attributeCode;
        this.dataType = dataType;
        this.dataSource = dataSource;
        this.validationConstraint = validationConstraint;
    }
}
