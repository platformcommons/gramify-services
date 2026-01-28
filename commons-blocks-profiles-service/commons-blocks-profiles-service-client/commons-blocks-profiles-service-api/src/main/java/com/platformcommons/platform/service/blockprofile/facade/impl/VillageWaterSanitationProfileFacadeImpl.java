package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageWaterSanitationProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageWaterSanitationProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageWaterSanitationProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageWaterSanitationProfileFacadeImpl implements VillageWaterSanitationProfileFacade {

    private final VillageWaterSanitationProfileServiceExt serviceExt;
    private final VillageWaterSanitationProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEWATERSANITATIONPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERSANITATIONPROFILE.CREATE";
    private static final String VILLAGEWATERSANITATIONPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERSANITATIONPROFILE.UPDATED";
    private static final String VILLAGEWATERSANITATIONPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERSANITATIONPROFILE.DELETE";
    private static final String VILLAGEWATERSANITATIONPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEWATERSANITATIONPROFILE.GET";

    public VillageWaterSanitationProfileFacadeImpl(VillageWaterSanitationProfileServiceExt serviceExt, VillageWaterSanitationProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageWaterSanitationProfile entry in the system.
     *
     * @param VillageWaterSanitationProfileDTO The VillageWaterSanitationProfile information to be saved
     * @return The saved VillageWaterSanitationProfile data
     */
    @Override
    public VillageWaterSanitationProfileDTO save(VillageWaterSanitationProfileDTO VillageWaterSanitationProfileDTO) {
        log.debug("Entry - save(VillageWaterSanitationProfileDTO={})", VillageWaterSanitationProfileDTO);
        evaluator.evaluate(VILLAGEWATERSANITATIONPROFILE_CREATE, new HashMap<>());
        VillageWaterSanitationProfileDTO = preHookSave(VillageWaterSanitationProfileDTO);
        VillageWaterSanitationProfileDTO dto = serviceExt.save(VillageWaterSanitationProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageWaterSanitationProfile entry.
     *
     * @param VillageWaterSanitationProfileDTO The VillageWaterSanitationProfile information to be updated
     * @return The updated VillageWaterSanitationProfile data
     */
    @Override
    public VillageWaterSanitationProfileDTO update(VillageWaterSanitationProfileDTO VillageWaterSanitationProfileDTO) {
        log.debug("Entry - update(VillageWaterSanitationProfileDTO={})", VillageWaterSanitationProfileDTO);
        evaluator.evaluate(VILLAGEWATERSANITATIONPROFILE_UPDATE, new HashMap<>());
        VillageWaterSanitationProfileDTO = preHookUpdate(VillageWaterSanitationProfileDTO);
        VillageWaterSanitationProfileDTO dto = serviceExt.update(VillageWaterSanitationProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageWaterSanitationProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageWaterSanitationProfiles
     */
    @Override
    public PageDTO<VillageWaterSanitationProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEWATERSANITATIONPROFILE_GET, new HashMap<>());
        PageDTO<VillageWaterSanitationProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageWaterSanitationProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageWaterSanitationProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEWATERSANITATIONPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageWaterSanitationProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageWaterSanitationProfile by their ID.
     *
     * @param id The ID of the VillageWaterSanitationProfile to retrieve
     * @return The VillageWaterSanitationProfile with the specified ID
     */
    @Override
    public VillageWaterSanitationProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEWATERSANITATIONPROFILE_GET, new HashMap<>());
        VillageWaterSanitationProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageWaterSanitationProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageWaterSanitationProfileDTO
     */
    @Override
    public Set<VillageWaterSanitationProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEWATERSANITATIONPROFILE_GET, new HashMap<>());
        Set<VillageWaterSanitationProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageWaterSanitationProfileDTO postHookSave(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected VillageWaterSanitationProfileDTO preHookSave(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected VillageWaterSanitationProfileDTO postHookUpdate(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected VillageWaterSanitationProfileDTO preHookUpdate(VillageWaterSanitationProfileDTO VillageWaterSanitationProfileDTO) {
        return VillageWaterSanitationProfileDTO;
    }

    protected VillageWaterSanitationProfileDTO postHookDelete(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageWaterSanitationProfileDTO postHookGetById(VillageWaterSanitationProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageWaterSanitationProfileDTO> postHookGetAll(PageDTO<VillageWaterSanitationProfileDTO> result) {
        return result;
    }
}
