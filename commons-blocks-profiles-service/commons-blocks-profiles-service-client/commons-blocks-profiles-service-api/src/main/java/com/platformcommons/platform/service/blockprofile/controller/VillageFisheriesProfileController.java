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


import com.platformcommons.platform.service.blockprofile.api.VillageFisheriesProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageFisheriesProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageFisheriesProfileFacadeExt;


@RestController
@Slf4j
public class VillageFisheriesProfileController implements VillageFisheriesProfileControllerApi {

    private final VillageFisheriesProfileFacadeExt villageFisheriesProfileFacadeExt;

    /**
     * Constructs a VillageFisheriesProfileController with the specified facade.
     *
     * @param villageFisheriesProfileFacadeExt The VillageFisheriesProfile facade extension to be used
     */
    public VillageFisheriesProfileController(VillageFisheriesProfileFacadeExt villageFisheriesProfileFacadeExt) {
        this.villageFisheriesProfileFacadeExt =villageFisheriesProfileFacadeExt;
    }

    /**
     * Creates a new VillageFisheriesProfile.
     *
     * @param villageFisheriesProfileDTO The VillageFisheriesProfile data to create
     * @return ResponseEntity containing the ID of the created VillageFisheriesProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageFisheriesProfileDTO villageFisheriesProfileDTO) {
        log.debug("Entry - create(VillageFisheriesProfileDTO={})" , villageFisheriesProfileDTO);
        Long id = villageFisheriesProfileFacadeExt.save(villageFisheriesProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageFisheriesProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageFisheriesProfile.
     *
     * @param villageFisheriesProfileDTO The VillageFisheriesProfile data to update
     * @return ResponseEntity containing the updated VillageFisheriesProfile data
     */
    @Override
    public ResponseEntity<VillageFisheriesProfileDTO> update(@RequestBody VillageFisheriesProfileDTO villageFisheriesProfileDTO) {
        log.debug("Entry - update(VillageFisheriesProfileDTO={})", villageFisheriesProfileDTO);
        VillageFisheriesProfileDTO updated = villageFisheriesProfileFacadeExt.update(villageFisheriesProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageFisheriesProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageFisheriesProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageFisheriesProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageFisheriesProfileDTO> result = villageFisheriesProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageFisheriesProfile by their ID.
     *
     * @param id The ID of the VillageFisheriesProfile to retrieve
     * @return ResponseEntity containing the VillageFisheriesProfile data
     */
    @Override
    public ResponseEntity<VillageFisheriesProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageFisheriesProfileDTO dto = villageFisheriesProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageFisheriesProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageFisheriesProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageFisheriesProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageFisheriesProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageFisheriesProfileDTO> result = villageFisheriesProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageFisheriesProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
