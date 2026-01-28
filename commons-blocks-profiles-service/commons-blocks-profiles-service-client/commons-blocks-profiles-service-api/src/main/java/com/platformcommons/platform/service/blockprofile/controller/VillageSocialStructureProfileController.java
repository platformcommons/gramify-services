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


import com.platformcommons.platform.service.blockprofile.api.VillageSocialStructureProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageSocialStructureProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageSocialStructureProfileFacadeExt;


@RestController
@Slf4j
public class VillageSocialStructureProfileController implements VillageSocialStructureProfileControllerApi {

    private final VillageSocialStructureProfileFacadeExt villageSocialStructureProfileFacadeExt;

    /**
     * Constructs a VillageSocialStructureProfileController with the specified facade.
     *
     * @param villageSocialStructureProfileFacadeExt The VillageSocialStructureProfile facade extension to be used
     */
    public VillageSocialStructureProfileController(VillageSocialStructureProfileFacadeExt villageSocialStructureProfileFacadeExt) {
        this.villageSocialStructureProfileFacadeExt =villageSocialStructureProfileFacadeExt;
    }

    /**
     * Creates a new VillageSocialStructureProfile.
     *
     * @param villageSocialStructureProfileDTO The VillageSocialStructureProfile data to create
     * @return ResponseEntity containing the ID of the created VillageSocialStructureProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageSocialStructureProfileDTO villageSocialStructureProfileDTO) {
        log.debug("Entry - create(VillageSocialStructureProfileDTO={})" , villageSocialStructureProfileDTO);
        Long id = villageSocialStructureProfileFacadeExt.save(villageSocialStructureProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageSocialStructureProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageSocialStructureProfile.
     *
     * @param villageSocialStructureProfileDTO The VillageSocialStructureProfile data to update
     * @return ResponseEntity containing the updated VillageSocialStructureProfile data
     */
    @Override
    public ResponseEntity<VillageSocialStructureProfileDTO> update(@RequestBody VillageSocialStructureProfileDTO villageSocialStructureProfileDTO) {
        log.debug("Entry - update(VillageSocialStructureProfileDTO={})", villageSocialStructureProfileDTO);
        VillageSocialStructureProfileDTO updated = villageSocialStructureProfileFacadeExt.update(villageSocialStructureProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageSocialStructureProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageSocialStructureProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageSocialStructureProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageSocialStructureProfileDTO> result = villageSocialStructureProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageSocialStructureProfile by their ID.
     *
     * @param id The ID of the VillageSocialStructureProfile to retrieve
     * @return ResponseEntity containing the VillageSocialStructureProfile data
     */
    @Override
    public ResponseEntity<VillageSocialStructureProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageSocialStructureProfileDTO dto = villageSocialStructureProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageSocialStructureProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageSocialStructureProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageSocialStructureProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageSocialStructureProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageSocialStructureProfileDTO> result = villageSocialStructureProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageSocialStructureProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
