package com.platformcommons.platform.service.assessment.domain;

import java.util.Objects;
import java.util.Set;
import java.math.BigDecimal;
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
import org.hibernate.annotations.Where;

@Entity
@Getter
@Setter
@Table(name = "section_questions")
@Where(clause = "is_active = 1")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class SectionQuestions extends BaseTransactionalEntity implements DomainEntity<SectionQuestions> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_paper_section", nullable = true, updatable = false)
    private QuestionPaperSection questionPaperSection;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "question_id")
    private Long questionId;

    @Column(name = "question_code")
    private String questionCode;

    @Column(name = "question_number")
    private Long questionNumber;

    @Column(name = "tenant")
    private Long tenant;

    @Column(name = "section_question_meta",columnDefinition = "LONGTEXT")
    private String sectionQuestionMeta;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @Column(name = "weight")
    private BigDecimal weight;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "section_questions_description", joinColumns = @JoinColumn(name = "section_questions_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "sec_quest_desc_id", referencedColumnName = "id", unique = true))
    private Set<MLText> secQuestDesc;

    @Transient
    private boolean isNew;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sectionQuestion")
    @BatchSize(size = 20)
    private Set<AiaDefaultResponse> aiaDefaultResponseList;


    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "sectionQuestion")
    @BatchSize(size = 20)
    private Set<QuestionPaperSet> questionPaperSetList;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;
    @Builder
    public SectionQuestions(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                            Long id, QuestionPaperSection questionPaperSection, Long orderNo, Long questionId,
                            Long questionNumber, Long tenant, String sectionQuestionMeta, Boolean isMandatory,
                            BigDecimal weight, Set<MLText> secQuestDesc, Set<AiaDefaultResponse> aiaDefaultResponseList,
                            Set<QuestionPaperSet> questionPaperSetList,String linkedSystemId,String linkedSystemType,String questionCode) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.questionPaperSection = questionPaperSection;
        this.orderNo = orderNo;
        this.questionId = questionId;
        this.questionNumber = questionNumber;
        this.tenant = tenant;
        this.sectionQuestionMeta = sectionQuestionMeta;
        this.isMandatory = isMandatory;
        this.weight = weight;
        this.secQuestDesc = secQuestDesc;
        this.aiaDefaultResponseList = aiaDefaultResponseList;
        this.questionPaperSetList = questionPaperSetList;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType=linkedSystemType;
        this.questionCode = questionCode;
        if (null != aiaDefaultResponseList) {
            this.aiaDefaultResponseList.forEach(it -> it.setSectionQuestion(this));
        }
        if (null != questionPaperSetList) {
            this.questionPaperSetList.forEach(it -> it.setSectionQuestion(this));
        }
    }

    public SectionQuestions init() {
        this.id=null;
        if(Objects.isNull(this.isMandatory)) {
            this.isMandatory = false;
        }
        return this;
    }

    public void patch(SectionQuestions sectionQuestions) {
        if(sectionQuestions.getIsActive()!=null && !sectionQuestions.getIsActive()) {
            deactivate(sectionQuestions.getInactiveReason());
            return;
        }
        if (null != sectionQuestions.getOrderNo()) setOrderNo(sectionQuestions.getOrderNo());
        if (null != sectionQuestions.getQuestionId()) setQuestionId(sectionQuestions.getQuestionId());
        if (null != sectionQuestions.getQuestionNumber()) setQuestionNumber(sectionQuestions.getQuestionNumber());
        if (null != sectionQuestions.getIsMandatory()) setIsMandatory(sectionQuestions.getIsMandatory());
        if (null != sectionQuestions.getWeight()) setWeight(sectionQuestions.getWeight());
    }
}