package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.CommonHealthIssueFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.CommonHealthIssueProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.CommonHealthIssueServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class CommonHealthIssueFacadeImpl implements CommonHealthIssueFacade {

    private final CommonHealthIssueServiceExt serviceExt;
    private final CommonHealthIssueProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String COMMONHEALTHISSUE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONHEALTHISSUE.CREATE";
    private static final String COMMONHEALTHISSUE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONHEALTHISSUE.UPDATED";
    private static final String COMMONHEALTHISSUE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONHEALTHISSUE.DELETE";
    private static final String COMMONHEALTHISSUE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.COMMONHEALTHISSUE.GET";

    public CommonHealthIssueFacadeImpl(CommonHealthIssueServiceExt serviceExt, CommonHealthIssueProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new CommonHealthIssue entry in the system.
     *
     * @param CommonHealthIssueDTO The CommonHealthIssue information to be saved
     * @return The saved CommonHealthIssue data
     */
    @Override
    public CommonHealthIssueDTO save(CommonHealthIssueDTO CommonHealthIssueDTO) {
        log.debug("Entry - save(CommonHealthIssueDTO={})", CommonHealthIssueDTO);
        evaluator.evaluate(COMMONHEALTHISSUE_CREATE, new HashMap<>());
        CommonHealthIssueDTO = preHookSave(CommonHealthIssueDTO);
        CommonHealthIssueDTO dto = serviceExt.save(CommonHealthIssueDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing CommonHealthIssue entry.
     *
     * @param CommonHealthIssueDTO The CommonHealthIssue information to be updated
     * @return The updated CommonHealthIssue data
     */
    @Override
    public CommonHealthIssueDTO update(CommonHealthIssueDTO CommonHealthIssueDTO) {
        log.debug("Entry - update(CommonHealthIssueDTO={})", CommonHealthIssueDTO);
        evaluator.evaluate(COMMONHEALTHISSUE_UPDATE, new HashMap<>());
        CommonHealthIssueDTO = preHookUpdate(CommonHealthIssueDTO);
        CommonHealthIssueDTO dto = serviceExt.update(CommonHealthIssueDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of CommonHealthIssues.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of CommonHealthIssues
     */
    @Override
    public PageDTO<CommonHealthIssueDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(COMMONHEALTHISSUE_GET, new HashMap<>());
        PageDTO<CommonHealthIssueDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a CommonHealthIssue by their ID with a specified reason.
     *
     * @param id     The ID of the CommonHealthIssue to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(COMMONHEALTHISSUE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        CommonHealthIssueDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a CommonHealthIssue by their ID.
     *
     * @param id The ID of the CommonHealthIssue to retrieve
     * @return The CommonHealthIssue with the specified ID
     */
    @Override
    public CommonHealthIssueDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(COMMONHEALTHISSUE_GET, new HashMap<>());
        CommonHealthIssueDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all CommonHealthIssues by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of CommonHealthIssueDTO
     */
    @Override
    public Set<CommonHealthIssueDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(COMMONHEALTHISSUE_GET, new HashMap<>());
        Set<CommonHealthIssueDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CommonHealthIssueDTO postHookSave(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected CommonHealthIssueDTO preHookSave(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected CommonHealthIssueDTO postHookUpdate(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected CommonHealthIssueDTO preHookUpdate(CommonHealthIssueDTO CommonHealthIssueDTO) {
        return CommonHealthIssueDTO;
    }

    protected CommonHealthIssueDTO postHookDelete(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CommonHealthIssueDTO postHookGetById(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected PageDTO<CommonHealthIssueDTO> postHookGetAll(PageDTO<CommonHealthIssueDTO> result) {
        return result;
    }
}
