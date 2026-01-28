package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageCropProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageCropProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageCropProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageCropProfileFacadeImpl implements VillageCropProfileFacade {

    private final VillageCropProfileServiceExt serviceExt;
    private final VillageCropProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGECROPPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECROPPROFILE.CREATE";
    private static final String VILLAGECROPPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECROPPROFILE.UPDATED";
    private static final String VILLAGECROPPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECROPPROFILE.DELETE";
    private static final String VILLAGECROPPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECROPPROFILE.GET";

    public VillageCropProfileFacadeImpl(VillageCropProfileServiceExt serviceExt, VillageCropProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageCropProfile entry in the system.
     *
     * @param VillageCropProfileDTO The VillageCropProfile information to be saved
     * @return The saved VillageCropProfile data
     */
    @Override
    public VillageCropProfileDTO save(VillageCropProfileDTO VillageCropProfileDTO) {
        log.debug("Entry - save(VillageCropProfileDTO={})", VillageCropProfileDTO);
        evaluator.evaluate(VILLAGECROPPROFILE_CREATE, new HashMap<>());
        VillageCropProfileDTO = preHookSave(VillageCropProfileDTO);
        VillageCropProfileDTO dto = serviceExt.save(VillageCropProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageCropProfile entry.
     *
     * @param VillageCropProfileDTO The VillageCropProfile information to be updated
     * @return The updated VillageCropProfile data
     */
    @Override
    public VillageCropProfileDTO update(VillageCropProfileDTO VillageCropProfileDTO) {
        log.debug("Entry - update(VillageCropProfileDTO={})", VillageCropProfileDTO);
        evaluator.evaluate(VILLAGECROPPROFILE_UPDATE, new HashMap<>());
        VillageCropProfileDTO = preHookUpdate(VillageCropProfileDTO);
        VillageCropProfileDTO dto = serviceExt.update(VillageCropProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageCropProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCropProfiles
     */
    @Override
    public PageDTO<VillageCropProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGECROPPROFILE_GET, new HashMap<>());
        PageDTO<VillageCropProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageCropProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCropProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGECROPPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageCropProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageCropProfile by their ID.
     *
     * @param id The ID of the VillageCropProfile to retrieve
     * @return The VillageCropProfile with the specified ID
     */
    @Override
    public VillageCropProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGECROPPROFILE_GET, new HashMap<>());
        VillageCropProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageCropProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCropProfileDTO
     */
    @Override
    public Set<VillageCropProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGECROPPROFILE_GET, new HashMap<>());
        Set<VillageCropProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of cropList to a VillageCropProfile identified by their ID.
     *
     * @param id               The ID of the VillageCropProfile to add hobbies to
     * @param cropList  to be added
     * @since 1.0.0
     */
    @Override
    public void addCropToVillageCropProfile(Long id, List<CropDTO> cropList){
        serviceExt.addCropToVillageCropProfile(id,cropList);
    }
/**
     * Adds a list of croppingSeasonList to a VillageCropProfile identified by their ID.
     *
     * @param id               The ID of the VillageCropProfile to add hobbies to
     * @param croppingSeasonList  to be added
     * @since 1.0.0
     */
    @Override
    public void addCroppingSeasonToVillageCropProfile(Long id, List<CroppingSeasonDTO> croppingSeasonList){
        serviceExt.addCroppingSeasonToVillageCropProfile(id,croppingSeasonList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageCropProfileDTO postHookSave(VillageCropProfileDTO dto) {
        return dto;
    }

    protected VillageCropProfileDTO preHookSave(VillageCropProfileDTO dto) {
        return dto;
    }

    protected VillageCropProfileDTO postHookUpdate(VillageCropProfileDTO dto) {
        return dto;
    }

    protected VillageCropProfileDTO preHookUpdate(VillageCropProfileDTO VillageCropProfileDTO) {
        return VillageCropProfileDTO;
    }

    protected VillageCropProfileDTO postHookDelete(VillageCropProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCropProfileDTO postHookGetById(VillageCropProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCropProfileDTO> postHookGetAll(PageDTO<VillageCropProfileDTO> result) {
        return result;
    }
}
