package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.StapleFoodsConsumedService;


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

public class StapleFoodsConsumedServiceImpl implements StapleFoodsConsumedService {

    private final StapleFoodsConsumedDTOAssembler assembler;
    private final StapleFoodsConsumedRepository repository;


    public StapleFoodsConsumedServiceImpl(
        StapleFoodsConsumedRepository repository, StapleFoodsConsumedDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new StapleFoodsConsumed.
     *
     * @param StapleFoodsConsumed the StapleFoodsConsumed DTO to save
     * @return the saved StapleFoodsConsumed DTO
     */
    @Transactional
    @Override
    public StapleFoodsConsumedDTO save(StapleFoodsConsumedDTO StapleFoodsConsumed) {
        log.debug("Entry - save(StapleFoodsConsumed={})", StapleFoodsConsumed);
        StapleFoodsConsumed = preHookSave(StapleFoodsConsumed);
        StapleFoodsConsumed entity = assembler.fromDTO(StapleFoodsConsumed);
        StapleFoodsConsumedDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing StapleFoodsConsumed.
     *
     * @param StapleFoodsConsumed the StapleFoodsConsumed DTO to update
     * @return the updated StapleFoodsConsumed DTO
     * @throws NotFoundException if the StapleFoodsConsumed is not found
     */
    @Transactional
    @Override
    public StapleFoodsConsumedDTO update(StapleFoodsConsumedDTO StapleFoodsConsumed) {
        log.debug("Entry - update(StapleFoodsConsumed={})", StapleFoodsConsumed);
        StapleFoodsConsumed = preHookUpdate(StapleFoodsConsumed);
        StapleFoodsConsumed saved = repository.findById(StapleFoodsConsumed.getId()).orElseThrow(() -> new NotFoundException("StapleFoodsConsumed not found"));
        saved.update(assembler.fromDTO(StapleFoodsConsumed));
        saved = repository.save(saved);
        StapleFoodsConsumedDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of StapleFoodsConsumeds.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing StapleFoodsConsumed DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<StapleFoodsConsumedDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<StapleFoodsConsumed> result = repository.findAll(PageRequest.of(page, size));
        Set<StapleFoodsConsumedDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<StapleFoodsConsumedDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a StapleFoodsConsumed by ID.
     *
     * @param id     the ID of the StapleFoodsConsumed to delete
     * @param reason the reason for deletion
     * @return the deleted StapleFoodsConsumed DTO
     * @throws NotFoundException if the StapleFoodsConsumed is not found
     */
    @Transactional
    @Override
    public StapleFoodsConsumedDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        StapleFoodsConsumed saved = repository.findById(id).orElseThrow(() -> new NotFoundException("StapleFoodsConsumed not found"));
        saved.deactivate(reason);
        repository.save(saved);
        StapleFoodsConsumedDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a StapleFoodsConsumed by ID.
     *
     * @param id the ID of the StapleFoodsConsumed to retrieve
     * @return the StapleFoodsConsumed DTO
     * @throws NotFoundException if the StapleFoodsConsumed is not found
     */
    @Transactional(readOnly = true)
    @Override
    public StapleFoodsConsumedDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        StapleFoodsConsumed saved = repository.findById(id).orElseThrow(() -> new NotFoundException("StapleFoodsConsumed not found"));
        StapleFoodsConsumedDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<StapleFoodsConsumedDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<StapleFoodsConsumed> savedData = repository.findAllById(ids);
        Set<StapleFoodsConsumedDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected StapleFoodsConsumedDTO postHookSave(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected StapleFoodsConsumedDTO preHookSave(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected StapleFoodsConsumedDTO postHookUpdate(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected StapleFoodsConsumedDTO preHookUpdate(StapleFoodsConsumedDTO StapleFoodsConsumedDTO) {
        return StapleFoodsConsumedDTO;
    }

    protected StapleFoodsConsumedDTO postHookDelete(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected StapleFoodsConsumedDTO postHookGetById(StapleFoodsConsumedDTO dto) {
        return dto;
    }

    protected PageDTO<StapleFoodsConsumedDTO> postHookGetAll(PageDTO<StapleFoodsConsumedDTO> result) {
        return result;
    }




}
