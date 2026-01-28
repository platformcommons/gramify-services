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


import com.platformcommons.platform.service.blockprofile.api.VillageNicheProductProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageNicheProductProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageNicheProductProfileFacadeExt;


@RestController
@Slf4j
public class VillageNicheProductProfileController implements VillageNicheProductProfileControllerApi {

    private final VillageNicheProductProfileFacadeExt villageNicheProductProfileFacadeExt;

    /**
     * Constructs a VillageNicheProductProfileController with the specified facade.
     *
     * @param villageNicheProductProfileFacadeExt The VillageNicheProductProfile facade extension to be used
     */
    public VillageNicheProductProfileController(VillageNicheProductProfileFacadeExt villageNicheProductProfileFacadeExt) {
        this.villageNicheProductProfileFacadeExt =villageNicheProductProfileFacadeExt;
    }

    /**
     * Creates a new VillageNicheProductProfile.
     *
     * @param villageNicheProductProfileDTO The VillageNicheProductProfile data to create
     * @return ResponseEntity containing the ID of the created VillageNicheProductProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageNicheProductProfileDTO villageNicheProductProfileDTO) {
        log.debug("Entry - create(VillageNicheProductProfileDTO={})" , villageNicheProductProfileDTO);
        Long id = villageNicheProductProfileFacadeExt.save(villageNicheProductProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageNicheProductProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageNicheProductProfile.
     *
     * @param villageNicheProductProfileDTO The VillageNicheProductProfile data to update
     * @return ResponseEntity containing the updated VillageNicheProductProfile data
     */
    @Override
    public ResponseEntity<VillageNicheProductProfileDTO> update(@RequestBody VillageNicheProductProfileDTO villageNicheProductProfileDTO) {
        log.debug("Entry - update(VillageNicheProductProfileDTO={})", villageNicheProductProfileDTO);
        VillageNicheProductProfileDTO updated = villageNicheProductProfileFacadeExt.update(villageNicheProductProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageNicheProductProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageNicheProductProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageNicheProductProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageNicheProductProfileDTO> result = villageNicheProductProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageNicheProductProfile by their ID.
     *
     * @param id The ID of the VillageNicheProductProfile to retrieve
     * @return ResponseEntity containing the VillageNicheProductProfile data
     */
    @Override
    public ResponseEntity<VillageNicheProductProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageNicheProductProfileDTO dto = villageNicheProductProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageNicheProductProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageNicheProductProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageNicheProductProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageNicheProductProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageNicheProductProfileDTO> result = villageNicheProductProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageNicheProductProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
