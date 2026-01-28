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


import com.platformcommons.platform.service.blockprofile.api.VillageCommonFaunaControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageCommonFaunaDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageCommonFaunaFacadeExt;


@RestController
@Slf4j
public class VillageCommonFaunaController implements VillageCommonFaunaControllerApi {

    private final VillageCommonFaunaFacadeExt villageCommonFaunaFacadeExt;

    /**
     * Constructs a VillageCommonFaunaController with the specified facade.
     *
     * @param villageCommonFaunaFacadeExt The VillageCommonFauna facade extension to be used
     */
    public VillageCommonFaunaController(VillageCommonFaunaFacadeExt villageCommonFaunaFacadeExt) {
        this.villageCommonFaunaFacadeExt =villageCommonFaunaFacadeExt;
    }

    /**
     * Creates a new VillageCommonFauna.
     *
     * @param villageCommonFaunaDTO The VillageCommonFauna data to create
     * @return ResponseEntity containing the ID of the created VillageCommonFauna
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageCommonFaunaDTO villageCommonFaunaDTO) {
        log.debug("Entry - create(VillageCommonFaunaDTO={})" , villageCommonFaunaDTO);
        Long id = villageCommonFaunaFacadeExt.save(villageCommonFaunaDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageCommonFaunaUri(id)).body(id);
    }

    /**
     * Updates an existing VillageCommonFauna.
     *
     * @param villageCommonFaunaDTO The VillageCommonFauna data to update
     * @return ResponseEntity containing the updated VillageCommonFauna data
     */
    @Override
    public ResponseEntity<VillageCommonFaunaDTO> update(@RequestBody VillageCommonFaunaDTO villageCommonFaunaDTO) {
        log.debug("Entry - update(VillageCommonFaunaDTO={})", villageCommonFaunaDTO);
        VillageCommonFaunaDTO updated = villageCommonFaunaFacadeExt.update(villageCommonFaunaDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageCommonFaunas.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageCommonFaunas
     */
    @Override
    public ResponseEntity<PageDTO<VillageCommonFaunaDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageCommonFaunaDTO> result = villageCommonFaunaFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageCommonFauna by their ID.
     *
     * @param id The ID of the VillageCommonFauna to retrieve
     * @return ResponseEntity containing the VillageCommonFauna data
     */
    @Override
    public ResponseEntity<VillageCommonFaunaDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCommonFaunaDTO dto = villageCommonFaunaFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageCommonFauna by their ID with an optional reason.
     *
     * @param id     The ID of the VillageCommonFauna to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageCommonFaunaFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageCommonFaunaDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageCommonFaunaDTO> result = villageCommonFaunaFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageCommonFaunaUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
