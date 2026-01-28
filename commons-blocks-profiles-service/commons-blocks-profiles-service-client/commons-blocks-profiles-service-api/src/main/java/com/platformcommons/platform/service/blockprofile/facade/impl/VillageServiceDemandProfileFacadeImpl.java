package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageServiceDemandProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageServiceDemandProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageServiceDemandProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageServiceDemandProfileFacadeImpl implements VillageServiceDemandProfileFacade {

    private final VillageServiceDemandProfileServiceExt serviceExt;
    private final VillageServiceDemandProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGESERVICEDEMANDPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESERVICEDEMANDPROFILE.CREATE";
    private static final String VILLAGESERVICEDEMANDPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESERVICEDEMANDPROFILE.UPDATED";
    private static final String VILLAGESERVICEDEMANDPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESERVICEDEMANDPROFILE.DELETE";
    private static final String VILLAGESERVICEDEMANDPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESERVICEDEMANDPROFILE.GET";

    public VillageServiceDemandProfileFacadeImpl(VillageServiceDemandProfileServiceExt serviceExt, VillageServiceDemandProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageServiceDemandProfile entry in the system.
     *
     * @param VillageServiceDemandProfileDTO The VillageServiceDemandProfile information to be saved
     * @return The saved VillageServiceDemandProfile data
     */
    @Override
    public VillageServiceDemandProfileDTO save(VillageServiceDemandProfileDTO VillageServiceDemandProfileDTO) {
        log.debug("Entry - save(VillageServiceDemandProfileDTO={})", VillageServiceDemandProfileDTO);
        evaluator.evaluate(VILLAGESERVICEDEMANDPROFILE_CREATE, new HashMap<>());
        VillageServiceDemandProfileDTO = preHookSave(VillageServiceDemandProfileDTO);
        VillageServiceDemandProfileDTO dto = serviceExt.save(VillageServiceDemandProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageServiceDemandProfile entry.
     *
     * @param VillageServiceDemandProfileDTO The VillageServiceDemandProfile information to be updated
     * @return The updated VillageServiceDemandProfile data
     */
    @Override
    public VillageServiceDemandProfileDTO update(VillageServiceDemandProfileDTO VillageServiceDemandProfileDTO) {
        log.debug("Entry - update(VillageServiceDemandProfileDTO={})", VillageServiceDemandProfileDTO);
        evaluator.evaluate(VILLAGESERVICEDEMANDPROFILE_UPDATE, new HashMap<>());
        VillageServiceDemandProfileDTO = preHookUpdate(VillageServiceDemandProfileDTO);
        VillageServiceDemandProfileDTO dto = serviceExt.update(VillageServiceDemandProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageServiceDemandProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageServiceDemandProfiles
     */
    @Override
    public PageDTO<VillageServiceDemandProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGESERVICEDEMANDPROFILE_GET, new HashMap<>());
        PageDTO<VillageServiceDemandProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageServiceDemandProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageServiceDemandProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGESERVICEDEMANDPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageServiceDemandProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageServiceDemandProfile by their ID.
     *
     * @param id The ID of the VillageServiceDemandProfile to retrieve
     * @return The VillageServiceDemandProfile with the specified ID
     */
    @Override
    public VillageServiceDemandProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGESERVICEDEMANDPROFILE_GET, new HashMap<>());
        VillageServiceDemandProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageServiceDemandProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageServiceDemandProfileDTO
     */
    @Override
    public Set<VillageServiceDemandProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGESERVICEDEMANDPROFILE_GET, new HashMap<>());
        Set<VillageServiceDemandProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of wherePeopleGoForRepairsList to a VillageServiceDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageServiceDemandProfile to add hobbies to
     * @param wherePeopleGoForRepairsList  to be added
     * @since 1.0.0
     */
    @Override
    public void addWherePeopleGoForRepairsToVillageServiceDemandProfile(Long id, List<WherePeopleGoForRepairsDTO> wherePeopleGoForRepairsList){
        serviceExt.addWherePeopleGoForRepairsToVillageServiceDemandProfile(id,wherePeopleGoForRepairsList);
    }
/**
     * Adds a list of wherePeopleGoForCommonIllnessList to a VillageServiceDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageServiceDemandProfile to add hobbies to
     * @param wherePeopleGoForCommonIllnessList  to be added
     * @since 1.0.0
     */
    @Override
    public void addWherePeopleGoForCommonIllnessToVillageServiceDemandProfile(Long id, List<WherePeopleGoForCommonIllnessDTO> wherePeopleGoForCommonIllnessList){
        serviceExt.addWherePeopleGoForCommonIllnessToVillageServiceDemandProfile(id,wherePeopleGoForCommonIllnessList);
    }
/**
     * Adds a list of commonRepairNeedList to a VillageServiceDemandProfile identified by their ID.
     *
     * @param id               The ID of the VillageServiceDemandProfile to add hobbies to
     * @param commonRepairNeedList  to be added
     * @since 1.0.0
     */
    @Override
    public void addCommonRepairNeedToVillageServiceDemandProfile(Long id, List<CommonRepairNeedDTO> commonRepairNeedList){
        serviceExt.addCommonRepairNeedToVillageServiceDemandProfile(id,commonRepairNeedList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageServiceDemandProfileDTO postHookSave(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected VillageServiceDemandProfileDTO preHookSave(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected VillageServiceDemandProfileDTO postHookUpdate(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected VillageServiceDemandProfileDTO preHookUpdate(VillageServiceDemandProfileDTO VillageServiceDemandProfileDTO) {
        return VillageServiceDemandProfileDTO;
    }

    protected VillageServiceDemandProfileDTO postHookDelete(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageServiceDemandProfileDTO postHookGetById(VillageServiceDemandProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageServiceDemandProfileDTO> postHookGetAll(PageDTO<VillageServiceDemandProfileDTO> result) {
        return result;
    }
}
