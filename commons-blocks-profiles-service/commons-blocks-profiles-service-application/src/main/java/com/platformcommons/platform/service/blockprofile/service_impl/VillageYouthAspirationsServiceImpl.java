package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.VillageYouthAspirationsService;


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

public class VillageYouthAspirationsServiceImpl implements VillageYouthAspirationsService {

    private final VillageYouthAspirationsDTOAssembler assembler;
    private final VillageYouthAspirationsRepository repository;


    public VillageYouthAspirationsServiceImpl(
        VillageYouthAspirationsRepository repository, VillageYouthAspirationsDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new VillageYouthAspirations.
     *
     * @param VillageYouthAspirations the VillageYouthAspirations DTO to save
     * @return the saved VillageYouthAspirations DTO
     */
    @Transactional
    @Override
    public VillageYouthAspirationsDTO save(VillageYouthAspirationsDTO VillageYouthAspirations) {
        log.debug("Entry - save(VillageYouthAspirations={})", VillageYouthAspirations);
        VillageYouthAspirations = preHookSave(VillageYouthAspirations);
        VillageYouthAspirations entity = assembler.fromDTO(VillageYouthAspirations);
        VillageYouthAspirationsDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing VillageYouthAspirations.
     *
     * @param VillageYouthAspirations the VillageYouthAspirations DTO to update
     * @return the updated VillageYouthAspirations DTO
     * @throws NotFoundException if the VillageYouthAspirations is not found
     */
    @Transactional
    @Override
    public VillageYouthAspirationsDTO update(VillageYouthAspirationsDTO VillageYouthAspirations) {
        log.debug("Entry - update(VillageYouthAspirations={})", VillageYouthAspirations);
        VillageYouthAspirations = preHookUpdate(VillageYouthAspirations);
        VillageYouthAspirations saved = repository.findById(VillageYouthAspirations.getId()).orElseThrow(() -> new NotFoundException("VillageYouthAspirations not found"));
        saved.update(assembler.fromDTO(VillageYouthAspirations));
        saved = repository.save(saved);
        VillageYouthAspirationsDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of VillageYouthAspirationss.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing VillageYouthAspirations DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<VillageYouthAspirationsDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<VillageYouthAspirations> result = repository.findAll(PageRequest.of(page, size));
        Set<VillageYouthAspirationsDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<VillageYouthAspirationsDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a VillageYouthAspirations by ID.
     *
     * @param id     the ID of the VillageYouthAspirations to delete
     * @param reason the reason for deletion
     * @return the deleted VillageYouthAspirations DTO
     * @throws NotFoundException if the VillageYouthAspirations is not found
     */
    @Transactional
    @Override
    public VillageYouthAspirationsDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        VillageYouthAspirations saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageYouthAspirations not found"));
        saved.deactivate(reason);
        repository.save(saved);
        VillageYouthAspirationsDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a VillageYouthAspirations by ID.
     *
     * @param id the ID of the VillageYouthAspirations to retrieve
     * @return the VillageYouthAspirations DTO
     * @throws NotFoundException if the VillageYouthAspirations is not found
     */
    @Transactional(readOnly = true)
    @Override
    public VillageYouthAspirationsDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        VillageYouthAspirations saved = repository.findById(id).orElseThrow(() -> new NotFoundException("VillageYouthAspirations not found"));
        VillageYouthAspirationsDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<VillageYouthAspirationsDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<VillageYouthAspirations> savedData = repository.findAllById(ids);
        Set<VillageYouthAspirationsDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected VillageYouthAspirationsDTO postHookSave(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected VillageYouthAspirationsDTO preHookSave(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected VillageYouthAspirationsDTO postHookUpdate(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected VillageYouthAspirationsDTO preHookUpdate(VillageYouthAspirationsDTO VillageYouthAspirationsDTO) {
        return VillageYouthAspirationsDTO;
    }

    protected VillageYouthAspirationsDTO postHookDelete(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected VillageYouthAspirationsDTO postHookGetById(VillageYouthAspirationsDTO dto) {
        return dto;
    }

    protected PageDTO<VillageYouthAspirationsDTO> postHookGetAll(PageDTO<VillageYouthAspirationsDTO> result) {
        return result;
    }




}
