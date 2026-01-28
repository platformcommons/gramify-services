package com.platformcommons.platform.service.blockprofile.controller;

import com.platformcommons.platform.service.dto.base.PageDTO;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.*;


import com.platformcommons.platform.service.blockprofile.api.ExistingStorageIssueControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.ExistingStorageIssueDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.ExistingStorageIssueFacadeExt;


@RestController
@Slf4j
public class ExistingStorageIssueController implements ExistingStorageIssueControllerApi {

    private final ExistingStorageIssueFacadeExt existingStorageIssueFacadeExt;

    /**
     * Constructs a ExistingStorageIssueController with the specified facade.
     *
     * @param existingStorageIssueFacadeExt The ExistingStorageIssue facade extension to be used
     */
    public ExistingStorageIssueController(ExistingStorageIssueFacadeExt existingStorageIssueFacadeExt) {
        this.existingStorageIssueFacadeExt =existingStorageIssueFacadeExt;
    }

    /**
     * Creates a new ExistingStorageIssue.
     *
     * @param existingStorageIssueDTO The ExistingStorageIssue data to create
     * @return ResponseEntity containing the ID of the created ExistingStorageIssue
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody ExistingStorageIssueDTO existingStorageIssueDTO) {
        log.debug("Entry - create(ExistingStorageIssueDTO={})" , existingStorageIssueDTO);
        Long id = existingStorageIssueFacadeExt.save(existingStorageIssueDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createExistingStorageIssueUri(id)).body(id);
    }

    /**
     * Updates an existing ExistingStorageIssue.
     *
     * @param existingStorageIssueDTO The ExistingStorageIssue data to update
     * @return ResponseEntity containing the updated ExistingStorageIssue data
     */
    @Override
    public ResponseEntity<ExistingStorageIssueDTO> update(@RequestBody ExistingStorageIssueDTO existingStorageIssueDTO) {
        log.debug("Entry - update(ExistingStorageIssueDTO={})", existingStorageIssueDTO);
        ExistingStorageIssueDTO updated = existingStorageIssueFacadeExt.update(existingStorageIssueDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of ExistingStorageIssues.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of ExistingStorageIssues
     */
    @Override
    public ResponseEntity<PageDTO<ExistingStorageIssueDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<ExistingStorageIssueDTO> result = existingStorageIssueFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a ExistingStorageIssue by their ID.
     *
     * @param id The ID of the ExistingStorageIssue to retrieve
     * @return ResponseEntity containing the ExistingStorageIssue data
     */
    @Override
    public ResponseEntity<ExistingStorageIssueDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        ExistingStorageIssueDTO dto = existingStorageIssueFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a ExistingStorageIssue by their ID with an optional reason.
     *
     * @param id     The ID of the ExistingStorageIssue to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        existingStorageIssueFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<ExistingStorageIssueDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<ExistingStorageIssueDTO> result = existingStorageIssueFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createExistingStorageIssueUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
