package com.platformcommons.platform.service.assessment.domain;

import java.util.Set;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.BatchSize;

@Entity
@Getter
@Setter
@Table(name = "qp_sets")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class QPSets extends BaseTransactionalEntity implements DomainEntity<QPSets> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment", nullable = true, updatable = false)
    private Assessment assessment;

    @Column(name = "qpsets_code")
    private String qpsetsCode;

    @Column(name = "tenant")
    private Long tenant;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "qp_sets_name", joinColumns = @JoinColumn(name = "qpsets_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "qp_sets_name_id", referencedColumnName = "id", unique = true))
    private Set<MLText> qpSetsName;

    @Transient
    private boolean isNew;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qpsets")
    @BatchSize(size = 20)
    private Set<AssessmentInstanceAssesse> assessmentInstanceAssesseList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "qPSets")
    @BatchSize(size = 20)
    private Set<QuestionPaperSet> questionPaperSetList;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;
    @Builder
    public QPSets(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Assessment assessment, String qpsetsCode, Long tenant, Set<MLText> qpSetsName, Set<AssessmentInstanceAssesse> assessmentInstanceAssesseList, Set<QuestionPaperSet> questionPaperSetList,String linkedSystemId,String linkedSystemType) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.assessment = assessment;
        this.qpsetsCode = qpsetsCode;
        this.tenant = tenant;
        this.qpSetsName = qpSetsName;
        this.assessmentInstanceAssesseList = assessmentInstanceAssesseList;
        this.questionPaperSetList = questionPaperSetList;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType=linkedSystemType;
    }

}