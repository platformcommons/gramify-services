package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageEnvironmentalRiskProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageEnvironmentalRiskProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageEnvironmentalRiskProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageEnvironmentalRiskProfileFacadeImpl implements VillageEnvironmentalRiskProfileFacade {

    private final VillageEnvironmentalRiskProfileServiceExt serviceExt;
    private final VillageEnvironmentalRiskProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEENVIRONMENTALRISKPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEENVIRONMENTALRISKPROFILE.CREATE";
    private static final String VILLAGEENVIRONMENTALRISKPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEENVIRONMENTALRISKPROFILE.UPDATED";
    private static final String VILLAGEENVIRONMENTALRISKPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEENVIRONMENTALRISKPROFILE.DELETE";
    private static final String VILLAGEENVIRONMENTALRISKPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEENVIRONMENTALRISKPROFILE.GET";

    public VillageEnvironmentalRiskProfileFacadeImpl(VillageEnvironmentalRiskProfileServiceExt serviceExt, VillageEnvironmentalRiskProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageEnvironmentalRiskProfile entry in the system.
     *
     * @param VillageEnvironmentalRiskProfileDTO The VillageEnvironmentalRiskProfile information to be saved
     * @return The saved VillageEnvironmentalRiskProfile data
     */
    @Override
    public VillageEnvironmentalRiskProfileDTO save(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfileDTO) {
        log.debug("Entry - save(VillageEnvironmentalRiskProfileDTO={})", VillageEnvironmentalRiskProfileDTO);
        evaluator.evaluate(VILLAGEENVIRONMENTALRISKPROFILE_CREATE, new HashMap<>());
        VillageEnvironmentalRiskProfileDTO = preHookSave(VillageEnvironmentalRiskProfileDTO);
        VillageEnvironmentalRiskProfileDTO dto = serviceExt.save(VillageEnvironmentalRiskProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageEnvironmentalRiskProfile entry.
     *
     * @param VillageEnvironmentalRiskProfileDTO The VillageEnvironmentalRiskProfile information to be updated
     * @return The updated VillageEnvironmentalRiskProfile data
     */
    @Override
    public VillageEnvironmentalRiskProfileDTO update(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfileDTO) {
        log.debug("Entry - update(VillageEnvironmentalRiskProfileDTO={})", VillageEnvironmentalRiskProfileDTO);
        evaluator.evaluate(VILLAGEENVIRONMENTALRISKPROFILE_UPDATE, new HashMap<>());
        VillageEnvironmentalRiskProfileDTO = preHookUpdate(VillageEnvironmentalRiskProfileDTO);
        VillageEnvironmentalRiskProfileDTO dto = serviceExt.update(VillageEnvironmentalRiskProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageEnvironmentalRiskProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageEnvironmentalRiskProfiles
     */
    @Override
    public PageDTO<VillageEnvironmentalRiskProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEENVIRONMENTALRISKPROFILE_GET, new HashMap<>());
        PageDTO<VillageEnvironmentalRiskProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageEnvironmentalRiskProfile by their ID with a specified reason.
     *
     * @param id     The ID of the VillageEnvironmentalRiskProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEENVIRONMENTALRISKPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageEnvironmentalRiskProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageEnvironmentalRiskProfile by their ID.
     *
     * @param id The ID of the VillageEnvironmentalRiskProfile to retrieve
     * @return The VillageEnvironmentalRiskProfile with the specified ID
     */
    @Override
    public VillageEnvironmentalRiskProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEENVIRONMENTALRISKPROFILE_GET, new HashMap<>());
        VillageEnvironmentalRiskProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageEnvironmentalRiskProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageEnvironmentalRiskProfileDTO
     */
    @Override
    public Set<VillageEnvironmentalRiskProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEENVIRONMENTALRISKPROFILE_GET, new HashMap<>());
        Set<VillageEnvironmentalRiskProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageEnvironmentalRiskProfileDTO postHookSave(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected VillageEnvironmentalRiskProfileDTO preHookSave(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected VillageEnvironmentalRiskProfileDTO postHookUpdate(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected VillageEnvironmentalRiskProfileDTO preHookUpdate(VillageEnvironmentalRiskProfileDTO VillageEnvironmentalRiskProfileDTO) {
        return VillageEnvironmentalRiskProfileDTO;
    }

    protected VillageEnvironmentalRiskProfileDTO postHookDelete(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageEnvironmentalRiskProfileDTO postHookGetById(VillageEnvironmentalRiskProfileDTO dto) {
        return dto;
    }

    protected PageDTO<VillageEnvironmentalRiskProfileDTO> postHookGetAll(PageDTO<VillageEnvironmentalRiskProfileDTO> result) {
        return result;
    }
}
