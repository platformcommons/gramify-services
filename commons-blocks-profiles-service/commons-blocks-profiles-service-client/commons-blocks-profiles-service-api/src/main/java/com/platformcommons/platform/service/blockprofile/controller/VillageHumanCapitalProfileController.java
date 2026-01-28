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


import com.platformcommons.platform.service.blockprofile.api.VillageHumanCapitalProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageHumanCapitalProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageHumanCapitalProfileFacadeExt;


@RestController
@Slf4j
public class VillageHumanCapitalProfileController implements VillageHumanCapitalProfileControllerApi {

    private final VillageHumanCapitalProfileFacadeExt villageHumanCapitalProfileFacadeExt;

    /**
     * Constructs a VillageHumanCapitalProfileController with the specified facade.
     *
     * @param villageHumanCapitalProfileFacadeExt The VillageHumanCapitalProfile facade extension to be used
     */
    public VillageHumanCapitalProfileController(VillageHumanCapitalProfileFacadeExt villageHumanCapitalProfileFacadeExt) {
        this.villageHumanCapitalProfileFacadeExt =villageHumanCapitalProfileFacadeExt;
    }

    /**
     * Creates a new VillageHumanCapitalProfile.
     *
     * @param villageHumanCapitalProfileDTO The VillageHumanCapitalProfile data to create
     * @return ResponseEntity containing the ID of the created VillageHumanCapitalProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageHumanCapitalProfileDTO villageHumanCapitalProfileDTO) {
        log.debug("Entry - create(VillageHumanCapitalProfileDTO={})" , villageHumanCapitalProfileDTO);
        Long id = villageHumanCapitalProfileFacadeExt.save(villageHumanCapitalProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageHumanCapitalProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageHumanCapitalProfile.
     *
     * @param villageHumanCapitalProfileDTO The VillageHumanCapitalProfile data to update
     * @return ResponseEntity containing the updated VillageHumanCapitalProfile data
     */
    @Override
    public ResponseEntity<VillageHumanCapitalProfileDTO> update(@RequestBody VillageHumanCapitalProfileDTO villageHumanCapitalProfileDTO) {
        log.debug("Entry - update(VillageHumanCapitalProfileDTO={})", villageHumanCapitalProfileDTO);
        VillageHumanCapitalProfileDTO updated = villageHumanCapitalProfileFacadeExt.update(villageHumanCapitalProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageHumanCapitalProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageHumanCapitalProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageHumanCapitalProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageHumanCapitalProfileDTO> result = villageHumanCapitalProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageHumanCapitalProfile by their ID.
     *
     * @param id The ID of the VillageHumanCapitalProfile to retrieve
     * @return ResponseEntity containing the VillageHumanCapitalProfile data
     */
    @Override
    public ResponseEntity<VillageHumanCapitalProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageHumanCapitalProfileDTO dto = villageHumanCapitalProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageHumanCapitalProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageHumanCapitalProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageHumanCapitalProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageHumanCapitalProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageHumanCapitalProfileDTO> result = villageHumanCapitalProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageHumanCapitalProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
