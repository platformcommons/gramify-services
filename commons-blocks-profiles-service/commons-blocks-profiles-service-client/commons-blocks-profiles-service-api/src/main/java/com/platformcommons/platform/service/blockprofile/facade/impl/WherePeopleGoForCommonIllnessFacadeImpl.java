package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.WherePeopleGoForCommonIllnessFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.WherePeopleGoForCommonIllnessProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.WherePeopleGoForCommonIllnessServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class WherePeopleGoForCommonIllnessFacadeImpl implements WherePeopleGoForCommonIllnessFacade {

    private final WherePeopleGoForCommonIllnessServiceExt serviceExt;
    private final WherePeopleGoForCommonIllnessProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String WHEREPEOPLEGOFORCOMMONILLNESS_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREPEOPLEGOFORCOMMONILLNESS.CREATE";
    private static final String WHEREPEOPLEGOFORCOMMONILLNESS_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREPEOPLEGOFORCOMMONILLNESS.UPDATED";
    private static final String WHEREPEOPLEGOFORCOMMONILLNESS_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREPEOPLEGOFORCOMMONILLNESS.DELETE";
    private static final String WHEREPEOPLEGOFORCOMMONILLNESS_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREPEOPLEGOFORCOMMONILLNESS.GET";

    public WherePeopleGoForCommonIllnessFacadeImpl(WherePeopleGoForCommonIllnessServiceExt serviceExt, WherePeopleGoForCommonIllnessProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new WherePeopleGoForCommonIllness entry in the system.
     *
     * @param WherePeopleGoForCommonIllnessDTO The WherePeopleGoForCommonIllness information to be saved
     * @return The saved WherePeopleGoForCommonIllness data
     */
    @Override
    public WherePeopleGoForCommonIllnessDTO save(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllnessDTO) {
        log.debug("Entry - save(WherePeopleGoForCommonIllnessDTO={})", WherePeopleGoForCommonIllnessDTO);
        evaluator.evaluate(WHEREPEOPLEGOFORCOMMONILLNESS_CREATE, new HashMap<>());
        WherePeopleGoForCommonIllnessDTO = preHookSave(WherePeopleGoForCommonIllnessDTO);
        WherePeopleGoForCommonIllnessDTO dto = serviceExt.save(WherePeopleGoForCommonIllnessDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing WherePeopleGoForCommonIllness entry.
     *
     * @param WherePeopleGoForCommonIllnessDTO The WherePeopleGoForCommonIllness information to be updated
     * @return The updated WherePeopleGoForCommonIllness data
     */
    @Override
    public WherePeopleGoForCommonIllnessDTO update(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllnessDTO) {
        log.debug("Entry - update(WherePeopleGoForCommonIllnessDTO={})", WherePeopleGoForCommonIllnessDTO);
        evaluator.evaluate(WHEREPEOPLEGOFORCOMMONILLNESS_UPDATE, new HashMap<>());
        WherePeopleGoForCommonIllnessDTO = preHookUpdate(WherePeopleGoForCommonIllnessDTO);
        WherePeopleGoForCommonIllnessDTO dto = serviceExt.update(WherePeopleGoForCommonIllnessDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of WherePeopleGoForCommonIllnesss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of WherePeopleGoForCommonIllnesss
     */
    @Override
    public PageDTO<WherePeopleGoForCommonIllnessDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(WHEREPEOPLEGOFORCOMMONILLNESS_GET, new HashMap<>());
        PageDTO<WherePeopleGoForCommonIllnessDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a WherePeopleGoForCommonIllness by their ID with a specified reason.
     *
     * @param id     The ID of the WherePeopleGoForCommonIllness to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(WHEREPEOPLEGOFORCOMMONILLNESS_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        WherePeopleGoForCommonIllnessDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a WherePeopleGoForCommonIllness by their ID.
     *
     * @param id The ID of the WherePeopleGoForCommonIllness to retrieve
     * @return The WherePeopleGoForCommonIllness with the specified ID
     */
    @Override
    public WherePeopleGoForCommonIllnessDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(WHEREPEOPLEGOFORCOMMONILLNESS_GET, new HashMap<>());
        WherePeopleGoForCommonIllnessDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all WherePeopleGoForCommonIllnesss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of WherePeopleGoForCommonIllnessDTO
     */
    @Override
    public Set<WherePeopleGoForCommonIllnessDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(WHEREPEOPLEGOFORCOMMONILLNESS_GET, new HashMap<>());
        Set<WherePeopleGoForCommonIllnessDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected WherePeopleGoForCommonIllnessDTO postHookSave(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected WherePeopleGoForCommonIllnessDTO preHookSave(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected WherePeopleGoForCommonIllnessDTO postHookUpdate(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected WherePeopleGoForCommonIllnessDTO preHookUpdate(WherePeopleGoForCommonIllnessDTO WherePeopleGoForCommonIllnessDTO) {
        return WherePeopleGoForCommonIllnessDTO;
    }

    protected WherePeopleGoForCommonIllnessDTO postHookDelete(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected WherePeopleGoForCommonIllnessDTO postHookGetById(WherePeopleGoForCommonIllnessDTO dto) {
        return dto;
    }

    protected PageDTO<WherePeopleGoForCommonIllnessDTO> postHookGetAll(PageDTO<WherePeopleGoForCommonIllnessDTO> result) {
        return result;
    }
}
