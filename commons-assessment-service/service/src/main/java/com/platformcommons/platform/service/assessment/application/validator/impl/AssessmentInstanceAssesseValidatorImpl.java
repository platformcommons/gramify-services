package com.platformcommons.platform.service.assessment.application.validator.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.service.assessment.application.cache.AssessmentContextCacheManger;
import com.platformcommons.platform.service.assessment.application.constant.ServiceConstants;
import com.platformcommons.platform.service.assessment.application.validator.AssessmentInstanceAssesseValidator;
import com.platformcommons.platform.service.assessment.application.validator.CollectionsValidator;
import com.platformcommons.platform.service.assessment.domain.*;
import com.platformcommons.platform.service.assessment.dto.AssessmentContextCachedDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
// TODO: Added more conscious validation for unexpected data
@RequiredArgsConstructor
@Slf4j
public class AssessmentInstanceAssesseValidatorImpl implements AssessmentInstanceAssesseValidator {

    private final CollectionsValidator collectionsValidator;
    private final AssessmentContextCacheManger cacheManger;

    @Value("${commons.platform.service.commons-assessment-service.validation.assessment-instance-assesse.enable:true}")
    private boolean assessmentInstanceAssesseValidation;


    @Override
    public void validateOnCreate(AssessmentInstanceAssesse aia) {
        if (assessmentInstanceAssesseValidation) {
            validateState(aia);
        }
    }

    @Override
    public void validateOnUpdate(AssessmentInstanceAssesse aia) {
        if (assessmentInstanceAssesseValidation) {
            validateState(aia);
        }
    }


    private void validateState(AssessmentInstanceAssesse aia) {

        boolean nonDraftResponse = Objects.isNull(aia.getAssessmentTaken()) ||
                Objects.equals(ServiceConstants.ASSESSMENT_TAKEN_COMPLETE, aia.getAssessmentTaken());
        if (Objects.isNull(aia.getAssessmentInstance())) {
            throw new InvalidInputException(
                    "[ERR_ASSESSMENT_SUBMIT_MISSING_INSTANCE] Field 'assessmentInstance': The entire object is missing. " +
                            "Fix: Please provide an 'assessmentInstance' object in the payload."
            );
        }
        if (Objects.isNull(aia.getAssessmentInstance().getId())) {
            throw new InvalidInputException(
                    "[ERR_ASSESSMENT_SUBMIT_MISSING_INSTANCE_ID] Field 'assessmentInstance.id': This field is missing or null. " +
                            "Fix: The 'assessmentInstance' object must contain the correct 'id' for the assessment being submitted."
            );
        }
        if (Objects.isNull(aia.getAssessmentInstance().getAssessment())) {
            throw new InvalidInputException(
                    "[ERR_ASSESSMENT_SUBMIT_MISSING_ASSESSMENT_OBJ] Field 'assessmentInstance.assessment': The nested 'assessment' object is missing. " +
                            "Fix: While the server only requires 'assessmentInstance.id', if you are sending the full object, ensure the nested 'assessment' object is also present with id."
            );
        }
        if (Objects.isNull(aia.getAssessmentInstance().getAssessment().getId())) {
            throw new InvalidInputException(
                    "[ERR_ASSESSMENT_SUBMIT_MISSING_ASSESSMENT_ID] Field 'assessmentInstance.assessment.id': The nested 'assessment.id' field is missing. " +
                            "Fix: If providing the nested 'assessment' object, ensure it contains its own 'id'."
            );
        }
        AssessmentContextCachedDTO contextCachedDTO = cacheManger.getAssessmentContext(aia.getAssessmentInstance().getId());
        Set<AiaDefaultResponse> aiaDefaultResponses = aia.getAiadefaultresponseList()
                .stream()
                .filter(aiaDefaultResponse -> !Objects.equals(Boolean.FALSE, aiaDefaultResponse.getIsActive()))
                .collect(Collectors.toSet());
        collectionsValidator.validateCollectionIsEmptyOrNull(aiaDefaultResponses,"[ERR_ASSESSMENT_SUBMIT_NO_RESPONSES] Field 'aiadefaultresponseList': The response list is empty or null. Fix: Please submit an array containing at least one answer.");
        for (AiaDefaultResponse aiaDefaultResponse : aiaDefaultResponses) {
            validate(aiaDefaultResponse, contextCachedDTO);
        }


        Set<Long> mandatorySectionQuestions = contextCachedDTO.getSectionQuestionsMap()
                .entrySet()
                .stream()
                .filter(entry -> Boolean.TRUE.equals(entry.getValue().getIsMandatory()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        if (nonDraftResponse) {

            Set<Long> mandatoryQuestionIds = contextCachedDTO.getSectionQuestionsMap()
                    .values().stream()
                    .filter(q -> Boolean.TRUE.equals(q.getIsMandatory()))
                    .map(QuestionContextCacheDTO::getQuestionId)
                    .collect(Collectors.toSet());

            Set<Long> answeredQuestionIds = aiaDefaultResponses.stream()
                    .filter(aiaDefaultResponse -> !Objects.equals(Boolean.FALSE, aiaDefaultResponse.getIsActive()))
                    .map(this::getQuestionId)
                    .collect(Collectors.toSet());

            mandatoryQuestionIds.removeAll(answeredQuestionIds);

            if (!mandatoryQuestionIds.isEmpty()) {
                throw new InvalidInputException(
                        String.format("[ERR_ASSESSMENT_SUBMIT_MANDATORY_QUESTIONS_MISSING] Mandatory questions are unanswered. " +
                                "Fix: Please provide responses for the following mandatory question IDs: %s.", mandatoryQuestionIds)
                );
            }
        }

        List<Long> allQuestionIds = aiaDefaultResponses
                .stream()
                .map(this::getQuestionId)
                .collect(Collectors.toList());

        Set<Long> duplicateQuestionIds = allQuestionIds.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());

        if (!duplicateQuestionIds.isEmpty()) {
            throw new InvalidInputException(
                    String.format("[ERR_ASSESSMENT_SUBMIT_DUPLICATE_RESPONSES] Each question can only be answered once. " +
                            "The following question IDs were submitted multiple times: %s", duplicateQuestionIds)
            );
        }

    }

    private void validate(AiaDefaultResponse aiaDR, AssessmentContextCachedDTO contextCachedDTO) {
        if(Objects.equals(Boolean.FALSE, aiaDR.getIsActive())){
            return;
        }
        if (inCompleteChildQuestionData(aiaDR)) {
            throw new InvalidInputException(
                    String.format(
                            "[ERR_ASSESSMENT_SUBMIT_INCOMPLETE_CHILD_RESPONSE] Incomplete data for a child question response linked to parent question ID '%d'. " +
                                    "Fix: When responding to a child question, you must provide a value for all three fields: 'childQuestionId', 'childDefaultOptionId', and 'childQuestionParentQuestionId'.",
                            aiaDR.getChildQuestionParentQuestionId()
                    )
            );
        }
        if (isNullSectionQuestion(aiaDR) && isNullChildQuestionData(aiaDR)) {
            throw new InvalidInputException(
                    "[ERR_ASSESSMENT_SUBMIT_INVALID_RESPONSE_TARGET] Ambiguous response target. An object in the 'aiadefaultresponseList' does not specify which question it is for. " +
                            "Fix: Each response object must target a question. Provide either a 'sectionQuestion' object with a valid 'id', OR a complete set of 'childQuestionId', 'childDefaultOptionId', and 'childQuestionParentQuestionId' fields."
            );
        }
        Long questionId = getQuestionId(aiaDR);
        Set<DrObjectiveResponse> drObjectiveResponseSet = Optional.ofNullable(aiaDR.getDrobjectiveresponseList())
                .orElse(new HashSet<>())
                .stream()
                .filter(drObjectiveResponse -> !Objects.equals(Boolean.FALSE, drObjectiveResponse.getIsActive()))
                .collect(Collectors.toSet());
        Set<DrSubjectiveResponse> drSubjectiveResponses = Optional.ofNullable(aiaDR.getDrSubjectiveResponseList())
                .orElse(new HashSet<>())
                .stream()
                .filter(drObjectiveResponse -> !Objects.equals(Boolean.FALSE, drObjectiveResponse.getIsActive()))
                .collect(Collectors.toSet());
        if (CollectionUtils.isEmpty(drObjectiveResponseSet) &&
                CollectionUtils.isEmpty(drSubjectiveResponses) &&
                aiaDR.getOptionId() == null
        ) {
            throw new InvalidInputException(
                    String.format(
                            "[ERR_ASSESSMENT_SUBMIT_EMPTY_RESPONSE] No answer provided for question ID '%d'. " +
                                    "Fix: For each response object, you must provide an answer by setting a value for 'optionId' (for single-choice), or by adding entries to 'drobjectiveresponseList' (for multiple-choice/grid) or 'drsubjectiveresponseList' (for text answers).",
                            questionId
                    )
            );
        }
        QuestionContextCacheDTO questionContextCacheDTO;
        if (childQuestionFieldsAreNull(aiaDR)) {
            if (log.isDebugEnabled()) {
                ObjectMapper mapper = new ObjectMapper();
                log.debug("Validating response for section question id: {}", aiaDR.getSectionQuestion().getId());
                if (contextCachedDTO.getSectionQuestionsMap().containsKey(aiaDR.getSectionQuestion().getId())) {
                    log.debug("Section question found in cache");
                } else {
                    logAiaDefaultResponse(aiaDR);
                    logCacheContext(contextCachedDTO);
                }
            }
            questionContextCacheDTO = contextCachedDTO.getSectionQuestionsMap().get(aiaDR.getSectionQuestion().getId());
        } else {
            if (log.isDebugEnabled()) {
                log.debug("Validating response for child question id response: {}", getQuestionId(aiaDR));
                if (contextCachedDTO.getChildQuestionMap().containsKey(getQuestionId(aiaDR))) {
                    log.debug("Child question found in cache");
                } else {
                    logAiaDefaultResponse(aiaDR);
                    logCacheContext(contextCachedDTO);
                }
            }
            questionContextCacheDTO = contextCachedDTO.getChildQuestionMap().get(getQuestionId(aiaDR));
        }


        if (questionContextCacheDTO == null) {
            throw new InvalidInputException(
                    String.format(
                            "[ERR_ASSESSMENT_SUBMIT_INVALID_QUESTION_ID] Field 'sectionQuestion.id' or 'childQuestionId': The provided question ID '%d' is not part of this assessment. " +
                                    "Fix: Please remove this response or use a question ID that belongs to the current assessment.",
                            questionId
                    )
            );
        }

        validateOptionSelection(aiaDR, questionContextCacheDTO);

    }

    private void logAiaDefaultResponse(AiaDefaultResponse aiaDR) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug("AiaDefaultResponse {}", mapper.writeValueAsString(aiaDR));
        } catch (JsonProcessingException e) {
            log.debug("Error in converting aiaDR to string");
        }
    }

    private void logCacheContext(AssessmentContextCachedDTO contextCachedDTO) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            log.debug("ContextCachedDTO {}", mapper.writeValueAsString(contextCachedDTO));
        } catch (JsonProcessingException e) {
            log.debug("Error in converting contextCachedDTO to string");
        }
        log.debug("Section question not found in cache");
    }

    private boolean inCompleteChildQuestionData(AiaDefaultResponse aiaDR) {
        boolean nullValues = (aiaDR.getChildQuestionId() == null) || (aiaDR.getChildDefaultOptionId() == null) || (aiaDR.getChildQuestionParentQuestionId() == null);
        boolean notNullValues = (aiaDR.getChildQuestionId() != null) || (aiaDR.getChildDefaultOptionId() != null) || (aiaDR.getChildQuestionParentQuestionId() != null);
        return nullValues && notNullValues;
    }

    private void validateOptionSelection(AiaDefaultResponse aiaDR, QuestionContextCacheDTO questionContextCacheDTO) {
        Long questionId = getQuestionId(aiaDR);
        Set<Long> validOptionIds = new HashSet<>();

        if(questionContextCacheDTO.getOptions()!=null){
            validOptionIds = questionContextCacheDTO.getOptions()
                    .stream()
                    .flatMap(opt -> Stream.of(opt.getOptionId(), opt.getDefaultOptionId(), opt.getMtfOptionId()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());
        }
        if (aiaDR.getOptionId() != null && aiaDR.getOptionId().getId() != null) {


            Long submittedOptionId = aiaDR.getOptionId().getId();
            if (!validOptionIds.contains(submittedOptionId)) {
                throw new InvalidInputException(
                        String.format("[ERR_ASSESSMENT_SUBMIT_INVALID_OPTION_ID] Field 'optionId': The provided option ID '%d' is not a valid choice for question ID '%d'. " +
                                "Fix: Please select a valid option from the ones available for this question.", submittedOptionId, questionId));
            }
        }
        Set<DrObjectiveResponse> drObjectiveResponses = Optional.ofNullable(aiaDR.getDrobjectiveresponseList())
                .orElse(new HashSet<>())
                .stream()
                .filter(drObjectiveResponse -> !Objects.equals(Boolean.FALSE, drObjectiveResponse.getIsActive()))
                .collect(Collectors.toSet());;
        if (!CollectionUtils.isEmpty(drObjectiveResponses)) {
            for (DrObjectiveResponse drObjectiveResponse : drObjectiveResponses) {
                Long submittedOptionId = null;
                String fieldName = "";

                if (drObjectiveResponse.getDefaultOption() != null && drObjectiveResponse.getDefaultOption().getId() != null) {
                    submittedOptionId = drObjectiveResponse.getDefaultOption().getId();
                    fieldName = "drobjectiveresponseList[...].defaultOption.id";
                } else if (drObjectiveResponse.getMtfOptionId() != null && drObjectiveResponse.getMtfOptionId().getId() != null) {
                    submittedOptionId = drObjectiveResponse.getMtfOptionId().getId();
                    fieldName = "drobjectiveresponseList[...].mtfOptionId.id";
                }
                if (submittedOptionId == null) {
                    throw new InvalidInputException(String.format("[ERR_ASSESSMENT_SUBMIT_EMPTY_RESPONSE] Field 'drobjectiveresponseList': An object in this list for question ID '%d' is missing an option selection. Fix: Ensure every object in the list contains either a 'defaultOption' with an 'id' or an 'mtfOptionId' with an 'id'.", questionId));
                }
                if (!validOptionIds.contains(submittedOptionId)) {
                    throw new InvalidInputException(String.format("[ERR_ASSESSMENT_SUBMIT_INVALID_OPTION_ID] Field '%s': The provided option ID '%d' is not a valid choice for question ID '%d'. Fix: Please select a valid option from the ones available for this question.", fieldName, submittedOptionId, questionId));
                }
            }
        }

    }

    private boolean isNonNullSectionQuestion(AiaDefaultResponse aiaDR) {
        SectionQuestions sectionQuestion = aiaDR.getSectionQuestion();
        return sectionQuestion != null && sectionQuestion.getQuestionId() != null && !Objects.equals(sectionQuestion.getQuestionId(), 0L);
    }


    private boolean childQuestionFieldsAreNull(AiaDefaultResponse aiaDR) {
        return aiaDR.getChildQuestionId() == null && aiaDR.getChildDefaultOptionId() == null && aiaDR.getChildQuestionParentQuestionId() == null;
    }

    private boolean isNullSectionQuestion(AiaDefaultResponse aiaDR) {
        SectionQuestions sectionQuestion = aiaDR.getSectionQuestion();
        return sectionQuestion == null || sectionQuestion.getQuestionId() == null || Objects.equals(sectionQuestion.getQuestionId(), 0L);
    }

    private boolean isNullChildQuestionData(AiaDefaultResponse aiaDR) {
        return aiaDR.getChildQuestionId() == null || aiaDR.getChildDefaultOptionId() == null || aiaDR.getChildQuestionParentQuestionId() == null;
    }


    private Long getQuestionId(AiaDefaultResponse aiaDR) {
        return Objects.isNull(aiaDR.getChildQuestionId()) ? aiaDR.getSectionQuestion().getQuestionId() : aiaDR.getChildQuestionId();
    }
}
