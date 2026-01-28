package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.OtherCommunityGroupFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.OtherCommunityGroupProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.OtherCommunityGroupServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class OtherCommunityGroupFacadeImpl implements OtherCommunityGroupFacade {

    private final OtherCommunityGroupServiceExt serviceExt;
    private final OtherCommunityGroupProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String OTHERCOMMUNITYGROUP_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OTHERCOMMUNITYGROUP.CREATE";
    private static final String OTHERCOMMUNITYGROUP_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OTHERCOMMUNITYGROUP.UPDATED";
    private static final String OTHERCOMMUNITYGROUP_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OTHERCOMMUNITYGROUP.DELETE";
    private static final String OTHERCOMMUNITYGROUP_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OTHERCOMMUNITYGROUP.GET";

    public OtherCommunityGroupFacadeImpl(OtherCommunityGroupServiceExt serviceExt, OtherCommunityGroupProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new OtherCommunityGroup entry in the system.
     *
     * @param OtherCommunityGroupDTO The OtherCommunityGroup information to be saved
     * @return The saved OtherCommunityGroup data
     */
    @Override
    public OtherCommunityGroupDTO save(OtherCommunityGroupDTO OtherCommunityGroupDTO) {
        log.debug("Entry - save(OtherCommunityGroupDTO={})", OtherCommunityGroupDTO);
        evaluator.evaluate(OTHERCOMMUNITYGROUP_CREATE, new HashMap<>());
        OtherCommunityGroupDTO = preHookSave(OtherCommunityGroupDTO);
        OtherCommunityGroupDTO dto = serviceExt.save(OtherCommunityGroupDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing OtherCommunityGroup entry.
     *
     * @param OtherCommunityGroupDTO The OtherCommunityGroup information to be updated
     * @return The updated OtherCommunityGroup data
     */
    @Override
    public OtherCommunityGroupDTO update(OtherCommunityGroupDTO OtherCommunityGroupDTO) {
        log.debug("Entry - update(OtherCommunityGroupDTO={})", OtherCommunityGroupDTO);
        evaluator.evaluate(OTHERCOMMUNITYGROUP_UPDATE, new HashMap<>());
        OtherCommunityGroupDTO = preHookUpdate(OtherCommunityGroupDTO);
        OtherCommunityGroupDTO dto = serviceExt.update(OtherCommunityGroupDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of OtherCommunityGroups.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of OtherCommunityGroups
     */
    @Override
    public PageDTO<OtherCommunityGroupDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(OTHERCOMMUNITYGROUP_GET, new HashMap<>());
        PageDTO<OtherCommunityGroupDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a OtherCommunityGroup by their ID with a specified reason.
     *
     * @param id     The ID of the OtherCommunityGroup to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(OTHERCOMMUNITYGROUP_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        OtherCommunityGroupDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a OtherCommunityGroup by their ID.
     *
     * @param id The ID of the OtherCommunityGroup to retrieve
     * @return The OtherCommunityGroup with the specified ID
     */
    @Override
    public OtherCommunityGroupDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(OTHERCOMMUNITYGROUP_GET, new HashMap<>());
        OtherCommunityGroupDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all OtherCommunityGroups by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of OtherCommunityGroupDTO
     */
    @Override
    public Set<OtherCommunityGroupDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(OTHERCOMMUNITYGROUP_GET, new HashMap<>());
        Set<OtherCommunityGroupDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected OtherCommunityGroupDTO postHookSave(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected OtherCommunityGroupDTO preHookSave(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected OtherCommunityGroupDTO postHookUpdate(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected OtherCommunityGroupDTO preHookUpdate(OtherCommunityGroupDTO OtherCommunityGroupDTO) {
        return OtherCommunityGroupDTO;
    }

    protected OtherCommunityGroupDTO postHookDelete(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected OtherCommunityGroupDTO postHookGetById(OtherCommunityGroupDTO dto) {
        return dto;
    }

    protected PageDTO<OtherCommunityGroupDTO> postHookGetAll(PageDTO<OtherCommunityGroupDTO> result) {
        return result;
    }
}
