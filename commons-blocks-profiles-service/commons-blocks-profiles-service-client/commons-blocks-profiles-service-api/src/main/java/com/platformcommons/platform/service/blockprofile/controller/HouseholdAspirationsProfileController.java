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


import com.platformcommons.platform.service.blockprofile.api.HouseholdAspirationsProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdAspirationsProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HouseholdAspirationsProfileFacadeExt;


@RestController
@Slf4j
public class HouseholdAspirationsProfileController implements HouseholdAspirationsProfileControllerApi {

    private final HouseholdAspirationsProfileFacadeExt householdAspirationsProfileFacadeExt;

    /**
     * Constructs a HouseholdAspirationsProfileController with the specified facade.
     *
     * @param householdAspirationsProfileFacadeExt The HouseholdAspirationsProfile facade extension to be used
     */
    public HouseholdAspirationsProfileController(HouseholdAspirationsProfileFacadeExt householdAspirationsProfileFacadeExt) {
        this.householdAspirationsProfileFacadeExt =householdAspirationsProfileFacadeExt;
    }

    /**
     * Creates a new HouseholdAspirationsProfile.
     *
     * @param householdAspirationsProfileDTO The HouseholdAspirationsProfile data to create
     * @return ResponseEntity containing the ID of the created HouseholdAspirationsProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HouseholdAspirationsProfileDTO householdAspirationsProfileDTO) {
        log.debug("Entry - create(HouseholdAspirationsProfileDTO={})" , householdAspirationsProfileDTO);
        Long id = householdAspirationsProfileFacadeExt.save(householdAspirationsProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHouseholdAspirationsProfileUri(id)).body(id);
    }

    /**
     * Updates an existing HouseholdAspirationsProfile.
     *
     * @param householdAspirationsProfileDTO The HouseholdAspirationsProfile data to update
     * @return ResponseEntity containing the updated HouseholdAspirationsProfile data
     */
    @Override
    public ResponseEntity<HouseholdAspirationsProfileDTO> update(@RequestBody HouseholdAspirationsProfileDTO householdAspirationsProfileDTO) {
        log.debug("Entry - update(HouseholdAspirationsProfileDTO={})", householdAspirationsProfileDTO);
        HouseholdAspirationsProfileDTO updated = householdAspirationsProfileFacadeExt.update(householdAspirationsProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HouseholdAspirationsProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HouseholdAspirationsProfiles
     */
    @Override
    public ResponseEntity<PageDTO<HouseholdAspirationsProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HouseholdAspirationsProfileDTO> result = householdAspirationsProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HouseholdAspirationsProfile by their ID.
     *
     * @param id The ID of the HouseholdAspirationsProfile to retrieve
     * @return ResponseEntity containing the HouseholdAspirationsProfile data
     */
    @Override
    public ResponseEntity<HouseholdAspirationsProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdAspirationsProfileDTO dto = householdAspirationsProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HouseholdAspirationsProfile by their ID with an optional reason.
     *
     * @param id     The ID of the HouseholdAspirationsProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        householdAspirationsProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HouseholdAspirationsProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HouseholdAspirationsProfileDTO> result = householdAspirationsProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHouseholdAspirationsProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
