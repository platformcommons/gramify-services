package com.platformcommons.platform.service.assessment.application.impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.assessment.application.*;
import com.platformcommons.platform.service.assessment.domain.*;
import com.platformcommons.platform.service.assessment.domain.repo.*;
import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentQuestionPaperDTOAssembler;
import com.platformcommons.platform.service.assessment.facade.assembler.QuestionDTOAssembler;
import com.platformcommons.platform.service.assessment.messaging.producer.AssessmentEventProducer;
import com.platformcommons.platform.service.assessment.messaging.producer.AssessmentInstanceEventProducer;
import com.platformcommons.platform.service.assessment.messaging.producer.AssessmentQuestionPaperEventProducer;
import com.platformcommons.platform.service.assessment.messaging.producer.QuestionEventProducer;
import com.platformcommons.platform.service.dto.commons.RefDataDTO;
import com.platformcommons.platform.service.entity.common.MLText;
import com.platformcommons.platform.service.facade.validator.RefDataDTOValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DuplicateEntityServiceImpl implements DuplicateEntityService {

    private final AssessmentInstanceDTOAssembler assessmentInstanceDTOAssembler;
    private final AssessmentDTOAssembler assessmentDTOAssembler;
    private final AssessmentQuestionPaperDTOAssembler assessmentQuestionPaperDTOAssembler;
    private final QuestionDTOAssembler questionDTOAssembler;

    private final AssessmentInstanceService instanceService;
    private final QuestionService questionService;
    private final AssessmentQuestionPaperService questionPaperService;
    private final AssessmentService assessmentService;

    private final AssessmentQuestionPaperNonMTRepository questionPaperRepository;
    private final QuestionNonMTRepository questionNonMTRepository;
    private final AssessmentNonMTRepository assessmentNonMTRepository;
    private final AssessmentInstanceNontMTRepository notMTRepository;
    private final QuestionRepository questionRepository;

    private final AssessmentEventProducer assessmentEventProducer;
    private final AssessmentInstanceEventProducer assessmentInstanceEventProducer;
    private final AssessmentQuestionPaperEventProducer questionPaperEventProducer;
    private final QuestionEventProducer questionEventProducer;
    private final RefDataDTOValidator refDataDTOValidator;

    @Override
    public AssessmentInstanceDTO duplicateAssessmentInstance(Long assessmentInstanceId, String name) {
        AssessmentInstance assessmentInstance = instanceService.getAssessmentInstanceByIdv2(assessmentInstanceId);
        Set<AssessmentQuestionPaper> questionPapers = questionPaperRepository.getAllAssessmentQuestionPapersOfInstance(assessmentInstanceId);

        Assessment assessment=assessmentService.createAssessment(duplicateAssessment(assessmentInstance.getAssessment(),name));
        assessmentEventProducer.assessmentCreated(assessmentDTOAssembler.toDTO(assessment));

        Long createdAssessmentId=assessment.getId();


        HashMap<Long, Long> questionIdMap = duplicateAssessmentInstanceQuestions(questionPapers);
        questionPapers.forEach(paper->{
            AssessmentQuestionPaper assessmentQuestionPaper=questionPaperService.createAssessmentQuestionPaper(duplicateAssessmentQuestionPaper(paper,createdAssessmentId,name,questionIdMap));
            questionPaperEventProducer.assessmentQuestionPaperCreated(assessmentQuestionPaperDTOAssembler.toDTO(assessmentQuestionPaper));
        });

        AssessmentInstance instance= instanceService.createAssessmentInstance(copyAssessmentInstance(assessmentInstance,createdAssessmentId,name));
        assessmentInstanceEventProducer.assessmentInstanceCreated(assessmentInstanceDTOAssembler.toDTO(instance,null));
        AssessmentInstanceDTO instanceDTO = assessmentInstanceDTOAssembler.toDTO(instance,null);

        increaseDuplicateCount(assessmentInstanceId);
        return instanceDTO;
    }
    @Override
    public Question duplicateQuestion(Long questionId) {

        Question question = questionNonMTRepository.findById(questionId).orElseThrow(()->new NotFoundException("Question Not Found"));
        boolean crossTenant = question.isQuestionCrossTenant();
        questionNonMTRepository.updateQuestionCount(question.getQuestionDuplicatedFrom(),1L);
        if(crossTenant){
            List<Question> questionOptional = questionRepository.getQuestionExistenceInTenancy(question.getId(), PageRequest.of(0,1));
            crossTenant =  questionOptional.isEmpty();
        }
        Question duplicatedQuestion = duplicateQuestion(question,crossTenant);
        questionEventProducer.questionCreated(questionDTOAssembler.toDTO(duplicatedQuestion));
        return duplicatedQuestion;
    }
    private AssessmentInstance copyAssessmentInstance(AssessmentInstance assessmentInstance, Long assessmentId,String name) {
        String baseLanguage=assessmentInstance.getAssessment().getBaseLanguage()==null ? "ENG": assessmentInstance.getAssessment().getBaseLanguage();

        AssessmentInstance instance = AssessmentInstance.builder()
                .id(0L)
                .assessment(Assessment.builder().id(assessmentId).build())
                .conductedBy(renewAssessmentInstanceActor(assessmentInstance.getConductedBy()))
                .monitoredBy(renewAssessmentInstanceActor(assessmentInstance.getMonitoredBy()))
                .startDate(assessmentInstance.getStartDate())
                .endDate(assessmentInstance.getEndDate())
                .forEntityType(assessmentInstance.getForEntityType())
                .forEntityId(assessmentInstance.getForEntityId())
                .duration(assessmentInstance.getDuration())
                .school(assessmentInstance.getSchool())
                .testNumber(assessmentInstance.getTestNumber())
                .testType(assessmentInstance.getTestType())
                .academicSession(assessmentInstance.getAcademicSession())
                .scheduleStatus(refDataDTOValidator.fromDTO(RefDataDTO.builder().code("ASSESSMENT.DRAFT").build()))
                .confirmationDate(assessmentInstance.getConfirmationDate())
                .tenant(assessmentInstance.getTenant())
                .imageURL(assessmentInstance.getImageURL())
                .asmtInstDesc(renewMltext(assessmentInstance.getAsmtInstDesc()))
                .asmtInstName(Collections.singleton(MLText.builder().id(0L)
                        .text(name)
                        .languageCode(baseLanguage)
                        .build()))
                .isPublic(assessmentInstance.getIsPublic())
                .duplicatedFrom(assessmentInstance.getId())
                .duplicatedCount(0L)
                .build();
        Set<MLText> m = renewMltext(filterMltext(instance.getAsmtInstName(),baseLanguage));
        m.addAll(instance.getAsmtInstName());
        instance.setAsmtInstName(m);
        return instance;
    }
    private Assessment duplicateAssessment(Assessment assessment,String name) {
        String baseLanguage=assessment.getBaseLanguage()==null ? "ENG": assessment.getBaseLanguage();
        Assessment duplicateAssessment = Assessment.builder()
                .id(0L)
                .domain(assessment.getDomain())
                .subDomain(assessment.getSubDomain())
                .assessmentCode(assessment.getAssessmentCode())
                .assessmentName(Collections.singleton(MLText.builder().id(0L)
                        .text(name)
                        .languageCode(baseLanguage)
                        .build()))
                .assessmentType(assessment.getAssessmentType())
                .tenant(assessment.getTenant())
                .assessmentDesc(renewMltext(assessment.getAssessmentDesc()))
                .assessmentMode(assessment.getAssessmentMode())
                .assessmentSubType(assessment.getAssessmentSubType())
                .context(assessment.getContext())
                .frequency(assessment.getFrequency())
                .levelCode(assessment.getLevelCode())
                .subjectCode(assessment.getSubjectCode())
                .frequency(assessment.getFrequency())
                .totallMarks(assessment.getTotallMarks())
                .duplicatedFrom(assessment.getId())
                .duplicatedCount(0L)
                .build();
        Set<MLText> m = renewMltext(filterMltext(assessment.getAssessmentName(),baseLanguage));
        m.addAll(duplicateAssessment.getAssessmentName());

        duplicateAssessment.setAssessmentName(m);
        return duplicateAssessment;
    }
    private AssessmentQuestionPaper duplicateAssessmentQuestionPaper(AssessmentQuestionPaper paper, Long assessmentId, String name, HashMap<Long, Long> questionIdMap) {

        String baseLanguage=paper.getAssessment().getBaseLanguage()==null ? "ENG": paper.getAssessment().getBaseLanguage();
        AssessmentQuestionPaper assessmentQuestionPaper = AssessmentQuestionPaper.builder()
                .id(0L)
                .assessment(Assessment.builder().id(assessmentId).build())
                .tenant(paper.getTenant())
                .assessmentQuestionPaperCode(paper.getAssessmentQuestionPaperCode())
                .aqpDesc(renewMltext(paper.getAqpDesc()))
                .aqpName(Collections.singleton(MLText.builder().id(0L)
                        .text(name)
                        .languageCode(baseLanguage)
                        .build()))
                .questionpapersectionList(paper.getQuestionpapersectionList().stream().map(el->duplicateQuestionPaperSection(el,questionIdMap)).collect(Collectors.toCollection(LinkedHashSet::new)))
                .duplicatedFrom(paper.getId())
                .duplicatedCount(0L)
                .build();

        Set<MLText> m = renewMltext(filterMltext(assessmentQuestionPaper.getAqpName(),baseLanguage));
        m.addAll(assessmentQuestionPaper.getAqpName());
        assessmentQuestionPaper.setAqpName(m);

        return assessmentQuestionPaper;

    }
    private QuestionPaperSection duplicateQuestionPaperSection(QuestionPaperSection section, HashMap<Long, Long> questionIdMap) {
        return QuestionPaperSection.builder()
                .id(0L)
                .tenant(section.getTenant())
                .totalMarks(section.getTotalMarks())
                .totalQuestions(section.getTotalQuestions())
                .totalQuestionsToBeAnswered(section.getTotalQuestionsToBeAnswered())
                .orderNo(section.getOrderNo())
                .sectionDesc(section.getSectionDesc())
                .qpsDesc(renewMltext(section.getQpsDesc()))
                .qpsName(renewMltext(section.getQpsName()))
                .qpsText(renewMltext(section.getQpsText()))
                .sectionquestionsList(section.getSectionquestionsList().stream().map(el->duplicateSectionQuestion(el,questionIdMap)).collect(Collectors.toCollection(LinkedHashSet::new)))
                .build();
    }
    private SectionQuestions duplicateSectionQuestion(SectionQuestions sectionQuestions, HashMap<Long, Long> questionIdMap) {
        return SectionQuestions.builder()
                .id(0L)
                .questionId(questionIdMap==null? sectionQuestions.getQuestionId():questionIdMap.get(sectionQuestions.getQuestionId()))
                .tenant(sectionQuestions.getTenant())
                .questionNumber(sectionQuestions.getQuestionNumber())
                .orderNo(sectionQuestions.getOrderNo())
                .secQuestDesc(renewMltext(sectionQuestions.getSecQuestDesc()))
                .sectionQuestionMeta(sectionQuestions.getSectionQuestionMeta())
                .isMandatory(sectionQuestions.getIsMandatory())
                .build();
    }

    private HashMap<Long, Long> duplicateAssessmentInstanceQuestions(Set<AssessmentQuestionPaper> questionPapers) {

        Map<Long,Long> sectionQuestionAndQuestionMap = questionPapers.stream()
                .flatMap(paper -> paper.getQuestionpapersectionList().stream())
                .flatMap(section -> section.getSectionquestionsList().stream())
                .collect(Collectors.toMap(SectionQuestions::getId,SectionQuestions::getQuestionId));
        Set<Long> questionIds = sectionQuestionAndQuestionMap.values()
                .stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        Set<Question> questions = questionNonMTRepository.getQuestionByIds(questionIds);
        Map<Long,Long> frequencyMap = questions.stream().map(Question::getQuestionDuplicatedFrom)
                .collect(Collectors.groupingBy(e -> e, Collectors.counting()));
        frequencyMap.forEach(questionNonMTRepository::updateQuestionCount);

        HashMap<Long,Long> questionIdMap=new HashMap<>();
        questions.forEach(
                question -> {
                    boolean crossTenant = question.isQuestionCrossTenant();
                    if(crossTenant){
                        List<Question> questionOptional = questionRepository.getQuestionExistenceInTenancy(question.getId(),PageRequest.of(0,1));
                        crossTenant =  questionOptional.isEmpty();
                    }
                    Question quest = duplicateQuestion(question,crossTenant);
                    questionEventProducer.questionCreated(questionDTOAssembler.toDTO(quest));
                    questionIdMap.put(question.getId(),quest.getId());
                }
        );
        return questionIdMap;
    }

    public Question duplicateQuestion(Question question,Boolean crossTenant){

        Question duplicateQuestion = Question.builder()
                .id(null)
                .duplicatedCount(null)
                .duplicatedFrom(question.getId())
                .questionCode(question.getQuestionCode())
                .appCreatedTimestamp(question.getAppCreatedTimestamp())
                .appLastModifiedTimestamp(question.getAppLastModifiedTimestamp())
                .questionType(question.getQuestionType())
                .questionSubtype(question.getQuestionSubtype())
                .responseuomcode(question.getResponseuomcode())
                .complexityLevel(question.getComplexityLevel())
                .weight(question.getWeight())
                .domain(question.getDomain())
                .tenant(question.getTenant())
                .questionClass(question.getQuestionClass())
                .frequencyindays(question.getFrequencyindays())
                .timeToRectifyInDays(question.getTimeToRectifyInDays())
                .ltldSkillList(Optional.ofNullable(question.getLtldSkillList()).map(HashSet::new).orElse(null))
                .defaultOptionsList(duplicateDefaultOptions(question.getDefaultOptionsList()))
                .mtfoptionList(duplicateMtfoptions(question.getMtfoptionList()))
                .questionName(renewMltext(question.getQuestionName()))
                .questionText(renewMltext(question.getQuestionText()))
                .questionSubText(renewMltext(question.getQuestionSubText()))
                .questionMeta(question.getQuestionMeta())
                .questionImageurl(question.getQuestionImageurl())
                .subDomain(question.getSubDomain())
                .skill(question.getSkill())
                .subSkill(question.getSubSkill())
                .duplicatedCount(null)
                .duplicatedFrom(question.getId())
                .isQuestionModified(crossTenant)
                .build();
        duplicateQuestion.getDefaultOptionsList().forEach(el -> {
            el.setQuestion(duplicateQuestion);
            el.setChildQuestionList(el.getChildQuestionList().stream().map(id -> duplicateQuestion(id).getId()).collect(Collectors.toSet()));
        });
        return questionService.createQuestionWithResponse(duplicateQuestion);
    }

    private Set<MtfOption> duplicateMtfoptions(Set<MtfOption> mtfoptionList) {
        Set<MtfOption> set  = new LinkedHashSet<>();
        for (MtfOption mtfOption : mtfoptionList) {
            set.add(MtfOption.builder()
                    .id(null)
                    .weight(mtfOption.getWeight())
                    .tenant(mtfOption.getTenant())
                    .optionLeft(duplicateOptions(mtfOption.getOptionLeft()))
                    .optionRight(duplicateOptions(mtfOption.getOptionRight()))
                    .build());
        }
        return set;
    }

    private Options duplicateOptions(Options options) {
        return Options.builder()
                .id(null)
                .optionName(renewMltext(options.getOptionName()))
                .tenant(options.getTenant())
                .optionCode(options.getOptionCode())
                .optionImageurl(options.getOptionImageurl())
                .build();
    }

    private Set<DefaultOptions> duplicateDefaultOptions(Set<DefaultOptions> defaultoptionsList) {
        return defaultoptionsList.stream().map(
                (defaultOptions -> {
                    DefaultOptions duplicateDefaultOption = DefaultOptions.builder()
                            .id(null)
                            .options(duplicateOptions(defaultOptions.getOptions()))
                            .appCreatedTimestamp(defaultOptions.getAppCreatedTimestamp())
                            .appLastModifiedTimestamp(defaultOptions.getAppLastModifiedTimestamp())
                            .childQuestionList(Optional.ofNullable(defaultOptions.getChildQuestionList()).map(HashSet::new).orElse(null))
                            .weight(defaultOptions.getWeight())
                            .orderNo(defaultOptions.getOrderNo())
                            .defaultOptExpl(renewMltext(defaultOptions.getDefaultOptExpl()))
                            .isCorrect(defaultOptions.getIsCorrect())
                            .tenant(defaultOptions.getTenant())
                            .build();
                    duplicateDefaultOption.getOptions().setDefaultOptionsList(Collections.singleton(duplicateDefaultOption));
                    return duplicateDefaultOption;
                })
        ).collect(Collectors.toSet());

    }
    private AssessmentInstanceActor renewAssessmentInstanceActor(AssessmentInstanceActor conductedBy) {

        return conductedBy==null?null:AssessmentInstanceActor.builder()
                .actorId(conductedBy.getActorId())
                .actorType(conductedBy.getActorType())
                .build();
    }
    private Set<MLText> renewMltext(Set<MLText> mltexts) {
        return mltexts==null ? new HashSet<>():mltexts.stream().map(text-> MLText.builder().id(null).text(text.getText()).languageCode(text.getLanguageCode()).build()).collect(Collectors.toSet());
    }
    private Set<MLText> filterMltext(Set<MLText> aqpName,String language) {

        return aqpName==null?new HashSet<>():aqpName.stream()
                .filter(el -> !el.getLanguageCode().equals( language ))
                .collect(Collectors.toSet());
    }
    private void increaseDuplicateCount(Long assessmentInstanceId) {
        notMTRepository.updateDuplicatedCountById(assessmentInstanceId);
        assessmentNonMTRepository.updateDuplicatedCountById(assessmentInstanceId);
        questionPaperRepository.updateDuplicatedCountById(assessmentInstanceId);
    }
}
