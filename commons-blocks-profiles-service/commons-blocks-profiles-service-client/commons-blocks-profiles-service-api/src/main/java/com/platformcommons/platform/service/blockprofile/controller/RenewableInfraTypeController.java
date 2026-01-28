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


import com.platformcommons.platform.service.blockprofile.api.RenewableInfraTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.RenewableInfraTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.RenewableInfraTypeFacadeExt;


@RestController
@Slf4j
public class RenewableInfraTypeController implements RenewableInfraTypeControllerApi {

    private final RenewableInfraTypeFacadeExt renewableInfraTypeFacadeExt;

    /**
     * Constructs a RenewableInfraTypeController with the specified facade.
     *
     * @param renewableInfraTypeFacadeExt The RenewableInfraType facade extension to be used
     */
    public RenewableInfraTypeController(RenewableInfraTypeFacadeExt renewableInfraTypeFacadeExt) {
        this.renewableInfraTypeFacadeExt =renewableInfraTypeFacadeExt;
    }

    /**
     * Creates a new RenewableInfraType.
     *
     * @param renewableInfraTypeDTO The RenewableInfraType data to create
     * @return ResponseEntity containing the ID of the created RenewableInfraType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody RenewableInfraTypeDTO renewableInfraTypeDTO) {
        log.debug("Entry - create(RenewableInfraTypeDTO={})" , renewableInfraTypeDTO);
        Long id = renewableInfraTypeFacadeExt.save(renewableInfraTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createRenewableInfraTypeUri(id)).body(id);
    }

    /**
     * Updates an existing RenewableInfraType.
     *
     * @param renewableInfraTypeDTO The RenewableInfraType data to update
     * @return ResponseEntity containing the updated RenewableInfraType data
     */
    @Override
    public ResponseEntity<RenewableInfraTypeDTO> update(@RequestBody RenewableInfraTypeDTO renewableInfraTypeDTO) {
        log.debug("Entry - update(RenewableInfraTypeDTO={})", renewableInfraTypeDTO);
        RenewableInfraTypeDTO updated = renewableInfraTypeFacadeExt.update(renewableInfraTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of RenewableInfraTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of RenewableInfraTypes
     */
    @Override
    public ResponseEntity<PageDTO<RenewableInfraTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<RenewableInfraTypeDTO> result = renewableInfraTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a RenewableInfraType by their ID.
     *
     * @param id The ID of the RenewableInfraType to retrieve
     * @return ResponseEntity containing the RenewableInfraType data
     */
    @Override
    public ResponseEntity<RenewableInfraTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        RenewableInfraTypeDTO dto = renewableInfraTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a RenewableInfraType by their ID with an optional reason.
     *
     * @param id     The ID of the RenewableInfraType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        renewableInfraTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<RenewableInfraTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<RenewableInfraTypeDTO> result = renewableInfraTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createRenewableInfraTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
