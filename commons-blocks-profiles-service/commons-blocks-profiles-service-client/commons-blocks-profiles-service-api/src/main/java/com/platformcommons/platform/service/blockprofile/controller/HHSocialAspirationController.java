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


import com.platformcommons.platform.service.blockprofile.api.HHSocialAspirationControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HHSocialAspirationDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HHSocialAspirationFacadeExt;


@RestController
@Slf4j
public class HHSocialAspirationController implements HHSocialAspirationControllerApi {

    private final HHSocialAspirationFacadeExt hHSocialAspirationFacadeExt;

    /**
     * Constructs a HHSocialAspirationController with the specified facade.
     *
     * @param hHSocialAspirationFacadeExt The HHSocialAspiration facade extension to be used
     */
    public HHSocialAspirationController(HHSocialAspirationFacadeExt hHSocialAspirationFacadeExt) {
        this.hHSocialAspirationFacadeExt =hHSocialAspirationFacadeExt;
    }

    /**
     * Creates a new HHSocialAspiration.
     *
     * @param hHSocialAspirationDTO The HHSocialAspiration data to create
     * @return ResponseEntity containing the ID of the created HHSocialAspiration
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HHSocialAspirationDTO hHSocialAspirationDTO) {
        log.debug("Entry - create(HHSocialAspirationDTO={})" , hHSocialAspirationDTO);
        Long id = hHSocialAspirationFacadeExt.save(hHSocialAspirationDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHHSocialAspirationUri(id)).body(id);
    }

    /**
     * Updates an existing HHSocialAspiration.
     *
     * @param hHSocialAspirationDTO The HHSocialAspiration data to update
     * @return ResponseEntity containing the updated HHSocialAspiration data
     */
    @Override
    public ResponseEntity<HHSocialAspirationDTO> update(@RequestBody HHSocialAspirationDTO hHSocialAspirationDTO) {
        log.debug("Entry - update(HHSocialAspirationDTO={})", hHSocialAspirationDTO);
        HHSocialAspirationDTO updated = hHSocialAspirationFacadeExt.update(hHSocialAspirationDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HHSocialAspirations.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HHSocialAspirations
     */
    @Override
    public ResponseEntity<PageDTO<HHSocialAspirationDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HHSocialAspirationDTO> result = hHSocialAspirationFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HHSocialAspiration by their ID.
     *
     * @param id The ID of the HHSocialAspiration to retrieve
     * @return ResponseEntity containing the HHSocialAspiration data
     */
    @Override
    public ResponseEntity<HHSocialAspirationDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HHSocialAspirationDTO dto = hHSocialAspirationFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HHSocialAspiration by their ID with an optional reason.
     *
     * @param id     The ID of the HHSocialAspiration to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        hHSocialAspirationFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HHSocialAspirationDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HHSocialAspirationDTO> result = hHSocialAspirationFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHHSocialAspirationUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
