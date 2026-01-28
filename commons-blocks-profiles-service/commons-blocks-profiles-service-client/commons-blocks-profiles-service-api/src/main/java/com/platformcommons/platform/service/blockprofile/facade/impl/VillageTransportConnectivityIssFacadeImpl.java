package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.VillageTransportConnectivityIssFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.VillageTransportConnectivityIssProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.VillageTransportConnectivityIssServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class VillageTransportConnectivityIssFacadeImpl implements VillageTransportConnectivityIssFacade {

    private final VillageTransportConnectivityIssServiceExt serviceExt;
    private final VillageTransportConnectivityIssProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String VILLAGETRANSPORTCONNECTIVITYISS_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGETRANSPORTCONNECTIVITYISS.CREATE";
    private static final String VILLAGETRANSPORTCONNECTIVITYISS_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGETRANSPORTCONNECTIVITYISS.UPDATED";
    private static final String VILLAGETRANSPORTCONNECTIVITYISS_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGETRANSPORTCONNECTIVITYISS.DELETE";
    private static final String VILLAGETRANSPORTCONNECTIVITYISS_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.VILLAGETRANSPORTCONNECTIVITYISS.GET";

    public VillageTransportConnectivityIssFacadeImpl(VillageTransportConnectivityIssServiceExt serviceExt, VillageTransportConnectivityIssProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new VillageTransportConnectivityIss entry in the system.
     *
     * @param VillageTransportConnectivityIssDTO The VillageTransportConnectivityIss information to be saved
     * @return The saved VillageTransportConnectivityIss data
     */
    @Override
    public VillageTransportConnectivityIssDTO save(VillageTransportConnectivityIssDTO VillageTransportConnectivityIssDTO) {
        log.debug("Entry - save(VillageTransportConnectivityIssDTO={})", VillageTransportConnectivityIssDTO);
        evaluator.evaluate(VILLAGETRANSPORTCONNECTIVITYISS_CREATE, new HashMap<>());
        VillageTransportConnectivityIssDTO = preHookSave(VillageTransportConnectivityIssDTO);
        VillageTransportConnectivityIssDTO dto = serviceExt.save(VillageTransportConnectivityIssDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing VillageTransportConnectivityIss entry.
     *
     * @param VillageTransportConnectivityIssDTO The VillageTransportConnectivityIss information to be updated
     * @return The updated VillageTransportConnectivityIss data
     */
    @Override
    public VillageTransportConnectivityIssDTO update(VillageTransportConnectivityIssDTO VillageTransportConnectivityIssDTO) {
        log.debug("Entry - update(VillageTransportConnectivityIssDTO={})", VillageTransportConnectivityIssDTO);
        evaluator.evaluate(VILLAGETRANSPORTCONNECTIVITYISS_UPDATE, new HashMap<>());
        VillageTransportConnectivityIssDTO = preHookUpdate(VillageTransportConnectivityIssDTO);
        VillageTransportConnectivityIssDTO dto = serviceExt.update(VillageTransportConnectivityIssDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of VillageTransportConnectivityIsss.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of VillageTransportConnectivityIsss
     */
    @Override
    public PageDTO<VillageTransportConnectivityIssDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(VILLAGETRANSPORTCONNECTIVITYISS_GET, new HashMap<>());
        PageDTO<VillageTransportConnectivityIssDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a VillageTransportConnectivityIss by their ID with a specified reason.
     *
     * @param id     The ID of the VillageTransportConnectivityIss to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(VILLAGETRANSPORTCONNECTIVITYISS_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        VillageTransportConnectivityIssDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a VillageTransportConnectivityIss by their ID.
     *
     * @param id The ID of the VillageTransportConnectivityIss to retrieve
     * @return The VillageTransportConnectivityIss with the specified ID
     */
    @Override
    public VillageTransportConnectivityIssDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(VILLAGETRANSPORTCONNECTIVITYISS_GET, new HashMap<>());
        VillageTransportConnectivityIssDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all VillageTransportConnectivityIsss by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of VillageTransportConnectivityIssDTO
     */
    @Override
    public Set<VillageTransportConnectivityIssDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(VILLAGETRANSPORTCONNECTIVITYISS_GET, new HashMap<>());
        Set<VillageTransportConnectivityIssDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageTransportConnectivityIssDTO postHookSave(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected VillageTransportConnectivityIssDTO preHookSave(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected VillageTransportConnectivityIssDTO postHookUpdate(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected VillageTransportConnectivityIssDTO preHookUpdate(VillageTransportConnectivityIssDTO VillageTransportConnectivityIssDTO) {
        return VillageTransportConnectivityIssDTO;
    }

    protected VillageTransportConnectivityIssDTO postHookDelete(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageTransportConnectivityIssDTO postHookGetById(VillageTransportConnectivityIssDTO dto) {
        return dto;
    }

    protected PageDTO<VillageTransportConnectivityIssDTO> postHookGetAll(PageDTO<VillageTransportConnectivityIssDTO> result) {
        return result;
    }
}
