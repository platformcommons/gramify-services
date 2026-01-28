package com.platformcommons.platform.service.assessment.domain;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.entity.base.BaseTransactionalEntity;
import com.platformcommons.platform.service.entity.base.DomainEntity;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.springframework.util.CollectionUtils;

@Entity
@Getter
@Setter
@Table(name = "question")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class Question extends BaseTransactionalEntity implements DomainEntity<Question> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @NotNull(message = "questionType must not be null")
    @Column(name = "question_type")
    private String questionType;

    @Column(name = "question_class")
    private String questionClass;

    @NotNull(message = "QuestionSubtype must not be null")
    @Column(name = "question_subtype")
    private String questionSubtype;

    @Column(name = "complexity_level")
    private Long complexityLevel;

    @Column(name = "domain")
    private String domain;

    @Column(name = "frequency_in_days")
    private Long frequencyindays;

    @Column(name = "question_code")
    private String questionCode;

    @Column(name = "response_uom_code")
    private String responseuomcode;

    @Column(name = "tenant")
    private Long tenant;

    @Column(name = "time_to_rectify_in_days")
    private Long timeToRectifyInDays;

    @Column(name = "weight")
    private Double weight;

    @Column(name = "question_meta")
    private String questionMeta;

    @Column(name = "response_validation")
    private String responseValidation;

    @Column(name = "validation_message")
    private String validationMessage;

    @Column(name = "question_imageurl")
    private String questionImageurl;

    @Column(name = "sub_domain")
    private String subDomain;

    @Column(name = "skill_id")
    private Long skill;

    @Column(name = "sub_skill_id")
    private Long subSkill;

    @Column(name = "skill_code")
    private String skillCode;

    @Column(name = "sub_skill_code")
    private String subSkillCode;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Long> ltldSkillList;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "question_name", joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "question_name_id", referencedColumnName = "id", unique = true))
    private Set<MLText> questionName;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "Question_sub_text", joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "question_sub_text_id", referencedColumnName = "id", unique = true))
    private Set<MLText> questionSubText;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL}, orphanRemoval = true)
    @JoinTable(name = "question_text", joinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "question_text_id", referencedColumnName = "id", unique = true))
    private Set<MLText> questionText;

    @Transient
    private boolean isNew;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question")
    @BatchSize(size = 20)
    private Set<DefaultOptions> defaultOptionsList;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question")
    @BatchSize(size = 20)
    private Set<QuestionPaperSet> questionPaperSetList;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "question")
    @BatchSize(size = 20)
    private Set<MtfOption> mtfoptionList;
    @Column(name = "linked_system_type")
    private String linkedSystemType;
    @Column(name = "linked_system_id")
    private String linkedSystemId;
    @Column(name = "duplicated_from")
    private Long duplicatedFrom;
    @Column(name = "duplicated_count")
    private Long duplicatedCount;
    @Column(name = "is_question_modified")
    private Boolean isQuestionModified;

    @Column(name = "is_mandatory")
    private Boolean isMandatory;

    @Column(name = "hint_text")
    private String hintText;

    @Column(name = "option_source_type")
    private String optionSourceType;

    @Column(name = "option_source_value")
    private String optionSourceValue;

    @Column(name = "default_param_for_option_source")
    private String defaultParamForOptionSource;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "question")
    private Set<QuestionConfig> questionConfigs;

    @Transient
    private Map<Long, MLText> questionNameMap;
    @Transient
    private Map<Long, MLText> questionTextMap;
    @Transient
    private Map<Long, MLText> questionSubTextMap;
    @Transient
    private Map<Long, DefaultOptions> defaultOptionsMap;
    @Transient
    private Map<Long, MtfOption> mtfoptionMap;
    @Transient
    private Map<String, Options> leftOptionMap;
    @Transient
    private Map<String, Options> rightOptionMap;


    @Builder
    public Question(String uuid, Long tenantId, Boolean isActive, Long appCreatedTimestamp, Long appLastModifiedTimestamp,
                    Long id, String questionType, String questionClass, String questionSubtype, Long complexityLevel, String domain,
                    Long frequencyindays, String questionCode, String responseuomcode, Long tenant, Long timeToRectifyInDays,
                    Double weight, String questionMeta, String questionImageurl, String subDomain, Long skill, Long subSkill,
                    Set<MLText> questionSubText, Set<MLText> questionName, Set<MLText> questionText, Set<DefaultOptions> defaultOptionsList,
                    Set<QuestionPaperSet> questionPaperSetList, Long duplicatedFrom, Long duplicatedCount, Boolean isQuestionModified,
                    String linkedSystemId, String linkedSystemType, Set<Long> ltldSkillList, Set<MtfOption> mtfoptionList,
                    String validationMessage,String responseValidation,String hintText,
                    String optionSourceType, String optionSourceValue, String defaultParamForOptionSource,
                    Set<QuestionConfig> questionConfigs,String skillCode,String subSkillCode,
                    Boolean isMandatory) {
        super(uuid, tenantId, isActive, appCreatedTimestamp, appLastModifiedTimestamp);
        this.id = id;
        this.questionType = questionType;
        this.questionClass = questionClass;
        this.questionSubtype = questionSubtype;
        this.complexityLevel = complexityLevel;
        this.domain = domain;
        this.frequencyindays = frequencyindays;
        this.questionCode = questionCode;
        this.responseuomcode = responseuomcode;
        this.tenant = tenant;
        this.timeToRectifyInDays = timeToRectifyInDays;
        this.weight = weight;
        this.questionMeta = questionMeta;
        this.questionImageurl = questionImageurl;
        this.subDomain = subDomain;
        this.skill = skill;
        this.subSkill = subSkill;
        this.questionName = questionName;
        this.questionText = questionText;
        this.questionSubText = questionSubText;
        this.defaultOptionsList = defaultOptionsList;
        this.questionPaperSetList = questionPaperSetList;
        this.ltldSkillList = ltldSkillList;

        this.duplicatedFrom = duplicatedFrom;
        this.duplicatedCount = duplicatedCount;
        this.isQuestionModified = isQuestionModified;
        this.isMandatory = isMandatory;

        this.linkedSystemId = linkedSystemId;
        this.linkedSystemType = linkedSystemType;
        if (!CollectionUtils.isEmpty(defaultOptionsList)) {
            this.defaultOptionsList.forEach(defaultOptions -> defaultOptions.setQuestion(this));
        }
        if (!CollectionUtils.isEmpty(questionPaperSetList)) {
            this.questionPaperSetList.forEach(questionPaperSet -> questionPaperSet.setQuestion(this));
        }
        if(!CollectionUtils.isEmpty(this.questionConfigs)){
            this.questionConfigs.forEach(questionConfig -> questionConfig.setQuestion(this));
        }
        this.mtfoptionList = mtfoptionList;
        this.validationMessage = validationMessage;
        this.responseValidation = responseValidation;
        this.hintText = hintText;

        this.optionSourceType = optionSourceType;
        this.optionSourceValue = optionSourceValue;
        this.defaultParamForOptionSource = defaultParamForOptionSource;
        this.questionConfigs = questionConfigs;
        this.skillCode = skillCode;
        this.subSkillCode = subSkillCode;
    }

    public Question init() {
        this.id = null;
        if (isGridType()) {

            final Map<String,Options> leftOptionMap = getLeftOptionMap();
            final Map<String,Options> rightOptionMap = getRightOptionMap();

            for (MtfOption mtfOption : this.getMtfoptionList()) {
                mtfOption.validateOptions();
                Options leftOption  = leftOptionMap.get(mtfOption.getOptionLeft().getUuid());
                Options rightOption = rightOptionMap.get(mtfOption.getOptionRight().getUuid());

                mtfOption.getOptionLeft().setOptionCount(leftOption==null  ? 1L:leftOption.getOptionCount()+1L);
                mtfOption.getOptionRight().setOptionCount(rightOption==null ? 1L:rightOption.getOptionCount()+1L);

                leftOptionMap.put(mtfOption.getOptionLeft().getUuid(), mtfOption.getOptionLeft());
                rightOptionMap.put(mtfOption.getOptionRight().getUuid(), mtfOption.getOptionRight());
            }
            leftOptionMap.forEach((key, value) -> {
                value.init();
                if(value.getOptionCount()!=rightOptionMap.size())
                    throw new InvalidInputException("ERR_SVC_QUE_INVALID_INPUT");
            });
            rightOptionMap.forEach((key, value) -> {
                value.init();
                if(value.getOptionCount()!=leftOptionMap.size())
                    throw new InvalidInputException("ERR_SVC_QUE_INVALID_INPUT");
            });
        }
        if(this.defaultOptionsList!=null){
            this.defaultOptionsList.forEach(defaultOptions -> defaultOptions.init().setQuestion(this));
        }
        if(!CollectionUtils.isEmpty(this.questionConfigs)){
            this.questionConfigs.forEach(questionConfig -> questionConfig.init(this));
        }
        if(this.mtfoptionList!=null){
            this.mtfoptionList.forEach(mtfOption -> {
                mtfOption.init();
                mtfOption.setQuestion(this);
            });
        }
        return this;
    }

    public boolean isGridType() {
        return this.getQuestionType() != null &&
                (this.getQuestionType().equals("QUESTION_TYPE.MULTISELECT_GRID") ||
                        this.getQuestionType().equals("QUESTION_TYPE.SINGLE_SELECT_GRID")) &&
                this.getMtfoptionList() != null;
    }

    public Question patchUpdate(Question toBeUpdated) {
        setIsQuestionModified(true);
        if (toBeUpdated.getQuestionType() != null) setQuestionType(toBeUpdated.getQuestionType());
        if (toBeUpdated.getQuestionClass() != null) setQuestionClass(toBeUpdated.getQuestionClass());
        if (toBeUpdated.getQuestionSubtype() != null) setQuestionSubtype(toBeUpdated.getQuestionSubtype());
        if (toBeUpdated.getComplexityLevel() != null) setComplexityLevel(toBeUpdated.getComplexityLevel());
        if (toBeUpdated.getDomain() != null) setDomain(toBeUpdated.getDomain());
        if (toBeUpdated.getFrequencyindays() != null) setFrequencyindays(toBeUpdated.getFrequencyindays());
        if (toBeUpdated.getQuestionCode() != null) setQuestionCode(toBeUpdated.getQuestionCode());
        if (toBeUpdated.getResponseuomcode() != null) setResponseuomcode(toBeUpdated.getResponseuomcode());
        if (toBeUpdated.getTimeToRectifyInDays() != null) setTimeToRectifyInDays(toBeUpdated.getTimeToRectifyInDays());
        if (toBeUpdated.getWeight() != null) setWeight(toBeUpdated.getWeight());
        if (toBeUpdated.getQuestionMeta() != null) setQuestionMeta(toBeUpdated.getQuestionMeta());
        if (toBeUpdated.getQuestionImageurl() != null) setQuestionImageurl(toBeUpdated.getQuestionImageurl());
        if (toBeUpdated.getSubDomain() != null) setSubDomain(toBeUpdated.getSubDomain());
        if (toBeUpdated.getSkill() != null) setSkill(toBeUpdated.getSkill());
        if (toBeUpdated.getSubSkill() != null) setSubSkill(toBeUpdated.getSubSkill());
        if (toBeUpdated.getQuestionName() != null) {
            Map<Long,MLText> questionNameMap = getQuestionNameMap();
            for (MLText mlText : toBeUpdated.getQuestionName()) {
                if (questionNameMap.containsKey(mlText.getId())) questionNameMap.get(mlText.getId()).patchUpdate(mlText);
                else getQuestionName().add(mlText);
            }
        }
        if(toBeUpdated.getSkillCode()!=null) this.skillCode = toBeUpdated.getSkillCode();
        if(toBeUpdated.getSubSkillCode()!=null) this.subSkillCode = toBeUpdated.getSubSkillCode();
        if (toBeUpdated.getOptionSourceType() != null)  this.optionSourceType = toBeUpdated.getOptionSourceType();
        if (toBeUpdated.getOptionSourceValue() != null) this.optionSourceValue = toBeUpdated.getOptionSourceValue();
        if (toBeUpdated.getQuestionText() != null) {
            Map<Long,MLText> questionTextMap = getQuestionTextMap();
            for (MLText mlText : toBeUpdated.getQuestionText()) {
                if (questionTextMap.containsKey(mlText.getId())) questionTextMap.get(mlText.getId()).patchUpdate(mlText);
                else getQuestionText().add(mlText);
            }
        }
        if (toBeUpdated.getQuestionSubText() != null) {
            Map<Long,MLText> questionSubTextMap = getQuestionSubTextMap();
            for (MLText mlText : toBeUpdated.getQuestionSubText()) {
                if (questionSubTextMap.containsKey(mlText.getId()))
                    questionSubTextMap.get(mlText.getId()).patchUpdate(mlText);
                else {
                    questionSubTextMap.put(mlText.getId(), mlText);
                    getQuestionSubText().add(mlText);
                }
            }
        }
        if (toBeUpdated.getDefaultOptionsList() != null) {
            Map<Long,DefaultOptions> defaultOptionsMap = getDefaultOptionsMap();
            for (DefaultOptions defaultOptions : toBeUpdated.getDefaultOptionsList()) {
                if (defaultOptionsMap.containsKey(defaultOptions.getId()))
                    defaultOptionsMap.get(defaultOptions.getId()).patchUpdate(defaultOptions);
                else {
                    defaultOptions.init().setQuestion(this);
                    getDefaultOptionsList().add(defaultOptions);
                }
            }
        }
        if(toBeUpdated.getLtldSkillList()!=null) setLtldSkillList(toBeUpdated.getLtldSkillList());
        if (toBeUpdated.getDuplicatedFrom() != null) setDuplicatedFrom(toBeUpdated.getDuplicatedFrom());
        if (toBeUpdated.getDuplicatedCount() != null) setDuplicatedCount(toBeUpdated.getDuplicatedCount());
        if (toBeUpdated.getIsQuestionModified() != null) setIsQuestionModified(toBeUpdated.getIsQuestionModified());
        if(toBeUpdated.getResponseValidation()!=null) this.responseValidation  = toBeUpdated.getResponseValidation();
        if(toBeUpdated.getValidationMessage()!=null) this.validationMessage  = toBeUpdated.getValidationMessage();
        if (toBeUpdated.getMtfoptionList() != null && isGridType()) {
            Map<String,Options> leftOptionMap = getLeftOptionMap();
            Map<String,Options> rightOptionMap = getRightOptionMap();
            Map<Long,MtfOption> mtfOptionMap = getMtfoptionMap();

            for (MtfOption mtfOption : getMtfoptionList()) {
                leftOptionMap.put(mtfOption.getUuid(),mtfOption.getOptionLeft());
                rightOptionMap.put(mtfOption.getUuid(),mtfOption.getOptionRight());
            }
            Set<String> uuids = new HashSet<>();
            for (MtfOption mtfOption : toBeUpdated.getMtfoptionList()) {

                mtfOption.validateOptions();

                if(mtfOptionMap.containsKey(mtfOption.getId())) {
                    mtfOptionMap.get(mtfOption.getId()).patchUpdate(mtfOption);
                }
                else {
                    mtfOption.init();
                    getMtfoptionList().add(mtfOption);
                }

                String leftOptionUuid = mtfOption.getOptionLeft().getUuid();
                if(!uuids.contains(leftOptionUuid)){
                    if(leftOptionMap.containsKey(leftOptionUuid))
                        leftOptionMap.get(leftOptionUuid).patchUpdate(mtfOption.getOptionLeft());
                    else
                        leftOptionMap.put(leftOptionUuid,mtfOption.getOptionLeft());
                }
                uuids.add(leftOptionUuid);

                String rightOptionUuid = mtfOption.getOptionRight().getUuid();
                if(!uuids.contains(rightOptionUuid)){
                    if(rightOptionMap.containsKey(rightOptionUuid))
                        rightOptionMap.get(rightOptionUuid).patchUpdate(mtfOption.getOptionRight());
                    else
                        rightOptionMap.put(rightOptionUuid,mtfOption.getOptionRight());
                }
                uuids.add(rightOptionUuid);
            }
        }
        if(toBeUpdated.getHintText()!=null) this.hintText = toBeUpdated.getHintText();
        return this;
    }
    public Long getQuestionDuplicatedFrom() {
        return getIsQuestionModified() ? id : duplicatedFrom;
    }
    public Boolean getIsQuestionModified(){
        return this.isQuestionModified==null || this.isQuestionModified;
    }

    public boolean isQuestionCrossTenant() {
        return !this.getTenantId().equals(PlatformSecurityUtil.getCurrentTenantId());
    }

    public void mapOptionMap(List<Options> options) {
        Map<String,Options> leftOptionMap = getLeftOptionMap();
        Map<String,Options> rightOptionMap = getRightOptionMap();
        for (Options option : options) {
            if(leftOptionMap.containsKey(option.getUuid()))      leftOptionMap.put(option.getUuid(),option);
            else if(rightOptionMap.containsKey(option.getUuid()))rightOptionMap.put(option.getUuid(),option);
            else{
                throw new InvalidInputException("Something went Wrong");
            }
        }
        for (MtfOption mtfOption : getMtfoptionList()) {
            mtfOption.setOptionLeft(leftOptionMap.get(mtfOption.getOptionLeft().getUuid()));
            mtfOption.setOptionRight(rightOptionMap.get(mtfOption.getOptionRight().getUuid()));
        }
    }

    public List<Options> getOptions() {
        List<Options> options = new ArrayList<>(this.getLeftOptionMap().values());
        options.addAll(this.getRightOptionMap().values());
        return options;
    }
    public Map<String,Options> getLeftOptionMap(){
        return this.leftOptionMap = this.leftOptionMap==null?new HashMap<>():this.leftOptionMap;
    }
    public Map<String,Options> getRightOptionMap(){
        return this.rightOptionMap = this.rightOptionMap==null?new HashMap<>():this.rightOptionMap;
    }
    public Map<Long,MLText> getQuestionNameMap(){
        return this.questionNameMap==null?this.questionName.stream().collect(Collectors.toMap(MLText::getId, Function.identity())):this.questionNameMap;
    }
    public Map<Long,MLText> getQuestionTextMap(){
        return this.questionTextMap = this.questionTextMap==null?this.questionText.stream().collect(Collectors.toMap(MLText::getId, Function.identity())):this.questionTextMap;
    }
    public Map<Long,MLText> getQuestionSubTextMap(){
        return this.questionSubTextMap=this.questionSubTextMap==null?this.questionSubText.stream().collect(Collectors.toMap(MLText::getId, Function.identity())):this.questionSubTextMap;
    }
    public Map<Long,DefaultOptions> getDefaultOptionsMap(){
        return this.defaultOptionsMap = this.defaultOptionsMap==null?this.defaultOptionsList.stream().collect(Collectors.toMap(DefaultOptions::getId, Function.identity())):this.defaultOptionsMap;
    }

    public Map<Long,MtfOption> getMtfoptionMap(){
        return this.mtfoptionMap = this.mtfoptionMap==null?this.mtfoptionList.stream().collect(Collectors.toMap(MtfOption::getId, Function.identity())):this.mtfoptionMap;
    }

}