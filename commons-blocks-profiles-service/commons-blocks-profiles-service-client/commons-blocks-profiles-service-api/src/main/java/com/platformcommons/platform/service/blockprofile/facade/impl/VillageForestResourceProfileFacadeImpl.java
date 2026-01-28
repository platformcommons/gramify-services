package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageForestResourceProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageForestResourceProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageForestResourceProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageForestResourceProfileFacadeImpl implements VillageForestResourceProfileFacade {

    private final VillageForestResourceProfileServiceExt serviceExt;
    private final VillageForestResourceProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEFORESTRESOURCEPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFORESTRESOURCEPROFILE.CREATE";
    private static final String VILLAGEFORESTRESOURCEPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFORESTRESOURCEPROFILE.UPDATED";
    private static final String VILLAGEFORESTRESOURCEPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFORESTRESOURCEPROFILE.DELETE";
    private static final String VILLAGEFORESTRESOURCEPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFORESTRESOURCEPROFILE.GET";

    public VillageForestResourceProfileFacadeImpl(VillageForestResourceProfileServiceExt serviceExt, VillageForestResourceProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageForestResourceProfile entry in the system.
     *
     * @param VillageForestResourceProfileDTO The VillageForestResourceProfile information to be saved
     * @return The saved VillageForestResourceProfile data
     */
    @Override
    public VillageForestResourceProfileDTO save(VillageForestResourceProfileDTO VillageForestResourceProfileDTO) {
        log.debug("Entry - save(VillageForestResourceProfileDTO={})", VillageForestResourceProfileDTO);
        evaluator.evaluate(VILLAGEFORESTRESOURCEPROFILE_CREATE, new HashMap<>());
        VillageForestResourceProfileDTO = preHookSave(VillageForestResourceProfileDTO);
        VillageForestResourceProfileDTO dto = serviceExt.save(VillageForestResourceProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageForestResourceProfile entry.
     *
     * @param VillageForestResourceProfileDTO The VillageForestResourceProfile information to be updated
     * @return The updated VillageForestResourceProfile data
     */
    @Override
    public VillageForestResourceProfileDTO update(VillageForestResourceProfileDTO VillageForestResourceProfileDTO) {
        log.debug("Entry - update(VillageForestResourceProfileDTO={})", VillageForestResourceProfileDTO);
        evaluator.evaluate(VILLAGEFORESTRESOURCEPROFILE_UPDATE, new HashMap<>());
        VillageForestResourceProfileDTO = preHookUpdate(VillageForestResourceProfileDTO);
        VillageForestResourceProfileDTO dto = serviceExt.update(VillageForestResourceProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageForestResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageForestResourceProfiles
     */
    @Override
    public PageDTO<VillageForestResourceProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEFORESTRESOURCEPROFILE_GET, new HashMap<>());
        PageDTO<VillageForestResourceProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageForestResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageForestResourceProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEFORESTRESOURCEPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageForestResourceProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageForestResourceProfile by their ID.
     *
     * @param id The ID of the VillageForestResourceProfile to retrieve
     * @return The VillageForestResourceProfile with the specified ID
     */
    @Override
    public VillageForestResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEFORESTRESOURCEPROFILE_GET, new HashMap<>());
        VillageForestResourceProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageForestResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageForestResourceProfileDTO
     */
    @Override
    public Set<VillageForestResourceProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEFORESTRESOURCEPROFILE_GET, new HashMap<>());
        Set<VillageForestResourceProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of villageForestProduceTypeList to a VillageForestResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageForestResourceProfile to add hobbies to
     * @param villageForestProduceTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageForestProduceTypeToVillageForestResourceProfile(Long id, List<VillageForestProduceTypeDTO> villageForestProduceTypeList){
        serviceExt.addVillageForestProduceTypeToVillageForestResourceProfile(id,villageForestProduceTypeList);
    }
/**
     * Adds a list of villageCommonWildlifeList to a VillageForestResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageForestResourceProfile to add hobbies to
     * @param villageCommonWildlifeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageCommonWildlifeToVillageForestResourceProfile(Long id, List<VillageCommonWildlifeDTO> villageCommonWildlifeList){
        serviceExt.addVillageCommonWildlifeToVillageForestResourceProfile(id,villageCommonWildlifeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageForestResourceProfileDTO postHookSave(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected VillageForestResourceProfileDTO preHookSave(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected VillageForestResourceProfileDTO postHookUpdate(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected VillageForestResourceProfileDTO preHookUpdate(VillageForestResourceProfileDTO VillageForestResourceProfileDTO) {
        return VillageForestResourceProfileDTO;
    }

    protected VillageForestResourceProfileDTO postHookDelete(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageForestResourceProfileDTO postHookGetById(VillageForestResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageForestResourceProfileDTO> postHookGetAll(PageDTO<VillageForestResourceProfileDTO> result) {
        return result;
    }
}
