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


import com.platformcommons.platform.service.blockprofile.api.VillageWaterSanitationProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageWaterSanitationProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageWaterSanitationProfileFacadeExt;


@RestController
@Slf4j
public class VillageWaterSanitationProfileController implements VillageWaterSanitationProfileControllerApi {

    private final VillageWaterSanitationProfileFacadeExt villageWaterSanitationProfileFacadeExt;

    /**
     * Constructs a VillageWaterSanitationProfileController with the specified facade.
     *
     * @param villageWaterSanitationProfileFacadeExt The VillageWaterSanitationProfile facade extension to be used
     */
    public VillageWaterSanitationProfileController(VillageWaterSanitationProfileFacadeExt villageWaterSanitationProfileFacadeExt) {
        this.villageWaterSanitationProfileFacadeExt =villageWaterSanitationProfileFacadeExt;
    }

    /**
     * Creates a new VillageWaterSanitationProfile.
     *
     * @param villageWaterSanitationProfileDTO The VillageWaterSanitationProfile data to create
     * @return ResponseEntity containing the ID of the created VillageWaterSanitationProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageWaterSanitationProfileDTO villageWaterSanitationProfileDTO) {
        log.debug("Entry - create(VillageWaterSanitationProfileDTO={})" , villageWaterSanitationProfileDTO);
        Long id = villageWaterSanitationProfileFacadeExt.save(villageWaterSanitationProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageWaterSanitationProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageWaterSanitationProfile.
     *
     * @param villageWaterSanitationProfileDTO The VillageWaterSanitationProfile data to update
     * @return ResponseEntity containing the updated VillageWaterSanitationProfile data
     */
    @Override
    public ResponseEntity<VillageWaterSanitationProfileDTO> update(@RequestBody VillageWaterSanitationProfileDTO villageWaterSanitationProfileDTO) {
        log.debug("Entry - update(VillageWaterSanitationProfileDTO={})", villageWaterSanitationProfileDTO);
        VillageWaterSanitationProfileDTO updated = villageWaterSanitationProfileFacadeExt.update(villageWaterSanitationProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageWaterSanitationProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageWaterSanitationProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageWaterSanitationProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageWaterSanitationProfileDTO> result = villageWaterSanitationProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageWaterSanitationProfile by their ID.
     *
     * @param id The ID of the VillageWaterSanitationProfile to retrieve
     * @return ResponseEntity containing the VillageWaterSanitationProfile data
     */
    @Override
    public ResponseEntity<VillageWaterSanitationProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageWaterSanitationProfileDTO dto = villageWaterSanitationProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageWaterSanitationProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageWaterSanitationProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageWaterSanitationProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageWaterSanitationProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageWaterSanitationProfileDTO> result = villageWaterSanitationProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageWaterSanitationProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
