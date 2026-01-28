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


import com.platformcommons.platform.service.blockprofile.api.HHInfrastructureAspirationControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHInfrastructureAspirationDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHInfrastructureAspirationFacadeExt;


@RestController
@Slf4j
public class HHInfrastructureAspirationController implements HHInfrastructureAspirationControllerApi {

    private final HHInfrastructureAspirationFacadeExt hHInfrastructureAspirationFacadeExt;

    /**
     * Constructs a HHInfrastructureAspirationController with the specified facade.
     *
     * @param hHInfrastructureAspirationFacadeExt The HHInfrastructureAspiration facade extension to be used
     */
    public HHInfrastructureAspirationController(HHInfrastructureAspirationFacadeExt hHInfrastructureAspirationFacadeExt) {
        this.hHInfrastructureAspirationFacadeExt =hHInfrastructureAspirationFacadeExt;
    }

    /**
     * Creates a new HHInfrastructureAspiration.
     *
     * @param hHInfrastructureAspirationDTO The HHInfrastructureAspiration data to create
     * @return ResponseEntity containing the ID of the created HHInfrastructureAspiration
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHInfrastructureAspirationDTO hHInfrastructureAspirationDTO) {
        log.debug("Entry - create(HHInfrastructureAspirationDTO={})" , hHInfrastructureAspirationDTO);
        Long id = hHInfrastructureAspirationFacadeExt.save(hHInfrastructureAspirationDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHInfrastructureAspirationUri(id)).body(id);
    }

    /**
     * Updates an existing HHInfrastructureAspiration.
     *
     * @param hHInfrastructureAspirationDTO The HHInfrastructureAspiration data to update
     * @return ResponseEntity containing the updated HHInfrastructureAspiration data
     */
    @Override
    public ResponseEntity<HHInfrastructureAspirationDTO> update(@RequestBody HHInfrastructureAspirationDTO hHInfrastructureAspirationDTO) {
        log.debug("Entry - update(HHInfrastructureAspirationDTO={})", hHInfrastructureAspirationDTO);
        HHInfrastructureAspirationDTO updated = hHInfrastructureAspirationFacadeExt.update(hHInfrastructureAspirationDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHInfrastructureAspirations.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHInfrastructureAspirations
     */
    @Override
    public ResponseEntity<PageDTO<HHInfrastructureAspirationDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHInfrastructureAspirationDTO> result = hHInfrastructureAspirationFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHInfrastructureAspiration by their ID.
     *
     * @param id The ID of the HHInfrastructureAspiration to retrieve
     * @return ResponseEntity containing the HHInfrastructureAspiration data
     */
    @Override
    public ResponseEntity<HHInfrastructureAspirationDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHInfrastructureAspirationDTO dto = hHInfrastructureAspirationFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHInfrastructureAspiration by their ID with an optional reason.
     *
     * @param id     The ID of the HHInfrastructureAspiration to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHInfrastructureAspirationFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHInfrastructureAspirationDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHInfrastructureAspirationDTO> result = hHInfrastructureAspirationFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHInfrastructureAspirationUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
