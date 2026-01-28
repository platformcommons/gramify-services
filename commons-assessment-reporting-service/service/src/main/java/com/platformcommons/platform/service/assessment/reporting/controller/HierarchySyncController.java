package com.platformcommons.platform.service.assessment.reporting.controller;

import com.platformcommons.platform.service.assessment.reporting.application.HierarchySyncService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/sync/hierarchies")
@RequiredArgsConstructor
public class HierarchySyncController {

    private final HierarchySyncService hierarchySyncService;

    @PostMapping("/{assessmentId}")
    @ApiOperation(value = "Resync Hierarchy Summary Tables", notes = "Deletes all summary data for an assessment and re-emits Kafka events to rebuild it.")
    public ResponseEntity<String> syncHierarchySummaries(@PathVariable Long assessmentId) {
        hierarchySyncService.resyncSummariesForAssessment(assessmentId);
        return ResponseEntity.accepted().body("Hierarchy summary synchronization process initiated for assessmentId: " + assessmentId);
    }
}