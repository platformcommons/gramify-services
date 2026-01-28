package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.ItemsUsuallyBoughtFromOutsideFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.ItemsUsuallyBoughtFromOutsideProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.ItemsUsuallyBoughtFromOutsideServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class ItemsUsuallyBoughtFromOutsideFacadeImpl implements ItemsUsuallyBoughtFromOutsideFacade {

    private final ItemsUsuallyBoughtFromOutsideServiceExt serviceExt;
    private final ItemsUsuallyBoughtFromOutsideProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String ITEMSUSUALLYBOUGHTFROMOUTSIDE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ITEMSUSUALLYBOUGHTFROMOUTSIDE.CREATE";
    private static final String ITEMSUSUALLYBOUGHTFROMOUTSIDE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ITEMSUSUALLYBOUGHTFROMOUTSIDE.UPDATED";
    private static final String ITEMSUSUALLYBOUGHTFROMOUTSIDE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ITEMSUSUALLYBOUGHTFROMOUTSIDE.DELETE";
    private static final String ITEMSUSUALLYBOUGHTFROMOUTSIDE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.ITEMSUSUALLYBOUGHTFROMOUTSIDE.GET";

    public ItemsUsuallyBoughtFromOutsideFacadeImpl(ItemsUsuallyBoughtFromOutsideServiceExt serviceExt, ItemsUsuallyBoughtFromOutsideProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new ItemsUsuallyBoughtFromOutside entry in the system.
     *
     * @param ItemsUsuallyBoughtFromOutsideDTO The ItemsUsuallyBoughtFromOutside information to be saved
     * @return The saved ItemsUsuallyBoughtFromOutside data
     */
    @Override
    public ItemsUsuallyBoughtFromOutsideDTO save(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutsideDTO) {
        log.debug("Entry - save(ItemsUsuallyBoughtFromOutsideDTO={})", ItemsUsuallyBoughtFromOutsideDTO);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTFROMOUTSIDE_CREATE, new HashMap<>());
        ItemsUsuallyBoughtFromOutsideDTO = preHookSave(ItemsUsuallyBoughtFromOutsideDTO);
        ItemsUsuallyBoughtFromOutsideDTO dto = serviceExt.save(ItemsUsuallyBoughtFromOutsideDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing ItemsUsuallyBoughtFromOutside entry.
     *
     * @param ItemsUsuallyBoughtFromOutsideDTO The ItemsUsuallyBoughtFromOutside information to be updated
     * @return The updated ItemsUsuallyBoughtFromOutside data
     */
    @Override
    public ItemsUsuallyBoughtFromOutsideDTO update(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutsideDTO) {
        log.debug("Entry - update(ItemsUsuallyBoughtFromOutsideDTO={})", ItemsUsuallyBoughtFromOutsideDTO);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTFROMOUTSIDE_UPDATE, new HashMap<>());
        ItemsUsuallyBoughtFromOutsideDTO = preHookUpdate(ItemsUsuallyBoughtFromOutsideDTO);
        ItemsUsuallyBoughtFromOutsideDTO dto = serviceExt.update(ItemsUsuallyBoughtFromOutsideDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of ItemsUsuallyBoughtFromOutsides.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ItemsUsuallyBoughtFromOutsides
     */
    @Override
    public PageDTO<ItemsUsuallyBoughtFromOutsideDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTFROMOUTSIDE_GET, new HashMap<>());
        PageDTO<ItemsUsuallyBoughtFromOutsideDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a ItemsUsuallyBoughtFromOutside by their ID with a specified reason.
     *
     * @param id     The ID of the ItemsUsuallyBoughtFromOutside to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTFROMOUTSIDE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        ItemsUsuallyBoughtFromOutsideDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a ItemsUsuallyBoughtFromOutside by their ID.
     *
     * @param id The ID of the ItemsUsuallyBoughtFromOutside to retrieve
     * @return The ItemsUsuallyBoughtFromOutside with the specified ID
     */
    @Override
    public ItemsUsuallyBoughtFromOutsideDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTFROMOUTSIDE_GET, new HashMap<>());
        ItemsUsuallyBoughtFromOutsideDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all ItemsUsuallyBoughtFromOutsides by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ItemsUsuallyBoughtFromOutsideDTO
     */
    @Override
    public Set<ItemsUsuallyBoughtFromOutsideDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(ITEMSUSUALLYBOUGHTFROMOUTSIDE_GET, new HashMap<>());
        Set<ItemsUsuallyBoughtFromOutsideDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ItemsUsuallyBoughtFromOutsideDTO postHookSave(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtFromOutsideDTO preHookSave(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtFromOutsideDTO postHookUpdate(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected ItemsUsuallyBoughtFromOutsideDTO preHookUpdate(ItemsUsuallyBoughtFromOutsideDTO ItemsUsuallyBoughtFromOutsideDTO) {
        return ItemsUsuallyBoughtFromOutsideDTO;
    }

    protected ItemsUsuallyBoughtFromOutsideDTO postHookDelete(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ItemsUsuallyBoughtFromOutsideDTO postHookGetById(ItemsUsuallyBoughtFromOutsideDTO dto) {
        return dto;
    }

    protected PageDTO<ItemsUsuallyBoughtFromOutsideDTO> postHookGetAll(PageDTO<ItemsUsuallyBoughtFromOutsideDTO> result) {
        return result;
    }
}
