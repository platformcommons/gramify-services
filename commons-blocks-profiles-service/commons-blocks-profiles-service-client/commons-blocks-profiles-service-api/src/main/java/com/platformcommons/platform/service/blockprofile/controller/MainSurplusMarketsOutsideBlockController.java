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


import com.platformcommons.platform.service.blockprofile.api.MainSurplusMarketsOutsideBlockControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.MainSurplusMarketsOutsideBlockDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.MainSurplusMarketsOutsideBlockFacadeExt;


@RestController
@Slf4j
public class MainSurplusMarketsOutsideBlockController implements MainSurplusMarketsOutsideBlockControllerApi {

    private final MainSurplusMarketsOutsideBlockFacadeExt mainSurplusMarketsOutsideBlockFacadeExt;

    /**
     * Constructs a MainSurplusMarketsOutsideBlockController with the specified facade.
     *
     * @param mainSurplusMarketsOutsideBlockFacadeExt The MainSurplusMarketsOutsideBlock facade extension to be used
     */
    public MainSurplusMarketsOutsideBlockController(MainSurplusMarketsOutsideBlockFacadeExt mainSurplusMarketsOutsideBlockFacadeExt) {
        this.mainSurplusMarketsOutsideBlockFacadeExt =mainSurplusMarketsOutsideBlockFacadeExt;
    }

    /**
     * Creates a new MainSurplusMarketsOutsideBlock.
     *
     * @param mainSurplusMarketsOutsideBlockDTO The MainSurplusMarketsOutsideBlock data to create
     * @return ResponseEntity containing the ID of the created MainSurplusMarketsOutsideBlock
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody MainSurplusMarketsOutsideBlockDTO mainSurplusMarketsOutsideBlockDTO) {
        log.debug("Entry - create(MainSurplusMarketsOutsideBlockDTO={})" , mainSurplusMarketsOutsideBlockDTO);
        Long id = mainSurplusMarketsOutsideBlockFacadeExt.save(mainSurplusMarketsOutsideBlockDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createMainSurplusMarketsOutsideBlockUri(id)).body(id);
    }

    /**
     * Updates an existing MainSurplusMarketsOutsideBlock.
     *
     * @param mainSurplusMarketsOutsideBlockDTO The MainSurplusMarketsOutsideBlock data to update
     * @return ResponseEntity containing the updated MainSurplusMarketsOutsideBlock data
     */
    @Override
    public ResponseEntity<MainSurplusMarketsOutsideBlockDTO> update(@RequestBody MainSurplusMarketsOutsideBlockDTO mainSurplusMarketsOutsideBlockDTO) {
        log.debug("Entry - update(MainSurplusMarketsOutsideBlockDTO={})", mainSurplusMarketsOutsideBlockDTO);
        MainSurplusMarketsOutsideBlockDTO updated = mainSurplusMarketsOutsideBlockFacadeExt.update(mainSurplusMarketsOutsideBlockDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of MainSurplusMarketsOutsideBlocks.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of MainSurplusMarketsOutsideBlocks
     */
    @Override
    public ResponseEntity<PageDTO<MainSurplusMarketsOutsideBlockDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<MainSurplusMarketsOutsideBlockDTO> result = mainSurplusMarketsOutsideBlockFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a MainSurplusMarketsOutsideBlock by their ID.
     *
     * @param id The ID of the MainSurplusMarketsOutsideBlock to retrieve
     * @return ResponseEntity containing the MainSurplusMarketsOutsideBlock data
     */
    @Override
    public ResponseEntity<MainSurplusMarketsOutsideBlockDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        MainSurplusMarketsOutsideBlockDTO dto = mainSurplusMarketsOutsideBlockFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a MainSurplusMarketsOutsideBlock by their ID with an optional reason.
     *
     * @param id     The ID of the MainSurplusMarketsOutsideBlock to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        mainSurplusMarketsOutsideBlockFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<MainSurplusMarketsOutsideBlockDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<MainSurplusMarketsOutsideBlockDTO> result = mainSurplusMarketsOutsideBlockFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createMainSurplusMarketsOutsideBlockUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
