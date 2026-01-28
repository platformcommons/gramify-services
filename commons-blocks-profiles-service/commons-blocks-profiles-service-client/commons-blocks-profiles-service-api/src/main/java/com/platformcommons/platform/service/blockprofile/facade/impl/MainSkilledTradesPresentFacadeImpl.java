package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.MainSkilledTradesPresentFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.MainSkilledTradesPresentProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.MainSkilledTradesPresentServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class MainSkilledTradesPresentFacadeImpl implements MainSkilledTradesPresentFacade {

    private final MainSkilledTradesPresentServiceExt serviceExt;
    private final MainSkilledTradesPresentProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String MAINSKILLEDTRADESPRESENT_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSKILLEDTRADESPRESENT.CREATE";
    private static final String MAINSKILLEDTRADESPRESENT_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSKILLEDTRADESPRESENT.UPDATED";
    private static final String MAINSKILLEDTRADESPRESENT_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSKILLEDTRADESPRESENT.DELETE";
    private static final String MAINSKILLEDTRADESPRESENT_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.MAINSKILLEDTRADESPRESENT.GET";

    public MainSkilledTradesPresentFacadeImpl(MainSkilledTradesPresentServiceExt serviceExt, MainSkilledTradesPresentProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new MainSkilledTradesPresent entry in the system.
     *
     * @param MainSkilledTradesPresentDTO The MainSkilledTradesPresent information to be saved
     * @return The saved MainSkilledTradesPresent data
     */
    @Override
    public MainSkilledTradesPresentDTO save(MainSkilledTradesPresentDTO MainSkilledTradesPresentDTO) {
        log.debug("Entry - save(MainSkilledTradesPresentDTO={})", MainSkilledTradesPresentDTO);
        evaluator.evaluate(MAINSKILLEDTRADESPRESENT_CREATE, new HashMap<>());
        MainSkilledTradesPresentDTO = preHookSave(MainSkilledTradesPresentDTO);
        MainSkilledTradesPresentDTO dto = serviceExt.save(MainSkilledTradesPresentDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing MainSkilledTradesPresent entry.
     *
     * @param MainSkilledTradesPresentDTO The MainSkilledTradesPresent information to be updated
     * @return The updated MainSkilledTradesPresent data
     */
    @Override
    public MainSkilledTradesPresentDTO update(MainSkilledTradesPresentDTO MainSkilledTradesPresentDTO) {
        log.debug("Entry - update(MainSkilledTradesPresentDTO={})", MainSkilledTradesPresentDTO);
        evaluator.evaluate(MAINSKILLEDTRADESPRESENT_UPDATE, new HashMap<>());
        MainSkilledTradesPresentDTO = preHookUpdate(MainSkilledTradesPresentDTO);
        MainSkilledTradesPresentDTO dto = serviceExt.update(MainSkilledTradesPresentDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of MainSkilledTradesPresents.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of MainSkilledTradesPresents
     */
    @Override
    public PageDTO<MainSkilledTradesPresentDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(MAINSKILLEDTRADESPRESENT_GET, new HashMap<>());
        PageDTO<MainSkilledTradesPresentDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a MainSkilledTradesPresent by their ID with a specified reason.
     *
     * @param id     The ID of the MainSkilledTradesPresent to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(MAINSKILLEDTRADESPRESENT_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        MainSkilledTradesPresentDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a MainSkilledTradesPresent by their ID.
     *
     * @param id The ID of the MainSkilledTradesPresent to retrieve
     * @return The MainSkilledTradesPresent with the specified ID
     */
    @Override
    public MainSkilledTradesPresentDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(MAINSKILLEDTRADESPRESENT_GET, new HashMap<>());
        MainSkilledTradesPresentDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all MainSkilledTradesPresents by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of MainSkilledTradesPresentDTO
     */
    @Override
    public Set<MainSkilledTradesPresentDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(MAINSKILLEDTRADESPRESENT_GET, new HashMap<>());
        Set<MainSkilledTradesPresentDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainSkilledTradesPresentDTO postHookSave(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected MainSkilledTradesPresentDTO preHookSave(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected MainSkilledTradesPresentDTO postHookUpdate(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected MainSkilledTradesPresentDTO preHookUpdate(MainSkilledTradesPresentDTO MainSkilledTradesPresentDTO) {
        return MainSkilledTradesPresentDTO;
    }

    protected MainSkilledTradesPresentDTO postHookDelete(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainSkilledTradesPresentDTO postHookGetById(MainSkilledTradesPresentDTO dto) {
        return dto;
    }

    protected PageDTO<MainSkilledTradesPresentDTO> postHookGetAll(PageDTO<MainSkilledTradesPresentDTO> result) {
        return result;
    }
}
