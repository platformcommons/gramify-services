package com.platformcommons.platform.service.profile.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "specification")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Specification extends BaseTransactionalEntity implements DomainEntity<Specification> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "code")
    private String code;

    @Column(name = "value")
    private String value;

    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_specification_id")
    private CostSpecification costSpecification;

    @Builder
    public Specification(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, String code, Long id, String value
                         ,CostSpecification costSpecification) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.code = code;
        this.id = id;
        this.value = value;
        this.costSpecification = costSpecification;
    }


    public void patch(Specification updated) {
        if (updated.getCode() != null) {
            this.setCode(updated.getCode());
        }
        if (updated.getValue() != null) {
            this.setValue(updated.getValue());
        }
    }
}
