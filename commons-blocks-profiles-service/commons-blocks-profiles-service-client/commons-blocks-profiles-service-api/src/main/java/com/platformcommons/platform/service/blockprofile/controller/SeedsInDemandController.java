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


import com.platformcommons.platform.service.blockprofile.api.SeedsInDemandControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.SeedsInDemandDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.SeedsInDemandFacadeExt;


@RestController
@Slf4j
public class SeedsInDemandController implements SeedsInDemandControllerApi {

    private final SeedsInDemandFacadeExt seedsInDemandFacadeExt;

    /**
     * Constructs a SeedsInDemandController with the specified facade.
     *
     * @param seedsInDemandFacadeExt The SeedsInDemand facade extension to be used
     */
    public SeedsInDemandController(SeedsInDemandFacadeExt seedsInDemandFacadeExt) {
        this.seedsInDemandFacadeExt =seedsInDemandFacadeExt;
    }

    /**
     * Creates a new SeedsInDemand.
     *
     * @param seedsInDemandDTO The SeedsInDemand data to create
     * @return ResponseEntity containing the ID of the created SeedsInDemand
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody SeedsInDemandDTO seedsInDemandDTO) {
        log.debug("Entry - create(SeedsInDemandDTO={})" , seedsInDemandDTO);
        Long id = seedsInDemandFacadeExt.save(seedsInDemandDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createSeedsInDemandUri(id)).body(id);
    }

    /**
     * Updates an existing SeedsInDemand.
     *
     * @param seedsInDemandDTO The SeedsInDemand data to update
     * @return ResponseEntity containing the updated SeedsInDemand data
     */
    @Override
    public ResponseEntity<SeedsInDemandDTO> update(@RequestBody SeedsInDemandDTO seedsInDemandDTO) {
        log.debug("Entry - update(SeedsInDemandDTO={})", seedsInDemandDTO);
        SeedsInDemandDTO updated = seedsInDemandFacadeExt.update(seedsInDemandDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of SeedsInDemands.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of SeedsInDemands
     */
    @Override
    public ResponseEntity<PageDTO<SeedsInDemandDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<SeedsInDemandDTO> result = seedsInDemandFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a SeedsInDemand by their ID.
     *
     * @param id The ID of the SeedsInDemand to retrieve
     * @return ResponseEntity containing the SeedsInDemand data
     */
    @Override
    public ResponseEntity<SeedsInDemandDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        SeedsInDemandDTO dto = seedsInDemandFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a SeedsInDemand by their ID with an optional reason.
     *
     * @param id     The ID of the SeedsInDemand to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        seedsInDemandFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<SeedsInDemandDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<SeedsInDemandDTO> result = seedsInDemandFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createSeedsInDemandUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
