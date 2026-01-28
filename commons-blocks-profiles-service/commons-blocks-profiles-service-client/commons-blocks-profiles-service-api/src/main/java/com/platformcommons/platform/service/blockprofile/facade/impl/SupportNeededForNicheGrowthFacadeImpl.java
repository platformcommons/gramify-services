package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.SupportNeededForNicheGrowthFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.SupportNeededForNicheGrowthProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.SupportNeededForNicheGrowthServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class SupportNeededForNicheGrowthFacadeImpl implements SupportNeededForNicheGrowthFacade {

    private final SupportNeededForNicheGrowthServiceExt serviceExt;
    private final SupportNeededForNicheGrowthProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String SUPPORTNEEDEDFORNICHEGROWTH_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SUPPORTNEEDEDFORNICHEGROWTH.CREATE";
    private static final String SUPPORTNEEDEDFORNICHEGROWTH_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SUPPORTNEEDEDFORNICHEGROWTH.UPDATED";
    private static final String SUPPORTNEEDEDFORNICHEGROWTH_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SUPPORTNEEDEDFORNICHEGROWTH.DELETE";
    private static final String SUPPORTNEEDEDFORNICHEGROWTH_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.SUPPORTNEEDEDFORNICHEGROWTH.GET";

    public SupportNeededForNicheGrowthFacadeImpl(SupportNeededForNicheGrowthServiceExt serviceExt, SupportNeededForNicheGrowthProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new SupportNeededForNicheGrowth entry in the system.
     *
     * @param SupportNeededForNicheGrowthDTO The SupportNeededForNicheGrowth information to be saved
     * @return The saved SupportNeededForNicheGrowth data
     */
    @Override
    public SupportNeededForNicheGrowthDTO save(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowthDTO) {
        log.debug("Entry - save(SupportNeededForNicheGrowthDTO={})", SupportNeededForNicheGrowthDTO);
        evaluator.evaluate(SUPPORTNEEDEDFORNICHEGROWTH_CREATE, new HashMap<>());
        SupportNeededForNicheGrowthDTO = preHookSave(SupportNeededForNicheGrowthDTO);
        SupportNeededForNicheGrowthDTO dto = serviceExt.save(SupportNeededForNicheGrowthDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing SupportNeededForNicheGrowth entry.
     *
     * @param SupportNeededForNicheGrowthDTO The SupportNeededForNicheGrowth information to be updated
     * @return The updated SupportNeededForNicheGrowth data
     */
    @Override
    public SupportNeededForNicheGrowthDTO update(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowthDTO) {
        log.debug("Entry - update(SupportNeededForNicheGrowthDTO={})", SupportNeededForNicheGrowthDTO);
        evaluator.evaluate(SUPPORTNEEDEDFORNICHEGROWTH_UPDATE, new HashMap<>());
        SupportNeededForNicheGrowthDTO = preHookUpdate(SupportNeededForNicheGrowthDTO);
        SupportNeededForNicheGrowthDTO dto = serviceExt.update(SupportNeededForNicheGrowthDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of SupportNeededForNicheGrowths.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of SupportNeededForNicheGrowths
     */
    @Override
    public PageDTO<SupportNeededForNicheGrowthDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(SUPPORTNEEDEDFORNICHEGROWTH_GET, new HashMap<>());
        PageDTO<SupportNeededForNicheGrowthDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a SupportNeededForNicheGrowth by their ID with a specified reason.
     *
     * @param id     The ID of the SupportNeededForNicheGrowth to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(SUPPORTNEEDEDFORNICHEGROWTH_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        SupportNeededForNicheGrowthDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a SupportNeededForNicheGrowth by their ID.
     *
     * @param id The ID of the SupportNeededForNicheGrowth to retrieve
     * @return The SupportNeededForNicheGrowth with the specified ID
     */
    @Override
    public SupportNeededForNicheGrowthDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(SUPPORTNEEDEDFORNICHEGROWTH_GET, new HashMap<>());
        SupportNeededForNicheGrowthDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all SupportNeededForNicheGrowths by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of SupportNeededForNicheGrowthDTO
     */
    @Override
    public Set<SupportNeededForNicheGrowthDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(SUPPORTNEEDEDFORNICHEGROWTH_GET, new HashMap<>());
        Set<SupportNeededForNicheGrowthDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SupportNeededForNicheGrowthDTO postHookSave(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected SupportNeededForNicheGrowthDTO preHookSave(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected SupportNeededForNicheGrowthDTO postHookUpdate(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected SupportNeededForNicheGrowthDTO preHookUpdate(SupportNeededForNicheGrowthDTO SupportNeededForNicheGrowthDTO) {
        return SupportNeededForNicheGrowthDTO;
    }

    protected SupportNeededForNicheGrowthDTO postHookDelete(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SupportNeededForNicheGrowthDTO postHookGetById(SupportNeededForNicheGrowthDTO dto) {
        return dto;
    }

    protected PageDTO<SupportNeededForNicheGrowthDTO> postHookGetAll(PageDTO<SupportNeededForNicheGrowthDTO> result) {
        return result;
    }
}
