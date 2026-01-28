package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HouseholdAspirationsProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HouseholdAspirationsProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HouseholdAspirationsProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HouseholdAspirationsProfileFacadeImpl implements HouseholdAspirationsProfileFacade {

    private final HouseholdAspirationsProfileServiceExt serviceExt;
    private final HouseholdAspirationsProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HOUSEHOLDASPIRATIONSPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDASPIRATIONSPROFILE.CREATE";
    private static final String HOUSEHOLDASPIRATIONSPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDASPIRATIONSPROFILE.UPDATED";
    private static final String HOUSEHOLDASPIRATIONSPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDASPIRATIONSPROFILE.DELETE";
    private static final String HOUSEHOLDASPIRATIONSPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDASPIRATIONSPROFILE.GET";

    public HouseholdAspirationsProfileFacadeImpl(HouseholdAspirationsProfileServiceExt serviceExt, HouseholdAspirationsProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HouseholdAspirationsProfile entry in the system.
     *
     * @param HouseholdAspirationsProfileDTO The HouseholdAspirationsProfile information to be saved
     * @return The saved HouseholdAspirationsProfile data
     */
    @Override
    public HouseholdAspirationsProfileDTO save(HouseholdAspirationsProfileDTO HouseholdAspirationsProfileDTO) {
        log.debug("Entry - save(HouseholdAspirationsProfileDTO={})", HouseholdAspirationsProfileDTO);
        evaluator.evaluate(HOUSEHOLDASPIRATIONSPROFILE_CREATE, new HashMap<>());
        HouseholdAspirationsProfileDTO = preHookSave(HouseholdAspirationsProfileDTO);
        HouseholdAspirationsProfileDTO dto = serviceExt.save(HouseholdAspirationsProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HouseholdAspirationsProfile entry.
     *
     * @param HouseholdAspirationsProfileDTO The HouseholdAspirationsProfile information to be updated
     * @return The updated HouseholdAspirationsProfile data
     */
    @Override
    public HouseholdAspirationsProfileDTO update(HouseholdAspirationsProfileDTO HouseholdAspirationsProfileDTO) {
        log.debug("Entry - update(HouseholdAspirationsProfileDTO={})", HouseholdAspirationsProfileDTO);
        evaluator.evaluate(HOUSEHOLDASPIRATIONSPROFILE_UPDATE, new HashMap<>());
        HouseholdAspirationsProfileDTO = preHookUpdate(HouseholdAspirationsProfileDTO);
        HouseholdAspirationsProfileDTO dto = serviceExt.update(HouseholdAspirationsProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HouseholdAspirationsProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdAspirationsProfiles
     */
    @Override
    public PageDTO<HouseholdAspirationsProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HOUSEHOLDASPIRATIONSPROFILE_GET, new HashMap<>());
        PageDTO<HouseholdAspirationsProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HouseholdAspirationsProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdAspirationsProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HOUSEHOLDASPIRATIONSPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HouseholdAspirationsProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HouseholdAspirationsProfile by their ID.
     *
     * @param id The ID of the HouseholdAspirationsProfile to retrieve
     * @return The HouseholdAspirationsProfile with the specified ID
     */
    @Override
    public HouseholdAspirationsProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HOUSEHOLDASPIRATIONSPROFILE_GET, new HashMap<>());
        HouseholdAspirationsProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HouseholdAspirationsProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdAspirationsProfileDTO
     */
    @Override
    public Set<HouseholdAspirationsProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HOUSEHOLDASPIRATIONSPROFILE_GET, new HashMap<>());
        Set<HouseholdAspirationsProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of hHSocialAspirationList to a HouseholdAspirationsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdAspirationsProfile to add hobbies to
     * @param hHSocialAspirationList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHSocialAspirationToHouseholdAspirationsProfile(Long id, List<HHSocialAspirationDTO> hHSocialAspirationList){
        serviceExt.addHHSocialAspirationToHouseholdAspirationsProfile(id,hHSocialAspirationList);
    }
/**
     * Adds a list of hHEconomicAspirationList to a HouseholdAspirationsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdAspirationsProfile to add hobbies to
     * @param hHEconomicAspirationList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHEconomicAspirationToHouseholdAspirationsProfile(Long id, List<HHEconomicAspirationDTO> hHEconomicAspirationList){
        serviceExt.addHHEconomicAspirationToHouseholdAspirationsProfile(id,hHEconomicAspirationList);
    }
/**
     * Adds a list of hHGovernanceAspirationList to a HouseholdAspirationsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdAspirationsProfile to add hobbies to
     * @param hHGovernanceAspirationList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHGovernanceAspirationToHouseholdAspirationsProfile(Long id, List<HHGovernanceAspirationDTO> hHGovernanceAspirationList){
        serviceExt.addHHGovernanceAspirationToHouseholdAspirationsProfile(id,hHGovernanceAspirationList);
    }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdAspirationsProfileDTO postHookSave(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected HouseholdAspirationsProfileDTO preHookSave(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected HouseholdAspirationsProfileDTO postHookUpdate(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected HouseholdAspirationsProfileDTO preHookUpdate(HouseholdAspirationsProfileDTO HouseholdAspirationsProfileDTO) {
        return HouseholdAspirationsProfileDTO;
    }

    protected HouseholdAspirationsProfileDTO postHookDelete(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdAspirationsProfileDTO postHookGetById(HouseholdAspirationsProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdAspirationsProfileDTO> postHookGetAll(PageDTO<HouseholdAspirationsProfileDTO> result) {
        return result;
    }
}
