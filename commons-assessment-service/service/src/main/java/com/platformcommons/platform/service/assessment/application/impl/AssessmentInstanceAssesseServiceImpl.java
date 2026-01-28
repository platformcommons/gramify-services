package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.exception.generic.UnAuthorizedAccessException;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceAssesseService;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceService;
import com.platformcommons.platform.service.assessment.application.OptionsService;
import com.platformcommons.platform.service.assessment.application.*;
import com.platformcommons.platform.service.assessment.application.cache.AssessmentContextCacheManger;
import com.platformcommons.platform.service.assessment.application.constant.AssessmentConfigKeyConstants;
import com.platformcommons.platform.service.assessment.application.constant.ServiceConstants;
import com.platformcommons.platform.service.assessment.application.validator.AssessmentInstanceAssesseValidator;
import com.platformcommons.platform.service.assessment.application.utility.PageUtil;
import com.platformcommons.platform.service.assessment.domain.*;
import com.platformcommons.platform.service.assessment.domain.repo.AssessmentInstanceAssesseNonMTRepository;
import com.platformcommons.platform.service.assessment.domain.repo.AssessmentInstanceAssesseRepository;
import com.platformcommons.platform.service.assessment.dto.AssessmentContextCachedDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseFilterDTO;
import com.platformcommons.platform.service.assessment.exception.UnprocessableEntity;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceAssesseDTOAssembler;
import com.platformcommons.platform.service.dto.base.PageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssessmentInstanceAssesseServiceImpl implements AssessmentInstanceAssesseService {

    private final EntityManager entityManager;
    private final AssessmentInstanceAssesseRepository repository;
    private final AssessmentInstanceService assessmentInstanceService;
    private final AssessmentInstanceAssesseNonMTRepository nonMTRepository;
    private final AssessmentInstanceAssesseDTOAssembler assembler;
    private final AssessmentInstanceAssesseValidator validator;
    private final AssessmentContextCacheManger cacheManger;
    private final SectionQuestionService sectionQuestionService;
    private final DefaultOptionsService defaultOptionsService;
    private final OptionsService optionsService;
    private final OptionsService optionService;


    @Override
    public AssessmentInstanceAssesse createAssessmentInstanceAssesseV3(AssessmentInstanceAssesse assessmentInstanceAssesse) {
        checkAccessibility(assessmentInstanceAssesse);
        validator.validateOnCreate(assessmentInstanceAssesse.init());
        boolean exists = repository.existsByUuid(assessmentInstanceAssesse.getUuid());
        if (exists) {
            throw new UnprocessableEntity(
                    "This AssessmentInstanceAssesse already exists with identifier: "
                            + assessmentInstanceAssesse.getUuid()
            );
        }
        AssessmentInstanceAssesse aia = repository.save(assessmentInstanceAssesse);
        logAIACreated(aia);
        refreshEntities(aia);
        return aia;
    }

    private void refreshEntities(AssessmentInstanceAssesse aia) {
        Set<Long> sectionQuestionIds = new HashSet<>();
        Set<Long> defaultOptionsIds = new HashSet<>();
        Set<Long> options  = new HashSet<>();
        for (AiaDefaultResponse aiaDefaultResponse : aia.getAiadefaultresponseList()) {
            Optional.ofNullable(aiaDefaultResponse.getOptionId()).map(Options::getId).ifPresent(options::add);
            Optional.ofNullable(aiaDefaultResponse.getSectionQuestion()).map(SectionQuestions::getId).ifPresent(sectionQuestionIds::add);
            if(!CollectionUtils.isEmpty(aiaDefaultResponse.getDrobjectiveresponseList())){
                for (DrObjectiveResponse drObjectiveResponse : aiaDefaultResponse.getDrobjectiveresponseList()) {
                    Optional.ofNullable(drObjectiveResponse.getDefaultOption()).map(DefaultOptions::getId).ifPresent(defaultOptionsIds::add);
                }
            }
        }
        Map<Long, SectionQuestions> sectionQuestionsMap = getMap(sectionQuestionService.getSectionQuestions(sectionQuestionIds),SectionQuestions::getId);
        Map<Long, DefaultOptions> defaultOptionsMap = getMap(defaultOptionsService.getDefaultOptionsIds(defaultOptionsIds),DefaultOptions::getId);
        Map<Long, Options> optionsMap = getMap(optionsService.getByIds(options), Options::getId);

        for (AiaDefaultResponse aiaDefaultResponse : aia.getAiadefaultresponseList()) {
            Optional.ofNullable(aiaDefaultResponse.getOptionId())
                    .map(Options::getId)
                    .ifPresent(id -> aiaDefaultResponse.setOptionId(optionsMap.get(id)));
            Optional.ofNullable(aiaDefaultResponse.getSectionQuestion())
                    .map(SectionQuestions::getId)
                    .ifPresent(id -> aiaDefaultResponse.setSectionQuestion(sectionQuestionsMap.get(id)));
            if(!CollectionUtils.isEmpty(aiaDefaultResponse.getDrobjectiveresponseList())){
                for (DrObjectiveResponse drObjectiveResponse : aiaDefaultResponse.getDrobjectiveresponseList()) {
                    Optional.ofNullable(drObjectiveResponse.getDefaultOption())
                            .map(DefaultOptions::getId)
                            .ifPresent(id -> drObjectiveResponse.setDefaultOption(defaultOptionsMap.get(id)));
                }
            }
        }

    }

    private <K,T> Map<K, T> getMap(Collection<T> collection, Function<T,K> function) {
        return collection.stream().collect(Collectors.toMap(function,Function.identity()));
    }

    private void logAIACreated(@NotNull AssessmentInstanceAssesse aia) {
        log.trace("AIA created with id {}", aia.getId());
    }

    @Override
    public Page<AssessmentInstanceAssesse> getAssessmentInstanceAssesse(Long assessmentId, Integer page, Integer size) {
        return nonMTRepository.findByAssessmentId(assessmentId, PageRequest.of(page, size));
    }

    @Override
    public Long getAssessmentInstanceAssesseCount(Long assessmentId) {
        return repository.countByAssessmentInstance_Assessment_Id(assessmentId);
    }

    @Override
    public void deleteAssessmentInstanceAssesse(Long id, String reason) {
        AssessmentInstanceAssesse assesse = repository.findById(id).orElseThrow(() -> new NotFoundException("Assesse not found"));
        assesse.inactivate(reason);
        repository.save(assesse);
    }

    @Override
    public Set<AssessmentInstanceAssesse> getAssessmentInstanceAssesseByAssessedForEntityIds(List<String> assessedForEntityId, String assessedForEntityType, List<Long> assessmentInstanceId) {
        return repository.findByAssessedForEntityIdAndAssessedForEntityTypeAndAssessmentInstance_Ids(assessedForEntityId, assessedForEntityType, assessmentInstanceId);
    }

    @Override
    public Set<AssessmentInstanceAssesse> getAssessmentInstanceAssesseByAssesse(List<String> actorIds, String actorType, List<Long> assessmentInstanceId) {
        return repository.getAssessmentInstanceAssesseByAssesse(actorIds, actorType, assessmentInstanceId);
    }

    @Override
    public AssessmentInstanceAssesse updateAssessmentInstanceAssesseWithResponse(AssessmentInstanceAssesse assessmentInstanceAssesse) {
        AssessmentInstanceAssesse existingAssesse = getAssessmentInstanceAssesseByIdV2(assessmentInstanceAssesse.getId());
        AssessmentContextCachedDTO assessmentContext = cacheManger.getAssessmentContext(assessmentInstanceAssesse.getAssessmentInstance().getId());
        boolean partialUpdateAllowed = Objects.equals(assessmentContext.getConfig().get(AssessmentConfigKeyConstants.PARTIAL_RESPONSE), "1");
        if (!partialUpdateAllowed) {
            throw new UnAuthorizedAccessException("Assesse Updates are not allowed");
        }
        validator.validateOnUpdate(existingAssesse.patch(assessmentInstanceAssesse));
        return nonMTRepository.save(existingAssesse);
    }


    private AssessmentInstanceAssesse getAssessmentInstanceAssesseByIdV2(Long id) {
        return nonMTRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("AssessmentInstanceAssesse not found"));
    }


    @Override
    public AssessmentInstanceAssesse getAssessmentInstanceAssesseById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NotFoundException("AssessmentInstanceAssesse not found"));
    }

    public PageDTO<AssessmentInstanceAssesseDTO> getAssessmentInstanceAssesseByAccessorId(AssessmentInstanceAssesseFilterDTO filterDTO) {
        Long count = nonMTRepository.findByAssessmentInstanceIdAndAssessorCount(
                filterDTO.getInstanceIds(), filterDTO.getAssessorId(),
                filterDTO.getAssessorType(), filterDTO.getAssesseId(),
                filterDTO.getAssesseType(), filterDTO.getSubType(),
                filterDTO.getContext());
        Set<AssessmentInstanceAssesse> assesses = nonMTRepository.findByAssessmentInstanceIdAndAssessor(
                filterDTO.getInstanceIds(), filterDTO.getAssessorId(),
                filterDTO.getAssessorType(), filterDTO.getAssesseId(),
                filterDTO.getAssesseType(), filterDTO.getSubType(),
                filterDTO.getContext(), filterDTO.getPage() * filterDTO.getSize(),
                filterDTO.getSize());
        return PageUtil.getPage(assesses, count, filterDTO.getPage(), filterDTO.getSize(), assembler::toDTO);
    }

    @Override
    public AssessmentInstanceAssesse patchUpdateAssesse(AssessmentInstanceAssesse assessmentInstanceAssesse) {
        AssessmentInstanceAssesse existingAssesse = getAssessmentInstanceAssesseById(assessmentInstanceAssesse.getId());
        if (existingAssesse.getAssessmentTaken() == null || Objects.equals(ServiceConstants.ASSESSMENT_TAKEN_COMPLETE, existingAssesse.getAssessmentTaken())) {
            throw new IllegalStateException("Cannot update! The assessment is already completed.");
        }
        validator.validateOnUpdate(existingAssesse.patch(assessmentInstanceAssesse));
        return repository.save(existingAssesse);
    }


    private void checkAccessibility(AssessmentInstanceAssesse aia) {
//        AssessmentInstanceAccessibilityVO instance = assessmentInstanceService.getAssessmentInstanceAccessibilityVOById(assessmentInstanceAssesse.getAssessmentInstance().getId());
        AssessmentInstance instance = assessmentInstanceService.getAssessmentInstanceByIdv2(aia.getAssessmentInstance().getId());
        if (instance.getScheduleStatus() == null || !(instance.getScheduleStatus().equals(ServiceConstants.PUBLISH) || instance.getScheduleStatus().equals(ServiceConstants.ASSESSMENT_RELEASE))) {
            throw new UnAuthorizedAccessException("ERR_SVC_AIA_NOT_AUTHORIZED");
        }
        aia.setAssessmentInstance(instance);
    }


}