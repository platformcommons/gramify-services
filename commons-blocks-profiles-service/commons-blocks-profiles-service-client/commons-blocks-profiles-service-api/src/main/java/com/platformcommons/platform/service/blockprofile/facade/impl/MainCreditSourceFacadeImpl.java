package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.MainCreditSourceFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.MainCreditSourceProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.MainCreditSourceServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class MainCreditSourceFacadeImpl implements MainCreditSourceFacade {

    private final MainCreditSourceServiceExt serviceExt;
    private final MainCreditSourceProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String MAINCREDITSOURCE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINCREDITSOURCE.CREATE";
    private static final String MAINCREDITSOURCE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINCREDITSOURCE.UPDATED";
    private static final String MAINCREDITSOURCE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINCREDITSOURCE.DELETE";
    private static final String MAINCREDITSOURCE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINCREDITSOURCE.GET";

    public MainCreditSourceFacadeImpl(MainCreditSourceServiceExt serviceExt, MainCreditSourceProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new MainCreditSource entry in the system.
     *
     * @param MainCreditSourceDTO The MainCreditSource information to be saved
     * @return The saved MainCreditSource data
     */
    @Override
    public MainCreditSourceDTO save(MainCreditSourceDTO MainCreditSourceDTO) {
        log.debug("Entry - save(MainCreditSourceDTO={})", MainCreditSourceDTO);
        evaluator.evaluate(MAINCREDITSOURCE_CREATE, new HashMap<>());
        MainCreditSourceDTO = preHookSave(MainCreditSourceDTO);
        MainCreditSourceDTO dto = serviceExt.save(MainCreditSourceDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing MainCreditSource entry.
     *
     * @param MainCreditSourceDTO The MainCreditSource information to be updated
     * @return The updated MainCreditSource data
     */
    @Override
    public MainCreditSourceDTO update(MainCreditSourceDTO MainCreditSourceDTO) {
        log.debug("Entry - update(MainCreditSourceDTO={})", MainCreditSourceDTO);
        evaluator.evaluate(MAINCREDITSOURCE_UPDATE, new HashMap<>());
        MainCreditSourceDTO = preHookUpdate(MainCreditSourceDTO);
        MainCreditSourceDTO dto = serviceExt.update(MainCreditSourceDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of MainCreditSources.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainCreditSources
     */
    @Override
    public PageDTO<MainCreditSourceDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(MAINCREDITSOURCE_GET, new HashMap<>());
        PageDTO<MainCreditSourceDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a MainCreditSource by their ID with a specified reason.
     *
     * @param id     The ID of the MainCreditSource to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(MAINCREDITSOURCE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        MainCreditSourceDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a MainCreditSource by their ID.
     *
     * @param id The ID of the MainCreditSource to retrieve
     * @return The MainCreditSource with the specified ID
     */
    @Override
    public MainCreditSourceDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(MAINCREDITSOURCE_GET, new HashMap<>());
        MainCreditSourceDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all MainCreditSources by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainCreditSourceDTO
     */
    @Override
    public Set<MainCreditSourceDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(MAINCREDITSOURCE_GET, new HashMap<>());
        Set<MainCreditSourceDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainCreditSourceDTO postHookSave(MainCreditSourceDTO dto) {
        return dto;
    }

    protected MainCreditSourceDTO preHookSave(MainCreditSourceDTO dto) {
        return dto;
    }

    protected MainCreditSourceDTO postHookUpdate(MainCreditSourceDTO dto) {
        return dto;
    }

    protected MainCreditSourceDTO preHookUpdate(MainCreditSourceDTO MainCreditSourceDTO) {
        return MainCreditSourceDTO;
    }

    protected MainCreditSourceDTO postHookDelete(MainCreditSourceDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainCreditSourceDTO postHookGetById(MainCreditSourceDTO dto) {
        return dto;
    }

    protected PageDTO<MainCreditSourceDTO> postHookGetAll(PageDTO<MainCreditSourceDTO> result) {
        return result;
    }
}
