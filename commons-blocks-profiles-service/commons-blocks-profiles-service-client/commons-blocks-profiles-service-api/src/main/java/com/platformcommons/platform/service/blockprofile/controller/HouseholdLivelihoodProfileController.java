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


import com.platformcommons.platform.service.blockprofile.api.HouseholdLivelihoodProfileControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdLivelihoodProfileDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HouseholdLivelihoodProfileFacadeExt;


@RestController
@Slf4j
public class HouseholdLivelihoodProfileController implements HouseholdLivelihoodProfileControllerApi {

    private final HouseholdLivelihoodProfileFacadeExt householdLivelihoodProfileFacadeExt;

    /**
     * Constructs a HouseholdLivelihoodProfileController with the specified facade.
     *
     * @param householdLivelihoodProfileFacadeExt The HouseholdLivelihoodProfile facade extension to be used
     */
    public HouseholdLivelihoodProfileController(HouseholdLivelihoodProfileFacadeExt householdLivelihoodProfileFacadeExt) {
        this.householdLivelihoodProfileFacadeExt =householdLivelihoodProfileFacadeExt;
    }

    /**
     * Creates a new HouseholdLivelihoodProfile.
     *
     * @param householdLivelihoodProfileDTO The HouseholdLivelihoodProfile data to create
     * @return ResponseEntity containing the ID of the created HouseholdLivelihoodProfile
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HouseholdLivelihoodProfileDTO householdLivelihoodProfileDTO) {
        log.debug("Entry - create(HouseholdLivelihoodProfileDTO={})" , householdLivelihoodProfileDTO);
        Long id = householdLivelihoodProfileFacadeExt.save(householdLivelihoodProfileDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHouseholdLivelihoodProfileUri(id)).body(id);
    }

    /**
     * Updates an existing HouseholdLivelihoodProfile.
     *
     * @param householdLivelihoodProfileDTO The HouseholdLivelihoodProfile data to update
     * @return ResponseEntity containing the updated HouseholdLivelihoodProfile data
     */
    @Override
    public ResponseEntity<HouseholdLivelihoodProfileDTO> update(@RequestBody HouseholdLivelihoodProfileDTO householdLivelihoodProfileDTO) {
        log.debug("Entry - update(HouseholdLivelihoodProfileDTO={})", householdLivelihoodProfileDTO);
        HouseholdLivelihoodProfileDTO updated = householdLivelihoodProfileFacadeExt.update(householdLivelihoodProfileDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HouseholdLivelihoodProfiles.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HouseholdLivelihoodProfiles
     */
    @Override
    public ResponseEntity<PageDTO<HouseholdLivelihoodProfileDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HouseholdLivelihoodProfileDTO> result = householdLivelihoodProfileFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HouseholdLivelihoodProfile by their ID.
     *
     * @param id The ID of the HouseholdLivelihoodProfile to retrieve
     * @return ResponseEntity containing the HouseholdLivelihoodProfile data
     */
    @Override
    public ResponseEntity<HouseholdLivelihoodProfileDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdLivelihoodProfileDTO dto = householdLivelihoodProfileFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HouseholdLivelihoodProfile by their ID with an optional reason.
     *
     * @param id     The ID of the HouseholdLivelihoodProfile to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        householdLivelihoodProfileFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HouseholdLivelihoodProfileDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HouseholdLivelihoodProfileDTO> result = householdLivelihoodProfileFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHouseholdLivelihoodProfileUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
