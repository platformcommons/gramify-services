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


import com.platformcommons.platform.service.blockprofile.api.VillageFinancialInstitutionProfControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageFinancialInstitutionProfDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageFinancialInstitutionProfFacadeExt;


@RestController
@Slf4j
public class VillageFinancialInstitutionProfController implements VillageFinancialInstitutionProfControllerApi {

    private final VillageFinancialInstitutionProfFacadeExt villageFinancialInstitutionProfFacadeExt;

    /**
     * Constructs a VillageFinancialInstitutionProfController with the specified facade.
     *
     * @param villageFinancialInstitutionProfFacadeExt The VillageFinancialInstitutionProf facade extension to be used
     */
    public VillageFinancialInstitutionProfController(VillageFinancialInstitutionProfFacadeExt villageFinancialInstitutionProfFacadeExt) {
        this.villageFinancialInstitutionProfFacadeExt =villageFinancialInstitutionProfFacadeExt;
    }

    /**
     * Creates a new VillageFinancialInstitutionProf.
     *
     * @param villageFinancialInstitutionProfDTO The VillageFinancialInstitutionProf data to create
     * @return ResponseEntity containing the ID of the created VillageFinancialInstitutionProf
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageFinancialInstitutionProfDTO villageFinancialInstitutionProfDTO) {
        log.debug("Entry - create(VillageFinancialInstitutionProfDTO={})" , villageFinancialInstitutionProfDTO);
        Long id = villageFinancialInstitutionProfFacadeExt.save(villageFinancialInstitutionProfDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageFinancialInstitutionProfUri(id)).body(id);
    }

    /**
     * Updates an existing VillageFinancialInstitutionProf.
     *
     * @param villageFinancialInstitutionProfDTO The VillageFinancialInstitutionProf data to update
     * @return ResponseEntity containing the updated VillageFinancialInstitutionProf data
     */
    @Override
    public ResponseEntity<VillageFinancialInstitutionProfDTO> update(@RequestBody VillageFinancialInstitutionProfDTO villageFinancialInstitutionProfDTO) {
        log.debug("Entry - update(VillageFinancialInstitutionProfDTO={})", villageFinancialInstitutionProfDTO);
        VillageFinancialInstitutionProfDTO updated = villageFinancialInstitutionProfFacadeExt.update(villageFinancialInstitutionProfDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageFinancialInstitutionProfs.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageFinancialInstitutionProfs
     */
    @Override
    public ResponseEntity<PageDTO<VillageFinancialInstitutionProfDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageFinancialInstitutionProfDTO> result = villageFinancialInstitutionProfFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageFinancialInstitutionProf by their ID.
     *
     * @param id The ID of the VillageFinancialInstitutionProf to retrieve
     * @return ResponseEntity containing the VillageFinancialInstitutionProf data
     */
    @Override
    public ResponseEntity<VillageFinancialInstitutionProfDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageFinancialInstitutionProfDTO dto = villageFinancialInstitutionProfFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageFinancialInstitutionProf by their ID with an optional reason.
     *
     * @param id     The ID of the VillageFinancialInstitutionProf to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageFinancialInstitutionProfFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageFinancialInstitutionProfDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageFinancialInstitutionProfDTO> result = villageFinancialInstitutionProfFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageFinancialInstitutionProfUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
