package com.platformcommons.platform.service.assessment.facade.assembler;

import com.platformcommons.platform.service.assessment.application.constant.ServiceConstants;
import com.platformcommons.platform.service.assessment.domain.Assessment;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentExcelVO;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentQuestionExcelVO;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentQuestionOptionExcelVO;
import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.facade.assembler.MLTextDTOAssembler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AssessmentExcelVOMapper {


    private final MimicRefDataDTOAssembler mimicRefDataDTOAssembler;

    private final MLTextDTOAssembler mlTextDTOAssembler;


    public AssessmentDTO fromAssessmentExcelVO(AssessmentExcelVO assessmentExcelVO){
        Map<String,String> assessmentName = new HashMap<>();
        Map<String,String> assessmentDesc = new HashMap<>();

        assessmentDesc.put("ENG",assessmentExcelVO.getAssessmentDesc());
        assessmentDesc.put("HIN",assessmentExcelVO.getAssessmentDescHindi());
        assessmentDesc.put("GUJ",assessmentExcelVO.getAssessmentDescGujarati());
        assessmentDesc.put("ORI",assessmentExcelVO.getAssessmentDescOdia());
        assessmentDesc.put("TAM",assessmentExcelVO.getAssessmentDescTamil());
        assessmentDesc.put("TEL",assessmentExcelVO.getAssessmentDescTelugu());
        assessmentDesc.put("KAN",assessmentExcelVO.getAssessmentDescKannada());
        assessmentDesc.put("MAR",assessmentExcelVO.getAssessmentDescMarathi());

        assessmentName.put("ENG",assessmentExcelVO.getAssessmentName());
        assessmentName.put("HIN",assessmentExcelVO.getAssessmentNameHindi());
        assessmentName.put("GUJ",assessmentExcelVO.getAssessmentNameGujarati());
        assessmentName.put("ORI",assessmentExcelVO.getAssessmentNameOdia());
        assessmentName.put("TAM",assessmentExcelVO.getAssessmentNameTamil());
        assessmentName.put("TEL",assessmentExcelVO.getAssessmentNameTelugu());
        assessmentName.put("KAN",assessmentExcelVO.getAssessmentNameKannada());
        assessmentName.put("MAR",assessmentExcelVO.getAssessmentNameMarathi());
        return AssessmentDTO.builder()
                .id(0L)
                .tenant(0L)
                .domain(assessmentExcelVO.getDomain())
                .assessmentCode(assessmentExcelVO.getAssessmentCode())
                .subDomain(assessmentExcelVO.getSubDomain())
                .assessmentType(fromRefCode(assessmentExcelVO.getAssessmentType()))
                .subjectCode(assessmentExcelVO.getSubjectCode())
                .assessmentName(toMimicMLText(assessmentName))
                .assessmentDesc(toMimicMLText(assessmentDesc))
                .totallMarks(assessmentExcelVO.getTotalMarks())
                .context(assessmentExcelVO.getContext())
                .assessmentKind(assessmentExcelVO.getAssessmentKind()==null || assessmentExcelVO.getAssessmentKind().isEmpty()?null:MimicRefDataDTO.builder().code(assessmentExcelVO.getAssessmentKind()).build())
                .assessmentSubKind(assessmentExcelVO.getAssessmentSubKind()==null || assessmentExcelVO.getAssessmentSubKind().isEmpty()?null:MimicRefDataDTO.builder().code(assessmentExcelVO.getAssessmentSubKind()).build())
                .assessmentSubType(assessmentExcelVO.getAssessmentSubType()==null || assessmentExcelVO.getAssessmentSubType().isEmpty()?null:MimicRefDataDTO.builder().code(assessmentExcelVO.getAssessmentSubType()).build())
                .build();

    }



    public QuestionDTO fromExcelVO(AssessmentQuestionExcelVO assessmentQuestionExcelVO,
                                   List<AssessmentQuestionOptionExcelVO> options,Map<String,Set<Long>> optionChildQuestionMap ){
        Map<String,String> texts = new HashMap<>();
        texts.put("ENG",assessmentQuestionExcelVO.getQuestionName());
        texts.put("HIN",assessmentQuestionExcelVO.getQuestionNameHindi());
        texts.put("GUJ",assessmentQuestionExcelVO.getQuestionNameGujarati());
        texts.put("ORI",assessmentQuestionExcelVO.getQuestionNameOdia());
        texts.put("TAM",assessmentQuestionExcelVO.getQuestionNameTamil());
        texts.put("TEL",assessmentQuestionExcelVO.getQuestionNameTelugu());
        texts.put("KAN",assessmentQuestionExcelVO.getQuestionNameKannada());
        texts.put("MAR",assessmentQuestionExcelVO.getQuestionNameMarathi());
        Map<String,String> descriptions = new HashMap<>();
        descriptions.put("ENG",assessmentQuestionExcelVO.getQuestionDescription());

        return QuestionDTO.builder()
                .id(0L)
                .tenant(0L)
                .domain(assessmentQuestionExcelVO.getDomain())
                .subDomain(assessmentQuestionExcelVO.getSubDomain())
                .questionClass(MimicRefDataDTO.builder().code("QUESTION_CLASS.QUESTION").build())
                .questionCode(assessmentQuestionExcelVO.getQuestionCode())
                .questionType(MimicRefDataDTO.builder().code(assessmentQuestionExcelVO.getQuestionType()).build())
                .questionSubtype(MimicRefDataDTO.builder().code(assessmentQuestionExcelVO.getQuestionSubType()).build())
                .questionText(toMimicMLText(texts))
                .questionSubText(toMimicMLText(descriptions))
                .hintText(assessmentQuestionExcelVO.getHintText())
                .skillCode(assessmentQuestionExcelVO.getSkill())
                .subSkillCode(assessmentQuestionExcelVO.getSubSkill())
                .optionSourceType(assessmentQuestionExcelVO.getOptionSourceType())
                .optionSourceValue(assessmentQuestionExcelVO.getOptionSourceValue())
                .weight(assessmentQuestionExcelVO.getWeight())
                .isMandatory(assessmentQuestionExcelVO.getMandatory())
                .defaultOptionsList(options!=null && !options.isEmpty()?
                        options.stream().map(it-> fromExcelVO(it,optionChildQuestionMap.get(it.getOptionCode()))).collect(Collectors.toCollection(LinkedHashSet::new)):null)
                .build();

    }



    private DefaultOptionsDTO fromExcelVO(AssessmentQuestionOptionExcelVO assessmentQuestionOptionExcelVO, Set<Long> childQuestionIds){
        Map<String,String> texts = new HashMap<>();
        texts.put("ENG",assessmentQuestionOptionExcelVO.getOptionText());
        texts.put("HIN",assessmentQuestionOptionExcelVO.getOptionTextHindi());
        texts.put("GUJ",assessmentQuestionOptionExcelVO.getOptionTextGujarati());
        texts.put("ORI",assessmentQuestionOptionExcelVO.getOptionTextOdia());
        texts.put("TAM",assessmentQuestionOptionExcelVO.getOptionTextTamil());
        texts.put("TEL",assessmentQuestionOptionExcelVO.getOptionTextTelugu());
        texts.put("KAN",assessmentQuestionOptionExcelVO.getOptionTextKannada());
        texts.put("MAR",assessmentQuestionOptionExcelVO.getOptionTextMarathi());
        return DefaultOptionsDTO.builder()
                .id(0L)
                .tenant(0L)
                .isCorrect(assessmentQuestionOptionExcelVO.getIsCorrect())
                .options(OptionsDTO.builder().id(0L).optionCode(assessmentQuestionOptionExcelVO.getOptionCode())
                        .optionName(toMimicMLText(texts)).build())
                .weight(assessmentQuestionOptionExcelVO.getOptionWeight())
                .orderNo(assessmentQuestionOptionExcelVO.getSequence())
                .remarksRequired(assessmentQuestionOptionExcelVO.getRemarksRequired())
                .childQuestionList(childQuestionIds!=null && !childQuestionIds.isEmpty()? childQuestionIds :null)
                .isChildOption(assessmentQuestionOptionExcelVO.getIsChildOption())
                .parentOptionCode(assessmentQuestionOptionExcelVO.getParentOptionCode())
                .build();
    }



    public Set<MimicMLTextDTO> toMimicMLText(String text) {
        if (text != null && !text.isEmpty()) {
            return Collections.singleton(MimicMLTextDTO.builder().language(MimicLanguageDTO.builder().code("ENG").build()).text(text).build());
        } else {
            return null;
        }
    }
    public Set<MimicMLTextDTO> toMimicMLText(Map<String,String> texts) {
        Set<MimicMLTextDTO> dtos = new LinkedHashSet<>();
        texts.forEach((language,text)->{
            if(text==null || text.isEmpty()) return;
            dtos.add(MimicMLTextDTO.builder().language(MimicLanguageDTO.builder().code(language).build()).text(text).build());
        });
        return dtos;
    }


    private MimicRefDataDTO fromRefCode(String code){
        if(code==null || code.isEmpty()){
            return  null;
        }
        return MimicRefDataDTO.builder()
                .code(code)
                .build();
    }

    public AssessmentInstanceDTO buildAssessmentInstance(AssessmentExcelVO assessmentExcelVO, Date startDate, Date endDate, Date confirmationDate,Long assessmentId) {
        Map<String,String> assessmentName = new HashMap<>();
        Map<String,String> assessmentDesc = new HashMap<>();

        assessmentDesc.put("ENG",assessmentExcelVO.getAssessmentDesc());
        assessmentDesc.put("HIN",assessmentExcelVO.getAssessmentDescHindi());
        assessmentDesc.put("GUJ",assessmentExcelVO.getAssessmentDescGujarati());
        assessmentDesc.put("ODIA",assessmentExcelVO.getAssessmentDescOdia());
        assessmentDesc.put("TAM",assessmentExcelVO.getAssessmentDescTamil());
        assessmentDesc.put("TEL",assessmentExcelVO.getAssessmentDescTelugu());
        assessmentDesc.put("KAN",assessmentExcelVO.getAssessmentDescKannada());
        assessmentDesc.put("MAR",assessmentExcelVO.getAssessmentDescMarathi());

        assessmentName.put("ENG",assessmentExcelVO.getAssessmentName());
        assessmentName.put("HIN",assessmentExcelVO.getAssessmentNameHindi());
        assessmentName.put("GUJ",assessmentExcelVO.getAssessmentNameGujarati());
        assessmentName.put("ODIA",assessmentExcelVO.getAssessmentNameOdia());
        assessmentName.put("TAM",assessmentExcelVO.getAssessmentNameTamil());
        assessmentName.put("TEL",assessmentExcelVO.getAssessmentNameTelugu());
        assessmentName.put("KAN",assessmentExcelVO.getAssessmentNameKannada());
        assessmentName.put("MAR",assessmentExcelVO.getAssessmentNameMarathi());

        return AssessmentInstanceDTO.builder()
                .id(0L)
                .assessment(AssessmentDTO.builder().id(assessmentId).build())
                .confirmationDate(confirmationDate)
                .startDate(startDate)
                .endDate(endDate)
                .asmtInstDesc(toMimicMLText(assessmentDesc))
                .asmtInstName(toMimicMLText(assessmentName))
                .isPublic(Boolean.TRUE)
                .isActive(Boolean.TRUE)
                .specificVisibility(Boolean.FALSE)
                .scheduleStatus(MimicRefDataDTO.builder().code("ASSESSMENT.DRAFT").build())
                .tenant(0L)
                .build();

    }

    public AssessmentInstanceDTO fromAssessmentExcelVOToInstance(AssessmentExcelVO assessmentExcelVO,Long assessmentId) {
        Map<String,String> assessmentName = new HashMap<>();
        Map<String,String> assessmentDesc = new HashMap<>();

        assessmentDesc.put("ENG",assessmentExcelVO.getAssessmentDesc());
        assessmentDesc.put("HIN",assessmentExcelVO.getAssessmentDescHindi());
        assessmentDesc.put("GUJ",assessmentExcelVO.getAssessmentDescGujarati());
        assessmentDesc.put("ORI",assessmentExcelVO.getAssessmentDescOdia());
        assessmentDesc.put("TAM",assessmentExcelVO.getAssessmentDescTamil());
        assessmentDesc.put("TEL",assessmentExcelVO.getAssessmentDescTelugu());
        assessmentDesc.put("KAN",assessmentExcelVO.getAssessmentDescKannada());
        assessmentDesc.put("MAR",assessmentExcelVO.getAssessmentDescMarathi());

        assessmentName.put("ENG",assessmentExcelVO.getAssessmentName());
        assessmentName.put("HIN",assessmentExcelVO.getAssessmentNameHindi());
        assessmentName.put("GUJ",assessmentExcelVO.getAssessmentNameGujarati());
        assessmentName.put("ORI",assessmentExcelVO.getAssessmentNameOdia());
        assessmentName.put("TAM",assessmentExcelVO.getAssessmentNameTamil());
        assessmentName.put("TEL",assessmentExcelVO.getAssessmentNameTelugu());
        assessmentName.put("KAN",assessmentExcelVO.getAssessmentNameKannada());
        assessmentName.put("MAR",assessmentExcelVO.getAssessmentNameMarathi());
        return AssessmentInstanceDTO.builder()
                .id(0L)
                .tenant(0L)
                .asmtInstName(toMimicMLText(assessmentName))
                .asmtInstDesc(toMimicMLText(assessmentDesc))
                .assessment(AssessmentDTO.builder()
                        .id(assessmentId)
                        .build())
                .scheduleStatus(MimicRefDataDTO.builder().code(ServiceConstants.PUBLISH).build())
                .isPublic(true)
                .specificVisibility(false)
                .isActive(true)
                .startDate(new Date())
                .endDate(new Date())
                .build();
    }
}
