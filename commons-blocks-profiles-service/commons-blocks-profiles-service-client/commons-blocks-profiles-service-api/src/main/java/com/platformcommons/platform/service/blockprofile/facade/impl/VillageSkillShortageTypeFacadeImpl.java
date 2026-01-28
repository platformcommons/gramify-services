package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageSkillShortageTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageSkillShortageTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageSkillShortageTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageSkillShortageTypeFacadeImpl implements VillageSkillShortageTypeFacade {

    private final VillageSkillShortageTypeServiceExt serviceExt;
    private final VillageSkillShortageTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGESKILLSHORTAGETYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESKILLSHORTAGETYPE.CREATE";
    private static final String VILLAGESKILLSHORTAGETYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESKILLSHORTAGETYPE.UPDATED";
    private static final String VILLAGESKILLSHORTAGETYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESKILLSHORTAGETYPE.DELETE";
    private static final String VILLAGESKILLSHORTAGETYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGESKILLSHORTAGETYPE.GET";

    public VillageSkillShortageTypeFacadeImpl(VillageSkillShortageTypeServiceExt serviceExt, VillageSkillShortageTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageSkillShortageType entry in the system.
     *
     * @param VillageSkillShortageTypeDTO The VillageSkillShortageType information to be saved
     * @return The saved VillageSkillShortageType data
     */
    @Override
    public VillageSkillShortageTypeDTO save(VillageSkillShortageTypeDTO VillageSkillShortageTypeDTO) {
        log.debug("Entry - save(VillageSkillShortageTypeDTO={})", VillageSkillShortageTypeDTO);
        evaluator.evaluate(VILLAGESKILLSHORTAGETYPE_CREATE, new HashMap<>());
        VillageSkillShortageTypeDTO = preHookSave(VillageSkillShortageTypeDTO);
        VillageSkillShortageTypeDTO dto = serviceExt.save(VillageSkillShortageTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageSkillShortageType entry.
     *
     * @param VillageSkillShortageTypeDTO The VillageSkillShortageType information to be updated
     * @return The updated VillageSkillShortageType data
     */
    @Override
    public VillageSkillShortageTypeDTO update(VillageSkillShortageTypeDTO VillageSkillShortageTypeDTO) {
        log.debug("Entry - update(VillageSkillShortageTypeDTO={})", VillageSkillShortageTypeDTO);
        evaluator.evaluate(VILLAGESKILLSHORTAGETYPE_UPDATE, new HashMap<>());
        VillageSkillShortageTypeDTO = preHookUpdate(VillageSkillShortageTypeDTO);
        VillageSkillShortageTypeDTO dto = serviceExt.update(VillageSkillShortageTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageSkillShortageTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageSkillShortageTypes
     */
    @Override
    public PageDTO<VillageSkillShortageTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGESKILLSHORTAGETYPE_GET, new HashMap<>());
        PageDTO<VillageSkillShortageTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageSkillShortageType by their ID with a specified reason.
     *
     * @param id     The ID of the VillageSkillShortageType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGESKILLSHORTAGETYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageSkillShortageTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageSkillShortageType by their ID.
     *
     * @param id The ID of the VillageSkillShortageType to retrieve
     * @return The VillageSkillShortageType with the specified ID
     */
    @Override
    public VillageSkillShortageTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGESKILLSHORTAGETYPE_GET, new HashMap<>());
        VillageSkillShortageTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageSkillShortageTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageSkillShortageTypeDTO
     */
    @Override
    public Set<VillageSkillShortageTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGESKILLSHORTAGETYPE_GET, new HashMap<>());
        Set<VillageSkillShortageTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageSkillShortageTypeDTO postHookSave(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected VillageSkillShortageTypeDTO preHookSave(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected VillageSkillShortageTypeDTO postHookUpdate(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected VillageSkillShortageTypeDTO preHookUpdate(VillageSkillShortageTypeDTO VillageSkillShortageTypeDTO) {
        return VillageSkillShortageTypeDTO;
    }

    protected VillageSkillShortageTypeDTO postHookDelete(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageSkillShortageTypeDTO postHookGetById(VillageSkillShortageTypeDTO dto) {
        return dto;
    }

    protected PageDTO<VillageSkillShortageTypeDTO> postHookGetAll(PageDTO<VillageSkillShortageTypeDTO> result) {
        return result;
    }
}
