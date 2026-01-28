package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageCasteCompositionFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageCasteCompositionProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageCasteCompositionServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageCasteCompositionFacadeImpl implements VillageCasteCompositionFacade {

    private final VillageCasteCompositionServiceExt serviceExt;
    private final VillageCasteCompositionProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGECASTECOMPOSITION_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECASTECOMPOSITION.CREATE";
    private static final String VILLAGECASTECOMPOSITION_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECASTECOMPOSITION.UPDATED";
    private static final String VILLAGECASTECOMPOSITION_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECASTECOMPOSITION.DELETE";
    private static final String VILLAGECASTECOMPOSITION_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGECASTECOMPOSITION.GET";

    public VillageCasteCompositionFacadeImpl(VillageCasteCompositionServiceExt serviceExt, VillageCasteCompositionProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageCasteComposition entry in the system.
     *
     * @param VillageCasteCompositionDTO The VillageCasteComposition information to be saved
     * @return The saved VillageCasteComposition data
     */
    @Override
    public VillageCasteCompositionDTO save(VillageCasteCompositionDTO VillageCasteCompositionDTO) {
        log.debug("Entry - save(VillageCasteCompositionDTO={})", VillageCasteCompositionDTO);
        evaluator.evaluate(VILLAGECASTECOMPOSITION_CREATE, new HashMap<>());
        VillageCasteCompositionDTO = preHookSave(VillageCasteCompositionDTO);
        VillageCasteCompositionDTO dto = serviceExt.save(VillageCasteCompositionDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageCasteComposition entry.
     *
     * @param VillageCasteCompositionDTO The VillageCasteComposition information to be updated
     * @return The updated VillageCasteComposition data
     */
    @Override
    public VillageCasteCompositionDTO update(VillageCasteCompositionDTO VillageCasteCompositionDTO) {
        log.debug("Entry - update(VillageCasteCompositionDTO={})", VillageCasteCompositionDTO);
        evaluator.evaluate(VILLAGECASTECOMPOSITION_UPDATE, new HashMap<>());
        VillageCasteCompositionDTO = preHookUpdate(VillageCasteCompositionDTO);
        VillageCasteCompositionDTO dto = serviceExt.update(VillageCasteCompositionDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageCasteCompositions.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageCasteCompositions
     */
    @Override
    public PageDTO<VillageCasteCompositionDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGECASTECOMPOSITION_GET, new HashMap<>());
        PageDTO<VillageCasteCompositionDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageCasteComposition by their ID with a specified reason.
     *
     * @param id     The ID of the VillageCasteComposition to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGECASTECOMPOSITION_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageCasteCompositionDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageCasteComposition by their ID.
     *
     * @param id The ID of the VillageCasteComposition to retrieve
     * @return The VillageCasteComposition with the specified ID
     */
    @Override
    public VillageCasteCompositionDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGECASTECOMPOSITION_GET, new HashMap<>());
        VillageCasteCompositionDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageCasteCompositions by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageCasteCompositionDTO
     */
    @Override
    public Set<VillageCasteCompositionDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGECASTECOMPOSITION_GET, new HashMap<>());
        Set<VillageCasteCompositionDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageCasteCompositionDTO postHookSave(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected VillageCasteCompositionDTO preHookSave(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected VillageCasteCompositionDTO postHookUpdate(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected VillageCasteCompositionDTO preHookUpdate(VillageCasteCompositionDTO VillageCasteCompositionDTO) {
        return VillageCasteCompositionDTO;
    }

    protected VillageCasteCompositionDTO postHookDelete(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageCasteCompositionDTO postHookGetById(VillageCasteCompositionDTO dto) {
        return dto;
    }

    protected PageDTO<VillageCasteCompositionDTO> postHookGetAll(PageDTO<VillageCasteCompositionDTO> result) {
        return result;
    }
}
