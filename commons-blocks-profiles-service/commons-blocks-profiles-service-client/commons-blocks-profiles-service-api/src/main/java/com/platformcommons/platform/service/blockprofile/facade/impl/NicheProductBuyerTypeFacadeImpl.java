package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.NicheProductBuyerTypeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.NicheProductBuyerTypeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.NicheProductBuyerTypeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class NicheProductBuyerTypeFacadeImpl implements NicheProductBuyerTypeFacade {

    private final NicheProductBuyerTypeServiceExt serviceExt;
    private final NicheProductBuyerTypeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String NICHEPRODUCTBUYERTYPE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NICHEPRODUCTBUYERTYPE.CREATE";
    private static final String NICHEPRODUCTBUYERTYPE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NICHEPRODUCTBUYERTYPE.UPDATED";
    private static final String NICHEPRODUCTBUYERTYPE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NICHEPRODUCTBUYERTYPE.DELETE";
    private static final String NICHEPRODUCTBUYERTYPE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.NICHEPRODUCTBUYERTYPE.GET";

    public NicheProductBuyerTypeFacadeImpl(NicheProductBuyerTypeServiceExt serviceExt, NicheProductBuyerTypeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new NicheProductBuyerType entry in the system.
     *
     * @param NicheProductBuyerTypeDTO The NicheProductBuyerType information to be saved
     * @return The saved NicheProductBuyerType data
     */
    @Override
    public NicheProductBuyerTypeDTO save(NicheProductBuyerTypeDTO NicheProductBuyerTypeDTO) {
        log.debug("Entry - save(NicheProductBuyerTypeDTO={})", NicheProductBuyerTypeDTO);
        evaluator.evaluate(NICHEPRODUCTBUYERTYPE_CREATE, new HashMap<>());
        NicheProductBuyerTypeDTO = preHookSave(NicheProductBuyerTypeDTO);
        NicheProductBuyerTypeDTO dto = serviceExt.save(NicheProductBuyerTypeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing NicheProductBuyerType entry.
     *
     * @param NicheProductBuyerTypeDTO The NicheProductBuyerType information to be updated
     * @return The updated NicheProductBuyerType data
     */
    @Override
    public NicheProductBuyerTypeDTO update(NicheProductBuyerTypeDTO NicheProductBuyerTypeDTO) {
        log.debug("Entry - update(NicheProductBuyerTypeDTO={})", NicheProductBuyerTypeDTO);
        evaluator.evaluate(NICHEPRODUCTBUYERTYPE_UPDATE, new HashMap<>());
        NicheProductBuyerTypeDTO = preHookUpdate(NicheProductBuyerTypeDTO);
        NicheProductBuyerTypeDTO dto = serviceExt.update(NicheProductBuyerTypeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of NicheProductBuyerTypes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of NicheProductBuyerTypes
     */
    @Override
    public PageDTO<NicheProductBuyerTypeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(NICHEPRODUCTBUYERTYPE_GET, new HashMap<>());
        PageDTO<NicheProductBuyerTypeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a NicheProductBuyerType by their ID with a specified reason.
     *
     * @param id     The ID of the NicheProductBuyerType to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(NICHEPRODUCTBUYERTYPE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        NicheProductBuyerTypeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a NicheProductBuyerType by their ID.
     *
     * @param id The ID of the NicheProductBuyerType to retrieve
     * @return The NicheProductBuyerType with the specified ID
     */
    @Override
    public NicheProductBuyerTypeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(NICHEPRODUCTBUYERTYPE_GET, new HashMap<>());
        NicheProductBuyerTypeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all NicheProductBuyerTypes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of NicheProductBuyerTypeDTO
     */
    @Override
    public Set<NicheProductBuyerTypeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(NICHEPRODUCTBUYERTYPE_GET, new HashMap<>());
        Set<NicheProductBuyerTypeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected NicheProductBuyerTypeDTO postHookSave(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected NicheProductBuyerTypeDTO preHookSave(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected NicheProductBuyerTypeDTO postHookUpdate(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected NicheProductBuyerTypeDTO preHookUpdate(NicheProductBuyerTypeDTO NicheProductBuyerTypeDTO) {
        return NicheProductBuyerTypeDTO;
    }

    protected NicheProductBuyerTypeDTO postHookDelete(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected NicheProductBuyerTypeDTO postHookGetById(NicheProductBuyerTypeDTO dto) {
        return dto;
    }

    protected PageDTO<NicheProductBuyerTypeDTO> postHookGetAll(PageDTO<NicheProductBuyerTypeDTO> result) {
        return result;
    }
}
