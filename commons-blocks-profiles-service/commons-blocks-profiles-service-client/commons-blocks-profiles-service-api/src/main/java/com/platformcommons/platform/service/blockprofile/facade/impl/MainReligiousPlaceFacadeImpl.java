package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.MainReligiousPlaceFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.MainReligiousPlaceProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.MainReligiousPlaceServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class MainReligiousPlaceFacadeImpl implements MainReligiousPlaceFacade {

    private final MainReligiousPlaceServiceExt serviceExt;
    private final MainReligiousPlaceProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String MAINRELIGIOUSPLACE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINRELIGIOUSPLACE.CREATE";
    private static final String MAINRELIGIOUSPLACE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINRELIGIOUSPLACE.UPDATED";
    private static final String MAINRELIGIOUSPLACE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINRELIGIOUSPLACE.DELETE";
    private static final String MAINRELIGIOUSPLACE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINRELIGIOUSPLACE.GET";

    public MainReligiousPlaceFacadeImpl(MainReligiousPlaceServiceExt serviceExt, MainReligiousPlaceProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new MainReligiousPlace entry in the system.
     *
     * @param MainReligiousPlaceDTO The MainReligiousPlace information to be saved
     * @return The saved MainReligiousPlace data
     */
    @Override
    public MainReligiousPlaceDTO save(MainReligiousPlaceDTO MainReligiousPlaceDTO) {
        log.debug("Entry - save(MainReligiousPlaceDTO={})", MainReligiousPlaceDTO);
        evaluator.evaluate(MAINRELIGIOUSPLACE_CREATE, new HashMap<>());
        MainReligiousPlaceDTO = preHookSave(MainReligiousPlaceDTO);
        MainReligiousPlaceDTO dto = serviceExt.save(MainReligiousPlaceDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing MainReligiousPlace entry.
     *
     * @param MainReligiousPlaceDTO The MainReligiousPlace information to be updated
     * @return The updated MainReligiousPlace data
     */
    @Override
    public MainReligiousPlaceDTO update(MainReligiousPlaceDTO MainReligiousPlaceDTO) {
        log.debug("Entry - update(MainReligiousPlaceDTO={})", MainReligiousPlaceDTO);
        evaluator.evaluate(MAINRELIGIOUSPLACE_UPDATE, new HashMap<>());
        MainReligiousPlaceDTO = preHookUpdate(MainReligiousPlaceDTO);
        MainReligiousPlaceDTO dto = serviceExt.update(MainReligiousPlaceDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of MainReligiousPlaces.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainReligiousPlaces
     */
    @Override
    public PageDTO<MainReligiousPlaceDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(MAINRELIGIOUSPLACE_GET, new HashMap<>());
        PageDTO<MainReligiousPlaceDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a MainReligiousPlace by their ID with a specified reason.
     *
     * @param id     The ID of the MainReligiousPlace to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(MAINRELIGIOUSPLACE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        MainReligiousPlaceDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a MainReligiousPlace by their ID.
     *
     * @param id The ID of the MainReligiousPlace to retrieve
     * @return The MainReligiousPlace with the specified ID
     */
    @Override
    public MainReligiousPlaceDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(MAINRELIGIOUSPLACE_GET, new HashMap<>());
        MainReligiousPlaceDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all MainReligiousPlaces by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainReligiousPlaceDTO
     */
    @Override
    public Set<MainReligiousPlaceDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(MAINRELIGIOUSPLACE_GET, new HashMap<>());
        Set<MainReligiousPlaceDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainReligiousPlaceDTO postHookSave(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected MainReligiousPlaceDTO preHookSave(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected MainReligiousPlaceDTO postHookUpdate(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected MainReligiousPlaceDTO preHookUpdate(MainReligiousPlaceDTO MainReligiousPlaceDTO) {
        return MainReligiousPlaceDTO;
    }

    protected MainReligiousPlaceDTO postHookDelete(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainReligiousPlaceDTO postHookGetById(MainReligiousPlaceDTO dto) {
        return dto;
    }

    protected PageDTO<MainReligiousPlaceDTO> postHookGetAll(PageDTO<MainReligiousPlaceDTO> result) {
        return result;
    }
}
