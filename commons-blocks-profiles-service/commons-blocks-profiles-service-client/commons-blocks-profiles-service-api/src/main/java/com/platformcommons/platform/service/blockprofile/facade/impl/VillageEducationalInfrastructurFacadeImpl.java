package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageEducationalInfrastructurFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageEducationalInfrastructurProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageEducationalInfrastructurServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageEducationalInfrastructurFacadeImpl implements VillageEducationalInfrastructurFacade {

    private final VillageEducationalInfrastructurServiceExt serviceExt;
    private final VillageEducationalInfrastructurProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEEDUCATIONALINFRASTRUCTUR_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEDUCATIONALINFRASTRUCTUR.CREATE";
    private static final String VILLAGEEDUCATIONALINFRASTRUCTUR_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEDUCATIONALINFRASTRUCTUR.UPDATED";
    private static final String VILLAGEEDUCATIONALINFRASTRUCTUR_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEDUCATIONALINFRASTRUCTUR.DELETE";
    private static final String VILLAGEEDUCATIONALINFRASTRUCTUR_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEEDUCATIONALINFRASTRUCTUR.GET";

    public VillageEducationalInfrastructurFacadeImpl(VillageEducationalInfrastructurServiceExt serviceExt, VillageEducationalInfrastructurProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageEducationalInfrastructur entry in the system.
     *
     * @param VillageEducationalInfrastructurDTO The VillageEducationalInfrastructur information to be saved
     * @return The saved VillageEducationalInfrastructur data
     */
    @Override
    public VillageEducationalInfrastructurDTO save(VillageEducationalInfrastructurDTO VillageEducationalInfrastructurDTO) {
        log.debug("Entry - save(VillageEducationalInfrastructurDTO={})", VillageEducationalInfrastructurDTO);
        evaluator.evaluate(VILLAGEEDUCATIONALINFRASTRUCTUR_CREATE, new HashMap<>());
        VillageEducationalInfrastructurDTO = preHookSave(VillageEducationalInfrastructurDTO);
        VillageEducationalInfrastructurDTO dto = serviceExt.save(VillageEducationalInfrastructurDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageEducationalInfrastructur entry.
     *
     * @param VillageEducationalInfrastructurDTO The VillageEducationalInfrastructur information to be updated
     * @return The updated VillageEducationalInfrastructur data
     */
    @Override
    public VillageEducationalInfrastructurDTO update(VillageEducationalInfrastructurDTO VillageEducationalInfrastructurDTO) {
        log.debug("Entry - update(VillageEducationalInfrastructurDTO={})", VillageEducationalInfrastructurDTO);
        evaluator.evaluate(VILLAGEEDUCATIONALINFRASTRUCTUR_UPDATE, new HashMap<>());
        VillageEducationalInfrastructurDTO = preHookUpdate(VillageEducationalInfrastructurDTO);
        VillageEducationalInfrastructurDTO dto = serviceExt.update(VillageEducationalInfrastructurDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageEducationalInfrastructurs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageEducationalInfrastructurs
     */
    @Override
    public PageDTO<VillageEducationalInfrastructurDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEEDUCATIONALINFRASTRUCTUR_GET, new HashMap<>());
        PageDTO<VillageEducationalInfrastructurDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageEducationalInfrastructur by their ID with a specified reason.
     *
     * @param id     The ID of the VillageEducationalInfrastructur to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEEDUCATIONALINFRASTRUCTUR_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageEducationalInfrastructurDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageEducationalInfrastructur by their ID.
     *
     * @param id The ID of the VillageEducationalInfrastructur to retrieve
     * @return The VillageEducationalInfrastructur with the specified ID
     */
    @Override
    public VillageEducationalInfrastructurDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEEDUCATIONALINFRASTRUCTUR_GET, new HashMap<>());
        VillageEducationalInfrastructurDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageEducationalInfrastructurs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageEducationalInfrastructurDTO
     */
    @Override
    public Set<VillageEducationalInfrastructurDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEEDUCATIONALINFRASTRUCTUR_GET, new HashMap<>());
        Set<VillageEducationalInfrastructurDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageEducationalInfrastructurDTO postHookSave(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected VillageEducationalInfrastructurDTO preHookSave(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected VillageEducationalInfrastructurDTO postHookUpdate(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected VillageEducationalInfrastructurDTO preHookUpdate(VillageEducationalInfrastructurDTO VillageEducationalInfrastructurDTO) {
        return VillageEducationalInfrastructurDTO;
    }

    protected VillageEducationalInfrastructurDTO postHookDelete(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageEducationalInfrastructurDTO postHookGetById(VillageEducationalInfrastructurDTO dto) {
        return dto;
    }

    protected PageDTO<VillageEducationalInfrastructurDTO> postHookGetAll(PageDTO<VillageEducationalInfrastructurDTO> result) {
        return result;
    }
}
