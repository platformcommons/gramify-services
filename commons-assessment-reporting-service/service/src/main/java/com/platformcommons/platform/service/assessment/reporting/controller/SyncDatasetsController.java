package com.platformcommons.platform.service.assessment.reporting.controller;

import com.platformcommons.platform.service.assessment.reporting.application.context.EventContext;
import com.platformcommons.platform.service.assessment.reporting.facade.AssessmentInstanceDimFacade;
import com.platformcommons.platform.service.assessment.reporting.facade.SyncDatasetsFacade;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sync-datasets")
@Tag(name = "SyncDatasets", description = "SyncDatasets API's for Commons Assessment reporting service.")
public class SyncDatasetsController {

    @Autowired
    private SyncDatasetsFacade syncDatasetsFacade;
    @Autowired
    private AssessmentInstanceDimFacade assessmentInstanceDimFacade;
    @PatchMapping("/assessment/{assessment}")
    @ApiOperation(value = "Sync Assessment Data", notes = "Syncs the assessment data for the given assessment id.",tags = "SyncDatasets")
    /** 1. Fetches Assessment, AssessmentQuestionPaper, Question in AssessmentQuestionPaper and  Assessment Instance
     *  2. Sync Question Dims, Option Dim
     *  3. Sync Assessment Instance Dim, Question Paper Section Dims,Section Question Dims
     *  4. Sync QuestionFact, OptionFact, SkillFact
     *  5. UserContributionDim
     * @param assessment
     */
    public void syncAssessmentData(@PathVariable Long assessment, @RequestParam String linkedSystem) {
        EventContext.setSystemEvent(linkedSystem);
        syncDatasetsFacade.syncAssessmentData(assessment);
    }

    @PatchMapping("/assessment/respondent/{assessment}")
    @ApiOperation(value = "Sync Assessment Respondent Data", notes = "Syncs the assessment respondent data for the given assessment id.",tags = "SyncDatasets")
    public void syncAssesseData(@PathVariable Long assessment, @RequestParam Boolean sa,@RequestParam String linkedSystem) {
        EventContext.setSystemEvent(linkedSystem);
        syncDatasetsFacade.syncAssesseData(assessment,sa);
    }


    @DeleteMapping("/assessment/{assessmentId}")
    @ApiOperation(value = "Deletes Assessment Data", notes = "",tags = "SyncDatasets")
    public void cleanUpAssessmentData(@PathVariable Long assessmentId, @RequestParam String linkedSystem) {
        EventContext.setSystemEvent(linkedSystem);
        assessmentInstanceDimFacade.cleanUpAssessmentData(assessmentId);
    }

}
