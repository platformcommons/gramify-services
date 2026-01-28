package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HouseholdFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HouseholdProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HouseholdServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HouseholdFacadeImpl implements HouseholdFacade {

    private final HouseholdServiceExt serviceExt;
    private final HouseholdProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HOUSEHOLD_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLD.CREATE";
    private static final String HOUSEHOLD_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLD.UPDATED";
    private static final String HOUSEHOLD_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLD.DELETE";
    private static final String HOUSEHOLD_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLD.GET";

    public HouseholdFacadeImpl(HouseholdServiceExt serviceExt, HouseholdProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new Household entry in the system.
     *
     * @param HouseholdDTO The Household information to be saved
     * @return The saved Household data
     */
    @Override
    public HouseholdDTO save(HouseholdDTO HouseholdDTO) {
        log.debug("Entry - save(HouseholdDTO={})", HouseholdDTO);
        evaluator.evaluate(HOUSEHOLD_CREATE, new HashMap<>());
        HouseholdDTO = preHookSave(HouseholdDTO);
        HouseholdDTO dto = serviceExt.save(HouseholdDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing Household entry.
     *
     * @param HouseholdDTO The Household information to be updated
     * @return The updated Household data
     */
    @Override
    public HouseholdDTO update(HouseholdDTO HouseholdDTO) {
        log.debug("Entry - update(HouseholdDTO={})", HouseholdDTO);
        evaluator.evaluate(HOUSEHOLD_UPDATE, new HashMap<>());
        HouseholdDTO = preHookUpdate(HouseholdDTO);
        HouseholdDTO dto = serviceExt.update(HouseholdDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of Households.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of Households
     */
    @Override
    public PageDTO<HouseholdDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HOUSEHOLD_GET, new HashMap<>());
        PageDTO<HouseholdDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a Household by their ID with a specified reason.
     *
     * @param id     The ID of the Household to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HOUSEHOLD_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HouseholdDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a Household by their ID.
     *
     * @param id The ID of the Household to retrieve
     * @return The Household with the specified ID
     */
    @Override
    public HouseholdDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HOUSEHOLD_GET, new HashMap<>());
        HouseholdDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all Households by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdDTO
     */
    @Override
    public Set<HouseholdDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HOUSEHOLD_GET, new HashMap<>());
        Set<HouseholdDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of hHLoanSourceList to a Household identified by their ID.
     *
     * @param id               The ID of the Household to add hobbies to
     * @param hHLoanSourceList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHLoanSourceToHousehold(Long id, List<HHLoanSourceDTO> hHLoanSourceList){
        serviceExt.addHHLoanSourceToHousehold(id,hHLoanSourceList);
    }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdDTO postHookSave(HouseholdDTO dto) {
        return dto;
    }

    protected HouseholdDTO preHookSave(HouseholdDTO dto) {
        return dto;
    }

    protected HouseholdDTO postHookUpdate(HouseholdDTO dto) {
        return dto;
    }

    protected HouseholdDTO preHookUpdate(HouseholdDTO HouseholdDTO) {
        return HouseholdDTO;
    }

    protected HouseholdDTO postHookDelete(HouseholdDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdDTO postHookGetById(HouseholdDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdDTO> postHookGetAll(PageDTO<HouseholdDTO> result) {
        return result;
    }
}
