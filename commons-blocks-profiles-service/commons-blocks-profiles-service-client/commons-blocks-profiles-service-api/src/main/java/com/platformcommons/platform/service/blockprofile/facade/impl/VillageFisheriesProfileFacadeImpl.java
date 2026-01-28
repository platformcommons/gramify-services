package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageFisheriesProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageFisheriesProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageFisheriesProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageFisheriesProfileFacadeImpl implements VillageFisheriesProfileFacade {

    private final VillageFisheriesProfileServiceExt serviceExt;
    private final VillageFisheriesProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEFISHERIESPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFISHERIESPROFILE.CREATE";
    private static final String VILLAGEFISHERIESPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFISHERIESPROFILE.UPDATED";
    private static final String VILLAGEFISHERIESPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFISHERIESPROFILE.DELETE";
    private static final String VILLAGEFISHERIESPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEFISHERIESPROFILE.GET";

    public VillageFisheriesProfileFacadeImpl(VillageFisheriesProfileServiceExt serviceExt, VillageFisheriesProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageFisheriesProfile entry in the system.
     *
     * @param VillageFisheriesProfileDTO The VillageFisheriesProfile information to be saved
     * @return The saved VillageFisheriesProfile data
     */
    @Override
    public VillageFisheriesProfileDTO save(VillageFisheriesProfileDTO VillageFisheriesProfileDTO) {
        log.debug("Entry - save(VillageFisheriesProfileDTO={})", VillageFisheriesProfileDTO);
        evaluator.evaluate(VILLAGEFISHERIESPROFILE_CREATE, new HashMap<>());
        VillageFisheriesProfileDTO = preHookSave(VillageFisheriesProfileDTO);
        VillageFisheriesProfileDTO dto = serviceExt.save(VillageFisheriesProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageFisheriesProfile entry.
     *
     * @param VillageFisheriesProfileDTO The VillageFisheriesProfile information to be updated
     * @return The updated VillageFisheriesProfile data
     */
    @Override
    public VillageFisheriesProfileDTO update(VillageFisheriesProfileDTO VillageFisheriesProfileDTO) {
        log.debug("Entry - update(VillageFisheriesProfileDTO={})", VillageFisheriesProfileDTO);
        evaluator.evaluate(VILLAGEFISHERIESPROFILE_UPDATE, new HashMap<>());
        VillageFisheriesProfileDTO = preHookUpdate(VillageFisheriesProfileDTO);
        VillageFisheriesProfileDTO dto = serviceExt.update(VillageFisheriesProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageFisheriesProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageFisheriesProfiles
     */
    @Override
    public PageDTO<VillageFisheriesProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEFISHERIESPROFILE_GET, new HashMap<>());
        PageDTO<VillageFisheriesProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageFisheriesProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageFisheriesProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEFISHERIESPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageFisheriesProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageFisheriesProfile by their ID.
     *
     * @param id The ID of the VillageFisheriesProfile to retrieve
     * @return The VillageFisheriesProfile with the specified ID
     */
    @Override
    public VillageFisheriesProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEFISHERIESPROFILE_GET, new HashMap<>());
        VillageFisheriesProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageFisheriesProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageFisheriesProfileDTO
     */
    @Override
    public Set<VillageFisheriesProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEFISHERIESPROFILE_GET, new HashMap<>());
        Set<VillageFisheriesProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of productionSeasonList to a VillageFisheriesProfile identified by their ID.
     *
     * @param id               The ID of the VillageFisheriesProfile to add hobbies to
     * @param productionSeasonList  to be added
     * @since 1.0.0
     */
    @Override
    public void addProductionSeasonToVillageFisheriesProfile(Long id, List<ProductionSeasonDTO> productionSeasonList){
        serviceExt.addProductionSeasonToVillageFisheriesProfile(id,productionSeasonList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageFisheriesProfileDTO postHookSave(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected VillageFisheriesProfileDTO preHookSave(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected VillageFisheriesProfileDTO postHookUpdate(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected VillageFisheriesProfileDTO preHookUpdate(VillageFisheriesProfileDTO VillageFisheriesProfileDTO) {
        return VillageFisheriesProfileDTO;
    }

    protected VillageFisheriesProfileDTO postHookDelete(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageFisheriesProfileDTO postHookGetById(VillageFisheriesProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageFisheriesProfileDTO> postHookGetAll(PageDTO<VillageFisheriesProfileDTO> result) {
        return result;
    }
}
