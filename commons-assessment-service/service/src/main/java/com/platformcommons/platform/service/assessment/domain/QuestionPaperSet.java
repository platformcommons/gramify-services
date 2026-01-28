package com.platformcommons.platform.service.assessment.domain;

import javax.validation.constraints.NotNull;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name = "question_paper_set")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class QuestionPaperSet extends BaseTransactionalEntity implements DomainEntity<QuestionPaperSet> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_question_paper", nullable = true, updatable = false)
    private AssessmentQuestionPaper assessmentQuestionPaper;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "question", nullable = true, updatable = false)
    private Question question;

    @NotNull(message = "sectionQuestion must not be null")
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "section_question", nullable = false, updatable = false)
    private SectionQuestions sectionQuestion;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "qpsets", nullable = true, updatable = false)
    private QPSets qPSets;

    @Column(name = "name")
    private String name;

    @NotNull(message = "questionNumber must not be null")
    @Column(name = "question_number")
    private Long questionNumber;

    @Column(name = "tenant")
    private Long tenant;

    @Transient
    private boolean isNew;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;
    @Builder
    public QuestionPaperSet(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, AssessmentQuestionPaper assessmentQuestionPaper, Question question, SectionQuestions sectionQuestion, QPSets qPSets, String name, Long questionNumber, Long tenant,String linkedSystemId,String linkedSystemType) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.assessmentQuestionPaper = assessmentQuestionPaper;
        this.question = question;
        this.sectionQuestion = sectionQuestion;
        this.qPSets = qPSets;
        this.name = name;
        this.questionNumber = questionNumber;
        this.tenant = tenant;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType=linkedSystemType;
    }

    public void init() {
        this.id = null;
    }

    public void update(QuestionPaperSet toBeUpdated) {
    }
}