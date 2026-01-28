package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.HHLoanSourceFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.HHLoanSourceProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.HHLoanSourceServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class HHLoanSourceFacadeImpl implements HHLoanSourceFacade {

    private final HHLoanSourceServiceExt serviceExt;
    private final HHLoanSourceProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String HHLOANSOURCE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHLOANSOURCE.CREATE";
    private static final String HHLOANSOURCE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHLOANSOURCE.UPDATED";
    private static final String HHLOANSOURCE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHLOANSOURCE.DELETE";
    private static final String HHLOANSOURCE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.HHLOANSOURCE.GET";

    public HHLoanSourceFacadeImpl(HHLoanSourceServiceExt serviceExt, HHLoanSourceProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new HHLoanSource entry in the system.
     *
     * @param HHLoanSourceDTO The HHLoanSource information to be saved
     * @return The saved HHLoanSource data
     */
    @Override
    public HHLoanSourceDTO save(HHLoanSourceDTO HHLoanSourceDTO) {
        log.debug("Entry - save(HHLoanSourceDTO={})", HHLoanSourceDTO);
        evaluator.evaluate(HHLOANSOURCE_CREATE, new HashMap<>());
        HHLoanSourceDTO = preHookSave(HHLoanSourceDTO);
        HHLoanSourceDTO dto = serviceExt.save(HHLoanSourceDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing HHLoanSource entry.
     *
     * @param HHLoanSourceDTO The HHLoanSource information to be updated
     * @return The updated HHLoanSource data
     */
    @Override
    public HHLoanSourceDTO update(HHLoanSourceDTO HHLoanSourceDTO) {
        log.debug("Entry - update(HHLoanSourceDTO={})", HHLoanSourceDTO);
        evaluator.evaluate(HHLOANSOURCE_UPDATE, new HashMap<>());
        HHLoanSourceDTO = preHookUpdate(HHLoanSourceDTO);
        HHLoanSourceDTO dto = serviceExt.update(HHLoanSourceDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of HHLoanSources.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of HHLoanSources
     */
    @Override
    public PageDTO<HHLoanSourceDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(HHLOANSOURCE_GET, new HashMap<>());
        PageDTO<HHLoanSourceDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a HHLoanSource by their ID with a specified reason.
     *
     * @param id     The ID of the HHLoanSource to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(HHLOANSOURCE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        HHLoanSourceDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a HHLoanSource by their ID.
     *
     * @param id The ID of the HHLoanSource to retrieve
     * @return The HHLoanSource with the specified ID
     */
    @Override
    public HHLoanSourceDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(HHLOANSOURCE_GET, new HashMap<>());
        HHLoanSourceDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all HHLoanSources by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of HHLoanSourceDTO
     */
    @Override
    public Set<HHLoanSourceDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(HHLOANSOURCE_GET, new HashMap<>());
        Set<HHLoanSourceDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected HHLoanSourceDTO postHookSave(HHLoanSourceDTO dto) {
        return dto;
    }

    protected HHLoanSourceDTO preHookSave(HHLoanSourceDTO dto) {
        return dto;
    }

    protected HHLoanSourceDTO postHookUpdate(HHLoanSourceDTO dto) {
        return dto;
    }

    protected HHLoanSourceDTO preHookUpdate(HHLoanSourceDTO HHLoanSourceDTO) {
        return HHLoanSourceDTO;
    }

    protected HHLoanSourceDTO postHookDelete(HHLoanSourceDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected HHLoanSourceDTO postHookGetById(HHLoanSourceDTO dto) {
        return dto;
    }

    protected PageDTO<HHLoanSourceDTO> postHookGetAll(PageDTO<HHLoanSourceDTO> result) {
        return result;
    }
}
