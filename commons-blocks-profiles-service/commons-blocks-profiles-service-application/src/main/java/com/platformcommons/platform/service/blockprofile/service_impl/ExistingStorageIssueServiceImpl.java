package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.ExistingStorageIssueService;


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

public class ExistingStorageIssueServiceImpl implements ExistingStorageIssueService {

    private final ExistingStorageIssueDTOAssembler assembler;
    private final ExistingStorageIssueRepository repository;


    public ExistingStorageIssueServiceImpl(
        ExistingStorageIssueRepository repository, ExistingStorageIssueDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new ExistingStorageIssue.
     *
     * @param ExistingStorageIssue the ExistingStorageIssue DTO to save
     * @return the saved ExistingStorageIssue DTO
     */
    @Transactional
    @Override
    public ExistingStorageIssueDTO save(ExistingStorageIssueDTO ExistingStorageIssue) {
        log.debug("Entry - save(ExistingStorageIssue={})", ExistingStorageIssue);
        ExistingStorageIssue = preHookSave(ExistingStorageIssue);
        ExistingStorageIssue entity = assembler.fromDTO(ExistingStorageIssue);
        ExistingStorageIssueDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing ExistingStorageIssue.
     *
     * @param ExistingStorageIssue the ExistingStorageIssue DTO to update
     * @return the updated ExistingStorageIssue DTO
     * @throws NotFoundException if the ExistingStorageIssue is not found
     */
    @Transactional
    @Override
    public ExistingStorageIssueDTO update(ExistingStorageIssueDTO ExistingStorageIssue) {
        log.debug("Entry - update(ExistingStorageIssue={})", ExistingStorageIssue);
        ExistingStorageIssue = preHookUpdate(ExistingStorageIssue);
        ExistingStorageIssue saved = repository.findById(ExistingStorageIssue.getId()).orElseThrow(() -> new NotFoundException("ExistingStorageIssue not found"));
        saved.update(assembler.fromDTO(ExistingStorageIssue));
        saved = repository.save(saved);
        ExistingStorageIssueDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of ExistingStorageIssues.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing ExistingStorageIssue DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<ExistingStorageIssueDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<ExistingStorageIssue> result = repository.findAll(PageRequest.of(page, size));
        Set<ExistingStorageIssueDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<ExistingStorageIssueDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a ExistingStorageIssue by ID.
     *
     * @param id     the ID of the ExistingStorageIssue to delete
     * @param reason the reason for deletion
     * @return the deleted ExistingStorageIssue DTO
     * @throws NotFoundException if the ExistingStorageIssue is not found
     */
    @Transactional
    @Override
    public ExistingStorageIssueDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        ExistingStorageIssue saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ExistingStorageIssue not found"));
        saved.deactivate(reason);
        repository.save(saved);
        ExistingStorageIssueDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a ExistingStorageIssue by ID.
     *
     * @param id the ID of the ExistingStorageIssue to retrieve
     * @return the ExistingStorageIssue DTO
     * @throws NotFoundException if the ExistingStorageIssue is not found
     */
    @Transactional(readOnly = true)
    @Override
    public ExistingStorageIssueDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        ExistingStorageIssue saved = repository.findById(id).orElseThrow(() -> new NotFoundException("ExistingStorageIssue not found"));
        ExistingStorageIssueDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<ExistingStorageIssueDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<ExistingStorageIssue> savedData = repository.findAllById(ids);
        Set<ExistingStorageIssueDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected ExistingStorageIssueDTO postHookSave(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected ExistingStorageIssueDTO preHookSave(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected ExistingStorageIssueDTO postHookUpdate(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected ExistingStorageIssueDTO preHookUpdate(ExistingStorageIssueDTO ExistingStorageIssueDTO) {
        return ExistingStorageIssueDTO;
    }

    protected ExistingStorageIssueDTO postHookDelete(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected ExistingStorageIssueDTO postHookGetById(ExistingStorageIssueDTO dto) {
        return dto;
    }

    protected PageDTO<ExistingStorageIssueDTO> postHookGetAll(PageDTO<ExistingStorageIssueDTO> result) {
        return result;
    }




}
