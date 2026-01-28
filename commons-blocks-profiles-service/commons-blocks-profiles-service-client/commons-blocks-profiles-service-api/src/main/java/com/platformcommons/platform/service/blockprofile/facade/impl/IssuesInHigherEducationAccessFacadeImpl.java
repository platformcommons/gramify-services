package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.IssuesInHigherEducationAccessFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.IssuesInHigherEducationAccessProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.IssuesInHigherEducationAccessServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class IssuesInHigherEducationAccessFacadeImpl implements IssuesInHigherEducationAccessFacade {

    private final IssuesInHigherEducationAccessServiceExt serviceExt;
    private final IssuesInHigherEducationAccessProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String ISSUESINHIGHEREDUCATIONACCESS_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ISSUESINHIGHEREDUCATIONACCESS.CREATE";
    private static final String ISSUESINHIGHEREDUCATIONACCESS_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ISSUESINHIGHEREDUCATIONACCESS.UPDATED";
    private static final String ISSUESINHIGHEREDUCATIONACCESS_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ISSUESINHIGHEREDUCATIONACCESS.DELETE";
    private static final String ISSUESINHIGHEREDUCATIONACCESS_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ISSUESINHIGHEREDUCATIONACCESS.GET";

    public IssuesInHigherEducationAccessFacadeImpl(IssuesInHigherEducationAccessServiceExt serviceExt, IssuesInHigherEducationAccessProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new IssuesInHigherEducationAccess entry in the system.
     *
     * @param IssuesInHigherEducationAccessDTO The IssuesInHigherEducationAccess information to be saved
     * @return The saved IssuesInHigherEducationAccess data
     */
    @Override
    public IssuesInHigherEducationAccessDTO save(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccessDTO) {
        log.debug("Entry - save(IssuesInHigherEducationAccessDTO={})", IssuesInHigherEducationAccessDTO);
        evaluator.evaluate(ISSUESINHIGHEREDUCATIONACCESS_CREATE, new HashMap<>());
        IssuesInHigherEducationAccessDTO = preHookSave(IssuesInHigherEducationAccessDTO);
        IssuesInHigherEducationAccessDTO dto = serviceExt.save(IssuesInHigherEducationAccessDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing IssuesInHigherEducationAccess entry.
     *
     * @param IssuesInHigherEducationAccessDTO The IssuesInHigherEducationAccess information to be updated
     * @return The updated IssuesInHigherEducationAccess data
     */
    @Override
    public IssuesInHigherEducationAccessDTO update(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccessDTO) {
        log.debug("Entry - update(IssuesInHigherEducationAccessDTO={})", IssuesInHigherEducationAccessDTO);
        evaluator.evaluate(ISSUESINHIGHEREDUCATIONACCESS_UPDATE, new HashMap<>());
        IssuesInHigherEducationAccessDTO = preHookUpdate(IssuesInHigherEducationAccessDTO);
        IssuesInHigherEducationAccessDTO dto = serviceExt.update(IssuesInHigherEducationAccessDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of IssuesInHigherEducationAccesss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of IssuesInHigherEducationAccesss
     */
    @Override
    public PageDTO<IssuesInHigherEducationAccessDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(ISSUESINHIGHEREDUCATIONACCESS_GET, new HashMap<>());
        PageDTO<IssuesInHigherEducationAccessDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a IssuesInHigherEducationAccess by their ID with a specified reason.
     *
     * @param id     The ID of the IssuesInHigherEducationAccess to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(ISSUESINHIGHEREDUCATIONACCESS_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        IssuesInHigherEducationAccessDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a IssuesInHigherEducationAccess by their ID.
     *
     * @param id The ID of the IssuesInHigherEducationAccess to retrieve
     * @return The IssuesInHigherEducationAccess with the specified ID
     */
    @Override
    public IssuesInHigherEducationAccessDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(ISSUESINHIGHEREDUCATIONACCESS_GET, new HashMap<>());
        IssuesInHigherEducationAccessDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all IssuesInHigherEducationAccesss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of IssuesInHigherEducationAccessDTO
     */
    @Override
    public Set<IssuesInHigherEducationAccessDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(ISSUESINHIGHEREDUCATIONACCESS_GET, new HashMap<>());
        Set<IssuesInHigherEducationAccessDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected IssuesInHigherEducationAccessDTO postHookSave(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected IssuesInHigherEducationAccessDTO preHookSave(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected IssuesInHigherEducationAccessDTO postHookUpdate(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected IssuesInHigherEducationAccessDTO preHookUpdate(IssuesInHigherEducationAccessDTO IssuesInHigherEducationAccessDTO) {
        return IssuesInHigherEducationAccessDTO;
    }

    protected IssuesInHigherEducationAccessDTO postHookDelete(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected IssuesInHigherEducationAccessDTO postHookGetById(IssuesInHigherEducationAccessDTO dto) {
        return dto;
    }

    protected PageDTO<IssuesInHigherEducationAccessDTO> postHookGetAll(PageDTO<IssuesInHigherEducationAccessDTO> result) {
        return result;
    }
}
