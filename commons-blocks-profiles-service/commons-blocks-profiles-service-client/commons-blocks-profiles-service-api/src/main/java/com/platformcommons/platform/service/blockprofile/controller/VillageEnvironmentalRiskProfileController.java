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


import com.platformcommons.platform.service.blockprofile.api.VillageEnvironmentalRiskProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageEnvironmentalRiskProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageEnvironmentalRiskProfileFacadeExt;


@RestController
@Slf4j
public class VillageEnvironmentalRiskProfileController implements VillageEnvironmentalRiskProfileControllerApi {

    private final VillageEnvironmentalRiskProfileFacadeExt villageEnvironmentalRiskProfileFacadeExt;

    /**
     * Constructs a VillageEnvironmentalRiskProfileController with the specified facade.
     *
     * @param villageEnvironmentalRiskProfileFacadeExt The VillageEnvironmentalRiskProfile facade extension to be used
     */
    public VillageEnvironmentalRiskProfileController(VillageEnvironmentalRiskProfileFacadeExt villageEnvironmentalRiskProfileFacadeExt) {
        this.villageEnvironmentalRiskProfileFacadeExt =villageEnvironmentalRiskProfileFacadeExt;
    }

    /**
     * Creates a new VillageEnvironmentalRiskProfile.
     *
     * @param villageEnvironmentalRiskProfileDTO The VillageEnvironmentalRiskProfile data to create
     * @return ResponseEntity containing the ID of the created VillageEnvironmentalRiskProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageEnvironmentalRiskProfileDTO villageEnvironmentalRiskProfileDTO) {
        log.debug("Entry - create(VillageEnvironmentalRiskProfileDTO={})" , villageEnvironmentalRiskProfileDTO);
        Long id = villageEnvironmentalRiskProfileFacadeExt.save(villageEnvironmentalRiskProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageEnvironmentalRiskProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageEnvironmentalRiskProfile.
     *
     * @param villageEnvironmentalRiskProfileDTO The VillageEnvironmentalRiskProfile data to update
     * @return ResponseEntity containing the updated VillageEnvironmentalRiskProfile data
     */
    @Override
    public ResponseEntity<VillageEnvironmentalRiskProfileDTO> update(@RequestBody VillageEnvironmentalRiskProfileDTO villageEnvironmentalRiskProfileDTO) {
        log.debug("Entry - update(VillageEnvironmentalRiskProfileDTO={})", villageEnvironmentalRiskProfileDTO);
        VillageEnvironmentalRiskProfileDTO updated = villageEnvironmentalRiskProfileFacadeExt.update(villageEnvironmentalRiskProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageEnvironmentalRiskProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageEnvironmentalRiskProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageEnvironmentalRiskProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageEnvironmentalRiskProfileDTO> result = villageEnvironmentalRiskProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageEnvironmentalRiskProfile by their ID.
     *
     * @param id The ID of the VillageEnvironmentalRiskProfile to retrieve
     * @return ResponseEntity containing the VillageEnvironmentalRiskProfile data
     */
    @Override
    public ResponseEntity<VillageEnvironmentalRiskProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageEnvironmentalRiskProfileDTO dto = villageEnvironmentalRiskProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageEnvironmentalRiskProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageEnvironmentalRiskProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageEnvironmentalRiskProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageEnvironmentalRiskProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageEnvironmentalRiskProfileDTO> result = villageEnvironmentalRiskProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageEnvironmentalRiskProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
