package com.platformcommons.platform.service.assessment.reporting.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.platformcommons.platform.service.assessment.dto.AssessmentInstanceAssesseDTO;
import com.platformcommons.platform.service.assessment.reporting.messaging.constants.MessagingConstant;
import com.platformcommons.platform.service.assessment.reporting.messaging.eventHandler.AssessmentInstanceAssesseEventHandler;
import com.platformcommons.platform.service.dto.event.EventDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test/assessment-events")
@RequiredArgsConstructor
@Slf4j
public class AssessmentTestController {

    private final AssessmentInstanceAssesseEventHandler assessmentInstanceAssesseEventHandler;

    @PostMapping("/create")
    public ResponseEntity<String> publishCreateEvent(@RequestBody AssessmentInstanceAssesseDTO assesseDTO) {
        log.info("Received request to publish a 'create' event for DTO: {}", assesseDTO);
        try {
            assessmentInstanceAssesseEventHandler.createAssessmentInstanceAssesseEvent(assesseDTO);
            return ResponseEntity.ok("'Create' event published successfully.");

        } catch (Exception e) {
            log.error("Failed to publish 'create' event", e);
            return ResponseEntity.internalServerError().body("Failed to publish event: " + e.getMessage());
        }
    }

    @PostMapping("/update")
    public ResponseEntity<String> publishUpdateEvent(@RequestBody AssessmentInstanceAssesseDTO assesseDTO) {
        log.info("Received request to publish an 'update' event for DTO: {}", assesseDTO);

        try {
            assessmentInstanceAssesseEventHandler.updateAssessmentInstanceAssesseEvent(assesseDTO);
            return ResponseEntity.ok("'Update' event published successfully.");
        } catch (Exception e) {
            log.error("Failed to publish 'update' event", e);
            return ResponseEntity.internalServerError().body("Failed to publish event: " + e.getMessage());
        }
    }
}