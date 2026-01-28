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


import com.platformcommons.platform.service.blockprofile.api.VillageCropProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageCropProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageCropProfileFacadeExt;


@RestController
@Slf4j
public class VillageCropProfileController implements VillageCropProfileControllerApi {

    private final VillageCropProfileFacadeExt villageCropProfileFacadeExt;

    /**
     * Constructs a VillageCropProfileController with the specified facade.
     *
     * @param villageCropProfileFacadeExt The VillageCropProfile facade extension to be used
     */
    public VillageCropProfileController(VillageCropProfileFacadeExt villageCropProfileFacadeExt) {
        this.villageCropProfileFacadeExt =villageCropProfileFacadeExt;
    }

    /**
     * Creates a new VillageCropProfile.
     *
     * @param villageCropProfileDTO The VillageCropProfile data to create
     * @return ResponseEntity containing the ID of the created VillageCropProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageCropProfileDTO villageCropProfileDTO) {
        log.debug("Entry - create(VillageCropProfileDTO={})" , villageCropProfileDTO);
        Long id = villageCropProfileFacadeExt.save(villageCropProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageCropProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageCropProfile.
     *
     * @param villageCropProfileDTO The VillageCropProfile data to update
     * @return ResponseEntity containing the updated VillageCropProfile data
     */
    @Override
    public ResponseEntity<VillageCropProfileDTO> update(@RequestBody VillageCropProfileDTO villageCropProfileDTO) {
        log.debug("Entry - update(VillageCropProfileDTO={})", villageCropProfileDTO);
        VillageCropProfileDTO updated = villageCropProfileFacadeExt.update(villageCropProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageCropProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageCropProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageCropProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageCropProfileDTO> result = villageCropProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageCropProfile by their ID.
     *
     * @param id The ID of the VillageCropProfile to retrieve
     * @return ResponseEntity containing the VillageCropProfile data
     */
    @Override
    public ResponseEntity<VillageCropProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCropProfileDTO dto = villageCropProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageCropProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageCropProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageCropProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageCropProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageCropProfileDTO> result = villageCropProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageCropProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
