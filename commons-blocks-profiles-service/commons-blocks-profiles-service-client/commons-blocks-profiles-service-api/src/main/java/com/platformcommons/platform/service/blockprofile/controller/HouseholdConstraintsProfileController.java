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


import com.platformcommons.platform.service.blockprofile.api.HouseholdConstraintsProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdConstraintsProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HouseholdConstraintsProfileFacadeExt;


@RestController
@Slf4j
public class HouseholdConstraintsProfileController implements HouseholdConstraintsProfileControllerApi {

    private final HouseholdConstraintsProfileFacadeExt householdConstraintsProfileFacadeExt;

    /**
     * Constructs a HouseholdConstraintsProfileController with the specified facade.
     *
     * @param householdConstraintsProfileFacadeExt The HouseholdConstraintsProfile facade extension to be used
     */
    public HouseholdConstraintsProfileController(HouseholdConstraintsProfileFacadeExt householdConstraintsProfileFacadeExt) {
        this.householdConstraintsProfileFacadeExt =householdConstraintsProfileFacadeExt;
    }

    /**
     * Creates a new HouseholdConstraintsProfile.
     *
     * @param householdConstraintsProfileDTO The HouseholdConstraintsProfile data to create
     * @return ResponseEntity containing the ID of the created HouseholdConstraintsProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HouseholdConstraintsProfileDTO householdConstraintsProfileDTO) {
        log.debug("Entry - create(HouseholdConstraintsProfileDTO={})" , householdConstraintsProfileDTO);
        Long id = householdConstraintsProfileFacadeExt.save(householdConstraintsProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHouseholdConstraintsProfileUri(id)).body(id);
    }

    /**
     * Updates an existing HouseholdConstraintsProfile.
     *
     * @param householdConstraintsProfileDTO The HouseholdConstraintsProfile data to update
     * @return ResponseEntity containing the updated HouseholdConstraintsProfile data
     */
    @Override
    public ResponseEntity<HouseholdConstraintsProfileDTO> update(@RequestBody HouseholdConstraintsProfileDTO householdConstraintsProfileDTO) {
        log.debug("Entry - update(HouseholdConstraintsProfileDTO={})", householdConstraintsProfileDTO);
        HouseholdConstraintsProfileDTO updated = householdConstraintsProfileFacadeExt.update(householdConstraintsProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HouseholdConstraintsProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HouseholdConstraintsProfiles
     */
    @Override
    public ResponseEntity<PageDTO<HouseholdConstraintsProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HouseholdConstraintsProfileDTO> result = householdConstraintsProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HouseholdConstraintsProfile by their ID.
     *
     * @param id The ID of the HouseholdConstraintsProfile to retrieve
     * @return ResponseEntity containing the HouseholdConstraintsProfile data
     */
    @Override
    public ResponseEntity<HouseholdConstraintsProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdConstraintsProfileDTO dto = householdConstraintsProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HouseholdConstraintsProfile by their ID with an optional reason.
     *
     * @param id     The ID of the HouseholdConstraintsProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        householdConstraintsProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HouseholdConstraintsProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HouseholdConstraintsProfileDTO> result = householdConstraintsProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHouseholdConstraintsProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
