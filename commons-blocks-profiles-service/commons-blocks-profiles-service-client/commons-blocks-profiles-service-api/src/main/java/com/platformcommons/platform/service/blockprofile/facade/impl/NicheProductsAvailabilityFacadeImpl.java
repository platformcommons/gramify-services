package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.NicheProductsAvailabilityFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.NicheProductsAvailabilityProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.NicheProductsAvailabilityServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class NicheProductsAvailabilityFacadeImpl implements NicheProductsAvailabilityFacade {

    private final NicheProductsAvailabilityServiceExt serviceExt;
    private final NicheProductsAvailabilityProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String NICHEPRODUCTSAVAILABILITY_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NICHEPRODUCTSAVAILABILITY.CREATE";
    private static final String NICHEPRODUCTSAVAILABILITY_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NICHEPRODUCTSAVAILABILITY.UPDATED";
    private static final String NICHEPRODUCTSAVAILABILITY_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NICHEPRODUCTSAVAILABILITY.DELETE";
    private static final String NICHEPRODUCTSAVAILABILITY_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NICHEPRODUCTSAVAILABILITY.GET";

    public NicheProductsAvailabilityFacadeImpl(NicheProductsAvailabilityServiceExt serviceExt, NicheProductsAvailabilityProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new NicheProductsAvailability entry in the system.
     *
     * @param NicheProductsAvailabilityDTO The NicheProductsAvailability information to be saved
     * @return The saved NicheProductsAvailability data
     */
    @Override
    public NicheProductsAvailabilityDTO save(NicheProductsAvailabilityDTO NicheProductsAvailabilityDTO) {
        log.debug("Entry - save(NicheProductsAvailabilityDTO={})", NicheProductsAvailabilityDTO);
        evaluator.evaluate(NICHEPRODUCTSAVAILABILITY_CREATE, new HashMap<>());
        NicheProductsAvailabilityDTO = preHookSave(NicheProductsAvailabilityDTO);
        NicheProductsAvailabilityDTO dto = serviceExt.save(NicheProductsAvailabilityDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing NicheProductsAvailability entry.
     *
     * @param NicheProductsAvailabilityDTO The NicheProductsAvailability information to be updated
     * @return The updated NicheProductsAvailability data
     */
    @Override
    public NicheProductsAvailabilityDTO update(NicheProductsAvailabilityDTO NicheProductsAvailabilityDTO) {
        log.debug("Entry - update(NicheProductsAvailabilityDTO={})", NicheProductsAvailabilityDTO);
        evaluator.evaluate(NICHEPRODUCTSAVAILABILITY_UPDATE, new HashMap<>());
        NicheProductsAvailabilityDTO = preHookUpdate(NicheProductsAvailabilityDTO);
        NicheProductsAvailabilityDTO dto = serviceExt.update(NicheProductsAvailabilityDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of NicheProductsAvailabilitys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of NicheProductsAvailabilitys
     */
    @Override
    public PageDTO<NicheProductsAvailabilityDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(NICHEPRODUCTSAVAILABILITY_GET, new HashMap<>());
        PageDTO<NicheProductsAvailabilityDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a NicheProductsAvailability by their ID with a specified reason.
     *
     * @param id     The ID of the NicheProductsAvailability to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(NICHEPRODUCTSAVAILABILITY_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        NicheProductsAvailabilityDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a NicheProductsAvailability by their ID.
     *
     * @param id The ID of the NicheProductsAvailability to retrieve
     * @return The NicheProductsAvailability with the specified ID
     */
    @Override
    public NicheProductsAvailabilityDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(NICHEPRODUCTSAVAILABILITY_GET, new HashMap<>());
        NicheProductsAvailabilityDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all NicheProductsAvailabilitys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NicheProductsAvailabilityDTO
     */
    @Override
    public Set<NicheProductsAvailabilityDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(NICHEPRODUCTSAVAILABILITY_GET, new HashMap<>());
        Set<NicheProductsAvailabilityDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected NicheProductsAvailabilityDTO postHookSave(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected NicheProductsAvailabilityDTO preHookSave(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected NicheProductsAvailabilityDTO postHookUpdate(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected NicheProductsAvailabilityDTO preHookUpdate(NicheProductsAvailabilityDTO NicheProductsAvailabilityDTO) {
        return NicheProductsAvailabilityDTO;
    }

    protected NicheProductsAvailabilityDTO postHookDelete(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected NicheProductsAvailabilityDTO postHookGetById(NicheProductsAvailabilityDTO dto) {
        return dto;
    }

    protected PageDTO<NicheProductsAvailabilityDTO> postHookGetAll(PageDTO<NicheProductsAvailabilityDTO> result) {
        return result;
    }
}
