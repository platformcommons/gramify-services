package com.platformcommons.platform.service.profile.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.UoMValue;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "cost_specification")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CostSpecification extends BaseTransactionalEntity implements DomainEntity<CostSpecification> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "for_entity_id")
    private String forEntityId;

    @Column(name = "for_entity_type")
    private String forEntityType;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "tagged_to_entity_id")
    private String taggedToEntityId;

    @Column(name = "tagged_to_entity_type")
    private String taggedToEntityType;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "sub_type")
    private String subType;

    @Column(name = "remarks", columnDefinition = "TEXT")
    private String remarks;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue quantity;

    @OneToMany(mappedBy = "costSpecification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private Set<CostSpecificationLineItem> costSpecificationLineItemsList;

    @OneToMany(mappedBy = "costSpecification", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private Set<Specification> specificationsList;


    @Builder
    public CostSpecification(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,  Date endDate, String forEntityId, String forEntityType, Long id, Date startDate, String taggedToEntityId, String taggedToEntityType,String status, String type, String subType,  String remarks,UoMValue quantity,Set<CostSpecificationLineItem> costSpecificationLineItemsList
                              ,Set<Specification> specificationsList) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.endDate = endDate;
        this.forEntityId = forEntityId;
        this.forEntityType = forEntityType;
        this.id = id;
        this.startDate = startDate;
        this.taggedToEntityId = taggedToEntityId;
        this.taggedToEntityType = taggedToEntityType;
        this.status=status;
        this.type= type;
        this.subType= subType;
        this.remarks=remarks;
        this.quantity=quantity;
        this.costSpecificationLineItemsList=costSpecificationLineItemsList;
        this.specificationsList= specificationsList;
        if (null != costSpecificationLineItemsList) {
            this.costSpecificationLineItemsList.forEach(it -> it.setCostSpecification(this));
        }
        if (null != specificationsList) {
            this.specificationsList.forEach(it -> it.setCostSpecification(this));
        }
    }

    public void update(CostSpecification toBeUpdated) {
        if (toBeUpdated.getForEntityId() != null) {
            this.setForEntityId(toBeUpdated.getForEntityId());
        }
        if (toBeUpdated.getForEntityType() != null) {
            this.setForEntityType(toBeUpdated.getForEntityType());
        }
        if (toBeUpdated.getStartDate() != null) {
            this.setStartDate(toBeUpdated.getStartDate());
        }
        if (toBeUpdated.getEndDate() != null) {
            this.setEndDate(toBeUpdated.getEndDate());
        }
        if (toBeUpdated.getTaggedToEntityId() != null) {
            this.setTaggedToEntityId(toBeUpdated.getTaggedToEntityId());
        }
        if (toBeUpdated.getTaggedToEntityType() != null) {
            this.setTaggedToEntityType(toBeUpdated.getTaggedToEntityType());
        }
        if (toBeUpdated.getStatus() != null) {
            this.setStatus(toBeUpdated.getStatus());
        }
        if (toBeUpdated.getType() != null) {
            this.setType(toBeUpdated.getType());
        }
        if (toBeUpdated.getSubType() != null) {
            this.setSubType(toBeUpdated.getSubType());
        }
        if (toBeUpdated.getRemarks()!= null) {
            this.setRemarks(toBeUpdated.getRemarks());
        }
        if (toBeUpdated.getQuantity()!= null) {
            this.setQuantity(toBeUpdated.getQuantity());
        }
    }

    public void patch(CostSpecification toBeUpdated) {
        if (toBeUpdated.getForEntityId() != null) {
            this.setForEntityId(toBeUpdated.getForEntityId());
        }
        if (toBeUpdated.getForEntityType() != null) {
            this.setForEntityType(toBeUpdated.getForEntityType());
        }
        if (toBeUpdated.getStartDate() != null) {
            this.setStartDate(toBeUpdated.getStartDate());
        }
        if (toBeUpdated.getEndDate() != null) {
            this.setEndDate(toBeUpdated.getEndDate());
        }
        if (toBeUpdated.getTaggedToEntityId() != null) {
            this.setTaggedToEntityId(toBeUpdated.getTaggedToEntityId());
        }
        if (toBeUpdated.getTaggedToEntityType() != null) {
            this.setTaggedToEntityType(toBeUpdated.getTaggedToEntityType());
        }
        if (toBeUpdated.getStatus() != null) {
            this.setStatus(toBeUpdated.getStatus());
        }
        if (toBeUpdated.getType() != null) {
            this.setType(toBeUpdated.getType());
        }
        if (toBeUpdated.getSubType() != null) {
            this.setSubType(toBeUpdated.getSubType());
        }
        if (toBeUpdated.getRemarks()!= null) {
            this.setRemarks(toBeUpdated.getRemarks());
        }
        if (toBeUpdated.getQuantity()!= null) {
            this.setQuantity(toBeUpdated.getQuantity());
        }
        if (toBeUpdated.getCostSpecificationLineItemsList() != null && !toBeUpdated.getCostSpecificationLineItemsList().isEmpty()) {
            this.getCostSpecificationLineItemsList().forEach(existingItem ->
                    toBeUpdated.getCostSpecificationLineItemsList().forEach(updatedItem -> {
                        if (existingItem.getId().equals(updatedItem.getId())) {
                            existingItem.patch(updatedItem);
                        }
                    })
            );
            toBeUpdated.getCostSpecificationLineItemsList().forEach(newItem -> {
                if (newItem.getId() == null || newItem.getId().equals(0L)) {
                    newItem.setCostSpecification(this);
                    this.getCostSpecificationLineItemsList().add(newItem);
                }
            });
        }
        if (toBeUpdated.getSpecificationsList() != null && !toBeUpdated.getSpecificationsList().isEmpty()) {
            this.getSpecificationsList().forEach(existingSpec ->
                    toBeUpdated.getSpecificationsList().forEach(updatedSpec -> {
                        if (existingSpec.getId().equals(updatedSpec.getId())) {
                            existingSpec.patch(updatedSpec);
                        }
                    })
            );
            toBeUpdated.getSpecificationsList().forEach(newSpec -> {
                if (newSpec.getId() == null || newSpec.getId().equals(0L)) {
                    newSpec.setCostSpecification(this);
                    this.getSpecificationsList().add(newSpec);
                }
            });
        }
    }
}
