package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.FertilizersInDemandService;


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

public class FertilizersInDemandServiceImpl implements FertilizersInDemandService {

    private final FertilizersInDemandDTOAssembler assembler;
    private final FertilizersInDemandRepository repository;


    public FertilizersInDemandServiceImpl(
        FertilizersInDemandRepository repository, FertilizersInDemandDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new FertilizersInDemand.
     *
     * @param FertilizersInDemand the FertilizersInDemand DTO to save
     * @return the saved FertilizersInDemand DTO
     */
    @Transactional
    @Override
    public FertilizersInDemandDTO save(FertilizersInDemandDTO FertilizersInDemand) {
        log.debug("Entry - save(FertilizersInDemand={})", FertilizersInDemand);
        FertilizersInDemand = preHookSave(FertilizersInDemand);
        FertilizersInDemand entity = assembler.fromDTO(FertilizersInDemand);
        FertilizersInDemandDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing FertilizersInDemand.
     *
     * @param FertilizersInDemand the FertilizersInDemand DTO to update
     * @return the updated FertilizersInDemand DTO
     * @throws NotFoundException if the FertilizersInDemand is not found
     */
    @Transactional
    @Override
    public FertilizersInDemandDTO update(FertilizersInDemandDTO FertilizersInDemand) {
        log.debug("Entry - update(FertilizersInDemand={})", FertilizersInDemand);
        FertilizersInDemand = preHookUpdate(FertilizersInDemand);
        FertilizersInDemand saved = repository.findById(FertilizersInDemand.getId()).orElseThrow(() -> new NotFoundException("FertilizersInDemand not found"));
        saved.update(assembler.fromDTO(FertilizersInDemand));
        saved = repository.save(saved);
        FertilizersInDemandDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of FertilizersInDemands.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing FertilizersInDemand DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<FertilizersInDemandDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<FertilizersInDemand> result = repository.findAll(PageRequest.of(page, size));
        Set<FertilizersInDemandDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<FertilizersInDemandDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a FertilizersInDemand by ID.
     *
     * @param id     the ID of the FertilizersInDemand to delete
     * @param reason the reason for deletion
     * @return the deleted FertilizersInDemand DTO
     * @throws NotFoundException if the FertilizersInDemand is not found
     */
    @Transactional
    @Override
    public FertilizersInDemandDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        FertilizersInDemand saved = repository.findById(id).orElseThrow(() -> new NotFoundException("FertilizersInDemand not found"));
        saved.deactivate(reason);
        repository.save(saved);
        FertilizersInDemandDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a FertilizersInDemand by ID.
     *
     * @param id the ID of the FertilizersInDemand to retrieve
     * @return the FertilizersInDemand DTO
     * @throws NotFoundException if the FertilizersInDemand is not found
     */
    @Transactional(readOnly = true)
    @Override
    public FertilizersInDemandDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        FertilizersInDemand saved = repository.findById(id).orElseThrow(() -> new NotFoundException("FertilizersInDemand not found"));
        FertilizersInDemandDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<FertilizersInDemandDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<FertilizersInDemand> savedData = repository.findAllById(ids);
        Set<FertilizersInDemandDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected FertilizersInDemandDTO postHookSave(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected FertilizersInDemandDTO preHookSave(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected FertilizersInDemandDTO postHookUpdate(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected FertilizersInDemandDTO preHookUpdate(FertilizersInDemandDTO FertilizersInDemandDTO) {
        return FertilizersInDemandDTO;
    }

    protected FertilizersInDemandDTO postHookDelete(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected FertilizersInDemandDTO postHookGetById(FertilizersInDemandDTO dto) {
        return dto;
    }

    protected PageDTO<FertilizersInDemandDTO> postHookGetAll(PageDTO<FertilizersInDemandDTO> result) {
        return result;
    }




}
