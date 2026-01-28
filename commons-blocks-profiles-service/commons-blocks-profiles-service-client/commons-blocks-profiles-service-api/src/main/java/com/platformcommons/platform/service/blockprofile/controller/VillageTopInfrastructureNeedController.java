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


import com.platformcommons.platform.service.blockprofile.api.VillageTopInfrastructureNeedControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageTopInfrastructureNeedDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageTopInfrastructureNeedFacadeExt;


@RestController
@Slf4j
public class VillageTopInfrastructureNeedController implements VillageTopInfrastructureNeedControllerApi {

    private final VillageTopInfrastructureNeedFacadeExt villageTopInfrastructureNeedFacadeExt;

    /**
     * Constructs a VillageTopInfrastructureNeedController with the specified facade.
     *
     * @param villageTopInfrastructureNeedFacadeExt The VillageTopInfrastructureNeed facade extension to be used
     */
    public VillageTopInfrastructureNeedController(VillageTopInfrastructureNeedFacadeExt villageTopInfrastructureNeedFacadeExt) {
        this.villageTopInfrastructureNeedFacadeExt =villageTopInfrastructureNeedFacadeExt;
    }

    /**
     * Creates a new VillageTopInfrastructureNeed.
     *
     * @param villageTopInfrastructureNeedDTO The VillageTopInfrastructureNeed data to create
     * @return ResponseEntity containing the ID of the created VillageTopInfrastructureNeed
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageTopInfrastructureNeedDTO villageTopInfrastructureNeedDTO) {
        log.debug("Entry - create(VillageTopInfrastructureNeedDTO={})" , villageTopInfrastructureNeedDTO);
        Long id = villageTopInfrastructureNeedFacadeExt.save(villageTopInfrastructureNeedDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageTopInfrastructureNeedUri(id)).body(id);
    }

    /**
     * Updates an existing VillageTopInfrastructureNeed.
     *
     * @param villageTopInfrastructureNeedDTO The VillageTopInfrastructureNeed data to update
     * @return ResponseEntity containing the updated VillageTopInfrastructureNeed data
     */
    @Override
    public ResponseEntity<VillageTopInfrastructureNeedDTO> update(@RequestBody VillageTopInfrastructureNeedDTO villageTopInfrastructureNeedDTO) {
        log.debug("Entry - update(VillageTopInfrastructureNeedDTO={})", villageTopInfrastructureNeedDTO);
        VillageTopInfrastructureNeedDTO updated = villageTopInfrastructureNeedFacadeExt.update(villageTopInfrastructureNeedDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageTopInfrastructureNeeds.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageTopInfrastructureNeeds
     */
    @Override
    public ResponseEntity<PageDTO<VillageTopInfrastructureNeedDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageTopInfrastructureNeedDTO> result = villageTopInfrastructureNeedFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageTopInfrastructureNeed by their ID.
     *
     * @param id The ID of the VillageTopInfrastructureNeed to retrieve
     * @return ResponseEntity containing the VillageTopInfrastructureNeed data
     */
    @Override
    public ResponseEntity<VillageTopInfrastructureNeedDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageTopInfrastructureNeedDTO dto = villageTopInfrastructureNeedFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageTopInfrastructureNeed by their ID with an optional reason.
     *
     * @param id     The ID of the VillageTopInfrastructureNeed to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageTopInfrastructureNeedFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageTopInfrastructureNeedDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageTopInfrastructureNeedDTO> result = villageTopInfrastructureNeedFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageTopInfrastructureNeedUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
