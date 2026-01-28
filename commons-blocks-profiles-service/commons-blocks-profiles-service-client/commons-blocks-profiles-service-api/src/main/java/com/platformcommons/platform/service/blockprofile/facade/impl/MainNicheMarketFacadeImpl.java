package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.MainNicheMarketFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.MainNicheMarketProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.MainNicheMarketServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class MainNicheMarketFacadeImpl implements MainNicheMarketFacade {

    private final MainNicheMarketServiceExt serviceExt;
    private final MainNicheMarketProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String MAINNICHEMARKET_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINNICHEMARKET.CREATE";
    private static final String MAINNICHEMARKET_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINNICHEMARKET.UPDATED";
    private static final String MAINNICHEMARKET_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINNICHEMARKET.DELETE";
    private static final String MAINNICHEMARKET_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINNICHEMARKET.GET";

    public MainNicheMarketFacadeImpl(MainNicheMarketServiceExt serviceExt, MainNicheMarketProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new MainNicheMarket entry in the system.
     *
     * @param MainNicheMarketDTO The MainNicheMarket information to be saved
     * @return The saved MainNicheMarket data
     */
    @Override
    public MainNicheMarketDTO save(MainNicheMarketDTO MainNicheMarketDTO) {
        log.debug("Entry - save(MainNicheMarketDTO={})", MainNicheMarketDTO);
        evaluator.evaluate(MAINNICHEMARKET_CREATE, new HashMap<>());
        MainNicheMarketDTO = preHookSave(MainNicheMarketDTO);
        MainNicheMarketDTO dto = serviceExt.save(MainNicheMarketDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing MainNicheMarket entry.
     *
     * @param MainNicheMarketDTO The MainNicheMarket information to be updated
     * @return The updated MainNicheMarket data
     */
    @Override
    public MainNicheMarketDTO update(MainNicheMarketDTO MainNicheMarketDTO) {
        log.debug("Entry - update(MainNicheMarketDTO={})", MainNicheMarketDTO);
        evaluator.evaluate(MAINNICHEMARKET_UPDATE, new HashMap<>());
        MainNicheMarketDTO = preHookUpdate(MainNicheMarketDTO);
        MainNicheMarketDTO dto = serviceExt.update(MainNicheMarketDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of MainNicheMarkets.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainNicheMarkets
     */
    @Override
    public PageDTO<MainNicheMarketDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(MAINNICHEMARKET_GET, new HashMap<>());
        PageDTO<MainNicheMarketDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a MainNicheMarket by their ID with a specified reason.
     *
     * @param id     The ID of the MainNicheMarket to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(MAINNICHEMARKET_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        MainNicheMarketDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a MainNicheMarket by their ID.
     *
     * @param id The ID of the MainNicheMarket to retrieve
     * @return The MainNicheMarket with the specified ID
     */
    @Override
    public MainNicheMarketDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(MAINNICHEMARKET_GET, new HashMap<>());
        MainNicheMarketDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all MainNicheMarkets by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainNicheMarketDTO
     */
    @Override
    public Set<MainNicheMarketDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(MAINNICHEMARKET_GET, new HashMap<>());
        Set<MainNicheMarketDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainNicheMarketDTO postHookSave(MainNicheMarketDTO dto) {
        return dto;
    }

    protected MainNicheMarketDTO preHookSave(MainNicheMarketDTO dto) {
        return dto;
    }

    protected MainNicheMarketDTO postHookUpdate(MainNicheMarketDTO dto) {
        return dto;
    }

    protected MainNicheMarketDTO preHookUpdate(MainNicheMarketDTO MainNicheMarketDTO) {
        return MainNicheMarketDTO;
    }

    protected MainNicheMarketDTO postHookDelete(MainNicheMarketDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainNicheMarketDTO postHookGetById(MainNicheMarketDTO dto) {
        return dto;
    }

    protected PageDTO<MainNicheMarketDTO> postHookGetAll(PageDTO<MainNicheMarketDTO> result) {
        return result;
    }
}
