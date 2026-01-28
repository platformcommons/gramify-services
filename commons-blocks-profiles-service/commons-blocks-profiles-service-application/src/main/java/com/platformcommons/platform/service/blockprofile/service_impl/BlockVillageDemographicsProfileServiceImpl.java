package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.BlockVillageDemographicsProfileService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.*;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j

public class BlockVillageDemographicsProfileServiceImpl implements BlockVillageDemographicsProfileService {

    private final BlockVillageDemographicsProfileDTOAssembler assembler;
    private final BlockVillageDemographicsProfileRepository repository;


    public BlockVillageDemographicsProfileServiceImpl(
        BlockVillageDemographicsProfileRepository repository, BlockVillageDemographicsProfileDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new BlockVillageDemographicsProfile.
     *
     * @param BlockVillageDemographicsProfile the BlockVillageDemographicsProfile DTO to save
     * @return the saved BlockVillageDemographicsProfile DTO
     */
    @Transactional
    @Override
    public BlockVillageDemographicsProfileDTO save(BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfile) {
        log.debug("Entry - save(BlockVillageDemographicsProfile={})", BlockVillageDemographicsProfile);
        BlockVillageDemographicsProfile = preHookSave(BlockVillageDemographicsProfile);
        BlockVillageDemographicsProfile entity = assembler.fromDTO(BlockVillageDemographicsProfile);
        BlockVillageDemographicsProfileDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing BlockVillageDemographicsProfile.
     *
     * @param BlockVillageDemographicsProfile the BlockVillageDemographicsProfile DTO to update
     * @return the updated BlockVillageDemographicsProfile DTO
     * @throws NotFoundException if the BlockVillageDemographicsProfile is not found
     */
    @Transactional
    @Override
    public BlockVillageDemographicsProfileDTO update(BlockVillageDemographicsProfileDTO BlockVillageDemographicsProfile) {
        log.debug("Entry - update(BlockVillageDemographicsProfile={})", BlockVillageDemographicsProfile);
        BlockVillageDemographicsProfile = preHookUpdate(BlockVillageDemographicsProfile);
        BlockVillageDemographicsProfile saved = repository.findById(BlockVillageDemographicsProfile.getId()).orElseThrow(() -> new NotFoundException("BlockVillageDemographicsProfile not found"));
        saved.update(assembler.fromDTO(BlockVillageDemographicsProfile));
        saved = repository.save(saved);
        BlockVillageDemographicsProfileDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of BlockVillageDemographicsProfiles.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing BlockVillageDemographicsProfile DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<BlockVillageDemographicsProfileDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<BlockVillageDemographicsProfile> result = repository.findAll(PageRequest.of(page, size));
        Set<BlockVillageDemographicsProfileDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<BlockVillageDemographicsProfileDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a BlockVillageDemographicsProfile by ID.
     *
     * @param id     the ID of the BlockVillageDemographicsProfile to delete
     * @param reason the reason for deletion
     * @return the deleted BlockVillageDemographicsProfile DTO
     * @throws NotFoundException if the BlockVillageDemographicsProfile is not found
     */
    @Transactional
    @Override
    public BlockVillageDemographicsProfileDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        BlockVillageDemographicsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("BlockVillageDemographicsProfile not found"));
        saved.deactivate(reason);
        repository.save(saved);
        BlockVillageDemographicsProfileDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a BlockVillageDemographicsProfile by ID.
     *
     * @param id the ID of the BlockVillageDemographicsProfile to retrieve
     * @return the BlockVillageDemographicsProfile DTO
     * @throws NotFoundException if the BlockVillageDemographicsProfile is not found
     */
    @Transactional(readOnly = true)
    @Override
    public BlockVillageDemographicsProfileDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        BlockVillageDemographicsProfile saved = repository.findById(id).orElseThrow(() -> new NotFoundException("BlockVillageDemographicsProfile not found"));
        BlockVillageDemographicsProfileDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<BlockVillageDemographicsProfileDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<BlockVillageDemographicsProfile> savedData = repository.findAllById(ids);
        Set<BlockVillageDemographicsProfileDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
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
