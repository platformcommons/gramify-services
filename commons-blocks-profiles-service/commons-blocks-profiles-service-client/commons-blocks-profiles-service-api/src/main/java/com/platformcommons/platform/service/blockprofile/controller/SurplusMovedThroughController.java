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


import com.platformcommons.platform.service.blockprofile.api.SurplusMovedThroughControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.SurplusMovedThroughDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.SurplusMovedThroughFacadeExt;


@RestController
@Slf4j
public class SurplusMovedThroughController implements SurplusMovedThroughControllerApi {

    private final SurplusMovedThroughFacadeExt surplusMovedThroughFacadeExt;

    /**
     * Constructs a SurplusMovedThroughController with the specified facade.
     *
     * @param surplusMovedThroughFacadeExt The SurplusMovedThrough facade extension to be used
     */
    public SurplusMovedThroughController(SurplusMovedThroughFacadeExt surplusMovedThroughFacadeExt) {
        this.surplusMovedThroughFacadeExt =surplusMovedThroughFacadeExt;
    }

    /**
     * Creates a new SurplusMovedThrough.
     *
     * @param surplusMovedThroughDTO The SurplusMovedThrough data to create
     * @return ResponseEntity containing the ID of the created SurplusMovedThrough
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody SurplusMovedThroughDTO surplusMovedThroughDTO) {
        log.debug("Entry - create(SurplusMovedThroughDTO={})" , surplusMovedThroughDTO);
        Long id = surplusMovedThroughFacadeExt.save(surplusMovedThroughDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createSurplusMovedThroughUri(id)).body(id);
    }

    /**
     * Updates an existing SurplusMovedThrough.
     *
     * @param surplusMovedThroughDTO The SurplusMovedThrough data to update
     * @return ResponseEntity containing the updated SurplusMovedThrough data
     */
    @Override
    public ResponseEntity<SurplusMovedThroughDTO> update(@RequestBody SurplusMovedThroughDTO surplusMovedThroughDTO) {
        log.debug("Entry - update(SurplusMovedThroughDTO={})", surplusMovedThroughDTO);
        SurplusMovedThroughDTO updated = surplusMovedThroughFacadeExt.update(surplusMovedThroughDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of SurplusMovedThroughs.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of SurplusMovedThroughs
     */
    @Override
    public ResponseEntity<PageDTO<SurplusMovedThroughDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<SurplusMovedThroughDTO> result = surplusMovedThroughFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a SurplusMovedThrough by their ID.
     *
     * @param id The ID of the SurplusMovedThrough to retrieve
     * @return ResponseEntity containing the SurplusMovedThrough data
     */
    @Override
    public ResponseEntity<SurplusMovedThroughDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        SurplusMovedThroughDTO dto = surplusMovedThroughFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a SurplusMovedThrough by their ID with an optional reason.
     *
     * @param id     The ID of the SurplusMovedThrough to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        surplusMovedThroughFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<SurplusMovedThroughDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<SurplusMovedThroughDTO> result = surplusMovedThroughFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createSurplusMovedThroughUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
