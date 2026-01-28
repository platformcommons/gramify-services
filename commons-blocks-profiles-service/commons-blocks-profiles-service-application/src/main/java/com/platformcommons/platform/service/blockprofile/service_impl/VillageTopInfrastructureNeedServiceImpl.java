package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageTopInfrastructureNeedService;


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

public class VillageTopInfrastructureNeedServiceImpl implements VillageTopInfrastructureNeedService {

    private final VillageTopInfrastructureNeedDTOAssembler assembler;
    private final VillageTopInfrastructureNeedRepository repository;


    public VillageTopInfrastructureNeedServiceImpl(
        VillageTopInfrastructureNeedRepository repository, VillageTopInfrastructureNeedDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageTopInfrastructureNeed.
     *
     * @param VillageTopInfrastructureNeed the VillageTopInfrastructureNeed DTO to save
     * @return the saved VillageTopInfrastructureNeed DTO
     */
    @Transactional
    @Override
    public VillageTopInfrastructureNeedDTO save(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeed) {
        log.debug("Entry - save(VillageTopInfrastructureNeed={})", VillageTopInfrastructureNeed);
        VillageTopInfrastructureNeed = preHookSave(VillageTopInfrastructureNeed);
        VillageTopInfrastructureNeed entity = assembler.fromDTO(VillageTopInfrastructureNeed);
        VillageTopInfrastructureNeedDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageTopInfrastructureNeed.
     *
     * @param VillageTopInfrastructureNeed the VillageTopInfrastructureNeed DTO to update
     * @return the updated VillageTopInfrastructureNeed DTO
     * @throws NotFoundException if the VillageTopInfrastructureNeed is not found
     */
    @Transactional
    @Override
    public VillageTopInfrastructureNeedDTO update(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeed) {
        log.debug("Entry - update(VillageTopInfrastructureNeed={})", VillageTopInfrastructureNeed);
        VillageTopInfrastructureNeed = preHookUpdate(VillageTopInfrastructureNeed);
        VillageTopInfrastructureNeed saved = repository.findById(VillageTopInfrastructureNeed.getId()).orElseThrow(() -> new NotFoundException("VillageTopInfrastructureNeed not found"));
        saved.update(assembler.fromDTO(VillageTopInfrastructureNeed));
        saved = repository.save(saved);
        VillageTopInfrastructureNeedDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageTopInfrastructureNeeds.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageTopInfrastructureNeed DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageTopInfrastructureNeedDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageTopInfrastructureNeed> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageTopInfrastructureNeedDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageTopInfrastructureNeedDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageTopInfrastructureNeed by ID.
     *
     * @param id     the ID of the VillageTopInfrastructureNeed to delete
     * @param reason the reason for deletion
     * @return the deleted VillageTopInfrastructureNeed DTO
     * @throws NotFoundException if the VillageTopInfrastructureNeed is not found
     */
    @Transactional
    @Override
    public VillageTopInfrastructureNeedDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageTopInfrastructureNeed saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageTopInfrastructureNeed not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageTopInfrastructureNeedDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageTopInfrastructureNeed by ID.
     *
     * @param id the ID of the VillageTopInfrastructureNeed to retrieve
     * @return the VillageTopInfrastructureNeed DTO
     * @throws NotFoundException if the VillageTopInfrastructureNeed is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageTopInfrastructureNeedDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageTopInfrastructureNeed saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageTopInfrastructureNeed not found"));
        VillageTopInfrastructureNeedDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageTopInfrastructureNeedDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageTopInfrastructureNeed> savedData = repository.findAllById(ids);
        Set<VillageTopInfrastructureNeedDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageTopInfrastructureNeedDTO postHookSave(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected VillageTopInfrastructureNeedDTO preHookSave(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected VillageTopInfrastructureNeedDTO postHookUpdate(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected VillageTopInfrastructureNeedDTO preHookUpdate(VillageTopInfrastructureNeedDTO VillageTopInfrastructureNeedDTO) {
        return VillageTopInfrastructureNeedDTO;
    }

    protected VillageTopInfrastructureNeedDTO postHookDelete(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageTopInfrastructureNeedDTO postHookGetById(VillageTopInfrastructureNeedDTO dto) {
        return dto;
    }

    protected PageDTO<VillageTopInfrastructureNeedDTO> postHookGetAll(PageDTO<VillageTopInfrastructureNeedDTO> result) {
        return result;
    }




}
