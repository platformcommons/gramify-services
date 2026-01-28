package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageCulturalResourceProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageCulturalResourceProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageCulturalResourceProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageCulturalResourceProfileFacadeImpl implements VillageCulturalResourceProfileFacade {

    private final VillageCulturalResourceProfileServiceExt serviceExt;
    private final VillageCulturalResourceProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGECULTURALRESOURCEPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECULTURALRESOURCEPROFILE.CREATE";
    private static final String VILLAGECULTURALRESOURCEPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECULTURALRESOURCEPROFILE.UPDATED";
    private static final String VILLAGECULTURALRESOURCEPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECULTURALRESOURCEPROFILE.DELETE";
    private static final String VILLAGECULTURALRESOURCEPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECULTURALRESOURCEPROFILE.GET";

    public VillageCulturalResourceProfileFacadeImpl(VillageCulturalResourceProfileServiceExt serviceExt, VillageCulturalResourceProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageCulturalResourceProfile entry in the system.
     *
     * @param VillageCulturalResourceProfileDTO The VillageCulturalResourceProfile information to be saved
     * @return The saved VillageCulturalResourceProfile data
     */
    @Override
    public VillageCulturalResourceProfileDTO save(VillageCulturalResourceProfileDTO VillageCulturalResourceProfileDTO) {
        log.debug("Entry - save(VillageCulturalResourceProfileDTO={})", VillageCulturalResourceProfileDTO);
        evaluator.evaluate(VILLAGECULTURALRESOURCEPROFILE_CREATE, new HashMap<>());
        VillageCulturalResourceProfileDTO = preHookSave(VillageCulturalResourceProfileDTO);
        VillageCulturalResourceProfileDTO dto = serviceExt.save(VillageCulturalResourceProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageCulturalResourceProfile entry.
     *
     * @param VillageCulturalResourceProfileDTO The VillageCulturalResourceProfile information to be updated
     * @return The updated VillageCulturalResourceProfile data
     */
    @Override
    public VillageCulturalResourceProfileDTO update(VillageCulturalResourceProfileDTO VillageCulturalResourceProfileDTO) {
        log.debug("Entry - update(VillageCulturalResourceProfileDTO={})", VillageCulturalResourceProfileDTO);
        evaluator.evaluate(VILLAGECULTURALRESOURCEPROFILE_UPDATE, new HashMap<>());
        VillageCulturalResourceProfileDTO = preHookUpdate(VillageCulturalResourceProfileDTO);
        VillageCulturalResourceProfileDTO dto = serviceExt.update(VillageCulturalResourceProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageCulturalResourceProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCulturalResourceProfiles
     */
    @Override
    public PageDTO<VillageCulturalResourceProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGECULTURALRESOURCEPROFILE_GET, new HashMap<>());
        PageDTO<VillageCulturalResourceProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageCulturalResourceProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCulturalResourceProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGECULTURALRESOURCEPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageCulturalResourceProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageCulturalResourceProfile by their ID.
     *
     * @param id The ID of the VillageCulturalResourceProfile to retrieve
     * @return The VillageCulturalResourceProfile with the specified ID
     */
    @Override
    public VillageCulturalResourceProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGECULTURALRESOURCEPROFILE_GET, new HashMap<>());
        VillageCulturalResourceProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageCulturalResourceProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCulturalResourceProfileDTO
     */
    @Override
    public Set<VillageCulturalResourceProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGECULTURALRESOURCEPROFILE_GET, new HashMap<>());
        Set<VillageCulturalResourceProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of majorFestivalList to a VillageCulturalResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
     * @param majorFestivalList  to be added
     * @since 1.0.0
     */
    @Override
    public void addMajorFestivalToVillageCulturalResourceProfile(Long id, List<MajorFestivalDTO> majorFestivalList){
        serviceExt.addMajorFestivalToVillageCulturalResourceProfile(id,majorFestivalList);
    }
/**
     * Adds a list of mainReligiousPlaceList to a VillageCulturalResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
     * @param mainReligiousPlaceList  to be added
     * @since 1.0.0
     */
    @Override
    public void addMainReligiousPlaceToVillageCulturalResourceProfile(Long id, List<MainReligiousPlaceDTO> mainReligiousPlaceList){
        serviceExt.addMainReligiousPlaceToVillageCulturalResourceProfile(id,mainReligiousPlaceList);
    }
/**
     * Adds a list of localFestivalList to a VillageCulturalResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
     * @param localFestivalList  to be added
     * @since 1.0.0
     */
    @Override
    public void addLocalFestivalToVillageCulturalResourceProfile(Long id, List<LocalFestivalDTO> localFestivalList){
        serviceExt.addLocalFestivalToVillageCulturalResourceProfile(id,localFestivalList);
    }
/**
     * Adds a list of localArtFormTypeList to a VillageCulturalResourceProfile identified by their ID.
     *
     * @param id               The ID of the VillageCulturalResourceProfile to add hobbies to
     * @param localArtFormTypeList  to be added
     * @since 1.0.0
     */
    @Override
    public void addLocalArtFormTypeToVillageCulturalResourceProfile(Long id, List<LocalArtFormTypeDTO> localArtFormTypeList){
        serviceExt.addLocalArtFormTypeToVillageCulturalResourceProfile(id,localArtFormTypeList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageCulturalResourceProfileDTO postHookSave(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected VillageCulturalResourceProfileDTO preHookSave(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected VillageCulturalResourceProfileDTO postHookUpdate(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected VillageCulturalResourceProfileDTO preHookUpdate(VillageCulturalResourceProfileDTO VillageCulturalResourceProfileDTO) {
        return VillageCulturalResourceProfileDTO;
    }

    protected VillageCulturalResourceProfileDTO postHookDelete(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCulturalResourceProfileDTO postHookGetById(VillageCulturalResourceProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCulturalResourceProfileDTO> postHookGetAll(PageDTO<VillageCulturalResourceProfileDTO> result) {
        return result;
    }
}
