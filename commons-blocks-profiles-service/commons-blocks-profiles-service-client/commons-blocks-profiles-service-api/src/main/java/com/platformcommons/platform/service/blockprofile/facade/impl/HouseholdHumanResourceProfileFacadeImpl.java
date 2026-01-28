package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HouseholdHumanResourceProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HouseholdHumanResourceProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HouseholdHumanResourceProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HouseholdHumanResourceProfileFacadeImpl implements HouseholdHumanResourceProfileFacade {

    private final HouseholdHumanResourceProfileServiceExt serviceExt;
    private final HouseholdHumanResourceProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HOUSEHOLDHUMANRESOURCEPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDHUMANRESOURCEPROFILE.CREATE";
    private static final String HOUSEHOLDHUMANRESOURCEPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDHUMANRESOURCEPROFILE.UPDATED";
    private static final String HOUSEHOLDHUMANRESOURCEPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDHUMANRESOURCEPROFILE.DELETE";
    private static final String HOUSEHOLDHUMANRESOURCEPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HOUSEHOLDHUMANRESOURCEPROFILE.GET";

    public HouseholdHumanResourceProfileFacadeImpl(HouseholdHumanResourceProfileServiceExt serviceExt, HouseholdHumanResourceProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HouseholdHumanResourceProfile entry in the system.
     *
     * @param HouseholdHumanResourceProfileDTO The HouseholdHumanResourceProfile information to be saved
     * @return The saved HouseholdHumanResourceProfile data
     */
    @Override
    public HouseholdHumanResourceProfileDTO save(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfileDTO) {
        log.debug("Entry - save(HouseholdHumanResourceProfileDTO={})", HouseholdHumanResourceProfileDTO);
        evaluator.evaluate(HOUSEHOLDHUMANRESOURCEPROFILE_CREATE, new HashMap<>());
        HouseholdHumanResourceProfileDTO = preHookSave(HouseholdHumanResourceProfileDTO);
        HouseholdHumanResourceProfileDTO dto = serviceExt.save(HouseholdHumanResourceProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HouseholdHumanResourceProfile entry.
     *
     * @param HouseholdHumanResourceProfileDTO The HouseholdHumanResourceProfile information to be updated
     * @return The updated HouseholdHumanResourceProfile data
     */
    @Override
    public HouseholdHumanResourceProfileDTO update(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfileDTO) {
        log.debug("Entry - update(HouseholdHumanResourceProfileDTO={})", HouseholdHumanResourceProfileDTO);
        evaluator.evaluate(HOUSEHOLDHUMANRESOURCEPROFILE_UPDATE, new HashMap<>());
        HouseholdHumanResourceProfileDTO = preHookUpdate(HouseholdHumanResourceProfileDTO);
        HouseholdHumanResourceProfileDTO dto = serviceExt.update(HouseholdHumanResourceProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HouseholdHumanResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HouseholdHumanResourceProfiles
     */
    @Override
    public PageDTO<HouseholdHumanResourceProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HOUSEHOLDHUMANRESOURCEPROFILE_GET, new HashMap<>());
        PageDTO<HouseholdHumanResourceProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HouseholdHumanResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the HouseholdHumanResourceProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HOUSEHOLDHUMANRESOURCEPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HouseholdHumanResourceProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HouseholdHumanResourceProfile by their ID.
     *
     * @param id The ID of the HouseholdHumanResourceProfile to retrieve
     * @return The HouseholdHumanResourceProfile with the specified ID
     */
    @Override
    public HouseholdHumanResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HOUSEHOLDHUMANRESOURCEPROFILE_GET, new HashMap<>());
        HouseholdHumanResourceProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HouseholdHumanResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HouseholdHumanResourceProfileDTO
     */
    @Override
    public Set<HouseholdHumanResourceProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HOUSEHOLDHUMANRESOURCEPROFILE_GET, new HashMap<>());
        Set<HouseholdHumanResourceProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of hHEmploymentTypeList to a HouseholdHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdHumanResourceProfile to add hobbies to
     * @param hHEmploymentTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHEmploymentTypeToHouseholdHumanResourceProfile(Long id, List<HHEmploymentTypeDTO> hHEmploymentTypeList){
        serviceExt.addHHEmploymentTypeToHouseholdHumanResourceProfile(id,hHEmploymentTypeList);
    }
/**
     * Adds a list of hHEnterpriseTypeList to a HouseholdHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdHumanResourceProfile to add hobbies to
     * @param hHEnterpriseTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHEnterpriseTypeToHouseholdHumanResourceProfile(Long id, List<HHEnterpriseTypeDTO> hHEnterpriseTypeList){
        serviceExt.addHHEnterpriseTypeToHouseholdHumanResourceProfile(id,hHEnterpriseTypeList);
    }
/**
     * Adds a list of hHArtisanTypeList to a HouseholdHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the HouseholdHumanResourceProfile to add hobbies to
     * @param hHArtisanTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHArtisanTypeToHouseholdHumanResourceProfile(Long id, List<HHArtisanTypeDTO> hHArtisanTypeList){
        serviceExt.addHHArtisanTypeToHouseholdHumanResourceProfile(id,hHArtisanTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected HouseholdHumanResourceProfileDTO postHookSave(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected HouseholdHumanResourceProfileDTO preHookSave(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected HouseholdHumanResourceProfileDTO postHookUpdate(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected HouseholdHumanResourceProfileDTO preHookUpdate(HouseholdHumanResourceProfileDTO HouseholdHumanResourceProfileDTO) {
        return HouseholdHumanResourceProfileDTO;
    }

    protected HouseholdHumanResourceProfileDTO postHookDelete(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HouseholdHumanResourceProfileDTO postHookGetById(HouseholdHumanResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<HouseholdHumanResourceProfileDTO> postHookGetAll(PageDTO<HouseholdHumanResourceProfileDTO> result) {
        return result;
    }
}
