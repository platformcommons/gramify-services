package com.platformcommons.platform.service.assessment.domain;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
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
@Table(name = "assessment_question_paper")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class AssessmentQuestionPaper extends BaseTransactionalEntity implements DomainEntity<AssessmentQuestionPaper> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "assessment must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment", nullable = false, updatable = false)
    private Assessment assessment;

    @Column(name = "assessment_question_paper_code")
    private String assessmentQuestionPaperCode;

    @Column(name = "tenant")
    private Long tenant;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "aqp_description", joinColumns = @JoinColumn(name = "assessment_question_paper_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "aqp_desc_id", referencedColumnName = "id", unique = true))
    private Set<MLText> aqpDesc;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "aqp_name", joinColumns = @JoinColumn(name = "assessment_question_paper_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "aqp_name_id", referencedColumnName = "id", unique = true))
    private Set<MLText> aqpName;

    @Transient
    private boolean isNew;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assessmentQuestionPaper")
    @BatchSize(size = 20)
    private Set<QuestionPaperSection> questionpapersectionList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assessmentQuestionPaper")
    @BatchSize(size = 20)
    private Set<QuestionPaperSet> questionPaperSetList;
    @Column(name = "duplicated_from")
    private Long duplicatedFrom;
    @Column(name = "duplicated_count")
    private Long duplicatedCount;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;

    @Transient
    private Map<Long, MLText> aqpDescMap;
    @Transient
    private Map<Long, MLText> aqpNameMap;
    @Transient
    private Map<Long, QuestionPaperSection> questionPaperSectionMap;


    @Builder
    public AssessmentQuestionPaper(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, Assessment assessment, String assessmentQuestionPaperCode, Long tenant, Set<MLText> aqpDesc, Set<MLText> aqpName, Set<QuestionPaperSection> questionpapersectionList, Set<QuestionPaperSet> questionPaperSetList, String linkedSystemId, String linkedSystemType, Long duplicatedCount, Long duplicatedFrom) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.assessment = assessment;
        this.assessmentQuestionPaperCode = assessmentQuestionPaperCode;
        this.tenant = tenant;
        this.aqpDesc = aqpDesc;
        this.aqpName = aqpName;
        this.questionpapersectionList = questionpapersectionList;
        this.questionPaperSetList = questionPaperSetList;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType = linkedSystemType;
        if (null != questionpapersectionList) {
            this.questionpapersectionList.forEach(it -> it.setAssessmentQuestionPaper(this));
        }
        if (null != questionPaperSetList) {
            this.questionPaperSetList.forEach(it -> it.setAssessmentQuestionPaper(this));
        }
        this.duplicatedFrom=duplicatedFrom;
        this.duplicatedCount=duplicatedCount;
    }

    public AssessmentQuestionPaper init() {
        this.id = null;
        if(getQuestionpapersectionList()!=null && !getQuestionpapersectionList().isEmpty())
            getQuestionpapersectionList().forEach(QuestionPaperSection::init);
        return this;
    }

    public void patch(AssessmentQuestionPaper toUpdatedQuestionPaper) {
        if (toUpdatedQuestionPaper.getAssessmentQuestionPaperCode() != null)
            setAssessmentQuestionPaperCode(toUpdatedQuestionPaper.getAssessmentQuestionPaperCode());
        if (toUpdatedQuestionPaper.getAqpDesc() != null) {
            Map<Long, MLText> questionPaperDescMap = getAqpDescMap();
            toUpdatedQuestionPaper.getAqpDesc().forEach(mlText -> {
                if (questionPaperDescMap.containsKey(mlText.getId())) {
                    questionPaperDescMap.get(mlText.getId()).patchUpdate(mlText);
                } else {
                    questionPaperDescMap.put(mlText.getId(), mlText);
                }
            });
        }
        if (toUpdatedQuestionPaper.getAqpName() != null) {
            Map<Long, MLText> questionPaperNameMap = getAqpNameMap();
            for (MLText mlText : toUpdatedQuestionPaper.getAqpName()) {
                if (questionPaperNameMap.containsKey(mlText.getId())) {
                    questionPaperNameMap.get(mlText.getId()).patchUpdate(mlText);
                } else {
                    questionPaperNameMap.put(mlText.getId(), mlText);
                }
            }
        }
        if(toUpdatedQuestionPaper.getQuestionpapersectionList()!=null){
            final Map<Long, QuestionPaperSection> questionPaperSectionMap = getQuestionPaperSectionMap();
            for (QuestionPaperSection questionPaperSection : toUpdatedQuestionPaper.getQuestionpapersectionList()) {
                if (questionPaperSectionMap.containsKey(questionPaperSection.getId())) {
                    questionPaperSectionMap.get(questionPaperSection.getId()).patch(questionPaperSection);
                } else {
                    questionPaperSection.setAssessmentQuestionPaper(this);
                    getQuestionpapersectionList().add(questionPaperSection.init());
                }
            }
        }
    }

    private Map<Long, MLText> getAqpDescMap() {
        return this.aqpDescMap=this.aqpDescMap==null?getAqpDesc().stream().collect(Collectors.toMap(MLText::getId, Function.identity())):this.aqpDescMap;
    }

    private Map<Long, MLText> getAqpNameMap() {
        return this.aqpNameMap=this.aqpNameMap==null?getAqpName().stream().collect(Collectors.toMap(MLText::getId, Function.identity())):this.aqpNameMap;
    }

    private Map<Long, QuestionPaperSection> getQuestionPaperSectionMap() {
        return this.questionPaperSectionMap=this.questionPaperSectionMap==null?getQuestionpapersectionList().stream().collect(Collectors.toMap(QuestionPaperSection::getId, Function.identity())):this.questionPaperSectionMap;
    }

}