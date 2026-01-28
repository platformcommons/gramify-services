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


import com.platformcommons.platform.service.blockprofile.api.VillageAgriInputDemandProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageAgriInputDemandProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageAgriInputDemandProfileFacadeExt;


@RestController
@Slf4j
public class VillageAgriInputDemandProfileController implements VillageAgriInputDemandProfileControllerApi {

    private final VillageAgriInputDemandProfileFacadeExt villageAgriInputDemandProfileFacadeExt;

    /**
     * Constructs a VillageAgriInputDemandProfileController with the specified facade.
     *
     * @param villageAgriInputDemandProfileFacadeExt The VillageAgriInputDemandProfile facade extension to be used
     */
    public VillageAgriInputDemandProfileController(VillageAgriInputDemandProfileFacadeExt villageAgriInputDemandProfileFacadeExt) {
        this.villageAgriInputDemandProfileFacadeExt =villageAgriInputDemandProfileFacadeExt;
    }

    /**
     * Creates a new VillageAgriInputDemandProfile.
     *
     * @param villageAgriInputDemandProfileDTO The VillageAgriInputDemandProfile data to create
     * @return ResponseEntity containing the ID of the created VillageAgriInputDemandProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageAgriInputDemandProfileDTO villageAgriInputDemandProfileDTO) {
        log.debug("Entry - create(VillageAgriInputDemandProfileDTO={})" , villageAgriInputDemandProfileDTO);
        Long id = villageAgriInputDemandProfileFacadeExt.save(villageAgriInputDemandProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageAgriInputDemandProfileUri(id)).body(id);
    }

    /**
     * Updates an existing VillageAgriInputDemandProfile.
     *
     * @param villageAgriInputDemandProfileDTO The VillageAgriInputDemandProfile data to update
     * @return ResponseEntity containing the updated VillageAgriInputDemandProfile data
     */
    @Override
    public ResponseEntity<VillageAgriInputDemandProfileDTO> update(@RequestBody VillageAgriInputDemandProfileDTO villageAgriInputDemandProfileDTO) {
        log.debug("Entry - update(VillageAgriInputDemandProfileDTO={})", villageAgriInputDemandProfileDTO);
        VillageAgriInputDemandProfileDTO updated = villageAgriInputDemandProfileFacadeExt.update(villageAgriInputDemandProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageAgriInputDemandProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageAgriInputDemandProfiles
     */
    @Override
    public ResponseEntity<PageDTO<VillageAgriInputDemandProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageAgriInputDemandProfileDTO> result = villageAgriInputDemandProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageAgriInputDemandProfile by their ID.
     *
     * @param id The ID of the VillageAgriInputDemandProfile to retrieve
     * @return ResponseEntity containing the VillageAgriInputDemandProfile data
     */
    @Override
    public ResponseEntity<VillageAgriInputDemandProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageAgriInputDemandProfileDTO dto = villageAgriInputDemandProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageAgriInputDemandProfile by their ID with an optional reason.
     *
     * @param id     The ID of the VillageAgriInputDemandProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageAgriInputDemandProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageAgriInputDemandProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageAgriInputDemandProfileDTO> result = villageAgriInputDemandProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageAgriInputDemandProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
