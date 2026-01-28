package com.platformcommons.platform.service.blockprofile.facade.impl;

import com.platformcommons.platform.service.access.PolicyEvaluator;
import com.platformcommons.platform.service.dto.base.PageDTO;

import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.facade.BlockVillageDemographicsProfileFacade;
import com.platformcommons.platform.service.blockprofile.messaging.producer.BlockVillageDemographicsProfileProducer;
import com.platformcommons.platform.service.blockprofile.service_ext.BlockVillageDemographicsProfileServiceExt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Slf4j

public class BlockVillageDemographicsProfileFacadeImpl implements BlockVillageDemographicsProfileFacade {

    private final BlockVillageDemographicsProfileServiceExt serviceExt;
    private final BlockVillageDemographicsProfileProducer producer;
    private final PolicyEvaluator evaluator;

    private static final String BLOCKVILLAGEDEMOGRAPHICSPROFILE_CREATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.BLOCKVILLAGEDEMOGRAPHICSPROFILE.CREATE";
    private static final String BLOCKVILLAGEDEMOGRAPHICSPROFILE_UPDATE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.BLOCKVILLAGEDEMOGRAPHICSPROFILE.UPDATED";
    private static final String BLOCKVILLAGEDEMOGRAPHICSPROFILE_DELETE = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.BLOCKVILLAGEDEMOGRAPHICSPROFILE.DELETE";
    private static final String BLOCKVILLAGEDEMOGRAPHICSPROFILE_GET = "METHOD_CODE.COMMONS-BLOCKS-PROFILES-SERVICE.BLOCKVILLAGEDEMOGRAPHICSPROFILE.GET";

    public BlockVillageDemographicsProfileFacadeImpl(BlockVillageDemographicsProfileServiceExt serviceExt, BlockVillageDemographicsProfileProducer producer, PolicyEvaluator evaluator) {
        this.serviceExt = serviceExt;
        this.producer = producer;
        this.evaluator = evaluator;
    }


    /**
     * Creates a new BlockVillageDemographicsProfile entry in the system.
     *
     * @param BlockVillageDemographicsProfileDTO The BlockVillageDemographicsProfile information to be saved
     * @return The saved BlockVillageDemographicsProfile data
     */
    @Override
    public BlockVillageDemographicsProfileDTO save(BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfileDTO) {
        log.debug("Entry - save(BlockVillageDemographicsProfileDTO={})", BlockVillageDemographicsProfileDTO);
        evaluator.evaluate(BLOCKVILLAGEDEMOGRAPHICSPROFILE_CREATE, new HashMap<>());
        BlockVillageDemographicsProfileDTO = preHookSave(BlockVillageDemographicsProfileDTO);
        BlockVillageDemographicsProfileDTO dto = serviceExt.save(BlockVillageDemographicsProfileDTO);
        dto = postHookSave(dto);
        producer.created(dto);
        log.debug("Exit - save() with result: {}", dto);
        return dto;
    }


    /**
     * Updates an existing BlockVillageDemographicsProfile entry.
     *
     * @param BlockVillageDemographicsProfileDTO The BlockVillageDemographicsProfile information to be updated
     * @return The updated BlockVillageDemographicsProfile data
     */
    @Override
    public BlockVillageDemographicsProfileDTO update(BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfileDTO) {
        log.debug("Entry - update(BlockVillageDemographicsProfileDTO={})", BlockVillageDemographicsProfileDTO);
        evaluator.evaluate(BLOCKVILLAGEDEMOGRAPHICSPROFILE_UPDATE, new HashMap<>());
        BlockVillageDemographicsProfileDTO = preHookUpdate(BlockVillageDemographicsProfileDTO);
        BlockVillageDemographicsProfileDTO dto = serviceExt.update(BlockVillageDemographicsProfileDTO);
        dto = postHookUpdate(dto);
        producer.updated(dto);
        log.debug("Exit - update() with result: {}", dto);
        return dto;
    }


    /**
     * Retrieves a paginated list of BlockVillageDemographicsProfiles.
     *
     * @param page Zero-based page index
     * @param size The size of the page to be returned
     * @return A page of BlockVillageDemographicsProfiles
     */
    @Override
    public PageDTO<BlockVillageDemographicsProfileDTO> getAllPage(Integer page, Integer size) {
        log.debug("Entry - getAllPage(page={}, size={})", page, size);
        evaluator.evaluate(BLOCKVILLAGEDEMOGRAPHICSPROFILE_GET, new HashMap<>());
        PageDTO<BlockVillageDemographicsProfileDTO> result = serviceExt.getByPage(page, size);
        result = postHookGetAll(result);
        log.debug("Exit - getAllPage() with result: {}", result);
        return result;
    }


    /**
     * Deletes a BlockVillageDemographicsProfile by their ID with a specified reason.
     *
     * @param id     The ID of the BlockVillageDemographicsProfile to delete
     * @param reason The reason for deletion
     */
    @Override
    public void delete(Long id, String reason) {
        log.debug("Entry - delete(id={}, reason={})", id, reason);
        evaluator.evaluate(BLOCKVILLAGEDEMOGRAPHICSPROFILE_DELETE, new HashMap<>());
        preHookDelete(id, reason);
        BlockVillageDemographicsProfileDTO dto = serviceExt.deleteById(id, reason);
        dto = postHookDelete(dto);
        producer.deleted(dto);
        log.debug("Exit - delete()");
    }

    /**
     * Retrieves a BlockVillageDemographicsProfile by their ID.
     *
     * @param id The ID of the BlockVillageDemographicsProfile to retrieve
     * @return The BlockVillageDemographicsProfile with the specified ID
     */
    @Override
    public BlockVillageDemographicsProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        evaluator.evaluate(BLOCKVILLAGEDEMOGRAPHICSPROFILE_GET, new HashMap<>());
        BlockVillageDemographicsProfileDTO dto = serviceExt.getById(id);
        dto = postHookGetById(dto);
        log.debug("Exit - getById() with result: {}", dto);
        return dto;
    }

    /**
     * Retrieves all BlockVillageDemographicsProfiles by the given set of IDs.
     *
     * @param ids Set of IDs to fetch
     * @return Set of BlockVillageDemographicsProfileDTO
     */
    @Override
    public Set<BlockVillageDemographicsProfileDTO> getAllByIds(Set<Long> ids) {
        log.debug("Entry - getAllByIds(ids={})", ids);
        evaluator.evaluate(BLOCKVILLAGEDEMOGRAPHICSPROFILE_GET, new HashMap<>());
        Set<BlockVillageDemographicsProfileDTO> result = serviceExt.getAllByIds(ids);
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected BlockVillageDemographicsProfileDTO postHookSave(BlockVillageDemographicsProfileDTO dto) {
        return dto;
    }

    protected BlockVillageDemographicsProfileDTO preHookSave(BlockVillageDemographicsProfileDTO dto) {
        return dto;
    }

    protected BlockVillageDemographicsProfileDTO postHookUpdate(BlockVillageDemographicsProfileDTO dto) {
        return dto;
    }

    protected BlockVillageDemographicsProfileDTO preHookUpdate(BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfileDTO) {
        return BlockVillageDemographicsProfileDTO;
    }

    protected BlockVillageDemographicsProfileDTO postHookDelete(BlockVillageDemographicsProfileDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected BlockVillageDemographicsProfileDTO postHookGetById(BlockVillageDemographicsProfileDTO dto) {
        return dto;
    }

    protected PageDTO<BlockVillageDemographicsProfileDTO> postHookGetAll(PageDTO<BlockVillageDemographicsProfileDTO> result) {
        return result;
    }
}
