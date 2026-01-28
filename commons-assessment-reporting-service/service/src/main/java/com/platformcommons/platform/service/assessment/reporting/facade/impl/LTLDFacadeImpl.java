package com.platformcommons.platform.service.assessment.reporting.facade.impl;

import com.platformcommons.platform.service.assessment.dto.*;
import com.platformcommons.platform.service.assessment.reporting.application.context.AssessmentSyncContext;
import com.platformcommons.platform.service.assessment.reporting.facade.LTLDFacade;
import com.platformcommons.platform.service.assessment.reporting.facade.client.LTLDClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class LTLDFacadeImpl implements LTLDFacade {

    private final LTLDClient ltldClient;

    @Override
    public AssessmentSyncContext getAssessmentContext(Long assessment, String token) {
        AssessmentDTO assessmentDTO = getAssesment(assessment, token);
        AssessmentInstanceDTO assessmentInstanceDTO = getAssessmentInstance(assessment, token);
        AssessmentQuestionPaperDTO assessmentQuestionPaperDTO =  getAssessmentQuestionPaperDTO(assessment,token);
        Map<Long, QuestionDTO> questionDTOMap = getQuestionDTOMap(assessment, token);
        return AssessmentSyncContext.builder()
                .assessment(assessmentDTO)
                .assessmentInstanceDTO(assessmentInstanceDTO)
                .assessmentQuestionPaperDTO(assessmentQuestionPaperDTO)
                .questionDTOMap(questionDTOMap)
                .build();
    }

    private Map<Long, QuestionDTO> getQuestionDTOMap(Long assessment, String token) {
        return null;
    }

    private AssessmentQuestionPaperDTO getAssessmentQuestionPaperDTO(Long assessment, String token) {
        return null;
    }

    private AssessmentInstanceDTO getAssessmentInstance(Long assessment, String token) {
        return null;
    }

    private AssessmentDTO getAssesment(Long assessment, String token) {
//        com.mindtree.bridge.platform.dto.AssessmentDTO dto =  ltldClient.getAssessmentDtoList(token, String.format("id=%s", dto)).get(0);

//        AssessmentDTO.builder()
//                .id(dto.getId().longValue())
//                .tenantId(dto.getTenant().longValue())
//                .assessmentName(dto.getAssessmentName().stream().map(ltldmlTextDTO ->
//                    MimicMLTextDTO.builder()
//                            .language(MimicLanguageDTO.builder()
//                                                      .code(ltldmlTextDTO.getLanguageCode())
//                                                      .build())
//                            .build()
//                ).collect(Collectors.toSet()))
//                .assessmentDesc(dto.getAssessmentDesc().stream().map(ltldmlTextDTO ->
//                        MimicMLTextDTO.builder()
//                                .language(MimicLanguageDTO.builder()
//                                        .code(ltldmlTextDTO.getLanguageCode())
//                                        .build())
//                                .build()
//                ).collect(Collectors.toSet()))
//                .assessmentType(MimicMLTextDTO.builder().text(dto.getAssessmentType()).language())
////                .assessmentCategory(assessment.getAssessmentCategory())
////                .assessmentSubCategory(assessment.getAssessmentSubCategory())
////                .assessmentStatus(assessment.getAssessmentStatus())
////                .assessmentDuration(assessment.getAssessmentDuration())
//                .build();
        return null;
    }
}
