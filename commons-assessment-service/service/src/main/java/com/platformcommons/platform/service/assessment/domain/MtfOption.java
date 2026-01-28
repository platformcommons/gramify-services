package com.platformcommons.platform.service.assessment.domain;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "mtfoption")
public class MtfOption extends BaseTransactionalEntity implements DomainEntity<MtfOption> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "optionLeft must not be null")
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "OptionLeft", nullable = false, updatable = false)
    private Options optionLeft;

    @NotNull(message = "optionRight must not be null")
    @ManyToOne(cascade = {CascadeType.MERGE,CascadeType.REFRESH,CascadeType.DETACH}, fetch = FetchType.LAZY)
    @JoinColumn(name = "OptionRight", nullable = false, updatable = false,referencedColumnName = "id")
    private Options optionRight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question", nullable = true, updatable = false)
    private Question question;

    @NotNull(message = "weight must not be null")
    @Column(name = "Weight")
    private Double weight;

    @NotNull(message = "tenant must not be null")
    @Column(name = "Tenant")
    private Long tenant;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mtfOptionId")
    private Set<DrObjectiveResponse> drobjectiveresponseList;

    @Builder
    public MtfOption(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Options optionLeft, Options optionRight, Question question, Double weight, Long tenant, Set<DrObjectiveResponse> drobjectiveresponseList) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.optionLeft = optionLeft;
        this.optionRight = optionRight;
        this.question = question;
        this.weight = weight;
        this.tenant = tenant;
        this.drobjectiveresponseList = drobjectiveresponseList;
    }

    public void patchUpdate(MtfOption mtfOption) {
        mtfOption.weight = this.weight;
    }

    public void init() {
        this.id = null;
        if(this.tenant==null) this.tenant = 0L;
        getOptionRight().init();
        getOptionLeft().init();
    }

    public void validateOptions(){
        if(this.getOptionLeft().getUuid()==null || this.getOptionRight().getUuid()==null)
            throw new InvalidInputException("ERR_SVC_QUE_INVALID_INPUT");
    }
}
