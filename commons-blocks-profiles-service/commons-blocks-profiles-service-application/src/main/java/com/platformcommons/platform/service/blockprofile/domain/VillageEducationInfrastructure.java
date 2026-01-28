package com.platformcommons.platform.service.blockprofile.domain;

import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;

import com.platformcommons.platform.service.entity.common.*;

import lombok.*;
import java.util.*;
import org.hibernate.annotations.BatchSize;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "village_education_infrastructure")
public class VillageEducationInfrastructure extends  BaseTransactionalEntity implements DomainEntity<VillageEducationInfrastructure> {

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue primarySchoolCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue highSchoolCount;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue preUniversityCollegeCount;


    @Column(
         name = "vocational_training_available"
    )
    private Boolean vocationalTrainingAvailable;

    @Column(
             name = "teacher_vacancy_level"
    )
    private String teacherVacancyLevel;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue vocationalTrainingEnrolmentLastYear;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue middleSchoolCount;


    @Column(
         name = "higher_education_access_location"
    )
    private String higherEducationAccessLocation;

    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_context_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_context_entity_type"))
    })
    private LinkedCode linkedContext;



    @Embedded
    @AttributeOverrides({
                @AttributeOverride(name = "linkedCode", column = @Column(name = "linked_actor_code")),
                @AttributeOverride(name = "linkedEntityType", column = @Column(name = "linked_actor_entity_type"))
    })
    private LinkedCode linkedActor;



    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private UoMValue avgStudentTeacherRatio;



    @OneToMany(mappedBy = "villageEducationInfrastructure", cascade = CascadeType.ALL)
    private Set<IssuesInHigherEducationAccess> issuesinhighereducationaccessList;


    @Builder
    public VillageEducationInfrastructure(String uuid, Long tenantId, Boolean isActive, String inactiveReason,
        Long appCreatedTimestamp, Long appLastModifiedTimestamp,
        UoMValue primarySchoolCount,
        UoMValue highSchoolCount,
        UoMValue preUniversityCollegeCount,
        Boolean vocationalTrainingAvailable,
        String teacherVacancyLevel,
        UoMValue vocationalTrainingEnrolmentLastYear,
        Long id,
        UoMValue middleSchoolCount,
        String higherEducationAccessLocation,
        LinkedCode linkedContext,
        LinkedCode linkedActor,
        UoMValue avgStudentTeacherRatio,
        Set<IssuesInHigherEducationAccess> issuesinhighereducationaccessList
    ) {
        super(uuid, tenantId, isActive, inactiveReason, appCreatedTimestamp, appLastModifiedTimestamp);
        this.primarySchoolCount = primarySchoolCount;
        this.highSchoolCount = highSchoolCount;
        this.preUniversityCollegeCount = preUniversityCollegeCount;
        this.vocationalTrainingAvailable = vocationalTrainingAvailable;
        this.teacherVacancyLevel = teacherVacancyLevel;
        this.vocationalTrainingEnrolmentLastYear = vocationalTrainingEnrolmentLastYear;
        this.id = id;
        this.middleSchoolCount = middleSchoolCount;
        this.higherEducationAccessLocation = higherEducationAccessLocation;
        this.linkedContext = linkedContext;
        this.linkedActor = linkedActor;
        this.avgStudentTeacherRatio = avgStudentTeacherRatio;
        this.issuesinhighereducationaccessList=issuesinhighereducationaccessList;
        this.issuesinhighereducationaccessList.forEach(it->it.setVillageEducationInfrastructure(this));


    }


    public void update(VillageEducationInfrastructure toBeUpdated) {
        // Delegate to patch to support partial updates
        if (toBeUpdated == null) return;
        this.patch(toBeUpdated);
    }

    /**
     * Applies non-null fields from the provided instance to this instance.
     * Collections in ONE_MANY relationships are replaced if provided and each child is re-attached to this parent.
     */
    public void patch(VillageEducationInfrastructure toBeUpdated) {
        if (toBeUpdated == null) return;
        if (toBeUpdated.getPrimarySchoolCount() != null) {
            this.setPrimarySchoolCount(toBeUpdated.getPrimarySchoolCount());
        }
        if (toBeUpdated.getHighSchoolCount() != null) {
            this.setHighSchoolCount(toBeUpdated.getHighSchoolCount());
        }
        if (toBeUpdated.getPreUniversityCollegeCount() != null) {
            this.setPreUniversityCollegeCount(toBeUpdated.getPreUniversityCollegeCount());
        }
        if (toBeUpdated.getVocationalTrainingAvailable() != null) {
            this.setVocationalTrainingAvailable(toBeUpdated.getVocationalTrainingAvailable());
        }
        if (toBeUpdated.getTeacherVacancyLevel() != null) {
            this.setTeacherVacancyLevel(toBeUpdated.getTeacherVacancyLevel());
        }
        if (toBeUpdated.getVocationalTrainingEnrolmentLastYear() != null) {
            this.setVocationalTrainingEnrolmentLastYear(toBeUpdated.getVocationalTrainingEnrolmentLastYear());
        }
        if (toBeUpdated.getId() != null) {
            this.setId(toBeUpdated.getId());
        }
        if (toBeUpdated.getMiddleSchoolCount() != null) {
            this.setMiddleSchoolCount(toBeUpdated.getMiddleSchoolCount());
        }
        if (toBeUpdated.getHigherEducationAccessLocation() != null) {
            this.setHigherEducationAccessLocation(toBeUpdated.getHigherEducationAccessLocation());
        }
        if (toBeUpdated.getLinkedContext() != null) {
            this.setLinkedContext(toBeUpdated.getLinkedContext());
        }
        if (toBeUpdated.getLinkedActor() != null) {
            this.setLinkedActor(toBeUpdated.getLinkedActor());
        }
        if (toBeUpdated.getAvgStudentTeacherRatio() != null) {
            this.setAvgStudentTeacherRatio(toBeUpdated.getAvgStudentTeacherRatio());
        }
        if (toBeUpdated.getIssuesinhighereducationaccessList() != null) {
            // Patch existing children by ID and add new ones; do not replace entire collection
            if (this.getIssuesinhighereducationaccessList() != null) {
                this.getIssuesinhighereducationaccessList().forEach(current -> {
                    toBeUpdated.getIssuesinhighereducationaccessList().forEach(incoming -> {
                        if (Objects.equals(current.getId(), incoming.getId())) {
                            current.patch(incoming);
                        }
                    });
                });
            }
            toBeUpdated.getIssuesinhighereducationaccessList().forEach(incoming -> {
                if (incoming.getId() == null || incoming.getId().equals(0L)) {
                    incoming.setVillageEducationInfrastructure(this);
                    this.getIssuesinhighereducationaccessList().add(incoming);
                }
            });
        }
    }

    public void init() {

    }
}
