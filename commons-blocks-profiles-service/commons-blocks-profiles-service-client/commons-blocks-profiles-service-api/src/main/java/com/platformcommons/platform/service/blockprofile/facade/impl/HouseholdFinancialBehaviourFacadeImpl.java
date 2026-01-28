package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HouseholdFinancialBehaviourFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HouseholdFinancialBehaviourProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HouseholdFinancialBehaviourServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HouseholdFinancialBehaviourFacadeImpl implements HouseholdFinancialBehaviourFacade {

    private final HouseholdFinancialBehaviourServiceExt serviceExt;
    private final HouseholdFinancialBehaviourProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HOUSEHOLDFINANCIALBEHAVIOUR_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDFINANCIALBEHAVIOUR.CREATE";
    private static final String HOUSEHOLDFINANCIALBEHAVIOUR_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDFINANCIALBEHAVIOUR.UPDATED";
    private static final String HOUSEHOLDFINANCIALBEHAVIOUR_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDFINANCIALBEHAVIOUR.DELETE";
    private static final String HOUSEHOLDFINANCIALBEHAVIOUR_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDFINANCIALBEHAVIOUR.GET";

    public HouseholdFinancialBehaviourFacadeImpl(HouseholdFinancialBehaviourServiceExt serviceExt, HouseholdFinancialBehaviourProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HouseholdFinancialBehaviour entry in the system.
     *
     * @param HouseholdFinancialBehaviourDTO The HouseholdFinancialBehaviour information to be saved
     * @return The saved HouseholdFinancialBehaviour data
     */
    @Override
    public HouseholdFinancialBehaviourDTO save(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviourDTO) {
        log.debug("Entry - save(HouseholdFinancialBehaviourDTO={})", HouseholdFinancialBehaviourDTO);
        evaluator.evaluate(HOUSEHOLDFINANCIALBEHAVIOUR_CREATE, new HashMap<>());
        HouseholdFinancialBehaviourDTO = preHookSave(HouseholdFinancialBehaviourDTO);
        HouseholdFinancialBehaviourDTO dto = serviceExt.save(HouseholdFinancialBehaviourDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HouseholdFinancialBehaviour entry.
     *
     * @param HouseholdFinancialBehaviourDTO The HouseholdFinancialBehaviour information to be updated
     * @return The updated HouseholdFinancialBehaviour data
     */
    @Override
    public HouseholdFinancialBehaviourDTO update(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviourDTO) {
        log.debug("Entry - update(HouseholdFinancialBehaviourDTO={})", HouseholdFinancialBehaviourDTO);
        evaluator.evaluate(HOUSEHOLDFINANCIALBEHAVIOUR_UPDATE, new HashMap<>());
        HouseholdFinancialBehaviourDTO = preHookUpdate(HouseholdFinancialBehaviourDTO);
        HouseholdFinancialBehaviourDTO dto = serviceExt.update(HouseholdFinancialBehaviourDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HouseholdFinancialBehaviours.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdFinancialBehaviours
     */
    @Override
    public PageDTO<HouseholdFinancialBehaviourDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HOUSEHOLDFINANCIALBEHAVIOUR_GET, new HashMap<>());
        PageDTO<HouseholdFinancialBehaviourDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HouseholdFinancialBehaviour by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdFinancialBehaviour to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HOUSEHOLDFINANCIALBEHAVIOUR_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HouseholdFinancialBehaviourDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HouseholdFinancialBehaviour by their ID.
     *
     * @param id The ID of the HouseholdFinancialBehaviour to retrieve
     * @return The HouseholdFinancialBehaviour with the specified ID
     */
    @Override
    public HouseholdFinancialBehaviourDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HOUSEHOLDFINANCIALBEHAVIOUR_GET, new HashMap<>());
        HouseholdFinancialBehaviourDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HouseholdFinancialBehaviours by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdFinancialBehaviourDTO
     */
    @Override
    public Set<HouseholdFinancialBehaviourDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HOUSEHOLDFINANCIALBEHAVIOUR_GET, new HashMap<>());
        Set<HouseholdFinancialBehaviourDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of hHSavingsModeList to a HouseholdFinancialBehaviour identified by their ID.
     *
     * @param id               The ID of the HouseholdFinancialBehaviour to add hobbies to
     * @param hHSavingsModeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHSavingsModeToHouseholdFinancialBehaviour(Long id, List<HHSavingsModeDTO> hHSavingsModeList){
        serviceExt.addHHSavingsModeToHouseholdFinancialBehaviour(id,hHSavingsModeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdFinancialBehaviourDTO postHookSave(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected HouseholdFinancialBehaviourDTO preHookSave(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected HouseholdFinancialBehaviourDTO postHookUpdate(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected HouseholdFinancialBehaviourDTO preHookUpdate(HouseholdFinancialBehaviourDTO HouseholdFinancialBehaviourDTO) {
        return HouseholdFinancialBehaviourDTO;
    }

    protected HouseholdFinancialBehaviourDTO postHookDelete(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdFinancialBehaviourDTO postHookGetById(HouseholdFinancialBehaviourDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdFinancialBehaviourDTO> postHookGetAll(PageDTO<HouseholdFinancialBehaviourDTO> result) {
        return result;
    }
}
