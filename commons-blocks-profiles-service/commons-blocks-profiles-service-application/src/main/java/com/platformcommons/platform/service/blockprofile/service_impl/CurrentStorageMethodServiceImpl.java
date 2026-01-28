package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.CurrentStorageMethodService;


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

public class CurrentStorageMethodServiceImpl implements CurrentStorageMethodService {

    private final CurrentStorageMethodDTOAssembler assembler;
    private final CurrentStorageMethodRepository repository;


    public CurrentStorageMethodServiceImpl(
        CurrentStorageMethodRepository repository, CurrentStorageMethodDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new CurrentStorageMethod.
     *
     * @param CurrentStorageMethod the CurrentStorageMethod DTO to save
     * @return the saved CurrentStorageMethod DTO
     */
    @Transactional
    @Override
    public CurrentStorageMethodDTO save(CurrentStorageMethodDTO CurrentStorageMethod) {
        log.debug("Entry - save(CurrentStorageMethod={})", CurrentStorageMethod);
        CurrentStorageMethod = preHookSave(CurrentStorageMethod);
        CurrentStorageMethod entity = assembler.fromDTO(CurrentStorageMethod);
        CurrentStorageMethodDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing CurrentStorageMethod.
     *
     * @param CurrentStorageMethod the CurrentStorageMethod DTO to update
     * @return the updated CurrentStorageMethod DTO
     * @throws NotFoundException if the CurrentStorageMethod is not found
     */
    @Transactional
    @Override
    public CurrentStorageMethodDTO update(CurrentStorageMethodDTO CurrentStorageMethod) {
        log.debug("Entry - update(CurrentStorageMethod={})", CurrentStorageMethod);
        CurrentStorageMethod = preHookUpdate(CurrentStorageMethod);
        CurrentStorageMethod saved = repository.findById(CurrentStorageMethod.getId()).orElseThrow(() -> new NotFoundException("CurrentStorageMethod not found"));
        saved.update(assembler.fromDTO(CurrentStorageMethod));
        saved = repository.save(saved);
        CurrentStorageMethodDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of CurrentStorageMethods.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing CurrentStorageMethod DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<CurrentStorageMethodDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<CurrentStorageMethod> result = repository.findAll(PageRequest.of(page, size));
        Set<CurrentStorageMethodDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<CurrentStorageMethodDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a CurrentStorageMethod by ID.
     *
     * @param id     the ID of the CurrentStorageMethod to delete
     * @param reason the reason for deletion
     * @return the deleted CurrentStorageMethod DTO
     * @throws NotFoundException if the CurrentStorageMethod is not found
     */
    @Transactional
    @Override
    public CurrentStorageMethodDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        CurrentStorageMethod saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CurrentStorageMethod not found"));
        saved.deactivate(reason);
        repository.save(saved);
        CurrentStorageMethodDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a CurrentStorageMethod by ID.
     *
     * @param id the ID of the CurrentStorageMethod to retrieve
     * @return the CurrentStorageMethod DTO
     * @throws NotFoundException if the CurrentStorageMethod is not found
     */
    @Transactional(readOnly = true)
    @Override
    public CurrentStorageMethodDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        CurrentStorageMethod saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CurrentStorageMethod not found"));
        CurrentStorageMethodDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<CurrentStorageMethodDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<CurrentStorageMethod> savedData = repository.findAllById(ids);
        Set<CurrentStorageMethodDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CurrentStorageMethodDTO postHookSave(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected CurrentStorageMethodDTO preHookSave(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected CurrentStorageMethodDTO postHookUpdate(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected CurrentStorageMethodDTO preHookUpdate(CurrentStorageMethodDTO CurrentStorageMethodDTO) {
        return CurrentStorageMethodDTO;
    }

    protected CurrentStorageMethodDTO postHookDelete(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CurrentStorageMethodDTO postHookGetById(CurrentStorageMethodDTO dto) {
        return dto;
    }

    protected PageDTO<CurrentStorageMethodDTO> postHookGetAll(PageDTO<CurrentStorageMethodDTO> result) {
        return result;
    }




}
