package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HouseholdDemographicsFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HouseholdDemographicsProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HouseholdDemographicsServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HouseholdDemographicsFacadeImpl implements HouseholdDemographicsFacade {

    private final HouseholdDemographicsServiceExt serviceExt;
    private final HouseholdDemographicsProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HOUSEHOLDDEMOGRAPHICS_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDDEMOGRAPHICS.CREATE";
    private static final String HOUSEHOLDDEMOGRAPHICS_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDDEMOGRAPHICS.UPDATED";
    private static final String HOUSEHOLDDEMOGRAPHICS_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDDEMOGRAPHICS.DELETE";
    private static final String HOUSEHOLDDEMOGRAPHICS_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDDEMOGRAPHICS.GET";

    public HouseholdDemographicsFacadeImpl(HouseholdDemographicsServiceExt serviceExt, HouseholdDemographicsProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HouseholdDemographics entry in the system.
     *
     * @param HouseholdDemographicsDTO The HouseholdDemographics information to be saved
     * @return The saved HouseholdDemographics data
     */
    @Override
    public HouseholdDemographicsDTO save(HouseholdDemographicsDTO HouseholdDemographicsDTO) {
        log.debug("Entry - save(HouseholdDemographicsDTO={})", HouseholdDemographicsDTO);
        evaluator.evaluate(HOUSEHOLDDEMOGRAPHICS_CREATE, new HashMap<>());
        HouseholdDemographicsDTO = preHookSave(HouseholdDemographicsDTO);
        HouseholdDemographicsDTO dto = serviceExt.save(HouseholdDemographicsDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HouseholdDemographics entry.
     *
     * @param HouseholdDemographicsDTO The HouseholdDemographics information to be updated
     * @return The updated HouseholdDemographics data
     */
    @Override
    public HouseholdDemographicsDTO update(HouseholdDemographicsDTO HouseholdDemographicsDTO) {
        log.debug("Entry - update(HouseholdDemographicsDTO={})", HouseholdDemographicsDTO);
        evaluator.evaluate(HOUSEHOLDDEMOGRAPHICS_UPDATE, new HashMap<>());
        HouseholdDemographicsDTO = preHookUpdate(HouseholdDemographicsDTO);
        HouseholdDemographicsDTO dto = serviceExt.update(HouseholdDemographicsDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HouseholdDemographicss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdDemographicss
     */
    @Override
    public PageDTO<HouseholdDemographicsDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HOUSEHOLDDEMOGRAPHICS_GET, new HashMap<>());
        PageDTO<HouseholdDemographicsDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HouseholdDemographics by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdDemographics to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HOUSEHOLDDEMOGRAPHICS_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HouseholdDemographicsDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HouseholdDemographics by their ID.
     *
     * @param id The ID of the HouseholdDemographics to retrieve
     * @return The HouseholdDemographics with the specified ID
     */
    @Override
    public HouseholdDemographicsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HOUSEHOLDDEMOGRAPHICS_GET, new HashMap<>());
        HouseholdDemographicsDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HouseholdDemographicss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdDemographicsDTO
     */
    @Override
    public Set<HouseholdDemographicsDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HOUSEHOLDDEMOGRAPHICS_GET, new HashMap<>());
        Set<HouseholdDemographicsDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HouseholdDemographicsDTO postHookSave(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected HouseholdDemographicsDTO preHookSave(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected HouseholdDemographicsDTO postHookUpdate(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected HouseholdDemographicsDTO preHookUpdate(HouseholdDemographicsDTO HouseholdDemographicsDTO) {
        return HouseholdDemographicsDTO;
    }

    protected HouseholdDemographicsDTO postHookDelete(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdDemographicsDTO postHookGetById(HouseholdDemographicsDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdDemographicsDTO> postHookGetAll(PageDTO<HouseholdDemographicsDTO> result) {
        return result;
    }
}
