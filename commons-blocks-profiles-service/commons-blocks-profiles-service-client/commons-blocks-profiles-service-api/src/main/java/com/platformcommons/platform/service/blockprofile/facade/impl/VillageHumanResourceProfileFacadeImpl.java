package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageHumanResourceProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageHumanResourceProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageHumanResourceProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageHumanResourceProfileFacadeImpl implements VillageHumanResourceProfileFacade {

    private final VillageHumanResourceProfileServiceExt serviceExt;
    private final VillageHumanResourceProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEHUMANRESOURCEPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHUMANRESOURCEPROFILE.CREATE";
    private static final String VILLAGEHUMANRESOURCEPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHUMANRESOURCEPROFILE.UPDATED";
    private static final String VILLAGEHUMANRESOURCEPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHUMANRESOURCEPROFILE.DELETE";
    private static final String VILLAGEHUMANRESOURCEPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEHUMANRESOURCEPROFILE.GET";

    public VillageHumanResourceProfileFacadeImpl(VillageHumanResourceProfileServiceExt serviceExt, VillageHumanResourceProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageHumanResourceProfile entry in the system.
     *
     * @param VillageHumanResourceProfileDTO The VillageHumanResourceProfile information to be saved
     * @return The saved VillageHumanResourceProfile data
     */
    @Override
    public VillageHumanResourceProfileDTO save(VillageHumanResourceProfileDTO VillageHumanResourceProfileDTO) {
        log.debug("Entry - save(VillageHumanResourceProfileDTO={})", VillageHumanResourceProfileDTO);
        evaluator.evaluate(VILLAGEHUMANRESOURCEPROFILE_CREATE, new HashMap<>());
        VillageHumanResourceProfileDTO = preHookSave(VillageHumanResourceProfileDTO);
        VillageHumanResourceProfileDTO dto = serviceExt.save(VillageHumanResourceProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageHumanResourceProfile entry.
     *
     * @param VillageHumanResourceProfileDTO The VillageHumanResourceProfile information to be updated
     * @return The updated VillageHumanResourceProfile data
     */
    @Override
    public VillageHumanResourceProfileDTO update(VillageHumanResourceProfileDTO VillageHumanResourceProfileDTO) {
        log.debug("Entry - update(VillageHumanResourceProfileDTO={})", VillageHumanResourceProfileDTO);
        evaluator.evaluate(VILLAGEHUMANRESOURCEPROFILE_UPDATE, new HashMap<>());
        VillageHumanResourceProfileDTO = preHookUpdate(VillageHumanResourceProfileDTO);
        VillageHumanResourceProfileDTO dto = serviceExt.update(VillageHumanResourceProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageHumanResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageHumanResourceProfiles
     */
    @Override
    public PageDTO<VillageHumanResourceProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEHUMANRESOURCEPROFILE_GET, new HashMap<>());
        PageDTO<VillageHumanResourceProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageHumanResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageHumanResourceProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEHUMANRESOURCEPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageHumanResourceProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageHumanResourceProfile by their ID.
     *
     * @param id The ID of the VillageHumanResourceProfile to retrieve
     * @return The VillageHumanResourceProfile with the specified ID
     */
    @Override
    public VillageHumanResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEHUMANRESOURCEPROFILE_GET, new HashMap<>());
        VillageHumanResourceProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageHumanResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageHumanResourceProfileDTO
     */
    @Override
    public Set<VillageHumanResourceProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEHUMANRESOURCEPROFILE_GET, new HashMap<>());
        Set<VillageHumanResourceProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of hHSkilledWorkerTypeList to a VillageHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
     * @param hHSkilledWorkerTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addHHSkilledWorkerTypeToVillageHumanResourceProfile(Long id, List<HHSkilledWorkerTypeDTO> hHSkilledWorkerTypeList){
        serviceExt.addHHSkilledWorkerTypeToVillageHumanResourceProfile(id,hHSkilledWorkerTypeList);
    }
/**
     * Adds a list of emergingEnterpriseTypeList to a VillageHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
     * @param emergingEnterpriseTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addEmergingEnterpriseTypeToVillageHumanResourceProfile(Long id, List<EmergingEnterpriseTypeDTO> emergingEnterpriseTypeList){
        serviceExt.addEmergingEnterpriseTypeToVillageHumanResourceProfile(id,emergingEnterpriseTypeList);
    }
/**
     * Adds a list of mainSkilledTradesPresentList to a VillageHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
     * @param mainSkilledTradesPresentList  to be added
     * @since 1.0.0
     */
    @Override
    public void addMainSkilledTradesPresentToVillageHumanResourceProfile(Long id, List<MainSkilledTradesPresentDTO> mainSkilledTradesPresentList){
        serviceExt.addMainSkilledTradesPresentToVillageHumanResourceProfile(id,mainSkilledTradesPresentList);
    }
/**
     * Adds a list of artisanTypeList to a VillageHumanResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageHumanResourceProfile to add hobbies to
     * @param artisanTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addArtisanTypeToVillageHumanResourceProfile(Long id, List<ArtisanTypeDTO> artisanTypeList){
        serviceExt.addArtisanTypeToVillageHumanResourceProfile(id,artisanTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageHumanResourceProfileDTO postHookSave(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected VillageHumanResourceProfileDTO preHookSave(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected VillageHumanResourceProfileDTO postHookUpdate(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected VillageHumanResourceProfileDTO preHookUpdate(VillageHumanResourceProfileDTO VillageHumanResourceProfileDTO) {
        return VillageHumanResourceProfileDTO;
    }

    protected VillageHumanResourceProfileDTO postHookDelete(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageHumanResourceProfileDTO postHookGetById(VillageHumanResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageHumanResourceProfileDTO> postHookGetAll(PageDTO<VillageHumanResourceProfileDTO> result) {
        return result;
    }
}
