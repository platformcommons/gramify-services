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


import com.platformcommons.platform.service.blockprofile.api.PowerCutSeasonControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.PowerCutSeasonDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.PowerCutSeasonFacadeExt;


@RestController
@Slf4j
public class PowerCutSeasonController implements PowerCutSeasonControllerApi {

    private final PowerCutSeasonFacadeExt powerCutSeasonFacadeExt;

    /**
     * Constructs a PowerCutSeasonController with the specified facade.
     *
     * @param powerCutSeasonFacadeExt The PowerCutSeason facade extension to be used
     */
    public PowerCutSeasonController(PowerCutSeasonFacadeExt powerCutSeasonFacadeExt) {
        this.powerCutSeasonFacadeExt =powerCutSeasonFacadeExt;
    }

    /**
     * Creates a new PowerCutSeason.
     *
     * @param powerCutSeasonDTO The PowerCutSeason data to create
     * @return ResponseEntity containing the ID of the created PowerCutSeason
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody PowerCutSeasonDTO powerCutSeasonDTO) {
        log.debug("Entry - create(PowerCutSeasonDTO={})" , powerCutSeasonDTO);
        Long id = powerCutSeasonFacadeExt.save(powerCutSeasonDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createPowerCutSeasonUri(id)).body(id);
    }

    /**
     * Updates an existing PowerCutSeason.
     *
     * @param powerCutSeasonDTO The PowerCutSeason data to update
     * @return ResponseEntity containing the updated PowerCutSeason data
     */
    @Override
    public ResponseEntity<PowerCutSeasonDTO> update(@RequestBody PowerCutSeasonDTO powerCutSeasonDTO) {
        log.debug("Entry - update(PowerCutSeasonDTO={})", powerCutSeasonDTO);
        PowerCutSeasonDTO updated = powerCutSeasonFacadeExt.update(powerCutSeasonDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of PowerCutSeasons.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of PowerCutSeasons
     */
    @Override
    public ResponseEntity<PageDTO<PowerCutSeasonDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<PowerCutSeasonDTO> result = powerCutSeasonFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a PowerCutSeason by their ID.
     *
     * @param id The ID of the PowerCutSeason to retrieve
     * @return ResponseEntity containing the PowerCutSeason data
     */
    @Override
    public ResponseEntity<PowerCutSeasonDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        PowerCutSeasonDTO dto = powerCutSeasonFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a PowerCutSeason by their ID with an optional reason.
     *
     * @param id     The ID of the PowerCutSeason to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        powerCutSeasonFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<PowerCutSeasonDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<PowerCutSeasonDTO> result = powerCutSeasonFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createPowerCutSeasonUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
