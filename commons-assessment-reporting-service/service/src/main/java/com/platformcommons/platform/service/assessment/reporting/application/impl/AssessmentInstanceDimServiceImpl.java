package com.platformcommons.platform.service.assessment.reporting.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.assessment.dto.AssessmentDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceDTO;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.reporting.application.*;
import com.platformcommons.platform.service.assessment.reporting.application.constant.ApplicationConstants;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentReportSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.application.context.QuestionSyncContext;
import com.platformcommons.platform.service.assessment.reporting.domain.AssessmentInstanceDim;
import com.platformcommons.platform.service.assessment.reporting.domain.OptionsDim;
import com.platformcommons.platform.service.assessment.reporting.domain.QuestionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.SectionQuestionDim;
import com.platformcommons.platform.service.assessment.reporting.domain.repo.AssessmentInstanceDimRepository;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.AssessmentInstanceDimAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.assembler.UtilityAssembler;
import com.platformcommons.platform.service.assessment.reporting.facade.cache.AssessmentReportSyncContextCache;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class AssessmentInstanceDimServiceImpl implements AssessmentInstanceDimService {

    private final AssessmentInstanceDimRepository repository;

    private final UserContributionDimService userContributionDimService;
    private final SectionQuestionDimService sectionQuestionDimService;
    private final QuestionDimService questionDimService;
    private final OptionsDimService optionsDimService;

    private final AssessmentInstanceDimAssembler assembler;
    private final UtilityAssembler utilityAssembler;
    private final QuestionFactService questionFactService;

    @Value("${commons.platform.report.report-api-v5.tenant:50000}")
    private Long configurableTenantId;

    @Autowired(required = false)
    private AssessmentReportSyncContextCache assessmentReportSyncContextCache;

    @Override
    public void assessmentCreateEvent(AssessmentInstanceDim assessmentInstanceDim) {
        repository.save(assessmentInstanceDim);
    }

    @Override
    public void assessmentInstanceCreateEvent(AssessmentInstanceDTO dto) {
        AssessmentInstanceDim assessmentInstanceDim = assembler.assessmentInstanceDTOtoAssessmentInstanceDim(dto);
        Optional<AssessmentInstanceDim> optional = repository.getByAssessmentId(assessmentInstanceDim.getAssessmentId(), EventContext.getSystemEvent());
        if (!optional.isPresent()) {
            repository.save(assessmentInstanceDim);
            userContributionDimService.createAssessmentContributions(dto.getAssessment());
        } else {
            repository.updateForAssessmentInstanceCreateEvent(assessmentInstanceDim.getAssessmentInstanceId(),
                    assessmentInstanceDim.getAssessmentInstanceCreatedAt(),
                    assessmentInstanceDim.getDuplicatedCount(),
                    assessmentInstanceDim.getDuplicatedFrom(),
                    assessmentInstanceDim.getScheduleStatus(),
                    assessmentInstanceDim.getAssessmentInstanceEndDate(),
                    assessmentInstanceDim.getAssessmentInstanceStartDate(),
                    assessmentInstanceDim.getIsPublic(),
                    assessmentInstanceDim.getIsSpecificVisibility(),
                    assessmentInstanceDim.getMoniteredByActorId(),
                    assessmentInstanceDim.getMoniteredByActorType(),
                    assessmentInstanceDim.getConductedByActorId(),
                    assessmentInstanceDim.getConductedByActorType(),
                    assessmentInstanceDim.getForEntityId(),
                    assessmentInstanceDim.getForEntityType(),
                    assessmentInstanceDim.getAssessmentInstanceName(),
                    assessmentInstanceDim.getAssessmentId(),
                    assessmentInstanceDim.getSequence(),
                    EventContext.getSystemEvent());
        }
    }

    @Override
    public void assessmentQuestionPaperCreate(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        AssessmentInstanceDim dim = assembler.assessmentQuestionPaperDTOtoAssessmentInstanceDim(assessmentQuestionPaperDTO);
        Optional<AssessmentInstanceDim> optional = repository.getByAssessmentId(assessmentQuestionPaperDTO.getAssessment().getId(), EventContext.getSystemEvent());
        if (!optional.isPresent()) {
            repository.save(dim);
            userContributionDimService.createAssessmentContributions(assessmentQuestionPaperDTO.getAssessment());
        } else {
            repository.updateForAssessmentQuestionPaperCreateEvent(
                    dim.getQuestionPaperId(), dim.getQuestionPaperIdCreatedAt(),
                    dim.getQuestionPaperCode(),
                    dim.getQuestionPaperName(),
                    dim.getQuestionPaperDescription(), dim.getTotalWeight(),
                    dim.getResponseCount(), dim.getTotalQuestion(),
                    dim.getAssessmentId(), EventContext.getSystemEvent());
        }
    }

    @Override
    public AssessmentInstanceDim getByAssessmentId(Long id) {
        return repository.findByAssessmentId(id, EventContext.getSystemEvent())
                .orElseThrow(() -> new NotFoundException("Assessment Dim not found"));
    }

    private AssessmentInstanceDim getByAssessmentInstanceId(Long instanceId, Long tenantId) {
        return repository.findByAssessmentInstanceIdAndTenantIdAndLinkedSystem(instanceId, tenantId, EventContext.getSystemEvent())
                .orElseThrow(() -> new NotFoundException("Assessment Dim not found"));
    }

    @Override
    public void assessmentUpdateEvent(AssessmentDTO assessmentDTO) {
        Optional<AssessmentInstanceDim> assessmentInstanceDimOptional = repository.findByAssessmentId(assessmentDTO.getId(), EventContext.getSystemEvent());
        AssessmentInstanceDim assembledDim = assembler.assessmentDTOToAssessmentInstanceDim(assessmentDTO);
        if (assessmentInstanceDimOptional.isPresent()) {
            AssessmentInstanceDim dim = assessmentInstanceDimOptional.get();
            dim.updateForAssessmentEvent(assembledDim);
            repository.save(dim);
        } else {
            assessmentCreateEvent(assembledDim);
        }
    }

    @Override
    public void assessmentQuestionPaperUpdateEvent(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {

        Optional<AssessmentInstanceDim> assessmentInstanceDimOptional = repository.findByAssessmentId(assessmentQuestionPaperDTO.getAssessment().getId(), EventContext.getSystemEvent());
        AssessmentInstanceDim assembledDim = assembler.assessmentQuestionPaperDTOtoAssessmentInstanceDim(assessmentQuestionPaperDTO);
        if (assessmentInstanceDimOptional.isPresent()) {
            AssessmentInstanceDim dim = assessmentInstanceDimOptional.get();
            dim.updateForQuestionPaperEvent(assembledDim);
            repository.save(dim);
        } else {
            assessmentQuestionPaperCreate(assessmentQuestionPaperDTO);
        }
    }

    @Override
    public void assessmentInstanceUpdateEvent(AssessmentInstanceDTO assessmentInstanceDTO) {
        assessmentInstanceCreateEvent(assessmentInstanceDTO);
    }

    @Override
    public void assessmentInstanceDeleteEvent(Long id) {
        repository.deleteByAssessmentInstanceId(id, EventContext.getSystemEvent());
    }

    @Override
    public void syncAssessmentData(AssessmentSyncContext syncContext) {
        repository.deleteByAssessmentId(syncContext.getAssessment().getId(), EventContext.getSystemEvent());
        repository.save(assembler.createAssessmentInstanceDimFromSyncContext(syncContext));
    }

    @Override
    public Set<AssessmentReportSyncContext> getSyncContext(QuestionDTO questionDTO) {
        Set<Long> assessmentIds = questionFactService.getAssessmentIds(questionDTO);
        return assessmentIds.stream()
                .map(this::getSyncContext)
                .collect(Collectors.toSet());
    }

    @Override
    public AssessmentReportSyncContext getSyncContext(Long assessmentId) {
        if (assessmentReportSyncContextCache != null && assessmentReportSyncContextCache.contains(assessmentId)) {
            return assessmentReportSyncContextCache.get(assessmentId);
        } else {
            AssessmentInstanceDim assessmentInstanceDim;
            Set<SectionQuestionDim> sectionQuestionDims;
            Set<QuestionDim> questionDims;
            Set<OptionsDim> optionsDims;

            Set<Long> distinctQuestionIds;
            Optional<AssessmentInstanceDim> assessmentInstanceDims = repository.findByAssessmentId(assessmentId, EventContext.getSystemEvent());
            assessmentInstanceDim = assessmentInstanceDims.orElseThrow(() -> new NotFoundException("Assessment Dim not found"));
            //  ----------------------------------------------------------------------------------------------------- //
            sectionQuestionDims = sectionQuestionDimService.getByAssessmentId(assessmentId);
            distinctQuestionIds = sectionQuestionDims.stream().map(SectionQuestionDim::getQuestionId).collect(Collectors.toSet());

            questionDims = questionDimService.getByQuestionIds(distinctQuestionIds, assessmentInstanceDim);
            optionsDims = optionsDimService.getByQuestionIds(distinctQuestionIds, assessmentInstanceDim);
            optionsDims.forEach(dim -> {
                String childQuestion = dim.getChildQuestion();
                if (childQuestion != null) {
                    for (String childQuestionId : childQuestion.split(ApplicationConstants.PRIMARY_DELIMITER)) {
                        QuestionSyncContext questionSyncContext = getHierarchy(childQuestionId, assessmentInstanceDim.getBaseLanguage(), distinctQuestionIds);
                        questionDims.addAll(questionSyncContext.getQuestionDims());
                        optionsDims.addAll(questionSyncContext.getOptionsDims());
                    }
                }
            });
            //  ------------------------------------------------------------------------------------------------------ //

            AssessmentReportSyncContext context = utilityAssembler.createContext(assessmentInstanceDim, sectionQuestionDims, questionDims, optionsDims);
            if (assessmentReportSyncContextCache != null) assessmentReportSyncContextCache.add(context, assessmentId);
            return context;
        }
    }

    private QuestionSyncContext getHierarchy(String childQuestion, String baseLanguage, Set<Long> distinctQuestionIds) {
        Long questionId = Long.parseLong(childQuestion);
        QuestionDim questionDim = questionDimService.getByQuestionId(Long.parseLong(childQuestion), baseLanguage);
        Set<OptionsDim> optionsDims = optionsDimService.getByQuestionId(Long.parseLong(childQuestion), baseLanguage);
        QuestionSyncContext context = new QuestionSyncContext(null, null);

        if (!distinctQuestionIds.contains(questionId)) {
            context.addQuestionDim(questionDim);
            context.getOptionsDims().addAll(optionsDims);
            distinctQuestionIds.add(questionId);
        }
        optionsDims.forEach(dim -> {
            String childQuestionHierarchy = dim.getChildQuestion();
            if (childQuestionHierarchy != null) {
                for (String childQuestionId1 : childQuestion.split(ApplicationConstants.PRIMARY_DELIMITER)) {
                    QuestionSyncContext questionSyncContext = getHierarchy(childQuestionId1, baseLanguage, distinctQuestionIds);
                    context.getQuestionDims().addAll(questionSyncContext.getQuestionDims());
                    context.getOptionsDims().addAll(questionSyncContext.getOptionsDims());
                }
            }
        });
        return context;
    }

    @Override
    public void updateSyncContext(QuestionDTO questionDTO) {
        Set<Long> assessmentIds = questionFactService.getAssessmentIds(questionDTO);
        assessmentIds.forEach(this::updateSyncContext);
    }

    @Override
    public Set<AssessmentReportSyncContext> getAssessmentReportSyncContextByAssessmentInstanceIds(Set<Long> assessmentInstanceIds) {
        return assessmentInstanceIds.stream()
                .map(assessmentInstanceId -> {
                    return getAssessmentReportSyncContextByAssessmentInstanceId(assessmentInstanceId, configurableTenantId);
                })
                .collect(Collectors.toSet());
    }

    private AssessmentReportSyncContext getAssessmentReportSyncContextByAssessmentInstanceId(Long instanceId, Long tenantId) {
        AssessmentInstanceDim assessmentInstanceDim = getByAssessmentInstanceId(instanceId, tenantId);

        Long assessmentId = assessmentInstanceDim.getAssessmentId();
        if (assessmentReportSyncContextCache != null && assessmentReportSyncContextCache.contains(assessmentId)) {
            return assessmentReportSyncContextCache.get(assessmentId);
        } else {
            Set<SectionQuestionDim> sectionQuestionDims;
            Set<QuestionDim> questionDims;
            Set<OptionsDim> optionsDims;

            Set<Long> distinctQuestionIds;

            sectionQuestionDims = sectionQuestionDimService.getByAssessmentId(assessmentId);
            distinctQuestionIds = sectionQuestionDims.stream().map(SectionQuestionDim::getQuestionId).collect(Collectors.toSet());

            questionDims = questionDimService.getByQuestionIds(distinctQuestionIds, assessmentId, assessmentInstanceDim.getBaseLanguage());
            optionsDims = optionsDimService.getByQuestionIds(distinctQuestionIds, assessmentInstanceDim.getBaseLanguage());
            //  ------------------------------------------------------------------------------------------------------ //

            AssessmentReportSyncContext context = utilityAssembler.createContext(assessmentInstanceDim, sectionQuestionDims, questionDims, optionsDims);
            if (assessmentReportSyncContextCache != null) assessmentReportSyncContextCache.add(context, assessmentId);
            return context;
        }
    }


    @Override
    public AssessmentReportSyncContext getSyncContext(AssessmentDTO assessmentDTO, boolean cacheDisable) {

        if (!cacheDisable && assessmentReportSyncContextCache != null && assessmentReportSyncContextCache.contains(assessmentDTO.getId())) {
            return assessmentReportSyncContextCache.get(assessmentDTO.getId());
        } else {
            AssessmentInstanceDim assessmentInstanceDim;
            Set<SectionQuestionDim> sectionQuestionDims;
            Set<QuestionDim> questionDims;
            Set<OptionsDim> optionsDims;

            Set<Long> distinctQuestionIds;
            Long assessmentId = assessmentDTO.getId();

            //  ----------------------------------------------------------------------------------------------------- //
            sectionQuestionDims = sectionQuestionDimService.getByAssessmentId(assessmentId);
            distinctQuestionIds = sectionQuestionDims.stream().map(SectionQuestionDim::getQuestionId).collect(Collectors.toSet());

            questionDims = questionDimService.getByQuestionIds(distinctQuestionIds, assessmentDTO);
            assessmentInstanceDim = getByAssessmentId(assessmentDTO.getId());
            optionsDims = optionsDimService.getByQuestionIds(distinctQuestionIds, assessmentDTO);
            //  ------------------------------------------------------------------------------------------------------ //

            AssessmentReportSyncContext context = utilityAssembler.createContext(assessmentInstanceDim, sectionQuestionDims, questionDims, optionsDims);
            if (assessmentReportSyncContextCache != null) assessmentReportSyncContextCache.add(context, assessmentId);
            return context;
        }
    }

    @Override
    public void updateSyncContext(Long assessmentId) {
        if (assessmentReportSyncContextCache == null || !assessmentReportSyncContextCache.contains(assessmentId))
            return;
        AssessmentInstanceDim assessmentInstanceDim;
        Set<SectionQuestionDim> sectionQuestionDims;
        Set<QuestionDim> questionDims;
        Set<OptionsDim> optionsDims;
        Set<Long> distinctQuestionIds;
        Optional<AssessmentInstanceDim> assessmentInstanceDims = repository.findByAssessmentId(assessmentId, EventContext.getSystemEvent());
        assessmentInstanceDim = assessmentInstanceDims.orElseThrow(() -> new NotFoundException("Assessment Dim not found"));
        //  ----------------------------------------------------------------------------------------------------- //
        sectionQuestionDims = sectionQuestionDimService.getByAssessmentId(assessmentId);
        distinctQuestionIds = sectionQuestionDims.stream().map(SectionQuestionDim::getQuestionId).collect(Collectors.toSet());
        questionDims = questionDimService.getByQuestionIds(distinctQuestionIds, assessmentInstanceDim);
        optionsDims = optionsDimService.getByQuestionIds(distinctQuestionIds, assessmentInstanceDim);
        //  ------------------------------------------------------------------------------------------------------ //
        AssessmentReportSyncContext context = utilityAssembler.createContext(assessmentInstanceDim, sectionQuestionDims, questionDims, optionsDims);
        assessmentReportSyncContextCache.add(context, assessmentId);
    }

}
