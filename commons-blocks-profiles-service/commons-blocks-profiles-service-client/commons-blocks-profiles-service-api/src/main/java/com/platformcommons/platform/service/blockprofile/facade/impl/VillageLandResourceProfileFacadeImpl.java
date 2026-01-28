package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageLandResourceProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageLandResourceProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageLandResourceProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageLandResourceProfileFacadeImpl implements VillageLandResourceProfileFacade {

    private final VillageLandResourceProfileServiceExt serviceExt;
    private final VillageLandResourceProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGELANDRESOURCEPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGELANDRESOURCEPROFILE.CREATE";
    private static final String VILLAGELANDRESOURCEPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGELANDRESOURCEPROFILE.UPDATED";
    private static final String VILLAGELANDRESOURCEPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGELANDRESOURCEPROFILE.DELETE";
    private static final String VILLAGELANDRESOURCEPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGELANDRESOURCEPROFILE.GET";

    public VillageLandResourceProfileFacadeImpl(VillageLandResourceProfileServiceExt serviceExt, VillageLandResourceProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageLandResourceProfile entry in the system.
     *
     * @param VillageLandResourceProfileDTO The VillageLandResourceProfile information to be saved
     * @return The saved VillageLandResourceProfile data
     */
    @Override
    public VillageLandResourceProfileDTO save(VillageLandResourceProfileDTO VillageLandResourceProfileDTO) {
        log.debug("Entry - save(VillageLandResourceProfileDTO={})", VillageLandResourceProfileDTO);
        evaluator.evaluate(VILLAGELANDRESOURCEPROFILE_CREATE, new HashMap<>());
        VillageLandResourceProfileDTO = preHookSave(VillageLandResourceProfileDTO);
        VillageLandResourceProfileDTO dto = serviceExt.save(VillageLandResourceProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageLandResourceProfile entry.
     *
     * @param VillageLandResourceProfileDTO The VillageLandResourceProfile information to be updated
     * @return The updated VillageLandResourceProfile data
     */
    @Override
    public VillageLandResourceProfileDTO update(VillageLandResourceProfileDTO VillageLandResourceProfileDTO) {
        log.debug("Entry - update(VillageLandResourceProfileDTO={})", VillageLandResourceProfileDTO);
        evaluator.evaluate(VILLAGELANDRESOURCEPROFILE_UPDATE, new HashMap<>());
        VillageLandResourceProfileDTO = preHookUpdate(VillageLandResourceProfileDTO);
        VillageLandResourceProfileDTO dto = serviceExt.update(VillageLandResourceProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageLandResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageLandResourceProfiles
     */
    @Override
    public PageDTO<VillageLandResourceProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGELANDRESOURCEPROFILE_GET, new HashMap<>());
        PageDTO<VillageLandResourceProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageLandResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageLandResourceProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGELANDRESOURCEPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageLandResourceProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageLandResourceProfile by their ID.
     *
     * @param id The ID of the VillageLandResourceProfile to retrieve
     * @return The VillageLandResourceProfile with the specified ID
     */
    @Override
    public VillageLandResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGELANDRESOURCEPROFILE_GET, new HashMap<>());
        VillageLandResourceProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageLandResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageLandResourceProfileDTO
     */
    @Override
    public Set<VillageLandResourceProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGELANDRESOURCEPROFILE_GET, new HashMap<>());
        Set<VillageLandResourceProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of villageSoilTypeList to a VillageLandResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageLandResourceProfile to add hobbies to
     * @param villageSoilTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageSoilTypeToVillageLandResourceProfile(Long id, List<VillageSoilTypeDTO> villageSoilTypeList){
        serviceExt.addVillageSoilTypeToVillageLandResourceProfile(id,villageSoilTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageLandResourceProfileDTO postHookSave(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected VillageLandResourceProfileDTO preHookSave(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected VillageLandResourceProfileDTO postHookUpdate(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected VillageLandResourceProfileDTO preHookUpdate(VillageLandResourceProfileDTO VillageLandResourceProfileDTO) {
        return VillageLandResourceProfileDTO;
    }

    protected VillageLandResourceProfileDTO postHookDelete(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageLandResourceProfileDTO postHookGetById(VillageLandResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageLandResourceProfileDTO> postHookGetAll(PageDTO<VillageLandResourceProfileDTO> result) {
        return result;
    }
}
