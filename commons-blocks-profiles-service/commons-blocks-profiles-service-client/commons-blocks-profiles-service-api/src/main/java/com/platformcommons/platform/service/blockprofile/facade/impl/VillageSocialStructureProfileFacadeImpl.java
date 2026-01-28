package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageSocialStructureProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageSocialStructureProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageSocialStructureProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageSocialStructureProfileFacadeImpl implements VillageSocialStructureProfileFacade {

    private final VillageSocialStructureProfileServiceExt serviceExt;
    private final VillageSocialStructureProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGESOCIALSTRUCTUREPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESOCIALSTRUCTUREPROFILE.CREATE";
    private static final String VILLAGESOCIALSTRUCTUREPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESOCIALSTRUCTUREPROFILE.UPDATED";
    private static final String VILLAGESOCIALSTRUCTUREPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESOCIALSTRUCTUREPROFILE.DELETE";
    private static final String VILLAGESOCIALSTRUCTUREPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESOCIALSTRUCTUREPROFILE.GET";

    public VillageSocialStructureProfileFacadeImpl(VillageSocialStructureProfileServiceExt serviceExt, VillageSocialStructureProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageSocialStructureProfile entry in the system.
     *
     * @param VillageSocialStructureProfileDTO The VillageSocialStructureProfile information to be saved
     * @return The saved VillageSocialStructureProfile data
     */
    @Override
    public VillageSocialStructureProfileDTO save(VillageSocialStructureProfileDTO VillageSocialStructureProfileDTO) {
        log.debug("Entry - save(VillageSocialStructureProfileDTO={})", VillageSocialStructureProfileDTO);
        evaluator.evaluate(VILLAGESOCIALSTRUCTUREPROFILE_CREATE, new HashMap<>());
        VillageSocialStructureProfileDTO = preHookSave(VillageSocialStructureProfileDTO);
        VillageSocialStructureProfileDTO dto = serviceExt.save(VillageSocialStructureProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageSocialStructureProfile entry.
     *
     * @param VillageSocialStructureProfileDTO The VillageSocialStructureProfile information to be updated
     * @return The updated VillageSocialStructureProfile data
     */
    @Override
    public VillageSocialStructureProfileDTO update(VillageSocialStructureProfileDTO VillageSocialStructureProfileDTO) {
        log.debug("Entry - update(VillageSocialStructureProfileDTO={})", VillageSocialStructureProfileDTO);
        evaluator.evaluate(VILLAGESOCIALSTRUCTUREPROFILE_UPDATE, new HashMap<>());
        VillageSocialStructureProfileDTO = preHookUpdate(VillageSocialStructureProfileDTO);
        VillageSocialStructureProfileDTO dto = serviceExt.update(VillageSocialStructureProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageSocialStructureProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageSocialStructureProfiles
     */
    @Override
    public PageDTO<VillageSocialStructureProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGESOCIALSTRUCTUREPROFILE_GET, new HashMap<>());
        PageDTO<VillageSocialStructureProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageSocialStructureProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageSocialStructureProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGESOCIALSTRUCTUREPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageSocialStructureProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageSocialStructureProfile by their ID.
     *
     * @param id The ID of the VillageSocialStructureProfile to retrieve
     * @return The VillageSocialStructureProfile with the specified ID
     */
    @Override
    public VillageSocialStructureProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGESOCIALSTRUCTUREPROFILE_GET, new HashMap<>());
        VillageSocialStructureProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageSocialStructureProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSocialStructureProfileDTO
     */
    @Override
    public Set<VillageSocialStructureProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGESOCIALSTRUCTUREPROFILE_GET, new HashMap<>());
        Set<VillageSocialStructureProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of otherCommunityGroupList to a VillageSocialStructureProfile identified by their ID.
     *
     * @param id               The ID of the VillageSocialStructureProfile to add hobbies to
     * @param otherCommunityGroupList  to be added
     * @since 1.0.0
     */
    @Override
    public void addOtherCommunityGroupToVillageSocialStructureProfile(Long id, List<OtherCommunityGroupDTO> otherCommunityGroupList){
        serviceExt.addOtherCommunityGroupToVillageSocialStructureProfile(id,otherCommunityGroupList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageSocialStructureProfileDTO postHookSave(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected VillageSocialStructureProfileDTO preHookSave(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected VillageSocialStructureProfileDTO postHookUpdate(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected VillageSocialStructureProfileDTO preHookUpdate(VillageSocialStructureProfileDTO VillageSocialStructureProfileDTO) {
        return VillageSocialStructureProfileDTO;
    }

    protected VillageSocialStructureProfileDTO postHookDelete(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageSocialStructureProfileDTO postHookGetById(VillageSocialStructureProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageSocialStructureProfileDTO> postHookGetAll(PageDTO<VillageSocialStructureProfileDTO> result) {
        return result;
    }
}
