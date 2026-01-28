package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.RenewableInfraTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.RenewableInfraTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.RenewableInfraTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class RenewableInfraTypeFacadeImpl implements RenewableInfraTypeFacade {

    private final RenewableInfraTypeServiceExt serviceExt;
    private final RenewableInfraTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String RENEWABLEINFRATYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.RENEWABLEINFRATYPE.CREATE";
    private static final String RENEWABLEINFRATYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.RENEWABLEINFRATYPE.UPDATED";
    private static final String RENEWABLEINFRATYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.RENEWABLEINFRATYPE.DELETE";
    private static final String RENEWABLEINFRATYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.RENEWABLEINFRATYPE.GET";

    public RenewableInfraTypeFacadeImpl(RenewableInfraTypeServiceExt serviceExt, RenewableInfraTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new RenewableInfraType entry in the system.
     *
     * @param RenewableInfraTypeDTO The RenewableInfraType information to be saved
     * @return The saved RenewableInfraType data
     */
    @Override
    public RenewableInfraTypeDTO save(RenewableInfraTypeDTO RenewableInfraTypeDTO) {
        log.debug("Entry - save(RenewableInfraTypeDTO={})", RenewableInfraTypeDTO);
        evaluator.evaluate(RENEWABLEINFRATYPE_CREATE, new HashMap<>());
        RenewableInfraTypeDTO = preHookSave(RenewableInfraTypeDTO);
        RenewableInfraTypeDTO dto = serviceExt.save(RenewableInfraTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing RenewableInfraType entry.
     *
     * @param RenewableInfraTypeDTO The RenewableInfraType information to be updated
     * @return The updated RenewableInfraType data
     */
    @Override
    public RenewableInfraTypeDTO update(RenewableInfraTypeDTO RenewableInfraTypeDTO) {
        log.debug("Entry - update(RenewableInfraTypeDTO={})", RenewableInfraTypeDTO);
        evaluator.evaluate(RENEWABLEINFRATYPE_UPDATE, new HashMap<>());
        RenewableInfraTypeDTO = preHookUpdate(RenewableInfraTypeDTO);
        RenewableInfraTypeDTO dto = serviceExt.update(RenewableInfraTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of RenewableInfraTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of RenewableInfraTypes
     */
    @Override
    public PageDTO<RenewableInfraTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(RENEWABLEINFRATYPE_GET, new HashMap<>());
        PageDTO<RenewableInfraTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a RenewableInfraType by their ID with a specified reason.
     *
     * @param id     The ID of the RenewableInfraType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(RENEWABLEINFRATYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        RenewableInfraTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a RenewableInfraType by their ID.
     *
     * @param id The ID of the RenewableInfraType to retrieve
     * @return The RenewableInfraType with the specified ID
     */
    @Override
    public RenewableInfraTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(RENEWABLEINFRATYPE_GET, new HashMap<>());
        RenewableInfraTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all RenewableInfraTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of RenewableInfraTypeDTO
     */
    @Override
    public Set<RenewableInfraTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(RENEWABLEINFRATYPE_GET, new HashMap<>());
        Set<RenewableInfraTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected RenewableInfraTypeDTO postHookSave(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected RenewableInfraTypeDTO preHookSave(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected RenewableInfraTypeDTO postHookUpdate(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected RenewableInfraTypeDTO preHookUpdate(RenewableInfraTypeDTO RenewableInfraTypeDTO) {
        return RenewableInfraTypeDTO;
    }

    protected RenewableInfraTypeDTO postHookDelete(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected RenewableInfraTypeDTO postHookGetById(RenewableInfraTypeDTO dto) {
        return dto;
    }

    protected PageDTO<RenewableInfraTypeDTO> postHookGetAll(PageDTO<RenewableInfraTypeDTO> result) {
        return result;
    }
}
