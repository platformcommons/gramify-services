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


import com.platformcommons.platform.service.blockprofile.api.VillageInfrastructureConstraintControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageInfrastructureConstraintDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageInfrastructureConstraintFacadeExt;


@RestController
@Slf4j
public class VillageInfrastructureConstraintController implements VillageInfrastructureConstraintControllerApi {

    private final VillageInfrastructureConstraintFacadeExt villageInfrastructureConstraintFacadeExt;

    /**
     * Constructs a VillageInfrastructureConstraintController with the specified facade.
     *
     * @param villageInfrastructureConstraintFacadeExt The VillageInfrastructureConstraint facade extension to be used
     */
    public VillageInfrastructureConstraintController(VillageInfrastructureConstraintFacadeExt villageInfrastructureConstraintFacadeExt) {
        this.villageInfrastructureConstraintFacadeExt =villageInfrastructureConstraintFacadeExt;
    }

    /**
     * Creates a new VillageInfrastructureConstraint.
     *
     * @param villageInfrastructureConstraintDTO The VillageInfrastructureConstraint data to create
     * @return ResponseEntity containing the ID of the created VillageInfrastructureConstraint
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageInfrastructureConstraintDTO villageInfrastructureConstraintDTO) {
        log.debug("Entry - create(VillageInfrastructureConstraintDTO={})" , villageInfrastructureConstraintDTO);
        Long id = villageInfrastructureConstraintFacadeExt.save(villageInfrastructureConstraintDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageInfrastructureConstraintUri(id)).body(id);
    }

    /**
     * Updates an existing VillageInfrastructureConstraint.
     *
     * @param villageInfrastructureConstraintDTO The VillageInfrastructureConstraint data to update
     * @return ResponseEntity containing the updated VillageInfrastructureConstraint data
     */
    @Override
    public ResponseEntity<VillageInfrastructureConstraintDTO> update(@RequestBody VillageInfrastructureConstraintDTO villageInfrastructureConstraintDTO) {
        log.debug("Entry - update(VillageInfrastructureConstraintDTO={})", villageInfrastructureConstraintDTO);
        VillageInfrastructureConstraintDTO updated = villageInfrastructureConstraintFacadeExt.update(villageInfrastructureConstraintDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageInfrastructureConstraints.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageInfrastructureConstraints
     */
    @Override
    public ResponseEntity<PageDTO<VillageInfrastructureConstraintDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageInfrastructureConstraintDTO> result = villageInfrastructureConstraintFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageInfrastructureConstraint by their ID.
     *
     * @param id The ID of the VillageInfrastructureConstraint to retrieve
     * @return ResponseEntity containing the VillageInfrastructureConstraint data
     */
    @Override
    public ResponseEntity<VillageInfrastructureConstraintDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageInfrastructureConstraintDTO dto = villageInfrastructureConstraintFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageInfrastructureConstraint by their ID with an optional reason.
     *
     * @param id     The ID of the VillageInfrastructureConstraint to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageInfrastructureConstraintFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageInfrastructureConstraintDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageInfrastructureConstraintDTO> result = villageInfrastructureConstraintFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageInfrastructureConstraintUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
