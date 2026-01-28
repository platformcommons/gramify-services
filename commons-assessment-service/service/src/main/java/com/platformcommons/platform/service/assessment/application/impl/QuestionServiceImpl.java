package com.platformcommons.platform.service.assessment.application.impl;


import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.assessment.application.AssessmentInstanceService;
import com.platformcommons.platform.service.assessment.application.QuestionService;
import com.platformcommons.platform.service.assessment.domain.Options;
import com.platformcommons.platform.service.assessment.domain.Question;
import com.platformcommons.platform.service.assessment.domain.repo.*;
import com.platformcommons.platform.service.assessment.dto.QuestionContextCacheDTO;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.facade.assembler.QuestionDTOAssembler;
import com.platformcommons.platform.service.attachment.domain.Attachment;
import com.platformcommons.platform.service.attachment.service.AttachmentService;
import com.platformcommons.platform.service.dto.base.PageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {


    private final QuestionRepository repository;
    private final QuestionNonMTRepository nonMTRepository;
    private final AiaDefaultResponseRepository aiaDefaultResponseRepository;
    private final SectionQuestionsRepository sectionQuestionsRepository;
    private final OptionsRepository optionsRepository;
    private final AssessmentInstanceService assessmentInstanceService;
    private final AttachmentService attachmentService;
    private final QuestionDTOAssembler questionDTOAssembler;

    @Override
    public Question getQuestionById(Long id) {
        return repository.findById(id).orElseThrow(()->new NotFoundException("ERR_SVC_QUE_NOT_FOUND"));
    }
    @Override
    public Question createQuestionWithResponse(Question question) {
        return repository.save(createOptionsAndInitializeForGrid(question.init()));
    }

    @Override
    public Question updateQuestionWithResponse(Question question) {
        question = getQuestionById(question.getId()).patchUpdate(question);
        return repository.save(patchForGrid(question));
    }

    @Override
    public Boolean checkQuestionResponseExists(Long questionId) {
        if(!repository.existsById(questionId))
            throw new NotFoundException("ERR_SVC_QUE_NOT_FOUND");

        List<Long> sectionQuestionIds = sectionQuestionsRepository.getSectionQuestionIdByQuestionId(questionId);
        Boolean questionResponseExists = false;
        if(!sectionQuestionIds.isEmpty()){
            questionResponseExists =  aiaDefaultResponseRepository.existsAIADefaultResponseForSectionQuestions(sectionQuestionIds);
        }
        return questionResponseExists;
    }

    @Override
    public Set<Question> getQuestionByIdsAndInstanceId(Set<Long> questionIds, Long instanceId) {
        assessmentInstanceService.getAssessmentInstanceByIdv2(instanceId);
        Set<Long> questionIdsByInstanceId = nonMTRepository.getQuestionIdsByInstanceId(instanceId);
        Set<Long> notFoundQuestions = questionIds.stream()
                .filter(id -> !questionIdsByInstanceId.contains(id))
                .collect(Collectors.toSet());
        if(!notFoundQuestions.isEmpty()) throw new NotFoundException(String.format("Questions ids %s not found",notFoundQuestions.stream().map(String::valueOf).collect(Collectors.joining(","))));
        return nonMTRepository.getQuestionByIds(questionIds);
    }

    @Override
    public Set<Question> getChildQuestion(Set<Long> questionIds, Long instanceId) {
        assessmentInstanceService.getAssessmentInstanceByIdv2(instanceId);
        Set<Long> parentQuestionIDs = /*nonMTRepository.getParentQuestionIDs(instanceId)*/null;
        Set<Long> childQuestionIDs;
/*
        do {
            childQuestionIDs = nonMTRepository.getChildQuestionIDs(parentQuestionIDs);
            parentQuestionIDs.addAll(childQuestionIDs);
        }
        while( !childQuestionIDs.isEmpty() );
        Set<Long> notFoundQuestions=questionIds.stream()
                .filter(id->!parentQuestionIDs.contains(id))
                .collect(Collectors.toSet());
        if(!notFoundQuestions.isEmpty())
            throw new NotFoundException(String.format("Questions ids %s not found",notFoundQuestions.stream().map(String::valueOf).collect(Collectors.joining(","))));
*/
        return nonMTRepository.getQuestionByIds(questionIds);

    }

    @Override
    public PageDTO<QuestionDTO> searchByQuestionByQuestionText(String domain, String subDomain, String questionText, String questionType, String questionSubType, String sortBy, String sortOrder, String language, Integer page, Integer size) {
        if(questionText!=null){
//            return searchService.searchByQuestionByQuestionText(domain, subDomain, questionText, questionType, questionSubType, sortBy, sortOrder, language, page, size);
        }
        Pageable pageable;
        if(sortBy!=null)
            pageable = PageRequest.of(page,size, Sort.by((sortOrder==null ||sortOrder.equals("ASC"))? Sort.Direction.ASC: Sort.Direction.DESC,sortBy));
        else
            pageable = PageRequest.of(page,size);
        Page<Question> pg = repository.getQuestions(domain, subDomain,
                                       questionType, questionSubType,
                                       language, pageable);
        Set<QuestionDTO> questions = pg.stream().map(questionDTOAssembler::toDTO).collect(Collectors.toSet());
        return new PageDTO<>(questions,pg.hasNext(),pg.getTotalElements());
    }

    @Override
    public String createAssessmentAttachment(MultipartFile file, Long entityId) {
        Attachment attachment = new Attachment();
        attachment.setEntityId(entityId);
        attachment.setEntityType("Question");
        attachment.setFileName(file.getOriginalFilename());
        attachment.setPublic(true);
        attachment.setMimeType(file.getContentType());
        try {
            return attachmentService.uploadAttachment(file,attachment).getCompleteURL();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Question> getQuestionByIds(Set<Long> questionIds) {
        List<Question> questions = repository.findAllById(questionIds);
        Set<Long> childQuestions = repository.getChildQuestionsIds(questionIds);
        while(!childQuestions.isEmpty()){
            questions.addAll(repository.findAllById(childQuestions));
            childQuestions = repository.getChildQuestionsIds(childQuestions);
        }
        return questions;
    }

    @Override
    public List<Question> getQuestionByIdsV2(Set<Long> questionIds) {
        return repository.findAllById(questionIds);
    }

    @Override
    public List<Question> getQuestionByIdsV3(Set<Long> questionIds) {
        return nonMTRepository.findAllById(questionIds);
    }

    @Override
    public Set<Long> getChildQuestionIds(Set<Long> questionIds) {
        return nonMTRepository.getChildQuestionIds(questionIds);
    }

    private Question createOptionsAndInitializeForGrid(Question question) {
        if(question.isGridType()){
            List<Options> options = question.getOptions();
            options = optionsRepository.saveAll(options);
            question.mapOptionMap(options);
        }
        return question;
    }
    private Question patchForGrid(Question question) {
        if(question.isGridType()){
            List<Options> options = question.getOptions();
            options = optionsRepository.saveAll(options);
            question.mapOptionMap(options);
        }
        return question;
    }
}
