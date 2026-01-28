package com.platformcommons.platform.service.assessment.application.utility.submission;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.application.*;
import com.platformcommons.platform.service.assessment.domain.*;
import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.facade.AssessmentInstanceAssesseFacade;
import com.platformcommons.platform.service.assessment.facade.assembler.*;
import com.platformcommons.platform.service.assessment.messaging.producer.*;
import com.platformcommons.platform.service.entity.common.MLText;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class CGPostSubmissionExecutorTask {

    @Autowired
    @Lazy
    private AssessmentInstanceService assessmentInstanceService;
    @Autowired
    @Lazy
    private AssessmentService assessmentService;
    @Autowired
    @Lazy
    private AssessmentQuestionPaperService assessmentQuestionPaperService;

    @Autowired
    @Lazy
    private QuestionService questionService;

    @Autowired
    @Lazy
    private AssessmentInstanceAssesseFacade assessmentInstanceAssesseFacade;

    @Autowired
    @Lazy
    private DuplicateEntityService duplicateEntityService;


    @Autowired
    @Lazy
    private AssessmentEventProducer assessmentEventProducer;

    @Autowired
    @Lazy
    private AssessmentInstanceEventProducer assessmentInstanceEventProducer;

    @Autowired
    @Lazy
    private AssessmentQuestionPaperEventProducer assessmentQuestionPaperEventProducer;

    @Autowired
    @Lazy
    private AssessmentDTOAssembler assessmentDTOAssembler;

    @Autowired
    @Lazy
    private AssessmentInstanceDTOAssembler assessmentInstanceDTOAssembler;

    @Autowired
    @Lazy
    private AssessmentQuestionPaperDTOAssembler assessmentQuestionPaperDTOAssembler;

    @Transactional
    public void onSubmit(Long assessmentInstanceAssesseId) {
        AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO =
                assessmentInstanceAssesseFacade.getAssessmentInstanceAssesseById(assessmentInstanceAssesseId);
        boolean isDynamicAssessmentCreated = checkIfDynamicAssessmentCreated(assessmentInstanceAssesseDTO.getId());
        if(!isDynamicAssessmentCreated)
            buildTargetDTO(assessmentInstanceAssesseDTO, assessmentInstanceAssesseDTO.getAssessmentInstance().getId());
    }

    private boolean checkIfDynamicAssessmentCreated( Long aiaId) {
       boolean exists =  assessmentInstanceService.getAssessmentInstanceByLinkedSystemIdAndLinkedSystemType(aiaId,
               "CAS.SOURCE_AIA_ID");
       if(exists){
           log.error("\n\n\n\n============>Dynamic assessment already exists for assessment instance id : {}\n\n\n",aiaId);
       }
        return exists;
    }

    @Transactional
    public void buildTargetDTO(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO, Long assessmentInstanceId) {
        AssessmentInstanceDTO sourceAssessmentInstance = assessmentInstanceAssesseDTO.getAssessmentInstance();
        AssessmentDTO sourceAssessment = sourceAssessmentInstance.getAssessment();
        ConfigDTO assessmentConfig = sourceAssessment.getAssessmentConfigs().stream().filter(config
                        -> config.getConfigKey() != null && config.getConfigKey().equals("ACK.TARGET_ASSESSMENT_TEMPLATE_CODE"))
                .findAny().orElse(null);
        String templateCode = assessmentConfig != null ? assessmentConfig.getConfigValue() : null;
        if (templateCode != null) {

            AssessmentQuestionPaper sourceAssessmentQuestionPaper = assessmentQuestionPaperService.
                    getAllAssessmentQuestionPapersByAssessmentId(sourceAssessment.getId()).stream().findFirst().orElseThrow(() ->
                            new NotFoundException("AQP not found for assessment " + sourceAssessment.getId()));

            Map<Long, Question> sourceQuestions = questionService.getQuestionByIdsV3(sourceAssessmentQuestionPaper.getQuestionpapersectionList()
                    .stream()
                    .flatMap(sectionDTO -> sectionDTO.getSectionquestionsList()
                            .stream()
                            .map(SectionQuestions::getQuestionId))
                    .collect(Collectors.toSet())).stream().collect(Collectors.toMap(Question::getId, Function.identity()));

            Assessment templateAssessment = assessmentService.getAssessmentByCode(templateCode);
            AssessmentQuestionPaper templateAssessmentQuestionPaper = assessmentQuestionPaperService.
                    getAllAssessmentQuestionPapersByAssessmentId(templateAssessment.getId()).stream().findFirst().orElseThrow(() ->
                            new NotFoundException("AQP not found for assessment " + templateAssessment.getId()));

            Assessment targetAssessment = addAssessment(sourceAssessment,
                    assessmentInstanceAssesseDTO.getAssessee(), templateCode, templateAssessment.getAssessmentType());
            AssessmentInstance assessmentInstance = addAssessmentInstance(targetAssessment, assessmentInstanceAssesseDTO.getAssessee(),
                    sourceAssessmentInstance, assessmentInstanceAssesseDTO.getId());
            AssessmentQuestionPaper assessmentQuestionPaper = addAssessmentQuestionPaper(assessmentInstanceAssesseDTO,
                    templateAssessmentQuestionPaper, targetAssessment, sourceAssessmentQuestionPaper, sourceQuestions);
            produceKafkaEvent(targetAssessment,assessmentInstance,assessmentQuestionPaper);
            log.error("\n\n==>Dynamic assessment created with id : {} and assessment instance id : {} and assessmentQuestionPaperId :" +
                    " {}", targetAssessment.getId(), assessmentInstance.getId(), assessmentQuestionPaper.getId());
        }
    }

    private void produceKafkaEvent(Assessment targetAssessment, AssessmentInstance assessmentInstance,
                                   AssessmentQuestionPaper assessmentQuestionPaper) {
        assessmentEventProducer.assessmentCreated(assessmentDTOAssembler.toDTO(targetAssessment));
        assessmentInstanceEventProducer.assessmentInstanceCreated(assessmentInstanceDTOAssembler.toDTO(assessmentInstance,
                PlatformSecurityUtil.getCurrentUserLogin()));
        assessmentQuestionPaperEventProducer
                .assessmentQuestionPaperCreated(assessmentQuestionPaperDTOAssembler.toDTO(assessmentQuestionPaper));
    }


    private Assessment addAssessment(AssessmentDTO sourceAssessment, MimicAssessmentActorDTO assessee, String templateCode,
                                     String assessmentType) {
        String assessmentCode = templateCode + "--" + assessee.getActorId() + "--" + sourceAssessment.getAssessmentCode();
        if (assessmentCode.length() > 255) {
            assessmentCode = assessmentCode.substring(0, 255);
        }
        Assessment assessmentTarget = Assessment.builder()
                .assessmentCode(assessmentCode)
                //  .assessmentDesc(buildML(sourceAssessment.getAssessmentName(), assessee.getName()))
                .assessmentName(buildMLDTO(sourceAssessment.getAssessmentDesc(), assessee.getName()))
                .assessmentType(assessmentType)
                .context(sourceAssessment.getContext())
                .domain(sourceAssessment.getDomain())
                .subDomain(sourceAssessment.getSubDomain())
                .tenant(sourceAssessment.getTenant())
                .tenantId(sourceAssessment.getTenantId())
                .totallMarks(0.0)
                .id(null)
                .build();
        return assessmentService.createAssessment(assessmentTarget);
    }




    private AssessmentInstance addAssessmentInstance(Assessment assessmentTarget, MimicAssessmentActorDTO assessee,
                                                     AssessmentInstanceDTO assessmentInstanceSource,
                                                     Long aiaId) {
        AssessmentInstance assessmentInstance = AssessmentInstance.builder()
                .assessment(assessmentTarget)
                .asmtInstName(buildML(assessmentTarget.getAssessmentName(), null))
                //    .asmtInstDesc(buildML(assessmentTarget.getAssessmentDesc(), null))
                .forEntityId(assessee.getActorId())
                .linkedSystemType("CAS.SOURCE_AIA_ID")
                .linkedSystemId(String.valueOf(aiaId))
                .startDate(assessmentInstanceSource.getStartDate())
                .endDate(assessmentInstanceSource.getEndDate())
                .forEntityType(assessee.getActorType())
                .scheduleStatus(Optional.ofNullable(assessmentInstanceSource.getScheduleStatus()).map(MimicRefDataDTO::getCode).orElse(null))
                .tenant(assessmentTarget.getTenant())
                .tenantId(assessmentTarget.getTenantId())
                .id(null)
                .build();
        return assessmentInstanceService.createAssessmentInstance(assessmentInstance);
    }


    private AssessmentQuestionPaper addAssessmentQuestionPaper(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO,
                                                               AssessmentQuestionPaper assessmentQuestionPaperTemplate,
                                                               Assessment targetAssessment,
                                                               AssessmentQuestionPaper sourceAssessmentQuestionPaper,
                                                               Map<Long, Question> sourceQuestions) {

       QuestionPaperSection templateAssessmentDefaultSection =
               assessmentQuestionPaperTemplate.getQuestionpapersectionList().stream().findFirst().get();

        AssessmentQuestionPaper assessmentQuestionPaper = AssessmentQuestionPaper.builder()
                .assessment(targetAssessment)
                .id(null)
                .aqpName(buildML(sourceAssessmentQuestionPaper.getAqpName(), null))
                .questionpapersectionList(buildSectionList(assessmentInstanceAssesseDTO,templateAssessmentDefaultSection
                        , sourceAssessmentQuestionPaper, sourceQuestions))
                .build();
        assessmentQuestionPaper =
                assessmentQuestionPaperService.createAssessmentQuestionPaper(assessmentQuestionPaper);
        return assessmentQuestionPaper;
    }

    private Set<QuestionPaperSection> buildSectionList(AssessmentInstanceAssesseDTO assessmentInstanceAssesseDTO,
                                                       QuestionPaperSection templateAssessmentDefaultSection,
                                                       AssessmentQuestionPaper sourceAssessmentQuestionPaper,
                                                       Map<Long, Question> sourceQuestions) {
        Set<QuestionPaperSection> questionPaperSectionSet = new HashSet<>();
        assessmentInstanceAssesseDTO.getAiadefaultResponseList().forEach(aiaDefaultResponseDTO
                -> {
            for (DrObjectiveResponseDTO drObjectiveResponseDTO : aiaDefaultResponseDTO.getDrobjectiveresponseList()) {
                Map<Long, Long> questionMapOldQuestionDuplicatedQuestion = duplicateQuestions(templateAssessmentDefaultSection);
                Set<MLText> nameData = buildMLForSection(aiaDefaultResponseDTO.getSectionQuestion(),
                        sourceAssessmentQuestionPaper, sourceQuestions,
                        drObjectiveResponseDTO.getDefaultOption().getId());
                try {
                    QuestionPaperSection questionPaperSection = QuestionPaperSection.builder()
                            .id(null)
                            .qpsName(nameData)
                            .orderNo(getOrderNumber(aiaDefaultResponseDTO.getSectionQuestion(), sourceAssessmentQuestionPaper))
                            .sectionquestionsList(buildSectionQuestionList(templateAssessmentDefaultSection,questionMapOldQuestionDuplicatedQuestion))
                            .tenant(PlatformSecurityUtil.getCurrentTenantId())
                            .tenantId(PlatformSecurityUtil.getCurrentTenantId())
                            .build();
                    questionPaperSectionSet.add(questionPaperSection);
                } catch (Exception e) {
                    log.error("Spring wrapped exception", e);
                    if (e.getCause() instanceof TransientPropertyValueException) {
                        TransientPropertyValueException tpve = (TransientPropertyValueException) e.getCause();
                        log.error("Entity: {}, Property: {}", tpve.getTransientEntityName(), tpve.getPropertyName());
                    }
                    throw e;
                }

            }
        });
        return questionPaperSectionSet;
    }

    private Map<Long, Long> duplicateQuestions(QuestionPaperSection questionPaperSectionTemplate) {
        Map<Long, Long> questionMapOldQuestionDuplicatedQuestion = new HashMap<>();
        AtomicReference<SectionQuestions> sectionQuestionsAA = new AtomicReference<>();
        try {
            questionPaperSectionTemplate.getSectionquestionsList().forEach(sectionQuestions -> {
                sectionQuestionsAA.set(sectionQuestions);
                Question question = duplicateEntityService.duplicateQuestion(sectionQuestions.getQuestionId());
                questionMapOldQuestionDuplicatedQuestion.put(sectionQuestions.getQuestionId(), question.getId());
            });
            return questionMapOldQuestionDuplicatedQuestion;
        }catch (Exception ex){
            log.error("Error while duplicating question for section question id : {}", sectionQuestionsAA.get().getId());
            ex.printStackTrace();
        }
        return questionMapOldQuestionDuplicatedQuestion;
    }

    private Long getOrderNumber(SectionQuestionsDTO sectionQuestion, AssessmentQuestionPaper sourceAssessmentQuestionPaper) {
        AtomicReference<Long> sequence = new AtomicReference<>(0L);
        sourceAssessmentQuestionPaper.getQuestionpapersectionList().forEach(questionPaperSection
                -> {
            for (SectionQuestions sectionQuestions : questionPaperSection.getSectionquestionsList()) {
                if (sectionQuestions.getQuestionId().equals(sectionQuestion.getQuestionId())) {
                    sequence.set(questionPaperSection.getOrderNo());
                    break;
                }
            }
        });
        return sequence.get();
    }

    private Set<SectionQuestions> buildSectionQuestionList(QuestionPaperSection questionPaperSectionTemplate,
                                                           Map<Long, Long> questionMapOldQuestionDuplicatedQuestion) {
        Set<SectionQuestions> sectionQuestionsSet = new HashSet<>();
        for (SectionQuestions sectionQuestions : questionPaperSectionTemplate.getSectionquestionsList()) {
            SectionQuestions clone = new SectionQuestions();
            clone.setId(null);
            clone.setQuestionId(questionMapOldQuestionDuplicatedQuestion.get(sectionQuestions.getQuestionId()));
            clone.setOrderNo(sectionQuestions.getOrderNo());
            clone.setIsMandatory(sectionQuestions.getIsMandatory());
            clone.setWeight(sectionQuestions.getWeight());
            clone.setQuestionCode(sectionQuestions.getQuestionCode());
            clone.setTenant(PlatformSecurityUtil.getCurrentTenantId());
            clone.setQuestionNumber(sectionQuestions.getQuestionNumber());
            clone.setSectionQuestionMeta(sectionQuestions.getSectionQuestionMeta());
            sectionQuestionsSet.add(clone);
        }
        return sectionQuestionsSet;
    }

    private Set<MLText> buildMLForSection(SectionQuestionsDTO sectionQuestion,
                                          AssessmentQuestionPaper sourceAssessmentQuestionPaper, Map<Long, Question> sourceQuestions,
                                          @NotNull Long defaultOptionId) {
        Set<MLText> name = new HashSet<>();
        sourceAssessmentQuestionPaper.getQuestionpapersectionList().forEach(questionPaperSection
                -> questionPaperSection.getSectionquestionsList().forEach(sectionQuestions -> {
            if (sectionQuestions.getQuestionId().equals(sectionQuestion.getQuestionId())) {
                Question question = sourceQuestions.get(sectionQuestions.getQuestionId());
                question.getDefaultOptionsList().forEach(defaultOptions -> {
                    if (defaultOptions.getId().equals(defaultOptionId)) {
                        defaultOptions.getOptions().getOptionName().forEach(mlText -> name.add(MLText.builder()
                                .id(null)
                                .languageCode(mlText.getLanguageCode())
                                .text(sectionNameML(mlText.getLanguageCode(), mlText.getText(), questionPaperSection))
                                .build()));
                    }
                });
            }
        }));
        return name;
    }

    private String sectionNameML(String languageCode, String text, QuestionPaperSection questionPaperSection) {
        String sectionName = text;
        MLText sourceSectionName = questionPaperSection.getQpsName().stream().
                filter(mlText -> mlText.getLanguageCode().equals(languageCode)).
                collect(Collectors.toSet()).stream().findFirst().orElse(null);
        if (sourceSectionName != null) {
            sectionName = sourceSectionName.getText() + " - " + text;
        }
        return sectionName;
    }

    private Set<MLText> buildML(Set<MLText> assessmentName, String name) {
        return assessmentName.stream().map(mlText -> MLText.builder()
                .id(null)
                .languageCode(mlText.getLanguageCode())
                .text(name != null ? mlText.getText() + " - " + name : mlText.getText())
                .build()).collect(Collectors.toSet());
    }
    private Set<MLText> buildMLDTO(Set<MimicMLTextDTO> assessmentName, String name) {
        return assessmentName.stream().map(mlText -> MLText.builder()
                .id(null)
                .languageCode(mlText.getLanguage().getCode())
                .text(name != null ? mlText.getText() + " - " + name : mlText.getText())
                .build()).collect(Collectors.toSet());
    }
}
