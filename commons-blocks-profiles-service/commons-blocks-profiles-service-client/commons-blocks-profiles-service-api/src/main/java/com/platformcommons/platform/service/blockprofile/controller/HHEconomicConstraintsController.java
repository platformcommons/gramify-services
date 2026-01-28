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


import com.platformcommons.platform.service.blockprofile.api.HHEconomicConstraintsControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHEconomicConstraintsDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHEconomicConstraintsFacadeExt;


@RestController
@Slf4j
public class HHEconomicConstraintsController implements HHEconomicConstraintsControllerApi {

    private final HHEconomicConstraintsFacadeExt hHEconomicConstraintsFacadeExt;

    /**
     * Constructs a HHEconomicConstraintsController with the specified facade.
     *
     * @param hHEconomicConstraintsFacadeExt The HHEconomicConstraints facade extension to be used
     */
    public HHEconomicConstraintsController(HHEconomicConstraintsFacadeExt hHEconomicConstraintsFacadeExt) {
        this.hHEconomicConstraintsFacadeExt =hHEconomicConstraintsFacadeExt;
    }

    /**
     * Creates a new HHEconomicConstraints.
     *
     * @param hHEconomicConstraintsDTO The HHEconomicConstraints data to create
     * @return ResponseEntity containing the ID of the created HHEconomicConstraints
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHEconomicConstraintsDTO hHEconomicConstraintsDTO) {
        log.debug("Entry - create(HHEconomicConstraintsDTO={})" , hHEconomicConstraintsDTO);
        Long id = hHEconomicConstraintsFacadeExt.save(hHEconomicConstraintsDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHEconomicConstraintsUri(id)).body(id);
    }

    /**
     * Updates an existing HHEconomicConstraints.
     *
     * @param hHEconomicConstraintsDTO The HHEconomicConstraints data to update
     * @return ResponseEntity containing the updated HHEconomicConstraints data
     */
    @Override
    public ResponseEntity<HHEconomicConstraintsDTO> update(@RequestBody HHEconomicConstraintsDTO hHEconomicConstraintsDTO) {
        log.debug("Entry - update(HHEconomicConstraintsDTO={})", hHEconomicConstraintsDTO);
        HHEconomicConstraintsDTO updated = hHEconomicConstraintsFacadeExt.update(hHEconomicConstraintsDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHEconomicConstraintss.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHEconomicConstraintss
     */
    @Override
    public ResponseEntity<PageDTO<HHEconomicConstraintsDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHEconomicConstraintsDTO> result = hHEconomicConstraintsFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHEconomicConstraints by their ID.
     *
     * @param id The ID of the HHEconomicConstraints to retrieve
     * @return ResponseEntity containing the HHEconomicConstraints data
     */
    @Override
    public ResponseEntity<HHEconomicConstraintsDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHEconomicConstraintsDTO dto = hHEconomicConstraintsFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHEconomicConstraints by their ID with an optional reason.
     *
     * @param id     The ID of the HHEconomicConstraints to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHEconomicConstraintsFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHEconomicConstraintsDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHEconomicConstraintsDTO> result = hHEconomicConstraintsFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHEconomicConstraintsUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
