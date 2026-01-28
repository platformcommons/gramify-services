package com.platformcommons.platform.service.assessment.reporting.controller;

import com.platformcommons.platform.service.assessment.dto.AssesseDimHierarchyResolvedEventDTO;
import com.platformcommons.platform.service.assessment.reporting.messaging.producer.AssesseHierarchyResolutionProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AssesseDimController {

    private final AssesseHierarchyResolutionProducer producer;


    @PostMapping("/api/v1/assessee/event")
    public ResponseEntity<?> emitEvent(@RequestBody AssesseDimHierarchyResolvedEventDTO dto){
        producer.assesseDimHierarchyResolved(dto);
        return ResponseEntity.ok().build();
    }


}
