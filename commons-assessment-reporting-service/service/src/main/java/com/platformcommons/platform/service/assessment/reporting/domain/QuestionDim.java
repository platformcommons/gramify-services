package com.platformcommons.platform.service.assessment.reporting.domain;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "question_dim")
@Getter  @Setter
@NoArgsConstructor
public class QuestionDim extends BaseReportEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long createAt;
    private Long createBy;
    private Long questionId;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> skillIds;
    private String subSkillCode;
    private String skillCode;
    private String questionCode;
    private String questionDomain;
    private String questionSubDomain;
    private String questionType;
    private String questionSubtype;
    @Column(columnDefinition = "TEXT")
    private String questionText;
    private Double questionWeight;
    private Long options;
    private Long correctOptions;
    private String language;
    private Long tenantId;
    private String dimType;
    private Long childDefaultOptionId;
    private Long parentQuestionId;

    @Builder
    public QuestionDim(String linkedSystem, Long id, Long createAt, Long createBy, Long questionId, Set<Long> skillIds, String subSkillCode, String skillCode, String questionCode, String questionDomain, String questionSubDomain, String questionType, String questionSubtype, String questionText, Double questionWeight, Long options, Long correctOptions, String language, Long tenantId, String dimType, Long childDefaultOptionId, Long parentQuestionId) {
        super(linkedSystem);
        this.id = id;
        this.createAt = createAt;
        this.createBy = createBy;
        this.questionId = questionId;
        this.skillIds = skillIds;
        this.subSkillCode = subSkillCode;
        this.skillCode = skillCode;
        this.questionCode = questionCode;
        this.questionDomain = questionDomain;
        this.questionSubDomain = questionSubDomain;
        this.questionType = questionType;
        this.questionSubtype = questionSubtype;
        this.questionText = questionText;
        this.questionWeight = questionWeight;
        this.options = options;
        this.correctOptions = correctOptions;
        this.language = language;
        this.tenantId = tenantId;
        this.dimType = dimType;
        this.childDefaultOptionId = childDefaultOptionId;
        this.parentQuestionId = parentQuestionId;
    }

    public QuestionDim init() {
        this.id=0L;
        this.createAt = System.currentTimeMillis();
        this.createBy = PlatformSecurityUtil.getCurrentUserId();
        this.tenantId = PlatformSecurityUtil.getCurrentTenantId();
        return this;
    }
}
