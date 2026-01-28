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


import com.platformcommons.platform.service.blockprofile.api.HHSocialConstraintsControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHSocialConstraintsDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHSocialConstraintsFacadeExt;


@RestController
@Slf4j
public class HHSocialConstraintsController implements HHSocialConstraintsControllerApi {

    private final HHSocialConstraintsFacadeExt hHSocialConstraintsFacadeExt;

    /**
     * Constructs a HHSocialConstraintsController with the specified facade.
     *
     * @param hHSocialConstraintsFacadeExt The HHSocialConstraints facade extension to be used
     */
    public HHSocialConstraintsController(HHSocialConstraintsFacadeExt hHSocialConstraintsFacadeExt) {
        this.hHSocialConstraintsFacadeExt =hHSocialConstraintsFacadeExt;
    }

    /**
     * Creates a new HHSocialConstraints.
     *
     * @param hHSocialConstraintsDTO The HHSocialConstraints data to create
     * @return ResponseEntity containing the ID of the created HHSocialConstraints
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHSocialConstraintsDTO hHSocialConstraintsDTO) {
        log.debug("Entry - create(HHSocialConstraintsDTO={})" , hHSocialConstraintsDTO);
        Long id = hHSocialConstraintsFacadeExt.save(hHSocialConstraintsDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHSocialConstraintsUri(id)).body(id);
    }

    /**
     * Updates an existing HHSocialConstraints.
     *
     * @param hHSocialConstraintsDTO The HHSocialConstraints data to update
     * @return ResponseEntity containing the updated HHSocialConstraints data
     */
    @Override
    public ResponseEntity<HHSocialConstraintsDTO> update(@RequestBody HHSocialConstraintsDTO hHSocialConstraintsDTO) {
        log.debug("Entry - update(HHSocialConstraintsDTO={})", hHSocialConstraintsDTO);
        HHSocialConstraintsDTO updated = hHSocialConstraintsFacadeExt.update(hHSocialConstraintsDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHSocialConstraintss.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHSocialConstraintss
     */
    @Override
    public ResponseEntity<PageDTO<HHSocialConstraintsDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHSocialConstraintsDTO> result = hHSocialConstraintsFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHSocialConstraints by their ID.
     *
     * @param id The ID of the HHSocialConstraints to retrieve
     * @return ResponseEntity containing the HHSocialConstraints data
     */
    @Override
    public ResponseEntity<HHSocialConstraintsDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHSocialConstraintsDTO dto = hHSocialConstraintsFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHSocialConstraints by their ID with an optional reason.
     *
     * @param id     The ID of the HHSocialConstraints to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHSocialConstraintsFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHSocialConstraintsDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHSocialConstraintsDTO> result = hHSocialConstraintsFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHSocialConstraintsUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
