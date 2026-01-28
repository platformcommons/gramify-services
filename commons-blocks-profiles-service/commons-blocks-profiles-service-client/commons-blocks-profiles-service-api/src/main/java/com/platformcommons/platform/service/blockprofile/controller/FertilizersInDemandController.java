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


import com.platformcommons.platform.service.blockprofile.api.FertilizersInDemandControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.FertilizersInDemandDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.FertilizersInDemandFacadeExt;


@RestController
@Slf4j
public class FertilizersInDemandController implements FertilizersInDemandControllerApi {

    private final FertilizersInDemandFacadeExt fertilizersInDemandFacadeExt;

    /**
     * Constructs a FertilizersInDemandController with the specified facade.
     *
     * @param fertilizersInDemandFacadeExt The FertilizersInDemand facade extension to be used
     */
    public FertilizersInDemandController(FertilizersInDemandFacadeExt fertilizersInDemandFacadeExt) {
        this.fertilizersInDemandFacadeExt =fertilizersInDemandFacadeExt;
    }

    /**
     * Creates a new FertilizersInDemand.
     *
     * @param fertilizersInDemandDTO The FertilizersInDemand data to create
     * @return ResponseEntity containing the ID of the created FertilizersInDemand
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody FertilizersInDemandDTO fertilizersInDemandDTO) {
        log.debug("Entry - create(FertilizersInDemandDTO={})" , fertilizersInDemandDTO);
        Long id = fertilizersInDemandFacadeExt.save(fertilizersInDemandDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createFertilizersInDemandUri(id)).body(id);
    }

    /**
     * Updates an existing FertilizersInDemand.
     *
     * @param fertilizersInDemandDTO The FertilizersInDemand data to update
     * @return ResponseEntity containing the updated FertilizersInDemand data
     */
    @Override
    public ResponseEntity<FertilizersInDemandDTO> update(@RequestBody FertilizersInDemandDTO fertilizersInDemandDTO) {
        log.debug("Entry - update(FertilizersInDemandDTO={})", fertilizersInDemandDTO);
        FertilizersInDemandDTO updated = fertilizersInDemandFacadeExt.update(fertilizersInDemandDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of FertilizersInDemands.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of FertilizersInDemands
     */
    @Override
    public ResponseEntity<PageDTO<FertilizersInDemandDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<FertilizersInDemandDTO> result = fertilizersInDemandFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a FertilizersInDemand by their ID.
     *
     * @param id The ID of the FertilizersInDemand to retrieve
     * @return ResponseEntity containing the FertilizersInDemand data
     */
    @Override
    public ResponseEntity<FertilizersInDemandDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        FertilizersInDemandDTO dto = fertilizersInDemandFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a FertilizersInDemand by their ID with an optional reason.
     *
     * @param id     The ID of the FertilizersInDemand to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        fertilizersInDemandFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<FertilizersInDemandDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<FertilizersInDemandDTO> result = fertilizersInDemandFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createFertilizersInDemandUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
