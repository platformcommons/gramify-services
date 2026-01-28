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


import com.platformcommons.platform.service.blockprofile.api.HouseholdDemographicsControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdDemographicsDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HouseholdDemographicsFacadeExt;


@RestController
@Slf4j
public class HouseholdDemographicsController implements HouseholdDemographicsControllerApi {

    private final HouseholdDemographicsFacadeExt householdDemographicsFacadeExt;

    /**
     * Constructs a HouseholdDemographicsController with the specified facade.
     *
     * @param householdDemographicsFacadeExt The HouseholdDemographics facade extension to be used
     */
    public HouseholdDemographicsController(HouseholdDemographicsFacadeExt householdDemographicsFacadeExt) {
        this.householdDemographicsFacadeExt =householdDemographicsFacadeExt;
    }

    /**
     * Creates a new HouseholdDemographics.
     *
     * @param householdDemographicsDTO The HouseholdDemographics data to create
     * @return ResponseEntity containing the ID of the created HouseholdDemographics
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HouseholdDemographicsDTO householdDemographicsDTO) {
        log.debug("Entry - create(HouseholdDemographicsDTO={})" , householdDemographicsDTO);
        Long id = householdDemographicsFacadeExt.save(householdDemographicsDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHouseholdDemographicsUri(id)).body(id);
    }

    /**
     * Updates an existing HouseholdDemographics.
     *
     * @param householdDemographicsDTO The HouseholdDemographics data to update
     * @return ResponseEntity containing the updated HouseholdDemographics data
     */
    @Override
    public ResponseEntity<HouseholdDemographicsDTO> update(@RequestBody HouseholdDemographicsDTO householdDemographicsDTO) {
        log.debug("Entry - update(HouseholdDemographicsDTO={})", householdDemographicsDTO);
        HouseholdDemographicsDTO updated = householdDemographicsFacadeExt.update(householdDemographicsDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HouseholdDemographicss.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HouseholdDemographicss
     */
    @Override
    public ResponseEntity<PageDTO<HouseholdDemographicsDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HouseholdDemographicsDTO> result = householdDemographicsFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HouseholdDemographics by their ID.
     *
     * @param id The ID of the HouseholdDemographics to retrieve
     * @return ResponseEntity containing the HouseholdDemographics data
     */
    @Override
    public ResponseEntity<HouseholdDemographicsDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdDemographicsDTO dto = householdDemographicsFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HouseholdDemographics by their ID with an optional reason.
     *
     * @param id     The ID of the HouseholdDemographics to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        householdDemographicsFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HouseholdDemographicsDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HouseholdDemographicsDTO> result = householdDemographicsFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHouseholdDemographicsUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
