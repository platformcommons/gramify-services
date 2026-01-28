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


import com.platformcommons.platform.service.blockprofile.api.HouseholdHumanResourceProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdHumanResourceProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HouseholdHumanResourceProfileFacadeExt;


@RestController
@Slf4j
public class HouseholdHumanResourceProfileController implements HouseholdHumanResourceProfileControllerApi {

    private final HouseholdHumanResourceProfileFacadeExt householdHumanResourceProfileFacadeExt;

    /**
     * Constructs a HouseholdHumanResourceProfileController with the specified facade.
     *
     * @param householdHumanResourceProfileFacadeExt The HouseholdHumanResourceProfile facade extension to be used
     */
    public HouseholdHumanResourceProfileController(HouseholdHumanResourceProfileFacadeExt householdHumanResourceProfileFacadeExt) {
        this.householdHumanResourceProfileFacadeExt =householdHumanResourceProfileFacadeExt;
    }

    /**
     * Creates a new HouseholdHumanResourceProfile.
     *
     * @param householdHumanResourceProfileDTO The HouseholdHumanResourceProfile data to create
     * @return ResponseEntity containing the ID of the created HouseholdHumanResourceProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HouseholdHumanResourceProfileDTO householdHumanResourceProfileDTO) {
        log.debug("Entry - create(HouseholdHumanResourceProfileDTO={})" , householdHumanResourceProfileDTO);
        Long id = householdHumanResourceProfileFacadeExt.save(householdHumanResourceProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHouseholdHumanResourceProfileUri(id)).body(id);
    }

    /**
     * Updates an existing HouseholdHumanResourceProfile.
     *
     * @param householdHumanResourceProfileDTO The HouseholdHumanResourceProfile data to update
     * @return ResponseEntity containing the updated HouseholdHumanResourceProfile data
     */
    @Override
    public ResponseEntity<HouseholdHumanResourceProfileDTO> update(@RequestBody HouseholdHumanResourceProfileDTO householdHumanResourceProfileDTO) {
        log.debug("Entry - update(HouseholdHumanResourceProfileDTO={})", householdHumanResourceProfileDTO);
        HouseholdHumanResourceProfileDTO updated = householdHumanResourceProfileFacadeExt.update(householdHumanResourceProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HouseholdHumanResourceProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HouseholdHumanResourceProfiles
     */
    @Override
    public ResponseEntity<PageDTO<HouseholdHumanResourceProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HouseholdHumanResourceProfileDTO> result = householdHumanResourceProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HouseholdHumanResourceProfile by their ID.
     *
     * @param id The ID of the HouseholdHumanResourceProfile to retrieve
     * @return ResponseEntity containing the HouseholdHumanResourceProfile data
     */
    @Override
    public ResponseEntity<HouseholdHumanResourceProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdHumanResourceProfileDTO dto = householdHumanResourceProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HouseholdHumanResourceProfile by their ID with an optional reason.
     *
     * @param id     The ID of the HouseholdHumanResourceProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        householdHumanResourceProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HouseholdHumanResourceProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HouseholdHumanResourceProfileDTO> result = householdHumanResourceProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHouseholdHumanResourceProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
