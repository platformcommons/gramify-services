package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageRoadConnectivityProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageRoadConnectivityProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageRoadConnectivityProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageRoadConnectivityProfileFacadeImpl implements VillageRoadConnectivityProfileFacade {

    private final VillageRoadConnectivityProfileServiceExt serviceExt;
    private final VillageRoadConnectivityProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEROADCONNECTIVITYPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEROADCONNECTIVITYPROFILE.CREATE";
    private static final String VILLAGEROADCONNECTIVITYPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEROADCONNECTIVITYPROFILE.UPDATED";
    private static final String VILLAGEROADCONNECTIVITYPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEROADCONNECTIVITYPROFILE.DELETE";
    private static final String VILLAGEROADCONNECTIVITYPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEROADCONNECTIVITYPROFILE.GET";

    public VillageRoadConnectivityProfileFacadeImpl(VillageRoadConnectivityProfileServiceExt serviceExt, VillageRoadConnectivityProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageRoadConnectivityProfile entry in the system.
     *
     * @param VillageRoadConnectivityProfileDTO The VillageRoadConnectivityProfile information to be saved
     * @return The saved VillageRoadConnectivityProfile data
     */
    @Override
    public VillageRoadConnectivityProfileDTO save(VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfileDTO) {
        log.debug("Entry - save(VillageRoadConnectivityProfileDTO={})", VillageRoadConnectivityProfileDTO);
        evaluator.evaluate(VILLAGEROADCONNECTIVITYPROFILE_CREATE, new HashMap<>());
        VillageRoadConnectivityProfileDTO = preHookSave(VillageRoadConnectivityProfileDTO);
        VillageRoadConnectivityProfileDTO dto = serviceExt.save(VillageRoadConnectivityProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageRoadConnectivityProfile entry.
     *
     * @param VillageRoadConnectivityProfileDTO The VillageRoadConnectivityProfile information to be updated
     * @return The updated VillageRoadConnectivityProfile data
     */
    @Override
    public VillageRoadConnectivityProfileDTO update(VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfileDTO) {
        log.debug("Entry - update(VillageRoadConnectivityProfileDTO={})", VillageRoadConnectivityProfileDTO);
        evaluator.evaluate(VILLAGEROADCONNECTIVITYPROFILE_UPDATE, new HashMap<>());
        VillageRoadConnectivityProfileDTO = preHookUpdate(VillageRoadConnectivityProfileDTO);
        VillageRoadConnectivityProfileDTO dto = serviceExt.update(VillageRoadConnectivityProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageRoadConnectivityProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageRoadConnectivityProfiles
     */
    @Override
    public PageDTO<VillageRoadConnectivityProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEROADCONNECTIVITYPROFILE_GET, new HashMap<>());
        PageDTO<VillageRoadConnectivityProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageRoadConnectivityProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageRoadConnectivityProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEROADCONNECTIVITYPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageRoadConnectivityProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageRoadConnectivityProfile by their ID.
     *
     * @param id The ID of the VillageRoadConnectivityProfile to retrieve
     * @return The VillageRoadConnectivityProfile with the specified ID
     */
    @Override
    public VillageRoadConnectivityProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEROADCONNECTIVITYPROFILE_GET, new HashMap<>());
        VillageRoadConnectivityProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageRoadConnectivityProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageRoadConnectivityProfileDTO
     */
    @Override
    public Set<VillageRoadConnectivityProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEROADCONNECTIVITYPROFILE_GET, new HashMap<>());
        Set<VillageRoadConnectivityProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageRoadConnectivityProfileDTO postHookSave(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected VillageRoadConnectivityProfileDTO preHookSave(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected VillageRoadConnectivityProfileDTO postHookUpdate(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected VillageRoadConnectivityProfileDTO preHookUpdate(VillageRoadConnectivityProfileDTO VillageRoadConnectivityProfileDTO) {
        return VillageRoadConnectivityProfileDTO;
    }

    protected VillageRoadConnectivityProfileDTO postHookDelete(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageRoadConnectivityProfileDTO postHookGetById(VillageRoadConnectivityProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageRoadConnectivityProfileDTO> postHookGetAll(PageDTO<VillageRoadConnectivityProfileDTO> result) {
        return result;
    }
}
