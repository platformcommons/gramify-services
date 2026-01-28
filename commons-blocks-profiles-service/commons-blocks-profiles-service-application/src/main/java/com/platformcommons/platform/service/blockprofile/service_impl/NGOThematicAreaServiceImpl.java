package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.NGOThematicAreaService;


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

public class NGOThematicAreaServiceImpl implements NGOThematicAreaService {

    private final NGOThematicAreaDTOAssembler assembler;
    private final NGOThematicAreaRepository repository;


    public NGOThematicAreaServiceImpl(
        NGOThematicAreaRepository repository, NGOThematicAreaDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new NGOThematicArea.
     *
     * @param NGOThematicArea the NGOThematicArea DTO to save
     * @return the saved NGOThematicArea DTO
     */
    @Transactional
    @Override
    public NGOThematicAreaDTO save(NGOThematicAreaDTO NGOThematicArea) {
        log.debug("Entry - save(NGOThematicArea={})", NGOThematicArea);
        NGOThematicArea = preHookSave(NGOThematicArea);
        NGOThematicArea entity = assembler.fromDTO(NGOThematicArea);
        NGOThematicAreaDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing NGOThematicArea.
     *
     * @param NGOThematicArea the NGOThematicArea DTO to update
     * @return the updated NGOThematicArea DTO
     * @throws NotFoundException if the NGOThematicArea is not found
     */
    @Transactional
    @Override
    public NGOThematicAreaDTO update(NGOThematicAreaDTO NGOThematicArea) {
        log.debug("Entry - update(NGOThematicArea={})", NGOThematicArea);
        NGOThematicArea = preHookUpdate(NGOThematicArea);
        NGOThematicArea saved = repository.findById(NGOThematicArea.getId()).orElseThrow(() -> new NotFoundException("NGOThematicArea not found"));
        saved.update(assembler.fromDTO(NGOThematicArea));
        saved = repository.save(saved);
        NGOThematicAreaDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of NGOThematicAreas.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing NGOThematicArea DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<NGOThematicAreaDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<NGOThematicArea> result = repository.findAll(PageRequest.of(page, size));
        Set<NGOThematicAreaDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<NGOThematicAreaDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a NGOThematicArea by ID.
     *
     * @param id     the ID of the NGOThematicArea to delete
     * @param reason the reason for deletion
     * @return the deleted NGOThematicArea DTO
     * @throws NotFoundException if the NGOThematicArea is not found
     */
    @Transactional
    @Override
    public NGOThematicAreaDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        NGOThematicArea saved = repository.findById(id).orElseThrow(() -> new NotFoundException("NGOThematicArea not found"));
        saved.deactivate(reason);
        repository.save(saved);
        NGOThematicAreaDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a NGOThematicArea by ID.
     *
     * @param id the ID of the NGOThematicArea to retrieve
     * @return the NGOThematicArea DTO
     * @throws NotFoundException if the NGOThematicArea is not found
     */
    @Transactional(readOnly = true)
    @Override
    public NGOThematicAreaDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        NGOThematicArea saved = repository.findById(id).orElseThrow(() -> new NotFoundException("NGOThematicArea not found"));
        NGOThematicAreaDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<NGOThematicAreaDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<NGOThematicArea> savedData = repository.findAllById(ids);
        Set<NGOThematicAreaDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected NGOThematicAreaDTO postHookSave(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected NGOThematicAreaDTO preHookSave(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected NGOThematicAreaDTO postHookUpdate(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected NGOThematicAreaDTO preHookUpdate(NGOThematicAreaDTO NGOThematicAreaDTO) {
        return NGOThematicAreaDTO;
    }

    protected NGOThematicAreaDTO postHookDelete(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected NGOThematicAreaDTO postHookGetById(NGOThematicAreaDTO dto) {
        return dto;
    }

    protected PageDTO<NGOThematicAreaDTO> postHookGetAll(PageDTO<NGOThematicAreaDTO> result) {
        return result;
    }




}
