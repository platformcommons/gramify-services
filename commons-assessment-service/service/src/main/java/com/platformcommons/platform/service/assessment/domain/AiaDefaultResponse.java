package com.platformcommons.platform.service.assessment.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.*;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Where;
import org.springframework.util.CollectionUtils;

@Entity
@Getter
@Setter
@Table(name = "aia_default_response")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class AiaDefaultResponse extends BaseTransactionalEntity implements DomainEntity<AiaDefaultResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "option_id", nullable = true, updatable = false)
    private Options optionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_question", nullable = true, updatable = false)
    private SectionQuestions sectionQuestion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_instance_assesse", nullable = true, updatable = false)
    private AssessmentInstanceAssesse assessmentInstanceAssesse;

    @Column(name = "child_default_option_id")
    private Long childDefaultOptionId;

    @Column(name = "child_question_parent_question_id")
    private Long childQuestionParentQuestionId;

    @Column(name = "child_question_id")
    private Long childQuestionId;


    @Column(name = "tenant")
    private Long tenant;

    @Column(name = "location", nullable = true, length = 300)
    private String location;

    @Column(name = "remarks", nullable = true, length = 300)
    private String remarks;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @BatchSize(size = 10)
    @JoinTable(name = "aiadr_description",
            joinColumns = @JoinColumn(name = "aia_default_response_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "aiadr_description_id", referencedColumnName = "id", unique = true))
    private Set<MLText> aiaDesc;

    @Transient
    private boolean isNew;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "aiadefaultResponse")
    @BatchSize(size = 20)
    private Set<DrObjectiveResponse> drobjectiveresponseList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "aiadefaultResponse")
    @BatchSize(size = 20)
    private Set<DrSubjectiveResponse> drSubjectiveResponseList;

    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;
    @Transient
    private boolean optionIdUpdated;

    @Builder
    public AiaDefaultResponse(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Options optionId, SectionQuestions sectionQuestion, AssessmentInstanceAssesse assessmentInstanceAssesse, Long childDefaultOptionId, Long childQuestionId, Long tenant, String location, String remarks, Set<MLText> aiaDesc, Set<DrObjectiveResponse> drobjectiveresponseList, Set<DrSubjectiveResponse> drSubjectiveResponseList, String linkedSystemId, String linkedSystemType, Long childQuestionParentQuestionId) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.optionId = optionId;
        this.sectionQuestion = sectionQuestion;
        this.assessmentInstanceAssesse = assessmentInstanceAssesse;
        this.childDefaultOptionId = childDefaultOptionId;
        this.childQuestionId = childQuestionId;
        this.tenant = tenant;
        this.location = location;
        this.remarks = remarks;
        this.aiaDesc = aiaDesc;
        this.drobjectiveresponseList = drobjectiveresponseList;
        this.drSubjectiveResponseList = drSubjectiveResponseList;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType = linkedSystemType;
        this.childQuestionParentQuestionId = childQuestionParentQuestionId;
        if (null != drobjectiveresponseList) {
            this.drobjectiveresponseList.forEach(it -> it.setAiadefaultResponse(this));
        }
        if (null != drSubjectiveResponseList) {
            this.drSubjectiveResponseList.forEach(it -> it.setAiadefaultResponse(this));
        }
    }

    public AiaDefaultResponse init() {
        this.id = null;
        if (getDrobjectiveresponseList() != null) {
            getDrobjectiveresponseList().forEach(DrObjectiveResponse::init);
        }
        if (getDrSubjectiveResponseList() != null) {
            getDrSubjectiveResponseList().forEach(DrSubjectiveResponse::init);
        }
        return this;
    }

    public void patch(AiaDefaultResponse responseToUpdate) {
        if (responseToUpdate == null) return;
        if (Objects.nonNull(responseToUpdate.getIsActive()) && Objects.equals(responseToUpdate.getIsActive(), Boolean.FALSE)) {
            deactivate(responseToUpdate.getInactiveReason());
            return;
        }
        patchObjectiveResponse(responseToUpdate.getDrobjectiveresponseList());
        patchSubjectiveResponse(responseToUpdate.getDrSubjectiveResponseList());
    }

    private void patchObjectiveResponse(Set<DrObjectiveResponse> drobjectiveresponseList) {
        if (CollectionUtils.isEmpty(drobjectiveresponseList)) return;
        Map<Long, DrObjectiveResponse> currentObjectiveResponseMap = getDrObjectiveResponseMap();
        for (DrObjectiveResponse newResponse : drobjectiveresponseList) {
            if (currentObjectiveResponseMap.containsKey(newResponse.getId())) {
                currentObjectiveResponseMap.get(newResponse.getId()).update(newResponse);
            } else {
                addDrObjectiveResponse(newResponse);
            }
        }
    }

    private void patchSubjectiveResponse(Set<DrSubjectiveResponse> drSubjectiveResponseList) {
        if (CollectionUtils.isEmpty(drSubjectiveResponseList)) return;
        Map<Long, DrSubjectiveResponse> currentSubjectiveResponseMap = getDrSubjectiveResponseMap();
        for (DrSubjectiveResponse newResponse : drSubjectiveResponseList) {
            if (currentSubjectiveResponseMap.containsKey(newResponse.getId())) {
                currentSubjectiveResponseMap.get(newResponse.getId()).update(newResponse);
            } else {
                addDrSubjectiveResponse(newResponse);
            }
        }

    }

    private void addDrSubjectiveResponse(DrSubjectiveResponse newResponse) {
        newResponse.init();
        newResponse.setAiadefaultResponse(this);
        this.getDrSubjectiveResponseList().add(newResponse);
    }
    private void addDrObjectiveResponse(DrObjectiveResponse newResponse) {
        newResponse.init();
        newResponse.setAiadefaultResponse(this);
        this.getDrobjectiveresponseList().add(newResponse);
    }

    private Map<Long, MLText> getAiaDescMap() {
        return this.getAiaDesc().stream()
                .collect(Collectors.toMap(MLText::getId, Function.identity()));
    }

    private Map<Long, DrObjectiveResponse> getDrObjectiveResponseMap() {
        return this.getDrobjectiveresponseList().stream()
                .collect(Collectors.toMap(DrObjectiveResponse::getId, Function.identity()));
    }

    private Map<Long, DrSubjectiveResponse> getDrSubjectiveResponseMap() {
        return this.getDrSubjectiveResponseList().stream()
                .collect(Collectors.toMap(DrSubjectiveResponse::getId, Function.identity()));
    }

    public void setOptionId(Options optionId) {
        Long selectedOptionId = Optional.ofNullable(getOptionId()).map(Options::getId).orElse(null);
        if (Objects.nonNull(selectedOptionId) && !Objects.equals(selectedOptionId, optionId.getId())) {
            this.optionIdUpdated = true;
        }
        this.optionId = optionId;
    }
}