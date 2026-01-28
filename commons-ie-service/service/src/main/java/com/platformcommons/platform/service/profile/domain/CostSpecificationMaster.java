package com.platformcommons.platform.service.profile.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import com.platformcommons.platform.service.entity.common.RefData;

import com.platformcommons.platform.service.profile.domain.Enum.FieldType;
import com.platformcommons.platform.service.profile.domain.Enum.InputType;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "cost_specification_master")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class CostSpecificationMaster extends BaseTransactionalEntity implements DomainEntity<CostSpecificationMaster> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @BatchSize(size = 20)
    private Set<MLText> label;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_type", nullable = false)
    private InputType inputType;

    @Column(name = "code", unique = true)
    @NotNull(message = "code must not be null")
    private String code;

    @Column(name = "formula",columnDefinition = "TEXT")
    private String formula;

    @Enumerated(EnumType.STRING)
    @Column(name = "field_type", nullable = false)
    private FieldType fieldType;

    @Column(name = "for_entity_type")
    private String forEntityType;

    @Column(name = "for_entity_id")
    private String forEntityId;

    @Column(name = "purpose")
    private String purpose;

    @Column(name = "context")
    private String context;

    @Column(name = "context_id")
    private String contextId;

    @Column(name = "sequence")
    private Long sequence;

    @Column(name = "input_type_label")
    private String inputTypeLabel;

    @Column(name = "grouping_code")
    private String groupingCode;

    @Builder
    public CostSpecificationMaster(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, String code, FieldType fieldType, String forEntityId, String forEntityType, String formula, Long id, InputType inputType, Set<MLText> label, String purpose,String context,String contextId,Long sequence, String inputTypeLabel, String groupingCode) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.code = code;
        this.fieldType = fieldType;
        this.forEntityId = forEntityId;
        this.forEntityType = forEntityType;
        this.formula = formula;
        this.id = id;
        this.inputType = inputType;
        this.label = label;
        this.purpose = purpose;
        this.context=context;
        this.contextId=contextId;
        this.sequence=sequence;
        this.inputTypeLabel=inputTypeLabel;
        this.groupingCode=groupingCode;
    }

    public void update(CostSpecificationMaster toBeUpdated) {
        if (toBeUpdated.getLabel() != null) {
            Map<Long, MLText> labelMap = getLabelMap();
            for (MLText mlText : toBeUpdated.getLabel()) {
                if (labelMap.containsKey(mlText.getId())) {
                    labelMap.get(mlText.getId()).patchUpdate(mlText);
                } else {
                    this.label.add(mlText);
                }
            }
        }
        if (toBeUpdated.getInputType() != null) {
            this.setInputType(toBeUpdated.getInputType());
        }
        if (toBeUpdated.getCode() != null) {
            this.setCode(toBeUpdated.getCode());
        }
        if (toBeUpdated.getFormula() != null) {
            this.setFormula(toBeUpdated.getFormula());
        }
        if (toBeUpdated.getFieldType() != null) {
            this.setFieldType(toBeUpdated.getFieldType());
        }
        if (toBeUpdated.getForEntityType() != null) {
            this.setForEntityType(toBeUpdated.getForEntityType());
        }
        if (toBeUpdated.getForEntityId() != null) {
            this.setForEntityId(toBeUpdated.getForEntityId());
        }
        if (toBeUpdated.getPurpose() != null) {
            this.setPurpose(toBeUpdated.getPurpose());
        }
        if (toBeUpdated.getContext() != null) {
            this.setContext(toBeUpdated.getContext());
        }
        if (toBeUpdated.getContextId() != null) {
            this.setContextId(toBeUpdated.getContextId());
        }
        if (toBeUpdated.getSequence() != null) {
            this.setSequence(toBeUpdated.getSequence());
        }
        if (toBeUpdated.getInputTypeLabel() != null) {
            this.setInputTypeLabel(toBeUpdated.getInputTypeLabel());
        }
        if (toBeUpdated.getGroupingCode() != null) {
            this.setGroupingCode(toBeUpdated.getGroupingCode());
        }

    }

    private Map<Long, MLText> getLabelMap() {
        return this.label == null ? new HashMap<>() : this.label.stream()
                .collect(Collectors.toMap(MLText::getId, Function.identity()));
    }
}
