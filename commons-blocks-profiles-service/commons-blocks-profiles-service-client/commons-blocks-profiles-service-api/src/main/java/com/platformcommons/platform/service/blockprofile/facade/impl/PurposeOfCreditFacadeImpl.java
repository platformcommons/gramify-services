package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.PurposeOfCreditFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.PurposeOfCreditProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.PurposeOfCreditServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class PurposeOfCreditFacadeImpl implements PurposeOfCreditFacade {

    private final PurposeOfCreditServiceExt serviceExt;
    private final PurposeOfCreditProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String PURPOSEOFCREDIT_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PURPOSEOFCREDIT.CREATE";
    private static final String PURPOSEOFCREDIT_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PURPOSEOFCREDIT.UPDATED";
    private static final String PURPOSEOFCREDIT_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PURPOSEOFCREDIT.DELETE";
    private static final String PURPOSEOFCREDIT_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PURPOSEOFCREDIT.GET";

    public PurposeOfCreditFacadeImpl(PurposeOfCreditServiceExt serviceExt, PurposeOfCreditProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new PurposeOfCredit entry in the system.
     *
     * @param PurposeOfCreditDTO The PurposeOfCredit information to be saved
     * @return The saved PurposeOfCredit data
     */
    @Override
    public PurposeOfCreditDTO save(PurposeOfCreditDTO PurposeOfCreditDTO) {
        log.debug("Entry - save(PurposeOfCreditDTO={})", PurposeOfCreditDTO);
        evaluator.evaluate(PURPOSEOFCREDIT_CREATE, new HashMap<>());
        PurposeOfCreditDTO = preHookSave(PurposeOfCreditDTO);
        PurposeOfCreditDTO dto = serviceExt.save(PurposeOfCreditDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing PurposeOfCredit entry.
     *
     * @param PurposeOfCreditDTO The PurposeOfCredit information to be updated
     * @return The updated PurposeOfCredit data
     */
    @Override
    public PurposeOfCreditDTO update(PurposeOfCreditDTO PurposeOfCreditDTO) {
        log.debug("Entry - update(PurposeOfCreditDTO={})", PurposeOfCreditDTO);
        evaluator.evaluate(PURPOSEOFCREDIT_UPDATE, new HashMap<>());
        PurposeOfCreditDTO = preHookUpdate(PurposeOfCreditDTO);
        PurposeOfCreditDTO dto = serviceExt.update(PurposeOfCreditDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of PurposeOfCredits.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of PurposeOfCredits
     */
    @Override
    public PageDTO<PurposeOfCreditDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(PURPOSEOFCREDIT_GET, new HashMap<>());
        PageDTO<PurposeOfCreditDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a PurposeOfCredit by their ID with a specified reason.
     *
     * @param id     The ID of the PurposeOfCredit to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(PURPOSEOFCREDIT_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        PurposeOfCreditDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a PurposeOfCredit by their ID.
     *
     * @param id The ID of the PurposeOfCredit to retrieve
     * @return The PurposeOfCredit with the specified ID
     */
    @Override
    public PurposeOfCreditDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(PURPOSEOFCREDIT_GET, new HashMap<>());
        PurposeOfCreditDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all PurposeOfCredits by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of PurposeOfCreditDTO
     */
    @Override
    public Set<PurposeOfCreditDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(PURPOSEOFCREDIT_GET, new HashMap<>());
        Set<PurposeOfCreditDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected PurposeOfCreditDTO postHookSave(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected PurposeOfCreditDTO preHookSave(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected PurposeOfCreditDTO postHookUpdate(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected PurposeOfCreditDTO preHookUpdate(PurposeOfCreditDTO PurposeOfCreditDTO) {
        return PurposeOfCreditDTO;
    }

    protected PurposeOfCreditDTO postHookDelete(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected PurposeOfCreditDTO postHookGetById(PurposeOfCreditDTO dto) {
        return dto;
    }

    protected PageDTO<PurposeOfCreditDTO> postHookGetAll(PageDTO<PurposeOfCreditDTO> result) {
        return result;
    }
}
