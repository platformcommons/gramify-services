package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageYouthAspirationsFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageYouthAspirationsProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageYouthAspirationsServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageYouthAspirationsFacadeImpl implements VillageYouthAspirationsFacade {

    private final VillageYouthAspirationsServiceExt serviceExt;
    private final VillageYouthAspirationsProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEYOUTHASPIRATIONS_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEYOUTHASPIRATIONS.CREATE";
    private static final String VILLAGEYOUTHASPIRATIONS_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEYOUTHASPIRATIONS.UPDATED";
    private static final String VILLAGEYOUTHASPIRATIONS_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEYOUTHASPIRATIONS.DELETE";
    private static final String VILLAGEYOUTHASPIRATIONS_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEYOUTHASPIRATIONS.GET";

    public VillageYouthAspirationsFacadeImpl(VillageYouthAspirationsServiceExt serviceExt, VillageYouthAspirationsProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageYouthAspirations entry in the system.
     *
     * @param VillageYouthAspirationsDTO The VillageYouthAspirations information to be saved
     * @return The saved VillageYouthAspirations data
     */
    @Override
    public VillageYouthAspirationsDTO save(VillageYouthAspirationsDTO VillageYouthAspirationsDTO) {
        log.debug("Entry - save(VillageYouthAspirationsDTO={})", VillageYouthAspirationsDTO);
        evaluator.evaluate(VILLAGEYOUTHASPIRATIONS_CREATE, new HashMap<>());
        VillageYouthAspirationsDTO = preHookSave(VillageYouthAspirationsDTO);
        VillageYouthAspirationsDTO dto = serviceExt.save(VillageYouthAspirationsDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageYouthAspirations entry.
     *
     * @param VillageYouthAspirationsDTO The VillageYouthAspirations information to be updated
     * @return The updated VillageYouthAspirations data
     */
    @Override
    public VillageYouthAspirationsDTO update(VillageYouthAspirationsDTO VillageYouthAspirationsDTO) {
        log.debug("Entry - update(VillageYouthAspirationsDTO={})", VillageYouthAspirationsDTO);
        evaluator.evaluate(VILLAGEYOUTHASPIRATIONS_UPDATE, new HashMap<>());
        VillageYouthAspirationsDTO = preHookUpdate(VillageYouthAspirationsDTO);
        VillageYouthAspirationsDTO dto = serviceExt.update(VillageYouthAspirationsDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageYouthAspirationss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageYouthAspirationss
     */
    @Override
    public PageDTO<VillageYouthAspirationsDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEYOUTHASPIRATIONS_GET, new HashMap<>());
        PageDTO<VillageYouthAspirationsDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageYouthAspirations by their ID with a specified reason.
     *
     * @param id     The ID of the VillageYouthAspirations to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEYOUTHASPIRATIONS_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageYouthAspirationsDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageYouthAspirations by their ID.
     *
     * @param id The ID of the VillageYouthAspirations to retrieve
     * @return The VillageYouthAspirations with the specified ID
     */
    @Override
    public VillageYouthAspirationsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEYOUTHASPIRATIONS_GET, new HashMap<>());
        VillageYouthAspirationsDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageYouthAspirationss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageYouthAspirationsDTO
     */
    @Override
    public Set<VillageYouthAspirationsDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEYOUTHASPIRATIONS_GET, new HashMap<>());
        Set<VillageYouthAspirationsDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageYouthAspirationsDTO postHookSave(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected VillageYouthAspirationsDTO preHookSave(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected VillageYouthAspirationsDTO postHookUpdate(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected VillageYouthAspirationsDTO preHookUpdate(VillageYouthAspirationsDTO VillageYouthAspirationsDTO) {
        return VillageYouthAspirationsDTO;
    }

    protected VillageYouthAspirationsDTO postHookDelete(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageYouthAspirationsDTO postHookGetById(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected PageDTO<VillageYouthAspirationsDTO> postHookGetAll(PageDTO<VillageYouthAspirationsDTO> result) {
        return result;
    }
}
