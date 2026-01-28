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


import com.platformcommons.platform.service.blockprofile.api.VillageCulturalResourceProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageCulturalResourceProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageCulturalResourceProfileFacadeExt;


@RestController
@Slf4j
public class VillageCulturalResourceProfileController implements VillageCulturalResourceProfileControllerApi {

    private final VillageCulturalResourceProfileFacadeExt villageCulturalResourceProfileFacadeExt;

    /**
     * Constructs a VillageCulturalResourceProfileController with the specified facade.
     *
     * @param villageCulturalResourceProfileFacadeExt The VillageCulturalResourceProfile facade extension to be used
     */
    public VillageCulturalResourceProfileController(VillageCulturalResourceProfileFacadeExt villageCulturalResourceProfileFacadeExt) {
        this.villageCulturalResourceProfileFacadeExt =villageCulturalResourceProfileFacadeExt;
    }

    /**
     * Creates a new VillageCulturalResourceProfile.
     *
     * @param villageCulturalResourceProfileDTO The VillageCulturalResourceProfile data to create
     * @return ResponseEntity containing the ID of the created VillageCulturalResourceProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageCulturalResourceProfileDTO villageCulturalResourceProfileDTO) {
        log.debug("Entry - create(VillageCulturalResourceProfileDTO={})" , villageCulturalResourceProfileDTO);
        Long id = villageCulturalResourceProfileFacadeExt.save(villageCulturalResourceProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageCulturalResourceProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageCulturalResourceProfile.
     *
     * @param villageCulturalResourceProfileDTO The VillageCulturalResourceProfile data to update
     * @return ResponseEntity containing the updated VillageCulturalResourceProfile data
     */
    @Override
    public ResponseEntity<VillageCulturalResourceProfileDTO> update(@RequestBody VillageCulturalResourceProfileDTO villageCulturalResourceProfileDTO) {
        log.debug("Entry - update(VillageCulturalResourceProfileDTO={})", villageCulturalResourceProfileDTO);
        VillageCulturalResourceProfileDTO updated = villageCulturalResourceProfileFacadeExt.update(villageCulturalResourceProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageCulturalResourceProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageCulturalResourceProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageCulturalResourceProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageCulturalResourceProfileDTO> result = villageCulturalResourceProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageCulturalResourceProfile by their ID.
     *
     * @param id The ID of the VillageCulturalResourceProfile to retrieve
     * @return ResponseEntity containing the VillageCulturalResourceProfile data
     */
    @Override
    public ResponseEntity<VillageCulturalResourceProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCulturalResourceProfileDTO dto = villageCulturalResourceProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageCulturalResourceProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageCulturalResourceProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageCulturalResourceProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageCulturalResourceProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageCulturalResourceProfileDTO> result = villageCulturalResourceProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageCulturalResourceProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
