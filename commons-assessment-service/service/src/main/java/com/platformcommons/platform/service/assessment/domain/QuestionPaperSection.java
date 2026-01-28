package com.platformcommons.platform.service.assessment.domain;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
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
@Table(name = "question_paper_section")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Where(clause = "is_active = 1")
public class QuestionPaperSection extends BaseTransactionalEntity implements DomainEntity<QuestionPaperSection> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessment_question_paper", nullable = true, updatable = false)
    private AssessmentQuestionPaper assessmentQuestionPaper;

    @Column(name = "order_no")
    private Long orderNo;

    @Column(name = "tenant")
    private Long tenant;

    @Column(name = "total_marks")
    private Double totalMarks;

    @Column(name = "total_questions")
    private Long totalQuestions;

    @Column(name = "total_questions_to_be_answered")
    private Long totalQuestionsToBeAnswered;

    @Column(name = "section_desc")
    private String sectionDesc;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "question_paper_section_description", joinColumns = @JoinColumn(name = "question_paper_section_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "qps_desc_id", referencedColumnName = "id", unique = true))
    private Set<MLText> qpsDesc;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "question_paper_section_name", joinColumns = @JoinColumn(name = "question_paper_section_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "qps_name_id", referencedColumnName = "id", unique = true))
    private Set<MLText> qpsName;

    @OneToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "question_paper_section_text", joinColumns = @JoinColumn(name = "question_paper_section_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "qps_text_id", referencedColumnName = "id", unique = true))
    private Set<MLText> qpsText;

    @Transient
    private boolean isNew;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "questionPaperSection")
    @BatchSize(size = 20)
    private Set<SectionQuestions> sectionquestionsList;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;
    @Transient
    private Map<Long, SectionQuestions> sectionquestionsMap;

    @Builder
    public QuestionPaperSection(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, AssessmentQuestionPaper assessmentQuestionPaper, Long orderNo, Long tenant, Double totalMarks, Long totalQuestions, Long totalQuestionsToBeAnswered, String sectionDesc, Set<MLText> qpsDesc, Set<MLText> qpsName, Set<MLText> qpsText, Set<SectionQuestions> sectionquestionsList,String linkedSystemId,String linkedSystemType) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.assessmentQuestionPaper = assessmentQuestionPaper;
        this.orderNo = orderNo;
        this.tenant = tenant;
        this.totalMarks = totalMarks;
        this.totalQuestions = totalQuestions;
        this.totalQuestionsToBeAnswered = totalQuestionsToBeAnswered;
        this.sectionDesc = sectionDesc;
        this.qpsDesc = qpsDesc;
        this.qpsName = qpsName;
        this.qpsText = qpsText;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType=linkedSystemType;
        this.sectionquestionsList = sectionquestionsList;
        if (null != sectionquestionsList) {
            this.sectionquestionsList.forEach(it -> it.setQuestionPaperSection(this));
        }
    }

    public void patch(QuestionPaperSection questionPaperSection) {
        if(questionPaperSection.getIsActive()!=null && !questionPaperSection.getIsActive()) {
            deactivate(questionPaperSection.getInactiveReason());
            for (SectionQuestions sectionQuestions : getSectionquestionsList())
                sectionQuestions.deactivate(questionPaperSection.getInactiveReason());
            return;
        }
        if(questionPaperSection.getOrderNo() != null)  setOrderNo(questionPaperSection.getOrderNo());
        if(questionPaperSection.getTotalMarks() != null)  setTotalMarks(questionPaperSection.getTotalMarks());
        if(questionPaperSection.getTotalQuestions() != null)  setTotalQuestions(questionPaperSection.getTotalQuestions());
        if(questionPaperSection.getSectionquestionsList()!=null){
            Map<Long,SectionQuestions> sectionQuestionsMap = getSectionQuestionsMap();
            for(SectionQuestions sectionQuestions : questionPaperSection.getSectionquestionsList()){
                if(sectionQuestionsMap.containsKey(sectionQuestions.getId())){
                    sectionQuestionsMap.get(sectionQuestions.getId()).patch(sectionQuestions);
                }else{
                    sectionQuestions.setQuestionPaperSection(this);
                    getSectionquestionsList().add(sectionQuestions.init());
                }
            }
        }
    }

    private Map<Long, SectionQuestions> getSectionQuestionsMap() {
        return this.sectionquestionsMap = (this.sectionquestionsMap==null) ? this.getSectionquestionsList().stream().collect(Collectors.toMap(SectionQuestions::getId, Function.identity())) : this.sectionquestionsMap;
    }

    public QuestionPaperSection init() {
        this.id = null;
        if(getSectionquestionsList()!=null && !getSectionquestionsList().isEmpty()){
            getSectionquestionsList().forEach(SectionQuestions::init);
        }
        return this;
    }
}