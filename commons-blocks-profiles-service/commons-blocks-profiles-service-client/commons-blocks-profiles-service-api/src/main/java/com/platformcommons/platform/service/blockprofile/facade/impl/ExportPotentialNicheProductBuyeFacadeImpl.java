package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.ExportPotentialNicheProductBuyeFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.ExportPotentialNicheProductBuyeProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.ExportPotentialNicheProductBuyeServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class ExportPotentialNicheProductBuyeFacadeImpl implements ExportPotentialNicheProductBuyeFacade {

    private final ExportPotentialNicheProductBuyeServiceExt serviceExt;
    private final ExportPotentialNicheProductBuyeProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String EXPORTPOTENTIALNICHEPRODUCTBUYE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXPORTPOTENTIALNICHEPRODUCTBUYE.CREATE";
    private static final String EXPORTPOTENTIALNICHEPRODUCTBUYE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXPORTPOTENTIALNICHEPRODUCTBUYE.UPDATED";
    private static final String EXPORTPOTENTIALNICHEPRODUCTBUYE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXPORTPOTENTIALNICHEPRODUCTBUYE.DELETE";
    private static final String EXPORTPOTENTIALNICHEPRODUCTBUYE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.EXPORTPOTENTIALNICHEPRODUCTBUYE.GET";

    public ExportPotentialNicheProductBuyeFacadeImpl(ExportPotentialNicheProductBuyeServiceExt serviceExt, ExportPotentialNicheProductBuyeProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new ExportPotentialNicheProductBuye entry in the system.
     *
     * @param ExportPotentialNicheProductBuyeDTO The ExportPotentialNicheProductBuye information to be saved
     * @return The saved ExportPotentialNicheProductBuye data
     */
    @Override
    public ExportPotentialNicheProductBuyeDTO save(ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuyeDTO) {
        log.debug("Entry - save(ExportPotentialNicheProductBuyeDTO={})", ExportPotentialNicheProductBuyeDTO);
        evaluator.evaluate(EXPORTPOTENTIALNICHEPRODUCTBUYE_CREATE, new HashMap<>());
        ExportPotentialNicheProductBuyeDTO = preHookSave(ExportPotentialNicheProductBuyeDTO);
        ExportPotentialNicheProductBuyeDTO dto = serviceExt.save(ExportPotentialNicheProductBuyeDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing ExportPotentialNicheProductBuye entry.
     *
     * @param ExportPotentialNicheProductBuyeDTO The ExportPotentialNicheProductBuye information to be updated
     * @return The updated ExportPotentialNicheProductBuye data
     */
    @Override
    public ExportPotentialNicheProductBuyeDTO update(ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuyeDTO) {
        log.debug("Entry - update(ExportPotentialNicheProductBuyeDTO={})", ExportPotentialNicheProductBuyeDTO);
        evaluator.evaluate(EXPORTPOTENTIALNICHEPRODUCTBUYE_UPDATE, new HashMap<>());
        ExportPotentialNicheProductBuyeDTO = preHookUpdate(ExportPotentialNicheProductBuyeDTO);
        ExportPotentialNicheProductBuyeDTO dto = serviceExt.update(ExportPotentialNicheProductBuyeDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of ExportPotentialNicheProductBuyes.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of ExportPotentialNicheProductBuyes
     */
    @Override
    public PageDTO<ExportPotentialNicheProductBuyeDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(EXPORTPOTENTIALNICHEPRODUCTBUYE_GET, new HashMap<>());
        PageDTO<ExportPotentialNicheProductBuyeDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a ExportPotentialNicheProductBuye by their ID with a specified reason.
     *
     * @param id     The ID of the ExportPotentialNicheProductBuye to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(EXPORTPOTENTIALNICHEPRODUCTBUYE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        ExportPotentialNicheProductBuyeDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a ExportPotentialNicheProductBuye by their ID.
     *
     * @param id The ID of the ExportPotentialNicheProductBuye to retrieve
     * @return The ExportPotentialNicheProductBuye with the specified ID
     */
    @Override
    public ExportPotentialNicheProductBuyeDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(EXPORTPOTENTIALNICHEPRODUCTBUYE_GET, new HashMap<>());
        ExportPotentialNicheProductBuyeDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all ExportPotentialNicheProductBuyes by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of ExportPotentialNicheProductBuyeDTO
     */
    @Override
    public Set<ExportPotentialNicheProductBuyeDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(EXPORTPOTENTIALNICHEPRODUCTBUYE_GET, new HashMap<>());
        Set<ExportPotentialNicheProductBuyeDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ExportPotentialNicheProductBuyeDTO postHookSave(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected ExportPotentialNicheProductBuyeDTO preHookSave(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected ExportPotentialNicheProductBuyeDTO postHookUpdate(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected ExportPotentialNicheProductBuyeDTO preHookUpdate(ExportPotentialNicheProductBuyeDTO ExportPotentialNicheProductBuyeDTO) {
        return ExportPotentialNicheProductBuyeDTO;
    }

    protected ExportPotentialNicheProductBuyeDTO postHookDelete(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ExportPotentialNicheProductBuyeDTO postHookGetById(ExportPotentialNicheProductBuyeDTO dto) {
        return dto;
    }

    protected PageDTO<ExportPotentialNicheProductBuyeDTO> postHookGetAll(PageDTO<ExportPotentialNicheProductBuyeDTO> result) {
        return result;
    }
}
