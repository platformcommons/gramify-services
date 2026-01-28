package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HouseholdConstraintsProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HouseholdConstraintsProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HouseholdConstraintsProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HouseholdConstraintsProfileFacadeImpl implements HouseholdConstraintsProfileFacade {

    private final HouseholdConstraintsProfileServiceExt serviceExt;
    private final HouseholdConstraintsProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HOUSEHOLDCONSTRAINTSPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDCONSTRAINTSPROFILE.CREATE";
    private static final String HOUSEHOLDCONSTRAINTSPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDCONSTRAINTSPROFILE.UPDATED";
    private static final String HOUSEHOLDCONSTRAINTSPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDCONSTRAINTSPROFILE.DELETE";
    private static final String HOUSEHOLDCONSTRAINTSPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDCONSTRAINTSPROFILE.GET";

    public HouseholdConstraintsProfileFacadeImpl(HouseholdConstraintsProfileServiceExt serviceExt, HouseholdConstraintsProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HouseholdConstraintsProfile entry in the system.
     *
     * @param HouseholdConstraintsProfileDTO The HouseholdConstraintsProfile information to be saved
     * @return The saved HouseholdConstraintsProfile data
     */
    @Override
    public HouseholdConstraintsProfileDTO save(HouseholdConstraintsProfileDTO HouseholdConstraintsProfileDTO) {
        log.debug("Entry - save(HouseholdConstraintsProfileDTO={})", HouseholdConstraintsProfileDTO);
        evaluator.evaluate(HOUSEHOLDCONSTRAINTSPROFILE_CREATE, new HashMap<>());
        HouseholdConstraintsProfileDTO = preHookSave(HouseholdConstraintsProfileDTO);
        HouseholdConstraintsProfileDTO dto = serviceExt.save(HouseholdConstraintsProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HouseholdConstraintsProfile entry.
     *
     * @param HouseholdConstraintsProfileDTO The HouseholdConstraintsProfile information to be updated
     * @return The updated HouseholdConstraintsProfile data
     */
    @Override
    public HouseholdConstraintsProfileDTO update(HouseholdConstraintsProfileDTO HouseholdConstraintsProfileDTO) {
        log.debug("Entry - update(HouseholdConstraintsProfileDTO={})", HouseholdConstraintsProfileDTO);
        evaluator.evaluate(HOUSEHOLDCONSTRAINTSPROFILE_UPDATE, new HashMap<>());
        HouseholdConstraintsProfileDTO = preHookUpdate(HouseholdConstraintsProfileDTO);
        HouseholdConstraintsProfileDTO dto = serviceExt.update(HouseholdConstraintsProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HouseholdConstraintsProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdConstraintsProfiles
     */
    @Override
    public PageDTO<HouseholdConstraintsProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HOUSEHOLDCONSTRAINTSPROFILE_GET, new HashMap<>());
        PageDTO<HouseholdConstraintsProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HouseholdConstraintsProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdConstraintsProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HOUSEHOLDCONSTRAINTSPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HouseholdConstraintsProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HouseholdConstraintsProfile by their ID.
     *
     * @param id The ID of the HouseholdConstraintsProfile to retrieve
     * @return The HouseholdConstraintsProfile with the specified ID
     */
    @Override
    public HouseholdConstraintsProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HOUSEHOLDCONSTRAINTSPROFILE_GET, new HashMap<>());
        HouseholdConstraintsProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HouseholdConstraintsProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdConstraintsProfileDTO
     */
    @Override
    public Set<HouseholdConstraintsProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HOUSEHOLDCONSTRAINTSPROFILE_GET, new HashMap<>());
        Set<HouseholdConstraintsProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of hHSocialConstraintsList to a HouseholdConstraintsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdConstraintsProfile to add hobbies to
     * @param hHSocialConstraintsList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHSocialConstraintsToHouseholdConstraintsProfile(Long id, List<HHSocialConstraintsDTO> hHSocialConstraintsList){
        serviceExt.addHHSocialConstraintsToHouseholdConstraintsProfile(id,hHSocialConstraintsList);
    }
/**
     * Adds a list of hHEconomicConstraintsList to a HouseholdConstraintsProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdConstraintsProfile to add hobbies to
     * @param hHEconomicConstraintsList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHEconomicConstraintsToHouseholdConstraintsProfile(Long id, List<HHEconomicConstraintsDTO> hHEconomicConstraintsList){
        serviceExt.addHHEconomicConstraintsToHouseholdConstraintsProfile(id,hHEconomicConstraintsList);
    }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdConstraintsProfileDTO postHookSave(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected HouseholdConstraintsProfileDTO preHookSave(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected HouseholdConstraintsProfileDTO postHookUpdate(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected HouseholdConstraintsProfileDTO preHookUpdate(HouseholdConstraintsProfileDTO HouseholdConstraintsProfileDTO) {
        return HouseholdConstraintsProfileDTO;
    }

    protected HouseholdConstraintsProfileDTO postHookDelete(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdConstraintsProfileDTO postHookGetById(HouseholdConstraintsProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdConstraintsProfileDTO> postHookGetAll(PageDTO<HouseholdConstraintsProfileDTO> result) {
        return result;
    }
}
