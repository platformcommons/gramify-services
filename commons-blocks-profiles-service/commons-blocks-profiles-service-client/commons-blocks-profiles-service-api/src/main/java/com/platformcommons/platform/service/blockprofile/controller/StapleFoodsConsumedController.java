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


import com.platformcommons.platform.service.blockprofile.api.StapleFoodsConsumedControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.StapleFoodsConsumedDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.StapleFoodsConsumedFacadeExt;


@RestController
@Slf4j
public class StapleFoodsConsumedController implements StapleFoodsConsumedControllerApi {

    private final StapleFoodsConsumedFacadeExt stapleFoodsConsumedFacadeExt;

    /**
     * Constructs a StapleFoodsConsumedController with the specified facade.
     *
     * @param stapleFoodsConsumedFacadeExt The StapleFoodsConsumed facade extension to be used
     */
    public StapleFoodsConsumedController(StapleFoodsConsumedFacadeExt stapleFoodsConsumedFacadeExt) {
        this.stapleFoodsConsumedFacadeExt =stapleFoodsConsumedFacadeExt;
    }

    /**
     * Creates a new StapleFoodsConsumed.
     *
     * @param stapleFoodsConsumedDTO The StapleFoodsConsumed data to create
     * @return ResponseEntity containing the ID of the created StapleFoodsConsumed
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody StapleFoodsConsumedDTO stapleFoodsConsumedDTO) {
        log.debug("Entry - create(StapleFoodsConsumedDTO={})" , stapleFoodsConsumedDTO);
        Long id = stapleFoodsConsumedFacadeExt.save(stapleFoodsConsumedDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createStapleFoodsConsumedUri(id)).body(id);
    }

    /**
     * Updates an existing StapleFoodsConsumed.
     *
     * @param stapleFoodsConsumedDTO The StapleFoodsConsumed data to update
     * @return ResponseEntity containing the updated StapleFoodsConsumed data
     */
    @Override
    public ResponseEntity<StapleFoodsConsumedDTO> update(@RequestBody StapleFoodsConsumedDTO stapleFoodsConsumedDTO) {
        log.debug("Entry - update(StapleFoodsConsumedDTO={})", stapleFoodsConsumedDTO);
        StapleFoodsConsumedDTO updated = stapleFoodsConsumedFacadeExt.update(stapleFoodsConsumedDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of StapleFoodsConsumeds.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of StapleFoodsConsumeds
     */
    @Override
    public ResponseEntity<PageDTO<StapleFoodsConsumedDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<StapleFoodsConsumedDTO> result = stapleFoodsConsumedFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a StapleFoodsConsumed by their ID.
     *
     * @param id The ID of the StapleFoodsConsumed to retrieve
     * @return ResponseEntity containing the StapleFoodsConsumed data
     */
    @Override
    public ResponseEntity<StapleFoodsConsumedDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        StapleFoodsConsumedDTO dto = stapleFoodsConsumedFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a StapleFoodsConsumed by their ID with an optional reason.
     *
     * @param id     The ID of the StapleFoodsConsumed to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        stapleFoodsConsumedFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<StapleFoodsConsumedDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<StapleFoodsConsumedDTO> result = stapleFoodsConsumedFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createStapleFoodsConsumedUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
