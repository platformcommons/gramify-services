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


import com.platformcommons.platform.service.blockprofile.api.WhereFarmersBuyInputControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.WhereFarmersBuyInputDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.WhereFarmersBuyInputFacadeExt;


@RestController
@Slf4j
public class WhereFarmersBuyInputController implements WhereFarmersBuyInputControllerApi {

    private final WhereFarmersBuyInputFacadeExt whereFarmersBuyInputFacadeExt;

    /**
     * Constructs a WhereFarmersBuyInputController with the specified facade.
     *
     * @param whereFarmersBuyInputFacadeExt The WhereFarmersBuyInput facade extension to be used
     */
    public WhereFarmersBuyInputController(WhereFarmersBuyInputFacadeExt whereFarmersBuyInputFacadeExt) {
        this.whereFarmersBuyInputFacadeExt =whereFarmersBuyInputFacadeExt;
    }

    /**
     * Creates a new WhereFarmersBuyInput.
     *
     * @param whereFarmersBuyInputDTO The WhereFarmersBuyInput data to create
     * @return ResponseEntity containing the ID of the created WhereFarmersBuyInput
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody WhereFarmersBuyInputDTO whereFarmersBuyInputDTO) {
        log.debug("Entry - create(WhereFarmersBuyInputDTO={})" , whereFarmersBuyInputDTO);
        Long id = whereFarmersBuyInputFacadeExt.save(whereFarmersBuyInputDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createWhereFarmersBuyInputUri(id)).body(id);
    }

    /**
     * Updates an existing WhereFarmersBuyInput.
     *
     * @param whereFarmersBuyInputDTO The WhereFarmersBuyInput data to update
     * @return ResponseEntity containing the updated WhereFarmersBuyInput data
     */
    @Override
    public ResponseEntity<WhereFarmersBuyInputDTO> update(@RequestBody WhereFarmersBuyInputDTO whereFarmersBuyInputDTO) {
        log.debug("Entry - update(WhereFarmersBuyInputDTO={})", whereFarmersBuyInputDTO);
        WhereFarmersBuyInputDTO updated = whereFarmersBuyInputFacadeExt.update(whereFarmersBuyInputDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of WhereFarmersBuyInputs.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of WhereFarmersBuyInputs
     */
    @Override
    public ResponseEntity<PageDTO<WhereFarmersBuyInputDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<WhereFarmersBuyInputDTO> result = whereFarmersBuyInputFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a WhereFarmersBuyInput by their ID.
     *
     * @param id The ID of the WhereFarmersBuyInput to retrieve
     * @return ResponseEntity containing the WhereFarmersBuyInput data
     */
    @Override
    public ResponseEntity<WhereFarmersBuyInputDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        WhereFarmersBuyInputDTO dto = whereFarmersBuyInputFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a WhereFarmersBuyInput by their ID with an optional reason.
     *
     * @param id     The ID of the WhereFarmersBuyInput to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        whereFarmersBuyInputFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<WhereFarmersBuyInputDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<WhereFarmersBuyInputDTO> result = whereFarmersBuyInputFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createWhereFarmersBuyInputUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
