package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageDigitalConnectivityProfiFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageDigitalConnectivityProfiProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageDigitalConnectivityProfiServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageDigitalConnectivityProfiFacadeImpl implements VillageDigitalConnectivityProfiFacade {

    private final VillageDigitalConnectivityProfiServiceExt serviceExt;
    private final VillageDigitalConnectivityProfiProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEDIGITALCONNECTIVITYPROFI_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEDIGITALCONNECTIVITYPROFI.CREATE";
    private static final String VILLAGEDIGITALCONNECTIVITYPROFI_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEDIGITALCONNECTIVITYPROFI.UPDATED";
    private static final String VILLAGEDIGITALCONNECTIVITYPROFI_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEDIGITALCONNECTIVITYPROFI.DELETE";
    private static final String VILLAGEDIGITALCONNECTIVITYPROFI_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEDIGITALCONNECTIVITYPROFI.GET";

    public VillageDigitalConnectivityProfiFacadeImpl(VillageDigitalConnectivityProfiServiceExt serviceExt, VillageDigitalConnectivityProfiProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageDigitalConnectivityProfi entry in the system.
     *
     * @param VillageDigitalConnectivityProfiDTO The VillageDigitalConnectivityProfi information to be saved
     * @return The saved VillageDigitalConnectivityProfi data
     */
    @Override
    public VillageDigitalConnectivityProfiDTO save(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfiDTO) {
        log.debug("Entry - save(VillageDigitalConnectivityProfiDTO={})", VillageDigitalConnectivityProfiDTO);
        evaluator.evaluate(VILLAGEDIGITALCONNECTIVITYPROFI_CREATE, new HashMap<>());
        VillageDigitalConnectivityProfiDTO = preHookSave(VillageDigitalConnectivityProfiDTO);
        VillageDigitalConnectivityProfiDTO dto = serviceExt.save(VillageDigitalConnectivityProfiDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageDigitalConnectivityProfi entry.
     *
     * @param VillageDigitalConnectivityProfiDTO The VillageDigitalConnectivityProfi information to be updated
     * @return The updated VillageDigitalConnectivityProfi data
     */
    @Override
    public VillageDigitalConnectivityProfiDTO update(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfiDTO) {
        log.debug("Entry - update(VillageDigitalConnectivityProfiDTO={})", VillageDigitalConnectivityProfiDTO);
        evaluator.evaluate(VILLAGEDIGITALCONNECTIVITYPROFI_UPDATE, new HashMap<>());
        VillageDigitalConnectivityProfiDTO = preHookUpdate(VillageDigitalConnectivityProfiDTO);
        VillageDigitalConnectivityProfiDTO dto = serviceExt.update(VillageDigitalConnectivityProfiDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageDigitalConnectivityProfis.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageDigitalConnectivityProfis
     */
    @Override
    public PageDTO<VillageDigitalConnectivityProfiDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEDIGITALCONNECTIVITYPROFI_GET, new HashMap<>());
        PageDTO<VillageDigitalConnectivityProfiDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageDigitalConnectivityProfi by their ID with a specified reason.
     *
     * @param id     The ID of the VillageDigitalConnectivityProfi to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEDIGITALCONNECTIVITYPROFI_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageDigitalConnectivityProfiDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageDigitalConnectivityProfi by their ID.
     *
     * @param id The ID of the VillageDigitalConnectivityProfi to retrieve
     * @return The VillageDigitalConnectivityProfi with the specified ID
     */
    @Override
    public VillageDigitalConnectivityProfiDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEDIGITALCONNECTIVITYPROFI_GET, new HashMap<>());
        VillageDigitalConnectivityProfiDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageDigitalConnectivityProfis by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageDigitalConnectivityProfiDTO
     */
    @Override
    public Set<VillageDigitalConnectivityProfiDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEDIGITALCONNECTIVITYPROFI_GET, new HashMap<>());
        Set<VillageDigitalConnectivityProfiDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageDigitalConnectivityProfiDTO postHookSave(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected VillageDigitalConnectivityProfiDTO preHookSave(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected VillageDigitalConnectivityProfiDTO postHookUpdate(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected VillageDigitalConnectivityProfiDTO preHookUpdate(VillageDigitalConnectivityProfiDTO VillageDigitalConnectivityProfiDTO) {
        return VillageDigitalConnectivityProfiDTO;
    }

    protected VillageDigitalConnectivityProfiDTO postHookDelete(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageDigitalConnectivityProfiDTO postHookGetById(VillageDigitalConnectivityProfiDTO dto) {
        return dto;
    }

    protected PageDTO<VillageDigitalConnectivityProfiDTO> postHookGetAll(PageDTO<VillageDigitalConnectivityProfiDTO> result) {
        return result;
    }
}
