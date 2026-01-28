package com.platformcommons.platform.service.assessment.controller;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.assessment.facade.QuestionFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping
@Tag(name = "AssessmentQuestionAttachment")
@RequiredArgsConstructor
public class AssessmentQuestionAttachmentController {

    private final QuestionFacade questionFacade;

    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping("/api/v1/attachment/assessment")
    @ApiOperation(value = "createAssessmentAttachment", nickname = "createAssessmentAttachment", notes = "",  tags={ "AssessmentQuestionAttachment", })
    public ResponseEntity<String> createAssessmentAttachment(
            @RequestPart(value = "file",required = false) MultipartFile file,
            @RequestParam(name = "entityId",required = false) Long entityId) throws IOException {
        if( (file==null || file.isEmpty()) ){
            throw new NotFoundException("Please attach the file to be uploaded");
        }
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(questionFacade.createAssessmentQuestionAttachment(file,entityId));
    }
}
