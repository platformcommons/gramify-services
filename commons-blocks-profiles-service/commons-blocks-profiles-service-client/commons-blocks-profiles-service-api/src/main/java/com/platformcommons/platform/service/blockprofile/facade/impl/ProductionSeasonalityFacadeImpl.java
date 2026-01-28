package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.ProductionSeasonalityFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.ProductionSeasonalityProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.ProductionSeasonalityServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class ProductionSeasonalityFacadeImpl implements ProductionSeasonalityFacade {

    private final ProductionSeasonalityServiceExt serviceExt;
    private final ProductionSeasonalityProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String PRODUCTIONSEASONALITY_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PRODUCTIONSEASONALITY.CREATE";
    private static final String PRODUCTIONSEASONALITY_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PRODUCTIONSEASONALITY.UPDATED";
    private static final String PRODUCTIONSEASONALITY_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PRODUCTIONSEASONALITY.DELETE";
    private static final String PRODUCTIONSEASONALITY_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.PRODUCTIONSEASONALITY.GET";

    public ProductionSeasonalityFacadeImpl(ProductionSeasonalityServiceExt serviceExt, ProductionSeasonalityProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new ProductionSeasonality entry in the system.
     *
     * @param ProductionSeasonalityDTO The ProductionSeasonality information to be saved
     * @return The saved ProductionSeasonality data
     */
    @Override
    public ProductionSeasonalityDTO save(ProductionSeasonalityDTO ProductionSeasonalityDTO) {
        log.debug("Entry - save(ProductionSeasonalityDTO={})", ProductionSeasonalityDTO);
        evaluator.evaluate(PRODUCTIONSEASONALITY_CREATE, new HashMap<>());
        ProductionSeasonalityDTO = preHookSave(ProductionSeasonalityDTO);
        ProductionSeasonalityDTO dto = serviceExt.save(ProductionSeasonalityDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing ProductionSeasonality entry.
     *
     * @param ProductionSeasonalityDTO The ProductionSeasonality information to be updated
     * @return The updated ProductionSeasonality data
     */
    @Override
    public ProductionSeasonalityDTO update(ProductionSeasonalityDTO ProductionSeasonalityDTO) {
        log.debug("Entry - update(ProductionSeasonalityDTO={})", ProductionSeasonalityDTO);
        evaluator.evaluate(PRODUCTIONSEASONALITY_UPDATE, new HashMap<>());
        ProductionSeasonalityDTO = preHookUpdate(ProductionSeasonalityDTO);
        ProductionSeasonalityDTO dto = serviceExt.update(ProductionSeasonalityDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of ProductionSeasonalitys.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ProductionSeasonalitys
     */
    @Override
    public PageDTO<ProductionSeasonalityDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(PRODUCTIONSEASONALITY_GET, new HashMap<>());
        PageDTO<ProductionSeasonalityDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a ProductionSeasonality by their ID with a specified reason.
     *
     * @param id     The ID of the ProductionSeasonality to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(PRODUCTIONSEASONALITY_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        ProductionSeasonalityDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a ProductionSeasonality by their ID.
     *
     * @param id The ID of the ProductionSeasonality to retrieve
     * @return The ProductionSeasonality with the specified ID
     */
    @Override
    public ProductionSeasonalityDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(PRODUCTIONSEASONALITY_GET, new HashMap<>());
        ProductionSeasonalityDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all ProductionSeasonalitys by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ProductionSeasonalityDTO
     */
    @Override
    public Set<ProductionSeasonalityDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(PRODUCTIONSEASONALITY_GET, new HashMap<>());
        Set<ProductionSeasonalityDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ProductionSeasonalityDTO postHookSave(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected ProductionSeasonalityDTO preHookSave(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected ProductionSeasonalityDTO postHookUpdate(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected ProductionSeasonalityDTO preHookUpdate(ProductionSeasonalityDTO ProductionSeasonalityDTO) {
        return ProductionSeasonalityDTO;
    }

    protected ProductionSeasonalityDTO postHookDelete(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ProductionSeasonalityDTO postHookGetById(ProductionSeasonalityDTO dto) {
        return dto;
    }

    protected PageDTO<ProductionSeasonalityDTO> postHookGetAll(PageDTO<ProductionSeasonalityDTO> result) {
        return result;
    }
}
