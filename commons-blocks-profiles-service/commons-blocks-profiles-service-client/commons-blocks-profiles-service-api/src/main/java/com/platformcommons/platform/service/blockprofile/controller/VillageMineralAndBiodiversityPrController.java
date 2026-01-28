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


import com.platformcommons.platform.service.blockprofile.api.VillageMineralAndBiodiversityPrControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.VillageMineralAndBiodiversityPrDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.VillageMineralAndBiodiversityPrFacadeExt;


@RestController
@Slf4j
public class VillageMineralAndBiodiversityPrController implements VillageMineralAndBiodiversityPrControllerApi {

    private final VillageMineralAndBiodiversityPrFacadeExt villageMineralAndBiodiversityPrFacadeExt;

    /**
     * Constructs a VillageMineralAndBiodiversityPrController with the specified facade.
     *
     * @param villageMineralAndBiodiversityPrFacadeExt The VillageMineralAndBiodiversityPr facade extension to be used
     */
    public VillageMineralAndBiodiversityPrController(VillageMineralAndBiodiversityPrFacadeExt villageMineralAndBiodiversityPrFacadeExt) {
        this.villageMineralAndBiodiversityPrFacadeExt =villageMineralAndBiodiversityPrFacadeExt;
    }

    /**
     * Creates a new VillageMineralAndBiodiversityPr.
     *
     * @param villageMineralAndBiodiversityPrDTO The VillageMineralAndBiodiversityPr data to create
     * @return ResponseEntity containing the ID of the created VillageMineralAndBiodiversityPr
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody VillageMineralAndBiodiversityPrDTO villageMineralAndBiodiversityPrDTO) {
        log.debug("Entry - create(VillageMineralAndBiodiversityPrDTO={})" , villageMineralAndBiodiversityPrDTO);
        Long id = villageMineralAndBiodiversityPrFacadeExt.save(villageMineralAndBiodiversityPrDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createVillageMineralAndBiodiversityPrUri(id)).body(id);
    }

    /**
     * Updates an existing VillageMineralAndBiodiversityPr.
     *
     * @param villageMineralAndBiodiversityPrDTO The VillageMineralAndBiodiversityPr data to update
     * @return ResponseEntity containing the updated VillageMineralAndBiodiversityPr data
     */
    @Override
    public ResponseEntity<VillageMineralAndBiodiversityPrDTO> update(@RequestBody VillageMineralAndBiodiversityPrDTO villageMineralAndBiodiversityPrDTO) {
        log.debug("Entry - update(VillageMineralAndBiodiversityPrDTO={})", villageMineralAndBiodiversityPrDTO);
        VillageMineralAndBiodiversityPrDTO updated = villageMineralAndBiodiversityPrFacadeExt.update(villageMineralAndBiodiversityPrDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of VillageMineralAndBiodiversityPrs.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of VillageMineralAndBiodiversityPrs
     */
    @Override
    public ResponseEntity<PageDTO<VillageMineralAndBiodiversityPrDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<VillageMineralAndBiodiversityPrDTO> result = villageMineralAndBiodiversityPrFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a VillageMineralAndBiodiversityPr by their ID.
     *
     * @param id The ID of the VillageMineralAndBiodiversityPr to retrieve
     * @return ResponseEntity containing the VillageMineralAndBiodiversityPr data
     */
    @Override
    public ResponseEntity<VillageMineralAndBiodiversityPrDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageMineralAndBiodiversityPrDTO dto = villageMineralAndBiodiversityPrFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a VillageMineralAndBiodiversityPr by their ID with an optional reason.
     *
     * @param id     The ID of the VillageMineralAndBiodiversityPr to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        villageMineralAndBiodiversityPrFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<VillageMineralAndBiodiversityPrDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<VillageMineralAndBiodiversityPrDTO> result = villageMineralAndBiodiversityPrFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createVillageMineralAndBiodiversityPrUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
