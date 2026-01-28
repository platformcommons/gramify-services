package com.platformcommons.platform.service.assessment.facade.assembler.impl;

import com.platformcommons.platform.security.util.PlatformSecurityUtil;
import com.platformcommons.platform.service.assessment.domain.AssessmentInstance;
import com.platformcommons.platform.service.assessment.domain.AssessmentQuestionPaper;
import com.platformcommons.platform.service.assessment.domain.vo.AssessmentInstanceDetailVO;
import com.platformcommons.platform.service.assessment.facade.assembler.AssessmentInstanceDetailVoAssembler;
import com.platformcommons.platform.service.entity.common.MLText;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class AssessmentInstanceDetailVoAssemblerImpl implements AssessmentInstanceDetailVoAssembler {
    @Override
    public AssessmentInstanceDetailVO toAssessmentDetailVo(AssessmentInstance assessmentInstance, String language, String name) {

        return AssessmentInstanceDetailVO.builder()
                .assessmentImage(assessmentInstance.getImageURL())
                .assessmentName(
                        assessmentInstance.getAssessment()
                                .getAssessmentName()
                                .stream()
                                .filter(assessmentName -> language == null || assessmentName.getLanguageCode().equals(language))
                                .map(MLText::getText)
                                .findAny()
                                .orElse(null))
                .assessmentInstanceId(assessmentInstance.getId())
                .subDomainCode(assessmentInstance.getAssessment().getSubDomain())
                .domainCode(assessmentInstance.getAssessment().getDomain())
                .noOfQuestion(
                        assessmentInstance.getAssessment()
                                .getAssessmentQuestionPaperList()
                                .stream()
                                .map(AssessmentQuestionPaper::getQuestionpapersectionList)
                                .flatMap(Set::stream)
                                .mapToLong(questionPaperSection -> questionPaperSection.getSectionquestionsList().size())
                                .sum()
                )
                .author(name)
                .editable(PlatformSecurityUtil.getCurrentUserId().equals(assessmentInstance.getCreatedByUser()))
                .build();
    }
}
