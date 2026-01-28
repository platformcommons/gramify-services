package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.SeedsInDemandService;


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

public class SeedsInDemandServiceImpl implements SeedsInDemandService {

    private final SeedsInDemandDTOAssembler assembler;
    private final SeedsInDemandRepository repository;


    public SeedsInDemandServiceImpl(
        SeedsInDemandRepository repository, SeedsInDemandDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new SeedsInDemand.
     *
     * @param SeedsInDemand the SeedsInDemand DTO to save
     * @return the saved SeedsInDemand DTO
     */
    @Transactional
    @Override
    public SeedsInDemandDTO save(SeedsInDemandDTO SeedsInDemand) {
        log.debug("Entry - save(SeedsInDemand={})", SeedsInDemand);
        SeedsInDemand = preHookSave(SeedsInDemand);
        SeedsInDemand entity = assembler.fromDTO(SeedsInDemand);
        SeedsInDemandDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing SeedsInDemand.
     *
     * @param SeedsInDemand the SeedsInDemand DTO to update
     * @return the updated SeedsInDemand DTO
     * @throws NotFoundException if the SeedsInDemand is not found
     */
    @Transactional
    @Override
    public SeedsInDemandDTO update(SeedsInDemandDTO SeedsInDemand) {
        log.debug("Entry - update(SeedsInDemand={})", SeedsInDemand);
        SeedsInDemand = preHookUpdate(SeedsInDemand);
        SeedsInDemand saved = repository.findById(SeedsInDemand.getId()).orElseThrow(() -> new NotFoundException("SeedsInDemand not found"));
        saved.update(assembler.fromDTO(SeedsInDemand));
        saved = repository.save(saved);
        SeedsInDemandDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of SeedsInDemands.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing SeedsInDemand DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<SeedsInDemandDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<SeedsInDemand> result = repository.findAll(PageRequest.of(page, size));
        Set<SeedsInDemandDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<SeedsInDemandDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a SeedsInDemand by ID.
     *
     * @param id     the ID of the SeedsInDemand to delete
     * @param reason the reason for deletion
     * @return the deleted SeedsInDemand DTO
     * @throws NotFoundException if the SeedsInDemand is not found
     */
    @Transactional
    @Override
    public SeedsInDemandDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        SeedsInDemand saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SeedsInDemand not found"));
        saved.deactivate(reason);
        repository.save(saved);
        SeedsInDemandDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a SeedsInDemand by ID.
     *
     * @param id the ID of the SeedsInDemand to retrieve
     * @return the SeedsInDemand DTO
     * @throws NotFoundException if the SeedsInDemand is not found
     */
    @Transactional(readOnly = true)
    @Override
    public SeedsInDemandDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        SeedsInDemand saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SeedsInDemand not found"));
        SeedsInDemandDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<SeedsInDemandDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<SeedsInDemand> savedData = repository.findAllById(ids);
        Set<SeedsInDemandDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SeedsInDemandDTO postHookSave(SeedsInDemandDTO dto) {
        return dto;
    }

    protected SeedsInDemandDTO preHookSave(SeedsInDemandDTO dto) {
        return dto;
    }

    protected SeedsInDemandDTO postHookUpdate(SeedsInDemandDTO dto) {
        return dto;
    }

    protected SeedsInDemandDTO preHookUpdate(SeedsInDemandDTO SeedsInDemandDTO) {
        return SeedsInDemandDTO;
    }

    protected SeedsInDemandDTO postHookDelete(SeedsInDemandDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SeedsInDemandDTO postHookGetById(SeedsInDemandDTO dto) {
        return dto;
    }

    protected PageDTO<SeedsInDemandDTO> postHookGetAll(PageDTO<SeedsInDemandDTO> result) {
        return result;
    }




}
