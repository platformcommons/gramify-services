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


import com.platformcommons.platform.service.blockprofile.api.VillageSoilTypeControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageSoilTypeDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageSoilTypeFacadeExt;


@RestController
@Slf4j
public class VillageSoilTypeController implements VillageSoilTypeControllerApi {

    private final VillageSoilTypeFacadeExt villageSoilTypeFacadeExt;

    /**
     * Constructs a VillageSoilTypeController with the specified facade.
     *
     * @param villageSoilTypeFacadeExt The VillageSoilType facade extension to be used
     */
    public VillageSoilTypeController(VillageSoilTypeFacadeExt villageSoilTypeFacadeExt) {
        this.villageSoilTypeFacadeExt =villageSoilTypeFacadeExt;
    }

    /**
     * Creates a new VillageSoilType.
     *
     * @param villageSoilTypeDTO The VillageSoilType data to create
     * @return ResponseEntity containing the ID of the created VillageSoilType
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageSoilTypeDTO villageSoilTypeDTO) {
        log.debug("Entry - create(VillageSoilTypeDTO={})" , villageSoilTypeDTO);
        Long id = villageSoilTypeFacadeExt.save(villageSoilTypeDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageSoilTypeUri(id)).body(id);
    }

    /**
     * Updates an existing VillageSoilType.
     *
     * @param villageSoilTypeDTO The VillageSoilType data to update
     * @return ResponseEntity containing the updated VillageSoilType data
     */
    @Override
    public ResponseEntity<VillageSoilTypeDTO> update(@RequestBody VillageSoilTypeDTO villageSoilTypeDTO) {
        log.debug("Entry - update(VillageSoilTypeDTO={})", villageSoilTypeDTO);
        VillageSoilTypeDTO updated = villageSoilTypeFacadeExt.update(villageSoilTypeDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageSoilTypes.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageSoilTypes
     */
    @Override
    public ResponseEntity<PageDTO<VillageSoilTypeDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageSoilTypeDTO> result = villageSoilTypeFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageSoilType by their ID.
     *
     * @param id The ID of the VillageSoilType to retrieve
     * @return ResponseEntity containing the VillageSoilType data
     */
    @Override
    public ResponseEntity<VillageSoilTypeDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageSoilTypeDTO dto = villageSoilTypeFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageSoilType by their ID with an optional reason.
     *
     * @param id     The ID of the VillageSoilType to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageSoilTypeFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageSoilTypeDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageSoilTypeDTO> result = villageSoilTypeFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageSoilTypeUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
