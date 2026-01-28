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


import com.platformcommons.platform.service.blockprofile.api.CommonConsumerGoodsPurchasedControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.CommonConsumerGoodsPurchasedDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.CommonConsumerGoodsPurchasedFacadeExt;


@RestController
@Slf4j
public class CommonConsumerGoodsPurchasedController implements CommonConsumerGoodsPurchasedControllerApi {

    private final CommonConsumerGoodsPurchasedFacadeExt commonConsumerGoodsPurchasedFacadeExt;

    /**
     * Constructs a CommonConsumerGoodsPurchasedController with the specified facade.
     *
     * @param commonConsumerGoodsPurchasedFacadeExt The CommonConsumerGoodsPurchased facade extension to be used
     */
    public CommonConsumerGoodsPurchasedController(CommonConsumerGoodsPurchasedFacadeExt commonConsumerGoodsPurchasedFacadeExt) {
        this.commonConsumerGoodsPurchasedFacadeExt =commonConsumerGoodsPurchasedFacadeExt;
    }

    /**
     * Creates a new CommonConsumerGoodsPurchased.
     *
     * @param commonConsumerGoodsPurchasedDTO The CommonConsumerGoodsPurchased data to create
     * @return ResponseEntity containing the ID of the created CommonConsumerGoodsPurchased
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody CommonConsumerGoodsPurchasedDTO commonConsumerGoodsPurchasedDTO) {
        log.debug("Entry - create(CommonConsumerGoodsPurchasedDTO={})" , commonConsumerGoodsPurchasedDTO);
        Long id = commonConsumerGoodsPurchasedFacadeExt.save(commonConsumerGoodsPurchasedDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createCommonConsumerGoodsPurchasedUri(id)).body(id);
    }

    /**
     * Updates an existing CommonConsumerGoodsPurchased.
     *
     * @param commonConsumerGoodsPurchasedDTO The CommonConsumerGoodsPurchased data to update
     * @return ResponseEntity containing the updated CommonConsumerGoodsPurchased data
     */
    @Override
    public ResponseEntity<CommonConsumerGoodsPurchasedDTO> update(@RequestBody CommonConsumerGoodsPurchasedDTO commonConsumerGoodsPurchasedDTO) {
        log.debug("Entry - update(CommonConsumerGoodsPurchasedDTO={})", commonConsumerGoodsPurchasedDTO);
        CommonConsumerGoodsPurchasedDTO updated = commonConsumerGoodsPurchasedFacadeExt.update(commonConsumerGoodsPurchasedDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of CommonConsumerGoodsPurchaseds.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of CommonConsumerGoodsPurchaseds
     */
    @Override
    public ResponseEntity<PageDTO<CommonConsumerGoodsPurchasedDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<CommonConsumerGoodsPurchasedDTO> result = commonConsumerGoodsPurchasedFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a CommonConsumerGoodsPurchased by their ID.
     *
     * @param id The ID of the CommonConsumerGoodsPurchased to retrieve
     * @return ResponseEntity containing the CommonConsumerGoodsPurchased data
     */
    @Override
    public ResponseEntity<CommonConsumerGoodsPurchasedDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        CommonConsumerGoodsPurchasedDTO dto = commonConsumerGoodsPurchasedFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a CommonConsumerGoodsPurchased by their ID with an optional reason.
     *
     * @param id     The ID of the CommonConsumerGoodsPurchased to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        commonConsumerGoodsPurchasedFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<CommonConsumerGoodsPurchasedDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<CommonConsumerGoodsPurchasedDTO> result = commonConsumerGoodsPurchasedFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createCommonConsumerGoodsPurchasedUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
