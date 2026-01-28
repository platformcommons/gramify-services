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


import com.platformcommons.platform.service.blockprofile.api.SeasonalityOfSurplusControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.SeasonalityOfSurplusDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.SeasonalityOfSurplusFacadeExt;


@RestController
@Slf4j
public class SeasonalityOfSurplusController implements SeasonalityOfSurplusControllerApi {

    private final SeasonalityOfSurplusFacadeExt seasonalityOfSurplusFacadeExt;

    /**
     * Constructs a SeasonalityOfSurplusController with the specified facade.
     *
     * @param seasonalityOfSurplusFacadeExt The SeasonalityOfSurplus facade extension to be used
     */
    public SeasonalityOfSurplusController(SeasonalityOfSurplusFacadeExt seasonalityOfSurplusFacadeExt) {
        this.seasonalityOfSurplusFacadeExt =seasonalityOfSurplusFacadeExt;
    }

    /**
     * Creates a new SeasonalityOfSurplus.
     *
     * @param seasonalityOfSurplusDTO The SeasonalityOfSurplus data to create
     * @return ResponseEntity containing the ID of the created SeasonalityOfSurplus
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody SeasonalityOfSurplusDTO seasonalityOfSurplusDTO) {
        log.debug("Entry - create(SeasonalityOfSurplusDTO={})" , seasonalityOfSurplusDTO);
        Long id = seasonalityOfSurplusFacadeExt.save(seasonalityOfSurplusDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createSeasonalityOfSurplusUri(id)).body(id);
    }

    /**
     * Updates an existing SeasonalityOfSurplus.
     *
     * @param seasonalityOfSurplusDTO The SeasonalityOfSurplus data to update
     * @return ResponseEntity containing the updated SeasonalityOfSurplus data
     */
    @Override
    public ResponseEntity<SeasonalityOfSurplusDTO> update(@RequestBody SeasonalityOfSurplusDTO seasonalityOfSurplusDTO) {
        log.debug("Entry - update(SeasonalityOfSurplusDTO={})", seasonalityOfSurplusDTO);
        SeasonalityOfSurplusDTO updated = seasonalityOfSurplusFacadeExt.update(seasonalityOfSurplusDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of SeasonalityOfSurpluss.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of SeasonalityOfSurpluss
     */
    @Override
    public ResponseEntity<PageDTO<SeasonalityOfSurplusDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<SeasonalityOfSurplusDTO> result = seasonalityOfSurplusFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a SeasonalityOfSurplus by their ID.
     *
     * @param id The ID of the SeasonalityOfSurplus to retrieve
     * @return ResponseEntity containing the SeasonalityOfSurplus data
     */
    @Override
    public ResponseEntity<SeasonalityOfSurplusDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        SeasonalityOfSurplusDTO dto = seasonalityOfSurplusFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a SeasonalityOfSurplus by their ID with an optional reason.
     *
     * @param id     The ID of the SeasonalityOfSurplus to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        seasonalityOfSurplusFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<SeasonalityOfSurplusDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<SeasonalityOfSurplusDTO> result = seasonalityOfSurplusFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createSeasonalityOfSurplusUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
