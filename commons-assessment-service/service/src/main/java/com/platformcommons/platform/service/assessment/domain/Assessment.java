package com.platformcommons.platform.service.assessment.domain;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Entity;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.BatchSize;


@Entity
@Getter
@Setter
@Table(name = "assessment")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Assessment extends BaseTransactionalEntity implements DomainEntity<Assessment> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "assessment_sub_type")
    private String assessmentSubType;

    @Column(name = "assessment_mode")
    private String assessmentMode;

    @Column(name = "assessment_type")
    private String assessmentType;

    @Column(name = "assessment_code")
    private String assessmentCode;

    @Column(name = "assessment_kind")
    private String assessmentKind;

    @Column(name = "assessment_sub_kind")
    private String assessmentSubKind;

    @Column(name = "context")
    private String context;

    @NotNull(message = "Domain must not be null")
    @Column(name = "domain")
    private String domain;

    @Column(name = "frequency")
    private Long frequency;

    @Column(name = "level_code")
    private String levelCode;

    @Column(name = "subject_code")
    private String subjectCode;

    @NotNull(message = "tenant must not be null")
    @Column(name = "tenant")
    private Long tenant;

    @NotNull(message = "totallMarks must not be null")
    @Column(name = "totall_marks")
    private Double totallMarks;

    @Column(name = "is_enabled")
    private Boolean isEnabled;

    @Column(name = "sub_domain")
    private String subDomain;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "assessment_description", joinColumns = @JoinColumn(name = "assessment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "assessment_desc_id", referencedColumnName = "id", unique = true))
    @BatchSize(size = 25)
    private Set<MLText> assessmentDesc;

    @OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinTable(name = "assessment_name", joinColumns = @JoinColumn(name = "assessment_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "assessment_name_id", referencedColumnName = "id", unique = true))
    @BatchSize(size = 25)
    private Set<MLText> assessmentName;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "assessment")
    private Set<AssessmentConfig> assessmentConfigs;

    @Transient
    private boolean isNew;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assessment")
    @BatchSize(size = 5)
    private Set<AssessmentInstance> assessmentInstanceList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "assessment")
    @BatchSize(size = 20)
    private Set<AssessmentQuestionPaper> assessmentQuestionPaperList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "assessment")
    @BatchSize(size = 20)
    private Set<QPSets> qpSetsList;
    @Column(name = "base_language")
    private String baseLanguage;
    @Column(name = "duplicated_from")
    private Long duplicatedFrom;
    @Column(name = "duplicated_count")
    private Long duplicatedCount;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;
    @Transient
    private Map<Long, MLText> assessmentDescMap;
    @Transient
    private Map<Long, MLText> assessmentNameMap;


    @Builder
    public Assessment(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp, Long id, String assessmentSubType, String assessmentMode, String assessmentType, String assessmentCode, String assessmentKind, String assessmentSubKind, String context, String domain, Long frequency, String levelCode, String subjectCode, Long tenant, Double totallMarks, Boolean isEnabled, String subDomain, Set<MLText> assessmentDesc, Set<MLText> assessmentName, Set<AssessmentInstance> assessmentInstanceList, Set<AssessmentQuestionPaper> assessmentQuestionPaperList, Set<QPSets> qpSetsList,String linkedSystemType,String linkedSystemId,String baseLanguage,Long duplicatedCount,Long duplicatedFrom, Set<AssessmentConfig> assessmentConfigs) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.assessmentSubType = assessmentSubType;
        this.assessmentMode = assessmentMode;
        this.assessmentType = assessmentType;
        this.assessmentCode = assessmentCode;
        this.assessmentKind = assessmentKind;
        this.assessmentSubKind=assessmentSubKind;
        this.context = context;
        this.domain = domain;
        this.frequency = frequency;
        this.levelCode = levelCode;
        this.subjectCode = subjectCode;
        this.tenant = tenant;
        this.totallMarks = totallMarks;
        this.isEnabled = isEnabled;
        this.subDomain = subDomain;
        this.assessmentDesc = assessmentDesc;
        this.assessmentName = assessmentName;
        this.assessmentInstanceList = assessmentInstanceList;
        this.assessmentQuestionPaperList = assessmentQuestionPaperList;
        this.qpSetsList = qpSetsList;
        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType = linkedSystemType;
        if (null != assessmentInstanceList) {
            this.assessmentInstanceList.forEach(it -> it.setAssessment(this));
        }
        if (null != assessmentQuestionPaperList) {
            this.assessmentQuestionPaperList.forEach(it -> it.setAssessment(this));
        }
        if (null != qpSetsList) {
            this.qpSetsList.forEach(it -> it.setAssessment(this));
        }
        this.baseLanguage=baseLanguage;
        this.duplicatedFrom=duplicatedFrom;
        this.duplicatedCount=duplicatedCount;
        this.assessmentConfigs=assessmentConfigs;
    }


    public Assessment patch(Assessment assessment) {
        if(assessment.getAssessmentSubType()!=null) setAssessmentSubType(assessment.getAssessmentSubType());
        if(assessment.getAssessmentMode()!=null)    setAssessmentMode(assessment.getAssessmentMode());
        if(assessment.getAssessmentType()!=null)    setAssessmentType(assessment.getAssessmentType());
        if(assessment.getAssessmentCode()!=null)    setAssessmentCode(assessment.getAssessmentCode());
        if(assessment.getAssessmentKind()!=null)    setAssessmentKind(assessment.getAssessmentKind());
        if(assessment.getAssessmentSubKind()!=null)    setAssessmentSubKind(assessment.getAssessmentSubKind());
        if(assessment.getDomain()!=null)            setDomain(assessment.getDomain());
        if(assessment.getFrequency()!=null)         setFrequency(assessment.getFrequency());
        if(assessment.getLevelCode()!=null)         setLevelCode(assessment.getLevelCode());
        if(assessment.getSubjectCode()!=null)       setSubjectCode(assessment.getSubjectCode());
        if(assessment.getTenant()!=null)            setTenant(assessment.getTenant());
        if(assessment.getTotallMarks()!=null)       setTotallMarks(assessment.getTotallMarks());
        if(assessment.getIsEnabled()!=null)         setIsEnabled(assessment.getIsEnabled());
        if(assessment.getSubDomain()!=null)         setSubDomain(assessment.getSubDomain());
        if(assessment.getBaseLanguage()!=null)      setBaseLanguage(assessment.getBaseLanguage());
        if(assessment.getDuplicatedFrom()!=null)    setDuplicatedFrom(assessment.getDuplicatedFrom());
        if(assessment.getDuplicatedCount()!=null)   setDuplicatedCount(assessment.getDuplicatedCount());
        if(assessment.getAssessmentDesc()!=null){
            Map<Long,MLText> descMap = getAssessmentDescMap();
            for (MLText mlText : assessment.getAssessmentDesc()) {
                if(descMap.containsKey(mlText.getId())){
                    descMap.get(mlText.getId()).patchUpdate(mlText);
                }
                else{
                    getAssessmentDesc().add(mlText);
                }
            }
        }
        if(assessment.getAssessmentName()!=null){
            Map<Long,MLText> nameMap = getAssessmentNameMap();
            for (MLText mlText : assessment.getAssessmentName()) {
                if(nameMap.containsKey(mlText.getId())){
                    nameMap.get(mlText.getId()).patchUpdate(mlText);
                }
                else{
                    getAssessmentName().add(mlText);
                }
            }
        }
        if(assessment.getAssessmentConfigs()!=null){
            assessment.checkDuplicateConfigs();
            Map<String,AssessmentConfig> assessmentConfigMap = this.assessmentConfigs.stream()
                                    .collect(Collectors.toMap(AssessmentConfig::getConfigKey,Function.identity()));
            assessment.getAssessmentConfigs().forEach(config ->{
                if(assessmentConfigMap.containsKey(config.getConfigKey())){
                    assessmentConfigMap.get(config.getConfigKey()).setConfigValue(config.getConfigValue());
                }
                else{
                    this.assessmentConfigs.add(config);
                    config.init(this);
                }
            });
        }
        return this;
    }

    private Map<Long, MLText> getAssessmentNameMap() {
        return this.assessmentNameMap=this.assessmentNameMap==null?this.assessmentName.stream().collect(Collectors.toMap(MLText::getId, Function.identity())):this.assessmentNameMap;
    }

    private Map<Long, MLText> getAssessmentDescMap() {
        return this.assessmentDescMap=(this.assessmentDescMap==null?getAssessmentDesc().stream().collect(Collectors.toMap(MLText::getId, Function.identity())):this.assessmentDescMap);
    }

    public Assessment init() {
        this.id = null;
        checkDuplicateConfigs();
        if(this.assessmentConfigs!=null){
            for (AssessmentConfig assessmentConfig : this.assessmentConfigs) {
                assessmentConfig.init(this);
            }
        }
        return this;
    }

    private void checkDuplicateConfigs() {
        if(assessmentConfigs!=null){
            HashSet<String> set = new HashSet<>();
            for (AssessmentConfig assessmentConfig : assessmentConfigs) {
                if(set.contains(assessmentConfig.getConfigKey())){
                    throw new InvalidInputException("Duplicated config key: " + assessmentConfig.getConfigKey());
                }
                set.add(assessmentConfig.getConfigKey());
            }
        }
    }

    public void addAssessmentConfig(AssessmentConfig assessmentConfig) {
        assessmentConfig.init(this);
        if(this.assessmentConfigs==null){
            this.assessmentConfigs=new HashSet<>();
        }
        this.assessmentConfigs.add(assessmentConfig);
    }
}