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


import com.platformcommons.platform.service.blockprofile.api.HHGovernanceAspirationControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHGovernanceAspirationDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHGovernanceAspirationFacadeExt;


@RestController
@Slf4j
public class HHGovernanceAspirationController implements HHGovernanceAspirationControllerApi {

    private final HHGovernanceAspirationFacadeExt hHGovernanceAspirationFacadeExt;

    /**
     * Constructs a HHGovernanceAspirationController with the specified facade.
     *
     * @param hHGovernanceAspirationFacadeExt The HHGovernanceAspiration facade extension to be used
     */
    public HHGovernanceAspirationController(HHGovernanceAspirationFacadeExt hHGovernanceAspirationFacadeExt) {
        this.hHGovernanceAspirationFacadeExt =hHGovernanceAspirationFacadeExt;
    }

    /**
     * Creates a new HHGovernanceAspiration.
     *
     * @param hHGovernanceAspirationDTO The HHGovernanceAspiration data to create
     * @return ResponseEntity containing the ID of the created HHGovernanceAspiration
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHGovernanceAspirationDTO hHGovernanceAspirationDTO) {
        log.debug("Entry - create(HHGovernanceAspirationDTO={})" , hHGovernanceAspirationDTO);
        Long id = hHGovernanceAspirationFacadeExt.save(hHGovernanceAspirationDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHGovernanceAspirationUri(id)).body(id);
    }

    /**
     * Updates an existing HHGovernanceAspiration.
     *
     * @param hHGovernanceAspirationDTO The HHGovernanceAspiration data to update
     * @return ResponseEntity containing the updated HHGovernanceAspiration data
     */
    @Override
    public ResponseEntity<HHGovernanceAspirationDTO> update(@RequestBody HHGovernanceAspirationDTO hHGovernanceAspirationDTO) {
        log.debug("Entry - update(HHGovernanceAspirationDTO={})", hHGovernanceAspirationDTO);
        HHGovernanceAspirationDTO updated = hHGovernanceAspirationFacadeExt.update(hHGovernanceAspirationDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHGovernanceAspirations.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHGovernanceAspirations
     */
    @Override
    public ResponseEntity<PageDTO<HHGovernanceAspirationDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHGovernanceAspirationDTO> result = hHGovernanceAspirationFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHGovernanceAspiration by their ID.
     *
     * @param id The ID of the HHGovernanceAspiration to retrieve
     * @return ResponseEntity containing the HHGovernanceAspiration data
     */
    @Override
    public ResponseEntity<HHGovernanceAspirationDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHGovernanceAspirationDTO dto = hHGovernanceAspirationFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHGovernanceAspiration by their ID with an optional reason.
     *
     * @param id     The ID of the HHGovernanceAspiration to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHGovernanceAspirationFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHGovernanceAspirationDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHGovernanceAspirationDTO> result = hHGovernanceAspirationFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHGovernanceAspirationUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
