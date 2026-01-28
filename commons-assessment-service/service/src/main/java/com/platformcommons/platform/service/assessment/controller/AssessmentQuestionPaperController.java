package com.platformcommons.platform.service.assessment.controller;

import com.platformcommons.platform.service.assessment.client.AssessmentQuestionPaperAPI;
import com.platformcommons.platform.service.assessment.dto.AssessmentQuestionPaperDTO;
import com.platformcommons.platform.service.assessment.facade.AssessmentQuestionPaperFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Validated
@RestController
@RequestMapping
@Tag(name = "AssessmentQuestionPaper")
@RequiredArgsConstructor
public class AssessmentQuestionPaperController implements AssessmentQuestionPaperAPI {

    private final AssessmentQuestionPaperFacade facade;
    @Override
    public ResponseEntity<Long> createAssessmentQuestionPaper(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        return new ResponseEntity<>(facade.createAssessmentQuestionPaper(assessmentQuestionPaperDTO), HttpStatus.CREATED);
    }


    @GetMapping("/api/v2/assessmentQuestionPapers")
    @ApiOperation(value = "getAllAssessmentQuestionPapersByAssessmentInstance", nickname = "getAllAssessmentQuestionPapersByAssessmentInstance", tags={ "AssessmentQuestionPaper", })
    public ResponseEntity<Set<AssessmentQuestionPaperDTO>> getAllAssessmentQuestionPapersByAssessmentInstance(Long instanceId) {
//        AuthorityEvaluator.preAuthorizeAPIKey("ASSESSMENT_ORG.READ_ASSESSMENT_QUESTION_PAPER");
        return ResponseEntity.ok(facade.getAllAssessmentQuestionPapersOfInstance(instanceId));
    }

    @Override
    public ResponseEntity<AssessmentQuestionPaperDTO> updateAssessmentQuestionPaperV3(AssessmentQuestionPaperDTO assessmentQuestionPaperDTO) {
        return new ResponseEntity<>(facade.updateAssessmentQuestionPaperV3(assessmentQuestionPaperDTO), HttpStatus.CREATED);
    }

}

