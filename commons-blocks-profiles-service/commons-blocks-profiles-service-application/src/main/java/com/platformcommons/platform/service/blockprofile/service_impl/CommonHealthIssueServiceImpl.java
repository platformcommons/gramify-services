package com.platformcommons.platform.service.blockprofile.service_impl;

import com.platformcommons.platform.exception.generic.NotFoundException;
import com.platformcommons.platform.service.dto.base.PageDTO;


import com.platformcommons.platform.service.blockprofile.assembler.*;
import com.platformcommons.platform.service.blockprofile.domain.*;
import com.platformcommons.platform.service.blockprofile.domain.repo.*;
import com.platformcommons.platform.service.blockprofile.dto.*;
import com.platformcommons.platform.service.blockprofile.service.CommonHealthIssueService;


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

public class CommonHealthIssueServiceImpl implements CommonHealthIssueService {

    private final CommonHealthIssueDTOAssembler assembler;
    private final CommonHealthIssueRepository repository;


    public CommonHealthIssueServiceImpl(
        CommonHealthIssueRepository repository, CommonHealthIssueDTOAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    /**
     * Saves a new CommonHealthIssue.
     *
     * @param CommonHealthIssue the CommonHealthIssue DTO to save
     * @return the saved CommonHealthIssue DTO
     */
    @Transactional
    @Override
    public CommonHealthIssueDTO save(CommonHealthIssueDTO CommonHealthIssue) {
        log.debug("Entry - save(CommonHealthIssue={})", CommonHealthIssue);
        CommonHealthIssue = preHookSave(CommonHealthIssue);
        CommonHealthIssue entity = assembler.fromDTO(CommonHealthIssue);
        CommonHealthIssueDTO result = assembler.toDTO(repository.save(entity));
        result = postHookSave(result);
        log.debug("Exit - save() with result: {}", result);
        return result;
    }

    /**
     * Updates an existing CommonHealthIssue.
     *
     * @param CommonHealthIssue the CommonHealthIssue DTO to update
     * @return the updated CommonHealthIssue DTO
     * @throws NotFoundException if the CommonHealthIssue is not found
     */
    @Transactional
    @Override
    public CommonHealthIssueDTO update(CommonHealthIssueDTO CommonHealthIssue) {
        log.debug("Entry - update(CommonHealthIssue={})", CommonHealthIssue);
        CommonHealthIssue = preHookUpdate(CommonHealthIssue);
        CommonHealthIssue saved = repository.findById(CommonHealthIssue.getId()).orElseThrow(() -> new NotFoundException("CommonHealthIssue not found"));
        saved.update(assembler.fromDTO(CommonHealthIssue));
        saved = repository.save(saved);
        CommonHealthIssueDTO result = assembler.toDTO(saved);
        result = postHookUpdate(result);
        log.debug("Exit - update() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a page of CommonHealthIssues.
     *
     * @param page the page number
     * @param size the page size
     * @return a page DTO containing CommonHealthIssue DTOs
     */
    @Transactional(readOnly = true)
    @Override
    public PageDTO<CommonHealthIssueDTO> getByPage(Integer page, Integer size) {
        log.debug("Entry - getByPage(page={}, size={})", page, size);
        Page<CommonHealthIssue> result = repository.findAll(PageRequest.of(page, size));
        Set<CommonHealthIssueDTO> resultDTOs = result.stream().map(assembler::toDTO)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        PageDTO<CommonHealthIssueDTO> pageResult = new PageDTO<>(resultDTOs, result.hasNext());
        pageResult = postHookGetAll(pageResult);
        log.debug("Exit - getByPage() with result: {}", pageResult);
        return pageResult;
    }

    /**
     * Deletes (deactivates) a CommonHealthIssue by ID.
     *
     * @param id     the ID of the CommonHealthIssue to delete
     * @param reason the reason for deletion
     * @return the deleted CommonHealthIssue DTO
     * @throws NotFoundException if the CommonHealthIssue is not found
     */
    @Transactional
    @Override
    public CommonHealthIssueDTO deleteById(Long id, String reason) {
        log.debug("Entry - deleteById(id={}, reason={})", id, reason);
        preHookDelete(id, reason);
        CommonHealthIssue saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CommonHealthIssue not found"));
        saved.deactivate(reason);
        repository.save(saved);
        CommonHealthIssueDTO result = assembler.toDTO(saved);
        result = postHookDelete(result);
        log.debug("Exit - deleteById() with result: {}", result);
        return result;
    }

    /**
     * Retrieves a CommonHealthIssue by ID.
     *
     * @param id the ID of the CommonHealthIssue to retrieve
     * @return the CommonHealthIssue DTO
     * @throws NotFoundException if the CommonHealthIssue is not found
     */
    @Transactional(readOnly = true)
    @Override
    public CommonHealthIssueDTO getById(Long id) {
        log.debug("Entry - getById(id={})", id);
        CommonHealthIssue saved = repository.findById(id).orElseThrow(() -> new NotFoundException("CommonHealthIssue not found"));
        CommonHealthIssueDTO result = assembler.toDTO(saved);
        result = postHookGetById(result);
        log.debug("Exit - getById() with result: {}", result);
        return result;
    }


    @Transactional(readOnly = true)
    @Override
    public Set<CommonHealthIssueDTO> getAllByIds(Set<Long> ids){
        log.debug("Entry - getAllByIds(ids={})", ids);
        List<CommonHealthIssue> savedData = repository.findAllById(ids);
        Set<CommonHealthIssueDTO> result = savedData.stream().map(assembler::toDTO).collect(Collectors.toCollection(LinkedHashSet::new));
        log.debug("Exit - getAllByIds() with result: {}", result);
        return result;
    }



    /*Hooks to be overridden by subclasses*/
    protected CommonHealthIssueDTO postHookSave(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected CommonHealthIssueDTO preHookSave(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected CommonHealthIssueDTO postHookUpdate(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected CommonHealthIssueDTO preHookUpdate(CommonHealthIssueDTO CommonHealthIssueDTO) {
        return CommonHealthIssueDTO;
    }

    protected CommonHealthIssueDTO postHookDelete(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected void preHookDelete(Long id, String reason) {
    }

    protected CommonHealthIssueDTO postHookGetById(CommonHealthIssueDTO dto) {
        return dto;
    }

    protected PageDTO<CommonHealthIssueDTO> postHookGetAll(PageDTO<CommonHealthIssueDTO> result) {
        return result;
    }




}
