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


import com.platformcommons.platform.service.blockprofile.api.VillageCommonFloraControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageCommonFloraDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageCommonFloraFacadeExt;


@RestController
@Slf4j
public class VillageCommonFloraController implements VillageCommonFloraControllerApi {

    private final VillageCommonFloraFacadeExt villageCommonFloraFacadeExt;

    /**
     * Constructs a VillageCommonFloraController with the specified facade.
     *
     * @param villageCommonFloraFacadeExt The VillageCommonFlora facade extension to be used
     */
    public VillageCommonFloraController(VillageCommonFloraFacadeExt villageCommonFloraFacadeExt) {
        this.villageCommonFloraFacadeExt =villageCommonFloraFacadeExt;
    }

    /**
     * Creates a new VillageCommonFlora.
     *
     * @param villageCommonFloraDTO The VillageCommonFlora data to create
     * @return ResponseEntity containing the ID of the created VillageCommonFlora
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageCommonFloraDTO villageCommonFloraDTO) {
        log.debug("Entry - create(VillageCommonFloraDTO={})" , villageCommonFloraDTO);
        Long id = villageCommonFloraFacadeExt.save(villageCommonFloraDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageCommonFloraUri(id)).body(id);
    }

    /**
     * Updates an existing VillageCommonFlora.
     *
     * @param villageCommonFloraDTO The VillageCommonFlora data to update
     * @return ResponseEntity containing the updated VillageCommonFlora data
     */
    @Override
    public ResponseEntity<VillageCommonFloraDTO> update(@RequestBody VillageCommonFloraDTO villageCommonFloraDTO) {
        log.debug("Entry - update(VillageCommonFloraDTO={})", villageCommonFloraDTO);
        VillageCommonFloraDTO updated = villageCommonFloraFacadeExt.update(villageCommonFloraDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageCommonFloras.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageCommonFloras
     */
    @Override
    public ResponseEntity<PageDTO<VillageCommonFloraDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageCommonFloraDTO> result = villageCommonFloraFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageCommonFlora by their ID.
     *
     * @param id The ID of the VillageCommonFlora to retrieve
     * @return ResponseEntity containing the VillageCommonFlora data
     */
    @Override
    public ResponseEntity<VillageCommonFloraDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageCommonFloraDTO dto = villageCommonFloraFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageCommonFlora by their ID with an optional reason.
     *
     * @param id     The ID of the VillageCommonFlora to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageCommonFloraFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageCommonFloraDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageCommonFloraDTO> result = villageCommonFloraFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageCommonFloraUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
