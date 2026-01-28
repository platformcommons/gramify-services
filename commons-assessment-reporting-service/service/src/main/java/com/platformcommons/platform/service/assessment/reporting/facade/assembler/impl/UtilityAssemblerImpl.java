package com.platformcommons.platform.service.assessment.reporting.facade.assembler.impl;

import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.reporting.application.constant.ApplicationConstants;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionsDim;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;
import com.platformcommons.platform.service.assessment.reporting.dto.AssessmentInstanceDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.OptionsDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.QuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.dto.SectionQuestionDimDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.QuestionDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class UtilityAssemblerImpl implements UtilityAssembler {

    private final String DEFAULT_BASE_LANGUAGE = "ENG";
    private final DecimalFormat decimalFormat = new DecimalFormat("#.##");
    @Autowired
    private QuestionDimAssembler questionDimAssembler;

    public UtilityAssemblerImpl() {
        decimalFormat.setMaximumFractionDigits(2);
    }

    @Override
    public String filterMLTexts(Set<MimicMLTextDTO> assessmentName, String baseLanguage) {
        if (baseLanguage == null) baseLanguage = DEFAULT_BASE_LANGUAGE;
        if(assessmentName==null || assessmentName.isEmpty()) return null;
        String finalBaseLanguage = baseLanguage;
        return assessmentName.stream()
                .filter(mlText -> mlText.getLanguage()
                        .getCode()
                        .equals(finalBaseLanguage))
                .findFirst()
                .map(MimicMLTextDTO::getText)
                .orElse(null);
    }

    @Override
    public String getRefDataCode(MimicRefDataDTO assessmentMode) {
        return assessmentMode == null ? null : assessmentMode.getCode();
    }

    @Override
    public String getActorId(AssessmentInstanceActorDTO monitoredBy) {
        return monitoredBy == null ? null : monitoredBy.getActorId();
    }

    @Override
    public String getActorType(AssessmentInstanceActorDTO monitoredBy) {
        return monitoredBy == null ? null : monitoredBy.getActorType();
    }

    @Override
    public String getBaseLanguage(AssessmentDTO assessment) {
        return assessment.getBaseLanguage() == null ? DEFAULT_BASE_LANGUAGE : assessment.getBaseLanguage();
    }

    @Override
    public Double getTotalWeight(AssessmentQuestionPaperDTO questionPaper) {
        return questionPaper.getQuestionPaperSectionList()
                .stream()
                .mapToDouble(this::getTotalWeight)
                .sum();
    }

    @Override
    public Long getTotalQuestions(AssessmentQuestionPaperDTO questionPaper) {
        return questionPaper.getQuestionPaperSectionList().stream()
                .mapToLong(section -> section.getSectionQuestionsList().size())
                .sum();
    }

    @Override
    public Double getTotalWeight(QuestionPaperSectionDTO questionPaperSectionDTO) {
        AtomicReference<Double> totalWeight = new AtomicReference<>(0.0);
        questionPaperSectionDTO.getSectionQuestionsList().forEach(sectionQuestion -> {
            if (sectionQuestion.getWeight() != null) {
                totalWeight.updateAndGet(v -> v + sectionQuestion.getWeight());
            }
        });
        return totalWeight.get();
    }

    @Override
    public Long getMandatoryQuestion(QuestionPaperSectionDTO questionPaperSectionDTO) {
        return questionPaperSectionDTO.getSectionQuestionsList()
                .stream()
                .filter(sectionQuestionsDTO -> sectionQuestionsDTO.getIsMandatory() == null ? Boolean.FALSE : sectionQuestionsDTO.getIsMandatory())
                .count();
    }

    @Override
    public AssessmentReportSyncContext createContext(AssessmentInstanceDim assessmentInstanceDim, Set<SectionQuestionDim> sectionQuestionDims, Set<QuestionDim> questionDims, Set<OptionsDim> optionsDims) {
        AssessmentInstanceDimDTO assessmentInstanceDimDTO = AssessmentInstanceDimDTO.builder()
                .id(assessmentInstanceDim.getId())
                .assessmentInstanceId(assessmentInstanceDim.getAssessmentInstanceId())
                .assessmentId(assessmentInstanceDim.getAssessmentId())
                .questionPaperId(assessmentInstanceDim.getQuestionPaperId())
                .assessmentCreatedAt(assessmentInstanceDim.getAssessmentCreatedAt())
                .assessmentInstanceCreatedAt(assessmentInstanceDim.getAssessmentInstanceCreatedAt())
                .questionPaperIdCreatedAt(assessmentInstanceDim.getQuestionPaperIdCreatedAt())
                .createdBy(assessmentInstanceDim.getCreatedBy())
                .inActiveAt(assessmentInstanceDim.getInActiveAt())
                .inActiveReason(assessmentInstanceDim.getInActiveReason())
                .isActive(assessmentInstanceDim.getIsActive())
                .assessmentCode(assessmentInstanceDim.getAssessmentCode())
                .context(assessmentInstanceDim.getContext())
                .domain(assessmentInstanceDim.getDomain())
                .assessmentName(assessmentInstanceDim.getAssessmentName())
                .assessmentDesc(assessmentInstanceDim.getAssessmentDesc())
                .subDomain(assessmentInstanceDim.getSubDomain())
                .assessmentMode(assessmentInstanceDim.getAssessmentMode())
                .assessmentType(assessmentInstanceDim.getAssessmentType())
                .assessmentSubtype(assessmentInstanceDim.getAssessmentSubtype())
                .baseLanguage(assessmentInstanceDim.getBaseLanguage())
                .duplicatedCount(assessmentInstanceDim.getDuplicatedCount())
                .duplicatedFrom(assessmentInstanceDim.getDuplicatedFrom())
                .scheduleStatus(assessmentInstanceDim.getScheduleStatus())
                .assessmentInstanceEndDate(assessmentInstanceDim.getAssessmentInstanceEndDate())
                .assessmentInstanceStartDate(assessmentInstanceDim.getAssessmentInstanceStartDate())
                .isPublic(assessmentInstanceDim.getIsPublic())
                .isSpecificVisibility(assessmentInstanceDim.getIsSpecificVisibility())
                .moniteredByActorId(assessmentInstanceDim.getMoniteredByActorId())
                .moniteredByActorType(assessmentInstanceDim.getMoniteredByActorType())
                .conductedByActorId(assessmentInstanceDim.getConductedByActorId())
                .conductedByActorType(assessmentInstanceDim.getConductedByActorType())
                .forEntityId(assessmentInstanceDim.getForEntityId())
                .forEntityType(assessmentInstanceDim.getForEntityType())
                .assessmentInstanceName(assessmentInstanceDim.getAssessmentInstanceName())
                .questionPaperCode(assessmentInstanceDim.getQuestionPaperCode())
                .questionPaperName(assessmentInstanceDim.getQuestionPaperName())
                .questionPaperDescription(assessmentInstanceDim.getQuestionPaperDescription())
                .totalWeight(assessmentInstanceDim.getTotalWeight())
                .responseCount(assessmentInstanceDim.getResponseCount())
                .totalQuestion(assessmentInstanceDim.getTotalQuestion())
                .language(assessmentInstanceDim.getLanguage())
                .tenantId(assessmentInstanceDim.getTenantId())
                .dimType(assessmentInstanceDim.getDimType())
                .build();
        return AssessmentReportSyncContext.builder()
                .assessmentInstanceDim(assessmentInstanceDimDTO)
                .sectionQuestionDims(
                        sectionQuestionDims.stream()
                                .map(sectionQuestionDim -> SectionQuestionDimDTO.builder()
                                        .sectionQuestionId(sectionQuestionDim.getSectionQuestionId())
                                        .questionId(sectionQuestionDim.getQuestionId())
                                        .questionPaperId(sectionQuestionDim.getQuestionPaperId())
                                        .questionPaperSectionId(sectionQuestionDim.getQuestionPaperSectionId())
                                        .assessmentId(sectionQuestionDim.getAssessmentId())
                                        .createdAt(sectionQuestionDim.getCreatedAt())
                                        .createdBy(sectionQuestionDim.getCreatedBy())
                                        .sequence(sectionQuestionDim.getSequence())
                                        .description(sectionQuestionDim.getDescription())
                                        .totalWeight(sectionQuestionDim.getTotalWeight())
                                        .totalQuestions(sectionQuestionDim.getTotalQuestions())
                                        .mandatoryQuestion(sectionQuestionDim.getMandatoryQuestion())
                                        .language(sectionQuestionDim.getLanguage())
                                        .tenantId(sectionQuestionDim.getTenantId())
                                        .dimType(sectionQuestionDim.getDimType())
                                        .build())
                                .collect(Collectors.toSet())
                )
                .questionDims(questionDims.stream()
                        .map(questionDimAssembler::toDTO)
                        .collect(Collectors.toSet()))
                .optionsDims(
                        optionsDims.stream()
                                .map(dim ->
                                        OptionsDimDTO.builder()
                                                .id(dim.getId())
                                                .childQuestion(dim.getChildQuestion())
                                                .defaultOptionId(dim.getDefaultOptionId())
                                                .isCorrect(dim.getIsCorrect())
                                                .optionCode(dim.getOptionCode())
                                                .optionText(dim.getOptionText())
                                                .optionWeight(dim.getOptionWeight())
                                                .optionsId(dim.getOptionsId())
                                                .questionId(dim.getQuestionId())
                                                .tenantId(dim.getTenantId())
                                                .language(dim.getLanguage())
                                                .responseCount(dim.getResponseCount())
                                                .dimType(dim.getDimType())
                                                .createdAt(dim.getCreatedAt())
                                                .createdBy(dim.getCreatedBy())
                                                .mtfOptionId(dim.getMtfOptionId())
                                                .parentQuestionId(dim.getParentQuestionId())
                                                .childDefaultOptionId(dim.getChildDefaultOptionId())
                                                .build())
                                .collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Double getScore(AssessmentInstanceAssesseDTO assesseDTO, AssessmentReportSyncContext context) {
        return assesseDTO.getAiadefaultResponseList()
                .stream()
                .mapToDouble(value -> getScore(value, context))
                .sum();
    }

    @Override
    public Long getCorrectQuestions(AssessmentInstanceAssesseDTO assesseDTO) {
        return assesseDTO.getAiadefaultResponseList()
                .stream()
                .filter(
                        aiaDefaultResponseDTO ->
                                aiaDefaultResponseDTO.getDrobjectiveresponseList() != null &&
                                        !aiaDefaultResponseDTO.getDrobjectiveresponseList().isEmpty() &&
                                        aiaDefaultResponseDTO.getDrobjectiveresponseList()
                                                .stream()
                                                .anyMatch(drObjectiveResponseDTO -> drObjectiveResponseDTO.getDefaultOption() != null &&
                                                        drObjectiveResponseDTO.getDefaultOption().getIsCorrect() != null &&
                                                        drObjectiveResponseDTO.getDefaultOption().getIsCorrect()))
                .mapToLong(value -> value.getSectionQuestion().getId())
                .distinct()
                .count();


    }

    @Override
    public Double getScore(AiaDefaultResponseDTO aiaDefaultResponseDTO, AssessmentReportSyncContext context) {
        if (aiaDefaultResponseDTO.getSectionQuestion() == null) {
            return 0.0;
        }

        QuestionDimDTO questionDim = context.getQuestionDim(aiaDefaultResponseDTO.getSectionQuestion().getQuestionId());
        Double questionWeight = questionDim.getQuestionWeight() == null ? 0.0 : questionDim.getQuestionWeight();
        Double totalOptionWeight = 0.0;

        if (aiaDefaultResponseDTO.getDrobjectiveresponseList() != null && !aiaDefaultResponseDTO.getDrobjectiveresponseList().isEmpty()) {
            totalOptionWeight = aiaDefaultResponseDTO.getDrobjectiveresponseList()
                    .stream()
                    .mapToDouble(drObjectiveResponseDTO ->
                            drObjectiveResponseDTO.getDefaultOption() != null &&
                                    drObjectiveResponseDTO.getDefaultOption().getWeight() != null
                                    ? drObjectiveResponseDTO.getDefaultOption().getWeight()
                                    : 0.0
                    )
                    .sum();
        }

        else if (aiaDefaultResponseDTO.getOptionId() != null) {
            totalOptionWeight = context.getOptionsDims()
                    .stream()
                    .filter(dim -> dim.getOptionsId().equals(aiaDefaultResponseDTO.getOptionId().getId()))
                    .mapToDouble(OptionsDimDTO::getOptionWeight)
                    .filter(Objects::nonNull)
                    .sum();
        }

        double marks = (totalOptionWeight / 100.0) * questionWeight;
        return Double.valueOf(decimalFormat.format(marks));
    }


    @Override
    public Double getTotalWeightSectionQuestionDimDTO(List<SectionQuestionDimDTO> sectionQuestionDims) {
        return sectionQuestionDims.stream().mapToDouble(SectionQuestionDimDTO::getTotalWeight).sum();
    }

    @Override
    public Double getTotalWeightQuestionDimDTO(List<QuestionDimDTO> questionDimDTOS) {
        return questionDimDTOS.stream().filter(Objects::nonNull).mapToDouble(QuestionDimDTO::getQuestionWeight).sum();
    }

    @Override
    public Set<AiaDefaultResponseDTO> getCorrectQuestions(Set<AiaDefaultResponseDTO> responsesForSkill, Map<Long, Double> questionWeightMap) {
        return responsesForSkill.stream()
                .filter(aiaDefaultResponseDTO ->
                        aiaDefaultResponseDTO.getDrobjectiveresponseList() != null &&
                                !aiaDefaultResponseDTO.getDrobjectiveresponseList().isEmpty() &&
                                aiaDefaultResponseDTO.getDrobjectiveresponseList().stream()
                                        .anyMatch(drObjectiveResponseDTO -> {
                                            if (drObjectiveResponseDTO.getDefaultOption() != null &&
                                                    drObjectiveResponseDTO.getDefaultOption().getWeight() != null) {

                                                Double optionWeight = drObjectiveResponseDTO.getDefaultOption().getWeight();
                                                Double questionWeight = questionWeightMap.getOrDefault(
                                                        aiaDefaultResponseDTO.getSectionQuestion().getQuestionId(), 0.0);

                                                double calculatedValue = (optionWeight / 100.0) * questionWeight;
                                                return calculatedValue != 0;
                                            }
                                            return false;
                                        })
                )
                .collect(Collectors.toSet());
    }


    @Override
    public Set<AiaDefaultResponseDTO> getIncorrectQuestions(Set<AiaDefaultResponseDTO> responsesForSkill, Map<Long, Double> questionWeightMap) {
        return responsesForSkill.stream()
                .filter(aiaDefaultResponseDTO ->
                        aiaDefaultResponseDTO.getDrobjectiveresponseList() != null &&
                                !aiaDefaultResponseDTO.getDrobjectiveresponseList().isEmpty() &&
                                aiaDefaultResponseDTO.getDrobjectiveresponseList().stream()
                                        .noneMatch(drObjectiveResponseDTO -> {
                                            if (drObjectiveResponseDTO.getDefaultOption() != null &&
                                                    drObjectiveResponseDTO.getDefaultOption().getWeight() != null) {

                                                Double optionWeight = drObjectiveResponseDTO.getDefaultOption().getWeight();
                                                Double questionWeight = questionWeightMap.getOrDefault(
                                                        aiaDefaultResponseDTO.getSectionQuestion().getQuestionId(), 0.0);

                                                double calculatedValue = (optionWeight / 100.0) * questionWeight;
                                                return calculatedValue != 0;
                                            }
                                            return false;
                                        })
                )
                .collect(Collectors.toSet());
    }


    @Override
    public String getMultiselectResponseIds(AiaDefaultResponseDTO aiaDefaultResponseDTO) {
        return aiaDefaultResponseDTO.getDrobjectiveresponseList() == null || aiaDefaultResponseDTO.getDrobjectiveresponseList().isEmpty() ? null : aiaDefaultResponseDTO.getDrobjectiveresponseList().stream().map(drObjectiveResponseDTO -> drObjectiveResponseDTO.getDefaultOption().getOptions().getId().toString()).collect(Collectors.joining(ApplicationConstants.PRIMARY_DELIMITER));
    }

    @Override
    public String getOptionText(OptionsDTO optionId, AssessmentDTO assessment) {
        return optionId == null ? null : filterMLTexts(optionId.getOptionName(), assessment.getBaseLanguage());
    }

    @Override
    public String getMultiselectResponse(AiaDefaultResponseDTO aiaDefaultResponseDTO, AssessmentDTO assessment) {

        return aiaDefaultResponseDTO.getDrobjectiveresponseList() == null ||
                aiaDefaultResponseDTO.getDrobjectiveresponseList().isEmpty() ? null :
                aiaDefaultResponseDTO.getDrobjectiveresponseList()
                        .stream().map(drObjectiveResponseDTO ->
                                filterMLTexts(drObjectiveResponseDTO.getDefaultOption().getOptions().getOptionName(), getBaseLanguage(assessment)))
                        .collect(Collectors.joining(ApplicationConstants.PRIMARY_DELIMITER));
    }

    @Override
    public String getSubjectiveResponseIds(AiaDefaultResponseDTO aiaDefaultResponseDTO) {
        return aiaDefaultResponseDTO.getDrsubjectiveresponseList() == null ||
                aiaDefaultResponseDTO.getDrsubjectiveresponseList().isEmpty()
                ? null : aiaDefaultResponseDTO.getDrsubjectiveresponseList().stream().map(drSubjectiveResponseDTO -> drSubjectiveResponseDTO.getId().toString()).collect(Collectors.joining(ApplicationConstants.PRIMARY_DELIMITER));
    }

    @Override
    public String getSubjectiveResponses(AiaDefaultResponseDTO aiaDefaultResponseDTO) {
        return aiaDefaultResponseDTO.getDrsubjectiveresponseList() == null ||
                aiaDefaultResponseDTO.getDrsubjectiveresponseList().isEmpty()
                ? null : aiaDefaultResponseDTO.getDrsubjectiveresponseList().stream().map(DrSubjectiveResponseDTO::getResponseText).collect(Collectors.joining(ApplicationConstants.PRIMARY_DELIMITER));
    }

    @Override
    public String getQuestionResponse(String optionsText, String multiselectResponse, String subjectiveResponses) {
        if (optionsText != null) return optionsText;
        if (multiselectResponse != null && subjectiveResponses != null)
            return subjectiveResponses + ApplicationConstants.SECONDARY_DELIMITER + multiselectResponse;
        if (subjectiveResponses != null) return subjectiveResponses;
        return multiselectResponse;
    }

    @Override
    public Long getCorrectQuestions(AiaDefaultResponseDTO aiaDefaultResponseDTO, AssessmentReportSyncContext context) {
        if (aiaDefaultResponseDTO.getDrobjectiveresponseList() != null && !aiaDefaultResponseDTO.getDrobjectiveresponseList().isEmpty())
            return aiaDefaultResponseDTO.getDrobjectiveresponseList().stream()
                    .anyMatch(drObjectiveResponseDTO ->
                            drObjectiveResponseDTO.getDefaultOption()!=null &&
                            drObjectiveResponseDTO.getDefaultOption().getIsCorrect() != null) ? 1L : 0L;
        if (aiaDefaultResponseDTO.getOptionId() != null) {
            return context.getOptionsDims()
                    .stream()
                    .filter(dim -> dim.getOptionsId().equals(aiaDefaultResponseDTO.getOptionId().getId()))
                    .findAny()
                    .filter(dim -> dim.getIsCorrect() != null)
                    .map(OptionsDimDTO::getIsCorrect)
                    .orElse(Boolean.FALSE) ? 1L : 0L;

        }
        return 0L;
    }

    @Override
    public Long getOptionCount(QuestionDTO questionDTO) {
        return questionDTO.getDefaultOptionsList() == null ? 0L : questionDTO.getDefaultOptionsList().size();
    }

    @Override
    public Long getCorrectOptions(QuestionDTO questionDTO) {
        return questionDTO.getDefaultOptionsList() == null ? 0L : questionDTO.getDefaultOptionsList()
                .stream()
                .filter(optionDTO -> optionDTO.getIsCorrect() == null ?
                        Boolean.FALSE :
                        optionDTO.getIsCorrect())
                .count();
    }

    @Override
    public String getChildQuestionId(DefaultOptionsDTO defaultOptionsDTO) {
        return defaultOptionsDTO.getChildQuestionList() == null || defaultOptionsDTO.getChildQuestionList().isEmpty() ? null : defaultOptionsDTO.getChildQuestionList().stream().map(Object::toString).collect(Collectors.joining(ApplicationConstants.PRIMARY_DELIMITER));
    }

    @Override
    public String getLanguageCode(MimicMLTextDTO mimicMLTextDTO) {
        return mimicMLTextDTO.getLanguage() == null ? null : mimicMLTextDTO.getLanguage().getCode();
    }

    @Override
    public String getOptionText(OptionsDTO options, String language) {
        return options.getOptionName() == null ||
                options.getOptionName().isEmpty() ?
                null :
                options.getOptionName()
                        .stream()
                        .filter(mimicMLTextDTO -> language.equals(getLanguageCode(mimicMLTextDTO)))
                        .map(MimicMLTextDTO::getText)
                        .findFirst()
                        .orElse(null);
    }

    @Override
    public String getBaseLanguage(String baseLanguage) {
        return baseLanguage == null ? DEFAULT_BASE_LANGUAGE : baseLanguage;
    }
}
