package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.MainCreditSourceService;


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

public class MainCreditSourceServiceImpl implements MainCreditSourceService {

    private final MainCreditSourceDTOAssembler assembler;
    private final MainCreditSourceRepository repository;


    public MainCreditSourceServiceImpl(
        MainCreditSourceRepository repository, MainCreditSourceDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new MainCreditSource.
     *
     * @param MainCreditSource the MainCreditSource DTO to save
     * @return the saved MainCreditSource DTO
     */
    @Transactional
    @Override
    public MainCreditSourceDTO save(MainCreditSourceDTO MainCreditSource) {
        log.debug("Entry - save(MainCreditSource={})", MainCreditSource);
        MainCreditSource = preHookSave(MainCreditSource);
        MainCreditSource entity = assembler.fromDTO(MainCreditSource);
        MainCreditSourceDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing MainCreditSource.
     *
     * @param MainCreditSource the MainCreditSource DTO to update
     * @return the updated MainCreditSource DTO
     * @throws NotFoundException if the MainCreditSource is not found
     */
    @Transactional
    @Override
    public MainCreditSourceDTO update(MainCreditSourceDTO MainCreditSource) {
        log.debug("Entry - update(MainCreditSource={})", MainCreditSource);
        MainCreditSource = preHookUpdate(MainCreditSource);
        MainCreditSource saved = repository.findById(MainCreditSource.getId()).orElseThrow(() -> new NotFoundException("MainCreditSource not found"));
        saved.update(assembler.fromDTO(MainCreditSource));
        saved = repository.save(saved);
        MainCreditSourceDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of MainCreditSources.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing MainCreditSource DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<MainCreditSourceDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<MainCreditSource> result = repository.findAll(PageRequest.of(page, size));
        Set<MainCreditSourceDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<MainCreditSourceDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a MainCreditSource by ID.
     *
     * @param id     the ID of the MainCreditSource to delete
     * @param reason the reason for deletion
     * @return the deleted MainCreditSource DTO
     * @throws NotFoundException if the MainCreditSource is not found
     */
    @Transactional
    @Override
    public MainCreditSourceDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        MainCreditSource saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainCreditSource not found"));
        saved.deactivate(reason);
        repository.save(saved);
        MainCreditSourceDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a MainCreditSource by ID.
     *
     * @param id the ID of the MainCreditSource to retrieve
     * @return the MainCreditSource DTO
     * @throws NotFoundException if the MainCreditSource is not found
     */
    @Transactional(readOnly = true)
    @Override
    public MainCreditSourceDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        MainCreditSource saved = repository.findById(id).orElseThrow(() -> new NotFoundException("MainCreditSource not found"));
        MainCreditSourceDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<MainCreditSourceDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<MainCreditSource> savedData = repository.findAllById(ids);
        Set<MainCreditSourceDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected MainCreditSourceDTO postHookSave(MainCreditSourceDTO dto) {
        return dto;
    }

    protected MainCreditSourceDTO preHookSave(MainCreditSourceDTO dto) {
        return dto;
    }

    protected MainCreditSourceDTO postHookUpdate(MainCreditSourceDTO dto) {
        return dto;
    }

    protected MainCreditSourceDTO preHookUpdate(MainCreditSourceDTO MainCreditSourceDTO) {
        return MainCreditSourceDTO;
    }

    protected MainCreditSourceDTO postHookDelete(MainCreditSourceDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected MainCreditSourceDTO postHookGetById(MainCreditSourceDTO dto) {
        return dto;
    }

    protected PageDTO<MainCreditSourceDTO> postHookGetAll(PageDTO<MainCreditSourceDTO> result) {
        return result;
    }




}
