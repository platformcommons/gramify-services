package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.ExportPotentialSurplusProduceTyFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.ExportPotentialSurplusProduceTyProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.ExportPotentialSurplusProduceTyServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class ExportPotentialSurplusProduceTyFacadeImpl implements ExportPotentialSurplusProduceTyFacade {

    private final ExportPotentialSurplusProduceTyServiceExt serviceExt;
    private final ExportPotentialSurplusProduceTyProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String EXPORTPOTENTIALSURPLUSPRODUCETY_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXPORTPOTENTIALSURPLUSPRODUCETY.CREATE";
    private static final String EXPORTPOTENTIALSURPLUSPRODUCETY_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXPORTPOTENTIALSURPLUSPRODUCETY.UPDATED";
    private static final String EXPORTPOTENTIALSURPLUSPRODUCETY_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXPORTPOTENTIALSURPLUSPRODUCETY.DELETE";
    private static final String EXPORTPOTENTIALSURPLUSPRODUCETY_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXPORTPOTENTIALSURPLUSPRODUCETY.GET";

    public ExportPotentialSurplusProduceTyFacadeImpl(ExportPotentialSurplusProduceTyServiceExt serviceExt, ExportPotentialSurplusProduceTyProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new ExportPotentialSurplusProduceTy entry in the system.
     *
     * @param ExportPotentialSurplusProduceTyDTO The ExportPotentialSurplusProduceTy information to be saved
     * @return The saved ExportPotentialSurplusProduceTy data
     */
    @Override
    public ExportPotentialSurplusProduceTyDTO save(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTyDTO) {
        log.debug("Entry - save(ExportPotentialSurplusProduceTyDTO={})", ExportPotentialSurplusProduceTyDTO);
        evaluator.evaluate(EXPORTPOTENTIALSURPLUSPRODUCETY_CREATE, new HashMap<>());
        ExportPotentialSurplusProduceTyDTO = preHookSave(ExportPotentialSurplusProduceTyDTO);
        ExportPotentialSurplusProduceTyDTO dto = serviceExt.save(ExportPotentialSurplusProduceTyDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing ExportPotentialSurplusProduceTy entry.
     *
     * @param ExportPotentialSurplusProduceTyDTO The ExportPotentialSurplusProduceTy information to be updated
     * @return The updated ExportPotentialSurplusProduceTy data
     */
    @Override
    public ExportPotentialSurplusProduceTyDTO update(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTyDTO) {
        log.debug("Entry - update(ExportPotentialSurplusProduceTyDTO={})", ExportPotentialSurplusProduceTyDTO);
        evaluator.evaluate(EXPORTPOTENTIALSURPLUSPRODUCETY_UPDATE, new HashMap<>());
        ExportPotentialSurplusProduceTyDTO = preHookUpdate(ExportPotentialSurplusProduceTyDTO);
        ExportPotentialSurplusProduceTyDTO dto = serviceExt.update(ExportPotentialSurplusProduceTyDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of ExportPotentialSurplusProduceTys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ExportPotentialSurplusProduceTys
     */
    @Override
    public PageDTO<ExportPotentialSurplusProduceTyDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(EXPORTPOTENTIALSURPLUSPRODUCETY_GET, new HashMap<>());
        PageDTO<ExportPotentialSurplusProduceTyDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a ExportPotentialSurplusProduceTy by their ID with a specified reason.
     *
     * @param id     The ID of the ExportPotentialSurplusProduceTy to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(EXPORTPOTENTIALSURPLUSPRODUCETY_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        ExportPotentialSurplusProduceTyDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a ExportPotentialSurplusProduceTy by their ID.
     *
     * @param id The ID of the ExportPotentialSurplusProduceTy to retrieve
     * @return The ExportPotentialSurplusProduceTy with the specified ID
     */
    @Override
    public ExportPotentialSurplusProduceTyDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(EXPORTPOTENTIALSURPLUSPRODUCETY_GET, new HashMap<>());
        ExportPotentialSurplusProduceTyDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all ExportPotentialSurplusProduceTys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ExportPotentialSurplusProduceTyDTO
     */
    @Override
    public Set<ExportPotentialSurplusProduceTyDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(EXPORTPOTENTIALSURPLUSPRODUCETY_GET, new HashMap<>());
        Set<ExportPotentialSurplusProduceTyDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ExportPotentialSurplusProduceTyDTO postHookSave(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected ExportPotentialSurplusProduceTyDTO preHookSave(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected ExportPotentialSurplusProduceTyDTO postHookUpdate(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected ExportPotentialSurplusProduceTyDTO preHookUpdate(ExportPotentialSurplusProduceTyDTO ExportPotentialSurplusProduceTyDTO) {
        return ExportPotentialSurplusProduceTyDTO;
    }

    protected ExportPotentialSurplusProduceTyDTO postHookDelete(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ExportPotentialSurplusProduceTyDTO postHookGetById(ExportPotentialSurplusProduceTyDTO dto) {
        return dto;
    }

    protected PageDTO<ExportPotentialSurplusProduceTyDTO> postHookGetAll(PageDTO<ExportPotentialSurplusProduceTyDTO> result) {
        return result;
    }
}
