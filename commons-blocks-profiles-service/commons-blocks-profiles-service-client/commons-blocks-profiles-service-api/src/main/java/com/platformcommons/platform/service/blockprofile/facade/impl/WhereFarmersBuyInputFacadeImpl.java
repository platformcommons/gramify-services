package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.WhereFarmersBuyInputFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.WhereFarmersBuyInputProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.WhereFarmersBuyInputServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class WhereFarmersBuyInputFacadeImpl implements WhereFarmersBuyInputFacade {

    private final WhereFarmersBuyInputServiceExt serviceExt;
    private final WhereFarmersBuyInputProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String WHEREFARMERSBUYINPUT_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREFARMERSBUYINPUT.CREATE";
    private static final String WHEREFARMERSBUYINPUT_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREFARMERSBUYINPUT.UPDATED";
    private static final String WHEREFARMERSBUYINPUT_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREFARMERSBUYINPUT.DELETE";
    private static final String WHEREFARMERSBUYINPUT_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.WHEREFARMERSBUYINPUT.GET";

    public WhereFarmersBuyInputFacadeImpl(WhereFarmersBuyInputServiceExt serviceExt, WhereFarmersBuyInputProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new WhereFarmersBuyInput entry in the system.
     *
     * @param WhereFarmersBuyInputDTO The WhereFarmersBuyInput information to be saved
     * @return The saved WhereFarmersBuyInput data
     */
    @Override
    public WhereFarmersBuyInputDTO save(WhereFarmersBuyInputDTO WhereFarmersBuyInputDTO) {
        log.debug("Entry - save(WhereFarmersBuyInputDTO={})", WhereFarmersBuyInputDTO);
        evaluator.evaluate(WHEREFARMERSBUYINPUT_CREATE, new HashMap<>());
        WhereFarmersBuyInputDTO = preHookSave(WhereFarmersBuyInputDTO);
        WhereFarmersBuyInputDTO dto = serviceExt.save(WhereFarmersBuyInputDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing WhereFarmersBuyInput entry.
     *
     * @param WhereFarmersBuyInputDTO The WhereFarmersBuyInput information to be updated
     * @return The updated WhereFarmersBuyInput data
     */
    @Override
    public WhereFarmersBuyInputDTO update(WhereFarmersBuyInputDTO WhereFarmersBuyInputDTO) {
        log.debug("Entry - update(WhereFarmersBuyInputDTO={})", WhereFarmersBuyInputDTO);
        evaluator.evaluate(WHEREFARMERSBUYINPUT_UPDATE, new HashMap<>());
        WhereFarmersBuyInputDTO = preHookUpdate(WhereFarmersBuyInputDTO);
        WhereFarmersBuyInputDTO dto = serviceExt.update(WhereFarmersBuyInputDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of WhereFarmersBuyInputs.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of WhereFarmersBuyInputs
     */
    @Override
    public PageDTO<WhereFarmersBuyInputDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(WHEREFARMERSBUYINPUT_GET, new HashMap<>());
        PageDTO<WhereFarmersBuyInputDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a WhereFarmersBuyInput by their ID with a specified reason.
     *
     * @param id     The ID of the WhereFarmersBuyInput to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(WHEREFARMERSBUYINPUT_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        WhereFarmersBuyInputDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a WhereFarmersBuyInput by their ID.
     *
     * @param id The ID of the WhereFarmersBuyInput to retrieve
     * @return The WhereFarmersBuyInput with the specified ID
     */
    @Override
    public WhereFarmersBuyInputDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(WHEREFARMERSBUYINPUT_GET, new HashMap<>());
        WhereFarmersBuyInputDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all WhereFarmersBuyInputs by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of WhereFarmersBuyInputDTO
     */
    @Override
    public Set<WhereFarmersBuyInputDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(WHEREFARMERSBUYINPUT_GET, new HashMap<>());
        Set<WhereFarmersBuyInputDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected WhereFarmersBuyInputDTO postHookSave(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected WhereFarmersBuyInputDTO preHookSave(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected WhereFarmersBuyInputDTO postHookUpdate(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected WhereFarmersBuyInputDTO preHookUpdate(WhereFarmersBuyInputDTO WhereFarmersBuyInputDTO) {
        return WhereFarmersBuyInputDTO;
    }

    protected WhereFarmersBuyInputDTO postHookDelete(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected WhereFarmersBuyInputDTO postHookGetById(WhereFarmersBuyInputDTO dto) {
        return dto;
    }

    protected PageDTO<WhereFarmersBuyInputDTO> postHookGetAll(PageDTO<WhereFarmersBuyInputDTO> result) {
        return result;
    }
}
