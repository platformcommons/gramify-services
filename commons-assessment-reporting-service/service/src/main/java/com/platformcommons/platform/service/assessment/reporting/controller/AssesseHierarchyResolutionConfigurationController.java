package com.platformcommons.platform.service.assessment.reporting.controller;

import com.platformcommons.platform.service.assessment.dto.AssesseHierarchyResolutionConfigurationDTO;
import com.platformcommons.platform.service.assessment.reporting.facade.AssesseHierarchyResolutionConfigurationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api/v1/hierarchy-configurations")
@RequiredArgsConstructor
public class AssesseHierarchyResolutionConfigurationController {

    private final AssesseHierarchyResolutionConfigurationFacade facade;

    @PostMapping
    public ResponseEntity<AssesseHierarchyResolutionConfigurationDTO> createConfiguration(
            @Valid @RequestBody AssesseHierarchyResolutionConfigurationDTO dto) throws URISyntaxException {
        AssesseHierarchyResolutionConfigurationDTO result = facade.createConfiguration(dto);
        return ResponseEntity.created(new URI("/api/v1/hierarchy-configurations/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssesseHierarchyResolutionConfigurationDTO> updateConfiguration(
            @PathVariable Long id,
            @Valid @RequestBody AssesseHierarchyResolutionConfigurationDTO dto) {
        try {
            AssesseHierarchyResolutionConfigurationDTO result = facade.updateConfiguration(id, dto);
            return ResponseEntity.ok(result);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssesseHierarchyResolutionConfigurationDTO> getConfiguration(@PathVariable Long id) {
        return facade.findOne(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


}