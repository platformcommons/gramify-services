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


import com.platformcommons.platform.service.blockprofile.api.ItemsUsuallyBoughtLocallyControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.ItemsUsuallyBoughtLocallyDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.ItemsUsuallyBoughtLocallyFacadeExt;


@RestController
@Slf4j
public class ItemsUsuallyBoughtLocallyController implements ItemsUsuallyBoughtLocallyControllerApi {

    private final ItemsUsuallyBoughtLocallyFacadeExt itemsUsuallyBoughtLocallyFacadeExt;

    /**
     * Constructs a ItemsUsuallyBoughtLocallyController with the specified facade.
     *
     * @param itemsUsuallyBoughtLocallyFacadeExt The ItemsUsuallyBoughtLocally facade extension to be used
     */
    public ItemsUsuallyBoughtLocallyController(ItemsUsuallyBoughtLocallyFacadeExt itemsUsuallyBoughtLocallyFacadeExt) {
        this.itemsUsuallyBoughtLocallyFacadeExt =itemsUsuallyBoughtLocallyFacadeExt;
    }

    /**
     * Creates a new ItemsUsuallyBoughtLocally.
     *
     * @param itemsUsuallyBoughtLocallyDTO The ItemsUsuallyBoughtLocally data to create
     * @return ResponseEntity containing the ID of the created ItemsUsuallyBoughtLocally
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody ItemsUsuallyBoughtLocallyDTO itemsUsuallyBoughtLocallyDTO) {
        log.debug("Entry - create(ItemsUsuallyBoughtLocallyDTO={})" , itemsUsuallyBoughtLocallyDTO);
        Long id = itemsUsuallyBoughtLocallyFacadeExt.save(itemsUsuallyBoughtLocallyDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createItemsUsuallyBoughtLocallyUri(id)).body(id);
    }

    /**
     * Updates an existing ItemsUsuallyBoughtLocally.
     *
     * @param itemsUsuallyBoughtLocallyDTO The ItemsUsuallyBoughtLocally data to update
     * @return ResponseEntity containing the updated ItemsUsuallyBoughtLocally data
     */
    @Override
    public ResponseEntity<ItemsUsuallyBoughtLocallyDTO> update(@RequestBody ItemsUsuallyBoughtLocallyDTO itemsUsuallyBoughtLocallyDTO) {
        log.debug("Entry - update(ItemsUsuallyBoughtLocallyDTO={})", itemsUsuallyBoughtLocallyDTO);
        ItemsUsuallyBoughtLocallyDTO updated = itemsUsuallyBoughtLocallyFacadeExt.update(itemsUsuallyBoughtLocallyDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of ItemsUsuallyBoughtLocallys.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of ItemsUsuallyBoughtLocallys
     */
    @Override
    public ResponseEntity<PageDTO<ItemsUsuallyBoughtLocallyDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<ItemsUsuallyBoughtLocallyDTO> result = itemsUsuallyBoughtLocallyFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a ItemsUsuallyBoughtLocally by their ID.
     *
     * @param id The ID of the ItemsUsuallyBoughtLocally to retrieve
     * @return ResponseEntity containing the ItemsUsuallyBoughtLocally data
     */
    @Override
    public ResponseEntity<ItemsUsuallyBoughtLocallyDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        ItemsUsuallyBoughtLocallyDTO dto = itemsUsuallyBoughtLocallyFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a ItemsUsuallyBoughtLocally by their ID with an optional reason.
     *
     * @param id     The ID of the ItemsUsuallyBoughtLocally to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        itemsUsuallyBoughtLocallyFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<ItemsUsuallyBoughtLocallyDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<ItemsUsuallyBoughtLocallyDTO> result = itemsUsuallyBoughtLocallyFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createItemsUsuallyBoughtLocallyUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
