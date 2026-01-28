package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageMineralAndBiodiversityPrFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageMineralAndBiodiversityPrProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageMineralAndBiodiversityPrServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageMineralAndBiodiversityPrFacadeImpl implements VillageMineralAndBiodiversityPrFacade {

    private final VillageMineralAndBiodiversityPrServiceExt serviceExt;
    private final VillageMineralAndBiodiversityPrProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGEMINERALANDBIODIVERSITYPR_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEMINERALANDBIODIVERSITYPR.CREATE";
    private static final String VILLAGEMINERALANDBIODIVERSITYPR_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEMINERALANDBIODIVERSITYPR.UPDATED";
    private static final String VILLAGEMINERALANDBIODIVERSITYPR_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEMINERALANDBIODIVERSITYPR.DELETE";
    private static final String VILLAGEMINERALANDBIODIVERSITYPR_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGEMINERALANDBIODIVERSITYPR.GET";

    public VillageMineralAndBiodiversityPrFacadeImpl(VillageMineralAndBiodiversityPrServiceExt serviceExt, VillageMineralAndBiodiversityPrProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageMineralAndBiodiversityPr entry in the system.
     *
     * @param VillageMineralAndBiodiversityPrDTO The VillageMineralAndBiodiversityPr information to be saved
     * @return The saved VillageMineralAndBiodiversityPr data
     */
    @Override
    public VillageMineralAndBiodiversityPrDTO save(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPrDTO) {
        log.debug("Entry - save(VillageMineralAndBiodiversityPrDTO={})", VillageMineralAndBiodiversityPrDTO);
        evaluator.evaluate(VILLAGEMINERALANDBIODIVERSITYPR_CREATE, new HashMap<>());
        VillageMineralAndBiodiversityPrDTO = preHookSave(VillageMineralAndBiodiversityPrDTO);
        VillageMineralAndBiodiversityPrDTO dto = serviceExt.save(VillageMineralAndBiodiversityPrDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageMineralAndBiodiversityPr entry.
     *
     * @param VillageMineralAndBiodiversityPrDTO The VillageMineralAndBiodiversityPr information to be updated
     * @return The updated VillageMineralAndBiodiversityPr data
     */
    @Override
    public VillageMineralAndBiodiversityPrDTO update(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPrDTO) {
        log.debug("Entry - update(VillageMineralAndBiodiversityPrDTO={})", VillageMineralAndBiodiversityPrDTO);
        evaluator.evaluate(VILLAGEMINERALANDBIODIVERSITYPR_UPDATE, new HashMap<>());
        VillageMineralAndBiodiversityPrDTO = preHookUpdate(VillageMineralAndBiodiversityPrDTO);
        VillageMineralAndBiodiversityPrDTO dto = serviceExt.update(VillageMineralAndBiodiversityPrDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageMineralAndBiodiversityPrs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageMineralAndBiodiversityPrs
     */
    @Override
    public PageDTO<VillageMineralAndBiodiversityPrDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGEMINERALANDBIODIVERSITYPR_GET, new HashMap<>());
        PageDTO<VillageMineralAndBiodiversityPrDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageMineralAndBiodiversityPr by their ID with a specified reason.
     *
     * @param id     The ID of the VillageMineralAndBiodiversityPr to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGEMINERALANDBIODIVERSITYPR_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageMineralAndBiodiversityPrDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageMineralAndBiodiversityPr by their ID.
     *
     * @param id The ID of the VillageMineralAndBiodiversityPr to retrieve
     * @return The VillageMineralAndBiodiversityPr with the specified ID
     */
    @Override
    public VillageMineralAndBiodiversityPrDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGEMINERALANDBIODIVERSITYPR_GET, new HashMap<>());
        VillageMineralAndBiodiversityPrDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageMineralAndBiodiversityPrs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageMineralAndBiodiversityPrDTO
     */
    @Override
    public Set<VillageMineralAndBiodiversityPrDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGEMINERALANDBIODIVERSITYPR_GET, new HashMap<>());
        Set<VillageMineralAndBiodiversityPrDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }


/**
     * Adds a list of villageCommonFloraList to a VillageMineralAndBiodiversityPr identified by their ID.
     *
     * @param id               The ID of the VillageMineralAndBiodiversityPr to add hobbies to
     * @param villageCommonFloraList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageCommonFloraToVillageMineralAndBiodiversityPr(Long id, List<VillageCommonFloraDTO> villageCommonFloraList){
        serviceExt.addVillageCommonFloraToVillageMineralAndBiodiversityPr(id,villageCommonFloraList);
    }
/**
     * Adds a list of villageCommonFaunaList to a VillageMineralAndBiodiversityPr identified by their ID.
     *
     * @param id               The ID of the VillageMineralAndBiodiversityPr to add hobbies to
     * @param villageCommonFaunaList  to be added
     * @since 1.0.0
     */
    @Override
    public void addVillageCommonFaunaToVillageMineralAndBiodiversityPr(Long id, List<VillageCommonFaunaDTO> villageCommonFaunaList){
        serviceExt.addVillageCommonFaunaToVillageMineralAndBiodiversityPr(id,villageCommonFaunaList);
    }

    /*Hooks to be overridden by subclasses*/
    protected VillageMineralAndBiodiversityPrDTO postHookSave(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected VillageMineralAndBiodiversityPrDTO preHookSave(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected VillageMineralAndBiodiversityPrDTO postHookUpdate(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected VillageMineralAndBiodiversityPrDTO preHookUpdate(VillageMineralAndBiodiversityPrDTO VillageMineralAndBiodiversityPrDTO) {
        return VillageMineralAndBiodiversityPrDTO;
    }

    protected VillageMineralAndBiodiversityPrDTO postHookDelete(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageMineralAndBiodiversityPrDTO postHookGetById(VillageMineralAndBiodiversityPrDTO dto) {
        return dto;
    }

    protected PageDTO<VillageMineralAndBiodiversityPrDTO> postHookGetAll(PageDTO<VillageMineralAndBiodiversityPrDTO> result) {
        return result;
    }
}
