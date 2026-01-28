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


import com.platformcommons.platform.service.blockprofile.api.VillageWaterResourceProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageWaterResourceProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageWaterResourceProfileFacadeExt;


@RestController
@Slf4j
public class VillageWaterResourceProfileController implements VillageWaterResourceProfileControllerApi {

    private final VillageWaterResourceProfileFacadeExt villageWaterResourceProfileFacadeExt;

    /**
     * Constructs a VillageWaterResourceProfileController with the specified facade.
     *
     * @param villageWaterResourceProfileFacadeExt The VillageWaterResourceProfile facade extension to be used
     */
    public VillageWaterResourceProfileController(VillageWaterResourceProfileFacadeExt villageWaterResourceProfileFacadeExt) {
        this.villageWaterResourceProfileFacadeExt =villageWaterResourceProfileFacadeExt;
    }

    /**
     * Creates a new VillageWaterResourceProfile.
     *
     * @param villageWaterResourceProfileDTO The VillageWaterResourceProfile data to create
     * @return ResponseEntity containing the ID of the created VillageWaterResourceProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageWaterResourceProfileDTO villageWaterResourceProfileDTO) {
        log.debug("Entry - create(VillageWaterResourceProfileDTO={})" , villageWaterResourceProfileDTO);
        Long id = villageWaterResourceProfileFacadeExt.save(villageWaterResourceProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageWaterResourceProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageWaterResourceProfile.
     *
     * @param villageWaterResourceProfileDTO The VillageWaterResourceProfile data to update
     * @return ResponseEntity containing the updated VillageWaterResourceProfile data
     */
    @Override
    public ResponseEntity<VillageWaterResourceProfileDTO> update(@RequestBody VillageWaterResourceProfileDTO villageWaterResourceProfileDTO) {
        log.debug("Entry - update(VillageWaterResourceProfileDTO={})", villageWaterResourceProfileDTO);
        VillageWaterResourceProfileDTO updated = villageWaterResourceProfileFacadeExt.update(villageWaterResourceProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageWaterResourceProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageWaterResourceProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageWaterResourceProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageWaterResourceProfileDTO> result = villageWaterResourceProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageWaterResourceProfile by their ID.
     *
     * @param id The ID of the VillageWaterResourceProfile to retrieve
     * @return ResponseEntity containing the VillageWaterResourceProfile data
     */
    @Override
    public ResponseEntity<VillageWaterResourceProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageWaterResourceProfileDTO dto = villageWaterResourceProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageWaterResourceProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageWaterResourceProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageWaterResourceProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageWaterResourceProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageWaterResourceProfileDTO> result = villageWaterResourceProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageWaterResourceProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
