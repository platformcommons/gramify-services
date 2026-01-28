package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.SurplusMovedThroughService;


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

public class SurplusMovedThroughServiceImpl implements SurplusMovedThroughService {

    private final SurplusMovedThroughDTOAssembler assembler;
    private final SurplusMovedThroughRepository repository;


    public SurplusMovedThroughServiceImpl(
        SurplusMovedThroughRepository repository, SurplusMovedThroughDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new SurplusMovedThrough.
     *
     * @param SurplusMovedThrough the SurplusMovedThrough DTO to save
     * @return the saved SurplusMovedThrough DTO
     */
    @Transactional
    @Override
    public SurplusMovedThroughDTO save(SurplusMovedThroughDTO SurplusMovedThrough) {
        log.debug("Entry - save(SurplusMovedThrough={})", SurplusMovedThrough);
        SurplusMovedThrough = preHookSave(SurplusMovedThrough);
        SurplusMovedThrough entity = assembler.fromDTO(SurplusMovedThrough);
        SurplusMovedThroughDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing SurplusMovedThrough.
     *
     * @param SurplusMovedThrough the SurplusMovedThrough DTO to update
     * @return the updated SurplusMovedThrough DTO
     * @throws NotFoundException if the SurplusMovedThrough is not found
     */
    @Transactional
    @Override
    public SurplusMovedThroughDTO update(SurplusMovedThroughDTO SurplusMovedThrough) {
        log.debug("Entry - update(SurplusMovedThrough={})", SurplusMovedThrough);
        SurplusMovedThrough = preHookUpdate(SurplusMovedThrough);
        SurplusMovedThrough saved = repository.findById(SurplusMovedThrough.getId()).orElseThrow(() -> new NotFoundException("SurplusMovedThrough not found"));
        saved.update(assembler.fromDTO(SurplusMovedThrough));
        saved = repository.save(saved);
        SurplusMovedThroughDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of SurplusMovedThroughs.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing SurplusMovedThrough DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<SurplusMovedThroughDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<SurplusMovedThrough> result = repository.findAll(PageRequest.of(page, size));
        Set<SurplusMovedThroughDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<SurplusMovedThroughDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a SurplusMovedThrough by ID.
     *
     * @param id     the ID of the SurplusMovedThrough to delete
     * @param reason the reason for deletion
     * @return the deleted SurplusMovedThrough DTO
     * @throws NotFoundException if the SurplusMovedThrough is not found
     */
    @Transactional
    @Override
    public SurplusMovedThroughDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        SurplusMovedThrough saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SurplusMovedThrough not found"));
        saved.deactivate(reason);
        repository.save(saved);
        SurplusMovedThroughDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a SurplusMovedThrough by ID.
     *
     * @param id the ID of the SurplusMovedThrough to retrieve
     * @return the SurplusMovedThrough DTO
     * @throws NotFoundException if the SurplusMovedThrough is not found
     */
    @Transactional(readOnly = true)
    @Override
    public SurplusMovedThroughDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        SurplusMovedThrough saved = repository.findById(id).orElseThrow(() -> new NotFoundException("SurplusMovedThrough not found"));
        SurplusMovedThroughDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<SurplusMovedThroughDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<SurplusMovedThrough> savedData = repository.findAllById(ids);
        Set<SurplusMovedThroughDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected SurplusMovedThroughDTO postHookSave(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected SurplusMovedThroughDTO preHookSave(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected SurplusMovedThroughDTO postHookUpdate(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected SurplusMovedThroughDTO preHookUpdate(SurplusMovedThroughDTO SurplusMovedThroughDTO) {
        return SurplusMovedThroughDTO;
    }

    protected SurplusMovedThroughDTO postHookDelete(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected SurplusMovedThroughDTO postHookGetById(SurplusMovedThroughDTO dto) {
        return dto;
    }

    protected PageDTO<SurplusMovedThroughDTO> postHookGetAll(PageDTO<SurplusMovedThroughDTO> result) {
        return result;
    }




}
