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


import com.platformcommons.platform.service.blockprofile.api.ConsumptionControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.ConsumptionDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.ConsumptionFacadeExt;


@RestController
@Slf4j
public class ConsumptionController implements ConsumptionControllerApi {

    private final ConsumptionFacadeExt consumptionFacadeExt;

    /**
     * Constructs a ConsumptionController with the specified facade.
     *
     * @param consumptionFacadeExt The Consumption facade extension to be used
     */
    public ConsumptionController(ConsumptionFacadeExt consumptionFacadeExt) {
        this.consumptionFacadeExt =consumptionFacadeExt;
    }

    /**
     * Creates a new Consumption.
     *
     * @param consumptionDTO The Consumption data to create
     * @return ResponseEntity containing the ID of the created Consumption
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody ConsumptionDTO consumptionDTO) {
        log.debug("Entry - create(ConsumptionDTO={})" , consumptionDTO);
        Long id = consumptionFacadeExt.save(consumptionDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createConsumptionUri(id)).body(id);
    }

    /**
     * Updates an existing Consumption.
     *
     * @param consumptionDTO The Consumption data to update
     * @return ResponseEntity containing the updated Consumption data
     */
    @Override
    public ResponseEntity<ConsumptionDTO> update(@RequestBody ConsumptionDTO consumptionDTO) {
        log.debug("Entry - update(ConsumptionDTO={})", consumptionDTO);
        ConsumptionDTO updated = consumptionFacadeExt.update(consumptionDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of Consumptions.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of Consumptions
     */
    @Override
    public ResponseEntity<PageDTO<ConsumptionDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<ConsumptionDTO> result = consumptionFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a Consumption by their ID.
     *
     * @param id The ID of the Consumption to retrieve
     * @return ResponseEntity containing the Consumption data
     */
    @Override
    public ResponseEntity<ConsumptionDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        ConsumptionDTO dto = consumptionFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a Consumption by their ID with an optional reason.
     *
     * @param id     The ID of the Consumption to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        consumptionFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<ConsumptionDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<ConsumptionDTO> result = consumptionFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createConsumptionUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
