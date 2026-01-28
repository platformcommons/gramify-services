package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.WherePeopleGoForRepairsFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.WherePeopleGoForRepairsProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.WherePeopleGoForRepairsServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class WherePeopleGoForRepairsFacadeImpl implements WherePeopleGoForRepairsFacade {

    private final WherePeopleGoForRepairsServiceExt serviceExt;
    private final WherePeopleGoForRepairsProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String WHEREPEOPLEGOFORREPAIRS_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREPEOPLEGOFORREPAIRS.CREATE";
    private static final String WHEREPEOPLEGOFORREPAIRS_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREPEOPLEGOFORREPAIRS.UPDATED";
    private static final String WHEREPEOPLEGOFORREPAIRS_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREPEOPLEGOFORREPAIRS.DELETE";
    private static final String WHEREPEOPLEGOFORREPAIRS_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREPEOPLEGOFORREPAIRS.GET";

    public WherePeopleGoForRepairsFacadeImpl(WherePeopleGoForRepairsServiceExt serviceExt, WherePeopleGoForRepairsProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new WherePeopleGoForRepairs entry in the system.
     *
     * @param WherePeopleGoForRepairsDTO The WherePeopleGoForRepairs information to be saved
     * @return The saved WherePeopleGoForRepairs data
     */
    @Override
    public WherePeopleGoForRepairsDTO save(WherePeopleGoForRepairsDTO WherePeopleGoForRepairsDTO) {
        log.debug("Entry - save(WherePeopleGoForRepairsDTO={})", WherePeopleGoForRepairsDTO);
        evaluator.evaluate(WHEREPEOPLEGOFORREPAIRS_CREATE, new HashMap<>());
        WherePeopleGoForRepairsDTO = preHookSave(WherePeopleGoForRepairsDTO);
        WherePeopleGoForRepairsDTO dto = serviceExt.save(WherePeopleGoForRepairsDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing WherePeopleGoForRepairs entry.
     *
     * @param WherePeopleGoForRepairsDTO The WherePeopleGoForRepairs information to be updated
     * @return The updated WherePeopleGoForRepairs data
     */
    @Override
    public WherePeopleGoForRepairsDTO update(WherePeopleGoForRepairsDTO WherePeopleGoForRepairsDTO) {
        log.debug("Entry - update(WherePeopleGoForRepairsDTO={})", WherePeopleGoForRepairsDTO);
        evaluator.evaluate(WHEREPEOPLEGOFORREPAIRS_UPDATE, new HashMap<>());
        WherePeopleGoForRepairsDTO = preHookUpdate(WherePeopleGoForRepairsDTO);
        WherePeopleGoForRepairsDTO dto = serviceExt.update(WherePeopleGoForRepairsDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of WherePeopleGoForRepairss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of WherePeopleGoForRepairss
     */
    @Override
    public PageDTO<WherePeopleGoForRepairsDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(WHEREPEOPLEGOFORREPAIRS_GET, new HashMap<>());
        PageDTO<WherePeopleGoForRepairsDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a WherePeopleGoForRepairs by their ID with a specified reason.
     *
     * @param id     The ID of the WherePeopleGoForRepairs to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(WHEREPEOPLEGOFORREPAIRS_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        WherePeopleGoForRepairsDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a WherePeopleGoForRepairs by their ID.
     *
     * @param id The ID of the WherePeopleGoForRepairs to retrieve
     * @return The WherePeopleGoForRepairs with the specified ID
     */
    @Override
    public WherePeopleGoForRepairsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(WHEREPEOPLEGOFORREPAIRS_GET, new HashMap<>());
        WherePeopleGoForRepairsDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all WherePeopleGoForRepairss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of WherePeopleGoForRepairsDTO
     */
    @Override
    public Set<WherePeopleGoForRepairsDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(WHEREPEOPLEGOFORREPAIRS_GET, new HashMap<>());
        Set<WherePeopleGoForRepairsDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected WherePeopleGoForRepairsDTO postHookSave(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected WherePeopleGoForRepairsDTO preHookSave(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected WherePeopleGoForRepairsDTO postHookUpdate(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected WherePeopleGoForRepairsDTO preHookUpdate(WherePeopleGoForRepairsDTO WherePeopleGoForRepairsDTO) {
        return WherePeopleGoForRepairsDTO;
    }

    protected WherePeopleGoForRepairsDTO postHookDelete(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected WherePeopleGoForRepairsDTO postHookGetById(WherePeopleGoForRepairsDTO dto) {
        return dto;
    }

    protected PageDTO<WherePeopleGoForRepairsDTO> postHookGetAll(PageDTO<WherePeopleGoForRepairsDTO> result) {
        return result;
    }
}
