package com.platformcommons.platform.service.assessment.application;


import com.platformcommons.platform.service.assessment.domain.Question;
import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.dto.base.PageDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface QuestionService {


    Question getQuestionById(Long id);

    Question createQuestionWithResponse(Question fromDTO);

    Question updateQuestionWithResponse(Question question);

    Boolean checkQuestionResponseExists(Long questionId);

    Set<Question> getQuestionByIdsAndInstanceId(Set<Long> questionIds, Long instanceId);

    Set<Question> getChildQuestion(Set<Long> questionIds, Long instanceId);

    PageDTO<QuestionDTO> searchByQuestionByQuestionText(String domain, String subDomain, String questionText, String questionType, String questionSubType, String sortBy, String sortOrder, String language, Integer page, Integer size);

    String createAssessmentAttachment(MultipartFile file, Long entityId);

    List<Question> getQuestionByIds(Set<Long> ids);

    List<Question> getQuestionByIdsV2(Set<Long> questionIds);

    List<Question> getQuestionByIdsV3(Set<Long> questionIds);

    Set<Long> getChildQuestionIds(Set<Long> questionIds);

}

