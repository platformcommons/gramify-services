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


import com.platformcommons.platform.service.blockprofile.api.HouseholdIncomeAndPovertyProfilControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.HouseholdIncomeAndPovertyProfilDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.HouseholdIncomeAndPovertyProfilFacadeExt;


@RestController
@Slf4j
public class HouseholdIncomeAndPovertyProfilController implements HouseholdIncomeAndPovertyProfilControllerApi {

    private final HouseholdIncomeAndPovertyProfilFacadeExt householdIncomeAndPovertyProfilFacadeExt;

    /**
     * Constructs a HouseholdIncomeAndPovertyProfilController with the specified facade.
     *
     * @param householdIncomeAndPovertyProfilFacadeExt The HouseholdIncomeAndPovertyProfil facade extension to be used
     */
    public HouseholdIncomeAndPovertyProfilController(HouseholdIncomeAndPovertyProfilFacadeExt householdIncomeAndPovertyProfilFacadeExt) {
        this.householdIncomeAndPovertyProfilFacadeExt =householdIncomeAndPovertyProfilFacadeExt;
    }

    /**
     * Creates a new HouseholdIncomeAndPovertyProfil.
     *
     * @param householdIncomeAndPovertyProfilDTO The HouseholdIncomeAndPovertyProfil data to create
     * @return ResponseEntity containing the ID of the created HouseholdIncomeAndPovertyProfil
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody HouseholdIncomeAndPovertyProfilDTO householdIncomeAndPovertyProfilDTO) {
        log.debug("Entry - create(HouseholdIncomeAndPovertyProfilDTO={})" , householdIncomeAndPovertyProfilDTO);
        Long id = householdIncomeAndPovertyProfilFacadeExt.save(householdIncomeAndPovertyProfilDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createHouseholdIncomeAndPovertyProfilUri(id)).body(id);
    }

    /**
     * Updates an existing HouseholdIncomeAndPovertyProfil.
     *
     * @param householdIncomeAndPovertyProfilDTO The HouseholdIncomeAndPovertyProfil data to update
     * @return ResponseEntity containing the updated HouseholdIncomeAndPovertyProfil data
     */
    @Override
    public ResponseEntity<HouseholdIncomeAndPovertyProfilDTO> update(@RequestBody HouseholdIncomeAndPovertyProfilDTO householdIncomeAndPovertyProfilDTO) {
        log.debug("Entry - update(HouseholdIncomeAndPovertyProfilDTO={})", householdIncomeAndPovertyProfilDTO);
        HouseholdIncomeAndPovertyProfilDTO updated = householdIncomeAndPovertyProfilFacadeExt.update(householdIncomeAndPovertyProfilDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of HouseholdIncomeAndPovertyProfils.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of HouseholdIncomeAndPovertyProfils
     */
    @Override
    public ResponseEntity<PageDTO<HouseholdIncomeAndPovertyProfilDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<HouseholdIncomeAndPovertyProfilDTO> result = householdIncomeAndPovertyProfilFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a HouseholdIncomeAndPovertyProfil by their ID.
     *
     * @param id The ID of the HouseholdIncomeAndPovertyProfil to retrieve
     * @return ResponseEntity containing the HouseholdIncomeAndPovertyProfil data
     */
    @Override
    public ResponseEntity<HouseholdIncomeAndPovertyProfilDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        HouseholdIncomeAndPovertyProfilDTO dto = householdIncomeAndPovertyProfilFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a HouseholdIncomeAndPovertyProfil by their ID with an optional reason.
     *
     * @param id     The ID of the HouseholdIncomeAndPovertyProfil to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        householdIncomeAndPovertyProfilFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<HouseholdIncomeAndPovertyProfilDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<HouseholdIncomeAndPovertyProfilDTO> result = householdIncomeAndPovertyProfilFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createHouseholdIncomeAndPovertyProfilUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
