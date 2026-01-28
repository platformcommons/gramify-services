package com.platformcommons.platform.service.assessment.facade.impl;

import com.platformcommons.platform.exception.generic.InvalidInputException;
import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.assessment.application.AssessmentService;
import com.platformcommons.platform.service.assessment.application.QuestionService;
import com.platformcommons.platform.service.assessment.domain.*;
import com.platformcommons.platform.service.assessment.domain.vo.*;
import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.facade.*;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentExcelVOMapper;
import com.platformcommons.platform.service.assessment.messaging.producer.QuestionEventProducer;
import com.platformcommons.platform.service.sdk.bulkupload.infrastructure.PoiExcelHelper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.*;
import java.util.stream.Collectors;

@Component("AssessmentBulkUploadFacadeImpl")
@RequiredArgsConstructor
public class BulkUploadFacadeImpl implements BulkUploadFacade {


    private final AssessmentFacade assessmentFacade;
    private final QuestionFacade questionFacade;
    private final AssessmentQuestionPaperFacade questionPaperFacade;
    private final AssessmentInstanceFacade assessmentInstanceFacade;

    private final AssessmentExcelVOMapper assessmentExcelVOMapper;
    private final QuestionEventProducer producer;
    private final AssessmentService assessmentService;
    private final QuestionService questionService;


    @Override
    public UploadedAssessmentInfo uploadV2(MultipartFile file, Integer maxDepth, @NotNull Boolean createInstance) throws Exception {

        if (file == null) {
            throw new InvalidInputException("File can not be null");
        }
        UploadedAssessmentInfo.UploadedAssessmentInfoBuilder builder = UploadedAssessmentInfo.builder();
        XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());

        List<AssessmentExcelVO> assessmentExcelVOS = PoiExcelHelper
                .sheetToPOJO(workbook.getSheetAt(0), AssessmentExcelVO.class);
        List<AssessmentQuestionExcelVO> assessmentQuestionExcelVOS = PoiExcelHelper
                .sheetToPOJO(workbook.getSheetAt(1), AssessmentQuestionExcelVO.class);
        List<AssessmentQuestionOptionExcelVO> assessmentQuestionOptionExcelVOS = PoiExcelHelper
                .sheetToPOJO(workbook.getSheetAt(2), AssessmentQuestionOptionExcelVO.class);
        assessmentExcelVOS.forEach(AssessmentExcelVO::init);
        assessmentQuestionExcelVOS.forEach(AssessmentQuestionExcelVO::init);
        assessmentQuestionOptionExcelVOS.forEach(AssessmentQuestionOptionExcelVO::init);

        Map<String, Long> assessmentCodeIdMap = new LinkedHashMap<>();
        Map<String, Long> questionCodeIdMap = new LinkedHashMap<>();

        Map<String, AssessmentExcelVO> assessmentCodeVOMap = new LinkedHashMap<>();

        Map<String, Set<AssessmentQuestionExcelVO>> asssessmentQuestionMap = assessmentQuestionExcelVOS.stream()
                .filter(it -> it.getDepth() != null && it.getDepth().equals(0)).collect(Collectors.groupingBy(AssessmentQuestionExcelVO::getAssessmentCode,
                        Collectors.toCollection(LinkedHashSet::new)));

        Map<String, List<AssessmentQuestionOptionExcelVO>> optionsMap = assessmentQuestionOptionExcelVOS.stream()
                .collect(Collectors.groupingBy(AssessmentQuestionOptionExcelVO::getQuestionCode, Collectors.toCollection(LinkedList::new)));

        for (int i = maxDepth; i >= 0; i--) {
            int finalI = i;
            List<AssessmentQuestionExcelVO> childQuestions = assessmentQuestionExcelVOS.stream().filter(it -> it.getDepth().equals(finalI))
                    .collect(Collectors.toCollection(LinkedList::new));
            childQuestions.forEach(childQuestion -> {
                List<AssessmentQuestionOptionExcelVO> tempOptions = optionsMap.get(childQuestion.getQuestionCode());
                Map<String, Set<Long>> optionChildQuestionMap = new LinkedHashMap<>();
                if (tempOptions != null) {
                    tempOptions.stream().filter(it -> it.getChildQuestionCode() != null && !it.getChildQuestionCode().isEmpty()).forEach(it -> {
                        List<String> childQuestionCodes = Arrays.stream(it.getChildQuestionCode().split(",")).collect(Collectors.toCollection(LinkedList::new));
                        optionChildQuestionMap.put(it.getOptionCode(), childQuestionCodes.stream().map(questionCodeIdMap::get).collect(Collectors.toCollection(LinkedHashSet::new)));
                    });
                }
                QuestionDTO savedQuestion = questionFacade.createQuestionWithResponse(assessmentExcelVOMapper.fromExcelVO(childQuestion,
                        optionsMap.get(childQuestion.getQuestionCode()), optionChildQuestionMap));
                producer.questionCreated(savedQuestion);
                questionCodeIdMap.put(savedQuestion.getQuestionCode(), savedQuestion.getId());

            });
        }
        assessmentExcelVOS.forEach(assessmentExcelVO -> {
            Long savedId = assessmentFacade.createAssessment(assessmentExcelVOMapper.fromAssessmentExcelVO(assessmentExcelVO));
            assessmentCodeIdMap.put(assessmentExcelVO.getAssessmentCode(), savedId);
            assessmentCodeVOMap.put(assessmentExcelVO.getAssessmentCode(), assessmentExcelVO);
            builder.assessmentId(savedId);
        });

        asssessmentQuestionMap.forEach((assessmentCode, assessmentQuestions) -> {
            Set<QuestionPaperSectionDTO> questionPaperSectionDTOS = new LinkedHashSet<>();
            Map<String, Set<AssessmentQuestionExcelVO>> sectionQuestionMap = assessmentQuestions.stream()
                    .collect(Collectors.groupingBy(AssessmentQuestionExcelVO::getSection, Collectors.toCollection(LinkedHashSet::new)));
            sectionQuestionMap.forEach((section, assessmentSecQuestions) -> {

                AssessmentQuestionExcelVO assessmentQuestionExcelVO = assessmentSecQuestions.stream().findAny().get();
                Map<String, String> qpsNames = new HashMap<>();
                qpsNames.put("ENG", assessmentQuestionExcelVO.getSection());
                qpsNames.put("HIN", assessmentQuestionExcelVO.getSectionHindi());
                qpsNames.put("GUJ", assessmentQuestionExcelVO.getSectionGujarati());
                qpsNames.put("ORI", assessmentQuestionExcelVO.getSectionOdia());

                questionPaperSectionDTOS.add(
                        QuestionPaperSectionDTO.builder()
                                .id(0L)
                                .orderNo(Long.valueOf(assessmentQuestionExcelVO.getSectionSequence()))
                                .qpsName(assessmentExcelVOMapper.toMimicMLText(qpsNames))
                                .qpsDesc(assessmentExcelVOMapper.toMimicMLText(qpsNames))
                                .totalQuestions((long) assessmentQuestions.size())
                                .totalQuestionsToBeAnswered((long) assessmentQuestions.size())
                                .totalMarks(assessmentSecQuestions.stream().mapToDouble(AssessmentQuestionExcelVO::getWeight).sum())
                                .sectionQuestionsList(assessmentSecQuestions.stream().map(it -> SectionQuestionsDTO.builder()
                                        .id(0L)
                                        .isMandatory(Boolean.TRUE)
                                        .orderNo(Long.valueOf(it.getQuestionSequence()))
                                        .questionNumber(Long.valueOf(it.getQuestionSequence()))
                                        .isMandatory(it.getMandatory())
                                        .tenant(0L)
                                        .questionId(questionCodeIdMap.get(it.getQuestionCode()))
                                        .build()).collect(Collectors.toCollection(LinkedHashSet::new)))
                                .build());
            });
            AssessmentExcelVO assessmentExcelVO = assessmentCodeVOMap.get(assessmentCode);
            Map<String, String> aqpDesc = new HashMap<>();
            Map<String, String> aqpName = new HashMap<>();

            aqpDesc.put("ENG", assessmentExcelVO.getAssessmentDesc());
            aqpDesc.put("HIN", assessmentExcelVO.getAssessmentDescHindi());
            aqpDesc.put("GUJ", assessmentExcelVO.getAssessmentDescGujarati());
            aqpDesc.put("ORI", assessmentExcelVO.getAssessmentDescOdia());

            aqpName.put("ENG", assessmentExcelVO.getAssessmentName());
            aqpName.put("HIN", assessmentExcelVO.getAssessmentNameHindi());
            aqpName.put("GUJ", assessmentExcelVO.getAssessmentNameGujarati());
            aqpName.put("ORI", assessmentExcelVO.getAssessmentNameOdia());

            Long assessmentQuestionPaper = questionPaperFacade.createAssessmentQuestionPaper(AssessmentQuestionPaperDTO.builder()
                    .id(0L)
                    .assessment(AssessmentDTO.builder().id(assessmentCodeIdMap.get(assessmentCode)).build())
                    .aqpDesc(assessmentExcelVOMapper.toMimicMLText(aqpDesc))
                    .aqpName(assessmentExcelVOMapper.toMimicMLText(aqpName))
                    .questionPaperSectionList(questionPaperSectionDTOS)
                    .assessmentQuestionPaperCode("QP_" + assessmentCode)
                    .build());
            builder.questionPaperId(assessmentQuestionPaper);

        });

        if (createInstance) {
            assessmentExcelVOS.forEach(assessmentExcelVO -> {
                Long instanceId = assessmentInstanceFacade.createAssessmentInstance(assessmentExcelVOMapper.fromAssessmentExcelVOToInstance(assessmentExcelVO, assessmentCodeIdMap.get(assessmentExcelVO.getAssessmentCode())));
                builder.assessmentInstanceId(instanceId);
            });
        }
        return builder.build();
    }

    @Override
    @Transactional(readOnly = true)
    public ByteArrayInputStream downloadAssessmentAsExcel(Long assessmentId) throws Exception {
        Assessment assessment = assessmentService.getAssessmentById(assessmentId);
        if (assessment == null) {
            throw new NotFoundException("Assessment not found with id: " + assessmentId);
        }

        List<AssessmentExcelVO> assessmentData = Collections.singletonList(mapToAssessmentExcelVO(assessment));
        List<AssessmentQuestionExcelVO> questionData = new ArrayList<>();
        List<AssessmentQuestionOptionExcelVO> optionData = new ArrayList<>();

        if (assessment.getAssessmentQuestionPaperList() != null && !assessment.getAssessmentQuestionPaperList().isEmpty()) {
            AssessmentQuestionPaper qp = assessment.getAssessmentQuestionPaperList().iterator().next();

            if (qp.getQuestionpapersectionList() != null) {
                List<QuestionPaperSection> sortedSections = qp.getQuestionpapersectionList().stream()
                        .sorted(Comparator.comparing(QuestionPaperSection::getOrderNo))
                        .collect(Collectors.toList());

                int questionGlobalSequence = 1;
                for (int i = 0; i < sortedSections.size(); i++) {
                    QuestionPaperSection section = sortedSections.get(i);
                    final int sectionSequence = i + 1;

                    if (section.getSectionquestionsList() != null && !section.getSectionquestionsList().isEmpty()) {
                        List<SectionQuestions> sortedQuestions = section.getSectionquestionsList().stream()
                                .sorted(Comparator.comparing(SectionQuestions::getOrderNo))
                                .collect(Collectors.toList());

                        for (SectionQuestions sq : sortedQuestions) {
                            Question question = questionService.getQuestionById(sq.getQuestionId());
                            addQuestionAndChildren(
                                    assessment,
                                    question,
                                    sq,
                                    section,
                                    sectionSequence,
                                    questionGlobalSequence,
                                    questionData,
                                    optionData,
                                    0 // depth
                            );
                            questionGlobalSequence++;
                        }
                    }
                }
            }
        }

        XSSFWorkbook workbook = new XSSFWorkbook();
        PoiExcelHelper.pojoToSheet(workbook, "Assessment", assessmentData);
        PoiExcelHelper.pojoToSheet(workbook, "Questions", questionData);
        if(CollectionUtils.isNotEmpty(optionData)) {
            PoiExcelHelper.pojoToSheet(workbook, "Options", optionData);
        }
        else{
            workbook.createSheet("Options");
            workbook.getSheet("Options").createRow(0).createCell(0).setCellValue("No Options found for this Assessments");
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);
        workbook.close();
        return new ByteArrayInputStream(out.toByteArray());
    }

    private void addQuestionAndChildren(Assessment assessment, Question question, SectionQuestions sq, QuestionPaperSection section, int sectionSeq, int questionSeq,
                                        List<AssessmentQuestionExcelVO> questionData, List<AssessmentQuestionOptionExcelVO> optionData, int depth) {


        AssessmentQuestionExcelVO vo = mapToQuestionExcelVO(assessment, section, sq, question, sectionSeq, questionSeq);
        vo.setDepth(depth);
        questionData.add(vo);


        if (question.getDefaultOptionsList() != null) {
            question.getDefaultOptionsList().stream()
                    .sorted(Comparator.comparing(DefaultOptions::getOrderNo))
                    .forEach(option -> {
                        AssessmentQuestionOptionExcelVO optionVO = mapToOptionExcelVO(question, option);
                        optionData.add(optionVO);


                        if (option.getChildQuestionList() != null && !option.getChildQuestionList().isEmpty()) {
                            List<Question> childQuestions = questionService.getQuestionByIdsV3(option.getChildQuestionList());
                            for (Question child : childQuestions) {
                                SectionQuestions dummySQ = new SectionQuestions();
                                dummySQ.setIsMandatory(true);
                                dummySQ.setOrderNo(0L);
                                dummySQ.setQuestionId(child.getId());

                                addQuestionAndChildren(assessment, child, dummySQ, section, sectionSeq, 0, questionData, optionData, depth + 1);
                            }
                        }
                    });
        }
    }

    private AssessmentExcelVO mapToAssessmentExcelVO(Assessment assessment) {
        AssessmentExcelVO vo = new AssessmentExcelVO();
        vo.setDomain(assessment.getDomain());
        vo.setSubDomain(assessment.getSubDomain());
        vo.setAssessmentCode(assessment.getAssessmentCode());
        vo.setBaseLanguage(assessment.getBaseLanguage());
        vo.setAssessmentType(assessment.getAssessmentType());
        vo.setAssessmentSubType(assessment.getAssessmentSubType());
        vo.setSubjectCode(assessment.getSubjectCode());
        vo.setAssessmentKind(assessment.getAssessmentKind());
        vo.setAssessmentSubKind(assessment.getAssessmentSubKind());
        vo.setTotalMarks(assessment.getTotallMarks());
        vo.setContext(assessment.getContext());

        assessment.getAssessmentName().forEach(ml -> {
            switch (ml.getLanguageCode()) {
                case "ENG":
                    vo.setAssessmentName(ml.getText());
                    break;
                case "HIN":
                    vo.setAssessmentNameHindi(ml.getText());
                    break;
                case "TAM":
                    vo.setAssessmentNameTamil(ml.getText());
                    break;
                case "TEL":
                    vo.setAssessmentNameTelugu(ml.getText());
                    break;
                case "KAN":
                    vo.setAssessmentNameKannada(ml.getText());
                    break;
                case "MAR":
                    vo.setAssessmentNameMarathi(ml.getText());
                    break;
                case "GUJ":
                    vo.setAssessmentNameGujarati(ml.getText());
                    break;
                case "ODIA":
                    vo.setAssessmentNameOdia(ml.getText());
                    break;
            }
        });
        assessment.getAssessmentDesc().forEach(ml -> {
            switch (ml.getLanguageCode()) {
                case "ENG":
                    vo.setAssessmentDesc(ml.getText());
                    break;
                case "HIN":
                    vo.setAssessmentDescHindi(ml.getText());
                    break;
                case "TAM":
                    vo.setAssessmentDescTamil(ml.getText());
                    break;
                case "TEL":
                    vo.setAssessmentDescTelugu(ml.getText());
                    break;
                case "KAN":
                    vo.setAssessmentDescKannada(ml.getText());
                    break;
                case "MAR":
                    vo.setAssessmentDescMarathi(ml.getText());
                    break;
                case "GUJ":
                    vo.setAssessmentDescGujarati(ml.getText());
                    break;
                case "ODIA":
                    vo.setAssessmentDescOdia(ml.getText());
                    break;
            }
        });
        return vo;
    }

    private AssessmentQuestionExcelVO mapToQuestionExcelVO(Assessment assessment, QuestionPaperSection section, SectionQuestions sq, Question question, int sectionSeq, int questionSeq) {
        AssessmentQuestionExcelVO vo = new AssessmentQuestionExcelVO();
        vo.setAssessmentCode(assessment.getAssessmentCode());
        vo.setQuestionCode(question.getQuestionCode());
        vo.setSectionSequence(sectionSeq);
        vo.setQuestionSequence(questionSeq);
        vo.setQuestionType(question.getQuestionType());
        vo.setQuestionSubType(question.getQuestionSubtype());
        vo.setMandatory(String.valueOf(sq.getIsMandatory()));
        vo.setWeight(question.getWeight());
        vo.setSkill(question.getSkillCode());
        vo.setSubSkill(question.getSubSkillCode());
        vo.setHintText(question.getHintText());
        vo.setOptionSourceType(question.getOptionSourceType());
        vo.setOptionSourceValue(question.getOptionSourceValue());

        section.getQpsName().forEach(ml -> {
            switch (ml.getLanguageCode()) {
                case "ENG":
                    vo.setSection(ml.getText());
                    break;
                case "HIN":
                    vo.setSectionHindi(ml.getText());
                    break;
                case "TAM":
                    vo.setSectionTamil(ml.getText());
                    break;
                case "TEL":
                    vo.setSectionTelugu(ml.getText());
                    break;
                case "KAN":
                    vo.setSectionKannada(ml.getText());
                    break;
                case "MAR":
                    vo.setSectionMarathi(ml.getText());
                    break;
                case "GUJ":
                    vo.setSectionGujarati(ml.getText());
                    break;
                case "ODIA":
                    vo.setSectionOdia(ml.getText());
                    break;
            }
        });
        question.getQuestionText().forEach(ml -> {
            switch (ml.getLanguageCode()) {
                case "ENG":
                    vo.setQuestionName(ml.getText());
                    break;
                case "HIN":
                    vo.setQuestionNameHindi(ml.getText());
                    break;
                case "TAM":
                    vo.setQuestionNameTamil(ml.getText());
                    break;
                case "TEL":
                    vo.setQuestionNameTelugu(ml.getText());
                    break;
                case "KAN":
                    vo.setQuestionNameKannada(ml.getText());
                    break;
                case "MAR":
                    vo.setQuestionNameMarathi(ml.getText());
                    break;
                case "GUJ":
                    vo.setQuestionNameGujarati(ml.getText());
                    break;
                case "ODIA":
                    vo.setQuestionNameOdia(ml.getText());
                    break;
            }
        });
        question.getQuestionSubText().stream().findFirst().ifPresent(desc -> vo.setQuestionDescription(desc.getText()));
        return vo;
    }

    private AssessmentQuestionOptionExcelVO mapToOptionExcelVO(Question question, DefaultOptions defaultOption) {
        AssessmentQuestionOptionExcelVO vo = new AssessmentQuestionOptionExcelVO();
        Options option = defaultOption.getOptions();
        vo.setQuestionCode(question.getQuestionCode());
        vo.setOptionCode(option.getOptionCode());
        vo.setSequence(defaultOption.getOrderNo());
        vo.setOptionWeight(defaultOption.getWeight());
        vo.setIsCorrect(Boolean.valueOf(defaultOption.getIsCorrect()));
        vo.setRemarksRequired(defaultOption.getRemarksRequired());
        vo.setIsChildOption(defaultOption.getIsChildOption());
        vo.setParentOptionCode(defaultOption.getParentOptionCode());

        if (defaultOption.getChildQuestionList() != null && !defaultOption.getChildQuestionList().isEmpty()) {
            List<Question> childQuestions = questionService.getQuestionByIdsV3(defaultOption.getChildQuestionList());
            String childCodes = childQuestions.stream().map(Question::getQuestionCode).collect(Collectors.joining(","));
            vo.setChildQuestionCode(childCodes);
        }

        option.getOptionName().forEach(ml -> {
            switch (ml.getLanguageCode()) {
                case "ENG":
                    vo.setOptionText(ml.getText());
                    break;
                case "HIN":
                    vo.setOptionTextHindi(ml.getText());
                    break;
                case "TAM":
                    vo.setOptionTextTamil(ml.getText());
                    break;
                case "TEL":
                    vo.setOptionTextTelugu(ml.getText());
                    break;
                case "KAN":
                    vo.setOptionTextKannada(ml.getText());
                    break;
                case "MAR":
                    vo.setOptionTextMarathi(ml.getText());
                    break;
                case "GUJ":
                    vo.setOptionTextGujarati(ml.getText());
                    break;
                case "ODIA":
                    vo.setOptionTextOdia(ml.getText());
                    break;
            }
        });
        return vo;
    }


}
