package com.platformcommons.platform.service.assessment.domain;

import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Table(name = "default_options")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class DefaultOptions extends BaseTransactionalEntity implements DomainEntity<DefaultOptions> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "options must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "options", nullable = false, updatable = false)
    private Options options;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question", nullable = true, updatable = false)
    private Question question;

    @Column(name = "action_code")
    private String actionCode;

    @Column(name = "is_correct")
    private String isCorrect;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "tenant")
    private Long tenant;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "remarks_required")
    private Boolean remarksRequired;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "default_options_explain", joinColumns = @JoinColumn(name = "default_options_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "default_opt_expl_id", referencedColumnName = "id", unique = true))
    private Set<MLText> defaultOptExpl;

    private String parentOptionCode;

    private Boolean isChildOption;

    @Transient
    private boolean isNew;

    @ElementCollection(fetch = FetchType.LAZY)
    @BatchSize(size = 50)
    private Set<Long> childQuestionList;

    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;


    @Builder
    public DefaultOptions(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Options options, Question question, String isCorrect, Long orderNo, Long tenant, Double weight, Boolean remarksRequired, String linkedSystemId, String linkedSystemType, Set<Long >childQuestionList, Set<MLText> defaultOptExpl, String parentOptionCode, Boolean isChildOption) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.options = options;
        this.question = question;
        this.isCorrect = isCorrect;
        this.orderNo = orderNo;
        this.tenant = tenant;
        this.weight = weight;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType=linkedSystemType;
        this.remarksRequired = remarksRequired;
        this.childQuestionList=childQuestionList;
        this.defaultOptExpl=defaultOptExpl;
        this.parentOptionCode = parentOptionCode;
        this.isChildOption = isChildOption;
    }

    public DefaultOptions init() {
        this.id=null;
        if(this.options!=null) this.options.init();
        else throw new InvalidInputException("Options must not be null");
        return this;
    }

    public void patchUpdate(DefaultOptions toBeUpdated) {
        if(toBeUpdated.getIsActive()!=null && !toBeUpdated.getIsActive()) {
            deactivate(toBeUpdated.getInactiveReason());
            return;
        }
        if (null != toBeUpdated.getOptions()) this.options.patchUpdate(toBeUpdated.getOptions());
        if (null != toBeUpdated.getIsCorrect()) this.isCorrect = toBeUpdated.getIsCorrect();
        if (null != toBeUpdated.getOrderNo()) this.orderNo = toBeUpdated.getOrderNo();
        if (null != toBeUpdated.getTenant()) this.tenant = toBeUpdated.getTenant();
        if (null != toBeUpdated.getWeight()) this.weight = toBeUpdated.getWeight();
        if (null != toBeUpdated.getRemarksRequired()) this.remarksRequired = toBeUpdated.getRemarksRequired();
        if(toBeUpdated.getParentOptionCode()!=null) this.parentOptionCode=toBeUpdated.getParentOptionCode();
        if(toBeUpdated.getIsChildOption()!=null) this.isChildOption=toBeUpdated.getIsChildOption();
        if (null != toBeUpdated.getChildQuestionList()) {
            this.childQuestionList.clear();
            this.childQuestionList.addAll(toBeUpdated.getChildQuestionList());
        }
        if (null != toBeUpdated.getIsActive() && !toBeUpdated.getIsActive()) deactivate(toBeUpdated.getInactiveReason());
    }

}