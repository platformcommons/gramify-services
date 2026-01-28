package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HouseholdLivelihoodProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HouseholdLivelihoodProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HouseholdLivelihoodProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HouseholdLivelihoodProfileFacadeImpl implements HouseholdLivelihoodProfileFacade {

    private final HouseholdLivelihoodProfileServiceExt serviceExt;
    private final HouseholdLivelihoodProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HOUSEHOLDLIVELIHOODPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDLIVELIHOODPROFILE.CREATE";
    private static final String HOUSEHOLDLIVELIHOODPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDLIVELIHOODPROFILE.UPDATED";
    private static final String HOUSEHOLDLIVELIHOODPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDLIVELIHOODPROFILE.DELETE";
    private static final String HOUSEHOLDLIVELIHOODPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDLIVELIHOODPROFILE.GET";

    public HouseholdLivelihoodProfileFacadeImpl(HouseholdLivelihoodProfileServiceExt serviceExt, HouseholdLivelihoodProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HouseholdLivelihoodProfile entry in the system.
     *
     * @param HouseholdLivelihoodProfileDTO The HouseholdLivelihoodProfile information to be saved
     * @return The saved HouseholdLivelihoodProfile data
     */
    @Override
    public HouseholdLivelihoodProfileDTO save(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfileDTO) {
        log.debug("Entry - save(HouseholdLivelihoodProfileDTO={})", HouseholdLivelihoodProfileDTO);
        evaluator.evaluate(HOUSEHOLDLIVELIHOODPROFILE_CREATE, new HashMap<>());
        HouseholdLivelihoodProfileDTO = preHookSave(HouseholdLivelihoodProfileDTO);
        HouseholdLivelihoodProfileDTO dto = serviceExt.save(HouseholdLivelihoodProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HouseholdLivelihoodProfile entry.
     *
     * @param HouseholdLivelihoodProfileDTO The HouseholdLivelihoodProfile information to be updated
     * @return The updated HouseholdLivelihoodProfile data
     */
    @Override
    public HouseholdLivelihoodProfileDTO update(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfileDTO) {
        log.debug("Entry - update(HouseholdLivelihoodProfileDTO={})", HouseholdLivelihoodProfileDTO);
        evaluator.evaluate(HOUSEHOLDLIVELIHOODPROFILE_UPDATE, new HashMap<>());
        HouseholdLivelihoodProfileDTO = preHookUpdate(HouseholdLivelihoodProfileDTO);
        HouseholdLivelihoodProfileDTO dto = serviceExt.update(HouseholdLivelihoodProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HouseholdLivelihoodProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdLivelihoodProfiles
     */
    @Override
    public PageDTO<HouseholdLivelihoodProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HOUSEHOLDLIVELIHOODPROFILE_GET, new HashMap<>());
        PageDTO<HouseholdLivelihoodProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HouseholdLivelihoodProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdLivelihoodProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HOUSEHOLDLIVELIHOODPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HouseholdLivelihoodProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HouseholdLivelihoodProfile by their ID.
     *
     * @param id The ID of the HouseholdLivelihoodProfile to retrieve
     * @return The HouseholdLivelihoodProfile with the specified ID
     */
    @Override
    public HouseholdLivelihoodProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HOUSEHOLDLIVELIHOODPROFILE_GET, new HashMap<>());
        HouseholdLivelihoodProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HouseholdLivelihoodProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdLivelihoodProfileDTO
     */
    @Override
    public Set<HouseholdLivelihoodProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HOUSEHOLDLIVELIHOODPROFILE_GET, new HashMap<>());
        Set<HouseholdLivelihoodProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of hHMigrationMonthList to a HouseholdLivelihoodProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdLivelihoodProfile to add hobbies to
     * @param hHMigrationMonthList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHMigrationMonthToHouseholdLivelihoodProfile(Long id, List<HHMigrationMonthDTO> hHMigrationMonthList){
        serviceExt.addHHMigrationMonthToHouseholdLivelihoodProfile(id,hHMigrationMonthList);
    }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdLivelihoodProfileDTO postHookSave(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected HouseholdLivelihoodProfileDTO preHookSave(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected HouseholdLivelihoodProfileDTO postHookUpdate(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected HouseholdLivelihoodProfileDTO preHookUpdate(HouseholdLivelihoodProfileDTO HouseholdLivelihoodProfileDTO) {
        return HouseholdLivelihoodProfileDTO;
    }

    protected HouseholdLivelihoodProfileDTO postHookDelete(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdLivelihoodProfileDTO postHookGetById(HouseholdLivelihoodProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdLivelihoodProfileDTO> postHookGetAll(PageDTO<HouseholdLivelihoodProfileDTO> result) {
        return result;
    }
}
