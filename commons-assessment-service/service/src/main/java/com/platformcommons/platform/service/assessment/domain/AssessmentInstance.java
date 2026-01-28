package com.platformcommons.platform.service.assessment.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
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
@Table(name = "assessment_instance")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
@AttributeOverrides({
        @AttributeOverride(name = "monitoredBy.actorId", column = @Column(name = "monitored_by_actor_id")),
        @AttributeOverride(name = "monitoredBy.actorType", column = @Column(name = "monitored_by_actor_type")),
        @AttributeOverride(name = "conductedBy.actorId", column = @Column(name = "conducted_by_actor_id")),
        @AttributeOverride(name = "conductedBy.actorType", column = @Column(name = "conducted_by_actor_type")),
})
public class AssessmentInstance extends BaseTransactionalEntity implements DomainEntity<AssessmentInstance> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "test_type", updatable = false)
    private String testType;

    @NotNull(message = "assessment must not be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assessment", nullable = false, updatable = false)
    private Assessment assessment;

    @Column(name = "schedule_status")
    private String scheduleStatus;

    private AssessmentInstanceActor monitoredBy;

    private AssessmentInstanceActor conductedBy;

    @Column(name = "academic_session")
    private String academicSession;

    @Column(name = "confirmation_date")
    private Date confirmationDate;

    @Column(name = "duration")
    private Long duration;

    @NotNull(message = "endDate must not be null")
    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "for_entity_id")
    private String forEntityId;

    @Column(name = "for_entity_type")
    private String forEntityType;

    @Column(name = "sclevel_batch")
    private Long sclevelBatch;

    @Column(name = "school")
    private Long school;

    @NotNull(message = "startDate must not be null")
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "tenant")
    private Long tenant;

    @Column(name = "test_number")
    private Long testNumber;

    @Column(name = "imageurl")
    private String imageURL;

    @Column(name = "is_public")
    private Boolean isPublic;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "assessment_instance_description",
            joinColumns = @JoinColumn(name = "assessment_instance_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "asmt_inst_desc_id", referencedColumnName = "id", unique = true))
    private Set<MLText> asmtInstDesc;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "assessment_instance_name",
            joinColumns = @JoinColumn(name = "assessment_instance_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "asmt_inst_name_id", referencedColumnName = "id", unique = true)
    )
    private Set<MLText> asmtInstName;

    @Column(name = "specific_visibility")
    private Boolean specificVisibility;

    @Transient
    private boolean isNew;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assessmentInstance")
    @BatchSize(size = 20)
    private Set<AssessmentInstanceAssesse> assessmentInstanceAssesseList;

    @OneToMany(mappedBy = "assessmentInstance", fetch = FetchType.LAZY)
    @BatchSize(size = 20)
    private List<AssessmentInstanceAccessor> assessmentInstanceAccessors;
    @Column
    private Long duplicatedFrom;
    @Column
    private Long duplicatedCount;
    @Column
    private Long sequence;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;

    @Builder
    public AssessmentInstance(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String testType, Assessment assessment, String scheduleStatus, AssessmentInstanceActor monitoredBy, AssessmentInstanceActor conductedBy, String academicSession, Date confirmationDate, Long duration, Date endDate, String forEntityId, String forEntityType, Long sclevelBatch, Long school, Date startDate, Long tenant, Long testNumber, String imageURL, Boolean isPublic, Set<MLText> asmtInstName, Set<MLText> asmtInstDesc, Set<AssessmentInstanceAssesse> assessmentInstanceAssesseList, Long duplicatedFrom, Long duplicatedCount, Boolean specificVisibility, String linkedSystemId, String linkedSystemType, Long sequence) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.testType = testType;
        this.assessment = assessment;
        this.scheduleStatus = scheduleStatus;
        this.monitoredBy = monitoredBy;
        this.conductedBy = conductedBy;
        this.academicSession = academicSession;
        this.confirmationDate = confirmationDate;
        this.duration = duration;
        this.endDate = endDate;
        this.forEntityId = forEntityId;
        this.forEntityType = forEntityType;
        this.sclevelBatch = sclevelBatch;
        this.school = school;
        this.startDate = startDate;
        this.tenant = tenant;
        this.testNumber = testNumber;
        this.imageURL = imageURL;
        this.isPublic = isPublic;
        this.asmtInstName = asmtInstName;
        this.asmtInstDesc = asmtInstDesc;
        this.assessmentInstanceAssesseList = assessmentInstanceAssesseList;
        this.duplicatedFrom = duplicatedFrom;
        this.duplicatedCount = duplicatedCount;
        this.specificVisibility = specificVisibility;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType = linkedSystemType;
        if (null != assessmentInstanceAssesseList) {
            this.assessmentInstanceAssesseList.forEach(it -> it.setAssessmentInstance(this));
        }
        this.sequence = sequence;
    }

    public AssessmentInstance init(){
        this.id = null;
        if (getAssessment() == null || getAssessment().getId()==null) {
            throw new InvalidInputException("Assessment is mandatory for creating AssessmentInstance");
        }
        return this;
    }

    public void update(AssessmentInstance assessmentInstance) {
        if (assessmentInstance.getTestType() != null) this.testType = assessmentInstance.getTestType();
        if (assessmentInstance.getScheduleStatus() != null)
            this.scheduleStatus = assessmentInstance.getScheduleStatus();
        if (assessmentInstance.getMonitoredBy() != null) this.monitoredBy = assessmentInstance.getMonitoredBy();
        if (assessmentInstance.getConductedBy() != null) this.conductedBy = assessmentInstance.getConductedBy();
        if (assessmentInstance.getAcademicSession() != null)
            this.academicSession = assessmentInstance.getAcademicSession();
        if (assessmentInstance.getConfirmationDate() != null)
            this.confirmationDate = assessmentInstance.getConfirmationDate();
        if (assessmentInstance.getDuration() != null) this.duration = assessmentInstance.getDuration();
        if (assessmentInstance.getEndDate() != null) this.endDate = assessmentInstance.getEndDate();
        if (assessmentInstance.getForEntityId() != null) this.forEntityId = assessmentInstance.getForEntityId();
        if (assessmentInstance.getForEntityType() != null) this.forEntityType = assessmentInstance.getForEntityType();
        if (assessmentInstance.getSclevelBatch() != null) this.sclevelBatch = assessmentInstance.getSclevelBatch();
        if (assessmentInstance.getSchool() != null) this.school = assessmentInstance.getSchool();
        if (assessmentInstance.getStartDate() != null) this.startDate = assessmentInstance.getStartDate();
        if (assessmentInstance.getTenant() != null) this.tenant = assessmentInstance.getTenant();
        if (assessmentInstance.getTestNumber() != null) this.testNumber = assessmentInstance.getTestNumber();
        if (assessmentInstance.getImageURL() != null) this.imageURL = assessmentInstance.getImageURL();
        if (assessmentInstance.getIsPublic() != null) this.isPublic = assessmentInstance.getIsPublic();
        if (assessmentInstance.getAsmtInstName() != null) {
            Map<Long,MLText> nameMap = getAsmtInstNameMap();
            for (MLText mlText : assessmentInstance.getAsmtInstName()) {
                if(nameMap.containsKey(mlText.getId())){
                    nameMap.get(mlText.getId()).patchUpdate(mlText);
                }
                else{
                    getAsmtInstName().add(mlText);
                }
            }
        }
        if (assessmentInstance.getAsmtInstDesc() != null) {
            Map<Long,MLText> descMap = getAsmtInstDescMap();
            for (MLText mlText : assessmentInstance.getAsmtInstDesc()) {
                if(descMap.containsKey(mlText.getId())){
                    descMap.get(mlText.getId()).patchUpdate(mlText);
                }
                else{
                    getAsmtInstDesc().add(mlText);
                }
            }
        }
        if (assessmentInstance.getDuplicatedFrom() != null)
            this.duplicatedFrom = assessmentInstance.getDuplicatedFrom();
        if (assessmentInstance.getDuplicatedCount() != null)
            this.duplicatedCount = assessmentInstance.getDuplicatedCount();
        if (assessmentInstance.getSpecificVisibility() != null)
            this.specificVisibility = assessmentInstance.getSpecificVisibility();
        if (assessmentInstance.getSequence() != null)
            this.sequence = assessmentInstance.getSequence();
    }

    private Map<Long, MLText> getAsmtInstDescMap() {
        return this.asmtInstDesc==null?new HashMap<>():this.asmtInstDesc.stream().collect(Collectors.toMap(MLText::getId, Function.identity()));
    }

    private Map<Long, MLText> getAsmtInstNameMap() {
        return this.asmtInstName==null?new HashMap<>():this.asmtInstName.stream().collect(Collectors.toMap(MLText::getId, Function.identity()));
    }

    public Date getConfirmation_date() {
        return new Date(confirmationDate != null ? confirmationDate.getTime() : new Date().getTime());
    }

    public void setConfirmation_date(Date confirmationDate) {
        this.confirmationDate = new Date(confirmationDate != null ? confirmationDate.getTime() : new Date().getTime());
    }

    public Date getEnd_date() {
        return new Date(endDate != null ? endDate.getTime() : new Date().getTime());
    }

    public void setEnd_date(Date endDate) {
        this.endDate = new Date(endDate != null ? endDate.getTime() : new Date().getTime());
    }

    public Date getStart_date() {
        return new Date(startDate != null ? startDate.getTime() : new Date().getTime());
    }

    public void setStart_date(Date startDate) {
        this.startDate = new Date(startDate != null ? startDate.getTime() : new Date().getTime());
    }

}