package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageWaterResourceProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageWaterResourceProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageWaterResourceProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageWaterResourceProfileFacadeImpl implements VillageWaterResourceProfileFacade {

    private final VillageWaterResourceProfileServiceExt serviceExt;
    private final VillageWaterResourceProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEWATERRESOURCEPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERRESOURCEPROFILE.CREATE";
    private static final String VILLAGEWATERRESOURCEPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERRESOURCEPROFILE.UPDATED";
    private static final String VILLAGEWATERRESOURCEPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERRESOURCEPROFILE.DELETE";
    private static final String VILLAGEWATERRESOURCEPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERRESOURCEPROFILE.GET";

    public VillageWaterResourceProfileFacadeImpl(VillageWaterResourceProfileServiceExt serviceExt, VillageWaterResourceProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageWaterResourceProfile entry in the system.
     *
     * @param VillageWaterResourceProfileDTO The VillageWaterResourceProfile information to be saved
     * @return The saved VillageWaterResourceProfile data
     */
    @Override
    public VillageWaterResourceProfileDTO save(VillageWaterResourceProfileDTO VillageWaterResourceProfileDTO) {
        log.debug("Entry - save(VillageWaterResourceProfileDTO={})", VillageWaterResourceProfileDTO);
        evaluator.evaluate(VILLAGEWATERRESOURCEPROFILE_CREATE, new HashMap<>());
        VillageWaterResourceProfileDTO = preHookSave(VillageWaterResourceProfileDTO);
        VillageWaterResourceProfileDTO dto = serviceExt.save(VillageWaterResourceProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageWaterResourceProfile entry.
     *
     * @param VillageWaterResourceProfileDTO The VillageWaterResourceProfile information to be updated
     * @return The updated VillageWaterResourceProfile data
     */
    @Override
    public VillageWaterResourceProfileDTO update(VillageWaterResourceProfileDTO VillageWaterResourceProfileDTO) {
        log.debug("Entry - update(VillageWaterResourceProfileDTO={})", VillageWaterResourceProfileDTO);
        evaluator.evaluate(VILLAGEWATERRESOURCEPROFILE_UPDATE, new HashMap<>());
        VillageWaterResourceProfileDTO = preHookUpdate(VillageWaterResourceProfileDTO);
        VillageWaterResourceProfileDTO dto = serviceExt.update(VillageWaterResourceProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageWaterResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageWaterResourceProfiles
     */
    @Override
    public PageDTO<VillageWaterResourceProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEWATERRESOURCEPROFILE_GET, new HashMap<>());
        PageDTO<VillageWaterResourceProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageWaterResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageWaterResourceProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEWATERRESOURCEPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageWaterResourceProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageWaterResourceProfile by their ID.
     *
     * @param id The ID of the VillageWaterResourceProfile to retrieve
     * @return The VillageWaterResourceProfile with the specified ID
     */
    @Override
    public VillageWaterResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEWATERRESOURCEPROFILE_GET, new HashMap<>());
        VillageWaterResourceProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageWaterResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageWaterResourceProfileDTO
     */
    @Override
    public Set<VillageWaterResourceProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEWATERRESOURCEPROFILE_GET, new HashMap<>());
        Set<VillageWaterResourceProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of villageIrrigationSystemTypeList to a VillageWaterResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageWaterResourceProfile to add hobbies to
     * @param villageIrrigationSystemTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageIrrigationSystemTypeToVillageWaterResourceProfile(Long id, List<VillageIrrigationSystemTypeDTO> villageIrrigationSystemTypeList){
        serviceExt.addVillageIrrigationSystemTypeToVillageWaterResourceProfile(id,villageIrrigationSystemTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageWaterResourceProfileDTO postHookSave(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected VillageWaterResourceProfileDTO preHookSave(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected VillageWaterResourceProfileDTO postHookUpdate(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected VillageWaterResourceProfileDTO preHookUpdate(VillageWaterResourceProfileDTO VillageWaterResourceProfileDTO) {
        return VillageWaterResourceProfileDTO;
    }

    protected VillageWaterResourceProfileDTO postHookDelete(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageWaterResourceProfileDTO postHookGetById(VillageWaterResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageWaterResourceProfileDTO> postHookGetAll(PageDTO<VillageWaterResourceProfileDTO> result) {
        return result;
    }
}
