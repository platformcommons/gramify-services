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


import com.platformcommons.platform.service.blockprofile.api.HHEconomicAspirationControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHEconomicAspirationDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHEconomicAspirationFacadeExt;


@RestController
@Slf4j
public class HHEconomicAspirationController implements HHEconomicAspirationControllerApi {

    private final HHEconomicAspirationFacadeExt hHEconomicAspirationFacadeExt;

    /**
     * Constructs a HHEconomicAspirationController with the specified facade.
     *
     * @param hHEconomicAspirationFacadeExt The HHEconomicAspiration facade extension to be used
     */
    public HHEconomicAspirationController(HHEconomicAspirationFacadeExt hHEconomicAspirationFacadeExt) {
        this.hHEconomicAspirationFacadeExt =hHEconomicAspirationFacadeExt;
    }

    /**
     * Creates a new HHEconomicAspiration.
     *
     * @param hHEconomicAspirationDTO The HHEconomicAspiration data to create
     * @return ResponseEntity containing the ID of the created HHEconomicAspiration
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHEconomicAspirationDTO hHEconomicAspirationDTO) {
        log.debug("Entry - create(HHEconomicAspirationDTO={})" , hHEconomicAspirationDTO);
        Long id = hHEconomicAspirationFacadeExt.save(hHEconomicAspirationDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHEconomicAspirationUri(id)).body(id);
    }

    /**
     * Updates an existing HHEconomicAspiration.
     *
     * @param hHEconomicAspirationDTO The HHEconomicAspiration data to update
     * @return ResponseEntity containing the updated HHEconomicAspiration data
     */
    @Override
    public ResponseEntity<HHEconomicAspirationDTO> update(@RequestBody HHEconomicAspirationDTO hHEconomicAspirationDTO) {
        log.debug("Entry - update(HHEconomicAspirationDTO={})", hHEconomicAspirationDTO);
        HHEconomicAspirationDTO updated = hHEconomicAspirationFacadeExt.update(hHEconomicAspirationDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHEconomicAspirations.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHEconomicAspirations
     */
    @Override
    public ResponseEntity<PageDTO<HHEconomicAspirationDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHEconomicAspirationDTO> result = hHEconomicAspirationFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHEconomicAspiration by their ID.
     *
     * @param id The ID of the HHEconomicAspiration to retrieve
     * @return ResponseEntity containing the HHEconomicAspiration data
     */
    @Override
    public ResponseEntity<HHEconomicAspirationDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHEconomicAspirationDTO dto = hHEconomicAspirationFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHEconomicAspiration by their ID with an optional reason.
     *
     * @param id     The ID of the HHEconomicAspiration to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHEconomicAspirationFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHEconomicAspirationDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHEconomicAspirationDTO> result = hHEconomicAspirationFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHEconomicAspirationUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
