package com.platformcommons.platform.service.assessment.facade;

import com.platformcommons.platform.service.assessment.dto.OptionCacheDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Set;

public interface QuestionFacade {
    Boolean checkQuestionResponseExists(Long questionId);

    QuestionDTO createQuestionWithResponse(QuestionDTO questionDTO);

    QuestionDTO updateQuestionWithResponse(QuestionDTO questionDTO);

    Set<QuestionDTO> getQuestionByIdsAndInstanceId(Set<Long> questionIds, Long instanceId);

    Set<QuestionDTO> getChildQuestion(Set<Long> questionIds, Long instanceId);

    PageDTO<QuestionDTO> searchByQuestionByQuestionText(String domain, String subDomain, String questionText, String questionType, String questionSubType, String sortBy, String sortOrder, String language, Integer page, Integer size);

    QuestionDTO duplicateQuestion(Long questionId);

    String createAssessmentQuestionAttachment(MultipartFile file, Long entityId);

    Set<QuestionDTO> getQuestionByIds(Set<Long> ids);

    Set<QuestionDTO> getQuestionByIdsV2(Set<Long> questionIds);

    Set<QuestionDTO> getQuestionByIdsV3(Set<Long> questionIds);

    Map<Long, Set<OptionCacheDTO>> getOptions(Set<Long> questionIds);

    Set<Long> getChildQuestionIds(Set<Long> questionIds);

}

