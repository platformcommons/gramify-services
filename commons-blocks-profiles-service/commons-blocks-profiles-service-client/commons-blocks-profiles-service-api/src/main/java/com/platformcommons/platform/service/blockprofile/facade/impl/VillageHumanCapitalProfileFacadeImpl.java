package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageHumanCapitalProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageHumanCapitalProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageHumanCapitalProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageHumanCapitalProfileFacadeImpl implements VillageHumanCapitalProfileFacade {

    private final VillageHumanCapitalProfileServiceExt serviceExt;
    private final VillageHumanCapitalProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEHUMANCAPITALPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHUMANCAPITALPROFILE.CREATE";
    private static final String VILLAGEHUMANCAPITALPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHUMANCAPITALPROFILE.UPDATED";
    private static final String VILLAGEHUMANCAPITALPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHUMANCAPITALPROFILE.DELETE";
    private static final String VILLAGEHUMANCAPITALPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHUMANCAPITALPROFILE.GET";

    public VillageHumanCapitalProfileFacadeImpl(VillageHumanCapitalProfileServiceExt serviceExt, VillageHumanCapitalProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageHumanCapitalProfile entry in the system.
     *
     * @param VillageHumanCapitalProfileDTO The VillageHumanCapitalProfile information to be saved
     * @return The saved VillageHumanCapitalProfile data
     */
    @Override
    public VillageHumanCapitalProfileDTO save(VillageHumanCapitalProfileDTO VillageHumanCapitalProfileDTO) {
        log.debug("Entry - save(VillageHumanCapitalProfileDTO={})", VillageHumanCapitalProfileDTO);
        evaluator.evaluate(VILLAGEHUMANCAPITALPROFILE_CREATE, new HashMap<>());
        VillageHumanCapitalProfileDTO = preHookSave(VillageHumanCapitalProfileDTO);
        VillageHumanCapitalProfileDTO dto = serviceExt.save(VillageHumanCapitalProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageHumanCapitalProfile entry.
     *
     * @param VillageHumanCapitalProfileDTO The VillageHumanCapitalProfile information to be updated
     * @return The updated VillageHumanCapitalProfile data
     */
    @Override
    public VillageHumanCapitalProfileDTO update(VillageHumanCapitalProfileDTO VillageHumanCapitalProfileDTO) {
        log.debug("Entry - update(VillageHumanCapitalProfileDTO={})", VillageHumanCapitalProfileDTO);
        evaluator.evaluate(VILLAGEHUMANCAPITALPROFILE_UPDATE, new HashMap<>());
        VillageHumanCapitalProfileDTO = preHookUpdate(VillageHumanCapitalProfileDTO);
        VillageHumanCapitalProfileDTO dto = serviceExt.update(VillageHumanCapitalProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageHumanCapitalProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageHumanCapitalProfiles
     */
    @Override
    public PageDTO<VillageHumanCapitalProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEHUMANCAPITALPROFILE_GET, new HashMap<>());
        PageDTO<VillageHumanCapitalProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageHumanCapitalProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageHumanCapitalProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEHUMANCAPITALPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageHumanCapitalProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageHumanCapitalProfile by their ID.
     *
     * @param id The ID of the VillageHumanCapitalProfile to retrieve
     * @return The VillageHumanCapitalProfile with the specified ID
     */
    @Override
    public VillageHumanCapitalProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEHUMANCAPITALPROFILE_GET, new HashMap<>());
        VillageHumanCapitalProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageHumanCapitalProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHumanCapitalProfileDTO
     */
    @Override
    public Set<VillageHumanCapitalProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEHUMANCAPITALPROFILE_GET, new HashMap<>());
        Set<VillageHumanCapitalProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of villageYouthAspirationsList to a VillageHumanCapitalProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanCapitalProfile to add hobbies to
     * @param villageYouthAspirationsList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageYouthAspirationsToVillageHumanCapitalProfile(Long id, List<VillageYouthAspirationsDTO> villageYouthAspirationsList){
        serviceExt.addVillageYouthAspirationsToVillageHumanCapitalProfile(id,villageYouthAspirationsList);
    }
/**
     * Adds a list of villageSkillShortageTypeList to a VillageHumanCapitalProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanCapitalProfile to add hobbies to
     * @param villageSkillShortageTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageSkillShortageTypeToVillageHumanCapitalProfile(Long id, List<VillageSkillShortageTypeDTO> villageSkillShortageTypeList){
        serviceExt.addVillageSkillShortageTypeToVillageHumanCapitalProfile(id,villageSkillShortageTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageHumanCapitalProfileDTO postHookSave(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected VillageHumanCapitalProfileDTO preHookSave(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected VillageHumanCapitalProfileDTO postHookUpdate(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected VillageHumanCapitalProfileDTO preHookUpdate(VillageHumanCapitalProfileDTO VillageHumanCapitalProfileDTO) {
        return VillageHumanCapitalProfileDTO;
    }

    protected VillageHumanCapitalProfileDTO postHookDelete(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageHumanCapitalProfileDTO postHookGetById(VillageHumanCapitalProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageHumanCapitalProfileDTO> postHookGetAll(PageDTO<VillageHumanCapitalProfileDTO> result) {
        return result;
    }
}
