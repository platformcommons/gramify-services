package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.MainSurplusMarketsOutsideBlockFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.MainSurplusMarketsOutsideBlockProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.MainSurplusMarketsOutsideBlockServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class MainSurplusMarketsOutsideBlockFacadeImpl implements MainSurplusMarketsOutsideBlockFacade {

    private final MainSurplusMarketsOutsideBlockServiceExt serviceExt;
    private final MainSurplusMarketsOutsideBlockProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String MAINSURPLUSMARKETSOUTSIDEBLOCK_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSURPLUSMARKETSOUTSIDEBLOCK.CREATE";
    private static final String MAINSURPLUSMARKETSOUTSIDEBLOCK_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSURPLUSMARKETSOUTSIDEBLOCK.UPDATED";
    private static final String MAINSURPLUSMARKETSOUTSIDEBLOCK_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSURPLUSMARKETSOUTSIDEBLOCK.DELETE";
    private static final String MAINSURPLUSMARKETSOUTSIDEBLOCK_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSURPLUSMARKETSOUTSIDEBLOCK.GET";

    public MainSurplusMarketsOutsideBlockFacadeImpl(MainSurplusMarketsOutsideBlockServiceExt serviceExt, MainSurplusMarketsOutsideBlockProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new MainSurplusMarketsOutsideBlock entry in the system.
     *
     * @param MainSurplusMarketsOutsideBlockDTO The MainSurplusMarketsOutsideBlock information to be saved
     * @return The saved MainSurplusMarketsOutsideBlock data
     */
    @Override
    public MainSurplusMarketsOutsideBlockDTO save(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlockDTO) {
        log.debug("Entry - save(MainSurplusMarketsOutsideBlockDTO={})", MainSurplusMarketsOutsideBlockDTO);
        evaluator.evaluate(MAINSURPLUSMARKETSOUTSIDEBLOCK_CREATE, new HashMap<>());
        MainSurplusMarketsOutsideBlockDTO = preHookSave(MainSurplusMarketsOutsideBlockDTO);
        MainSurplusMarketsOutsideBlockDTO dto = serviceExt.save(MainSurplusMarketsOutsideBlockDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing MainSurplusMarketsOutsideBlock entry.
     *
     * @param MainSurplusMarketsOutsideBlockDTO The MainSurplusMarketsOutsideBlock information to be updated
     * @return The updated MainSurplusMarketsOutsideBlock data
     */
    @Override
    public MainSurplusMarketsOutsideBlockDTO update(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlockDTO) {
        log.debug("Entry - update(MainSurplusMarketsOutsideBlockDTO={})", MainSurplusMarketsOutsideBlockDTO);
        evaluator.evaluate(MAINSURPLUSMARKETSOUTSIDEBLOCK_UPDATE, new HashMap<>());
        MainSurplusMarketsOutsideBlockDTO = preHookUpdate(MainSurplusMarketsOutsideBlockDTO);
        MainSurplusMarketsOutsideBlockDTO dto = serviceExt.update(MainSurplusMarketsOutsideBlockDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of MainSurplusMarketsOutsideBlocks.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainSurplusMarketsOutsideBlocks
     */
    @Override
    public PageDTO<MainSurplusMarketsOutsideBlockDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(MAINSURPLUSMARKETSOUTSIDEBLOCK_GET, new HashMap<>());
        PageDTO<MainSurplusMarketsOutsideBlockDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a MainSurplusMarketsOutsideBlock by their ID with a specified reason.
     *
     * @param id     The ID of the MainSurplusMarketsOutsideBlock to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(MAINSURPLUSMARKETSOUTSIDEBLOCK_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        MainSurplusMarketsOutsideBlockDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a MainSurplusMarketsOutsideBlock by their ID.
     *
     * @param id The ID of the MainSurplusMarketsOutsideBlock to retrieve
     * @return The MainSurplusMarketsOutsideBlock with the specified ID
     */
    @Override
    public MainSurplusMarketsOutsideBlockDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(MAINSURPLUSMARKETSOUTSIDEBLOCK_GET, new HashMap<>());
        MainSurplusMarketsOutsideBlockDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all MainSurplusMarketsOutsideBlocks by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainSurplusMarketsOutsideBlockDTO
     */
    @Override
    public Set<MainSurplusMarketsOutsideBlockDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(MAINSURPLUSMARKETSOUTSIDEBLOCK_GET, new HashMap<>());
        Set<MainSurplusMarketsOutsideBlockDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainSurplusMarketsOutsideBlockDTO postHookSave(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected MainSurplusMarketsOutsideBlockDTO preHookSave(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected MainSurplusMarketsOutsideBlockDTO postHookUpdate(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected MainSurplusMarketsOutsideBlockDTO preHookUpdate(MainSurplusMarketsOutsideBlockDTO MainSurplusMarketsOutsideBlockDTO) {
        return MainSurplusMarketsOutsideBlockDTO;
    }

    protected MainSurplusMarketsOutsideBlockDTO postHookDelete(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainSurplusMarketsOutsideBlockDTO postHookGetById(MainSurplusMarketsOutsideBlockDTO dto) {
        return dto;
    }

    protected PageDTO<MainSurplusMarketsOutsideBlockDTO> postHookGetAll(PageDTO<MainSurplusMarketsOutsideBlockDTO> result) {
        return result;
    }
}
