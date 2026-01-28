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


import com.platformcommons.platform.service.blockprofile.api.VillageRoadConnectivityProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageRoadConnectivityProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageRoadConnectivityProfileFacadeExt;


@RestController
@Slf4j
public class VillageRoadConnectivityProfileController implements VillageRoadConnectivityProfileControllerApi {

    private final VillageRoadConnectivityProfileFacadeExt villageRoadConnectivityProfileFacadeExt;

    /**
     * Constructs a VillageRoadConnectivityProfileController with the specified facade.
     *
     * @param villageRoadConnectivityProfileFacadeExt The VillageRoadConnectivityProfile facade extension to be used
     */
    public VillageRoadConnectivityProfileController(VillageRoadConnectivityProfileFacadeExt villageRoadConnectivityProfileFacadeExt) {
        this.villageRoadConnectivityProfileFacadeExt =villageRoadConnectivityProfileFacadeExt;
    }

    /**
     * Creates a new VillageRoadConnectivityProfile.
     *
     * @param villageRoadConnectivityProfileDTO The VillageRoadConnectivityProfile data to create
     * @return ResponseEntity containing the ID of the created VillageRoadConnectivityProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageRoadConnectivityProfileDTO villageRoadConnectivityProfileDTO) {
        log.debug("Entry - create(VillageRoadConnectivityProfileDTO={})" , villageRoadConnectivityProfileDTO);
        Long id = villageRoadConnectivityProfileFacadeExt.save(villageRoadConnectivityProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageRoadConnectivityProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageRoadConnectivityProfile.
     *
     * @param villageRoadConnectivityProfileDTO The VillageRoadConnectivityProfile data to update
     * @return ResponseEntity containing the updated VillageRoadConnectivityProfile data
     */
    @Override
    public ResponseEntity<VillageRoadConnectivityProfileDTO> update(@RequestBody VillageRoadConnectivityProfileDTO villageRoadConnectivityProfileDTO) {
        log.debug("Entry - update(VillageRoadConnectivityProfileDTO={})", villageRoadConnectivityProfileDTO);
        VillageRoadConnectivityProfileDTO updated = villageRoadConnectivityProfileFacadeExt.update(villageRoadConnectivityProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageRoadConnectivityProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageRoadConnectivityProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageRoadConnectivityProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageRoadConnectivityProfileDTO> result = villageRoadConnectivityProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageRoadConnectivityProfile by their ID.
     *
     * @param id The ID of the VillageRoadConnectivityProfile to retrieve
     * @return ResponseEntity containing the VillageRoadConnectivityProfile data
     */
    @Override
    public ResponseEntity<VillageRoadConnectivityProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageRoadConnectivityProfileDTO dto = villageRoadConnectivityProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageRoadConnectivityProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageRoadConnectivityProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageRoadConnectivityProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageRoadConnectivityProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageRoadConnectivityProfileDTO> result = villageRoadConnectivityProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageRoadConnectivityProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
