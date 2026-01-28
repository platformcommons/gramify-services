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


import com.platformcommons.platform.service.blockprofile.api.VillageServiceDemandProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageServiceDemandProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageServiceDemandProfileFacadeExt;


@RestController
@Slf4j
public class VillageServiceDemandProfileController implements VillageServiceDemandProfileControllerApi {

    private final VillageServiceDemandProfileFacadeExt villageServiceDemandProfileFacadeExt;

    /**
     * Constructs a VillageServiceDemandProfileController with the specified facade.
     *
     * @param villageServiceDemandProfileFacadeExt The VillageServiceDemandProfile facade extension to be used
     */
    public VillageServiceDemandProfileController(VillageServiceDemandProfileFacadeExt villageServiceDemandProfileFacadeExt) {
        this.villageServiceDemandProfileFacadeExt =villageServiceDemandProfileFacadeExt;
    }

    /**
     * Creates a new VillageServiceDemandProfile.
     *
     * @param villageServiceDemandProfileDTO The VillageServiceDemandProfile data to create
     * @return ResponseEntity containing the ID of the created VillageServiceDemandProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageServiceDemandProfileDTO villageServiceDemandProfileDTO) {
        log.debug("Entry - create(VillageServiceDemandProfileDTO={})" , villageServiceDemandProfileDTO);
        Long id = villageServiceDemandProfileFacadeExt.save(villageServiceDemandProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageServiceDemandProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageServiceDemandProfile.
     *
     * @param villageServiceDemandProfileDTO The VillageServiceDemandProfile data to update
     * @return ResponseEntity containing the updated VillageServiceDemandProfile data
     */
    @Override
    public ResponseEntity<VillageServiceDemandProfileDTO> update(@RequestBody VillageServiceDemandProfileDTO villageServiceDemandProfileDTO) {
        log.debug("Entry - update(VillageServiceDemandProfileDTO={})", villageServiceDemandProfileDTO);
        VillageServiceDemandProfileDTO updated = villageServiceDemandProfileFacadeExt.update(villageServiceDemandProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageServiceDemandProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageServiceDemandProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageServiceDemandProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageServiceDemandProfileDTO> result = villageServiceDemandProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageServiceDemandProfile by their ID.
     *
     * @param id The ID of the VillageServiceDemandProfile to retrieve
     * @return ResponseEntity containing the VillageServiceDemandProfile data
     */
    @Override
    public ResponseEntity<VillageServiceDemandProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageServiceDemandProfileDTO dto = villageServiceDemandProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageServiceDemandProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageServiceDemandProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageServiceDemandProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageServiceDemandProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageServiceDemandProfileDTO> result = villageServiceDemandProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageServiceDemandProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
