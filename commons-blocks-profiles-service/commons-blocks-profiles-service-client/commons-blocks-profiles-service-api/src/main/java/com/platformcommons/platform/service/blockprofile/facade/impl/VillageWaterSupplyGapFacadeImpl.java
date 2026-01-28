package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageWaterSupplyGapFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageWaterSupplyGapProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageWaterSupplyGapServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageWaterSupplyGapFacadeImpl implements VillageWaterSupplyGapFacade {

    private final VillageWaterSupplyGapServiceExt serviceExt;
    private final VillageWaterSupplyGapProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEWATERSUPPLYGAP_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERSUPPLYGAP.CREATE";
    private static final String VILLAGEWATERSUPPLYGAP_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERSUPPLYGAP.UPDATED";
    private static final String VILLAGEWATERSUPPLYGAP_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERSUPPLYGAP.DELETE";
    private static final String VILLAGEWATERSUPPLYGAP_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERSUPPLYGAP.GET";

    public VillageWaterSupplyGapFacadeImpl(VillageWaterSupplyGapServiceExt serviceExt, VillageWaterSupplyGapProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageWaterSupplyGap entry in the system.
     *
     * @param VillageWaterSupplyGapDTO The VillageWaterSupplyGap information to be saved
     * @return The saved VillageWaterSupplyGap data
     */
    @Override
    public VillageWaterSupplyGapDTO save(VillageWaterSupplyGapDTO VillageWaterSupplyGapDTO) {
        log.debug("Entry - save(VillageWaterSupplyGapDTO={})", VillageWaterSupplyGapDTO);
        evaluator.evaluate(VILLAGEWATERSUPPLYGAP_CREATE, new HashMap<>());
        VillageWaterSupplyGapDTO = preHookSave(VillageWaterSupplyGapDTO);
        VillageWaterSupplyGapDTO dto = serviceExt.save(VillageWaterSupplyGapDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageWaterSupplyGap entry.
     *
     * @param VillageWaterSupplyGapDTO The VillageWaterSupplyGap information to be updated
     * @return The updated VillageWaterSupplyGap data
     */
    @Override
    public VillageWaterSupplyGapDTO update(VillageWaterSupplyGapDTO VillageWaterSupplyGapDTO) {
        log.debug("Entry - update(VillageWaterSupplyGapDTO={})", VillageWaterSupplyGapDTO);
        evaluator.evaluate(VILLAGEWATERSUPPLYGAP_UPDATE, new HashMap<>());
        VillageWaterSupplyGapDTO = preHookUpdate(VillageWaterSupplyGapDTO);
        VillageWaterSupplyGapDTO dto = serviceExt.update(VillageWaterSupplyGapDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageWaterSupplyGaps.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageWaterSupplyGaps
     */
    @Override
    public PageDTO<VillageWaterSupplyGapDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEWATERSUPPLYGAP_GET, new HashMap<>());
        PageDTO<VillageWaterSupplyGapDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageWaterSupplyGap by their ID with a specified reason.
     *
     * @param id     The ID of the VillageWaterSupplyGap to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEWATERSUPPLYGAP_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageWaterSupplyGapDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageWaterSupplyGap by their ID.
     *
     * @param id The ID of the VillageWaterSupplyGap to retrieve
     * @return The VillageWaterSupplyGap with the specified ID
     */
    @Override
    public VillageWaterSupplyGapDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEWATERSUPPLYGAP_GET, new HashMap<>());
        VillageWaterSupplyGapDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageWaterSupplyGaps by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageWaterSupplyGapDTO
     */
    @Override
    public Set<VillageWaterSupplyGapDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEWATERSUPPLYGAP_GET, new HashMap<>());
        Set<VillageWaterSupplyGapDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageWaterSupplyGapDTO postHookSave(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected VillageWaterSupplyGapDTO preHookSave(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected VillageWaterSupplyGapDTO postHookUpdate(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected VillageWaterSupplyGapDTO preHookUpdate(VillageWaterSupplyGapDTO VillageWaterSupplyGapDTO) {
        return VillageWaterSupplyGapDTO;
    }

    protected VillageWaterSupplyGapDTO postHookDelete(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageWaterSupplyGapDTO postHookGetById(VillageWaterSupplyGapDTO dto) {
        return dto;
    }

    protected PageDTO<VillageWaterSupplyGapDTO> postHookGetAll(PageDTO<VillageWaterSupplyGapDTO> result) {
        return result;
    }
}
