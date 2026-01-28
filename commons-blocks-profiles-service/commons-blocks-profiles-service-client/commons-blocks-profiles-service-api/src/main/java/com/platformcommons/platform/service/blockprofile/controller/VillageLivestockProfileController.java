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


import com.platformcommons.platform.service.blockprofile.api.VillageLivestockProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageLivestockProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageLivestockProfileFacadeExt;


@RestController
@Slf4j
public class VillageLivestockProfileController implements VillageLivestockProfileControllerApi {

    private final VillageLivestockProfileFacadeExt villageLivestockProfileFacadeExt;

    /**
     * Constructs a VillageLivestockProfileController with the specified facade.
     *
     * @param villageLivestockProfileFacadeExt The VillageLivestockProfile facade extension to be used
     */
    public VillageLivestockProfileController(VillageLivestockProfileFacadeExt villageLivestockProfileFacadeExt) {
        this.villageLivestockProfileFacadeExt =villageLivestockProfileFacadeExt;
    }

    /**
     * Creates a new VillageLivestockProfile.
     *
     * @param villageLivestockProfileDTO The VillageLivestockProfile data to create
     * @return ResponseEntity containing the ID of the created VillageLivestockProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageLivestockProfileDTO villageLivestockProfileDTO) {
        log.debug("Entry - create(VillageLivestockProfileDTO={})" , villageLivestockProfileDTO);
        Long id = villageLivestockProfileFacadeExt.save(villageLivestockProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageLivestockProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageLivestockProfile.
     *
     * @param villageLivestockProfileDTO The VillageLivestockProfile data to update
     * @return ResponseEntity containing the updated VillageLivestockProfile data
     */
    @Override
    public ResponseEntity<VillageLivestockProfileDTO> update(@RequestBody VillageLivestockProfileDTO villageLivestockProfileDTO) {
        log.debug("Entry - update(VillageLivestockProfileDTO={})", villageLivestockProfileDTO);
        VillageLivestockProfileDTO updated = villageLivestockProfileFacadeExt.update(villageLivestockProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageLivestockProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageLivestockProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageLivestockProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageLivestockProfileDTO> result = villageLivestockProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageLivestockProfile by their ID.
     *
     * @param id The ID of the VillageLivestockProfile to retrieve
     * @return ResponseEntity containing the VillageLivestockProfile data
     */
    @Override
    public ResponseEntity<VillageLivestockProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageLivestockProfileDTO dto = villageLivestockProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageLivestockProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageLivestockProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageLivestockProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageLivestockProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageLivestockProfileDTO> result = villageLivestockProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageLivestockProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
