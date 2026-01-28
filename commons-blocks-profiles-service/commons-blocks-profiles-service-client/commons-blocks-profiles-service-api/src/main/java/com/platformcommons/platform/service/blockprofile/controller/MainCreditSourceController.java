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


import com.platformcommons.platform.service.blockprofile.api.MainCreditSourceControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.MainCreditSourceDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.MainCreditSourceFacadeExt;


@RestController
@Slf4j
public class MainCreditSourceController implements MainCreditSourceControllerApi {

    private final MainCreditSourceFacadeExt mainCreditSourceFacadeExt;

    /**
     * Constructs a MainCreditSourceController with the specified facade.
     *
     * @param mainCreditSourceFacadeExt The MainCreditSource facade extension to be used
     */
    public MainCreditSourceController(MainCreditSourceFacadeExt mainCreditSourceFacadeExt) {
        this.mainCreditSourceFacadeExt =mainCreditSourceFacadeExt;
    }

    /**
     * Creates a new MainCreditSource.
     *
     * @param mainCreditSourceDTO The MainCreditSource data to create
     * @return ResponseEntity containing the ID of the created MainCreditSource
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody MainCreditSourceDTO mainCreditSourceDTO) {
        log.debug("Entry - create(MainCreditSourceDTO={})" , mainCreditSourceDTO);
        Long id = mainCreditSourceFacadeExt.save(mainCreditSourceDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createMainCreditSourceUri(id)).body(id);
    }

    /**
     * Updates an existing MainCreditSource.
     *
     * @param mainCreditSourceDTO The MainCreditSource data to update
     * @return ResponseEntity containing the updated MainCreditSource data
     */
    @Override
    public ResponseEntity<MainCreditSourceDTO> update(@RequestBody MainCreditSourceDTO mainCreditSourceDTO) {
        log.debug("Entry - update(MainCreditSourceDTO={})", mainCreditSourceDTO);
        MainCreditSourceDTO updated = mainCreditSourceFacadeExt.update(mainCreditSourceDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of MainCreditSources.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of MainCreditSources
     */
    @Override
    public ResponseEntity<PageDTO<MainCreditSourceDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<MainCreditSourceDTO> result = mainCreditSourceFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a MainCreditSource by their ID.
     *
     * @param id The ID of the MainCreditSource to retrieve
     * @return ResponseEntity containing the MainCreditSource data
     */
    @Override
    public ResponseEntity<MainCreditSourceDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        MainCreditSourceDTO dto = mainCreditSourceFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a MainCreditSource by their ID with an optional reason.
     *
     * @param id     The ID of the MainCreditSource to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        mainCreditSourceFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<MainCreditSourceDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<MainCreditSourceDTO> result = mainCreditSourceFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createMainCreditSourceUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
