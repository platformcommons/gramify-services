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


import com.platformcommons.platform.service.blockprofile.api.VillageSurplusProduceProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageSurplusProduceProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageSurplusProduceProfileFacadeExt;


@RestController
@Slf4j
public class VillageSurplusProduceProfileController implements VillageSurplusProduceProfileControllerApi {

    private final VillageSurplusProduceProfileFacadeExt villageSurplusProduceProfileFacadeExt;

    /**
     * Constructs a VillageSurplusProduceProfileController with the specified facade.
     *
     * @param villageSurplusProduceProfileFacadeExt The VillageSurplusProduceProfile facade extension to be used
     */
    public VillageSurplusProduceProfileController(VillageSurplusProduceProfileFacadeExt villageSurplusProduceProfileFacadeExt) {
        this.villageSurplusProduceProfileFacadeExt =villageSurplusProduceProfileFacadeExt;
    }

    /**
     * Creates a new VillageSurplusProduceProfile.
     *
     * @param villageSurplusProduceProfileDTO The VillageSurplusProduceProfile data to create
     * @return ResponseEntity containing the ID of the created VillageSurplusProduceProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageSurplusProduceProfileDTO villageSurplusProduceProfileDTO) {
        log.debug("Entry - create(VillageSurplusProduceProfileDTO={})" , villageSurplusProduceProfileDTO);
        Long id = villageSurplusProduceProfileFacadeExt.save(villageSurplusProduceProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageSurplusProduceProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageSurplusProduceProfile.
     *
     * @param villageSurplusProduceProfileDTO The VillageSurplusProduceProfile data to update
     * @return ResponseEntity containing the updated VillageSurplusProduceProfile data
     */
    @Override
    public ResponseEntity<VillageSurplusProduceProfileDTO> update(@RequestBody VillageSurplusProduceProfileDTO villageSurplusProduceProfileDTO) {
        log.debug("Entry - update(VillageSurplusProduceProfileDTO={})", villageSurplusProduceProfileDTO);
        VillageSurplusProduceProfileDTO updated = villageSurplusProduceProfileFacadeExt.update(villageSurplusProduceProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageSurplusProduceProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageSurplusProduceProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageSurplusProduceProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageSurplusProduceProfileDTO> result = villageSurplusProduceProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageSurplusProduceProfile by their ID.
     *
     * @param id The ID of the VillageSurplusProduceProfile to retrieve
     * @return ResponseEntity containing the VillageSurplusProduceProfile data
     */
    @Override
    public ResponseEntity<VillageSurplusProduceProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageSurplusProduceProfileDTO dto = villageSurplusProduceProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageSurplusProduceProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageSurplusProduceProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageSurplusProduceProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageSurplusProduceProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageSurplusProduceProfileDTO> result = villageSurplusProduceProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageSurplusProduceProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
