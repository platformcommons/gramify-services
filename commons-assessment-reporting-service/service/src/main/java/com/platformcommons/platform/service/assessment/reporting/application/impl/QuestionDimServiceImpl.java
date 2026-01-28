package com.platformcommons.platform.service.assessment.reporting.application.impl;


import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.DefaultOptionsDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.QuestionDimService;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.application.utility.ReportServiceSecurityUtil;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.QuestionDimRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.QuestionDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.client.AssessmentClient;
import com.platformcommons.platform.service.assessment.reporting.facade.client.DatasetClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuestionDimServiceImpl implements QuestionDimService {

    private final QuestionDimRepository repository;

    private final QuestionDimAssembler questionDimAssembler;
    private final UtilityAssembler utilityAssembler;

    private final AssessmentClient assessmentClient;
    private final DatasetClient datasetClient;
    @Value("${commons.assessment.service.appKey}")
    private String appKey;

    @Override
    public List<QuestionDim> createQuestionDim(Set<QuestionDim> questionDims) {
        questionDims.forEach(QuestionDim::init);
        return repository.saveAll(questionDims);
    }

    private List<QuestionDim> createQuestionDimForLaggingQuestions(Set<Long> toBeCreatedQuestionDims, Long assessmentId) {
        log.trace("Started Creating Question Dims For {} Question of Assessment Id {}", toBeCreatedQuestionDims.size(), assessmentId);

        Set<QuestionDTO> questionDTOSet = assessmentClient.getQuestionById(toBeCreatedQuestionDims, appKey);
        Set<QuestionDim> dims = questionDTOSet.stream()
                .map(questionDimAssembler::toQuestionDim)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
        if (questionDTOSet.size() != dims.size()) {
            log.warn("DownStream service hasn't served all questions => Requested questions ids {} Fetched QuestionIds {} ", toBeCreatedQuestionDims, questionDTOSet.stream().map(QuestionDTO::getId).collect(Collectors.toSet()));
        }
        log.trace("Completed Creating Question Dims For {} Question of Assessment Id {}", assessmentId, toBeCreatedQuestionDims.size());
        List<QuestionDim> questionDims = createQuestionDim(dims);
        createParentLink(questionDTOSet);
        return questionDims;
    }

    @Override
    public Set<QuestionDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentDTO dto) {
        final String baseLanguage = utilityAssembler.getBaseLanguage(dto);
        return getByQuestionIds(distinctQuestionIds,dto.getId(),baseLanguage);
    }
    @Override
    public Set<QuestionDim> getByQuestionIds(Set<Long> distinctQuestionIds, Long assessmentId, String baseLanguage) {
        Set<QuestionDim> questionDims = repository.findByQuestionIdInAndLanguage(distinctQuestionIds, baseLanguage, EventContext.getSystemEvent());
        Set<Long> questionDimForIds = questionDims.stream().map(QuestionDim::getQuestionId).collect(Collectors.toSet());

        if (questionDimForIds.size() != distinctQuestionIds.size()) {
            questionDims.addAll(
                    createQuestionDimForLaggingQuestions(distinctQuestionIds.stream()
                                    .filter(questionId -> !questionDimForIds.contains(questionId))
                                    .collect(Collectors.toSet()),
                            assessmentId)
                            .stream()
                            .filter(questionDim -> questionDim.getLanguage().equals(baseLanguage))
                            .collect(Collectors.toSet())
            );
        }
        Set<Long> questionIds = questionDims.stream().map(QuestionDim::getQuestionId).collect(Collectors.toSet());
        while(!questionIds.isEmpty()){
            Set<QuestionDim> dims = repository.findByParentQuestionIdIn(questionIds,baseLanguage,EventContext.getSystemEvent());
            questionIds = dims.stream().map(QuestionDim::getQuestionId).collect(Collectors.toSet());
            questionDims.addAll(dims);
        }

        return questionDims;
    }

    @Override
    public void updateQuestionDim(QuestionDTO questionDTO) {
        List<QuestionDim> questionDims = repository.findByQuestionId(questionDTO.getId(), EventContext.getSystemEvent());

        List<QuestionDim> toDelete = new ArrayList<>();
        List<QuestionDim> toUpdate = new ArrayList<>();
        List<QuestionDim> toSave = new ArrayList<>();

        Set<String> languages = questionDTO.getQuestionText().stream().map(el -> el.getLanguage().getCode()).collect(Collectors.toSet());

        questionDims.forEach(questionDim -> {
            if (languages.contains(questionDim.getLanguage())) {
                languages.remove(questionDim.getLanguage());
                questionDimAssembler.updateQuestionDim(questionDTO, questionDim);
                toUpdate.add(questionDim);
            } else {
                toDelete.add(questionDim);
            }
        });

        languages.forEach(language -> toSave.add(questionDimAssembler.toQuestionDim(questionDTO, language)));

        if (!toDelete.isEmpty())
            repository.deleteByIdIn(toDelete.stream().map(QuestionDim::getId).collect(Collectors.toSet()), EventContext.getSystemEvent());
        if (!toUpdate.isEmpty())
            repository.saveAll(toUpdate);
        if (!languages.isEmpty())
            repository.saveAll(toSave);


    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        Set<Long> distinctQuestionIds = syncContext.getQuestionDTOMap().keySet();
        repository.deleteByQuestionIdIn(distinctQuestionIds, EventContext.getSystemEvent());
        Collection<QuestionDTO> questionDTOS = syncContext.getQuestionDTOMap().values();
        Set<QuestionDim> questionDims = questionDTOS.stream()
                .map(questionDimAssembler::toQuestionDim)
                .flatMap(Collection::stream)
                .peek(QuestionDim::init)
                .collect(Collectors.toSet());
        repository.saveAll(questionDims);
        createParentLink(new HashSet<>(questionDTOS));
    }

    @Override
    public Set<QuestionDim> getByQuestionIds(Set<Long> distinctQuestionIds, AssessmentInstanceDim assessmentInstanceDim) {
        final String baseLanguage = utilityAssembler.getBaseLanguage(assessmentInstanceDim.getBaseLanguage());

        Set<QuestionDim> questionDims = repository.findByQuestionIdInAndLanguage(distinctQuestionIds, baseLanguage, EventContext.getSystemEvent());
        Set<Long> questionDimForIds = questionDims.stream().map(QuestionDim::getQuestionId).collect(Collectors.toSet());

        if (questionDimForIds.size() != distinctQuestionIds.size()) {
            questionDims.addAll(
                    createQuestionDimForLaggingQuestions(distinctQuestionIds.stream()
                                    .filter(questionId -> !questionDimForIds.contains(questionId))
                                    .collect(Collectors.toSet()),
                            assessmentInstanceDim.getAssessmentId())
                            .stream()
                            .filter(questionDim -> questionDim.getLanguage().equals(baseLanguage))
                            .collect(Collectors.toSet())
            );
        }
        return questionDims;
    }

    @Override
    public QuestionDim getByQuestionId(Long questionId, String baseLanguage) {
        return repository.findByQuestionIdAndLanguageAndLinkedSystem(questionId, baseLanguage, EventContext.getSystemEvent())
                .orElseThrow(() -> new NotFoundException("Question Dim not Found"));
    }

    @Override
    public void createParentLink(Set<QuestionDTO> questionDTOS) {
        if(questionDTOS==null) return;
        for (QuestionDTO questionDTO : questionDTOS) {
            if (questionDTO.getDefaultOptionsList() != null && !questionDTO.getDefaultOptionsList().isEmpty()) {
                for (DefaultOptionsDTO defaultOption : questionDTO.getDefaultOptionsList()) {
                    if(defaultOption.getChildQuestionList()!=null && !defaultOption.getChildQuestionList().isEmpty())
                        repository.updateParentQuestionIdAndDefaultOptionId(defaultOption.getChildQuestionList(), questionDTO.getId(), defaultOption.getId(),EventContext.getSystemEvent());
                }
            }
        }
    }

    @Override
    public List<Long> getChildQuestions(Long questionId) {
        if(EventContext.getSystemEvent().equals("COMMONS")){

            String params = String.format("QUESTION_ID=%s", questionId);

            List<Map<String,Object>> result;

            String dataset = "COMMONS.ASSESSMENT.GET_CHILD_QUESTIONS";
            if(PlatformSecurityUtil.isPlatformAppToken()){
                result = datasetClient.executeQueryV3AppKey(dataset, params,0,0, ReportServiceSecurityUtil.getAppToken());
            }
            else{
                result = datasetClient.executeQueryV3(dataset, params,0,0, PlatformSecurityUtil.getToken());
            }

            return result.stream().map(map->map.get("questionId"))
                    .map(o ->  Long.parseLong(o.toString()))
                    .distinct()
                    .collect(Collectors.toList());
        }
        else{
            return repository.getSequencedChildQuestions(questionId,EventContext.getSystemEvent());
        }
    }

    @Override
    public void unlink(Long id) {
        repository.unlink(id,EventContext.getSystemEvent());
    }

    @Override
    public Set<QuestionDim> getChildQuestionsDimById(Long questionId) {
        return repository.getChildQuestionsDimById(questionId,EventContext.getSystemEvent());
    }




}
