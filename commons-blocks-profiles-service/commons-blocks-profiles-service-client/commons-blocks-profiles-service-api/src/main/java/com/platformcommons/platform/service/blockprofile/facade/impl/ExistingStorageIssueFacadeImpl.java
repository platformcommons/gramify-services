package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.ExistingStorageIssueFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.ExistingStorageIssueProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.ExistingStorageIssueServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class ExistingStorageIssueFacadeImpl implements ExistingStorageIssueFacade {

    private final ExistingStorageIssueServiceExt serviceExt;
    private final ExistingStorageIssueProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String EXISTINGSTORAGEISSUE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXISTINGSTORAGEISSUE.CREATE";
    private static final String EXISTINGSTORAGEISSUE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXISTINGSTORAGEISSUE.UPDATED";
    private static final String EXISTINGSTORAGEISSUE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXISTINGSTORAGEISSUE.DELETE";
    private static final String EXISTINGSTORAGEISSUE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXISTINGSTORAGEISSUE.GET";

    public ExistingStorageIssueFacadeImpl(ExistingStorageIssueServiceExt serviceExt, ExistingStorageIssueProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new ExistingStorageIssue entry in the system.
     *
     * @param ExistingStorageIssueDTO The ExistingStorageIssue information to be saved
     * @return The saved ExistingStorageIssue data
     */
    @Override
    public ExistingStorageIssueDTO save(ExistingStorageIssueDTO ExistingStorageIssueDTO) {
        log.debug("Entry - save(ExistingStorageIssueDTO={})", ExistingStorageIssueDTO);
        evaluator.evaluate(EXISTINGSTORAGEISSUE_CREATE, new HashMap<>());
        ExistingStorageIssueDTO = preHookSave(ExistingStorageIssueDTO);
        ExistingStorageIssueDTO dto = serviceExt.save(ExistingStorageIssueDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing ExistingStorageIssue entry.
     *
     * @param ExistingStorageIssueDTO The ExistingStorageIssue information to be updated
     * @return The updated ExistingStorageIssue data
     */
    @Override
    public ExistingStorageIssueDTO update(ExistingStorageIssueDTO ExistingStorageIssueDTO) {
        log.debug("Entry - update(ExistingStorageIssueDTO={})", ExistingStorageIssueDTO);
        evaluator.evaluate(EXISTINGSTORAGEISSUE_UPDATE, new HashMap<>());
        ExistingStorageIssueDTO = preHookUpdate(ExistingStorageIssueDTO);
        ExistingStorageIssueDTO dto = serviceExt.update(ExistingStorageIssueDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of ExistingStorageIssues.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ExistingStorageIssues
     */
    @Override
    public PageDTO<ExistingStorageIssueDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(EXISTINGSTORAGEISSUE_GET, new HashMap<>());
        PageDTO<ExistingStorageIssueDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a ExistingStorageIssue by their ID with a specified reason.
     *
     * @param id     The ID of the ExistingStorageIssue to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(EXISTINGSTORAGEISSUE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        ExistingStorageIssueDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a ExistingStorageIssue by their ID.
     *
     * @param id The ID of the ExistingStorageIssue to retrieve
     * @return The ExistingStorageIssue with the specified ID
     */
    @Override
    public ExistingStorageIssueDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(EXISTINGSTORAGEISSUE_GET, new HashMap<>());
        ExistingStorageIssueDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all ExistingStorageIssues by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ExistingStorageIssueDTO
     */
    @Override
    public Set<ExistingStorageIssueDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(EXISTINGSTORAGEISSUE_GET, new HashMap<>());
        Set<ExistingStorageIssueDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ExistingStorageIssueDTO postHookSave(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected ExistingStorageIssueDTO preHookSave(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected ExistingStorageIssueDTO postHookUpdate(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected ExistingStorageIssueDTO preHookUpdate(ExistingStorageIssueDTO ExistingStorageIssueDTO) {
        return ExistingStorageIssueDTO;
    }

    protected ExistingStorageIssueDTO postHookDelete(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ExistingStorageIssueDTO postHookGetById(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected PageDTO<ExistingStorageIssueDTO> postHookGetAll(PageDTO<ExistingStorageIssueDTO> result) {
        return result;
    }
}
