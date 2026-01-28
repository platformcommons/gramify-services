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


import com.platformcommons.platform.service.blockprofile.api.VillageCommonWildlifeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageCommonWildlifeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageCommonWildlifeFacadeExt;


@RestController
@Slf4j
public class VillageCommonWildlifeController implements VillageCommonWildlifeControllerApi {

    private final VillageCommonWildlifeFacadeExt villageCommonWildlifeFacadeExt;

    /**
     * Constructs a VillageCommonWildlifeController with the specified facade.
     *
     * @param villageCommonWildlifeFacadeExt The VillageCommonWildlife facade extension to be used
     */
    public VillageCommonWildlifeController(VillageCommonWildlifeFacadeExt villageCommonWildlifeFacadeExt) {
        this.villageCommonWildlifeFacadeExt =villageCommonWildlifeFacadeExt;
    }

    /**
     * Creates a new VillageCommonWildlife.
     *
     * @param villageCommonWildlifeDTO The VillageCommonWildlife data to create
     * @return ResponseEntity containing the ID of the created VillageCommonWildlife
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageCommonWildlifeDTO villageCommonWildlifeDTO) {
        log.debug("Entry - create(VillageCommonWildlifeDTO={})" , villageCommonWildlifeDTO);
        Long id = villageCommonWildlifeFacadeExt.save(villageCommonWildlifeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageCommonWildlifeUri(id)).body(id);
    }

    /**
     * Updates an existing VillageCommonWildlife.
     *
     * @param villageCommonWildlifeDTO The VillageCommonWildlife data to update
     * @return ResponseEntity containing the updated VillageCommonWildlife data
     */
    @Override
    public ResponseEntity<VillageCommonWildlifeDTO> update(@RequestBody VillageCommonWildlifeDTO villageCommonWildlifeDTO) {
        log.debug("Entry - update(VillageCommonWildlifeDTO={})", villageCommonWildlifeDTO);
        VillageCommonWildlifeDTO updated = villageCommonWildlifeFacadeExt.update(villageCommonWildlifeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageCommonWildlifes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageCommonWildlifes
     */
    @Override
    public ResponseEntity<PageDTO<VillageCommonWildlifeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageCommonWildlifeDTO> result = villageCommonWildlifeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageCommonWildlife by their ID.
     *
     * @param id The ID of the VillageCommonWildlife to retrieve
     * @return ResponseEntity containing the VillageCommonWildlife data
     */
    @Override
    public ResponseEntity<VillageCommonWildlifeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCommonWildlifeDTO dto = villageCommonWildlifeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageCommonWildlife by their ID with an optional reason.
     *
     * @param id     The ID of the VillageCommonWildlife to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageCommonWildlifeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageCommonWildlifeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageCommonWildlifeDTO> result = villageCommonWildlifeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageCommonWildlifeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
