package com.platformcommons.platform.service.assessment.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.*;

import com.platformcommons.platform.service.assessment.application.constant.ServiceConstants;
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
@Table(name = "assessment_instance_assesse")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AttributeOverrides({
        @AttributeOverride(name = "assessee.actorId", column = @Column(name = "assessee_actor_id")),
        @AttributeOverride(name = "assessee.actorType", column = @Column(name = "assessee_actor_type")),
        @AttributeOverride(name = "assessee.name", column = @Column(name = "assessee_actor_name")),
        @AttributeOverride(name = "assessee.groupId", column = @Column(name = "assessee_group_id")),
        @AttributeOverride(name = "assessee.groupCode", column = @Column(name = "assessee_group_code")),
        @AttributeOverride(name = "assessee.actorIeId", column = @Column(name = "assessee_actor_ie_id")),
        @AttributeOverride(name = "assessor.actorId", column = @Column(name = "assessor_actor_id")),
        @AttributeOverride(name = "assessor.actorType", column = @Column(name = "assessor_actor_type")),
        @AttributeOverride(name = "assessor.groupId", column = @Column(name = "assessor_group_id")),
        @AttributeOverride(name = "assessor.groupCode", column = @Column(name = "assessor_group_code")),
        @AttributeOverride(name = "assessor.actorIeId", column = @Column(name = "assessor_actor_ie_id")),
        @AttributeOverride(name = "assessor.name", column = @Column(name = "assessor_actor_name")),
})
@Where(clause = "is_active = 1")


public class AssessmentInstanceAssesse extends BaseTransactionalEntity implements DomainEntity<AssessmentInstanceAssesse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;


    @Column(name = "status_of_result")
    private String statusOfResult;

    @Column(name = "assessment_taken")
    private String assessmentTaken;

    private EmbeddableAssessmentActor assessee;
    private EmbeddableAssessmentActor assessor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_instance", nullable = true, updatable = false)
    private AssessmentInstance assessmentInstance;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "qpsets", nullable = true, updatable = false)
    private QPSets qpsets;

    @Column(name = "assessor_type")
    private String assessorType;

    @Column(name = "grade")
    private String grade;

    @Column(name = "is_latest")
    private Boolean isLatest;

    @Column(name = "marks_obtained")
    private Double marksObtained;

    @Column(name = "result")
    private Long result;

    @Column(name = "tenant")
    private Long tenant;

    @Column(name = "assessed_for_entity_id")
    private String assessedForEntityId;

    @Column(name = "assessed_for_entity_type")
    private String assessedForEntityType;

    @Column(name = "assessment_taken_on")
    private Date assessmentTakenOn;

    @Column(name = "notes")
    private String notes;

    @Column(name = "location")
    private String location;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinTable(name = "aia_description", joinColumns = @JoinColumn(name = "assessment_instance_assesse_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "aia_description_id", referencedColumnName = "id", unique = true))
    private Set<MLText> aiaAsseseeDesc;


    @Column(name = "is_group_assessment_response")
    private Boolean isGroupAssessmentResponse;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "assessmentInstanceAssesse")
    @BatchSize(size = 20)
    private List<AssessmentActor> assessmentActorList;

    @Transient
    private boolean isNew;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assessmentInstanceAssesse")
    @BatchSize(size = 20)
    private Set<AiaDefaultResponse> aiadefaultresponseList;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assessmentInstanceAssesseId")
    @BatchSize(size = 20)
    private Set<AiaLevel> aiaLevelList;

    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;

    @Column(name = "assessment_taken_for_date")
    private Date assessmentTakenForDate;

    @Builder
    public AssessmentInstanceAssesse(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, EmbeddableAssessmentActor assessee, String statusOfResult, String assessmentTaken, EmbeddableAssessmentActor assessor, AssessmentInstance assessmentInstance, QPSets qpsets, String assessorType, String grade, Boolean isLatest, Double marksObtained, Long result, Long tenant, String assessedForEntityId, String assessedForEntityType, Date assessmentTakenOn, String notes, String location, Set<MLText> aiaAsseseeDesc, Boolean isGroupAssessmentResponse, List<AssessmentActor> assessmentActorList, Set<AiaDefaultResponse> aiadefaultresponseList, Set<AiaLevel> aiaLevelList, String linkedSystemId, String linkedSystemType, Date assessmentTakenForDate) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.assessee = assessee;
        this.statusOfResult = statusOfResult;
        this.assessmentTaken = assessmentTaken;
        this.assessor = assessor;
        this.assessmentInstance = assessmentInstance;
        this.qpsets = qpsets;
        this.assessorType = assessorType;
        this.grade = grade;
        this.isLatest = isLatest;
        this.marksObtained = marksObtained;
        this.result = result;
        this.tenant = tenant;
        this.assessedForEntityId = assessedForEntityId;
        this.assessedForEntityType = assessedForEntityType;
        this.assessmentTakenOn = assessmentTakenOn;
        this.notes = notes;
        this.location = location;
        this.aiaAsseseeDesc = aiaAsseseeDesc;
        this.isGroupAssessmentResponse = isGroupAssessmentResponse;
        this.assessmentActorList = assessmentActorList;
        this.aiadefaultresponseList = aiadefaultresponseList;
        this.aiaLevelList = aiaLevelList;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType = linkedSystemType;
        this.assessmentTakenForDate = assessmentTakenForDate;
        if (null != aiadefaultresponseList) {
            this.aiadefaultresponseList.forEach(it -> it.setAssessmentInstanceAssesse(this));
        }
        if (null != aiaLevelList) {
            this.aiaLevelList.forEach(it -> it.setAssessmentInstanceAssesseId(this));
        }
        if (null != assessmentActorList) {
            this.assessmentActorList.forEach(it -> it.setAssessmentInstanceAssesse(this));
        }
    }

    public Date getAssessment_taken_on() {
        return new Date(assessmentTakenOn != null ? assessmentTakenOn.getTime() : new Date().getTime());
    }

    public void setAssessment_taken_on(Date assessmentTakenOn) {
        this.assessmentTakenOn = new Date(assessmentTakenOn != null ? assessmentTakenOn.getTime() : new Date().getTime());
    }

    public void inactivate(String reason) {
        deactivate(reason);
        if (this.aiadefaultresponseList != null) {
            this.aiadefaultresponseList.forEach(aiaDefaultResponse -> aiaDefaultResponse.deactivate(reason));
        }
    }

    public AssessmentInstanceAssesse init() {
        this.id = null;
        if (getIsGroupAssessmentResponse() == null || !getIsGroupAssessmentResponse()) {
            this.isGroupAssessmentResponse = false;
            if (this.assessmentTaken == null)
                this.assessmentTaken = ServiceConstants.ASSESSMENT_TAKEN_COMPLETE;
        }
        if (getAiadefaultresponseList() != null && !getAiadefaultresponseList().isEmpty()) {
            getAiadefaultresponseList().forEach(AiaDefaultResponse::init);
        }
        if (getAssessmentActorList() != null && !getAssessmentActorList().isEmpty()) {
            getAssessmentActorList().forEach(AssessmentActor::init);
        }
        return this;
    }

    public AssessmentInstanceAssesse patch(AssessmentInstanceAssesse toBeUpdated) {
        boolean responseUpdateAllowed = Objects.equals(ServiceConstants.ASSESSMENT_TAKEN_INCOMPLETE, this.getAssessmentTaken());
        updateAssessmentTaken(toBeUpdated, responseUpdateAllowed);
        updateDefaultResponses(toBeUpdated, responseUpdateAllowed);
        updateAssessmentActors(toBeUpdated);
        return this;
    }

    private void updateAssessmentActors(AssessmentInstanceAssesse toBeUpdated) {
        if (!CollectionUtils.isEmpty(toBeUpdated.getAssessmentActorList())) {
            Map<Long, AssessmentActor> dbActorMap = getAssessmentActorMap();
            Set<String> distinctSet = this.getAssessmentActorList()
                    .stream()
                    .map(assessmentActor -> String.format("%s-%s", assessmentActor.getActorId(), assessmentActor.getActorType()))
                    .collect(Collectors.toSet());
            for (AssessmentActor payloadActor : toBeUpdated.getAssessmentActorList()) {
                if (dbActorMap.containsKey(payloadActor.getId())) {
                    dbActorMap.get(payloadActor.getId()).patch(payloadActor);
                } else if (Objects.isNull(payloadActor.getId()) || Objects.equals(0L, payloadActor.getId())) {
                    if (distinctSet.contains(String.format("%s-%s", payloadActor.getActorId(), payloadActor.getActorType())))
                        continue;
                    this.getAssessmentActorList().add(payloadActor.init());
                }
            }
        }
        if (toBeUpdated.getAssessmentTakenForDate() != null) {
            setAssessmentTakenForDate(toBeUpdated.getAssessmentTakenForDate());
        }
        if (toBeUpdated.getAiadefaultresponseList() != null && !toBeUpdated.getAiadefaultresponseList().isEmpty()) {
        }
    }

        private void updateDefaultResponses (AssessmentInstanceAssesse toBeUpdated,boolean responseUpdateAllowed){
            if (!CollectionUtils.isEmpty(toBeUpdated.getAiadefaultresponseList())) {
                Map<Long, AiaDefaultResponse> responseMap = getAiadefaultresponseMap();
                for (AiaDefaultResponse response : toBeUpdated.getAiadefaultresponseList()) {
                    if (!responseUpdateAllowed) {
                        throw new IllegalStateException("Cannot update! The assessment is already completed.");
                    }
                    if (responseMap.containsKey(response.getId())) {
                        responseMap.get(response.getId()).patch(response);
                    } else {
                        getAiadefaultresponseList().add(response.init());
                    }
                }
            }
        }

        private void updateAssessmentTaken (AssessmentInstanceAssesse toBeUpdated,boolean responseUpdateAllowed){
            if (toBeUpdated.getAssessmentTaken() != null && responseUpdateAllowed) {
                setAssessmentTaken(toBeUpdated.getAssessmentTaken());
            }
        }

        private Map<Long, AiaDefaultResponse> getAiadefaultresponseMap () {
            return this.aiadefaultresponseList.stream()
                    .collect(Collectors.toMap(AiaDefaultResponse::getId, Function.identity()));
        }

        private Map<Long, AssessmentActor> getAssessmentActorMap() {
        return this.assessmentActorList.stream()
                .collect(Collectors.toMap(AssessmentActor::getId, Function.identity()));
    }
}