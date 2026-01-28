package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.OtherIndustryTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.OtherIndustryTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.OtherIndustryTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class OtherIndustryTypeFacadeImpl implements OtherIndustryTypeFacade {

    private final OtherIndustryTypeServiceExt serviceExt;
    private final OtherIndustryTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String OTHERINDUSTRYTYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OTHERINDUSTRYTYPE.CREATE";
    private static final String OTHERINDUSTRYTYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OTHERINDUSTRYTYPE.UPDATED";
    private static final String OTHERINDUSTRYTYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OTHERINDUSTRYTYPE.DELETE";
    private static final String OTHERINDUSTRYTYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.OTHERINDUSTRYTYPE.GET";

    public OtherIndustryTypeFacadeImpl(OtherIndustryTypeServiceExt serviceExt, OtherIndustryTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new OtherIndustryType entry in the system.
     *
     * @param OtherIndustryTypeDTO The OtherIndustryType information to be saved
     * @return The saved OtherIndustryType data
     */
    @Override
    public OtherIndustryTypeDTO save(OtherIndustryTypeDTO OtherIndustryTypeDTO) {
        log.debug("Entry - save(OtherIndustryTypeDTO={})", OtherIndustryTypeDTO);
        evaluator.evaluate(OTHERINDUSTRYTYPE_CREATE, new HashMap<>());
        OtherIndustryTypeDTO = preHookSave(OtherIndustryTypeDTO);
        OtherIndustryTypeDTO dto = serviceExt.save(OtherIndustryTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing OtherIndustryType entry.
     *
     * @param OtherIndustryTypeDTO The OtherIndustryType information to be updated
     * @return The updated OtherIndustryType data
     */
    @Override
    public OtherIndustryTypeDTO update(OtherIndustryTypeDTO OtherIndustryTypeDTO) {
        log.debug("Entry - update(OtherIndustryTypeDTO={})", OtherIndustryTypeDTO);
        evaluator.evaluate(OTHERINDUSTRYTYPE_UPDATE, new HashMap<>());
        OtherIndustryTypeDTO = preHookUpdate(OtherIndustryTypeDTO);
        OtherIndustryTypeDTO dto = serviceExt.update(OtherIndustryTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of OtherIndustryTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of OtherIndustryTypes
     */
    @Override
    public PageDTO<OtherIndustryTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(OTHERINDUSTRYTYPE_GET, new HashMap<>());
        PageDTO<OtherIndustryTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a OtherIndustryType by their ID with a specified reason.
     *
     * @param id     The ID of the OtherIndustryType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(OTHERINDUSTRYTYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        OtherIndustryTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a OtherIndustryType by their ID.
     *
     * @param id The ID of the OtherIndustryType to retrieve
     * @return The OtherIndustryType with the specified ID
     */
    @Override
    public OtherIndustryTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(OTHERINDUSTRYTYPE_GET, new HashMap<>());
        OtherIndustryTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all OtherIndustryTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of OtherIndustryTypeDTO
     */
    @Override
    public Set<OtherIndustryTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(OTHERINDUSTRYTYPE_GET, new HashMap<>());
        Set<OtherIndustryTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected OtherIndustryTypeDTO postHookSave(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected OtherIndustryTypeDTO preHookSave(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected OtherIndustryTypeDTO postHookUpdate(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected OtherIndustryTypeDTO preHookUpdate(OtherIndustryTypeDTO OtherIndustryTypeDTO) {
        return OtherIndustryTypeDTO;
    }

    protected OtherIndustryTypeDTO postHookDelete(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected OtherIndustryTypeDTO postHookGetById(OtherIndustryTypeDTO dto) {
        return dto;
    }

    protected PageDTO<OtherIndustryTypeDTO> postHookGetAll(PageDTO<OtherIndustryTypeDTO> result) {
        return result;
    }
}
