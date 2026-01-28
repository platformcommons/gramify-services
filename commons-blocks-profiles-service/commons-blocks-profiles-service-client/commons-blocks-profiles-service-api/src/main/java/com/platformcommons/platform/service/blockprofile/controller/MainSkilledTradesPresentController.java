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


import com.platformcommons.platform.service.blockprofile.api.MainSkilledTradesPresentControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.MainSkilledTradesPresentDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.MainSkilledTradesPresentFacadeExt;


@RestController
@Slf4j
public class MainSkilledTradesPresentController implements MainSkilledTradesPresentControllerApi {

    private final MainSkilledTradesPresentFacadeExt mainSkilledTradesPresentFacadeExt;

    /**
     * Constructs a MainSkilledTradesPresentController with the specified facade.
     *
     * @param mainSkilledTradesPresentFacadeExt The MainSkilledTradesPresent facade extension to be used
     */
    public MainSkilledTradesPresentController(MainSkilledTradesPresentFacadeExt mainSkilledTradesPresentFacadeExt) {
        this.mainSkilledTradesPresentFacadeExt =mainSkilledTradesPresentFacadeExt;
    }

    /**
     * Creates a new MainSkilledTradesPresent.
     *
     * @param mainSkilledTradesPresentDTO The MainSkilledTradesPresent data to create
     * @return ResponseEntity containing the ID of the created MainSkilledTradesPresent
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody MainSkilledTradesPresentDTO mainSkilledTradesPresentDTO) {
        log.debug("Entry - create(MainSkilledTradesPresentDTO={})" , mainSkilledTradesPresentDTO);
        Long id = mainSkilledTradesPresentFacadeExt.save(mainSkilledTradesPresentDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createMainSkilledTradesPresentUri(id)).body(id);
    }

    /**
     * Updates an existing MainSkilledTradesPresent.
     *
     * @param mainSkilledTradesPresentDTO The MainSkilledTradesPresent data to update
     * @return ResponseEntity containing the updated MainSkilledTradesPresent data
     */
    @Override
    public ResponseEntity<MainSkilledTradesPresentDTO> update(@RequestBody MainSkilledTradesPresentDTO mainSkilledTradesPresentDTO) {
        log.debug("Entry - update(MainSkilledTradesPresentDTO={})", mainSkilledTradesPresentDTO);
        MainSkilledTradesPresentDTO updated = mainSkilledTradesPresentFacadeExt.update(mainSkilledTradesPresentDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of MainSkilledTradesPresents.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of MainSkilledTradesPresents
     */
    @Override
    public ResponseEntity<PageDTO<MainSkilledTradesPresentDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<MainSkilledTradesPresentDTO> result = mainSkilledTradesPresentFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a MainSkilledTradesPresent by their ID.
     *
     * @param id The ID of the MainSkilledTradesPresent to retrieve
     * @return ResponseEntity containing the MainSkilledTradesPresent data
     */
    @Override
    public ResponseEntity<MainSkilledTradesPresentDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        MainSkilledTradesPresentDTO dto = mainSkilledTradesPresentFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a MainSkilledTradesPresent by their ID with an optional reason.
     *
     * @param id     The ID of the MainSkilledTradesPresent to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        mainSkilledTradesPresentFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<MainSkilledTradesPresentDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<MainSkilledTradesPresentDTO> result = mainSkilledTradesPresentFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createMainSkilledTradesPresentUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
