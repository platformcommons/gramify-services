package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.ItemsUsuallyBoughtLocallyFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.ItemsUsuallyBoughtLocallyProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.ItemsUsuallyBoughtLocallyServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class ItemsUsuallyBoughtLocallyFacadeImpl implements ItemsUsuallyBoughtLocallyFacade {

    private final ItemsUsuallyBoughtLocallyServiceExt serviceExt;
    private final ItemsUsuallyBoughtLocallyProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String ITEMSUSUALLYBOUGHTLOCALLY_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ITEMSUSUALLYBOUGHTLOCALLY.CREATE";
    private static final String ITEMSUSUALLYBOUGHTLOCALLY_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ITEMSUSUALLYBOUGHTLOCALLY.UPDATED";
    private static final String ITEMSUSUALLYBOUGHTLOCALLY_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ITEMSUSUALLYBOUGHTLOCALLY.DELETE";
    private static final String ITEMSUSUALLYBOUGHTLOCALLY_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ITEMSUSUALLYBOUGHTLOCALLY.GET";

    public ItemsUsuallyBoughtLocallyFacadeImpl(ItemsUsuallyBoughtLocallyServiceExt serviceExt, ItemsUsuallyBoughtLocallyProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new ItemsUsuallyBoughtLocally entry in the system.
     *
     * @param ItemsUsuallyBoughtLocallyDTO The ItemsUsuallyBoughtLocally information to be saved
     * @return The saved ItemsUsuallyBoughtLocally data
     */
    @Override
    public ItemsUsuallyBoughtLocallyDTO save(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocallyDTO) {
        log.debug("Entry - save(ItemsUsuallyBoughtLocallyDTO={})", ItemsUsuallyBoughtLocallyDTO);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTLOCALLY_CREATE, new HashMap<>());
        ItemsUsuallyBoughtLocallyDTO = preHookSave(ItemsUsuallyBoughtLocallyDTO);
        ItemsUsuallyBoughtLocallyDTO dto = serviceExt.save(ItemsUsuallyBoughtLocallyDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing ItemsUsuallyBoughtLocally entry.
     *
     * @param ItemsUsuallyBoughtLocallyDTO The ItemsUsuallyBoughtLocally information to be updated
     * @return The updated ItemsUsuallyBoughtLocally data
     */
    @Override
    public ItemsUsuallyBoughtLocallyDTO update(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocallyDTO) {
        log.debug("Entry - update(ItemsUsuallyBoughtLocallyDTO={})", ItemsUsuallyBoughtLocallyDTO);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTLOCALLY_UPDATE, new HashMap<>());
        ItemsUsuallyBoughtLocallyDTO = preHookUpdate(ItemsUsuallyBoughtLocallyDTO);
        ItemsUsuallyBoughtLocallyDTO dto = serviceExt.update(ItemsUsuallyBoughtLocallyDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of ItemsUsuallyBoughtLocallys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ItemsUsuallyBoughtLocallys
     */
    @Override
    public PageDTO<ItemsUsuallyBoughtLocallyDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTLOCALLY_GET, new HashMap<>());
        PageDTO<ItemsUsuallyBoughtLocallyDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a ItemsUsuallyBoughtLocally by their ID with a specified reason.
     *
     * @param id     The ID of the ItemsUsuallyBoughtLocally to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTLOCALLY_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        ItemsUsuallyBoughtLocallyDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a ItemsUsuallyBoughtLocally by their ID.
     *
     * @param id The ID of the ItemsUsuallyBoughtLocally to retrieve
     * @return The ItemsUsuallyBoughtLocally with the specified ID
     */
    @Override
    public ItemsUsuallyBoughtLocallyDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTLOCALLY_GET, new HashMap<>());
        ItemsUsuallyBoughtLocallyDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all ItemsUsuallyBoughtLocallys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ItemsUsuallyBoughtLocallyDTO
     */
    @Override
    public Set<ItemsUsuallyBoughtLocallyDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTLOCALLY_GET, new HashMap<>());
        Set<ItemsUsuallyBoughtLocallyDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ItemsUsuallyBoughtLocallyDTO postHookSave(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtLocallyDTO preHookSave(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtLocallyDTO postHookUpdate(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtLocallyDTO preHookUpdate(ItemsUsuallyBoughtLocallyDTO ItemsUsuallyBoughtLocallyDTO) {
        return ItemsUsuallyBoughtLocallyDTO;
    }

    protected ItemsUsuallyBoughtLocallyDTO postHookDelete(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ItemsUsuallyBoughtLocallyDTO postHookGetById(ItemsUsuallyBoughtLocallyDTO dto) {
        return dto;
    }

    protected PageDTO<ItemsUsuallyBoughtLocallyDTO> postHookGetAll(PageDTO<ItemsUsuallyBoughtLocallyDTO> result) {
        return result;
    }
}
