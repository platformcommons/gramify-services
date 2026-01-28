package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.PesticidesInDemandService;


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

public class PesticidesInDemandServiceImpl implements PesticidesInDemandService {

    private final PesticidesInDemandDTOAssembler assembler;
    private final PesticidesInDemandRepository repository;


    public PesticidesInDemandServiceImpl(
        PesticidesInDemandRepository repository, PesticidesInDemandDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new PesticidesInDemand.
     *
     * @param PesticidesInDemand the PesticidesInDemand DTO to save
     * @return the saved PesticidesInDemand DTO
     */
    @Transactional
    @Override
    public PesticidesInDemandDTO save(PesticidesInDemandDTO PesticidesInDemand) {
        log.debug("Entry - save(PesticidesInDemand={})", PesticidesInDemand);
        PesticidesInDemand = preHookSave(PesticidesInDemand);
        PesticidesInDemand entity = assembler.fromDTO(PesticidesInDemand);
        PesticidesInDemandDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing PesticidesInDemand.
     *
     * @param PesticidesInDemand the PesticidesInDemand DTO to update
     * @return the updated PesticidesInDemand DTO
     * @throws NotFoundException if the PesticidesInDemand is not found
     */
    @Transactional
    @Override
    public PesticidesInDemandDTO update(PesticidesInDemandDTO PesticidesInDemand) {
        log.debug("Entry - update(PesticidesInDemand={})", PesticidesInDemand);
        PesticidesInDemand = preHookUpdate(PesticidesInDemand);
        PesticidesInDemand saved = repository.findById(PesticidesInDemand.getId()).orElseThrow(() -> new NotFoundException("PesticidesInDemand not found"));
        saved.update(assembler.fromDTO(PesticidesInDemand));
        saved = repository.save(saved);
        PesticidesInDemandDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of PesticidesInDemands.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing PesticidesInDemand DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<PesticidesInDemandDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<PesticidesInDemand> result = repository.findAll(PageRequest.of(page, size));
        Set<PesticidesInDemandDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<PesticidesInDemandDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a PesticidesInDemand by ID.
     *
     * @param id     the ID of the PesticidesInDemand to delete
     * @param reason the reason for deletion
     * @return the deleted PesticidesInDemand DTO
     * @throws NotFoundException if the PesticidesInDemand is not found
     */
    @Transactional
    @Override
    public PesticidesInDemandDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        PesticidesInDemand saved = repository.findById(id).orElseThrow(() -> new NotFoundException("PesticidesInDemand not found"));
        saved.deactivate(reason);
        repository.save(saved);
        PesticidesInDemandDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a PesticidesInDemand by ID.
     *
     * @param id the ID of the PesticidesInDemand to retrieve
     * @return the PesticidesInDemand DTO
     * @throws NotFoundException if the PesticidesInDemand is not found
     */
    @Transactional(readOnly = true)
    @Override
    public PesticidesInDemandDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        PesticidesInDemand saved = repository.findById(id).orElseThrow(() -> new NotFoundException("PesticidesInDemand not found"));
        PesticidesInDemandDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<PesticidesInDemandDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<PesticidesInDemand> savedData = repository.findAllById(ids);
        Set<PesticidesInDemandDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected PesticidesInDemandDTO postHookSave(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected PesticidesInDemandDTO preHookSave(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected PesticidesInDemandDTO postHookUpdate(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected PesticidesInDemandDTO preHookUpdate(PesticidesInDemandDTO PesticidesInDemandDTO) {
        return PesticidesInDemandDTO;
    }

    protected PesticidesInDemandDTO postHookDelete(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected PesticidesInDemandDTO postHookGetById(PesticidesInDemandDTO dto) {
        return dto;
    }

    protected PageDTO<PesticidesInDemandDTO> postHookGetAll(PageDTO<PesticidesInDemandDTO> result) {
        return result;
    }




}
