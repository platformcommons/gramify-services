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


import com.platformcommons.platform.service.blockprofile.api.VillageHumanResourceProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageHumanResourceProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageHumanResourceProfileFacadeExt;


@RestController
@Slf4j
public class VillageHumanResourceProfileController implements VillageHumanResourceProfileControllerApi {

    private final VillageHumanResourceProfileFacadeExt villageHumanResourceProfileFacadeExt;

    /**
     * Constructs a VillageHumanResourceProfileController with the specified facade.
     *
     * @param villageHumanResourceProfileFacadeExt The VillageHumanResourceProfile facade extension to be used
     */
    public VillageHumanResourceProfileController(VillageHumanResourceProfileFacadeExt villageHumanResourceProfileFacadeExt) {
        this.villageHumanResourceProfileFacadeExt =villageHumanResourceProfileFacadeExt;
    }

    /**
     * Creates a new VillageHumanResourceProfile.
     *
     * @param villageHumanResourceProfileDTO The VillageHumanResourceProfile data to create
     * @return ResponseEntity containing the ID of the created VillageHumanResourceProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageHumanResourceProfileDTO villageHumanResourceProfileDTO) {
        log.debug("Entry - create(VillageHumanResourceProfileDTO={})" , villageHumanResourceProfileDTO);
        Long id = villageHumanResourceProfileFacadeExt.save(villageHumanResourceProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageHumanResourceProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageHumanResourceProfile.
     *
     * @param villageHumanResourceProfileDTO The VillageHumanResourceProfile data to update
     * @return ResponseEntity containing the updated VillageHumanResourceProfile data
     */
    @Override
    public ResponseEntity<VillageHumanResourceProfileDTO> update(@RequestBody VillageHumanResourceProfileDTO villageHumanResourceProfileDTO) {
        log.debug("Entry - update(VillageHumanResourceProfileDTO={})", villageHumanResourceProfileDTO);
        VillageHumanResourceProfileDTO updated = villageHumanResourceProfileFacadeExt.update(villageHumanResourceProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageHumanResourceProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageHumanResourceProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageHumanResourceProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageHumanResourceProfileDTO> result = villageHumanResourceProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageHumanResourceProfile by their ID.
     *
     * @param id The ID of the VillageHumanResourceProfile to retrieve
     * @return ResponseEntity containing the VillageHumanResourceProfile data
     */
    @Override
    public ResponseEntity<VillageHumanResourceProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageHumanResourceProfileDTO dto = villageHumanResourceProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageHumanResourceProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageHumanResourceProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageHumanResourceProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageHumanResourceProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageHumanResourceProfileDTO> result = villageHumanResourceProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageHumanResourceProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
