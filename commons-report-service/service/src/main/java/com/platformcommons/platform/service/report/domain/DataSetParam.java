package com.platformcommons.platform.service.report.domain;

import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.base.NonMultiTenantBaseTransactionalEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "dataset_param")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class DataSetParam extends NonMultiTenantBaseTransactionalEntity implements DomainEntity<DataSetParam> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "param_name")
    private String paramName;

    @Column(name = "data_type")
    private String dataType;

    @Column(name = "position")
    private Integer position;


    @Builder
    public DataSetParam(String uuid, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id,
                        String paramName, String dataType, Integer position) {
        super(uuid, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.paramName = paramName;
        this.dataType = dataType;
        this.position = position;
    }
}
