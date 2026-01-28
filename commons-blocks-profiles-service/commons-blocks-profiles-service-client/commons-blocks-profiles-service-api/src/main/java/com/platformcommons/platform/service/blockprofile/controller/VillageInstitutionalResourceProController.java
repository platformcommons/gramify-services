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


import com.platformcommons.platform.service.blockprofile.api.VillageInstitutionalResourceProControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageInstitutionalResourceProDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageInstitutionalResourceProFacadeExt;


@RestController
@Slf4j
public class VillageInstitutionalResourceProController implements VillageInstitutionalResourceProControllerApi {

    private final VillageInstitutionalResourceProFacadeExt villageInstitutionalResourceProFacadeExt;

    /**
     * Constructs a VillageInstitutionalResourceProController with the specified facade.
     *
     * @param villageInstitutionalResourceProFacadeExt The VillageInstitutionalResourcePro facade extension to be used
     */
    public VillageInstitutionalResourceProController(VillageInstitutionalResourceProFacadeExt villageInstitutionalResourceProFacadeExt) {
        this.villageInstitutionalResourceProFacadeExt =villageInstitutionalResourceProFacadeExt;
    }

    /**
     * Creates a new VillageInstitutionalResourcePro.
     *
     * @param villageInstitutionalResourceProDTO The VillageInstitutionalResourcePro data to create
     * @return ResponseEntity containing the ID of the created VillageInstitutionalResourcePro
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageInstitutionalResourceProDTO villageInstitutionalResourceProDTO) {
        log.debug("Entry - create(VillageInstitutionalResourceProDTO={})" , villageInstitutionalResourceProDTO);
        Long id = villageInstitutionalResourceProFacadeExt.save(villageInstitutionalResourceProDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageInstitutionalResourceProUri(id)).body(id);
    }

    /**
     * Updates an existing VillageInstitutionalResourcePro.
     *
     * @param villageInstitutionalResourceProDTO The VillageInstitutionalResourcePro data to update
     * @return ResponseEntity containing the updated VillageInstitutionalResourcePro data
     */
    @Override
    public ResponseEntity<VillageInstitutionalResourceProDTO> update(@RequestBody VillageInstitutionalResourceProDTO villageInstitutionalResourceProDTO) {
        log.debug("Entry - update(VillageInstitutionalResourceProDTO={})", villageInstitutionalResourceProDTO);
        VillageInstitutionalResourceProDTO updated = villageInstitutionalResourceProFacadeExt.update(villageInstitutionalResourceProDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageInstitutionalResourcePros.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageInstitutionalResourcePros
     */
    @Override
    public ResponseEntity<PageDTO<VillageInstitutionalResourceProDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageInstitutionalResourceProDTO> result = villageInstitutionalResourceProFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageInstitutionalResourcePro by their ID.
     *
     * @param id The ID of the VillageInstitutionalResourcePro to retrieve
     * @return ResponseEntity containing the VillageInstitutionalResourcePro data
     */
    @Override
    public ResponseEntity<VillageInstitutionalResourceProDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageInstitutionalResourceProDTO dto = villageInstitutionalResourceProFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageInstitutionalResourcePro by their ID with an optional reason.
     *
     * @param id     The ID of the VillageInstitutionalResourcePro to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageInstitutionalResourceProFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageInstitutionalResourceProDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageInstitutionalResourceProDTO> result = villageInstitutionalResourceProFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageInstitutionalResourceProUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
