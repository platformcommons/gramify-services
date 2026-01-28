package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHEconomicConstraintsFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHEconomicConstraintsProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHEconomicConstraintsServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHEconomicConstraintsFacadeImpl implements HHEconomicConstraintsFacade {

    private final HHEconomicConstraintsServiceExt serviceExt;
    private final HHEconomicConstraintsProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHECONOMICCONSTRAINTS_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHECONOMICCONSTRAINTS.CREATE";
    private static final String HHECONOMICCONSTRAINTS_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHECONOMICCONSTRAINTS.UPDATED";
    private static final String HHECONOMICCONSTRAINTS_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHECONOMICCONSTRAINTS.DELETE";
    private static final String HHECONOMICCONSTRAINTS_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHECONOMICCONSTRAINTS.GET";

    public HHEconomicConstraintsFacadeImpl(HHEconomicConstraintsServiceExt serviceExt, HHEconomicConstraintsProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHEconomicConstraints entry in the system.
     *
     * @param HHEconomicConstraintsDTO The HHEconomicConstraints information to be saved
     * @return The saved HHEconomicConstraints data
     */
    @Override
    public HHEconomicConstraintsDTO save(HHEconomicConstraintsDTO HHEconomicConstraintsDTO) {
        log.debug("Entry - save(HHEconomicConstraintsDTO={})", HHEconomicConstraintsDTO);
        evaluator.evaluate(HHECONOMICCONSTRAINTS_CREATE, new HashMap<>());
        HHEconomicConstraintsDTO = preHookSave(HHEconomicConstraintsDTO);
        HHEconomicConstraintsDTO dto = serviceExt.save(HHEconomicConstraintsDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHEconomicConstraints entry.
     *
     * @param HHEconomicConstraintsDTO The HHEconomicConstraints information to be updated
     * @return The updated HHEconomicConstraints data
     */
    @Override
    public HHEconomicConstraintsDTO update(HHEconomicConstraintsDTO HHEconomicConstraintsDTO) {
        log.debug("Entry - update(HHEconomicConstraintsDTO={})", HHEconomicConstraintsDTO);
        evaluator.evaluate(HHECONOMICCONSTRAINTS_UPDATE, new HashMap<>());
        HHEconomicConstraintsDTO = preHookUpdate(HHEconomicConstraintsDTO);
        HHEconomicConstraintsDTO dto = serviceExt.update(HHEconomicConstraintsDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHEconomicConstraintss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHEconomicConstraintss
     */
    @Override
    public PageDTO<HHEconomicConstraintsDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHECONOMICCONSTRAINTS_GET, new HashMap<>());
        PageDTO<HHEconomicConstraintsDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHEconomicConstraints by their ID with a specified reason.
     *
     * @param id     The ID of the HHEconomicConstraints to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHECONOMICCONSTRAINTS_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHEconomicConstraintsDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHEconomicConstraints by their ID.
     *
     * @param id The ID of the HHEconomicConstraints to retrieve
     * @return The HHEconomicConstraints with the specified ID
     */
    @Override
    public HHEconomicConstraintsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHECONOMICCONSTRAINTS_GET, new HashMap<>());
        HHEconomicConstraintsDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHEconomicConstraintss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHEconomicConstraintsDTO
     */
    @Override
    public Set<HHEconomicConstraintsDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHECONOMICCONSTRAINTS_GET, new HashMap<>());
        Set<HHEconomicConstraintsDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHEconomicConstraintsDTO postHookSave(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected HHEconomicConstraintsDTO preHookSave(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected HHEconomicConstraintsDTO postHookUpdate(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected HHEconomicConstraintsDTO preHookUpdate(HHEconomicConstraintsDTO HHEconomicConstraintsDTO) {
        return HHEconomicConstraintsDTO;
    }

    protected HHEconomicConstraintsDTO postHookDelete(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHEconomicConstraintsDTO postHookGetById(HHEconomicConstraintsDTO dto) {
        return dto;
    }

    protected PageDTO<HHEconomicConstraintsDTO> postHookGetAll(PageDTO<HHEconomicConstraintsDTO> result) {
        return result;
    }
}
