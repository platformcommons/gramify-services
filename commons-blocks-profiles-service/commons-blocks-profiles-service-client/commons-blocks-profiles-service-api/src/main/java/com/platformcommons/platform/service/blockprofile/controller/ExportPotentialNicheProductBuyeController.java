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


import com.platformcommons.platform.service.blockprofile.api.ExportPotentialNicheProductBuyeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.ExportPotentialNicheProductBuyeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.ExportPotentialNicheProductBuyeFacadeExt;


@RestController
@Slf4j
public class ExportPotentialNicheProductBuyeController implements ExportPotentialNicheProductBuyeControllerApi {

    private final ExportPotentialNicheProductBuyeFacadeExt exportPotentialNicheProductBuyeFacadeExt;

    /**
     * Constructs a ExportPotentialNicheProductBuyeController with the specified facade.
     *
     * @param exportPotentialNicheProductBuyeFacadeExt The ExportPotentialNicheProductBuye facade extension to be used
     */
    public ExportPotentialNicheProductBuyeController(ExportPotentialNicheProductBuyeFacadeExt exportPotentialNicheProductBuyeFacadeExt) {
        this.exportPotentialNicheProductBuyeFacadeExt =exportPotentialNicheProductBuyeFacadeExt;
    }

    /**
     * Creates a new ExportPotentialNicheProductBuye.
     *
     * @param exportPotentialNicheProductBuyeDTO The ExportPotentialNicheProductBuye data to create
     * @return ResponseEntity containing the ID of the created ExportPotentialNicheProductBuye
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody ExportPotentialNicheProductBuyeDTO exportPotentialNicheProductBuyeDTO) {
        log.debug("Entry - create(ExportPotentialNicheProductBuyeDTO={})" , exportPotentialNicheProductBuyeDTO);
        Long id = exportPotentialNicheProductBuyeFacadeExt.save(exportPotentialNicheProductBuyeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createExportPotentialNicheProductBuyeUri(id)).body(id);
    }

    /**
     * Updates an existing ExportPotentialNicheProductBuye.
     *
     * @param exportPotentialNicheProductBuyeDTO The ExportPotentialNicheProductBuye data to update
     * @return ResponseEntity containing the updated ExportPotentialNicheProductBuye data
     */
    @Override
    public ResponseEntity<ExportPotentialNicheProductBuyeDTO> update(@RequestBody ExportPotentialNicheProductBuyeDTO exportPotentialNicheProductBuyeDTO) {
        log.debug("Entry - update(ExportPotentialNicheProductBuyeDTO={})", exportPotentialNicheProductBuyeDTO);
        ExportPotentialNicheProductBuyeDTO updated = exportPotentialNicheProductBuyeFacadeExt.update(exportPotentialNicheProductBuyeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of ExportPotentialNicheProductBuyes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of ExportPotentialNicheProductBuyes
     */
    @Override
    public ResponseEntity<PageDTO<ExportPotentialNicheProductBuyeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<ExportPotentialNicheProductBuyeDTO> result = exportPotentialNicheProductBuyeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a ExportPotentialNicheProductBuye by their ID.
     *
     * @param id The ID of the ExportPotentialNicheProductBuye to retrieve
     * @return ResponseEntity containing the ExportPotentialNicheProductBuye data
     */
    @Override
    public ResponseEntity<ExportPotentialNicheProductBuyeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        ExportPotentialNicheProductBuyeDTO dto = exportPotentialNicheProductBuyeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a ExportPotentialNicheProductBuye by their ID with an optional reason.
     *
     * @param id     The ID of the ExportPotentialNicheProductBuye to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        exportPotentialNicheProductBuyeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<ExportPotentialNicheProductBuyeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<ExportPotentialNicheProductBuyeDTO> result = exportPotentialNicheProductBuyeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createExportPotentialNicheProductBuyeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
