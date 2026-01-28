package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HorticultureProductFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HorticultureProductProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HorticultureProductServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HorticultureProductFacadeImpl implements HorticultureProductFacade {

    private final HorticultureProductServiceExt serviceExt;
    private final HorticultureProductProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HORTICULTUREPRODUCT_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HORTICULTUREPRODUCT.CREATE";
    private static final String HORTICULTUREPRODUCT_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HORTICULTUREPRODUCT.UPDATED";
    private static final String HORTICULTUREPRODUCT_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HORTICULTUREPRODUCT.DELETE";
    private static final String HORTICULTUREPRODUCT_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HORTICULTUREPRODUCT.GET";

    public HorticultureProductFacadeImpl(HorticultureProductServiceExt serviceExt, HorticultureProductProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HorticultureProduct entry in the system.
     *
     * @param HorticultureProductDTO The HorticultureProduct information to be saved
     * @return The saved HorticultureProduct data
     */
    @Override
    public HorticultureProductDTO save(HorticultureProductDTO HorticultureProductDTO) {
        log.debug("Entry - save(HorticultureProductDTO={})", HorticultureProductDTO);
        evaluator.evaluate(HORTICULTUREPRODUCT_CREATE, new HashMap<>());
        HorticultureProductDTO = preHookSave(HorticultureProductDTO);
        HorticultureProductDTO dto = serviceExt.save(HorticultureProductDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HorticultureProduct entry.
     *
     * @param HorticultureProductDTO The HorticultureProduct information to be updated
     * @return The updated HorticultureProduct data
     */
    @Override
    public HorticultureProductDTO update(HorticultureProductDTO HorticultureProductDTO) {
        log.debug("Entry - update(HorticultureProductDTO={})", HorticultureProductDTO);
        evaluator.evaluate(HORTICULTUREPRODUCT_UPDATE, new HashMap<>());
        HorticultureProductDTO = preHookUpdate(HorticultureProductDTO);
        HorticultureProductDTO dto = serviceExt.update(HorticultureProductDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HorticultureProducts.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HorticultureProducts
     */
    @Override
    public PageDTO<HorticultureProductDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HORTICULTUREPRODUCT_GET, new HashMap<>());
        PageDTO<HorticultureProductDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HorticultureProduct by their ID with a specified reason.
     *
     * @param id     The ID of the HorticultureProduct to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HORTICULTUREPRODUCT_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HorticultureProductDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HorticultureProduct by their ID.
     *
     * @param id The ID of the HorticultureProduct to retrieve
     * @return The HorticultureProduct with the specified ID
     */
    @Override
    public HorticultureProductDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HORTICULTUREPRODUCT_GET, new HashMap<>());
        HorticultureProductDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HorticultureProducts by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HorticultureProductDTO
     */
    @Override
    public Set<HorticultureProductDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HORTICULTUREPRODUCT_GET, new HashMap<>());
        Set<HorticultureProductDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HorticultureProductDTO postHookSave(HorticultureProductDTO dto) {
        return dto;
    }

    protected HorticultureProductDTO preHookSave(HorticultureProductDTO dto) {
        return dto;
    }

    protected HorticultureProductDTO postHookUpdate(HorticultureProductDTO dto) {
        return dto;
    }

    protected HorticultureProductDTO preHookUpdate(HorticultureProductDTO HorticultureProductDTO) {
        return HorticultureProductDTO;
    }

    protected HorticultureProductDTO postHookDelete(HorticultureProductDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HorticultureProductDTO postHookGetById(HorticultureProductDTO dto) {
        return dto;
    }

    protected PageDTO<HorticultureProductDTO> postHookGetAll(PageDTO<HorticultureProductDTO> result) {
        return result;
    }
}
