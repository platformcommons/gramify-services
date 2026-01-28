package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageInstitutionalResourceProFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageInstitutionalResourceProProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageInstitutionalResourceProServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageInstitutionalResourceProFacadeImpl implements VillageInstitutionalResourceProFacade {

    private final VillageInstitutionalResourceProServiceExt serviceExt;
    private final VillageInstitutionalResourceProProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEINSTITUTIONALRESOURCEPRO_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEINSTITUTIONALRESOURCEPRO.CREATE";
    private static final String VILLAGEINSTITUTIONALRESOURCEPRO_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEINSTITUTIONALRESOURCEPRO.UPDATED";
    private static final String VILLAGEINSTITUTIONALRESOURCEPRO_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEINSTITUTIONALRESOURCEPRO.DELETE";
    private static final String VILLAGEINSTITUTIONALRESOURCEPRO_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEINSTITUTIONALRESOURCEPRO.GET";

    public VillageInstitutionalResourceProFacadeImpl(VillageInstitutionalResourceProServiceExt serviceExt, VillageInstitutionalResourceProProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageInstitutionalResourcePro entry in the system.
     *
     * @param VillageInstitutionalResourceProDTO The VillageInstitutionalResourcePro information to be saved
     * @return The saved VillageInstitutionalResourcePro data
     */
    @Override
    public VillageInstitutionalResourceProDTO save(VillageInstitutionalResourceProDTO VillageInstitutionalResourceProDTO) {
        log.debug("Entry - save(VillageInstitutionalResourceProDTO={})", VillageInstitutionalResourceProDTO);
        evaluator.evaluate(VILLAGEINSTITUTIONALRESOURCEPRO_CREATE, new HashMap<>());
        VillageInstitutionalResourceProDTO = preHookSave(VillageInstitutionalResourceProDTO);
        VillageInstitutionalResourceProDTO dto = serviceExt.save(VillageInstitutionalResourceProDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageInstitutionalResourcePro entry.
     *
     * @param VillageInstitutionalResourceProDTO The VillageInstitutionalResourcePro information to be updated
     * @return The updated VillageInstitutionalResourcePro data
     */
    @Override
    public VillageInstitutionalResourceProDTO update(VillageInstitutionalResourceProDTO VillageInstitutionalResourceProDTO) {
        log.debug("Entry - update(VillageInstitutionalResourceProDTO={})", VillageInstitutionalResourceProDTO);
        evaluator.evaluate(VILLAGEINSTITUTIONALRESOURCEPRO_UPDATE, new HashMap<>());
        VillageInstitutionalResourceProDTO = preHookUpdate(VillageInstitutionalResourceProDTO);
        VillageInstitutionalResourceProDTO dto = serviceExt.update(VillageInstitutionalResourceProDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageInstitutionalResourcePros.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageInstitutionalResourcePros
     */
    @Override
    public PageDTO<VillageInstitutionalResourceProDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEINSTITUTIONALRESOURCEPRO_GET, new HashMap<>());
        PageDTO<VillageInstitutionalResourceProDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageInstitutionalResourcePro by their ID with a specified reason.
     *
     * @param id     The ID of the VillageInstitutionalResourcePro to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEINSTITUTIONALRESOURCEPRO_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageInstitutionalResourceProDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageInstitutionalResourcePro by their ID.
     *
     * @param id The ID of the VillageInstitutionalResourcePro to retrieve
     * @return The VillageInstitutionalResourcePro with the specified ID
     */
    @Override
    public VillageInstitutionalResourceProDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEINSTITUTIONALRESOURCEPRO_GET, new HashMap<>());
        VillageInstitutionalResourceProDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageInstitutionalResourcePros by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageInstitutionalResourceProDTO
     */
    @Override
    public Set<VillageInstitutionalResourceProDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEINSTITUTIONALRESOURCEPRO_GET, new HashMap<>());
        Set<VillageInstitutionalResourceProDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of nGOThematicAreaList to a VillageInstitutionalResourcePro identified by their ID.
     *
     * @param id               The ID of the VillageInstitutionalResourcePro to add hobbies to
     * @param nGOThematicAreaList  to be added
     * @since 1.0.0
     */
    @Override
    public void addNGOThematicAreaToVillageInstitutionalResourcePro(Long id, List<NGOThematicAreaDTO> nGOThematicAreaList){
        serviceExt.addNGOThematicAreaToVillageInstitutionalResourcePro(id,nGOThematicAreaList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageInstitutionalResourceProDTO postHookSave(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected VillageInstitutionalResourceProDTO preHookSave(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected VillageInstitutionalResourceProDTO postHookUpdate(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected VillageInstitutionalResourceProDTO preHookUpdate(VillageInstitutionalResourceProDTO VillageInstitutionalResourceProDTO) {
        return VillageInstitutionalResourceProDTO;
    }

    protected VillageInstitutionalResourceProDTO postHookDelete(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageInstitutionalResourceProDTO postHookGetById(VillageInstitutionalResourceProDTO dto) {
        return dto;
    }

    protected PageDTO<VillageInstitutionalResourceProDTO> postHookGetAll(PageDTO<VillageInstitutionalResourceProDTO> result) {
        return result;
    }
}
