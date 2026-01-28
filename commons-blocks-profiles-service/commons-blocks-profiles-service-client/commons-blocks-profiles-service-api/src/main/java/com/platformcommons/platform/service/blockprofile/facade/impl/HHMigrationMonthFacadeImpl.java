package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHMigrationMonthFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHMigrationMonthProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHMigrationMonthServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHMigrationMonthFacadeImpl implements HHMigrationMonthFacade {

    private final HHMigrationMonthServiceExt serviceExt;
    private final HHMigrationMonthProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHMIGRATIONMONTH_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHMIGRATIONMONTH.CREATE";
    private static final String HHMIGRATIONMONTH_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHMIGRATIONMONTH.UPDATED";
    private static final String HHMIGRATIONMONTH_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHMIGRATIONMONTH.DELETE";
    private static final String HHMIGRATIONMONTH_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHMIGRATIONMONTH.GET";

    public HHMigrationMonthFacadeImpl(HHMigrationMonthServiceExt serviceExt, HHMigrationMonthProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHMigrationMonth entry in the system.
     *
     * @param HHMigrationMonthDTO The HHMigrationMonth information to be saved
     * @return The saved HHMigrationMonth data
     */
    @Override
    public HHMigrationMonthDTO save(HHMigrationMonthDTO HHMigrationMonthDTO) {
        log.debug("Entry - save(HHMigrationMonthDTO={})", HHMigrationMonthDTO);
        evaluator.evaluate(HHMIGRATIONMONTH_CREATE, new HashMap<>());
        HHMigrationMonthDTO = preHookSave(HHMigrationMonthDTO);
        HHMigrationMonthDTO dto = serviceExt.save(HHMigrationMonthDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHMigrationMonth entry.
     *
     * @param HHMigrationMonthDTO The HHMigrationMonth information to be updated
     * @return The updated HHMigrationMonth data
     */
    @Override
    public HHMigrationMonthDTO update(HHMigrationMonthDTO HHMigrationMonthDTO) {
        log.debug("Entry - update(HHMigrationMonthDTO={})", HHMigrationMonthDTO);
        evaluator.evaluate(HHMIGRATIONMONTH_UPDATE, new HashMap<>());
        HHMigrationMonthDTO = preHookUpdate(HHMigrationMonthDTO);
        HHMigrationMonthDTO dto = serviceExt.update(HHMigrationMonthDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHMigrationMonths.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHMigrationMonths
     */
    @Override
    public PageDTO<HHMigrationMonthDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHMIGRATIONMONTH_GET, new HashMap<>());
        PageDTO<HHMigrationMonthDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHMigrationMonth by their ID with a specified reason.
     *
     * @param id     The ID of the HHMigrationMonth to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHMIGRATIONMONTH_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHMigrationMonthDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHMigrationMonth by their ID.
     *
     * @param id The ID of the HHMigrationMonth to retrieve
     * @return The HHMigrationMonth with the specified ID
     */
    @Override
    public HHMigrationMonthDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHMIGRATIONMONTH_GET, new HashMap<>());
        HHMigrationMonthDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHMigrationMonths by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHMigrationMonthDTO
     */
    @Override
    public Set<HHMigrationMonthDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHMIGRATIONMONTH_GET, new HashMap<>());
        Set<HHMigrationMonthDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHMigrationMonthDTO postHookSave(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected HHMigrationMonthDTO preHookSave(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected HHMigrationMonthDTO postHookUpdate(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected HHMigrationMonthDTO preHookUpdate(HHMigrationMonthDTO HHMigrationMonthDTO) {
        return HHMigrationMonthDTO;
    }

    protected HHMigrationMonthDTO postHookDelete(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHMigrationMonthDTO postHookGetById(HHMigrationMonthDTO dto) {
        return dto;
    }

    protected PageDTO<HHMigrationMonthDTO> postHookGetAll(PageDTO<HHMigrationMonthDTO> result) {
        return result;
    }
}
