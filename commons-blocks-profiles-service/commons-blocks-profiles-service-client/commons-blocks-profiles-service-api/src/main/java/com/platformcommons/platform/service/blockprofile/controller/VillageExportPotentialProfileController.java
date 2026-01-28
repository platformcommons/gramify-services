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


import com.platformcommons.platform.service.blockprofile.api.VillageExportPotentialProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageExportPotentialProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageExportPotentialProfileFacadeExt;


@RestController
@Slf4j
public class VillageExportPotentialProfileController implements VillageExportPotentialProfileControllerApi {

    private final VillageExportPotentialProfileFacadeExt villageExportPotentialProfileFacadeExt;

    /**
     * Constructs a VillageExportPotentialProfileController with the specified facade.
     *
     * @param villageExportPotentialProfileFacadeExt The VillageExportPotentialProfile facade extension to be used
     */
    public VillageExportPotentialProfileController(VillageExportPotentialProfileFacadeExt villageExportPotentialProfileFacadeExt) {
        this.villageExportPotentialProfileFacadeExt =villageExportPotentialProfileFacadeExt;
    }

    /**
     * Creates a new VillageExportPotentialProfile.
     *
     * @param villageExportPotentialProfileDTO The VillageExportPotentialProfile data to create
     * @return ResponseEntity containing the ID of the created VillageExportPotentialProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageExportPotentialProfileDTO villageExportPotentialProfileDTO) {
        log.debug("Entry - create(VillageExportPotentialProfileDTO={})" , villageExportPotentialProfileDTO);
        Long id = villageExportPotentialProfileFacadeExt.save(villageExportPotentialProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageExportPotentialProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageExportPotentialProfile.
     *
     * @param villageExportPotentialProfileDTO The VillageExportPotentialProfile data to update
     * @return ResponseEntity containing the updated VillageExportPotentialProfile data
     */
    @Override
    public ResponseEntity<VillageExportPotentialProfileDTO> update(@RequestBody VillageExportPotentialProfileDTO villageExportPotentialProfileDTO) {
        log.debug("Entry - update(VillageExportPotentialProfileDTO={})", villageExportPotentialProfileDTO);
        VillageExportPotentialProfileDTO updated = villageExportPotentialProfileFacadeExt.update(villageExportPotentialProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageExportPotentialProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageExportPotentialProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageExportPotentialProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageExportPotentialProfileDTO> result = villageExportPotentialProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageExportPotentialProfile by their ID.
     *
     * @param id The ID of the VillageExportPotentialProfile to retrieve
     * @return ResponseEntity containing the VillageExportPotentialProfile data
     */
    @Override
    public ResponseEntity<VillageExportPotentialProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageExportPotentialProfileDTO dto = villageExportPotentialProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageExportPotentialProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageExportPotentialProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageExportPotentialProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageExportPotentialProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageExportPotentialProfileDTO> result = villageExportPotentialProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageExportPotentialProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
