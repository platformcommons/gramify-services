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


import com.platformcommons.platform.service.blockprofile.api.ExportPotentialSurplusProduceTyControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.ExportPotentialSurplusProduceTyDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.ExportPotentialSurplusProduceTyFacadeExt;


@RestController
@Slf4j
public class ExportPotentialSurplusProduceTyController implements ExportPotentialSurplusProduceTyControllerApi {

    private final ExportPotentialSurplusProduceTyFacadeExt exportPotentialSurplusProduceTyFacadeExt;

    /**
     * Constructs a ExportPotentialSurplusProduceTyController with the specified facade.
     *
     * @param exportPotentialSurplusProduceTyFacadeExt The ExportPotentialSurplusProduceTy facade extension to be used
     */
    public ExportPotentialSurplusProduceTyController(ExportPotentialSurplusProduceTyFacadeExt exportPotentialSurplusProduceTyFacadeExt) {
        this.exportPotentialSurplusProduceTyFacadeExt =exportPotentialSurplusProduceTyFacadeExt;
    }

    /**
     * Creates a new ExportPotentialSurplusProduceTy.
     *
     * @param exportPotentialSurplusProduceTyDTO The ExportPotentialSurplusProduceTy data to create
     * @return ResponseEntity containing the ID of the created ExportPotentialSurplusProduceTy
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody ExportPotentialSurplusProduceTyDTO exportPotentialSurplusProduceTyDTO) {
        log.debug("Entry - create(ExportPotentialSurplusProduceTyDTO={})" , exportPotentialSurplusProduceTyDTO);
        Long id = exportPotentialSurplusProduceTyFacadeExt.save(exportPotentialSurplusProduceTyDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createExportPotentialSurplusProduceTyUri(id)).body(id);
    }

    /**
     * Updates an existing ExportPotentialSurplusProduceTy.
     *
     * @param exportPotentialSurplusProduceTyDTO The ExportPotentialSurplusProduceTy data to update
     * @return ResponseEntity containing the updated ExportPotentialSurplusProduceTy data
     */
    @Override
    public ResponseEntity<ExportPotentialSurplusProduceTyDTO> update(@RequestBody ExportPotentialSurplusProduceTyDTO exportPotentialSurplusProduceTyDTO) {
        log.debug("Entry - update(ExportPotentialSurplusProduceTyDTO={})", exportPotentialSurplusProduceTyDTO);
        ExportPotentialSurplusProduceTyDTO updated = exportPotentialSurplusProduceTyFacadeExt.update(exportPotentialSurplusProduceTyDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of ExportPotentialSurplusProduceTys.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of ExportPotentialSurplusProduceTys
     */
    @Override
    public ResponseEntity<PageDTO<ExportPotentialSurplusProduceTyDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<ExportPotentialSurplusProduceTyDTO> result = exportPotentialSurplusProduceTyFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a ExportPotentialSurplusProduceTy by their ID.
     *
     * @param id The ID of the ExportPotentialSurplusProduceTy to retrieve
     * @return ResponseEntity containing the ExportPotentialSurplusProduceTy data
     */
    @Override
    public ResponseEntity<ExportPotentialSurplusProduceTyDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        ExportPotentialSurplusProduceTyDTO dto = exportPotentialSurplusProduceTyFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a ExportPotentialSurplusProduceTy by their ID with an optional reason.
     *
     * @param id     The ID of the ExportPotentialSurplusProduceTy to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        exportPotentialSurplusProduceTyFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<ExportPotentialSurplusProduceTyDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<ExportPotentialSurplusProduceTyDTO> result = exportPotentialSurplusProduceTyFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createExportPotentialSurplusProduceTyUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
