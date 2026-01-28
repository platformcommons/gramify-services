package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageMarketProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageMarketProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageMarketProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageMarketProfileFacadeImpl implements VillageMarketProfileFacade {

    private final VillageMarketProfileServiceExt serviceExt;
    private final VillageMarketProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEMARKETPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEMARKETPROFILE.CREATE";
    private static final String VILLAGEMARKETPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEMARKETPROFILE.UPDATED";
    private static final String VILLAGEMARKETPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEMARKETPROFILE.DELETE";
    private static final String VILLAGEMARKETPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEMARKETPROFILE.GET";

    public VillageMarketProfileFacadeImpl(VillageMarketProfileServiceExt serviceExt, VillageMarketProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageMarketProfile entry in the system.
     *
     * @param VillageMarketProfileDTO The VillageMarketProfile information to be saved
     * @return The saved VillageMarketProfile data
     */
    @Override
    public VillageMarketProfileDTO save(VillageMarketProfileDTO VillageMarketProfileDTO) {
        log.debug("Entry - save(VillageMarketProfileDTO={})", VillageMarketProfileDTO);
        evaluator.evaluate(VILLAGEMARKETPROFILE_CREATE, new HashMap<>());
        VillageMarketProfileDTO = preHookSave(VillageMarketProfileDTO);
        VillageMarketProfileDTO dto = serviceExt.save(VillageMarketProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageMarketProfile entry.
     *
     * @param VillageMarketProfileDTO The VillageMarketProfile information to be updated
     * @return The updated VillageMarketProfile data
     */
    @Override
    public VillageMarketProfileDTO update(VillageMarketProfileDTO VillageMarketProfileDTO) {
        log.debug("Entry - update(VillageMarketProfileDTO={})", VillageMarketProfileDTO);
        evaluator.evaluate(VILLAGEMARKETPROFILE_UPDATE, new HashMap<>());
        VillageMarketProfileDTO = preHookUpdate(VillageMarketProfileDTO);
        VillageMarketProfileDTO dto = serviceExt.update(VillageMarketProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageMarketProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageMarketProfiles
     */
    @Override
    public PageDTO<VillageMarketProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEMARKETPROFILE_GET, new HashMap<>());
        PageDTO<VillageMarketProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageMarketProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageMarketProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEMARKETPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageMarketProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageMarketProfile by their ID.
     *
     * @param id The ID of the VillageMarketProfile to retrieve
     * @return The VillageMarketProfile with the specified ID
     */
    @Override
    public VillageMarketProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEMARKETPROFILE_GET, new HashMap<>());
        VillageMarketProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageMarketProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageMarketProfileDTO
     */
    @Override
    public Set<VillageMarketProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEMARKETPROFILE_GET, new HashMap<>());
        Set<VillageMarketProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of operatingDayList to a VillageMarketProfile identified by their ID.
     *
     * @param id               The ID of the VillageMarketProfile to add hobbies to
     * @param operatingDayList  to be added
     * @since 1.0.0
     */
    @Override
    public void addOperatingDayToVillageMarketProfile(Long id, List<OperatingDayDTO> operatingDayList){
        serviceExt.addOperatingDayToVillageMarketProfile(id,operatingDayList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageMarketProfileDTO postHookSave(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected VillageMarketProfileDTO preHookSave(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected VillageMarketProfileDTO postHookUpdate(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected VillageMarketProfileDTO preHookUpdate(VillageMarketProfileDTO VillageMarketProfileDTO) {
        return VillageMarketProfileDTO;
    }

    protected VillageMarketProfileDTO postHookDelete(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageMarketProfileDTO postHookGetById(VillageMarketProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageMarketProfileDTO> postHookGetAll(PageDTO<VillageMarketProfileDTO> result) {
        return result;
    }
}
