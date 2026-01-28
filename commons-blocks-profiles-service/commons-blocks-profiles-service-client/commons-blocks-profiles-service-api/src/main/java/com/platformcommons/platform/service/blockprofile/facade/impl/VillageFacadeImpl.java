package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageFacadeImpl implements VillageFacade {

    private final VillageServiceExt serviceExt;
    private final VillageProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGE.CREATE";
    private static final String VILLAGE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGE.UPDATED";
    private static final String VILLAGE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGE.DELETE";
    private static final String VILLAGE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGE.GET";

    public VillageFacadeImpl(VillageServiceExt serviceExt, VillageProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new Village entry in the system.
     *
     * @param VillageDTO The Village information to be saved
     * @return The saved Village data
     */
    @Override
    public VillageDTO save(VillageDTO VillageDTO) {
        log.debug("Entry - save(VillageDTO={})", VillageDTO);
        evaluator.evaluate(VILLAGE_CREATE, new HashMap<>());
        VillageDTO = preHookSave(VillageDTO);
        VillageDTO dto = serviceExt.save(VillageDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing Village entry.
     *
     * @param VillageDTO The Village information to be updated
     * @return The updated Village data
     */
    @Override
    public VillageDTO update(VillageDTO VillageDTO) {
        log.debug("Entry - update(VillageDTO={})", VillageDTO);
        evaluator.evaluate(VILLAGE_UPDATE, new HashMap<>());
        VillageDTO = preHookUpdate(VillageDTO);
        VillageDTO dto = serviceExt.update(VillageDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of Villages.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of Villages
     */
    @Override
    public PageDTO<VillageDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGE_GET, new HashMap<>());
        PageDTO<VillageDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a Village by their ID with a specified reason.
     *
     * @param id     The ID of the Village to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a Village by their ID.
     *
     * @param id The ID of the Village to retrieve
     * @return The Village with the specified ID
     */
    @Override
    public VillageDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGE_GET, new HashMap<>());
        VillageDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all Villages by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageDTO
     */
    @Override
    public Set<VillageDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGE_GET, new HashMap<>());
        Set<VillageDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageDTO postHookSave(VillageDTO dto) {
        return dto;
    }

    protected VillageDTO preHookSave(VillageDTO dto) {
        return dto;
    }

    protected VillageDTO postHookUpdate(VillageDTO dto) {
        return dto;
    }

    protected VillageDTO preHookUpdate(VillageDTO VillageDTO) {
        return VillageDTO;
    }

    protected VillageDTO postHookDelete(VillageDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageDTO postHookGetById(VillageDTO dto) {
        return dto;
    }

    protected PageDTO<VillageDTO> postHookGetAll(PageDTO<VillageDTO> result) {
        return result;
    }
}
