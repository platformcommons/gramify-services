package com.platformcommons.platform.service.assessment.controller;

import com.platformcommons.platform.service.assessment.application.utility.PageUtil;
import com.platformcommons.platform.service.assessment.client.QuestionAPI;
import com.platformcommons.platform.service.assessment.dto.QuestionDTO;
import com.platformcommons.platform.service.assessment.facade.QuestionFacade;
import com.platformcommons.platform.service.assessment.messaging.producer.QuestionEventProducer;
import com.platformcommons.platform.service.dto.base.PageDTO;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@Validated
@RestController
@RequestMapping
@Tag(name = "Question")
@RequiredArgsConstructor
public class QuestionController implements QuestionAPI {

    private final QuestionFacade facade;
    private final QuestionEventProducer producer;


    @Override
    public ResponseEntity<Boolean> checkQuestionResponseExists(Long questionId) {
        return new ResponseEntity<>(facade.checkQuestionResponseExists(questionId), HttpStatus.OK);
    }
    @PostMapping("/duplicate/{questionId}")
    public ResponseEntity<QuestionDTO> duplicateQuestion(@PathVariable Long questionId) {
        return new ResponseEntity<>(facade.duplicateQuestion(questionId), HttpStatus.CREATED);
    }
    @Override
    public ResponseEntity<QuestionDTO> createQuestionV2(QuestionDTO questionDTO) {
        questionDTO = facade.createQuestionWithResponse(questionDTO);
        producer.questionCreated(questionDTO);
        return new ResponseEntity<>(questionDTO, CREATED);
    }

    @Override
    public ResponseEntity<QuestionDTO> updateQuestionV2(QuestionDTO questionDTO) {
        questionDTO = facade.updateQuestionWithResponse(questionDTO);
        producer.questionUpdated(questionDTO);
        return new ResponseEntity<>(questionDTO, OK);
    }

    @GetMapping("/api/v2/questions/questions/instance/{instanceId}")
    @ApiOperation(value = "getQuestionByIdsAndInstanceId", nickname = "getQuestionByIdsAndInstanceId", notes = "",  tags={ "Question", })
    public ResponseEntity<Set<QuestionDTO>> getQuestionByIdsAndInstanceId(@RequestParam Set<Long> questionIds,@PathVariable Long instanceId) {
        return new ResponseEntity<>(facade.getQuestionByIdsAndInstanceId(questionIds,instanceId), OK);
    }

    @GetMapping("/api/v3/questions/questions/instance")
    @ApiOperation(value = "getQuestionByIdsAndInstanceIdV3", nickname = "getQuestionByIdsAndInstanceIdV3", notes = "",  tags={ "Question" })
    public ResponseEntity<Set<QuestionDTO>> getQuestionByIdsAndInstanceIdV3(@RequestParam Set<Long> questionIds,@RequestParam Long instanceId) {
        return new ResponseEntity<>(facade.getQuestionByIdsAndInstanceId(questionIds,instanceId), OK);
    }

    @GetMapping("/api/v2/questions/childquestion/instance/{instanceId}")
    @ApiOperation(value = "getChildQuestionByIdsAndInstanceId", nickname = "getChildQuestionByIdsAndInstanceId", notes = "",  tags={ "Question", })
    public ResponseEntity<Set<QuestionDTO>> getChildQuestionByIdsAndInstanceId(@RequestParam Set<Long> questionIds,
                                                                                @PathVariable Long instanceId) {
        return new ResponseEntity<>(facade.getChildQuestion(questionIds,instanceId), OK);
    }

    @GetMapping("/api/v3/questions/childquestion/instance")
    @ApiOperation(value = "getChildQuestionByIdsAndInstanceIdV3", nickname = "getChildQuestionByIdsAndInstanceIdV3", notes = "",  tags={ "Question", })
    public ResponseEntity<Set<QuestionDTO>> getChildQuestionByIdsAndInstanceIdV3(@RequestParam Set<Long> questionIds,
                                                                               @RequestParam Long instanceId) {
        return new ResponseEntity<>(facade.getChildQuestion(questionIds,instanceId), OK);
    }

    @GetMapping("/api/v2/questions/question/search")
    @ApiOperation(value = "searchByQuestionByQuestionText", nickname = "searchByQuestionByQuestionText", notes = "",  tags={ "Question", })
    public ResponseEntity<PageDTO<QuestionDTO>> searchByQuestionByQuestionText(@RequestParam(required = false) String domain,
                                                                               @RequestParam(required = false) String subDomain,
                                                                               @RequestParam(required = false) String questionText,
                                                                               @RequestParam(required = false) String questionType,
                                                                               @RequestParam(required = false) String questionSubType,
                                                                               @RequestParam(required = false) String sortBy,
                                                                               @RequestParam(required = false) String sortOrder,
                                                                               @RequestParam(required = false) String language,
                                                                               @RequestParam Integer page,
                                                                               @RequestParam Integer size
    ){
        return PageUtil.getResponseEntity(facade.searchByQuestionByQuestionText(domain,subDomain,questionText,questionType,questionSubType,sortBy,sortOrder,language,page,size));
    }


    @GetMapping("/api/v1/questions")
    @ApiOperation(value = "getQuestionById", nickname = "getQuestionById", notes = "",  tags={ "Question", })
    public ResponseEntity<Set<QuestionDTO>> getQuestionById(@RequestParam Set<Long> ids) {
        return new ResponseEntity<>(facade.getQuestionByIds(ids), OK);
    }
    @GetMapping("/api/v1/questions/questionids")
    @ApiOperation(value = "getQuestionByQuestionIds", nickname = "getQuestionByQuestionIds", notes = "",  tags={ "Question", })
    public ResponseEntity<Set<QuestionDTO>> getQuestionByQuestionIds(@RequestParam Set<Long> questionIds) {
        return new ResponseEntity<>(facade.getQuestionByIdsV2(questionIds), HttpStatus.OK);
    }

    @GetMapping("/api/v2/questions/questionids")
    @ApiOperation(value = "getQuestionByQuestionIdsV2", nickname = "getQuestionByQuestionIdsV2", notes = "",  tags={ "Question", })
    public ResponseEntity<Set<QuestionDTO>> getQuestionByQuestionIdsV2(@RequestParam Set<Long> questionIds) {
        return new ResponseEntity<>(facade.getQuestionByIdsV3(questionIds), HttpStatus.OK);
    }

}
