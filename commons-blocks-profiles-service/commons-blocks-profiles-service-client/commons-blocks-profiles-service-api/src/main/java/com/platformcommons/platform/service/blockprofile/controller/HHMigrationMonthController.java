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


import com.platformcommons.platform.service.blockprofile.api.HHMigrationMonthControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHMigrationMonthDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHMigrationMonthFacadeExt;


@RestController
@Slf4j
public class HHMigrationMonthController implements HHMigrationMonthControllerApi {

    private final HHMigrationMonthFacadeExt hHMigrationMonthFacadeExt;

    /**
     * Constructs a HHMigrationMonthController with the specified facade.
     *
     * @param hHMigrationMonthFacadeExt The HHMigrationMonth facade extension to be used
     */
    public HHMigrationMonthController(HHMigrationMonthFacadeExt hHMigrationMonthFacadeExt) {
        this.hHMigrationMonthFacadeExt =hHMigrationMonthFacadeExt;
    }

    /**
     * Creates a new HHMigrationMonth.
     *
     * @param hHMigrationMonthDTO The HHMigrationMonth data to create
     * @return ResponseEntity containing the ID of the created HHMigrationMonth
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHMigrationMonthDTO hHMigrationMonthDTO) {
        log.debug("Entry - create(HHMigrationMonthDTO={})" , hHMigrationMonthDTO);
        Long id = hHMigrationMonthFacadeExt.save(hHMigrationMonthDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHMigrationMonthUri(id)).body(id);
    }

    /**
     * Updates an existing HHMigrationMonth.
     *
     * @param hHMigrationMonthDTO The HHMigrationMonth data to update
     * @return ResponseEntity containing the updated HHMigrationMonth data
     */
    @Override
    public ResponseEntity<HHMigrationMonthDTO> update(@RequestBody HHMigrationMonthDTO hHMigrationMonthDTO) {
        log.debug("Entry - update(HHMigrationMonthDTO={})", hHMigrationMonthDTO);
        HHMigrationMonthDTO updated = hHMigrationMonthFacadeExt.update(hHMigrationMonthDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHMigrationMonths.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHMigrationMonths
     */
    @Override
    public ResponseEntity<PageDTO<HHMigrationMonthDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHMigrationMonthDTO> result = hHMigrationMonthFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHMigrationMonth by their ID.
     *
     * @param id The ID of the HHMigrationMonth to retrieve
     * @return ResponseEntity containing the HHMigrationMonth data
     */
    @Override
    public ResponseEntity<HHMigrationMonthDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHMigrationMonthDTO dto = hHMigrationMonthFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHMigrationMonth by their ID with an optional reason.
     *
     * @param id     The ID of the HHMigrationMonth to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHMigrationMonthFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHMigrationMonthDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHMigrationMonthDTO> result = hHMigrationMonthFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHMigrationMonthUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
