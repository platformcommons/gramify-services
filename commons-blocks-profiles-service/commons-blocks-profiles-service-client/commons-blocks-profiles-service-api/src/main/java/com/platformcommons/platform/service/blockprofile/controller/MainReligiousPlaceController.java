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


import com.platformcommons.platform.service.blockprofile.api.MainReligiousPlaceControllerApi;
import com.platformcommons.platform.service.blockprofile.dto.MainReligiousPlaceDTO;
import com.platformcommons.platform.service.blockprofile.facade_ext.MainReligiousPlaceFacadeExt;


@RestController
@Slf4j
public class MainReligiousPlaceController implements MainReligiousPlaceControllerApi {

    private final MainReligiousPlaceFacadeExt mainReligiousPlaceFacadeExt;

    /**
     * Constructs a MainReligiousPlaceController with the specified facade.
     *
     * @param mainReligiousPlaceFacadeExt The MainReligiousPlace facade extension to be used
     */
    public MainReligiousPlaceController(MainReligiousPlaceFacadeExt mainReligiousPlaceFacadeExt) {
        this.mainReligiousPlaceFacadeExt =mainReligiousPlaceFacadeExt;
    }

    /**
     * Creates a new MainReligiousPlace.
     *
     * @param mainReligiousPlaceDTO The MainReligiousPlace data to create
     * @return ResponseEntity containing the ID of the created MainReligiousPlace
     */
    @Override
    public ResponseEntity<Long> create(@RequestBody MainReligiousPlaceDTO mainReligiousPlaceDTO) {
        log.debug("Entry - create(MainReligiousPlaceDTO={})" , mainReligiousPlaceDTO);
        Long id = mainReligiousPlaceFacadeExt.save(mainReligiousPlaceDTO).getId();
        log.debug("Exit - create() with result: {}", id);
        return ResponseEntity.created(createMainReligiousPlaceUri(id)).body(id);
    }

    /**
     * Updates an existing MainReligiousPlace.
     *
     * @param mainReligiousPlaceDTO The MainReligiousPlace data to update
     * @return ResponseEntity containing the updated MainReligiousPlace data
     */
    @Override
    public ResponseEntity<MainReligiousPlaceDTO> update(@RequestBody MainReligiousPlaceDTO mainReligiousPlaceDTO) {
        log.debug("Entry - update(MainReligiousPlaceDTO={})", mainReligiousPlaceDTO);
        MainReligiousPlaceDTO updated = mainReligiousPlaceFacadeExt.update(mainReligiousPlaceDTO);
        log.debug("Exit - update() with result: {}", updated);
        return ResponseEntity.ok(updated);
    }

    /**
     * Retrieves a paginated list of MainReligiousPlaces.
     *
     * @param page Zero-based page index (defaults to 0)
     * @param size The size of the page to be returned (defaults to 10)
     * @return ResponseEntity containing a page of MainReligiousPlaces
     */
    @Override
    public ResponseEntity<PageDTO<MainReligiousPlaceDTO>> getAllPage(@RequestParam(defaultValue = "0") Integer page,
                                                          @RequestParam(defaultValue = "10") Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        PageDTO<MainReligiousPlaceDTO> result = mainReligiousPlaceFacadeExt.getAllPage(page, size);
        log.debug("Exit - getAllPage() with result: {}", result);
        return ResponseEntity.ok(result);
    }

    /**
     * Retrieves a MainReligiousPlace by their ID.
     *
     * @param id The ID of the MainReligiousPlace to retrieve
     * @return ResponseEntity containing the MainReligiousPlace data
     */
    @Override
    public ResponseEntity<MainReligiousPlaceDTO> getById(@PathVariable Long id) {
        log.debug("Entry - getById(id={})", id);
        MainReligiousPlaceDTO dto = mainReligiousPlaceFacadeExt.getById(id);
        log.debug("Exit - getById() with result: {}", dto);
        return ResponseEntity.ok(dto);
    }


    /**
     * Deletes a MainReligiousPlace by their ID with an optional reason.
     *
     * @param id     The ID of the MainReligiousPlace to delete
     * @param reason Optional reason for deletion
     * @return ResponseEntity with no content indicating successful deletion
     */
    @Override
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       @RequestParam(required = false) String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        mainReligiousPlaceFacadeExt.delete(id, reason);
        log.debug("Exit - delete()");
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Set<MainReligiousPlaceDTO>> getAllByIds(@RequestBody Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        Set<MainReligiousPlaceDTO> result = mainReligiousPlaceFacadeExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return ResponseEntity.ok(result);
    }
    
    private URI createMainReligiousPlaceUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }


}
