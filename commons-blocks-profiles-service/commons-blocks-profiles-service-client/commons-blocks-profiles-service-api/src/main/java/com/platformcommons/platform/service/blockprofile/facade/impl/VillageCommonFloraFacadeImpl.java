package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageCommonFloraFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageCommonFloraProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageCommonFloraServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageCommonFloraFacadeImpl implements VillageCommonFloraFacade {

    private final VillageCommonFloraServiceExt serviceExt;
    private final VillageCommonFloraProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGECOMMONFLORA_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONFLORA.CREATE";
    private static final String VILLAGECOMMONFLORA_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONFLORA.UPDATED";
    private static final String VILLAGECOMMONFLORA_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONFLORA.DELETE";
    private static final String VILLAGECOMMONFLORA_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECOMMONFLORA.GET";

    public VillageCommonFloraFacadeImpl(VillageCommonFloraServiceExt serviceExt, VillageCommonFloraProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageCommonFlora entry in the system.
     *
     * @param VillageCommonFloraDTO The VillageCommonFlora information to be saved
     * @return The saved VillageCommonFlora data
     */
    @Override
    public VillageCommonFloraDTO save(VillageCommonFloraDTO VillageCommonFloraDTO) {
        log.debug("Entry - save(VillageCommonFloraDTO={})", VillageCommonFloraDTO);
        evaluator.evaluate(VILLAGECOMMONFLORA_CREATE, new HashMap<>());
        VillageCommonFloraDTO = preHookSave(VillageCommonFloraDTO);
        VillageCommonFloraDTO dto = serviceExt.save(VillageCommonFloraDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageCommonFlora entry.
     *
     * @param VillageCommonFloraDTO The VillageCommonFlora information to be updated
     * @return The updated VillageCommonFlora data
     */
    @Override
    public VillageCommonFloraDTO update(VillageCommonFloraDTO VillageCommonFloraDTO) {
        log.debug("Entry - update(VillageCommonFloraDTO={})", VillageCommonFloraDTO);
        evaluator.evaluate(VILLAGECOMMONFLORA_UPDATE, new HashMap<>());
        VillageCommonFloraDTO = preHookUpdate(VillageCommonFloraDTO);
        VillageCommonFloraDTO dto = serviceExt.update(VillageCommonFloraDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageCommonFloras.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCommonFloras
     */
    @Override
    public PageDTO<VillageCommonFloraDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGECOMMONFLORA_GET, new HashMap<>());
        PageDTO<VillageCommonFloraDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageCommonFlora by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCommonFlora to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGECOMMONFLORA_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageCommonFloraDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageCommonFlora by their ID.
     *
     * @param id The ID of the VillageCommonFlora to retrieve
     * @return The VillageCommonFlora with the specified ID
     */
    @Override
    public VillageCommonFloraDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGECOMMONFLORA_GET, new HashMap<>());
        VillageCommonFloraDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageCommonFloras by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCommonFloraDTO
     */
    @Override
    public Set<VillageCommonFloraDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGECOMMONFLORA_GET, new HashMap<>());
        Set<VillageCommonFloraDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCommonFloraDTO postHookSave(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected VillageCommonFloraDTO preHookSave(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected VillageCommonFloraDTO postHookUpdate(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected VillageCommonFloraDTO preHookUpdate(VillageCommonFloraDTO VillageCommonFloraDTO) {
        return VillageCommonFloraDTO;
    }

    protected VillageCommonFloraDTO postHookDelete(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCommonFloraDTO postHookGetById(VillageCommonFloraDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCommonFloraDTO> postHookGetAll(PageDTO<VillageCommonFloraDTO> result) {
        return result;
    }
}
