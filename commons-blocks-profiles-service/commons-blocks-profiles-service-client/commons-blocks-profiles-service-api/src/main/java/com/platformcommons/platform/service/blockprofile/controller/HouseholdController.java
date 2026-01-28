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


import com.platformcommons.platform.service.blockprofile.api.HouseholdControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HouseholdFacadeExt;


@RestController
@Slf4j
public class HouseholdController implements HouseholdControllerApi {

    private final HouseholdFacadeExt householdFacadeExt;

    /**
     * Constructs a HouseholdController with the specified facade.
     *
     * @param householdFacadeExt The Household facade extension to be used
     */
    public HouseholdController(HouseholdFacadeExt householdFacadeExt) {
        this.householdFacadeExt =householdFacadeExt;
    }

    /**
     * Creates a new Household.
     *
     * @param householdDTO The Household data to create
     * @return ResponseEntity containing the ID of the created Household
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HouseholdDTO householdDTO) {
        log.debug("Entry - create(HouseholdDTO={})" , householdDTO);
        Long id = householdFacadeExt.save(householdDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHouseholdUri(id)).body(id);
    }

    /**
     * Updates an existing Household.
     *
     * @param householdDTO The Household data to update
     * @return ResponseEntity containing the updated Household data
     */
    @Override
    public ResponseEntity<HouseholdDTO> update(@RequestBody HouseholdDTO householdDTO) {
        log.debug("Entry - update(HouseholdDTO={})", householdDTO);
        HouseholdDTO updated = householdFacadeExt.update(householdDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of Households.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of Households
     */
    @Override
    public ResponseEntity<PageDTO<HouseholdDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HouseholdDTO> result = householdFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a Household by their ID.
     *
     * @param id The ID of the Household to retrieve
     * @return ResponseEntity containing the Household data
     */
    @Override
    public ResponseEntity<HouseholdDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdDTO dto = householdFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a Household by their ID with an optional reason.
     *
     * @param id     The ID of the Household to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        householdFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HouseholdDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HouseholdDTO> result = householdFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHouseholdUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
