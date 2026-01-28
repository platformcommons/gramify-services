package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.MainSurplusDestinationFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.MainSurplusDestinationProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.MainSurplusDestinationServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class MainSurplusDestinationFacadeImpl implements MainSurplusDestinationFacade {

    private final MainSurplusDestinationServiceExt serviceExt;
    private final MainSurplusDestinationProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String MAINSURPLUSDESTINATION_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSURPLUSDESTINATION.CREATE";
    private static final String MAINSURPLUSDESTINATION_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSURPLUSDESTINATION.UPDATED";
    private static final String MAINSURPLUSDESTINATION_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSURPLUSDESTINATION.DELETE";
    private static final String MAINSURPLUSDESTINATION_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSURPLUSDESTINATION.GET";

    public MainSurplusDestinationFacadeImpl(MainSurplusDestinationServiceExt serviceExt, MainSurplusDestinationProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new MainSurplusDestination entry in the system.
     *
     * @param MainSurplusDestinationDTO The MainSurplusDestination information to be saved
     * @return The saved MainSurplusDestination data
     */
    @Override
    public MainSurplusDestinationDTO save(MainSurplusDestinationDTO MainSurplusDestinationDTO) {
        log.debug("Entry - save(MainSurplusDestinationDTO={})", MainSurplusDestinationDTO);
        evaluator.evaluate(MAINSURPLUSDESTINATION_CREATE, new HashMap<>());
        MainSurplusDestinationDTO = preHookSave(MainSurplusDestinationDTO);
        MainSurplusDestinationDTO dto = serviceExt.save(MainSurplusDestinationDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing MainSurplusDestination entry.
     *
     * @param MainSurplusDestinationDTO The MainSurplusDestination information to be updated
     * @return The updated MainSurplusDestination data
     */
    @Override
    public MainSurplusDestinationDTO update(MainSurplusDestinationDTO MainSurplusDestinationDTO) {
        log.debug("Entry - update(MainSurplusDestinationDTO={})", MainSurplusDestinationDTO);
        evaluator.evaluate(MAINSURPLUSDESTINATION_UPDATE, new HashMap<>());
        MainSurplusDestinationDTO = preHookUpdate(MainSurplusDestinationDTO);
        MainSurplusDestinationDTO dto = serviceExt.update(MainSurplusDestinationDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of MainSurplusDestinations.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainSurplusDestinations
     */
    @Override
    public PageDTO<MainSurplusDestinationDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(MAINSURPLUSDESTINATION_GET, new HashMap<>());
        PageDTO<MainSurplusDestinationDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a MainSurplusDestination by their ID with a specified reason.
     *
     * @param id     The ID of the MainSurplusDestination to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(MAINSURPLUSDESTINATION_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        MainSurplusDestinationDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a MainSurplusDestination by their ID.
     *
     * @param id The ID of the MainSurplusDestination to retrieve
     * @return The MainSurplusDestination with the specified ID
     */
    @Override
    public MainSurplusDestinationDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(MAINSURPLUSDESTINATION_GET, new HashMap<>());
        MainSurplusDestinationDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all MainSurplusDestinations by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainSurplusDestinationDTO
     */
    @Override
    public Set<MainSurplusDestinationDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(MAINSURPLUSDESTINATION_GET, new HashMap<>());
        Set<MainSurplusDestinationDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainSurplusDestinationDTO postHookSave(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected MainSurplusDestinationDTO preHookSave(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected MainSurplusDestinationDTO postHookUpdate(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected MainSurplusDestinationDTO preHookUpdate(MainSurplusDestinationDTO MainSurplusDestinationDTO) {
        return MainSurplusDestinationDTO;
    }

    protected MainSurplusDestinationDTO postHookDelete(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainSurplusDestinationDTO postHookGetById(MainSurplusDestinationDTO dto) {
        return dto;
    }

    protected PageDTO<MainSurplusDestinationDTO> postHookGetAll(PageDTO<MainSurplusDestinationDTO> result) {
        return result;
    }
}
