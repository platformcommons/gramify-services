package com.platformcommons.platform.service.profile.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;


import lombok.*;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "cost_specification_line_item")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CostSpecificationLineItem extends BaseTransactionalEntity implements DomainEntity<CostSpecificationLineItem> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String code;

    @Column(name = "currency_or_uom_code")
    private String currencyOrUoMCode;

    @Column(name="value")
    private Double value;

    @Column(name = "date_of_entry")
    private Date dateOfEntry;


    @ManyToOne( fetch = FetchType.LAZY)
    @JoinColumn(name = "cost_specification_id", nullable = false, updatable = false)
    private CostSpecification costSpecification;


    @Builder
    public CostSpecificationLineItem(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, String code, String currencyOrUoMCode, Date dateOfEntry, Long id, Double value,CostSpecification costSpecification) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.code = code;
        this.currencyOrUoMCode = currencyOrUoMCode;
        this.dateOfEntry = dateOfEntry;
        this.id = id;
        this.value = value;
        this.costSpecification=costSpecification;
    }

    public void update(CostSpecificationLineItem updatedItem) {
        if (updatedItem.getCode() != null) {
            this.code = updatedItem.getCode();
        }
        if (updatedItem.getCurrencyOrUoMCode() != null) {
            this.currencyOrUoMCode = updatedItem.getCurrencyOrUoMCode();
        }
        if (updatedItem.getValue() != null) {
            this.value = updatedItem.getValue();
        }
        if (updatedItem.getDateOfEntry() != null) {
            this.dateOfEntry = updatedItem.getDateOfEntry();
        }
    }

    public void patch(CostSpecificationLineItem updatedItem) {
        if (updatedItem.getCode() != null) {
            this.code = updatedItem.getCode();
        }
        if (updatedItem.getCurrencyOrUoMCode() != null) {
            this.currencyOrUoMCode = updatedItem.getCurrencyOrUoMCode();
        }
        if (updatedItem.getValue() != null) {
            this.value = updatedItem.getValue();
        }
        if (updatedItem.getDateOfEntry() != null) {
            this.dateOfEntry = updatedItem.getDateOfEntry();
        }
    }
}

