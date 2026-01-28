package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.ProductionSeasonFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.ProductionSeasonProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.ProductionSeasonServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class ProductionSeasonFacadeImpl implements ProductionSeasonFacade {

    private final ProductionSeasonServiceExt serviceExt;
    private final ProductionSeasonProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String PRODUCTIONSEASON_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PRODUCTIONSEASON.CREATE";
    private static final String PRODUCTIONSEASON_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PRODUCTIONSEASON.UPDATED";
    private static final String PRODUCTIONSEASON_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PRODUCTIONSEASON.DELETE";
    private static final String PRODUCTIONSEASON_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PRODUCTIONSEASON.GET";

    public ProductionSeasonFacadeImpl(ProductionSeasonServiceExt serviceExt, ProductionSeasonProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new ProductionSeason entry in the system.
     *
     * @param ProductionSeasonDTO The ProductionSeason information to be saved
     * @return The saved ProductionSeason data
     */
    @Override
    public ProductionSeasonDTO save(ProductionSeasonDTO ProductionSeasonDTO) {
        log.debug("Entry - save(ProductionSeasonDTO={})", ProductionSeasonDTO);
        evaluator.evaluate(PRODUCTIONSEASON_CREATE, new HashMap<>());
        ProductionSeasonDTO = preHookSave(ProductionSeasonDTO);
        ProductionSeasonDTO dto = serviceExt.save(ProductionSeasonDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing ProductionSeason entry.
     *
     * @param ProductionSeasonDTO The ProductionSeason information to be updated
     * @return The updated ProductionSeason data
     */
    @Override
    public ProductionSeasonDTO update(ProductionSeasonDTO ProductionSeasonDTO) {
        log.debug("Entry - update(ProductionSeasonDTO={})", ProductionSeasonDTO);
        evaluator.evaluate(PRODUCTIONSEASON_UPDATE, new HashMap<>());
        ProductionSeasonDTO = preHookUpdate(ProductionSeasonDTO);
        ProductionSeasonDTO dto = serviceExt.update(ProductionSeasonDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of ProductionSeasons.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ProductionSeasons
     */
    @Override
    public PageDTO<ProductionSeasonDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(PRODUCTIONSEASON_GET, new HashMap<>());
        PageDTO<ProductionSeasonDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a ProductionSeason by their ID with a specified reason.
     *
     * @param id     The ID of the ProductionSeason to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(PRODUCTIONSEASON_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        ProductionSeasonDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a ProductionSeason by their ID.
     *
     * @param id The ID of the ProductionSeason to retrieve
     * @return The ProductionSeason with the specified ID
     */
    @Override
    public ProductionSeasonDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(PRODUCTIONSEASON_GET, new HashMap<>());
        ProductionSeasonDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all ProductionSeasons by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ProductionSeasonDTO
     */
    @Override
    public Set<ProductionSeasonDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(PRODUCTIONSEASON_GET, new HashMap<>());
        Set<ProductionSeasonDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ProductionSeasonDTO postHookSave(ProductionSeasonDTO dto) {
        return dto;
    }

    protected ProductionSeasonDTO preHookSave(ProductionSeasonDTO dto) {
        return dto;
    }

    protected ProductionSeasonDTO postHookUpdate(ProductionSeasonDTO dto) {
        return dto;
    }

    protected ProductionSeasonDTO preHookUpdate(ProductionSeasonDTO ProductionSeasonDTO) {
        return ProductionSeasonDTO;
    }

    protected ProductionSeasonDTO postHookDelete(ProductionSeasonDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ProductionSeasonDTO postHookGetById(ProductionSeasonDTO dto) {
        return dto;
    }

    protected PageDTO<ProductionSeasonDTO> postHookGetAll(PageDTO<ProductionSeasonDTO> result) {
        return result;
    }
}
