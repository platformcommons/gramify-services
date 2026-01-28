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


import com.platformcommons.platform.service.blockprofile.api.NicheProductsAvailabilityControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.NicheProductsAvailabilityDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.NicheProductsAvailabilityFacadeExt;


@RestController
@Slf4j
public class NicheProductsAvailabilityController implements NicheProductsAvailabilityControllerApi {

    private final NicheProductsAvailabilityFacadeExt nicheProductsAvailabilityFacadeExt;

    /**
     * Constructs a NicheProductsAvailabilityController with the specified facade.
     *
     * @param nicheProductsAvailabilityFacadeExt The NicheProductsAvailability facade extension to be used
     */
    public NicheProductsAvailabilityController(NicheProductsAvailabilityFacadeExt nicheProductsAvailabilityFacadeExt) {
        this.nicheProductsAvailabilityFacadeExt =nicheProductsAvailabilityFacadeExt;
    }

    /**
     * Creates a new NicheProductsAvailability.
     *
     * @param nicheProductsAvailabilityDTO The NicheProductsAvailability data to create
     * @return ResponseEntity containing the ID of the created NicheProductsAvailability
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody NicheProductsAvailabilityDTO nicheProductsAvailabilityDTO) {
        log.debug("Entry - create(NicheProductsAvailabilityDTO={})" , nicheProductsAvailabilityDTO);
        Long id = nicheProductsAvailabilityFacadeExt.save(nicheProductsAvailabilityDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createNicheProductsAvailabilityUri(id)).body(id);
    }

    /**
     * Updates an existing NicheProductsAvailability.
     *
     * @param nicheProductsAvailabilityDTO The NicheProductsAvailability data to update
     * @return ResponseEntity containing the updated NicheProductsAvailability data
     */
    @Override
    public ResponseEntity<NicheProductsAvailabilityDTO> update(@RequestBody NicheProductsAvailabilityDTO nicheProductsAvailabilityDTO) {
        log.debug("Entry - update(NicheProductsAvailabilityDTO={})", nicheProductsAvailabilityDTO);
        NicheProductsAvailabilityDTO updated = nicheProductsAvailabilityFacadeExt.update(nicheProductsAvailabilityDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of NicheProductsAvailabilitys.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of NicheProductsAvailabilitys
     */
    @Override
    public ResponseEntity<PageDTO<NicheProductsAvailabilityDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<NicheProductsAvailabilityDTO> result = nicheProductsAvailabilityFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a NicheProductsAvailability by their ID.
     *
     * @param id The ID of the NicheProductsAvailability to retrieve
     * @return ResponseEntity containing the NicheProductsAvailability data
     */
    @Override
    public ResponseEntity<NicheProductsAvailabilityDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        NicheProductsAvailabilityDTO dto = nicheProductsAvailabilityFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a NicheProductsAvailability by their ID with an optional reason.
     *
     * @param id     The ID of the NicheProductsAvailability to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        nicheProductsAvailabilityFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<NicheProductsAvailabilityDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<NicheProductsAvailabilityDTO> result = nicheProductsAvailabilityFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createNicheProductsAvailabilityUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
